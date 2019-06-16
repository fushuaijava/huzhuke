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

import com.zuoke.model.Meta;
import com.zuoke.model.core.TActiveHistory;
import com.zuoke.service.core.TActiveHistoryService;

@Controller
@RequestMapping("tactivehistory")
public class TActiveHistoryController {
	
	@Autowired
	private TActiveHistoryService tActiveHistoryService;
	private Logger logger = LoggerFactory.getLogger(TActiveHistoryController.class);
	
	@RequestMapping("search")
	@ResponseBody
	public Map<String, Object> search(Integer userId,Integer activeId,Integer pageId,Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<TActiveHistory> page =null;
		String sortStr="updateTime";
		if (pageId==null) {
			pageId=0;
		}
		if (size==null) {
			size=5;
		}
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(pageId, size, sort);
		page=tActiveHistoryService.search(userId, activeId, prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(TActiveHistory tActiveHistory,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =tActiveHistoryService.save(tActiveHistory);
		map.put("meta", meta);
		return map;

	}
	@RequestMapping(value = "/cannel")
	@ResponseBody
	public Map<String, Object> cannel(Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		
		Meta meta =tActiveHistoryService.cannel(id);
		map.put("meta", meta);
		return map;

	}
	
}
