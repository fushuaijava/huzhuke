package com.zuoke.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 阿里大鱼短信参数数据表.
 * @author wy
 *
 */
@Repository
public class AldySmsParamUtil {
	private static  Logger logger = LoggerFactory.getLogger(AldySmsParamUtil.class);
	
	@Autowired
	private  ConfigUtil configUtil;
	
	private final static String aldysms = "aldysms";
	private final static String serverUrl="serverUrl";
	private final static  String appKey="appKey";
	private final static  String appSecret="appSecret";
	public static Logger getLogger() {
		return logger;
	}
	public  String getServerurl() {
		return configUtil.getValue(aldysms, serverUrl);
	}
	public  String getAppkey() {
		return configUtil.getValue(aldysms, appKey);
	}
	public  String getAppsecret() {
		return configUtil.getValue(aldysms, appSecret);
	}
	public ConfigUtil getConfigUtil() {
		return configUtil;
	}
	public void setConfigUtil(ConfigUtil configUtil) {
		this.configUtil = configUtil;
	}
	
}
