package com.vortex.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.vortex.common.util.VorLog;

/**
 * 自定义年月选择
 *
 * @author Johnny.xu
 *         date 2017/3/21
 */
public class DataPickerYM extends DatePicker {

    public DataPickerYM(Context context, AttributeSet attrs) {
        super(context, attrs);

//        ((ViewGroup) ((ViewGroup) getChildAt(0)).
//                getChildAt(0)).
//                getChildAt(2).
//                setVisibility(View.GONE);

        printChild((ViewGroup) getChildAt(0), 1);
    }

    private void printChild(ViewGroup vg, int index) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            VorLog.i("ViewGroup index:" + index + " childGroup:" + i);
            if (vg.getChildAt(i) instanceof ViewGroup) {
                printChild((ViewGroup) vg.getChildAt(i), index + 1);
            }
        }
    }
}
