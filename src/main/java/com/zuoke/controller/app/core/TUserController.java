package com.zuoke.controller.app.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zuoke.common.util.AlbcIMUtil;
import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.common.util.SystemParamUtil;
import com.zuoke.common.util.ValidUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.SysConstants;
import com.zuoke.model.core.TUser;
import com.zuoke.model.core.TUserinfo;
import com.zuoke.service.core.TSignHistoryService;
import com.zuoke.service.core.TUserDoService;
import com.zuoke.service.core.TUserService;
import com.zuoke.service.core.TUserinfoService;

@Controller
@RequestMapping("user")
public class TUserController {
	private Logger logger = LoggerFactory.getLogger(TUserController.class);
	@Autowired
	private TUserService tUserService;
	@Autowired
	private TUserinfoService tUserinfoService;
	@Autowired
	private TUserDoService tUserDoService;
	@Autowired
	private ValidUtil validUtil;
	@Autowired
	private AlbcIMUtil albcIMUtil;
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private  SystemParamUtil systemParamUtil;
	@Autowired
	private TSignHistoryService tSignHistoryService;

	/**
	 *  用户注册
	 * @param userid用户id
	 * @param password 用户密码
	 * @param key 密匙
	 * @param logintime 登录时间
	 * @param md5key md5加密密匙
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/register")
	@ResponseBody
	public Map<String, Object> register(String userid, String password, String key, String logintime, String md5key,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = validUtil.valid(userid, password, key, logintime, md5key);
		if (meta == null) {
			meta = new Meta();
			TUser tUser = tUserService.findByUserId(userid);
			if (tUser == null) {
				tUser=tUserService.create( userid, password);
				request.getSession().setAttribute(SysConstants.SESSIONUSER, tUser);
				meta.setRescode(0);
			} else {
				meta.setRescode(3);
				meta.setMsg("该用户已经存在，换一个用户名试试吧！");
			}
		}
		map.put("meta", meta);
		return map;

	}
	@RequestMapping(value = "/wxuser/register")
	@ResponseBody
	public Map<String, Object> register(TUser tUser,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		logger.info("sdfsd");
		String wxxcxopenid=myCacheUtil.getWxxcxOpenid();
		logger.info("wxxcxopenid:"+wxxcxopenid);
		logger.info("tUser.getOpenid():"+tUser.getOpenid());
		if (tUser.getOpenid()!=null) {
			meta = new Meta();
			TUser hasuser = tUserService.findByOpenid(tUser.getOpenid());
			if (hasuser == null) {
				tUser=tUserService.create( tUser);
				myCacheUtil.setWxxcxSessionUser(tUser);
				meta.setRescode(0);
				map.put("tUser", tUser);
			} else {
				meta.setRescode(3);
				meta.setMsg("该用户已经存在，换一个用户名试试吧！");
			}
		}else{
			meta.setRescode(2);
			meta.setMsg("用户openid不符，请重试");
		}
		map.put("meta", meta);
		return map;

	}
	
	/**
	 * 用户
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getWxxcxSessionUser/{openid}")
	@ResponseBody
	public Map<String, Object> getWxxcxSessionUser(HttpServletRequest request, HttpServletResponse response) {
		logger.info("imuser begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
			meta.setRescode(0);
			map.put("tUser", tUser);
			
		}else{
			meta.setRescode(3);
			meta.setMsg("您还没有登录");
		}
		
		map.put("meta", meta);
		return map;

	}
	/**
	 * 用户
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getCurrenUser")
	@ResponseBody
	public Map<String, Object> getCurrenUser(HttpServletRequest request, HttpServletResponse response) {
		logger.info("imuser begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
			meta.setRescode(0);
			map.put("tUser", tUser);
			
		}else{
			meta.setRescode(3);
			meta.setMsg("您还没有登录");
		}
		
		map.put("meta", meta);
		return map;

	}
	/**
	 * 完善用户资料
	 * @param tUser
	 * @param TUserinfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(TUser tUser,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		if (tUser != null) {
			meta = new Meta();
			TUser otUser = tUserService.findOne(tUser.getId());
			if (otUser != null) {
				if (!StringUtils.isEmpty(tUser.getNickname())) {
					otUser.setNickname(tUser.getNickname());
				}
				if (tUser.getGender()!=0) {
					otUser.setGender(tUser.getGender());
				}
				if (!StringUtils.isEmpty(tUser.getHeadimg())) {
					otUser.setHeadimg(tUser.getHeadimg());
				}
				if (!StringUtils.isEmpty(tUser.getProvince())) {
					otUser.setProvince(tUser.getProvince());
				}
				if (!StringUtils.isEmpty(tUser.getCity())) {
					otUser.setCity(tUser.getCity());
				}
				albcIMUtil.addUser(otUser.getId()+"", otUser.getNickname(), otUser.getMobilephone(),otUser.getHeadimg());
				otUser.setCountry(tUser.getCountry());
				tUser=tUserService.update(otUser);	
				//插入实名制用户
				//tUserinfoService.insert(tUserinfo);
				request.getSession().setAttribute(SysConstants.SESSIONUSER, tUser);
				meta.setRescode(0);
			} else {
				meta.setRescode(3);
				meta.setMsg("该用户不存在，换一个用户名试试吧！");
			}
		}
		map.put("meta", meta);
		return map;

	}
	/**
	 * 签到
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sign")
	@ResponseBody
	public Map<String, Object> sign(HttpServletRequest request, HttpServletResponse response) {
		logger.info("sign begin");
		TUser tUser=myCacheUtil.getSessionUser();
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta=new Meta();
		if (tUser!=null) {
			
			meta =tUserDoService.doSign(tUser.getId());
		}else{
			meta.setRescode(3);
			meta.setMsg("您好，您的登录信息已经过时，请重新登陆.");
		}
		map.put("meta", meta);
		return map;

	}
	/**
	 * 签到
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hassign")
	@ResponseBody
	public Map<String, Object> hassign(
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("sign begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Boolean issign=tSignHistoryService.issign();
		if (issign==null) {
			meta.setRescode(3);
			meta.setMsg("只能登录之后才可以查询是否已经签到");
		}else{
			meta.setRescode(0);
			map.put("issign", issign);
		}
		map.put("meta", meta);
		return map;

	}
	/**
	 * im用户
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/get/imuser")
	@ResponseBody
	public Map<String, Object> getImuser(HttpServletRequest request, HttpServletResponse response) {
		logger.info("imuser begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
			tUser.setImpwd(albcIMUtil.getIMPWD(tUser.getId()+""));
			meta.setRescode(0);
			map.put("tUser", tUser);
			map.put("albcappkey", systemParamUtil.getAlbcappkey());
			
		}else{
			meta.setRescode(3);
			meta.setMsg("您还没有登录，只有登录后才能跟他联系");
		}
		
		map.put("meta", meta);
		return map;

	}
	@RequestMapping(value = "/get/user/{id}")
	@ResponseBody
	public Map<String, Object> getUser(@PathVariable int id,HttpServletRequest request, HttpServletResponse response) {
		logger.info("imuser begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=tUserService.findOne(id);
		if (tUser!=null) {
			map.put("tUser", tUser);
			meta.setRescode(0);
			
		}else{
			meta.setRescode(3);
			meta.setMsg("没有找到该用户");
		}
		
		map.put("meta", meta);
		return map;

	}
	@RequestMapping(value = "/findby/openid/user/{openid}")
	@ResponseBody
	public Map<String, Object> getUser(@PathVariable String openid,HttpServletRequest request, HttpServletResponse response) {
		logger.info("imuser begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=myCacheUtil.getWxxcxSessionUser(openid);
		if (tUser==null) {
			
			tUser=tUserService.findByOpenid(openid);
			
		}
		if (tUser!=null) {
			map.put("tUser", tUser);
			meta.setRescode(0);
			
		}else{
			meta.setRescode(3);
			meta.setMsg("没有找到该用户");
		}
		
		map.put("meta", meta);
		return map;

	}
	@RequestMapping(value = "/get/users")
	@ResponseBody
	public Map<String, Object> getUsers(@RequestParam(value = "uids[]")Integer[] uids,HttpServletRequest request, HttpServletResponse response) {
		logger.info("imuser begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		List<TUser> tUsers=tUserService.findByIdIn(uids);
		if (tUsers!=null) {
			Map<String, TUser> userMap=new HashMap<String, TUser>();
			for (TUser tUser : tUsers) {
				userMap.put(tUser.getId()+"", tUser);
			}
			map.put("tUsers", tUsers);
			map.put("userMap", userMap);
			meta.setRescode(0);
			
		}else{
			meta.setRescode(3);
			meta.setMsg("没有找到该用户");
		}
		
		map.put("meta", meta);
		return map;

	}
	
}
