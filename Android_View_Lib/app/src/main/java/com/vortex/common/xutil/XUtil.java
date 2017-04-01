package com.vortex.common.xutil;

import com.google.gson.Gson;
import com.vortex.common.util.VorLog;
import com.vortex.common.xutil.callback.DownloadCallback;
import com.vortex.common.xutil.callback.RequestCallBack;
import com.vortex.common.xutil.callback.RequestDefaultCallBack;
import com.vortex.common.xutil.empty.DefaultDownloadViewHolder;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.Map;

/**
 * xutil工具类
 *
 * @author Johnny.xu
 *         date 2017/2/21
 */
class XUtil {

    /**
     * 基础请求回调封装
     */
    static Callback.CommonCallback getCallback(final RequestCallBack callback) {
        return new RequestDefaultCallBack() {

            @Override
            public void onSuccess(String s) {
                VorLog.i(s);
                if (callback == null) return;
                try {
                    JSONObject jo = new JSONObject(s);
                    if (jo.optInt("result", RequestCallBack.Error_Code_Success) == RequestCallBack.Error_Code_Success) {
                        callback.onSuccess(jo);
                    } else {
                        callback.onFailed(RequestCallBack.Error_Code_Default, jo.optString("msg"));
                    }
                } catch (JSONException e) {
                    callback.onFailed(RequestCallBack.Error_Code_Json, "数据解析错误！");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (callback == null){
                    VorLog.i("onError : callback is null" );
                    return;
                }
                if (throwable instanceof HttpException) {
                    callback.onFailed(RequestCallBack.Error_Code_Network, "网络错误！");
                } else if (throwable.getMessage().endsWith("(Network is unreachable)")) {
                    callback.onFailed(RequestCallBack.Error_Code_Network, "网络错误！");
                } else {
                    String errorStr = throwable.getMessage();
                    if (errorStr.startsWith("failed to connect to") && errorStr.endsWith("after 15000ms")) {
                        callback.onFailed(RequestCallBack.Error_Code_Default, "连接超时");
                    } else {
                        callback.onFailed(RequestCallBack.Error_Code_Default, throwable.getMessage());
                    }
                }
                VorLog.i("onError : " + throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {
                VorLog.i("onCancelled : " + e.getMessage());
                if (callback != null) callback.onCancel();
            }

            @Override
            public void onFinished() {
                if (callback != null) callback.onFinished();
            }
        };
    }

    /**
     * 基础参数封装
     */
    static RequestParams getParameter(String url, Map<String, Object> params) {
        RequestParams param = new RequestParams(url);
        if (params == null) return param;
        for (String key : params.keySet()) {
            Object obj = params.get(key);
            if (obj != null) {
                if (obj instanceof File) {
                    param.addBodyParameter(key, (File) obj);
                    params.remove(key);
                }
            }
        }
        param.addBodyParameter("parameters",  new Gson().toJson(params));
        return param;
    }

    /**
     * 基础参数封装
     */
    static RequestParams getParameter(String url, Object obj) {
        RequestParams param = new RequestParams(url);
        if (obj == null) return param;
        param.addBodyParameter("parameters",  new Gson().toJson(obj));
        return param;
    }

    /**
     * 基础参数封装
     */
    static RequestParams getParameter(String url) {
        return new RequestParams(url);
    }

    static DefaultDownloadViewHolder getDownloadDefaultCallback(final DownloadCallback callback) {

        return new DefaultDownloadViewHolder(null, null) {
            @Override
            public void onStarted() {
                callback.onStart();
            }

            @Override
            public void onLoading(long total, long current) {
                callback.onLoading(total, current);
            }

            @Override
            public void onSuccess(File result) {
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                callback.onFailed();
            }
        };

//        return new Callback.ProgressCallback<File>() {
//            @Override
//            public void onWaiting() {
//
//            }
//
//            @Override
//            public void onStarted() {
//                callback.onStart();
//            }
//
//            @Override
//            public void onLoading(long l, long l1, boolean b) {
//                callback.onLoading(l, l1);
//            }
//
//            @Override
//            public void onSuccess(File file) {
//                callback.onSuccess(file);
//            }
//
//            @Override
//            public void onError(Throwable throwable, boolean b) {
//                callback.onFailed();
//
//                VorLog.d(throwable.getMessage());
//            }
//
//            @Override
//            public void onCancelled(CancelledException e) {
//
//            }
//
//            @Override
//            public void onFinished() {
//                callback.onFinished();
//            }
//        };
    }
}
