package com.insthub.BeeFramework.activity;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.insthub.BeeFramework.BeeFrameworkConst;
import com.insthub.BeeFramework.view.TouchableImageView;
import com.insthub.ecmobile.R;

public class FullScreenPhotoActivity extends BaseActivity implements OnClickListener{

    public static final String FLAG_URL = "img_url";
    public static final String IMG_TYPE = "img_type";
    private static final int URL_SEPARATOR = '/';
    private String imgUrl;
    private TouchableImageView mImgView;
    private ProgressBar mProgressBar;
    private LinearLayout mBtnsLine;
    private ImageView mBtnsRotateRight;
    private ImageView mBtnsRotateLeft;
    private ImageView mBtnsSavePic;
    private ImageView mBtnsBack;
    private FrameLayout mBackgroundLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fullscreen_photo);
        String imageUrlString = getIntent().getStringExtra(FLAG_URL);
        mImgView = (TouchableImageView) findViewById(R.id.img);
        mImgView.setOnClickListener(this);
        mImgView.setImageWithURL(this, imageUrlString,R.drawable.default_image);

        mBtnsRotateRight = (ImageView)findViewById(R.id.btn_rotate_right);
        mBtnsRotateRight.setOnClickListener(this);
        mBtnsRotateLeft = (ImageView)findViewById(R.id.btn_rotate_left);
        mBtnsRotateLeft.setOnClickListener(this);

        mBackgroundLayout = (FrameLayout)findViewById(R.id.back_ground_layout);
        mBackgroundLayout.setOnClickListener(this);

        mProgressBar = (ProgressBar)findViewById(R.id.activity_img_progress);

        mBtnsLine = (LinearLayout)findViewById(R.id.ll_btns);
        mBtnsLine.setVisibility(View.VISIBLE);
        mBtnsSavePic = (ImageView)findViewById(R.id.btn_save_pic);
        mBtnsSavePic.setOnClickListener(this);
        mBtnsBack = (ImageView)findViewById(R.id.btn_back);
        mBtnsBack.setOnClickListener(this);

        mImgView.setVisibility(View.VISIBLE);
        mBtnsRotateRight.setVisibility(View.VISIBLE);
        mBtnsRotateLeft.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v == mImgView || v == mBackgroundLayout || v == mBtnsBack) {
            finish();
        } else if (v == mBtnsRotateRight){
            mImgView.rotateRight();
        } else if (v == mBtnsRotateLeft) {
            mImgView.rotateLeft();
        } else if (v == mBtnsSavePic) {
            savePic();
        } else {

        }
    }

    private String getRootPath(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + BeeFrameworkConst.PIC_DIR_PATH;
        File storePath = new File(path);
        storePath.mkdirs();
        return path;
    }

    private String getFileName(String url) {
        int i = url.lastIndexOf(URL_SEPARATOR);
        int j = -1;
        String fileName = "";
        if (i >= 0) {
            j = url.substring(0, i).lastIndexOf(URL_SEPARATOR);
        }
        if (j >= 0) {
            fileName = url.substring(j + 1, i);
        }
        return fileName;
    }

    private void savePic() {
        Bitmap image = mImgView.getBitmap();
        if (image == null) {

        } else {
            boolean saveSuccess = false;
            ByteArrayOutputStream baos = null;
            BufferedOutputStream bos = null;
            String fileFullPath = getRootPath() + File.separator + getFileName(imgUrl) + ".jpg";
            File file = new File(fileFullPath);
            try {
                if (file.exists()) {
                    file.delete();
                }
                if (file.createNewFile()) {
                    baos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    bos.write(baos.toByteArray());

                    //	toast(getString(R.string.save_pic_success));
                    saveSuccess = true;
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(file)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (baos != null) {
                        baos.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                } catch (IOException e) {
                }
                if (!saveSuccess && file != null) {
                    file.delete();
                }
            }
        }
    }
}
