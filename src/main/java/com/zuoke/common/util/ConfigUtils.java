package com.zuoke.common.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigUtils 
{
	
	/**
     * 配置文件信息
     */
	private static ResourceBundle bundle = ResourceBundle.getBundle("config/config");

	/**
	 * 构造函数
	 */
	public ConfigUtils() {
		super();
	}
	
	/**
     * 读取配置文件信息
     * @param key
     * @return String value
     */
    public static String getString(String key)  {
        if (key == null || key.equals("") || key.equals("null"))  {
            return "";
        }
        String result = "";
        try {
            result = bundle.getString(key);
        }  catch (MissingResourceException e) {
            e.printStackTrace();
            System.out.println("配置文件中信息错误：file.properties中" + key + "不存在！");
        }
        
        return result;
    }
	
}
