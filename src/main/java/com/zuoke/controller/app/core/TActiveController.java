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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zuoke.model.Meta;
import com.zuoke.model.core.TActive;
import com.zuoke.model.core.THouse;
import com.zuoke.model.core.THouseFacility;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.TActiveService;

@Controller
@RequestMapping("tactive")
public class TActiveController {
	
	@Autowired
	private TActiveService tActiveService;
	private Logger logger = LoggerFactory.getLogger(TActiveController.class);
	
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> page(Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<TActive> page =null;
		String sortStr="updateTime";
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=5;
		}
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, sort);
		page=tActiveService.findByOrderByInsertTimeDesc(prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(TActive tActive,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		tActive=tActiveService.save(tActive);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tActive", tActive);
		return map;

	}
	
	@RequestMapping(value = "/active/{id}")
	@ResponseBody
	public Map<String, Object> activedetail(@PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		TActive tActive = tActiveService.findOne(id);
		if (tActive != null) {
			meta.setRescode(0);
			map.put("tActive", tActive);
		} else {
			meta.setRescode(3);
			meta.setMsg("无法获取到活动信息。");
		}
		return map;
	}
}
