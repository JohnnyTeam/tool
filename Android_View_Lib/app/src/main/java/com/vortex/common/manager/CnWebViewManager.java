package com.vortex.common.manager;

import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vortex.common.entity.CnUploadUrlBean;
import com.vortex.common.util.StringUtils;

/**
 * <p>Title:WebViewManager.java</p>
 * <p>Description:web加载管理类</p>
 * @author Johnny.xu
 * @date 2016年10月27日
 */
public class CnWebViewManager {
	
	private WebView mWebView;
	
	public CnWebViewManager (WebView webView, LoadWebCallBack callBack) {
		this.mWebView = webView;
		this.mCallBack = callBack;
		initWebView();
	} 
	
	public void initWebView() {
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setPluginState(PluginState.ON);
		mWebView.setWebChromeClient(new WebChromeClient());
		mWebView.getSettings().setBlockNetworkImage(false);
		// html的图片就会以单列显示就不会变形占了别的位置
		mWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		mWebView.getSettings().setSupportZoom(false);
		mWebView.getSettings().setAppCacheEnabled(true);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setSaveFormData(false);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.getSettings().setUseWideViewPort(true);//关键点  
		
	}
	
	private CnUploadUrlBean mLoadBean;
	
	/**
	 * 加载网页
	 */
	public void loadWeb(CnUploadUrlBean bean) {
		this.mLoadBean = bean;
		String url = mLoadBean == null ? "" : mLoadBean.getUrl();
		if (!StringUtils.isEmpty(url)) {
			mWebView.loadUrl(url);
		} else {
			if (mCallBack != null) {
				mCallBack.loadError("url出错");
			}
		}
	}
	
	/**
	 * 加载网页
	 */
	public void loadWeb(String url) {
		if (!StringUtils.isEmpty(url)) {
			mWebView.loadUrl(url);
		} else {
			if (mCallBack != null) {
				mCallBack.loadError("url出错");
			}
		}
	}
	
	private LoadWebCallBack mCallBack;
	
	public interface LoadWebCallBack {
		void loadStarted(WebView view, String url, Bitmap favicon);
		void loadFinished(WebView view, String url);
		void loadError(String error);
	}
	
	private class MyWebViewClient extends WebViewClient {
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			if (mCallBack != null) {
				mCallBack.loadStarted(view, url, favicon);
			}
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (mCallBack != null) {
				mCallBack.loadFinished(view, url);
			}
		}
		
		@Override
		public void onReceivedError(WebView view, int errorCode,
									String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			if (mCallBack != null) {
				mCallBack.loadError("加载网页失败!");
			}
		}
		
	} 
}
