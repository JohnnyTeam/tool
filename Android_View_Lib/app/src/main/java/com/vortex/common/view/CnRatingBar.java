package com.vortex.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vortex.common.library.R;

/**
 * 评分控件自定义
 *
 * @author Johnny.xu
 *         date 2017/2/17
 */
public class CnRatingBar extends LinearLayout {

    private boolean mClickable;
    private boolean isHalfStart;
    private int starCount;
    private float starNum;
    private float starImageWidth;
    private float starImageHeight;
    private float starImagePadding;
    private Drawable starEmptyDrawable;
    private Drawable starFillDrawable;
    private Drawable starHalfDrawable;

    private OnRatingChangeListener mOnRatingChangeListener;

    public void setOnRatingChangeListener(OnRatingChangeListener listener) {
        mOnRatingChangeListener = listener;
    }

    public float getStarNum() {
        return starNum;
    }

    public void setStarNum(float starNum) {
        this.starNum = starNum;
        initView(true);
    }

    public CnRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CnRatingBar);

        starHalfDrawable = mTypedArray.getDrawable(R.styleable.CnRatingBar_starHalf);
        starEmptyDrawable = mTypedArray.getDrawable(R.styleable.CnRatingBar_starEmpty);
        starFillDrawable = mTypedArray.getDrawable(R.styleable.CnRatingBar_starFill);
        mClickable = mTypedArray.getBoolean(R.styleable.CnRatingBar_clickable, true);
        isHalfStart = mTypedArray.getBoolean(R.styleable.CnRatingBar_halfStart, false);
        starImageWidth = mTypedArray.getDimension(R.styleable.CnRatingBar_starImageWidth, 48);
        starImageHeight = mTypedArray.getDimension(R.styleable.CnRatingBar_starImageHeight, 48);
        starImagePadding = mTypedArray.getDimension(R.styleable.CnRatingBar_starImagePadding, 32);

        starCount = mTypedArray.getInteger(R.styleable.CnRatingBar_starCount, 5);
        starCount = starCount < 0 ? 0 : starCount;

        starNum = mTypedArray.getFloat(R.styleable.CnRatingBar_starNum, 0);
        if (!isHalfStart) {
            starNum = (int)starNum;
        } else if (starNum % 0.5f != 0)
            starNum = 0.5f * (int)(starNum/0.5);

        if (starCount != 0) initView(false);
    }

    private void initView(boolean isRefresh) {
        for (int i = 0; i < starCount; i++) {
            if (isRefresh) {
               setImageStatus(i, (ImageView) getChildAt(i));
                if(mOnRatingChangeListener != null)
                    mOnRatingChangeListener.onRatingChange(starNum);
            } else {
                ImageView imageView = getStarImageView(getContext(), i);
                addView(imageView);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float mDownX = event.getX();
            if (mClickable) {
                float index = mDownX / (starImageWidth + starImagePadding);
                boolean isFull = mDownX % (starImageWidth + starImagePadding) == 0;
                if (isFull) {
                    this.starNum = (int) index;
                } else {
                    if (isHalfStart) {
                        int count = (int)index;
                        float nextIndex = mDownX % (starImageWidth + starImagePadding);
                        if (nextIndex > starImageWidth) {
                            this.starNum = (int) index + 1;
                        } else {
                            boolean isHalf = nextIndex >= 0.5f * starImageWidth;
                            if (isHalf) {
                                this.starNum = 1 + count;
                            } else {
                                this.starNum = 0.5f + count;
                            }
                        }
                    } else {
                        this.starNum = (int) index + 1;
                    }
                }
            }
            initView(true);
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取图片数据
     */
    private ImageView getStarImageView(Context context, int position) {
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(
                Math.round(starImageWidth),
                Math.round(starImageHeight)
        );
        if (position < starCount - 1)
            para.setMargins(0, 0, Math.round(starImagePadding), 0);
        imageView.setLayoutParams(para);
        setImageStatus(position, imageView);
        return imageView;
    }

    private void setImageStatus(int position, ImageView imageView) {
        if (isEmptyStar(position)) imageView.setImageDrawable(starEmptyDrawable);
        if (isHalfStart(position)) imageView.setImageDrawable(starHalfDrawable);
        if (isFullStar(position)) imageView.setImageDrawable(starFillDrawable);
    }

    private boolean isEmptyStar(int position) {
        return position >= starNum;
    }

    private boolean isHalfStart(int position) {
        return isHalfStart && position < starNum &&  position + 1 > starNum;
    }

    private boolean isFullStar(int position) {
        return !isEmptyStar(position) && !isHalfStart(position);
    }



    public interface OnRatingChangeListener {

        void onRatingChange(float RatingCount);
    }

}
