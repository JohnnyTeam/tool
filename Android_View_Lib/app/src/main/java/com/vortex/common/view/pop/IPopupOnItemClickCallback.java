package com.vortex.common.view.pop;

/**
 * <p>Title:IPopupOnItemClickCallback.java</p>
 * <p>Description:弹出框点击Item回调监听</p>
 * @author Johnny.xu
 * @date 2016年12月6日
 */
public interface IPopupOnItemClickCallback {
	
	/**
	 * item的点击事件
	 * @param position item的下标
	 * @param strName item的数据
	 */
	void onClick(int position, String strName);
}
