package com.vortex.common.xutil;

import com.google.gson.Gson;
import com.vortex.common.util.VorLog;
import com.vortex.common.xutil.callback.DownloadCallback;
import com.vortex.common.xutil.callback.RequestCallBack;
import com.vortex.common.xutil.empty.DownloadManager;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.Map;

/**
 * 请求工具类
 *
 * @author Johnny.xu
 *         date 2017/2/21
 */
public class HttpUtil {

    /**
     * get网络请求方式
     */
    public static Callback.Cancelable get(String url, Map<String, Object> params, RequestCallBack callback) {

        if (callback != null) callback.onStart();
        VorLog.i(buildStringUrl(url, params));
        return x.http().get(XUtil.getParameter(url, params), XUtil.getCallback(callback));
    }

    /**
     * post网络请求方式
     */
    public static Callback.Cancelable post(String url, Map<String, Object> params, RequestCallBack callback) {

        if (callback != null) callback.onStart();
        VorLog.i(buildStringUrl(url, params));
        return x.http().post(XUtil.getParameter(url, params), XUtil.getCallback(callback));
    }

    /**
     * post网络请求方式
     */
    public static Callback.Cancelable postBean(String url, Object params, RequestCallBack callback) {

        if (callback != null) callback.onStart();
        VorLog.i(buildStringUrl(url, params));
        return x.http().post(XUtil.getParameter(url, params), XUtil.getCallback(callback));
    }

    /**
     * post网络请求方式
     */
    public static Callback.Cancelable post(String url, RequestCallBack callback) {

        if (callback != null) callback.onStart();
        VorLog.i(url);
        return x.http().post(XUtil.getParameter(url), XUtil.getCallback(callback));
    }

    /**
     * 下载请求
     */
    public static void reqDownloadGet(String url, String address, DownloadCallback callback) {
        VorLog.i("正在下载文件 Address : " + url);
        try {
            DownloadManager.getInstance().startDownload(url, "", address, false, false, XUtil.getDownloadDefaultCallback(callback));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 下载请求
//     */
//    public static void reqDownloadPost(String url, Map<String, Object> params, DownloadCallback callback) {
//        x.http().post(XUtil.getParameter(url, params), XUtil.getDownloadDefaultCallback(callback));
//    }

    private static String buildStringUrl(String url, Map<String, Object> params) {
        if (params != null) {
            return url + "?parameters=" + new Gson().toJson(params);
        } else {
            return url;
        }
    }

    private static String buildStringUrl(String url, Object obj) {
        if (obj != null) {
            return url + "?parameters=" + new Gson().toJson(obj);
        } else {
            return url;
        }
    }

}
