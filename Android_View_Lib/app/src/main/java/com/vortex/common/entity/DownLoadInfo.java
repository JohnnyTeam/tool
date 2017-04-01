package com.vortex.common.entity;

/**
 * 下载对象
 *
 * @author Johnny.xu
 *         date 2017/3/6
 */
public class DownLoadInfo {

    public String versionDescription;

    public int versionCode;

    public String versionName;

    public String url;

    public String getApkName() {
        return "V" + versionCode + "_" + versionName + "release.apk";
    }
}
