package com.vortex.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.vortex.common.library.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title:PunchCardView.java</p>
 * <p>Description:打卡控件</p>
 * 
 * @author Johnny.xu
 * @date 2017年1月11日
 */
public class CnPunchCardView extends View {

	public static final int SHOW_MODEL_UNABLE = 0;
	public static final int SHOW_MODEL_COMMON = 1;
	public static final int SHOW_MODEL_REFRESHING = 2;

	private Context mContext;

	private int mShowModel = SHOW_MODEL_COMMON;

	private int width, height;

	private float timeSize, timeMargin, textSize, textMargin;

	private long mNowTime;

	private float mShadeWidth; // 控件阴影部分

	// 阴影颜色
	private int mShadeColor;
	// 正常颜色
	private int mCommonColor;
	// 不能点击颜色
	private int mUnableColor;
	// 文字颜色
	private int textColor, timeColor;

	private int mIntervalTime; // 停顿时间

	private boolean isRefreshTime; // 是否在刷新时间中
	private boolean isRefreshing; // 是否在刷新中

	private int mAngle;

	public CnPunchCardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;

		initParams(attrs);
		initPaint();
	}

	private void initParams(AttributeSet attrs) {
		TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CnPunchCardView);
		mShadeWidth = a.getDimension(R.styleable.CnPunchCardView_shadeWidth, 8);
		timeSize = a.getDimension(R.styleable.CnPunchCardView_timeSize, 28);
		timeMargin = a.getDimension(R.styleable.CnPunchCardView_timeMargin, 20);

		textSize = a.getDimension(R.styleable.CnPunchCardView_textSize, 32);
		textMargin = a.getDimension(R.styleable.CnPunchCardView_textMargin, 20);

		mShadeColor = mContext.getResources()
				.getColor(a.getResourceId(R.styleable.CnPunchCardView_shadeColor, R.color.black));
		mCommonColor = mContext.getResources()
				.getColor(a.getResourceId(R.styleable.CnPunchCardView_bgColorCommon, R.color.white));
		mUnableColor = mContext.getResources()
				.getColor(a.getResourceId(R.styleable.CnPunchCardView_bgColorUnable, R.color.black));
		textColor = mContext.getResources()
				.getColor(a.getResourceId(R.styleable.CnPunchCardView_textColor, R.color.black));
		timeColor = mContext.getResources()
				.getColor(a.getResourceId(R.styleable.CnPunchCardView_timeColor, R.color.black));
		mIntervalTime = a.getInt(R.styleable.CnPunchCardView_intervalTime, 1000);
		a.recycle();
	}

	private Paint mShadePaint, mContentPaint, mUnablePaint, mTextPaint, mTimePaint, mRefreshPaint;
	private float mTimeHeight;

	private SimpleDateFormat sdf_hms;

	/**
	 * 初始化画笔
	 */
	private void initPaint() {
		// 初始化阴影区域
		mShadePaint = new Paint();
		mShadePaint.setStrokeWidth(mShadeWidth);
		mShadePaint.setColor(mShadeColor);
		mShadePaint.setAntiAlias(true);
		mShadePaint.setStyle(Paint.Style.STROKE);

		// 初始化中间填充区域
		mContentPaint = new Paint();
		mContentPaint.setColor(mCommonColor);
		mContentPaint.setAntiAlias(true);
		mContentPaint.setStyle(Paint.Style.FILL);

		mUnablePaint = new Paint();
		mUnablePaint.setColor(mUnableColor);
		mUnablePaint.setAntiAlias(true);
		mUnablePaint.setStyle(Paint.Style.FILL);

		mTextPaint = new Paint();
		mTextPaint.setColor(textColor);
		mTextPaint.setTextSize(textSize);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setStyle(Paint.Style.FILL);

		mTimePaint = new Paint();
		mTimePaint.setColor(timeColor);
		mTimePaint.setTextSize(timeSize);
		mTimePaint.setAntiAlias(true);
		mTimePaint.setStyle(Paint.Style.FILL);

		sdf_hms = new SimpleDateFormat("HH:mm:ss");

		mNowTime = new Date().getTime();

		setTime(mNowTime);

		mTimeHeight = mTextPaint.getFontMetrics().descent - mTextPaint.getFontMetrics().ascent / 2;

		mRefreshPaint = new Paint();
		mRefreshPaint.setAntiAlias(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		// int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int length = Math.min(widthSize, heightSize);
		width = length;
		height = length;

		setMeasuredDimension(length, length);
	}
	
	private SweepGradient mSweepGradient;
	private Matrix mMatrix;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(width / 2, height / 2, height / 2 - mShadeWidth / 2, mShadePaint);

		if (mShowModel == SHOW_MODEL_COMMON) {
			canvas.drawCircle(width / 2, height / 2, (height + 1) / 2 - mShadeWidth, mContentPaint);
		} else if (mShowModel == SHOW_MODEL_UNABLE) {
			canvas.drawCircle(width / 2, height / 2, (height + 1) / 2 - mShadeWidth, mUnablePaint);
		} else {
			RectF rect = new RectF(0 + mShadeWidth, 0 + mShadeWidth, width - mShadeWidth, height - mShadeWidth);
			if (mSweepGradient == null) {
				mSweepGradient = new SweepGradient(width/2, height/2, new int[] {mCommonColor,
						mCommonColor, mCommonColor, mUnableColor}, null);
				
				mMatrix = new Matrix();
				mMatrix.setRotate(mAngle, width/2, height/2);
				
				mRefreshPaint.setShader(mSweepGradient);
			}
			mMatrix.setRotate(mAngle, width/2, height/2);
			mSweepGradient.setLocalMatrix(mMatrix);
			canvas.drawArc(rect, mAngle, 360, true, mRefreshPaint);
		}

		// 时间绘画
		String timeStr = sdf_hms.format(new Date(mNowTime));
		float timeLength = mTimePaint.measureText(String.valueOf(timeStr));
		canvas.drawText(timeStr, (width - timeLength) / 2, (height + mTimeHeight) / 2 + timeMargin, mTimePaint);
		
		// 提示绘画
		if (mPromptStr != null) {
			float textLength = mTextPaint.measureText(String.valueOf(mPromptStr));
			canvas.drawText(mPromptStr, (width - textLength) / 2, (height + mTimeHeight) / 2 + textMargin, mTextPaint);
		}
		
	}

	public synchronized void setTime(long time) {
		setNowTime(time, false);
		if (!isRefreshTime) {
			isRefreshTime = true;
			postDelayed(new Runnable() {

				public void run() {
					setNowTime(mIntervalTime, true);
					postInvalidate();
					postDelayed(this, mIntervalTime);
				}
			}, mIntervalTime);
		}
	}

	private synchronized void setNowTime(long time, boolean isAdd) {
		if (isAdd) {
			this.mNowTime += time;
		} else {
			this.mNowTime = time;
		}
	}

	public void setModel(int modelStyle) {
		if (-1 < modelStyle && modelStyle < 3) {

			this.mShowModel = modelStyle;

			if (modelStyle == SHOW_MODEL_REFRESHING && !isRefreshing) {
				isRefreshing = true;
				mAngle = 0;
				postDelayed(new Runnable() {
					public void run() {
						mAngle += 3;
						if (mAngle >= 360) mAngle -= 360;
						postInvalidate();
						if (isRefreshing) {
							postDelayed(this, 20);
						}
					}
				}, 20);
			} else if (modelStyle != SHOW_MODEL_REFRESHING) {
				isRefreshing = false;
			}

			invalidate();
		}
	}
	
	@Override
	public boolean performClick() {
		return super.performClick();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			if (mShowModel == SHOW_MODEL_COMMON) {
				return super.onTouchEvent(event);
			} else {
				return true;
			}
		}
		return super.onTouchEvent(event);
	}
	
	private String mPromptStr;

	public void setText(String string) {
		mPromptStr = string;
	}
}
