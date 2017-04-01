package com.vortex.common.view.dialog;

import android.content.Context;
import android.view.View;

/**
 * Dialog属性类
 * @author Johnny.xu
 */
public class AlertDialogParameter {
	
	/**
	 * 正文为TextView
	 */
	public final static int CONTENT_STYLE_TEXTVIEW = 0;
	
	/**
	 * 正文为EditText
	 */
	public final static int CONTENT_STYLE_EDITVIEW = 1;
	
	/**
	 * 正文为自定义布局
	 */
	public final static int CONTENT_STYLE_CUSTOM = 2;
	
	/**
	 * 一般布局
	 */
	public final static int THEME_DEFAULT = 1;
	
	/**
	 * 无左边图片
	 */
	public final static int THEME_NoLeftImage = 2;
	
	/**
	 * 无标题栏
	 */
	public final static int THEME_NoTitleBar = 3;
	
	/**
	 * Edit无输入输入限制
	 */
	public final static int EDIT_INPUT_DAFULT = 0;
	
	/**
	 * Edit字母(大小写)、数字输入限制
	 */
	public final static int EDIT_INPUT_LETTER_AND_NUMBER = 1;

    /**
     * Edit数字输入限制
     */
	public final static int EDIT_INPUT_NUMBER = 2;

    /**
     * Edit样式---下边添加文本
     */
	public final static int EDIT_VIEW_STYLE_PLUS_TEXT = 2;

	/**
	 * 电灯图片
	 */
	public final static int LEFT_TOP_IMAGE_LAMP = 1;
	/**
	 * 五角星图片
	 */
	public final static int LEFT_TOP_IMAGE_STAR = 2;
	
	/**
	 * 标题风格
	 */
	public int dialogStyle = THEME_DEFAULT;
	
	/**
	 * 正文样式
	 */
	public int contentStyle = CONTENT_STYLE_TEXTVIEW;
	
	/**
	 * EditText输入样式
	 */
	public int editTextStyle = 0;

    /**
     * Edit文本样式
     */
	public int editStyle = 0;

    /**
     * 点击确认按钮未输入时是否退出弹框
     */
    public boolean isAutoDismiss;

    /**
     * 是否显示弹框
     */
    public boolean isShowSoftInput;

	/**
	 * 上下文
	 */
	public Context context;
	/**
	 * dialog标题
	 */
	public String dialogTitle;
	/**
	 * 取消按钮的名称
	 */
	public String dialogCancelName;
	/**
	 * 确定按钮的名称
	 */
	public String dialogConfirmName;
	/**
	 * TextView内容
	 */
	public String dialogContent;
	/**
	 * EditText默认内容
	 */
	public String dialogEditHint;
	/**
	 * EditText内容
	 */
	public String dialogEditContent;
	/**
	 * EditText输入长度
	 */
	public int length;
	/**
	 * 中部区域View
	 */
	public View contentView;
	/**
	 * 左上角图标风格
	 */
	public int leftTopImageStyle = LEFT_TOP_IMAGE_LAMP;
	/**
	 * 左上角图标资源
	 */
	public int leftTopImageResource;
	
	/**
	 * 是否允许点击监听外部事件
	 */
	public boolean canceledOnTouchOutside;
	
	/**
	 * 是否监听返回事件
	 */
	public boolean cancelable; 
	
	public OnDialogClickListener onClickListener;
	
	/**
	 * 初始化alertDialog
	 * @param context 上下文
	 * @param dialogStyle 主题样式（暂时不用）
	 * @param layoutStyle 布局样式（暂时不用）
	 */
	public AlertDialogParameter(Context context, int dialogStyle, int layoutStyle) {
		this.context = context;
		this.dialogStyle = dialogStyle;
		this.contentStyle = layoutStyle;
	}
	
	/**
	 * 初始化alertDialog
	 * @param context 上下文
	 * @param dialogStyle 主题样式
	 */
	public AlertDialogParameter(Context context, int dialogStyle) {
		this.context = context;
		this.dialogStyle = dialogStyle;
	}
	
	/**
	 * 初始化alertDialog
	 * @param context 上下文
	 */
	public AlertDialogParameter(Context context) {
		this.context = context;
	}
	
	public boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
}
