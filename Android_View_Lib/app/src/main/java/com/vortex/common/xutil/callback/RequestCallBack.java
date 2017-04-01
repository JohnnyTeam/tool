package com.vortex.common.xutil.callback;

import org.json.JSONObject;

/**
 * 请求回调
 *
 * @author Johnny.xu
 *         date 2017/2/21
 */
public abstract class RequestCallBack {

    public static final int Error_Code_Success = 0;
    public static final int Error_Code_Json = -99;
    public static final int Error_Code_Network = -98;
    public static final int Error_Code_Default = 1;

    public void onStart() {}

    public void onSuccess(JSONObject jsonObject) {}

    public void onFailed(int code, String error) {}

    public void onFinished() {}

    public void onCancel(){}

}
