package com.zuoke.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BizResult;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.zuoke.model.Meta;
@Repository
public class SendSMSUtil {
	private static  Logger logger = LoggerFactory.getLogger(SystemParamUtil.class);
	@Autowired
	private AldySmsParamUtil aldySmsParamUtil;
	public Meta sendLoginSmS(String mobile,String code,String userId){
		Meta meta=new Meta();
		logger.info("sendLoginSmS mobile:"+mobile+"  code"+code);
		//TaobaoClient client = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest", "23290617", "bff41313c6263af963bfaba656161d4b");
		TaobaoClient client = new DefaultTaobaoClient(aldySmsParamUtil.getServerurl(), aldySmsParamUtil.getAppkey(), aldySmsParamUtil.getAppsecret());
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(userId);
		req.setSmsType("normal");
		req.setSmsFreeSignName("互助客");
		req.setSmsParamString("{\"code\":\""+code+"\",\"product\":\"互助客\"}");
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_3865278");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			if (rsp!=null) {
				BizResult result=rsp.getResult();
				if (result!=null&&result.getSuccess()) {
					if (result.getSuccess()) {
						meta.setRescode(0);
						meta.setMsg("验证码已经发送到您的手机，请注意查收。");
						return meta;
					}
				}
				logger.info(rsp.getBody());
			}else{
				logger.info("网络异常，rsp返回为空");
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		meta.setRescode(3);
		meta.setMsg("验证码发送失败，请您稍后重试");
		return meta;
	}
	public Meta helpfinishSMS(String mobile,String code,String userId){
		Meta meta=new Meta();
		logger.info("helpfinishSMS mobile:"+mobile+"  code"+code);
		//TaobaoClient client = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest", "23290617", "bff41313c6263af963bfaba656161d4b");
		TaobaoClient client = new DefaultTaobaoClient(aldySmsParamUtil.getServerurl(), aldySmsParamUtil.getAppkey(), aldySmsParamUtil.getAppsecret());
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(userId);
		req.setSmsType("normal");
		req.setSmsFreeSignName("互助客");
		req.setSmsParamString("{\"code\":\""+code+"\"}");
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_5920047");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			if (rsp!=null) {
				BizResult result=rsp.getResult();
				if (result!=null&&result.getSuccess()) {
					if (result.getSuccess()) {
						meta.setRescode(0);
						meta.setMsg("验证码已经发送到您的手机，请注意查收。");
						return meta;
					}
				}
				logger.info(rsp.getBody());
			}else{
				logger.info("网络异常，rsp返回为空");
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		meta.setRescode(3);
		meta.setMsg("验证码发送失败，请您稍后重试");
		return meta;
	}
	public Meta guestOrderPrePaySmS(String mastername,String guestname,String mobile,String userId){
		Meta meta=new Meta();
		//TaobaoClient client = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest", "23290617", "bff41313c6263af963bfaba656161d4b");
		TaobaoClient client = new DefaultTaobaoClient(aldySmsParamUtil.getServerurl(), aldySmsParamUtil.getAppkey(), aldySmsParamUtil.getAppsecret());
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(userId);
		req.setSmsType("normal");
		req.setSmsFreeSignName("互助客");
		req.setSmsParamString("{\"mastername\":\""+mastername+"\",\"guestname\":\""+guestname+"\"}");
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_7235998");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			if (rsp!=null) {
				BizResult result=rsp.getResult();
				if (result!=null&&result.getSuccess()) {
					if (result.getSuccess()) {
						meta.setRescode(0);
						meta.setMsg("验证码已经发送到您的手机，请注意查收。");
						return meta;
					}
				}
				logger.info(rsp.getBody());
			}else{
				logger.info("网络异常，rsp返回为空");
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		meta.setRescode(3);
		meta.setMsg("验证码发送失败，请您稍后重试");
		return meta;
	}
	public Meta guestOrderFinishSmS(String code,String hzkphone,String mobile,String userId){
		Meta meta=new Meta();
		logger.info("guestOrderFinishSmS mobile:"+mobile+"  code"+code);
		//TaobaoClient client = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest", "23290617", "bff41313c6263af963bfaba656161d4b");
		TaobaoClient client = new DefaultTaobaoClient(aldySmsParamUtil.getServerurl(), aldySmsParamUtil.getAppkey(), aldySmsParamUtil.getAppsecret());
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(userId);
		req.setSmsType("normal");
		req.setSmsFreeSignName("互助客");
		req.setSmsParamString("{\"code\":\""+code+"\",\"hzkphone\":\""+hzkphone+"\"}");
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_5870056");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			if (rsp!=null) {
				BizResult result=rsp.getResult();
				if (result!=null&&result.getSuccess()) {
					if (result.getSuccess()) {
						meta.setRescode(0);
						meta.setMsg("验证码已经发送到您的手机，请注意查收。");
						return meta;
					}
				}
				logger.info(rsp.getBody());
			}else{
				logger.info("网络异常，rsp返回为空");
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		meta.setRescode(3);
		meta.setMsg("验证码发送失败，请您稍后重试");
		return meta;
	}
	public Meta guestOrderAddCoinSmS(String code,String orderno,Integer coin,String mobile,String userId){
		Meta meta=new Meta();
		logger.info("guestOrderAddCoinSmS mobile:"+mobile+"  code"+code);
		//TaobaoClient client = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest", "23290617", "bff41313c6263af963bfaba656161d4b");
		TaobaoClient client = new DefaultTaobaoClient(aldySmsParamUtil.getServerurl(), aldySmsParamUtil.getAppkey(), aldySmsParamUtil.getAppsecret());
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(userId);
		req.setSmsType("normal");
		req.setSmsFreeSignName("互助客");
		req.setSmsParamString("{\"code\":\""+code+"\",\"orderno\":\""+orderno+"\",\"coin\":\""+coin+"\"}");
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_5890020");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			if (rsp!=null) {
				BizResult result=rsp.getResult();
				if (result!=null&&result.getSuccess()) {
					if (result.getSuccess()) {
						meta.setRescode(0);
						meta.setMsg("验证码已经发送到您的手机，请注意查收。");
						return meta;
					}
				}
				logger.info(rsp.getBody());
			}else{
				logger.info("网络异常，rsp返回为空");
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		meta.setRescode(3);
		meta.setMsg("验证码发送失败，请您稍后重试");
		return meta;
	}
	public Meta guestOrderStartSmS(String code,String mobile,String userId){
		Meta meta=new Meta();
		logger.info("guestOrderStartSmS mobile:"+mobile+"  code"+code);
		//TaobaoClient client = new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest", "23290617", "bff41313c6263af963bfaba656161d4b");
		TaobaoClient client = new DefaultTaobaoClient(aldySmsParamUtil.getServerurl(), aldySmsParamUtil.getAppkey(), aldySmsParamUtil.getAppsecret());
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(userId);
		req.setSmsType("normal");
		req.setSmsFreeSignName("互助客");
		req.setSmsParamString("{\"code\":\""+code+"\"}");
		req.setRecNum(mobile);
		req.setSmsTemplateCode("SMS_5880064");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			if (rsp!=null) {
				BizResult result=rsp.getResult();
				if (result!=null&&result.getSuccess()) {
					if (result.getSuccess()) {
						meta.setRescode(0);
						meta.setMsg("验证码已经发送到您的手机，请注意查收。");
						return meta;
					}
				}
				logger.info(rsp.getBody());
			}else{
				logger.info("网络异常，rsp返回为空");
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		meta.setRescode(3);
		meta.setMsg("验证码发送失败，请您稍后重试");
		return meta;
	}

}
