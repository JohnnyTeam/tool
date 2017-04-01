package com.vortex.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.vortex.common.library.R;

/**
 * 刷新效果
 *
 * @author Johnny.xu
 *         date 2017/2/11
 */
public class CnRefreshView extends View {

    private SweepGradient mSweepGradient;

    private Matrix mMatrix;

    private Paint mRefreshPaint;

    private int mAngle;

    private int mDefaultColor, mChangedColor;

    public CnRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        initView();
        initParams(attrs);
    }

    private void initParams(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CnRefreshView);
        mDefaultColor = getResources().getColor(a.getResourceId(R.styleable.CnRefreshView_mainColor, R.color.theme_color));
        mChangedColor = getResources().getColor(a.getResourceId(R.styleable.CnRefreshView_changingColor, R.color.theme_near_color));
        a.recycle();
    }

    private void initView() {
        mRefreshPaint = new Paint();
        mRefreshPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        if (mSweepGradient == null) {
            mSweepGradient = new SweepGradient(getWidth()/2, getHeight()/2, new int[] {mDefaultColor,
                    mDefaultColor, mDefaultColor, mChangedColor}, null);

            mMatrix = new Matrix();
            mMatrix.setRotate(mAngle, getWidth()/2, getHeight()/2);

            mRefreshPaint.setShader(mSweepGradient);
        }
        mMatrix.setRotate(mAngle, getWidth()/2, getHeight()/2);
        mSweepGradient.setLocalMatrix(mMatrix);
        canvas.drawArc(rect, mAngle, 360, true, mRefreshPaint);
    }

    private boolean isShowing;

    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        changeAction(visibility == View.VISIBLE);
    }

    public synchronized void changeAction(boolean show) {

        if (show != isShowing) {
            isShowing = show;
            if (show) {
                mAngle = 0;
                postDelayed(new Runnable() {
                    public void run() {
                        mAngle += 4;
                        if (mAngle >= 360) mAngle -= 360;
                        postInvalidate();
                        if (isShowing) {
                            postDelayed(this, 10);
                        }
                    }
                }, 10);
            }
        }
    }
}
