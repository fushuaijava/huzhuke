package com.zuoke.controller.wx;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zuoke.common.util.ConfigUtils;
import com.zuoke.common.util.HttpRequestUtil;
import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.controller.app.core.TUserController;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.TUserService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("wxlogin")
public class WxLoginController {
	private Logger logger = LoggerFactory.getLogger(WxLoginController.class);
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private TUserService tUserService;
	
	@RequestMapping(value = "/wxlogin")
	@ResponseBody
	public String wxlogin(String code){
		String appid=ConfigUtils.getString("wxxcx.appid");
		String appSecret=ConfigUtils.getString("wxxcx.appsecret");
		String request=HttpRequestUtil.httpRequest("https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code");
		JSONObject jsonObject=JSONObject.fromObject(request);
		String openid=jsonObject.getString("openid");
		String sessionkey=jsonObject.getString("session_key");
		logger.info(openid);
		TUser tUser=tUserService.findByOpenid(openid);
		if (tUser!=null) {
			myCacheUtil.setWxxcxSessionUser(tUser);
		}
		return request;
	}
	
	
	
}
