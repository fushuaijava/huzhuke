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
import org.springframework.web.servlet.ModelAndView;

import com.zuoke.model.Meta;
import com.zuoke.model.SysConstants;
import com.zuoke.model.core.THouseDesc;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.THouseDescService;

@Controller
@RequestMapping("housedesc")
public class THouseDescController {
	private Logger logger = LoggerFactory.getLogger(THouseDescController.class);

	@Autowired
	private THouseDescService tHouseDescService;
	/**
	 * 添加
	 * @param tHouseDesc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> add(THouseDesc tHouseDesc,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tHouseDesc.setUserId(tUser.getId());
		tHouseDesc=tHouseDescService.insert(tHouseDesc);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tHouseDesc", tHouseDesc);
		return map;

	}
	/**
	 * 添加
	 * @param tHouseDesc
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(THouseDesc tHouseDesc,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tHouseDesc.setUserId(tUser.getId());
		tHouseDesc=tHouseDescService.update(tHouseDesc);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tHouseDesc", tHouseDesc);
		return map;

	}

	@RequestMapping("myhome")
	public ModelAndView myHome(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("app/home/myhome");
		return modelAndView;
	}
}
