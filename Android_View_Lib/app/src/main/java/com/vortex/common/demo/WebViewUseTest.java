package com.vortex.common.demo;

import android.graphics.Bitmap;
import android.webkit.WebView;

import com.vortex.common.base.CnBaseActivity;
import com.vortex.common.entity.CnUploadUrlBean;
import com.vortex.common.manager.CnWebViewManager;

/**
 * <p>Title:WebViewUseTest.java</p>
 * <p>Description:加载WebVie测试demo</p>
 * @author Johnny.xu
 * @date 2016年12月14日
 */
public class WebViewUseTest extends CnBaseActivity {
	
	public void useWebVIew() {
		// 初始化网页管理
		CnWebViewManager manager = new CnWebViewManager(new WebView(this), new CnWebViewManager.LoadWebCallBack(){

			@Override
			public void loadStarted(WebView view, String url, Bitmap favicon) {
				
			}

			@Override
			public void loadFinished(WebView view, String url) {
				
			}

			@Override
			public void loadError(String error) {
				
			}
		});
		
		// 加载网页 两种类型都可以
		manager.loadWeb(new CnUploadUrlBean());
		manager.loadWeb("");
	}
}
