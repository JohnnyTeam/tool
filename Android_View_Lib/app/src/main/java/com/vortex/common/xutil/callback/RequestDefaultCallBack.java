package com.vortex.common.xutil.callback;

import org.xutils.common.Callback;

/**
 * 请求回调
 *
 * @author Johnny.xu
 *         date 2017/2/21
 */
public abstract class RequestDefaultCallBack implements Callback.CommonCallback<String> {

    @Override
    public void onSuccess(String s) {

    }

    @Override
    public void onError(Throwable throwable, boolean b) {

    }

    @Override
    public void onCancelled(CancelledException e) {

    }

    @Override
    public void onFinished() {

    }

}
