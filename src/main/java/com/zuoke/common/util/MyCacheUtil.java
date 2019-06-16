package com.zuoke.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zuoke.model.SysConstants;
import com.zuoke.model.core.TUser;

@Repository
public class MyCacheUtil {
	@Autowired
	private SystemParamUtil systemParamUtil;
	@Autowired
	private RedisTemplateUtil redisTemplateUtil;
	private Object getValue( String key){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		 return request.getSession().getAttribute(key);
	}
	private void setValue( String key,Object value){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		  request.getSession().setAttribute(key,value);
	}
	
	private void removeValue( String key){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		  request.getSession().removeAttribute(key);
	}
	public TUser getSessionUser(){
		return (TUser)getValue(SysConstants.SESSIONUSER);
	}
	public void removeSessionUser(){
		 removeValue(SysConstants.SESSIONUSER);
	}
	public void setSessionUser(TUser tUser){
		 setValue(SysConstants.SESSIONUSER,tUser);
	}
	public String getSmscode(){
		return (String)getValue(SysConstants.SMSCODE);
	}
	public void setSmscode(String code){
		 setValue(SysConstants.SMSCODE,code);
		 setSmscodeouttime();
	}
	
	public Boolean IsSmscodeouttime(){
		Date outdate=(Date)getValue(SysConstants.SMSCODEOUTTIME);
		if (outdate!=null) {
			return outdate.before(new Date());
		}
		return true;
	}
	public void setSmscodeouttime(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MINUTE, systemParamUtil.getSmsOutTimeMinute());
		setValue(SysConstants.SMSCODEOUTTIME,calendar.getTime());
	}
	public void setSendcodeMobile(String mobile){
		 setValue(SysConstants.MOBILEPHONE,mobile);
	}
	public String getSendcodeMobile(){
		return (String)getValue(SysConstants.MOBILEPHONE);
	}
	public void setSMSADDCOINMAP(Map<String, Object> map){
		setValue(SysConstants.MOBILEPHONE,map);
	}
	public Map<String, Object> getSMSADDCOINMAP(){
		return (Map<String, Object>)getValue(SysConstants.MOBILEPHONE);
	}
	public String getHzkPhone(){
		return systemParamUtil.getHzkphone();
	}
	public String getWxxcxOpenid(){
		return (String)getValue(SysConstants.WXXCXOPENID);
	}
	public void setWxxcxOpenid(String openid){
		setValue(SysConstants.WXXCXOPENID,openid);
	}
	public String getWxxcxSessionKey(){
		return (String)getValue(SysConstants.WXXCXSESSIONKEY);
	}
	public void setWxxcxSessionKey(String wxxcxsessionkey){
		setValue(SysConstants.WXXCXSESSIONKEY,wxxcxsessionkey);
	}
	public TUser getWxxcxSessionUser(String openid){
		return (TUser)redisTemplateUtil.get(SysConstants.SESSIONUSER+openid);
	} 
	public void removeWxxcxSessionUser(String openid){
		redisTemplateUtil.delete(SysConstants.SESSIONUSER+openid);
	}
	public void setWxxcxSessionUser(TUser tUser){
		redisTemplateUtil.set(SysConstants.SESSIONUSER+tUser.getOpenid(),tUser);
	}
}
