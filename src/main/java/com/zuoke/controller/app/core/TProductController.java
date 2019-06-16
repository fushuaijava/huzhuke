package com.zuoke.controller.app.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zuoke.model.Meta;
import com.zuoke.model.core.TProduct;
import com.zuoke.service.core.TProductService;

@Controller
@RequestMapping("product")
public class TProductController {
	private Logger logger = LoggerFactory.getLogger(TProductController.class);

	@Autowired
	private TProductService tProductService;
	
	/**
	 * 产品列表分页
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/page/{productType}")
	@ResponseBody
	public Map<String, Object> page(@PathVariable String productType,Integer start,Integer limit) {
		logger.info("tProduct begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<TProduct> page =null;
		//String sortStr="updateTime";
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=10;
		}
		//Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, null);
		page=tProductService.findByProductTypeOrder(productType, prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;

	}
	/**
	 * 产品列表
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(String productType) {
		logger.info("tProduct begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		List<TProduct> list =null;
		
		//Sort sort = new Sort(Direction.DESC, sortStr);
		list=tProductService.findByProductTypeOrder(productType);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", list);
		return map;

	}
}
