package com.vortex.common.view.pop;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

import java.util.List;
import java.util.Map;

/**
 * <p>Title:PopupParamet.java</p>
 * <p>Description:弹出框参数</p>
 * @author Johnny.xu
 * @date 2016年12月6日
 */
public class PopupParamet {
	
	public PopupParamet() {
//		mDataList = new ArrayList<String>();
	}
	
	public final static int POPUP_STYLE_CUSTOM = 0;
	
	public final static int POPUP_STYLE_ARRAY = 1;
	
	public final static int POPUP_STYLE_OTHER = 2;
	
	public final static int ADAPTER_STYLE_DEFAULT = 0;
	public final static int ADAPTER_STYLE_SHADE = 1;
	
	public int mWidth = LayoutParams.MATCH_PARENT;
	
	public int mHeight = LayoutParams.WRAP_CONTENT;
	
	public int style = POPUP_STYLE_ARRAY;
	
	public int arrayStyle = ADAPTER_STYLE_DEFAULT;
	
	public List<String> mDataList;

	public Map<String, String> mChildDataList;
	public IPopupOnItemClickCallback callback;
	
	public View view;
	
	public int position;
	
}
