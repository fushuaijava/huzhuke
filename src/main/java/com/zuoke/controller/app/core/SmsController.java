package com.zuoke.controller.app.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.common.util.SendSMSUtil;
import com.zuoke.common.util.ValidUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TGuestOrder;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.TGuestOrderService;

@Controller
@RequestMapping("sms")
public class SmsController {
	private Logger logger = LoggerFactory.getLogger(SmsController.class);
	
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private ValidUtil validUtil;
	@Autowired
	private SendSMSUtil sendSMSUtil;
	@Autowired
	private TGuestOrderService tGuestOrderService;
	

	/**
	 *  发送短信验证码
	 * @param userid用户id
	 * @param password 用户短信
	 * @param key 密匙
	 * @param logintime 登录时间
	 * @param md5key md5加密密匙
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/send")
	@ResponseBody
	public Map<String, Object> send(String userid, String password, String key, String logintime, String md5key,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		//Meta meta =null;
		Meta meta =validUtil.valid(userid, password, key, logintime, md5key);
		if (meta==null) {
			String code=""+RandomUtils.nextInt(999999);
			
			
			//正常运行
			meta =sendSMSUtil.sendLoginSmS(userid, code, userid);
			
			//测试代码
//			meta=new Meta();
//			meta.setRescode(0);
//			meta.setMsg("测试验证码"+code);
			//测试代码
			logger.info(meta.getMsg());
			if (0==meta.getRescode()) {
				myCacheUtil.setSmscode(code);
				myCacheUtil.setSendcodeMobile(userid);
			}
		}
		map.put("meta", meta);
		return map;

	}
	/**
	 *  发送短信验证码
	 * @param userid用户id
	 * @param password 用户短信
	 * @param key 密匙
	 * @param logintime 登录时间
	 * @param md5key md5加密密匙
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/smshelpfinish")
	@ResponseBody
	public Map<String, Object> smsHelpFinish(String userid, String password, String key, String logintime, String md5key,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =null;
		TUser tUser=myCacheUtil.getSessionUser();
		//Meta meta =validUtil.valid(userid, password, key, logintime, md5key);
		if (meta==null) {
			String code=""+RandomUtils.nextInt(999999);
			//正常运行
			meta =sendSMSUtil.helpfinishSMS(tUser.getMobilephone(), code, tUser.getMobilephone());
			//测试代码
			/*meta=new Meta();
			meta.setRescode(0);
			meta.setMsg("测试验证码"+code);*/
			logger.info(meta.getMsg());
			if (0==meta.getRescode()) {
				myCacheUtil.setSmscode(code);
				myCacheUtil.setSendcodeMobile(tUser.getMobilephone());
			}
		}
		map.put("meta", meta);
		return map;

	}
	/**
	 *  发送短信验证码
	 * @param userid用户id
	 * @param password 用户短信
	 * @param key 密匙
	 * @param logintime 登录时间
	 * @param md5key md5加密密匙
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/smsvisitstart")
	@ResponseBody
	public Map<String, Object> smsVisitStart(int guestOrderId,HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		//Meta meta =null;
		Meta meta =new Meta();
		TGuestOrder tGuestOrder=tGuestOrderService.get(guestOrderId);
		if (tGuestOrder!=null&&tGuestOrder.getGuestUser()!=null&&!StringUtils.isEmpty(tGuestOrder.getGuestUser().getMobilephone())) {
			String code=""+RandomUtils.nextInt(999999);
			//正常运行
			meta =sendSMSUtil.guestOrderStartSmS(code, tGuestOrder.getGuestUser().getMobilephone(), tGuestOrder.getGuestUser().getMobilephone());
			//测试代码
			/*meta=new Meta();
			meta.setRescode(0);
			meta.setMsg("测试验证码"+code);*/
			logger.info(meta.getMsg());
			if (0==meta.getRescode()) {
				myCacheUtil.setSmscode(code);
				myCacheUtil.setSendcodeMobile(tGuestOrder.getGuestUser().getMobilephone());
			}
		}else{
			meta.setRescode(3);
			meta.setMsg("无法找到您登陆的手机号，请您确认您登录的信息是否正确");
		}
		map.put("meta", meta);
		return map;

	}
	@RequestMapping(value = "/smscostadd")
	@ResponseBody
	public Map<String, Object> smsCostAdd(int guestOrderId,Integer coin,HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		//Meta meta =null;
		Meta meta =new Meta();
		TGuestOrder tGuestOrder=tGuestOrderService.get(guestOrderId);
		if (tGuestOrder!=null&&tGuestOrder.getGuestUser()!=null&&!StringUtils.isEmpty(tGuestOrder.getGuestUser().getMobilephone())) {
			String code=""+RandomUtils.nextInt(999999);
			//正常运行
			meta =sendSMSUtil.guestOrderAddCoinSmS(code, tGuestOrder.getId()+"", coin, tGuestOrder.getGuestUser().getMobilephone(), tGuestOrder.getGuestUser().getMobilephone());
			//测试代码
			/*meta=new Meta();
			meta.setRescode(0);
			meta.setMsg("测试验证码"+code);*/
			logger.info(meta.getMsg());
			if (0==meta.getRescode()) {
				myCacheUtil.setSmscode(code);
				myCacheUtil.setSendcodeMobile(tGuestOrder.getGuestUser().getMobilephone());
			}
		}else{
			meta.setRescode(3);
			meta.setMsg("无法找到您登陆的手机号，请您确认您登录的信息是否正确");
		}
		map.put("meta", meta);
		return map;

	}
	@RequestMapping(value = "/smsvisitfinish")
	@ResponseBody
	public Map<String, Object> smsVisitFinish(int guestOrderId,HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		//Meta meta =null;
		Meta meta =new Meta();
		TGuestOrder tGuestOrder=tGuestOrderService.get(guestOrderId);
		if (tGuestOrder!=null&&tGuestOrder.getGuestUser()!=null&&!StringUtils.isEmpty(tGuestOrder.getGuestUser().getMobilephone())) {
			String code=""+RandomUtils.nextInt(999999);
			//正常运行
			meta =sendSMSUtil.guestOrderFinishSmS(code, myCacheUtil.getHzkPhone(), tGuestOrder.getGuestUser().getMobilephone(), tGuestOrder.getGuestUser().getMobilephone());
			//测试代码
			/*meta=new Meta();
			meta.setRescode(0);
			meta.setMsg("测试验证码"+code);*/
			logger.info(meta.getMsg());
			if (0==meta.getRescode()) {
				myCacheUtil.setSmscode(code);
				myCacheUtil.setSendcodeMobile(tGuestOrder.getGuestUser().getMobilephone());
			}
		}else{
			meta.setRescode(3);
			meta.setMsg("无法找到您登陆的手机号，请您确认您登录的信息是否正确");
		}
		map.put("meta", meta);
		return map;

	}
	
}
