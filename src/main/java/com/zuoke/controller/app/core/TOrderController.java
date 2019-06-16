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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TOrder;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.TOrderService;

@Controller
@RequestMapping("order")
public class TOrderController {
	private Logger logger = LoggerFactory.getLogger(TOrderController.class);

	@Autowired
	private TOrderService tOrderService;
	@Autowired
	private MyCacheUtil myCacheUtil;
	/**
	 * 添加支付订单
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> add(TOrder tOrder,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		return tOrderService.create(tOrder);

	}
	/**
	 * 修改支付订单
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(TOrder tOrder,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("tOrder begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		tOrder=tOrderService.update(tOrder);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tOrder", tOrder);
		return map;

	}
	/**
	 * 支付订单分页
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> page(Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<TOrder> page =null;
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
		page=tOrderService.searchByUserIdAll(tUser.getId(), prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}
	
}
