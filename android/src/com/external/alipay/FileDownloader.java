/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.external.alipay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.net.ssl.SSLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;


/**
 * 文件下载类，支持断电续传
 * 
 * @author jianmin.jiang
 * 
 * @version $Id: FileDownloader.java, v 0.1 2012-2-14 下午8:41:05 jianmin.jiang
 *          Exp $
 */
@SuppressLint("HandlerLeak")
public final class FileDownloader {

	private String fileUrl;
	private String savePath;
	private String tmpPath;
	private FileDownloader.IDownloadProgress progressOutput;
	private FileFetch fetch;
	private boolean showProgress;

	public FileDownloader() {
		showProgress = false;
	}

	public FileDownloader(boolean showProgress) {
		this.showProgress = showProgress;
	}

	/**
	 * @param fileUrl
	 *            文件下载链接地址
	 */
	public final void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public final void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
	}

	protected final boolean showProgress() {
		return this.showProgress;
	}

	/**
	 * @param savePath
	 *            文件保存地址，使用绝对路径
	 */
	public final void setSavePath(String savePath) {
		this.savePath = savePath;
		this.tmpPath = savePath + ".tmp";
	}

	/**
	 * @param progressOutput
	 *            下载进度输出类
	 */
	public final void setProgressOutput(
			FileDownloader.IDownloadProgress progressOutput) {
		if (progressOutput != null) {
			this.progressOutput = progressOutput;
		}
	}

	public void start() {
		final ProgressOutput output = new ProgressOutput(Looper.getMainLooper());
		new Thread(new Runnable() {

			public void run() {
				fetch = new FileFetch(fileUrl, savePath, FileDownloader.this);
				long fileLen = -1;
				if (showProgress) {
					fileLen = getFileSize();
					if (fileLen <= 0) {
						output.sendEmptyMessage(0);
						return;
					}
				} else {
					deleteFile();
				}
				if (showProgress) {
					readTempFile();
					if (fetch.getFileEnd() != fileLen) {
						deleteFile();
						fetch.setFileStart(0);
						fetch.setFileEnd(fileLen);
					}
				}
				new Thread(fetch).start();
				output.isFinished = false;
				while (!fetch.isStop()) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					output.sendEmptyMessage(0);
				}
				output.sendEmptyMessage(0);
			}
		}).start();
	}

	public void stop() {

		fetch.stop();
	}

	private long getFileSize() {
		long fileLength = -1;
		try {
			HttpEntity entity = getHttpEntity(fileUrl, false);
			fileLength = entity.getContentLength();
		} catch (Exception e) {
//			AppErrorException.printException(this.getClass(),
//					"can not get file length", e);
			e.printStackTrace();
		}
		return fileLength;
	}

	private void deleteFile() {
		File file = new File(savePath);
		if (file.exists()) {
			file.delete();
		}
		file = new File(tmpPath);
		if (file.exists()) {
			file.delete();
		}
	}

	protected void writeTempFile() {
		FileOutputStream out = null;
		ObjectOutputStream objOut = null;
		try {
			out = new FileOutputStream(this.tmpPath);
			objOut = new ObjectOutputStream(out);
			objOut.writeLong(fetch.getFileStart());
			objOut.writeLong(fetch.getFileEnd());
			objOut.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
			try {
				objOut.close();
			} catch (Exception e) {
			}
		}
	}

	private void readTempFile() {
		FileInputStream in = null;
		ObjectInputStream objIn = null;
		try {
			in = new FileInputStream(this.tmpPath);
			objIn = new ObjectInputStream(in);
			fetch.setFileStart(objIn.readLong());
			fetch.setFileEnd(objIn.readLong());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {
			}
			try {
				objIn.close();
			} catch (Exception e) {
			}
		}
	}
	public static HttpEntity getHttpEntity(String netAddress, boolean isZip)
			throws Exception {
		try {
			// HttpGet连接对象
			HttpGet httpGet = new HttpGet(netAddress);
			// 取得HttpClient
			HttpClient httpClient = new DefaultHttpClient();
			if (isZip) {
				httpGet.addHeader("Accept-Encoding", "gzip");
			}
			// 请求HttpClient，获得HttpResponce
			HttpResponse response = httpClient.execute(httpGet);
			// 请求成功
			int code = response.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				return entity;
			} else {
				throw new Exception("net work exception,ErrorCode :"+code);
			}
		} catch (SSLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private class ProgressOutput extends Handler {

		private boolean isFinished;
		@SuppressLint("HandlerLeak")
		public ProgressOutput(Looper looper) {
			super(looper);
			isFinished = false;
		}

		@Override
		public void handleMessage(Message msg) {
			if (progressOutput == null) {
				return;
			}
			if (progressOutput == null) {
				return;
			}
			try {
				float progress = 50;
				if (showProgress) {
					progress = ((fetch.getFileStart() * 100) / fetch
							.getFileEnd());
				} else if (fetch.isStop()) {
					progress = 100;
				}

				if (fetch.isStop()) {
					if (progress == 100 && !isFinished) {
						progressOutput.downloadSucess();
						isFinished = true;
					} else if (progress > 100) {
						deleteFile();
						progressOutput.downloadFail();
					} else if(!isFinished){
						progressOutput.downloadFail();
					}
				} else {
					progressOutput.downloadProgress(progress);
				}
			} catch (Exception e) {
				progressOutput.downloadFail();
			}
		}
	}

	public interface IDownloadProgress {

		void downloadProgress(float progress);

		void downloadSucess();

		void downloadFail();

	}

}
