package com.vortex.common.listener;

import android.text.Editable;

/**
 * 纯数字输入
 *
 * @author Johnny.xu
 *         date 2017/3/29
 */
public class CnInputNumberListener extends CnEditInputListener {

    @Override
    public void afterTextChanged(Editable s) {
        String location_name = s.toString();
        if (location_name.matches(".*[^A-Z^a-z^0-9].*")) {
            location_name = location_name.replaceAll("[^A-Z^a-z^0-9]", "");
            s.clear();
            s.append(location_name);
        }
    }
}
