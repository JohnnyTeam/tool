package com.vortex.common.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * <p>Title:CnBaseView.java</p>
 * <p>Description:初始View基础类</p>
 *
 * @author Johnny.xu
 *         date 2017年1月23日
 */
public class CnBaseView extends FrameLayout {

    private View mShowView;

    public CnBaseView(Context context) {
        this(context, null);
    }

    public CnBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (getContentViewId() != 0) {
            mShowView = View.inflate(getContext(), getContentViewId(), null);
            initView(mShowView);
            addView(mShowView);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

//        addView(mShowView);
    }

    public View getChildView() {
        return mShowView;
    }

    protected void initView(View view) {
    }

    protected int getContentViewId() {
        return 0;
    }

    protected void onDestory() {

    }

    protected int getResColorById(int id) {
        return getResources().getColor(id);
    }
}
