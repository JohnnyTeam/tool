package com.vortex.common.xutil;

import android.app.Activity;
import android.view.View;

import org.xutils.x;

/**
 * Xutil工具View实现类
 * @author Johnny.xu
 * @time 2017/2/9
 */
public class ViewUtil {

    /**
     * 初始化View的注解方式
     * @param activity
     */
    public static void inject(Activity activity) {
        x.view().inject(activity);
    }

    /**
     * 初始化View的注解方式
     * @param view
     */
    public static void inject(View view) {
        x.view().inject(view);
    }

    /**
     * 初始化View的注解方式
     */
    public static void inject(Object obj, View view) {
        x.view().inject(obj, view);
    }




}
