package com.vortex.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 数据缓存类
 *
 * @author Johnny.xu
 *         date 2017/2/21
 */
public class SharePreferUtil {

    private static String TAG = "default_code";

    public static void setTag(String code) {
        TAG = code;
    }

    public static Editor getEditor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG,
                Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
        return sharedPreferences.edit();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(TAG, Context.MODE_WORLD_WRITEABLE | Context.MODE_WORLD_READABLE);
    }

    public static Editor getDefaultEditor(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.edit();
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

//    // 保存账号
//    public static void saveAccount(Context context, String userName) {
//        Editor editor = getEditor(context);
//        editor.putString(USERNAME, userName).commit();
//    }
//
//    public static String getAccount(Context context) {
//        return getSharedPreferences(context).getString(USERNAME, "");
//    }

}
