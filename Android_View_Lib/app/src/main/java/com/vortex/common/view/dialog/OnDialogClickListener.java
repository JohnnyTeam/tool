package com.vortex.common.view.dialog;

/**
 * 自定义DialogButton点击事件
 * @author Johnny.xu
 */
public abstract class OnDialogClickListener {
	
	/**
	 * 确认按钮点击事件
	 */
	public void onConfirmClick(String edit) {}
	
	/**
	 * 取消按钮点击事件
	 */
	public void onCancelClick() {}
    
    /**
     * 叉按钮点击事件
     */
	public void onForkClick() {}

    /**
     * 系统取消
     */
    public void onCancelListener() {}

    /**
     * 分享左边点击事件
     */
    public void shareLeftListener() {}

    /**
     * 分享右边点击事件
     */
    public void shareRightListener() {}
}

