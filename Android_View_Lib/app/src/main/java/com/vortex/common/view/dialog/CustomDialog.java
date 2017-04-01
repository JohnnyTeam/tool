package com.vortex.common.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vortex.common.library.R;
import com.vortex.common.listener.CnInputDecimalsListener;
import com.vortex.common.listener.CnInputNumberListener;
import com.vortex.common.util.StringUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 构建Dialog
 *
 * @author Johnny.xu
 */
public class CustomDialog {

    public static class BuildCustomDialog {

        private final AlertDialogParameter mParameter;
        private LayoutInflater layoutInflater;
        private AlertDialog.Builder mDialogBuilder;
        private Context mContext;
        private EditText edit;

        public BuildCustomDialog(Context context) {
            mDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
            mParameter = new AlertDialogParameter(context);
            layoutInflater = LayoutInflater.from(context);
            this.mContext = context;
        }

        public BuildCustomDialog(Context context, int dialogStyle) {
            mDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
            mParameter = new AlertDialogParameter(context, dialogStyle);
            layoutInflater = LayoutInflater.from(context);
            this.mContext = context;
        }

        public BuildCustomDialog(Context context, int dialogStyle, int layoutStyle) {
            mDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
            mParameter = new AlertDialogParameter(context, dialogStyle, layoutStyle);
            layoutInflater = LayoutInflater.from(context);
            this.mContext = context;
        }

        public BuildCustomDialog setLeftTopImageStyle(int leftTopImageStyle) {
            mParameter.leftTopImageStyle = leftTopImageStyle;
            return this;
        }

        public BuildCustomDialog setLeftTopImageResource(int leftTopImageResource) {
            mParameter.leftTopImageResource = leftTopImageResource;
            return this;
        }

        public BuildCustomDialog setDialogTitle(String dialogTitle) {
            mParameter.dialogTitle = dialogTitle;
            return this;
        }

        public BuildCustomDialog setDialogCancelButton(String dialogCancelName) {
            mParameter.dialogCancelName = dialogCancelName;
            return this;
        }

        public BuildCustomDialog setDialogConfirmButton(String dialogConfirmName) {
            mParameter.dialogConfirmName = dialogConfirmName;
            return this;
        }

        public BuildCustomDialog setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
            mParameter.onClickListener = onDialogClickListener;
            return this;
        }

        public BuildCustomDialog setDialogContent(String dialogContent) {
            mParameter.dialogContent = dialogContent;
            return this;
        }

        public BuildCustomDialog setDialogEditContent(String dialogEditHint, String dialogEditContent, int length) {
            mParameter.dialogEditHint = dialogEditHint;
            mParameter.dialogEditContent = dialogEditContent;
            mParameter.length = length;
            return this;
        }

        public BuildCustomDialog setContentView(View view) {
            mParameter.contentStyle = AlertDialogParameter.CONTENT_STYLE_CUSTOM;
            mParameter.contentView = view;
            return this;
        }

        public BuildCustomDialog setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mParameter.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public BuildCustomDialog setEditTextStyle(int editTextStyle) {
            mParameter.editTextStyle = editTextStyle;
            return this;
        }

        public BuildCustomDialog setEditStyle(int editStyle) {
            mParameter.editStyle = editStyle;
            return this;
        }

        public BuildCustomDialog setCancelable(boolean cancelable) {
            mParameter.cancelable = cancelable;
            return this;
        }

        public BuildCustomDialog setAutoDismiss(boolean isAutoDismiss) {
            mParameter.isAutoDismiss = isAutoDismiss;
            return this;
        }

        public BuildCustomDialog setShowSoftInput(boolean isShowSoftInput) {
            mParameter.isShowSoftInput = isShowSoftInput;
            return this;
        }

        @SuppressWarnings("deprecation")
		public AlertDialog showAlertDialog() {
            AlertDialog alertDialog = mDialogBuilder.create();
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setView(layoutInflater.inflate(R.layout.cn_dialog_layout_prompt, null));

            try {
                alertDialog.show();
            } catch (WindowManager.BadTokenException ex) {
                return null;
            }

            View view = layoutInflater.inflate(R.layout.cn_dialog_layout_prompt, null);
            applyDialog(alertDialog, view);
            alertDialog.setContentView(view);

            // 先顶dialog宽度为整个屏幕的88%
	        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
	        WindowManager manager = alertDialog.getWindow().getWindowManager();
	        Display display = manager.getDefaultDisplay();
	        params.width = (int) (display.getWidth() * 0.88);
	        alertDialog.getWindow().setAttributes(params);

            if (mParameter.isShowSoftInput) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        showInputMethod();
                    }
                }, 500);
            }
            return alertDialog;
        }

        /**
         * 初始化Dialog
         *
         * @param dialog
         * @param view   自定义布局
         */
        public void applyDialog(final AlertDialog dialog, final View view) {

            // Dialog标题
            if (!mParameter.isEmpty(mParameter.dialogTitle)) {
                ((TextView) view.findViewById(R.id.cn_dialog_title_text)).setText(mParameter.dialogTitle);
            }
            view.findViewById(R.id.cn_ll_et_content).setVisibility(View.GONE);

            // 中央TextView的文字
            if (mParameter.contentStyle == AlertDialogParameter.CONTENT_STYLE_TEXTVIEW) {
                view.findViewById(R.id.cn_tv_content).setVisibility(View.VISIBLE);
                if (!mParameter.isEmpty(mParameter.dialogContent)) {
                    ((TextView) view.findViewById(R.id.cn_tv_content)).setText(mParameter.dialogContent);
                }
            } else if (mParameter.contentStyle == AlertDialogParameter.CONTENT_STYLE_EDITVIEW) {
                edit = (EditText) view.findViewById(R.id.cn_et_content);
                view.findViewById(R.id.cn_ll_et_content).setVisibility(View.VISIBLE);
                if (!mParameter.isEmpty(mParameter.dialogEditHint)) {
                    edit.setHint(mParameter.dialogEditHint);
                }
                if (!mParameter.isEmpty(mParameter.dialogEditContent)) {
                    edit.setText(mParameter.dialogEditContent);
                }
                if (mParameter.length != 0) {
                    edit.setMaxEms(mParameter.length);
                    edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mParameter.length)});
                }
                if (mParameter.editStyle == AlertDialogParameter.EDIT_VIEW_STYLE_PLUS_TEXT) {
                    if (!mParameter.isEmpty(mParameter.dialogContent)) {
                        view.findViewById(R.id.cn_tv_content_prompt).setVisibility(View.VISIBLE);
                        ((TextView) view.findViewById(R.id.cn_tv_content_prompt)).setText(mParameter.dialogContent);
                    }
                }

                // EditText 输入样式判断
                if (mParameter.editTextStyle == AlertDialogParameter.EDIT_INPUT_LETTER_AND_NUMBER) {
                    edit.addTextChangedListener(new CnInputNumberListener());
                } else if (mParameter.editTextStyle == AlertDialogParameter.EDIT_INPUT_NUMBER) {
                    edit.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                    edit.addTextChangedListener(new CnInputDecimalsListener());
                }
            } else if (mParameter.contentStyle == AlertDialogParameter.CONTENT_STYLE_CUSTOM) {
                LinearLayout layout = (LinearLayout) view.findViewById(R.id.cn_ll_content_view);
                layout.removeAllViews();
                if (mParameter.contentView != null) {
                	layout.addView(mParameter.contentView);
                }
            }

            // Dialog确定按钮
            if (!StringUtils.isEmpty(mParameter.dialogConfirmName)) {
                final TextView confirmButton = (TextView) view.findViewById(R.id.cn_btn_confirm);
                confirmButton.setVisibility(View.VISIBLE);
                confirmButton.setText(mParameter.dialogConfirmName);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hintInputMethod();
                        if (!mParameter.isAutoDismiss) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                        if (mParameter.onClickListener != null) {
                            if (mParameter.contentStyle == AlertDialogParameter.CONTENT_STYLE_CUSTOM) {
                                mParameter.onClickListener.onConfirmClick("");
                            } else {
                                mParameter.onClickListener.onConfirmClick(((EditText) view.findViewById(R.id.cn_et_content)).getText().toString().trim());
                            }
                        }
                    }
                });
            } else {
                view.findViewById(R.id.cn_btn_confirm).setVisibility(View.GONE);
            }

            // Dialog取消按钮
            if (!StringUtils.isEmpty(mParameter.dialogCancelName)) {
                final TextView cancelButton = (TextView) view.findViewById(R.id.cn_btn_cancel);
                cancelButton.setVisibility(View.VISIBLE);
                cancelButton.setText(mParameter.dialogCancelName);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hintInputMethod();
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if (mParameter.onClickListener != null) {
                            mParameter.onClickListener.onCancelClick();
                        }
                    }
                });
            } else {
                view.findViewById(R.id.cn_btn_cancel).setVisibility(View.GONE);
            }

            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if (mParameter.onClickListener != null) {
                        mParameter.onClickListener.onCancelListener();
                    }
                }
            });

            dialog.setCanceledOnTouchOutside(mParameter.canceledOnTouchOutside);
//			dialog.setCancelable(mParameter.cancelable);
        }

        private void hintInputMethod() {
            if (edit != null && mContext != null) {
                InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

        private void showInputMethod() {
            if (edit != null && mContext != null) {
                InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                im.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }
}
