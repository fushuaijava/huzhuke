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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TGuestOrder;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.TGuestOrderService;

@Controller
@RequestMapping("guestorder")
public class TGuestOrderController {
	private Logger logger = LoggerFactory.getLogger(TGuestOrderController.class);

	@Autowired
	private TGuestOrderService tGuestOrderService;
	@Autowired
	private MyCacheUtil myCacheUtil;
	/**
	 * 添加做客记录
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> add(TGuestOrder tOrder,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		//TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tOrder=tGuestOrderService.insert(tOrder);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tHelpInfo", tOrder);
		return map;

	}
	@RequestMapping(value = "/get")
	@ResponseBody
	public Map<String, Object> get(TGuestOrder tOrder,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		//TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tOrder=tGuestOrderService.insert(tOrder);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tHelpInfo", tOrder);
		return map;

	}
	/**
	 * 创建做客记录
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	/*
	@RequestMapping(value = "/create")
	@ResponseBody
	public Map<String, Object> create(TGuestOrder tOrder,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		return tGuestOrderService.create(tOrder);
	}*/
	/**
	 * 创建做客记录
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/create")
	public ModelAndView create(TGuestOrder tOrder,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("result", tGuestOrderService.create(tOrder));
		modelAndView.setViewName("guestorder/payconfirm2");
		return modelAndView;
	}
	@RequestMapping(value = "/prepay")
	@ResponseBody
	public Map<String, Object> prepay(int id,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		return tGuestOrderService.prepay(id);
	}
	@RequestMapping(value = "/updatestate")
	@ResponseBody
	public Map<String, Object> updatestate(int id,int orderState,String code,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		if (orderState==3||orderState==4) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			Meta meta =new Meta();
			meta.setRescode(3);
			String smscode = myCacheUtil.getSmscode();
			String mobile = myCacheUtil.getSendcodeMobile();
			if (StringUtils.isEmpty(code) || myCacheUtil.IsSmscodeouttime()) {
				meta.setRescode(3);
				meta.setMsg("短信验证码已过期");
			} else if (!myCacheUtil.getSessionUser().getMobilephone().equals(mobile)) {
				meta.setRescode(3);
				meta.setMsg("短信验证手机和求助手机不一致，请重新输入");
			} else if (code.equals(smscode)) {
				meta.setRescode(0);
				return tGuestOrderService.updateState(id,orderState);
			} else {
				meta.setRescode(3);
				meta.setMsg("短信验证码不正确，请重新输入");
			}
			map.put("meta", meta);
			return map;
		}
		return tGuestOrderService.updateState(id,orderState);
	}
	/**
	 * 修改做客记录
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(TGuestOrder tOrder,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("tOrder begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		tOrder=tGuestOrderService.update(tOrder);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tOrder", tOrder);
		return map;

	}
	/**
	 * 做客访问记录分页
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("guestpage")
	@ResponseBody
	public Map<String, Object> guestpage(Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<TGuestOrder> page =null;
		String sortStr="updateTime";
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=5;
		}
		TUser tUser=myCacheUtil.getSessionUser();
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, sort);
		page=tGuestOrderService.searchByGuestUserIdAll(tUser, prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}
	/**
	 * 做客记录分页
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("masterpage")
	@ResponseBody
	public Map<String, Object> masterpage(Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<TGuestOrder> page =null;
		String sortStr="updateTime";
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=5;
		}
		TUser tUser=myCacheUtil.getSessionUser();
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, sort);
		page=tGuestOrderService.searchByMasterUserIdAll(tUser, prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}
}
