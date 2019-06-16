package com.zuoke.controller.app.core;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.SysConstants;
import com.zuoke.model.core.THelpInfo;
import com.zuoke.model.core.THouse;
import com.zuoke.model.core.THouseDesc;
import com.zuoke.model.core.THouseFacility;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.THouseDescService;
import com.zuoke.service.core.THouseFacilityService;
import com.zuoke.service.core.THouseService;

@Controller
@RequestMapping("house")
public class THouseController {
	private Logger logger = LoggerFactory.getLogger(THouseController.class);

	@Autowired
	private THouseService tHouseService;
	@Autowired
	private THouseDescService tHouseDescService;
	@Autowired
	private THouseFacilityService tHouseFacilityService;
	@Autowired
	private MyCacheUtil myCacheUtil;
	/**
	 * 添加
	 * @param tHouse
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> add(THouse tHouse,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tHouse.settUser(tUser);
		tHouse.setUserId(tUser.getId());
		tHouse=tHouseService.insert(tHouse);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tHouse", tHouse);
		return map;

	}
	/**
	 * 修改
	 * @param tHouse
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(THouse tHouse,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		
		TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tHouse.settUser(tUser);
		tHouse=tHouseService.update(tHouse);
		
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tHouse", tHouse);
		return map;

	}
	/**
	 * 做客分页
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> page(Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<THouse> page =null;
		String sortStr="updateTime";
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=5;
		}
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, sort);
		page=tHouseService.findAll(prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}
	/**
	 * 搜索查询
	 * @param searchstring
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("page/search")
	@ResponseBody
	public Map<String, Object> pageSearch(String searchstring,Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=5;
		}
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", tHouseService.findBySearchString(searchstring,start,limit));
		return map;
	}
	/**
	 * 我的做客邀请页
	 * @return
	 */
	@RequestMapping("myhome")
	@ResponseBody
	public Map<String, Object> myHome(){
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=myCacheUtil.getSessionUser();
		List<THouse> list=tHouseService.findByUserId(tUser.getId());
		for (THouse tHouse : list) {
			tHouse.settUser(tUser);
			map.put("house", tHouse);
		}
		meta.setRescode(0);
		map.put("meta", meta);
		return map;
	}
	@RequestMapping(value = "/housedetail/{id}")
	@ResponseBody
	public Map<String, Object> housedetail(@PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		THouse tHouse = tHouseService.findOne(id);
		THouseFacility tHouseFacility=tHouseFacilityService.findByHouseId(id);
		if (tHouse != null) {
			meta.setRescode(0);
			tHouse.settHouseFacility(tHouseFacility);
			map.put("house", tHouse);
			TUser tUser=myCacheUtil.getSessionUser();
			map.put("tUser", tUser);
			if (tUser!=null&&tUser.getId()==tHouse.getUserId()) {
				map.put("ismanager", true);
			}else{
				map.put("ismanager", false);
			}
		} else {
			meta.setRescode(3);
			meta.setMsg("无法获取到帮助信息。");
		}
		return map;
	}
}
