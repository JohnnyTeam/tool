package com.vortex.common.base;

import android.app.Application;

import org.xutils.x;

/**
 * <p>Title:CnBaseApplication.java</p>
 * <p>Description:基础应用类</p>
 * @author Johnny.xu
 * @date 2017年1月18日
 */
public class CnBaseApplication extends Application {
	
	private static CnBaseApplication mInstance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		x.Ext.init(this);
	}
	
	public static CnBaseApplication getInstance() {
		return mInstance;
	}  
}
