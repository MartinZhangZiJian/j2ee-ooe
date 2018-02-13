package com.oned.taobao.url;

/**
 * 全局请求的url
 */
public interface Url {
	/**
	 * 通用词汇
	 */
	String product = "product";  // 产品
	String search = "search";  // 搜索

	/**
	 * 淘宝客专用api
	 */
	String taobaoke = "taobaoke";
	
	/**
     * TOP服务地址，正式环境需要设置为http://gw.api.taobao.com/router/rest
     * 沙箱环境   http://gw.api.tbsandbox.com/router/rest
     */
	String taobaoke_url = "http://gw.api.taobao.com/router/rest";
	
}
