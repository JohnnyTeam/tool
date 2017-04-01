package com.vortex.common.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 输入框输入监听回调
 *
 * @author Johnny.xu
 *         date 2017/2/11
 */
public abstract class CnEditInputListener implements TextWatcher {

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    public void afterTextChanged(Editable s) {}
}
