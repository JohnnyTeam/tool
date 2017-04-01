package com.vortex.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vortex.common.library.R;
import com.vortex.common.listener.CnActionBarListener;
import com.vortex.common.util.DensityUtils;

/**
 * <p>Title:CnActionBar.java</p>
 * <p>Description:标题栏</p>
 * @author Johnny.xu
 * @date 2016年12月16日
 */
public class CnActionBar extends LinearLayout {

	ViewGroup mViewGroup;
	Context mContext;

	public TextView mLeftTv;
	public TextView mTitle;
	public TextView mRight;
	public TextView mRight2;
	public LinearLayout layout_left;
	public LinearLayout layout_right;
	public LinearLayout layout_right2;

	private CnActionBarListener mListener;

	public CnActionBar(Context context) {
		this(context, null);
	}

	public CnActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initControls(context);
	}

	void initControls(Context context) {
		mContext = context;
		LayoutInflater inflater = LayoutInflater.from(mContext);
		mViewGroup = (ViewGroup) inflater.inflate(R.layout.cn_view_actionbar, null);
		mViewGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		this.addView(mViewGroup);
		
		mLeftTv = (TextView) mViewGroup.findViewById(R.id.cn_tv_left);
		mTitle = (TextView) mViewGroup.findViewById(R.id.cn_tv_title);
		mRight = (TextView) mViewGroup.findViewById(R.id.cn_tv_right);
		mRight2 = (TextView) mViewGroup.findViewById(R.id.cn_tv_right2);
		layout_left = (LinearLayout) mViewGroup.findViewById(R.id.cn_layout_left);
		layout_right = (LinearLayout) mViewGroup.findViewById(R.id.cn_layout_right);
		layout_right2 = (LinearLayout) mViewGroup.findViewById(R.id.cn_layout_right2);
		
		layout_left.setOnClickListener(mOnClickListener);
		layout_right.setOnClickListener(mOnClickListener);
		layout_right2.setOnClickListener(mOnClickListener);
	}

	public View.OnClickListener mOnClickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			if (mListener == null)
				return;
			if (v == layout_left) {
				mListener.clickLeft(v);
			} else if (v == layout_right) {
				mListener.clickRight(v);
			} else if (v == layout_right2) {
				mListener.clickRight2(v);
			}
		}
	};

	public void setTitle(String text) {
		mTitle.setText("" + text);
	}

	public void setTitleColor(int resource) {
		mTitle.setTextColor(resource);
	}

	public void setLeftBtnDrawable(int resId) {
		mLeftTv.setBackgroundResource(resId);
	}

	public void setRightBtnDrawable(int resId) {
		ViewGroup.LayoutParams params = mRight.getLayoutParams();
		params.width = DensityUtils.dp2px(mContext, 24);
		params.height = DensityUtils.dp2px(mContext, 24);
		mRight.setLayoutParams(params);
		mRight.setBackgroundResource(resId);
	}

	public void setRightBtn2Drawable(int resid) {
		mRight2.setBackgroundResource(resid);
	}

	public void setLeftText(String text) {
		mLeftTv.setText("" + text);
	}

	public void setRightText(String text) {
		mRight.setText("" + text);
	}

	public void setRight2Text(String text) {
		mRight2.setText("" + text);
	}

	public void setLeftBtnVisibility(boolean flag) {
		layout_left.setVisibility(flag ? View.VISIBLE : View.GONE);
	}

	public void setRightBtnVisibility(boolean flag) {
		layout_right.setVisibility(flag ? View.VISIBLE : View.GONE);
	}

	public void setRightBtn2Visibility(boolean flag) {
		layout_right2.setVisibility(flag ? View.VISIBLE : View.GONE);
	}

	public void setListener(CnActionBarListener listener) {
		mListener = listener;
	}

}
