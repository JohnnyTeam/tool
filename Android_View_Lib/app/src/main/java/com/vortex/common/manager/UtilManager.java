package com.vortex.common.manager;

import com.vortex.common.log.CrashHandler;
import com.vortex.common.util.FileUtils;
import com.vortex.common.util.SharePreferUtil;
import com.vortex.common.xutil.DbUtils;

import org.xutils.DbManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 工具管理类
 *
 * @author Johnny.xu
 *         date 2017/2/21
 */
public class UtilManager {

    private UtilManager() {}

    static UtilManager params;

    public static List<Class> filtrationClass = new ArrayList<Class>();

    public synchronized static UtilManager init() {
        if (params == null) {
            params = new UtilManager();
        }
        return params;
    }

    public UtilManager setLogDebug(boolean isShow) {
        return this;
    }

    public UtilManager setSharePreferCode(String name) {
        SharePreferUtil.setTag(name);
        return this;
    }

    public UtilManager setFiltrationClass(Class ... name) {
        if (name != null && name.length > 0) {
            filtrationClass.addAll(Arrays.asList(name));
        }
        return this;
    }

    public UtilManager setAppCode(String name) {
        FileUtils.AppCode = name;
        initAppCode();
        return this;
    }

    public DbManager initDBName(String dbName) {
        return DbUtils.create(dbName);
    }

    private void initAppCode() {
        CrashHandler.initPath();
        FileUtils.initPath();
    }
}
