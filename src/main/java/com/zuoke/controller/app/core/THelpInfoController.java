package com.zuoke.controller.app.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.SysConstants;
import com.zuoke.model.core.THelpInfo;
import com.zuoke.model.core.THelpInfoReply;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.THelpInfoService;
import com.zuoke.service.core.THelpInfoReplyService;

@Controller
@RequestMapping("helpinfo")
public class THelpInfoController {
	private Logger logger = LoggerFactory.getLogger(THelpInfoController.class);

	@Autowired
	private THelpInfoService tHelpInfoService;
	@Autowired
	private THelpInfoReplyService tHelpInfoReplyService;
	@Autowired
	private MyCacheUtil myCacheUtil;

	/**
	 * 添加帮助
	 * 
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> add(THelpInfo tHelpInfo, HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		return tHelpInfoService.createHelpInfo(tHelpInfo);
	}

	/**
	 * 修改帮助
	 * 
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(THelpInfo tHelpInfo, THelpInfoReply tHelpInfoReply, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		TUser tUser = (TUser) request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tHelpInfo.settUser(tUser);
		tHelpInfo = tHelpInfoService.update(tHelpInfo);
		// tHelpInfoReply.setHelpInfoId(tHelpInfo.getId());
		// tHelpInfoReplyService.update(tHelpInfoReply);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tHelpInfo", tHelpInfo);
		return map;

	}

	/**
	 * 修改帮助
	 * 
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updatestate")
	@ResponseBody
	public Map<String, Object> updatestate(THelpInfo tHelpInfo, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		// TUser
		// tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tHelpInfo = tHelpInfoService.updatestate(tHelpInfo);
		if (tHelpInfo != null) {
			meta.setRescode(0);
			map.put("tHelpInfo", tHelpInfo);
		} else {
			meta.setRescode(3);
			meta.setMsg("您好，您传递的参数异常，请重试！");
		}
		map.put("meta", meta);
		return map;

	}

	@RequestMapping(value = "/helpsendcoin")
	@ResponseBody
	public Map<String, Object> helpSendCoin( int id, String smscode, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		THelpInfo helpInfo = tHelpInfoService.findOne(id);
		if (helpInfo != null && helpInfo.gettUser() != null) {

			String code = myCacheUtil.getSmscode();
			if (StringUtils.isEmpty(code) || myCacheUtil.IsSmscodeouttime()) {
				meta.setRescode(3);
				meta.setMsg("短信验证码已过期");
			} else if (code.equals(smscode)) {
				meta=tHelpInfoService.helpSendCoin(id);
				meta.setRescode(0);
			} else {
				meta.setRescode(3);
				meta.setMsg("短信验证码不正确，请重新输入");
			}
		}else{
			meta.setRescode(3);
			meta.setMsg("无法找到求助信息");
		}
		map.put("meta", meta);
		return map;
	}
	/*	*//**
			 * 帮助列表
			 * 
			 * @param start
			 * @param limit
			 * @return
			 *//*
			 * @RequestMapping("list") public ModelAndView list(Integer
			 * start,Integer limit) { Page<THelpInfo> page =null; String
			 * sortStr="updateTime"; if (start==null) { start=0; } if
			 * (limit==null) { limit=5; } Sort sort = new Sort(Direction.DESC,
			 * sortStr); PageRequest prequest = new PageRequest(start, limit,
			 * sort); ModelAndView modelAndView = new ModelAndView();
			 * page=tHelpInfoService.findAll(prequest);
			 * modelAndView.addObject("page", page);
			 * modelAndView.setViewName("app/home/list"); return modelAndView; }
			 */

	/**
	 * 帮助分页
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> page(Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		Page<THelpInfo> page = null;
		String sortStr = "updateTime";
		if (start == null) {
			start = 0;
		}
		if (limit == null) {
			limit = 5;
		}
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, sort);
		page = tHelpInfoService.findAll(prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}

	/**
	 * 帮助分页
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("askhelpinfo")
	@ResponseBody
	public Map<String, Object> askHelpInfos(Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		Page<THelpInfo> page = null;
		String sortStr = "updateTime";
		if (start == null) {
			start = 0;
		}
		if (limit == null) {
			limit = 5;
		}
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, sort);
		TUser tUser = myCacheUtil.getSessionUser();
		if (tUser != null) {
			page = tHelpInfoService.askHelpInfos(tUser.getId(), prequest);
			map.put("page", page);
			meta.setRescode(0);
		} else {
			meta.setRescode(3);
			meta.setMsg("好像登录信息过期了哦，请您重启该应用试试。");
		}
		map.put("meta", meta);
		return map;
	}

	/**
	 * 帮助详情
	 * 
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/helpdetail/{id}")
	@ResponseBody
	public Map<String, Object> helpDetail(@PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		THelpInfo helpInfo = tHelpInfoService.findOne(id);
		if (helpInfo != null) {
			meta.setRescode(0);
			map.put("helpinfo", helpInfo);
		} else {
			meta.setRescode(3);
			meta.setMsg("无法获取到帮助信息。");
		}
		return map;
	}

	/**
	 * 我的帮助主页
	 * 
	 * @return
	 */
	@RequestMapping("myhome")
	public ModelAndView myHome() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("app/home/myhome");
		return modelAndView;
	}

}
