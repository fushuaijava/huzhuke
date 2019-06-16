package com.zuoke.controller.app.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zuoke.common.util.AlbcIMUtil;
import com.zuoke.common.util.MD5Util;
import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.common.util.ValidUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.TUserService;

@Controller
@RequestMapping("login")
public class TUserLoginController {
	private Logger logger = LoggerFactory.getLogger(TUserLoginController.class);

	@Autowired
	private TUserService tUserService;
	@Autowired
	private ValidUtil validUtil;
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private AlbcIMUtil albcIMUtil;
	/**
	 *  用户登录
	 * @param userid用户id
	 * @param password 用户密码
	 * @param key 密匙
	 * @param logintime 登录时间
	 * @param md5key md5加密密匙
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Map<String, Object> login(String userid, String password, String key, String logintime, String md5key,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =validUtil.valid(userid, password, key, logintime, md5key);
		if (meta == null) {
			meta = new Meta();
			TUser tUser = tUserService.findByUserId(userid);
			if (tUser != null) {
				if (tUser.getHasImUser()==0) {
					albcIMUtil.addUser(tUser.getId()+"", tUser.getNickname(), tUser.getMobilephone());
					tUser.setHasImUser(1);
					tUserService.update(tUser);
				}
				//if ( tUser.getPassword().equals(MD5Util.MD5(password))) {
				//登录传递的加密过的密码
				if ( tUser.getPassword().equals(password)) {
					meta.setRescode(0);
					myCacheUtil.setSessionUser(tUser);
					map.put("tUser", tUser);
				}else{
					meta.setRescode(3);
					meta.setMsg("用户名或密码错误，请确认您输入的用户名密码是否正确！");
				}
			} else {
				meta.setRescode(3);
				meta.setMsg("没有找到该用户，请确认您输入的用户名密码是否正确！");
			}
		}
		map.put("meta", meta);
		return map;

	}
	/**
	 * 登录退出
	 * @param request
	 * @param response
	 * @return 返回成功
	 */
	@RequestMapping(value = "/loginout")
	@ResponseBody
	public Map<String, Object> loginout(HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		meta.setRescode(0);
		myCacheUtil.removeSessionUser();
		map.put("meta", meta);
		return map;

	}
	/**
	 *  用户短信登录
	 * @param userid用户id
	 * @param password 用户密码
	 * @param key 密匙
	 * @param logintime 登录时间
	 * @param md5key md5加密密匙
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/smslogin")
	@ResponseBody
	public Map<String, Object> smslogin(String userid, String password, String key, String logintime, String md5key,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		//校验短信密码是否有效
		Meta meta =validUtil.validSms(userid, password, key, logintime, md5key);
		if (meta == null) {
			meta = new Meta();
			TUser tUser = tUserService.findByUserId(userid);
			if (tUser == null) {
				tUser=tUserService.create(userid, MD5Util.MD5(password));
			}
			meta.setRescode(0);
			myCacheUtil.setSessionUser(tUser);
			map.put("tUser", tUser);
		}
		map.put("meta", meta);
		return map;

	}
	 

}
