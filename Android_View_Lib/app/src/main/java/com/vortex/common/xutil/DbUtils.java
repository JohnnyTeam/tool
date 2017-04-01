package com.vortex.common.xutil;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * @anthor wg
 * @time 2017/2/24 15:24
 * @describe xutil 数据库模块
 */
public class DbUtils {
    static DbManager.DaoConfig config;

    public static DbManager create(String dbName) {
        if (config == null) {

            config = new DbManager.DaoConfig()
                    .setDbName(dbName)
                    .setDbVersion(1)
                    .setAllowTransaction(true)//设置允许开启事务
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }


        return x.getDb(config);
    }

}
