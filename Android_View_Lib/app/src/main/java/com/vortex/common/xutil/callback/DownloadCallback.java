package com.vortex.common.xutil.callback;

import java.io.File;

/**
 * 下载请求回调
 *
 * @author Johnny.xu
 *         date 2017/2/21
 */
public abstract class DownloadCallback {
    public void onStart() {}

    public void onLoading(long allCount, long count) {}

    public void onSuccess(File file) {}

    public void onFailed() {}

    public void onFinished() {}
}
