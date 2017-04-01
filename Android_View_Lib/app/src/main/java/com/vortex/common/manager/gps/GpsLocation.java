package com.vortex.common.manager.gps;

/**
 * <p>Title:GpsLocation.java</p>
 * <p>Description:GPS定位类</p>
 * @author Johnny.xu
 * @date 2016年11月14日
 */
public class GpsLocation extends AbsGpsLocation {
	
	GpsLocation() {
		super();
	}
	
	public void addLocCallBack(String tag, AbsGpsCallBack callBack) {
		synchronized (GpsLocationManager.getInstance()) {
			mListenerList.put(tag, callBack);
			if (mListenerList.size() == 1) {
				reLock();
			}
		}
	}
	
	public synchronized void removeLocCallBack(String tag) {
		synchronized (GpsLocationManager.getInstance()) {
			mListenerList.remove(tag);
			if (mListenerList.size() == 0) {
				lm.removeUpdates(locationListener);
			}
		}
	}
	
	public synchronized void reLock() {
		super.reLock();
	}
}
