package com.vortex.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.vortex.common.library.R;
import com.vortex.common.util.StringUtils;

/**
 * 进度条
 *
 * @author Johnny.xu
 *         date 2017/2/15
 */
public class ProgressBarView extends View {

    private int mProgress;

    private int mWidth, mHeight;

    private float mTimeHeight;

    private Paint mBackgroundPaint, mProgressBarPaint, mTextPaint;

    private String mPrompt;

    private boolean isShowPrompt = true;

    private int mViewBackground, mProgressBackground;

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attrArray = getResources().obtainAttributes(attrs, R.styleable.ProgressBarView);
        mViewBackground = attrArray.getColor(R.styleable.ProgressBarView_pbv_bg_view, getResColorById(R.color.cover_color));
        mProgressBackground = attrArray.getColor(R.styleable.ProgressBarView_pbv_bg_progress, getResColorById(R.color.blue));
        isShowPrompt = attrArray.getBoolean(R.styleable.ProgressBarView_pbv_prompt_show, true);
        attrArray.recycle();
        initView();
    }

    private void initView() {

        mProgressBarPaint = new Paint();
        mProgressBarPaint.setAntiAlias(true);
        mProgressBarPaint.setColor(mProgressBackground);
        mProgressBarPaint.setStyle(Paint.Style.FILL);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(mViewBackground);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(getResources().getColor(R.color.white));
        mTextPaint.setTextSize(28);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);

        mTimeHeight = mTextPaint.getFontMetrics().descent - mTextPaint.getFontMetrics().ascent / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF oval3 = new RectF(0, 0, mWidth * mProgress / 100, mHeight);
        RectF oval4 = new RectF(0, 0, mWidth, mHeight);

        canvas.drawRoundRect(oval4, mHeight/2, mHeight/2, mBackgroundPaint);
        canvas.drawRoundRect(oval3, mHeight/2, mHeight/2, mProgressBarPaint);

        if (!StringUtils.isEmpty(mPrompt)) {
            float textLength = mTextPaint.measureText(String.valueOf(mPrompt));
            mTextPaint.setTextSize(mHeight / 3 * 2);
            canvas.drawText(mPrompt, (mWidth - textLength) / 2, (mHeight + mTimeHeight) / 2, mTextPaint);
        } else if (isShowPrompt) {
            String prompt = mProgress + "%";
            float textLength = mTextPaint.measureText(String.valueOf(prompt));
            mTextPaint.setTextSize(mHeight / 3 * 2);
            canvas.drawText(prompt, (mWidth - textLength) / 2, (mHeight + mTimeHeight) / 2, mTextPaint);
        }
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }
        this.mProgress = progress;
        postInvalidate();
    }

    public void setShowPromptTag(boolean isShow) {
        this.isShowPrompt = isShow;
        postInvalidate();
    }

    public void setText(String prompt) {
        this.mPrompt = prompt;
        postInvalidate();
    }

    public int getResColorById(int id) {
        return getResources().getColor(id);
    }
}
