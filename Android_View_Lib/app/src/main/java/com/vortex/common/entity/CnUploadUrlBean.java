package com.vortex.common.entity;

import com.vortex.common.util.StringUtils;

import java.io.Serializable;

/**
 * 加载Web链接bean文件
 * @author Johnny.xu
 */
public class CnUploadUrlBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String urlDefault;
	
	/**
	 *  初始连接 正常为网址加端口号  如：10.10.10.206:8080   但如果都是有默认参数也可以加上
	 */
	public String url;
	
	/**
	 * 一般为全部参数 单也可以是异同的参数
	 */
	public String param;
	
	public String getUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append(StringUtils.isEmpty(url) ? urlDefault : url);
		
		if (!StringUtils.isEmpty(param)) {
			if(param.startsWith("&")) {
				sb.append(param);
			} else {
				sb.append("&").append(param);
			}
		}
		String url = sb.toString();
		if (!url.contains("?")) {
			url = url.replaceFirst("&", "?");
		}
		url = url.replace("&&", "&").replace("?&", "?").replace("/?", "?");
		return url;
	}

}
