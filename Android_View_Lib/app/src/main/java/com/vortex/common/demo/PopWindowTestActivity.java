package com.vortex.common.demo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.vortex.common.base.CnBaseActivity;
import com.vortex.common.view.pop.CustomPopupWindow;
import com.vortex.common.view.pop.IPopupOnItemClickCallback;
import com.vortex.common.view.pop.PopupParamet;

/**
 * <p>Title:PopWindowTestActivity.java</p>
 * <p>Description:弹出框的默认方式</p>
 * @author Johnny.xu
 * @date 2016年12月14日
 */
public class PopWindowTestActivity extends CnBaseActivity {
	
	private CustomPopupWindow.PopupBuild mPop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createPop();
		
		mPop.createPopup().showAsDropDown(new View(this));
	}
	
	// 一般数据数组弹出框
	private void createPop() {
		mPop = new CustomPopupWindow.PopupBuild(this)
				// 设置宽度  默认满屏
				.setWidth(200) 
				// 设置高度 默认满屏
				.setHeight(LayoutParams.WRAP_CONTENT)
				// 设置弹出框边框 默认无  或ADAPTER_STYLE_SHADE为灰色边框
				.setArrayStyle(PopupParamet.ADAPTER_STYLE_SHADE)
				// 设置数据源 可以是string []  也可以是list<String> 类型
				.setData(new String[]{})
				// 每个Item点击回调
				.setListener(new IPopupOnItemClickCallback() {
					@Override
					public void onClick(int position, String strName) {}
				});
				
	}
	
	/**
	 * 创建自定义弹出框
	 */
	public void createCustomPop() {
		// 第一个参数为显示内容、第二个参数为宽度、第三个参数为高度
		CustomPopupWindow.getInstance(mContext, new View(this), 200, 200);
	}
}
