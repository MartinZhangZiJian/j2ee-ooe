package com.oned.taobao.url;

/**
 * 全局请求的url
 */
public interface Url {
	
	/**
	 * 淘宝客专用api
	 */
	String taobaoke = "taobaoke/";
	
	/**
     * TOP服务地址，正式环境需要设置为http://gw.api.taobao.com/router/rest
     * 沙箱环境   http://gw.api.tbsandbox.com/router/rest
     */
	String taobaoke_url = "http://gw.api.taobao.com/router/rest";
	
	/**
	 * 淘宝客商品查询
	 * http://open.taobao.com/doc2/apiDetail.htm?apiId=24515&scopeId=11655  
	 */
	String product_search = "product/search";
	
	/**
	 * 好券清单API【导购】
	 * http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.tD7sbS&scopeId=11655&apiId=29821
	 */
	String coupon_guide = "coupon/guide";
	
	/**
	 * 淘宝客商品关联推荐查询
	 */
	String product_recommend = "product/recommend";
	
	/**
	 * 淘抢购api
	 * # 获取淘抢购的数据，淘客商品转淘客链接，非淘客商品输出普通链接 #
	 */
	String tqg = "tqg";
	
	/**
	 * 淘宝客店铺查询
	 */
	String shop_get = "shop/get";
}
