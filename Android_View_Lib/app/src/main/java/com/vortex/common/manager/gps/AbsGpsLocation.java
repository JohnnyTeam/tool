package com.vortex.common.manager.gps;

import android.content.Context;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import com.vortex.common.base.CnBaseApplication;
import com.vortex.common.util.VorLog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>Title:AbsGpsLocation.java</p>
 * <p>Description:GPS定位逻辑类</p>
 * @author Johnny.xu
 * @date 2016年11月14日
 */
abstract class AbsGpsLocation {
	
	protected static final String TAG = AbsGpsLocation.class.getName();
	
	protected LocationManager lm;
	
	protected Map<String, AbsGpsCallBack> mListenerList;
	
	public AbsGpsLocation() {
		if (mListenerList == null) {
			mListenerList = new HashMap<String, AbsGpsCallBack>();
		}
	}
	
	protected synchronized void initGpsService() {
		if (lm == null) {
			lm = (LocationManager) CnBaseApplication.getInstance()
					.getSystemService(Context.LOCATION_SERVICE);
		}
	}
	
	protected void initGpsSetting() {

		if (!lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			for (String str : mListenerList.keySet()) {
				mListenerList.get(str).locError("请开启GPS定位!");
			}
			return;
		}

		// 为获取地理位置信息时设置配置信息
		String bestProvider = lm.getBestProvider(getCriteria(), true);
		// 获取位置信息
		// 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
		Location location = lm.getLastKnownLocation(bestProvider);
		// Location location=
		// lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		for (String str : mListenerList.keySet()) {
			mListenerList.get(str).locInfo(location);
		}

		// 监听状态
		lm.addGpsStatusListener(listener);
		
		reLock();
	}
	
	protected void reLock() {
		// 绑定监听，有4个参数
		// 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
		// 参数2，位置信息更新周期，单位毫秒
		// 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
		// 参数4，监听
		// 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

		// 1秒更新一次，或最小位移变化超过1米更新一次；
		// 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
	}

	/**
	 * GPS定位配置信息
	 */
	protected Criteria getCriteria() {
		Criteria criteria = new Criteria();
		// 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 设置是否要求速度
		criteria.setSpeedRequired(false);
		// 设置是否允许运营商收费
		criteria.setCostAllowed(false);
		// 设置是否需要方位信息
		criteria.setBearingRequired(false);
		// 设置是否需要海拔信息
		criteria.setAltitudeRequired(false);
		// 设置对电源的需求
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}

	// 状态监听
	private GpsStatus.Listener listener = new GpsStatus.Listener() {

		@Override
		public void onGpsStatusChanged(int event) {
			switch (event) {
			// 第一次定位
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				VorLog.i(TAG, "第一次定位");
				break;
			// 卫星状态改变
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				VorLog.i(TAG, "卫星状态改变");
				// 获取当前状态
				GpsStatus gpsStatus = lm.getGpsStatus(null);
				// 获取卫星颗数的默认最大值
				int maxSatellites = gpsStatus.getMaxSatellites();
				// 创建一个迭代器保存所有卫星
				Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
				int count = 0;
				while (iters.hasNext() && count <= maxSatellites) {
					iters.next();
					count++;
				}
				VorLog.i(TAG, "搜索到：" + count + "颗卫星");
				break;
			// 定位启动
			case GpsStatus.GPS_EVENT_STARTED:
				VorLog.i(TAG, "定位启动");
				break;
			// 定位结束
			case GpsStatus.GPS_EVENT_STOPPED:
				VorLog.i(TAG, "定位结束");
				break;
			}
		};
	};

	// 位置监听
	protected LocationListener locationListener = new LocationListener() {

		/**
		 * 位置信息变化时触发
		 */
		@Override
		public void onLocationChanged(Location location) {
			VorLog.i("onLocationChanged回调" + location.getLongitude() + "  " + location.getLatitude());
			for (String str : mListenerList.keySet()) {
				mListenerList.get(str).locInfo(location);
			}
		}

		/**
		 * GPS状态变化时触发
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
			// GPS状态为可见时
			case LocationProvider.AVAILABLE:
				VorLog.i(TAG, "当前GPS状态为可见状态");
				break;
			// GPS状态为服务区外时
			case LocationProvider.OUT_OF_SERVICE:
				VorLog.i(TAG, "当前GPS状态为服务区外状态");
				break;
			// GPS状态为暂停服务时
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				VorLog.i(TAG, "当前GPS状态为暂停服务状态");
				break;
			}
		}

		/**
		 * GPS开启时触发
		 */
		@Override
		public void onProviderEnabled(String provider) {
			Location location = lm.getLastKnownLocation(provider);
			
			VorLog.i("onProviderEnabled 回调 ：" + location.getLongitude() + "  " + location.getLatitude());
			
			for (String str : mListenerList.keySet()) {
				mListenerList.get(str).locInfo(location);
			}
		}

		/**
		 * GPS禁用时触发
		 */
		@Override
		public void onProviderDisabled(String provider) {
			
			VorLog.i("onProviderDisabled 回调 ：" + provider);
			for (String str : mListenerList.keySet()) {
				mListenerList.get(str).locError("Gps已被禁用!" + provider);
			}
		}
	};
}
