package com.vortex.common.factory;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.vortex.common.view.dialog.AlertDialogParameter;
import com.vortex.common.view.dialog.CustomDialog;
import com.vortex.common.view.dialog.OnDialogClickListener;

/**
 * <p>Title:DialogFactory.java</p>
 * <p>Description:弹出层工厂类</p>
 * @author Johnny.xu
 * @date 2016年12月9日
 */
public class CnDialogFactory {
	

    /**
     * 普通确认弹出框
     *
     * @param context               上下文
     * @param content               内容
     * @param onDialogClickListener 点击事件
     * @return AlertDialog
     */
    public static AlertDialog createSimpleConfirmDialog(Context context, String content, OnDialogClickListener onDialogClickListener) {
        return createCommonDialog(context, "提 示", "确认", "", content, onDialogClickListener);
    }

    /**
     * 创建确定按钮的AlertDialog
     *
     * @param context               正文
     * @param title                 标题
     * @param confirm               确认按钮名称
     * @param listener              按钮点击事件监听
     * @return AlertDialog
     */
    public static AlertDialog createSimpleConfirmDialog(Context context, String title, String confirm, String content, OnDialogClickListener listener) {
        return createCommonDialog(context, title, confirm, "", content, listener);
    }

    /**
     * 普通确认取消弹出框
     *
     * @param context               上下文
     * @param content               内容
     * @param onDialogClickListener 点击事件
     * @return AlertDialog
     */
    public static AlertDialog createSimpleDialog(Context context, String content, OnDialogClickListener onDialogClickListener) {
        return createCommonDialog(context, "提 示", "确认", "取消", content, onDialogClickListener);
    }

    /**
     * 创建取消按钮弹出框
     *
     * @param context               上下文
     * @param content               内容
     * @param onDialogClickListener 点击事件
     * @return AlertDialog
     */
    public static AlertDialog createSimpleCancelDialog(Context context, String content, OnDialogClickListener onDialogClickListener) {
        return createCommonDialog(context, "提 示", "", "取消", content, onDialogClickListener);
    }

    /**
     * 创建取消按钮弹出框
     *
     * @param context               上下文
     * @param content               内容
     * @param cancelBtn             取消按钮
     * @param onDialogClickListener 点击事件
     * @return AlertDialog
     */
    public static AlertDialog createSimpleCancelDialog(Context context, String cancelBtn, String content, OnDialogClickListener onDialogClickListener) {
        return createCommonDialog(context, "提 示", "", cancelBtn, content, onDialogClickListener);
    }

    /**
     * 创建取消确认按钮的AlertDialog
     *
     * @param context               正文
     * @param title                 标题
     * @param confirm               确认按钮名称
     * @param cancel                取消按钮名称
     * @param onDialogClickListener 按钮点击事件监听
     * @return AlertDialog
     */
    public static AlertDialog createCommonDialog(Context context, String title, String confirm, String cancel, String content, OnDialogClickListener onDialogClickListener) {
        CustomDialog.BuildCustomDialog build = new CustomDialog.BuildCustomDialog(context);
        build.setDialogTitle(title);
        build.setDialogCancelButton(cancel);
        build.setDialogConfirmButton(confirm);
        build.setDialogContent(content);
        build.setOnDialogClickListener(onDialogClickListener);
        return build.showAlertDialog();
    }

    /**
     * 自定义AlertDialog
     *
     * @param context               正文
     * @param title                 标题
     * @param confirm               确认按钮名称
     * @param cancel                取消按钮名称
     * @param onDialogClickListener 点击事件监听
     * @return
     */
    public static AlertDialog createDialogByView(Context context, String title, String confirm, String cancel, View view, OnDialogClickListener onDialogClickListener) {
        CustomDialog.BuildCustomDialog build = new CustomDialog.BuildCustomDialog(context);
        build.setDialogTitle(title);
        if (view != null) {
            build.setContentView(view);
        }
        if (cancel != null && !cancel.equals("")) {
            build.setDialogCancelButton(cancel);
        }
        if (confirm != null && !confirm.equals("")) {
            build.setDialogConfirmButton(confirm);
        }
        if (onDialogClickListener != null) {
            build.setOnDialogClickListener(onDialogClickListener);
        }
        return build.showAlertDialog();
    }

    /**
     * 创建Edit输入框的AlertDialog
     *
     * @param context               正文
     * @param title                 标题
     * @param confirm               确认按钮名称
     * @param cancel                取消按钮名称
     * @param hint                  Edit提示文案
     * @param editLength            Edit的长度（0表示没有长度）
     * @param onDialogClickListener 点击事件监听
     * @return AlertDialog
     */
    public static AlertDialog createEditDialog(Context context, String title, String confirm, String cancel, String hint, int editLength, OnDialogClickListener onDialogClickListener) {
        CustomDialog.BuildCustomDialog build = new CustomDialog.BuildCustomDialog(context, AlertDialogParameter.THEME_NoLeftImage, AlertDialogParameter.CONTENT_STYLE_EDITVIEW);
        build.setDialogTitle(title);
        build.setDialogConfirmButton(confirm);
        build.setDialogCancelButton(cancel);
        build.setDialogEditContent(hint, "", editLength);
        build.setEditTextStyle(AlertDialogParameter.EDIT_INPUT_NUMBER);
        build.setOnDialogClickListener(onDialogClickListener);
        return build.showAlertDialog();
    }


    /**
     * 创建Edit输入框的AlertDialog
     *
     * @param context               正文
     * @param title                 标题
     * @param confirm               确认按钮名称
     * @param cancel                取消按钮名称
     * @param content               Edit内容
     * @param hint                  Edit提示文案
     * @param editLength            Edit的长度（0表示没有长度）
     * @param onDialogClickListener 点击事件监听
     * @return AlertDialog
     */
    public static AlertDialog createEditDialog(Context context, String title, String confirm, String cancel, String content, String hint, int editLength, OnDialogClickListener onDialogClickListener) {
        CustomDialog.BuildCustomDialog build = new CustomDialog.BuildCustomDialog(context, AlertDialogParameter.THEME_NoLeftImage, AlertDialogParameter.CONTENT_STYLE_EDITVIEW);
        build.setDialogTitle(title);
        build.setDialogConfirmButton(confirm);
        build.setDialogCancelButton(cancel);
        build.setEditTextStyle(AlertDialogParameter.EDIT_INPUT_NUMBER);
        build.setDialogEditContent(hint, content, editLength);
        build.setOnDialogClickListener(onDialogClickListener);
        return build.showAlertDialog();
    }

    /**
     * 创建Edit输入框的AlertDialog有文本域
     *
     * @param context               正文
     * @param title                 标题
     * @param confirm               确认按钮名称
     * @param cancel                取消按钮名称
     * @param hint                  Edit提示文案
     * @param editLength            Edit的长度（0表示没有长度）
     * @param onDialogClickListener 点击事件监听
     * @return AlertDialog
     */
    public static AlertDialog createEditDialog(Context context, String title, String confirm, String cancel, String hint, int editLength,
                                               String text, int editStyle, int editTextStyle, OnDialogClickListener onDialogClickListener) {
        CustomDialog.BuildCustomDialog build = new CustomDialog.BuildCustomDialog(context, AlertDialogParameter.THEME_NoLeftImage, AlertDialogParameter.CONTENT_STYLE_EDITVIEW);
        build.setDialogTitle(title);
        build.setEditStyle(editStyle);
        build.setEditTextStyle(editTextStyle);
        build.setDialogContent(text);
        build.setDialogEditContent(hint, "", editLength);
        build.setDialogCancelButton(cancel);
        build.setDialogConfirmButton(confirm);
        build.setOnDialogClickListener(onDialogClickListener);
        return build.showAlertDialog();
    }

    public static AlertDialog createEditDialog(Context context, String title, String confirm, String cancel, String hint, int editLength,
                                               String text, int editStyle, int editTextStyle, boolean isAutoDismiss, boolean isShowSoftInput, OnDialogClickListener onDialogClickListener) {
        CustomDialog.BuildCustomDialog build = new CustomDialog.BuildCustomDialog(context, AlertDialogParameter.THEME_NoLeftImage, AlertDialogParameter.CONTENT_STYLE_EDITVIEW);
        build.setDialogTitle(title);
        build.setEditStyle(editStyle);
        build.setEditTextStyle(editTextStyle);
        build.setAutoDismiss(isAutoDismiss);
        build.setShowSoftInput(isShowSoftInput);
        build.setDialogContent(text);
        build.setDialogEditContent(hint, "", editLength);
        build.setDialogCancelButton(cancel);
        build.setDialogConfirmButton(confirm);
        build.setOnDialogClickListener(onDialogClickListener);
        return build.showAlertDialog();
    }
    
    public static AlertDialog createCustomDialog(Context context, String title, String confirm, View view, OnDialogClickListener onDialogClickListener) {
        CustomDialog.BuildCustomDialog build = new CustomDialog.BuildCustomDialog(context, AlertDialogParameter.THEME_NoLeftImage, AlertDialogParameter.CONTENT_STYLE_EDITVIEW);
        build.setDialogTitle(title);
        build.setDialogConfirmButton(confirm);
        build.setContentView(view);
        build.setOnDialogClickListener(onDialogClickListener);
        return build.showAlertDialog();
    }
}
