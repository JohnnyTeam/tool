package com.vortex.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.vortex.common.base.CnBaseView;
import com.vortex.common.library.R;

/**
 * <p>Title:RefreshDialogView.java</p>
 * <p>Description:刷新Dialog页面</p>
 *
 * @author Johnny.xu
 *         date 2017年1月23日
 */
public class RefreshDialogView extends CnBaseView {

    private TextView mRefreshPrompt;

    public RefreshDialogView(Context context) {
        super(context);
    }

    public RefreshDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.cn_refresh_dialog;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    public void setVisibility(boolean isShow) {
        super.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void showDialog() {
        showDialog("请求中...");
    }

    public void showDialog(String prompt) {
        setVisibility(View.VISIBLE);
        mRefreshPrompt.setText(prompt);
    }

    public boolean hideDialog() {
        if (getVisibility() == View.VISIBLE) {
            setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        mRefreshPrompt = (TextView) view.findViewById(R.id.tv_load_prompt);
    }

    /**
     * 拦截点击事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_DOWN || super.onTouchEvent(event);
    }
}
