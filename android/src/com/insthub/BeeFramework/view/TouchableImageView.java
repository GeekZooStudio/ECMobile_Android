package com.insthub.BeeFramework.view;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.insthub.BeeFramework.BeeFrameworkConst;

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


public class TouchableImageView extends WebImageView implements View.OnTouchListener{
    private static boolean DEBUG = BeeFrameworkConst.DEBUG;
    private static String TAG = "image";

    private static final int TOUCH_MODE_NONE = 0;
    private static final int TOUCH_MODE_DRAG = 1;
    private static final int TOUCH_MODE_ZOOM = 2;

    private static final int PIC_OUTOF_BOUND_X_Y = 0;
    private static final int PIC_OUTOF_BOUND_Y = 1;
    private static final int PIC_OUTOF_BOUND_X = 2;
    private static final int PIC_IN_BOUND_X_Y = 3;

    private static final int MSG_ON_CLICK = 1;
    private static final int MSG_ON_CLICK_DELAY = 250;

    private Context mContext;
    private Bitmap mBitmap = null;
    private int mTouchMode = TOUCH_MODE_NONE;
    private boolean mMoved = false;
    private OnClickListener mOnClickListener = null;

    private int mWLimit = 0;
    private int mHLimit = 0;

    private int mPaddingX = 0;
    private int mPaddingY = 0;

    private Matrix currentMatrix  = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private PointF startPoint = new PointF();
    private PointF midPoint = new PointF();
    private float oldDist = 0;
    private RectF mWindowRect = null;
    private RectF mImageRect = new RectF();;

    private float minScale = 0.5f;
    private float maxScale = 5;
    private int bmpWidth;
    private int bmpHeight;

    private boolean mRevertXY = false;
    private int mRotatedDegree = 0;

    private Handler mHandle = null;

    public TouchableImageView(Context context) {
        super(context);
        setScaleType(ScaleType.MATRIX);
        init(context);
    }

    public TouchableImageView(Context context, AttributeSet as) {
        super(context, as);
        setScaleType(ScaleType.CENTER);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_ON_CLICK && mOnClickListener != null) {
                    mOnClickListener.onClick(TouchableImageView.this);
                }
            }
        };
        setOnTouchListener(this);
    }


    public void setMinScale(float minScale) {
        this.minScale = minScale;
    }

    private RectF getWindowRect() {
        if (mWindowRect == null) {
            mWindowRect = new RectF(mPaddingX, mPaddingY, mWLimit - mPaddingX, mHLimit - mPaddingY);
        }
        return mWindowRect;
    }

    private void resetImageRect() {
        mImageRect.left = 0;
        mImageRect.top = 0;
        mImageRect.right = bmpWidth;
        mImageRect.bottom = bmpHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        getWindowRect();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mHLimit == 0 || mWLimit == 0) {
            mHLimit = bottom - top;
            mWLimit = right - left;
            setVisableRectLimit(mPaddingX, mPaddingY);
            resetImageBitmap(mBitmap);
        } else {
            mWindowRect = null;
            mHLimit = bottom - top;
            mWLimit = right - left;
            fixPositionCenter(false);
            fixPositionLimit();
            setImageMatrix(currentMatrix);
        }
    }

    public void rotateRight() {
        if (mBitmap == null) {
            return;
        }
        mRevertXY = !mRevertXY;
        mRotatedDegree = (mRotatedDegree + 90) % 360;
        RectF windowRect = getWindowRect();
        currentMatrix.postRotate(90, windowRect.centerX(), windowRect.centerY());
        fixPositionLimit();
        fixPositionCenter(false);
        setImageMatrix(currentMatrix);
    }

    public void rotateLeft() {
        if (mBitmap == null) {
            return;
        }
        mRevertXY = !mRevertXY;
        mRotatedDegree = (mRotatedDegree + 270) % 360;
        RectF windowRect = getWindowRect();
        currentMatrix.postRotate(270, windowRect.centerX(), windowRect.centerY());
        fixPositionLimit();
        fixPositionCenter(false);
        setImageMatrix(currentMatrix);
    }

    private void autoFixImagePosition() {
        if (bmpHeight > mHLimit) {
            float heightDivWidth = bmpHeight / bmpWidth;
            if (heightDivWidth >= 3) {
                fixImageAlignTop();
            } else {
                float scale = (float) mHLimit / bmpHeight;
                scale = Math.max(scale, minScale);
                currentMatrix.postScale(scale, scale);
                fixPositionCenter(true);
            }
        }
    }

    private void setImageBitmap() {
        mWindowRect = null;
        super.setImageBitmap(mBitmap);
        bmpWidth = mBitmap.getWidth();
        bmpHeight = mBitmap.getHeight();
        currentMatrix.reset();
        resetImageRect();
        setScaleType(ScaleType.MATRIX);
        fixPositionLimit();
        fixPositionCenter(true);
        autoFixImagePosition();
        setImageMatrix(currentMatrix);
        savedMatrix.set(currentMatrix);
    }

    public void resetImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBitmap = bitmap;
            setImageBitmap();
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        if (mBitmap == null && bitmap != null) {
            mBitmap = bitmap;
            setImageBitmap();
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mOnClickListener = l;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mBitmap == null) {
            return true;
        }
        RectF curImageRect = new RectF();
        currentMatrix.mapRect(curImageRect, mImageRect);
        if (DEBUG) {
            Log.i(TAG, "-------------------------------------------");
        }
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (DEBUG) {
                    Log.i(TAG, "-------ACTION_DOWN-------");
                }
                mMoved = false;
                savedMatrix.set(currentMatrix);
                startPoint.set(event.getX(), event.getY());
                mTouchMode = TOUCH_MODE_DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (DEBUG) {
                    Log.i(TAG, "-------ACTION_POINTER_DOWN-------");
                }
                mMoved = true;
                oldDist = spacing(event);
                if (oldDist > 6f) {
                    savedMatrix.set(currentMatrix);
                    midPoint = setMidPoint(midPoint, event);
                    mTouchMode = TOUCH_MODE_ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (DEBUG && action == MotionEvent.ACTION_UP) {
                    Log.i(TAG, "-------ACTION_UP-------");
                } else if (DEBUG && action == MotionEvent.ACTION_POINTER_UP) {
                    Log.i(TAG, "-------ACTION_POINTER_DOWN-------");
                }
                mTouchMode = TOUCH_MODE_NONE;
                savedMatrix.set(currentMatrix);
                if (!mMoved) {
                    if (mHandle.hasMessages(MSG_ON_CLICK)) {
                        // onDoubleClick
                        mHandle.removeMessages(MSG_ON_CLICK);
                        currentMatrix.reset();
                        resetImageBitmap(mBitmap);
                    } else {
                        mHandle.sendEmptyMessageDelayed(MSG_ON_CLICK, MSG_ON_CLICK_DELAY);
                    }
                }
                return true;
//			break;
            case MotionEvent.ACTION_MOVE:
                if (DEBUG) {
                    Log.i(TAG, "-------ACTION_MOVE-------");
                }
                if (!mMoved &&
                        Math.abs(startPoint.x - event.getX()) >= 2 &&
                        Math.abs(startPoint.y - event.getY()) >= 2) {
                    mMoved = true;
                }

                currentMatrix.set(savedMatrix);
                if (mTouchMode == TOUCH_MODE_DRAG) {
                    if (DEBUG) {
                        Log.i(TAG, "-------ACTION_MOVE:  DRAG-------");
                    }

                    float[] values = new float[9];
                    currentMatrix.getValues(values);
                    float scale = Math.max(getScaleX(values), 1f);
                    switch (getBitmapState(0)) {
                        case PIC_OUTOF_BOUND_Y:
                            currentMatrix.postTranslate(0, (event.getY() - startPoint.y) * scale);
                            break;
                        case PIC_OUTOF_BOUND_X:
                            currentMatrix.postTranslate((event.getX() - startPoint.x) * scale, 0);
                            break;
                        case PIC_OUTOF_BOUND_X_Y:
                            currentMatrix.postTranslate((event.getX() - startPoint.x) * scale, (event.getY() - startPoint.y) * scale);
                            break;
//				case PIC_IN_BOUND_X_Y:
//					if (mEnableOutofBoundXY) {
//						currentMatrix.postTranslate((event.getX() - startPoint.x) * scale, (event.getY() - startPoint.y) * scale);
//					}
//					break;
                    }
                } else if (mTouchMode == TOUCH_MODE_ZOOM) {
                    if (DEBUG) {
                        Log.i(TAG, "-------ACTION_MOVE:  ZOOM-------");
                    }
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        float scale = newDist / oldDist;
                        currentMatrix.postScale(scale, scale, midPoint.x, midPoint.y);

                        float[] values = new float[9];
                        currentMatrix.getValues(values);
                        float s = getScaleX(values);
                        if (s < minScale) {
                            currentMatrix.postScale(minScale/s, minScale/s, midPoint.x, midPoint.y);
                        } else if (s > maxScale) {
                            currentMatrix.postScale(maxScale/s, maxScale/s, midPoint.x, midPoint.y);
                        }
                        if (DEBUG) {
                            Log.i(TAG, "-------1-------" + currentMatrix.toString());
                        }
                        fixPositionCenter(false);
                        if (DEBUG) {
                            Log.i(TAG, "-------2-------" + currentMatrix.toString());
                        }
                    }
                }
                if (DEBUG) {
                    Log.i(TAG, "-------3-------" + currentMatrix.toString());
                }
                fixPositionLimit();
                if (DEBUG) {
                    Log.i(TAG, "-------4-------" + currentMatrix.toString());
                }
                break;
        }

        if (mBitmap == null) {
            currentMatrix.reset();
        } else if (!getImageMatrix().equals(currentMatrix)) {
            if (DEBUG) {
                Log.i(TAG, "-------5------- from " + getImageMatrix().toString() + " to " + currentMatrix.toShortString());
            }

            Log.i("zoom", "-------6-------" + currentMatrix.toString());
            setImageMatrix(currentMatrix);
            mMoved = true;
        }
        if (DEBUG) {
            Log.i(TAG, "-------------------------------------------");
        }
        return true;
    }

    private int getBitmapState(float scale) {
        float w, h;
        int result = PIC_OUTOF_BOUND_X_Y;
//		if (mEnableOutofBoundXY) {
//			return PIC_IN_BOUND_X_Y;
//		}

        if (scale == 0) {
            float[] values = new float[9];
            currentMatrix.getValues(values);
            w = bmpWidth * getScaleX(values);
            h = bmpHeight * getScaleY(values);
        } else {
            w = bmpWidth * scale;
            h = bmpHeight * scale;
        }
        if (mRevertXY) {
            float t = w;
            w = h;
            h = t;
        }

        RectF wr = getWindowRect();
        if (w <= wr.width()) {
            result = (h <= wr.height()) ? PIC_IN_BOUND_X_Y : PIC_OUTOF_BOUND_Y;
        } else if (h <= wr.height()) {
            result = PIC_OUTOF_BOUND_X;
        }

        return result;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }


    private PointF setMidPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
        return point;
    }

    private void fixPositionLimit() {
        float w, h;
        float[] values = new float[9];
        currentMatrix.getValues(values);

        w = bmpWidth * getScaleX(values);
        h = bmpHeight * getScaleY(values);
        if (mRevertXY) {
            float t = w;
            w = h;
            h = t;
        }

        RectF curImageRect = new RectF();
        RectF windowRect = getWindowRect();
        currentMatrix.mapRect(curImageRect, mImageRect);

        float deltaX = 0;
        float deltaY = 0;
        if (curImageRect.width() > windowRect.width()) {
            if (curImageRect.left > windowRect.left) {
                deltaX = windowRect.left - curImageRect.left;
            } else if (curImageRect.right < windowRect.right) {
                deltaX = windowRect.right - curImageRect.right;
            }
        }
        if (curImageRect.height() > windowRect.height()) {
            if (curImageRect.top > windowRect.top) {
                deltaY = windowRect.top - curImageRect.top;
            } else if (curImageRect.bottom < windowRect.bottom) {
                deltaY = windowRect.bottom - curImageRect.bottom;
            }
        }
        if (deltaX != 0 || deltaY != 0) {
            currentMatrix.postTranslate(deltaX, deltaY);
        }
    }

    private void fixPositionCenter(boolean force) {
        float w, h;
        float[] values = new float[9];
        currentMatrix.getValues(values);
        w = bmpWidth * getScaleX(values);
        h = bmpHeight * getScaleY(values);
        if (mRevertXY) {
            float t = w;
            w = h;
            h = t;
        }
        if (force || w <= mWLimit - mPaddingX - mPaddingX) {
            float tX = mWLimit / 2 - w / 2;
            float post = tX - values[Matrix.MTRANS_X] + getRotatedDeltaX(w, values);
            currentMatrix.postTranslate(post, 0);
            midPoint.x = mWLimit / 2;
        }
        if (force || h <= mHLimit - mPaddingY - mPaddingY) {
            float tY = mHLimit / 2 - h / 2;
            float post = tY - values[Matrix.MTRANS_Y] + getRotatedDeltaY(h, values);
            currentMatrix.postTranslate(0, post);
            midPoint.y = mHLimit / 2;
        }
    }

    private void fixImageAlignTop() {
        float[] values = new float[9];
        currentMatrix.getValues(values);
        float deltaY = values[Matrix.MTRANS_Y];
        if (deltaY < 0) {
            currentMatrix.postTranslate(0, -deltaY);
        }
    }

    private float calculateFixedScale(int widthLimit, int heightLimit) {
        float scale;
        boolean divWithScreen = ((float) bmpWidth / bmpHeight) > (float) widthLimit / heightLimit;
        if (divWithScreen) {
            scale = (float) widthLimit / bmpWidth;
        } else {
            scale = (float) heightLimit / bmpHeight;
        }
        scale = Math.max(scale, minScale);
        scale = Math.min(scale, maxScale);
        return scale;
    }

    public void setImageFullScreenAndFitCenter() {
        float scale = calculateFixedScale(mWLimit, mHLimit);
        float[] values = new float[9];
        currentMatrix.getValues(values);
        scale /= getScaleX(values);

        currentMatrix.postScale(scale, scale);
        fixPositionCenter(true);
        setImageMatrix(currentMatrix);
    }

    public void setImageFullWindow() {
        getWindowRect();
        float scale = calculateFixedScale((int)mWindowRect.width(), (int)mWindowRect.height());
        float[] values = new float[9];
        currentMatrix.getValues(values);
        scale /= getScaleX(values);

        currentMatrix.postScale(scale, scale);
        fixPositionCenter(true);
        setImageMatrix(currentMatrix);
    }

    private float getRotatedDeltaX(float width, float[] values) {
        float x = Math.min(values[Matrix.MSCALE_X], values[Matrix.MSKEW_X]);
        return x < 0 ? width : 0;
    }


    private float getRotatedDeltaY(float height, float[] values) {
        float y = Math.min(values[Matrix.MSCALE_Y], values[Matrix.MSKEW_Y]);
        return y < 0 ? height : 0;
    }


    private float getScaleX(float[] values) {
        return Math.abs(mRevertXY ? values[Matrix.MSKEW_X] : values[Matrix.MSCALE_X]);
    }


    private float getScaleY(float[] values) {
        return Math.abs(mRevertXY ? values[Matrix.MSKEW_Y] : values[Matrix.MSCALE_Y]);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }


    public void setVisableRectLimit(int paddingX, int paddingY) {
        mPaddingX = paddingX;
        mPaddingY = paddingY;
        if (mPaddingX != 0 || mPaddingY != 0) {
            resetImageRect();
            mWindowRect = new RectF(mPaddingX, mPaddingY, mWLimit - mPaddingX, mHLimit - mPaddingY);
        } else {
            mWindowRect = null;
        }
    }


    public Bitmap getBitmapInWindow() {
        final int X = 0;
        final int Y = 1;
        Bitmap output = null;
        getWindowRect();
        if (mBitmap != null && mWindowRect != null) {
            float [] imageLeftTop = new float [] { mImageRect.left, mImageRect.top };
            float [] windowLeftTop = new float [2];

            if (mRotatedDegree % 360 == 0) {
                windowLeftTop[X] = mWindowRect.left;
                windowLeftTop[Y] = mWindowRect.top;
            } else if (mRotatedDegree % 360 == 90) {
                windowLeftTop[X] = mWindowRect.right;
                windowLeftTop[Y] = mWindowRect.top;
            } else if (mRotatedDegree % 360 == 180) {
                windowLeftTop[X] = mWindowRect.right;
                windowLeftTop[Y] = mWindowRect.bottom;
            } else if (mRotatedDegree % 360 == 270) {
                windowLeftTop[X] = mWindowRect.left;
                windowLeftTop[Y] = mWindowRect.bottom;
            }
            currentMatrix.mapPoints(imageLeftTop);

            float[] values = new float[9];
            currentMatrix.getValues(values);
            float scaleX = mRevertXY ? values[Matrix.MSKEW_X] : values[Matrix.MSCALE_X];
            float scaleY = mRevertXY ? values[Matrix.MSKEW_Y] : values[Matrix.MSCALE_Y];
            float x = (windowLeftTop[X] - imageLeftTop[X]) / scaleX;
            float y = (windowLeftTop[Y] - imageLeftTop[Y]) / scaleY;

            if (mRevertXY) {
                float t = x;
                x = y;
                y = t;
            }

            scaleX = Math.abs(scaleX);
            scaleY = Math.abs(scaleY);
            int outputWidth = (int)(mWindowRect.width() / scaleX);
            int outputHeight = (int)(mWindowRect.height() / scaleY);
            output = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawBitmap(mBitmap, new Rect((int)x, (int)y, (int)(x + outputWidth), (int)(y + outputHeight)),
                    new RectF(0, 0, output.getWidth(), output.getHeight()), new Paint());

            if (mRotatedDegree % 360 != 0) {
                Bitmap outputRotate = output;
                output = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(output);

                Matrix rotate = new Matrix();
                rotate.postRotate(mRotatedDegree, outputWidth / 2, outputHeight / 2);
                canvas.drawBitmap(outputRotate, rotate, new Paint());
            }
        }
        return output;
    }

    public void recycle() {
        mBitmap = null;
    }


}
