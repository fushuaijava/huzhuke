package com.zuoke.model;

import javax.servlet.http.HttpServletRequest;

public class SysConstants {
	public static final String SESSIONUSER="SESSIONUSER";
	public static final String MOBILEPHONE="MOBILEPHONE";
	public static final String SMSCODE="SMSCODE";
	public static final String SMSCODEOUTTIME="SMSCODEOUTTIME";
	public static final String SMSADDCOINMAP="SMSADDCOINMAP";
	public static final String WXXCXOPENID="WXXCXOPENID";
	public static final String WXXCXSESSIONKEY="WXXCXSESSONKEY";
	
	public static final int OUTMINATE=10;
	public static String getRemortIP(HttpServletRequest request) {  
	    if (request.getHeader("x-forwarded-for") == null) {  
	        return request.getRemoteAddr();  
	    }  
	    return request.getHeader("x-forwarded-for");  
	}

}
