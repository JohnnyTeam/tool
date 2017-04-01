package com.vortex.common.view.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.vortex.common.library.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:CustomPopupWindow.java</p>
 * <p>Description:自定义弹出层</p>
 * @author Johnny.xu
 * @date 2016年12月6日
 */
public class CustomPopupWindow {
	
	public static class PopupBuild {
		
        private final PopupParamet mParameter;
        private PopupWindow mPopupWindow;
        public Context mContext;
        
        public PopupBuild(Context context) {
        	mParameter = new PopupParamet();
        	mContext = context;
        } 
        
        public PopupBuild setWidth(int width) {
        	mParameter.mWidth = width;
        	return this;
        } 
        
        public PopupBuild setHeight(int height) {
        	mParameter.mHeight = height;
        	return this;
        } 
        
        public PopupBuild setStyle(int style) {
        	mParameter.style = style;
        	return this;
        } 
        public PopupBuild setArrayStyle(int style) {
        	mParameter.arrayStyle = style;
        	return this;
        } 
        
        public PopupBuild setView(View view) {
        	mParameter.view = view;
        	mParameter.style = PopupParamet.POPUP_STYLE_CUSTOM;
        	return this;
        } 
        
        public PopupBuild setData(List<String> list) {
			mParameter.mDataList = list;
			return this;
		}
		public PopupBuild setSecondData(Map<String, String> list) {
			mParameter.mChildDataList = list;
			return this;
		}
        public PopupBuild setData(String[] list) {
        	if (list != null && list.length > 0) {
        		mParameter.mDataList = new ArrayList<String>();
        		for(String str : list) {
        			mParameter.mDataList.add(str);
        		}
        	}
        	return this;
        }

        public PopupBuild setListener(IPopupOnItemClickCallback callback) {
			mParameter.callback = callback;
			return this;
		}
        
        public PopupWindow createPopup() {
        	if (mPopupWindow == null) {
        		if (mParameter.view == null) {
        			if (mParameter.arrayStyle == PopupParamet.ADAPTER_STYLE_DEFAULT) {
        				mParameter.view = View.inflate(mContext, R.layout.cn_pop_default_array_view, null);
        			} else if (mParameter.arrayStyle == PopupParamet.ADAPTER_STYLE_SHADE) {
        				mParameter.view = View.inflate(mContext, R.layout.cn_pop_shade_array_view, null);
        			} else {
        				mParameter.view = View.inflate(mContext, R.layout.cn_pop_default_array_view, null);
        			}
            		ListView listView = (ListView)mParameter.view.findViewById(R.id.cn_lv_pop_list);
            		mParameter.view.setLayoutParams(new LayoutParams(mParameter.mWidth, mParameter.mHeight));
            		PopupDefaultAdapter adapter = new PopupDefaultAdapter(mContext);
            		adapter.addAllData(mParameter.mDataList);
            		listView.setAdapter(adapter);
            		listView.setOnItemClickListener(new OnItemClickListener() {

    					@Override
    					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
    						mParameter.position = position;
    						if (mParameter.callback != null) {
    							mParameter.callback.onClick(position, mParameter.mDataList.get(position));
    						}
    						if (mPopupWindow.isShowing()) {
    							mPopupWindow.dismiss();
    						}
    					}
    				});
            	}
            	mPopupWindow = getInstance(mContext, mParameter.view, mParameter.mWidth, mParameter.mHeight);
        	}
        	return mPopupWindow;
        }
        
        public int getPosition() {
        	return mParameter.mDataList == null || mParameter.mDataList.size() == 0 ? -1 :mParameter.position;
        }

	}
	
	/**
	 * 获取pop弹出层
	 * @param view
	 * @param width  使用ViewGroup.LayoutParams
	 * @param height 使用ViewGroup.LayoutParams
	 */
	public static PopupWindow getInstance(Context context, View view, int width, int height) {
		if (view == null) {
			throw new NullPointerException("弹出层的内容页面不能为空！");
		}
		PopupWindow pop = new PopupWindow(view, width, height, true);
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
		pop.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cn_shape_bg_cover_default));
		return pop;
	}
}
