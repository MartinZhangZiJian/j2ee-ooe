package com.oned.taobao.utils;
/**
 * 存放关键秘钥
 */
public class Key {
	
	/**
	 * 淘宝客
	 * session: 6102516b0695d2d315d33b411849b0883551dddb1d05b3b441359894
	 * refresh_token: 6102616205c7b9610504b7a1188c74ee764479e4f80a87a441359894
	 * 可替换为您的沙箱环境应用的appKey
	 * 可替换为您的沙箱环境应用的appSecret
	 * 必须替换为沙箱账号授权得到的真实有效sessionKey
	 * 
	 * 关于 pid  mm_memberId_siteId_adzoneId
	 * http://club.alimama.com/read.php?ds=1&page=1&tid=6306396&toread=1&ucmidtm=1506339923.75
	 */
	private static final String appKey = "24644982";
	private static final String appSecret = "adfd12eca8686b6fb72b46abc32745ef";
	private static final String sessionKey = "6102516b0695d2d315d33b411849b0883551dddb1d05b3b441359894";
	private static final String pid = "mm_127353326_37942117_138738639";
	private static final Long adzoneId = 138738639L;
	
	public static String getAppKey() {
		return appKey;
	}

	public static final String getAppSecret() {
		return appSecret;
	}
	
	public static final String getSessionKey() {
		return sessionKey;
	}
	
	public static final String getPid() {
		return pid;
	}
	
	public static final Long getAdzoneId() {
		return adzoneId;
	}
}
