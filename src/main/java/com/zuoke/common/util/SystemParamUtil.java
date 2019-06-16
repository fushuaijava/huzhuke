package com.zuoke.common.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

/**
 * 系统参数数据表.
 * @author wy
 *
 */
@Repository
public class SystemParamUtil {
	private static  Logger logger = LoggerFactory.getLogger(SystemParamUtil.class);
	
	@Autowired
	private  ConfigUtil configUtil;
	
	private final static String SYSTEM = "system";
	private final static String keyouttimeminuteName = "keyouttimeminute";
	private final static String uploaddirName = "uploaddir";
	private final static String imghttpName = "imghttp";
	private final static String ewmdirName= "ewmdir";
	private final static String payEwmDirName="payewmdirname";
	private final static String mtticketSearchTime="mtticketsearchtime";
	private final static String smsOutTimeMinute="smsouttimeminute";
	private final static String signSendLevel="signSendLevel";
	private final static String signSendCoin="signSendCoin";
	private final static String hzkphone="hzkphone";
	private final static String payproducturl="payproducturl";
	private final static String albcappkey="albcappkey";
	private final static String albcappSecret="albcappSecret";
	private final static String duibaappkey="duibaappkey";
	private final static String duibaappSecret="duibaappSecret";
	private final static String duibaloginredirecturl="duibaloginredirecturl";
	private final static String setUserInfoSendCoin="setUserInfoSendCoin";
	private final static String sethouseInfoSendCoin="sethouseInfoSendCoin";
	private final static String wyyxappkey="wyyxappkey";
	private final static String wyyxappsecret="wyyxappsecret";
	private final static String wyyxcreateurl="wyyxcreateurl";
	private final static String wyyxupdateurl="wyyxupdateurl";
	private final static String wyyxtokenurl="wyyxtokenurl";

	/**
	 * @return key缓存超时时间
	 */
	public Date getmtticketSearchTime(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DATE, -NumberUtils.parseNumber(configUtil.getValue(SYSTEM, mtticketSearchTime), Integer.class));
		return calendar.getTime();
	}
	/**
	 * @return key缓存超时时间
	 */
	public int getKeyouttimeminute(){
		return NumberUtils.parseNumber(configUtil.getValue(SYSTEM, keyouttimeminuteName), Integer.class);
	}
	
	/**
	 * @return 二维码文件夹
	 */
	public String getEwmdir(){
		return configUtil.getValue(SYSTEM, ewmdirName);
	}
	public String getPayEwmDirName(){
		return configUtil.getValue(SYSTEM, payEwmDirName);
	}
	public String getHzkphone(){
		return configUtil.getValue(SYSTEM, hzkphone);
	}
	
	/**
	 * @return 文件上传文件夹路径
	 */
	public String getUploaddir(){
		return configUtil.getValue(SYSTEM, uploaddirName);
	}
	
	/**
	 * @return 图片http链接前缀
	 */
	public String getImghttp(){
		return configUtil.getValue(SYSTEM, imghttpName);
	}
	/**
	 * @return key缓存超时时间
	 */
	public int getSmsOutTimeMinute(){
		return NumberUtils.parseNumber(configUtil.getValue(SYSTEM, smsOutTimeMinute), Integer.class);
	}
	/**
	 * @return 签到赠送用户经验值
	 */
	public int getSignSendLevel(){
		return NumberUtils.parseNumber(configUtil.getValue(SYSTEM, signSendLevel), Integer.class);
	}
	/**
	 * @return 签到赠送用户经验值
	 */
	public int getSignSendCoin(){
		return NumberUtils.parseNumber(configUtil.getValue(SYSTEM, signSendCoin), Integer.class);
	}
	/**
	 * @return 完善用户资料送金币值
	 */
	public int getUserInfoSendCoin(){
		return NumberUtils.parseNumber(configUtil.getValue(SYSTEM, setUserInfoSendCoin), Integer.class);
	}
	/**
	 * @return 用户邀请做客送金币值
	 */
	public int gethouseInfoSendCoin(){
		return NumberUtils.parseNumber(configUtil.getValue(SYSTEM, sethouseInfoSendCoin), Integer.class);
	}
	/**
	 *  订单号
	 * @return
	 */
	public String getOrderNo(){
		return DateUtil.DateToString(new Date(),"yyyyMMdd")+RandomUtils.nextInt(99999999);
	}
	/**
	 * @return   支付返回参评地址
	 */
	public String getpayproducturl(){
		return configUtil.getValue(SYSTEM, payproducturl);
	}
	/**
	 * @return 阿里百川appkey
	 */
	public String getAlbcappkey(){
		return configUtil.getValue(SYSTEM, albcappkey);
	}
	/**
	 * @return 阿里百川appSecret
	 */
	public String getAlbcappSecret(){
		return configUtil.getValue(SYSTEM, albcappSecret);
	}
	/**
	 * @return  兑吧appkey
	 */
	public String getDuibaappkey(){
		return configUtil.getValue(SYSTEM, duibaappkey);
	}
	/**
	 * @return 兑吧appSecret
	 */
	public String getDuibaappSecret(){
		return configUtil.getValue(SYSTEM, duibaappSecret);
	}
	/**
	 * @return 兑吧登录跳转页面
	 */
	public String getDuibaloginredirecturl(){
		return configUtil.getValue(SYSTEM, duibaloginredirecturl);
	}
	/**
	 * @return 网易云信appkey
	 */
	public String getWyyxappkey(){
		return configUtil.getValue(SYSTEM, wyyxappkey);
	}
	/**
	 * @return 网易云信wyyxappsecret
	 */
	public String getWyyxappsecret(){
		return configUtil.getValue(SYSTEM, wyyxappsecret);
	}
	/**
	 * @return 网易云信wyyxcreateurl
	 */
	public String getWyyxcreateurl(){
		return configUtil.getValue(SYSTEM, wyyxcreateurl);
	}
	/**
	 * @return 网易云信wyyxupdateurl
	 */
	public String getWyyxupdateurl(){
		return configUtil.getValue(SYSTEM, wyyxupdateurl);
	}
	/**
	 * @return 网易云信wyyxtokenurl
	 */
	public String getWyyxtokenurl(){
		return configUtil.getValue(SYSTEM, wyyxtokenurl);
	}
}
