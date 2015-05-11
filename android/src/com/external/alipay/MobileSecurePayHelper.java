/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package com.external.alipay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import com.insthub.BeeFramework.view.MyDialog;
import com.insthub.ecmobile.activity.AlixPayActivity;
import com.insthub.ecmobile.activity.EcmobileMainActivity;
import com.insthub.ecmobile.activity.PayWebActivity;
import com.insthub.ecmobile.protocol.ORDER_INFO;
import com.insthub.ecmobile.protocol.SESSION;
import org.json.JSONException;
import org.json.JSONObject;

import com.external.alipay.FileDownloader.IDownloadProgress;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.insthub.ecmobile.R;

/**
 * 检测安全支付服务是否正确安装，如果没有安装进行本地安装，或者下载安装， 检测安全支付服务版本，有新版本时进行下载。
 * 
 */
@SuppressLint("HandlerLeak")
public class MobileSecurePayHelper {
	static final String TAG = "MobileSecurePayHelper";

	private ProgressDialog mProgress = null;
	Context mContext = null;
    ORDER_INFO orderInfo;
	public MobileSecurePayHelper(Context context,ORDER_INFO orderInfo) {
		mContext = context;
        this.orderInfo=orderInfo;
	}

	/**
	 * 检测安全支付服务是否安装
	 * 
	 * @return
	 */
	public boolean detectMobile_sp() {
		boolean isMobile_spExist = isMobile_spExist();
		if (!isMobile_spExist) {
			//
			// get the cacheDir.
			// 获取系统缓冲绝对路径 获取/data/data//cache目录
			File cacheDir = mContext.getCacheDir();
			final String cachePath = cacheDir.getAbsolutePath() + "/temp.apk";
			//
			// 捆绑安装
			retrieveApkFromAssets(mContext, PartnerConfig.ALIPAY_PLUGIN_NAME,
					cachePath);

			mProgress = BaseHelper.showProgress(mContext, null, "正在检测安全支付服务版本",
					false, true);

			// 实例新线程检测是否有新版本进行下载
			new Thread(new Runnable() {
				public void run() {
					//
					// 检测是否有新的版本。
					PackageInfo apkInfo = getApkInfo(mContext, cachePath);
					String newApkdlUrl = checkNewUpdate(apkInfo);

					//
					// 动态下载
					if (newApkdlUrl != null) {
						FileDownloader fd = new FileDownloader();
						fd.setFileUrl(newApkdlUrl);
						fd.setSavePath(cachePath);
						fd.setProgressOutput(new IDownloadProgress() {
							@Override
							public void downloadSucess() {
								Message msg = mHandler.obtainMessage(
										AlixId.RQF_INSTALL_CHECK, cachePath);
								mHandler.sendMessage(msg);
							}

							@Override
							public void downloadProgress(float progress) {

							}

							@Override
							public void downloadFail() {
								Message msg = mHandler.obtainMessage(
										AlixId.RQF_DOWNLOAD_FAILED, cachePath);
								mHandler.sendMessage(msg);
							}
						});
						fd.start();
					}else{
						Message msg = mHandler.obtainMessage(
								AlixId.RQF_INSTALL_WITHOUT_DOWNLOAD, cachePath);
						mHandler.sendMessage(msg);
					}

					// send the result back to caller.
					// 发送结果

				}
			}).start();
		}
		// else ok.

		return isMobile_spExist;
	}

	/**
	 * 显示确认安装的提示
	 * 
	 * @param context
	 *            上下文环境
	 * @param cachePath
	 *            安装文件路径
	 */
	public void showInstallConfirmDialog(final Context context,
			final String cachePath) {
        Resources resource = context.getResources();
        String exit=resource.getString(R.string.confirm_install_hint);
        String exiten=resource.getString(R.string.confirm_install);
        final MyDialog mDialog = new MyDialog(context, exit, exiten);
        mDialog.show();
        mDialog.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
//
                // 修改apk权限
                BaseHelper.chmod("777", cachePath);

                //
                // install the apk.
                // 安装安全支付服务APK
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.parse("file://" + cachePath),
                        "application/vnd.android.package-archive");
                context.startActivity(intent);
            }
        });
        mDialog.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //支付宝web支付
                mDialog.dismiss();
                Intent data=new Intent();
                data.putExtra("pay_result", "fail");
                ((Activity)mContext).setResult(Activity.RESULT_OK, data);
                ((Activity) mContext).finish();

            }
        });
	}

	/**
	 * 遍历程序列表，判断是否安装安全支付服务
	 * 
	 * @return
	 */
	public boolean isMobile_spExist() {
		PackageManager manager = mContext.getPackageManager();
		List<PackageInfo> pkgList = manager.getInstalledPackages(0);
		for (int i = 0; i < pkgList.size(); i++) {
			PackageInfo pI = pkgList.get(i);
			if (pI.packageName.equalsIgnoreCase("com.alipay.android.app"))
				return true;
		}

		return false;
	}

	/**
	 * 安装安全支付服务，安装assets文件夹下的apk
	 * 
	 * @param context
	 *            上下文环境
	 * @param fileName
	 *            apk名称
	 * @param path
	 *            安装路径
	 * @return
	 */
	public boolean retrieveApkFromAssets(Context context, String fileName,
			String path) {
		boolean bRet = false;

		try {
			InputStream is = context.getAssets().open(fileName);

			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);

			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}

			fos.close();
			is.close();

			bRet = true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return bRet;
	}

	/**
	 * 获取未安装的APK信息
	 * 
	 * @param context
	 * @param archiveFilePath
	 *            APK文件的路径。如：/sdcard/download/XX.apk
	 */
	public static PackageInfo getApkInfo(Context context, String archiveFilePath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo apkInfo = pm.getPackageArchiveInfo(archiveFilePath,
				PackageManager.GET_META_DATA);
		return apkInfo;
	}

	/**
	 * 检查是否有新版本，如果有，返回apk下载地址
	 * 
	 * @param packageInfo
	 *            {@link PackageInfo}
	 * @return
	 */
	public String checkNewUpdate(PackageInfo packageInfo) {
		String url = null;

		try {
			JSONObject resp = sendCheckNewUpdate(packageInfo.versionName);
			// JSONObject resp = sendCheckNewUpdate("1.0.0");
			if (resp.getString("needUpdate").equalsIgnoreCase("true")) {
				url = resp.getString("updateUrl");
			}
			// else ok.
		} catch (Exception e) {
			e.printStackTrace();
		}

		return url;
	}

	/**
	 * 发送当前版本信息，返回是否需要升级 如果需要升级返回更新apk地址
	 * 
	 * @param versionName
	 *            当前版本号
	 * @return
	 */
	public JSONObject sendCheckNewUpdate(String versionName) {
		JSONObject objResp = null;
		try {
			JSONObject req = new JSONObject();
			req.put(AlixDefine.action, AlixDefine.actionUpdate);

			JSONObject data = new JSONObject();
			data.put(AlixDefine.platform, "android");
			data.put(AlixDefine.VERSION, versionName);
			data.put(AlixDefine.partner, "");

			req.put(AlixDefine.data, data);

			objResp = sendRequest(req.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return objResp;
	}

	/**
	 * 发送json数据
	 * 
	 * @param content
	 * @return
	 */
	public JSONObject sendRequest(final String content) {
		NetworkManager nM = new NetworkManager(this.mContext);

		//
		JSONObject jsonResponse = null;
		try {
			String response = null;

			synchronized (nM) {
				//
				response = nM.SendAndWaitResponse(content, Constant.server_url);
			}

			jsonResponse = new JSONObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//
		if (jsonResponse != null)
			BaseHelper.log(TAG, jsonResponse.toString());

		return jsonResponse;
	}

	/**
	 * 动态下载apk
	 * 
	 * @param context
	 *            上下文环境
	 * @param url
	 *            下载地址
	 * @param filename
	 *            文件名称
	 * @return
	 */
	public boolean retrieveApkFromNet(Context context, String url,
			String filename) {
		boolean ret = false;

		try {
			NetworkManager nm = new NetworkManager(mContext);
			ret = nm.urlDownloadToFile(context, url, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	//
	// close the progress bar
	void closeProgress() {
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	// the handler use to receive the install check result.
	// 此处接收安装检测结果
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				Log.e(TAG, "msg = "+msg);
				switch (msg.what) {
				case AlixId.RQF_INSTALL_CHECK:
				case AlixId.RQF_INSTALL_WITHOUT_DOWNLOAD:
				case AlixId.RQF_DOWNLOAD_FAILED: {
					//
					Log.i(TAG, "show Install dialog");
					closeProgress();
					String cachePath = (String) msg.obj;

					showInstallConfirmDialog(mContext, cachePath);
				}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
}
