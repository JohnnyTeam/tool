package com.vortex.common.listener;

import android.text.Editable;

/**
 * 小数输入监听
 *
 * @author Johnny.xu
 *         date 2017/3/29
 */
public class CnInputDecimalsListener extends CnEditInputListener {

    @Override
    public void afterTextChanged(Editable s) {
        String location_name = s.toString();
        if (location_name.matches(".*[^.^0-9].*") || location_name.startsWith(".") ||
                (location_name.contains(".") && location_name.lastIndexOf(".") != location_name.indexOf("."))) {
            location_name = location_name.replaceAll("[^.^0-9]", "");
            location_name = location_name.replaceFirst("\\.", "A");
            location_name = location_name.replaceAll("\\.", "");
            location_name = location_name.replaceFirst("A", ".");
            s.clear();
            if (location_name.startsWith(".")) {
                location_name = "0" + location_name;
                s.append(location_name);
            } else {
                s.append(location_name);
            }
        }
    }
}
