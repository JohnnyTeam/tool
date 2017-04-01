package com.vortex.common.manager.gps;

import android.location.Location;

/**
 * <p>Title:AbsGpsCallBack.java</p>
 * <p>Description:Gps管理类回调事件</p>
 * @author Johnny.xu
 * @date 2016年11月11日
 */
public abstract class AbsGpsCallBack {
	
	public void locError(String error){};
	
	public void locInfo(Location location){};
	
}
