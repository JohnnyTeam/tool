package com.vortex.common.manager.gps;

/**
 * <p>Title:GpsLocationManager.java</p>
 * <p>Description:GPS定位管理类</p>
 * @author Johnny.xu
 * @date 2016年11月11日
 */
public class GpsLocationManager {
	
	private static GpsLocation mGpsLocation;
	
	public synchronized static GpsLocation getInstance() {
		if (mGpsLocation == null) {
			mGpsLocation = new GpsLocation();
		}
		mGpsLocation.initGpsService();
		mGpsLocation.initGpsSetting();
		return mGpsLocation;
	}
}
