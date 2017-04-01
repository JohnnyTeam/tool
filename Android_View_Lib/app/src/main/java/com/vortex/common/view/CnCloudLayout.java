package com.vortex.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vortex.common.library.R;

import java.util.Hashtable;

/**
 * <p>Title:CnCloudLayout.java</p>
 * <p>Description:云局文件</p>
 * @author Johnny.xu
 * @date 2017年1月6日
 */
public class CnCloudLayout extends ViewGroup {
	
    int mLeft, mRight, mTop, mBottom;
    
    Hashtable<View, Position> map = new Hashtable<View, Position>();
    
    private Padding mPadding;
    
    private float mWidgetPadding; // 子控件间距
    
    private int minRowNum; // 最少每行显示数目
    private int showRowNum; // 最少每行显示数目
    
    private int mGravity; // 子控件对齐方式
    
    private boolean isFixedRowNum; // 是否固定每行的数量
    private boolean isFixedLayout; // 子控件宽高固定 默认为true
    
    private int mWidth; // 布局空间总长度
    
    private Context mContext;
    
    public CnCloudLayout(Context context) {
        this(context, null);
    }

    public CnCloudLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public CnCloudLayout(Context context, AttributeSet attrs, int a) {
        super(context, attrs, a);
        this.mContext = context;
        
        initParam(attrs);
    }
    
    private void initParam(AttributeSet attrs) {
    	TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CnCloudLayout);
    	mWidgetPadding = a.getDimension(R.styleable.CnCloudLayout_padding, 0);
    	// 默认每行显示3个
    	minRowNum = a.getInteger(R.styleable.CnCloudLayout_minRowNum, 3);
    	// 默认向左对齐
    	mGravity = a.getInteger(R.styleable.CnCloudLayout_gravity, 0);
    	// 默认不固定每行的数量
    	isFixedRowNum = a.getBoolean(R.styleable.CnCloudLayout_isFixedRowNum, false);
    	// 默认固定控件宽高
    	isFixedLayout = a.getBoolean(R.styleable.CnCloudLayout_isFixedLayout, true);
        a.recycle();
        
        System.out.println("参数：" + "mWidgetPadding:" + mWidgetPadding + 
        		", minRowNum:" + minRowNum + ", mGravity:" + mGravity +
        		", isFixedRowNum:" + isFixedRowNum + ", isFixedLayout:" + isFixedLayout);
	}

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mWidth = MeasureSpec.getSize(widthMeasureSpec);

        mPadding = new Padding(getPaddingLeft(), getPaddingRight(),
        		getPaddingTop(), getPaddingBottom());

        System.out.println("Padding:" + mPadding.paddingLeft +"  " + mPadding.paddingRight);

        int mCount = getChildCount();

        mLeft = 0;
        mRight = 0;
        mTop = 0;
        mBottom = 0;

        int innerwidth = 0; // 内宽度
        int d_value = 0; // 差值 为排满后还剩几位

        for (int i = 0; i < mCount; i++) {
            final View child = getChildAt(i);

            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            // 此处增加onLayout中的换行判断，用于计算所需的高度
            int childw = child.getMeasuredWidth();
            int childH = child.getMeasuredHeight();
            if (isFixedLayout) {
            	if (mPadding.padding == 0) {
                	innerwidth = mWidth - mPadding.paddingLeft - mPadding.paddingRight;
                	mPadding.height = childH;
                	if (isFixedRowNum) { // 是否是固定每行显示数量
                		showRowNum = minRowNum;
                	} else {
                		// 优先测量每行放多少列 间距多少
                    	showRowNum = (int) ((innerwidth + mWidgetPadding)/(childw + mWidgetPadding));
                    	showRowNum = showRowNum == 0 ? 1 : showRowNum;
                	}

                	d_value = showRowNum - (mCount % showRowNum);

                	if (mGravity == 3) { // blance_middle padding值均分中间区域
                		mPadding.padding = (innerwidth - showRowNum * childw) / ( showRowNum - 1);
                		mPadding.width = childw;
                	} else if (mGravity == 4) { // blance padding值均分所有区域
                		mPadding.padding = (mWidth - showRowNum * childw) / ( showRowNum + 1);
                		mPadding.width = childw;
                	} else {
                		mPadding.width = childw;
                		mPadding.padding = (int) mWidgetPadding;
                	}
    			}
            } else {}

            Position position = new Position();
            mLeft = getPosition(i);

            if (mGravity == 1 && i + mCount % showRowNum >= mCount) {
            	mLeft = getPosition(i + d_value);
            }
            mRight = mLeft + mPadding.width;
            if (i % showRowNum == 0) {
                if (i == 0) {
                	mTop = mPadding.paddingTop;
                } else {
                	mTop += mWidgetPadding + mPadding.height;
				}
            }
            mBottom = mTop + mPadding.height;
            position.left = mLeft;
            position.top = mTop;
            position.right = mRight;
            position.bottom = mBottom;
            map.put(child, position);
        }
        setMeasuredDimension(mWidth, mBottom + mPadding.paddingBottom);
    }

    @Override
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(1, 1); 
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Position pos = map.get(child);
            if (pos != null) {
                child.layout(pos.left, pos.top, pos.right, pos.bottom);
            } else {
                Log.i("MyLayout", "error");
            }
        }
    }


    private class Position {
        int left, top, right, bottom;
    }
    
    private class Padding {
    	public Padding(int paddingLeft, int paddingRight,
    			int paddingTop, int paddingBottom){
    		this.paddingLeft = paddingLeft;
    		this.paddingRight = paddingRight;
    		this.paddingTop = paddingTop;
    		this.paddingBottom = paddingBottom;
    	} 
    	
    	public int paddingLeft, paddingRight,
    		paddingTop, paddingBottom, padding/** 子控件间距 */, width, height;
    }

    public int getPosition(int IndexInRow) {
    	if (isFixedLayout) { 
    		int index = IndexInRow % showRowNum;
    		if (mGravity == 4) {
    			return mPadding.padding + (mPadding.width + mPadding.padding) * index;
    		} else if (mGravity == 3 || mGravity == 0) {
    			return mPadding.paddingLeft + (mPadding.width + mPadding.padding) * index;
    		} else if (mGravity == 1) {
    			return mWidth - mPadding.paddingRight - (showRowNum - index) * (mPadding.padding + mPadding.width) + mPadding.padding;
    		} else if (mGravity == 2) {
    			int left = (mWidth - showRowNum * (mPadding.width + mPadding.padding) + mPadding.padding) / 2;
    			return left + (mPadding.width + mPadding.padding) * index;
    		} else {
    			return mPadding.paddingLeft + (mPadding.width + mPadding.padding) * index;
    		}
    	} else {
    		if (IndexInRow > 0) {
                return getPosition(IndexInRow - 1)
                        + mPadding.width + mPadding.padding;
            }
            return mPadding.paddingLeft;
    	}
    }

    /**
     * 得到初始控件
     */
    public TextView getSimpleView(String prompt) {
        View view = View.inflate(getContext(), R.layout.cn_simple_text_view, null);
        TextView tv = (TextView) view.findViewById(R.id.cn_tv_simple_text);
        LayoutParams vp = tv.getLayoutParams();
        vp.width = LayoutParams.WRAP_CONTENT;
        vp.height = LayoutParams.WRAP_CONTENT;
        tv.setGravity(Gravity.CENTER);
        tv.setText(prompt);
        tv.setPadding(16, 4, 16, 4);
        tv.setLayoutParams(vp);
        this.addView(view);
        return tv;
    }
	
}