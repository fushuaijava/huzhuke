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

import com.zuoke.common.util.AlbcIMUtil;
import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.common.util.ValidUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.SysConstants;
import com.zuoke.model.core.THelpInfo;
import com.zuoke.model.core.TUser;
import com.zuoke.model.core.TUserinfo;
import com.zuoke.service.core.TUserDoService;
import com.zuoke.service.core.TUserService;
import com.zuoke.service.core.TUserinfoService;

@Controller
@RequestMapping("userinfo")
public class TUserinfoController {
	private Logger logger = LoggerFactory.getLogger(TUserinfoController.class);
	@Autowired
	private TUserService tUserService;
	@Autowired
	private TUserinfoService tUserinfoService;
	@Autowired
	private TUserDoService tUserDoService;
	@Autowired
	private ValidUtil validUtil;
	@Autowired
	private MyCacheUtil myCacheUtil;

	/**
	 * 完善用户资料
	 * @param tUser
	 * @param TUserinfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> save(TUserinfo tUserinfo,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		return tUserinfoService.save(tUserinfo);
		

	}
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> page(Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		Page<TUserinfo> page = null;
		if (start == null) {
			start = 0;
		}
		if (limit == null) {
			limit = 20;
		}
		PageRequest prequest = new PageRequest(start, limit);
		page = tUserinfoService.findNoValid(prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}
	@RequestMapping("/valid/userinfo")
	@ResponseBody
	public Map<String, Object> validUserInfo(TUserinfo tUserinfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		map.put("meta", meta);
		TUserinfo model=tUserinfoService.validUserInfo(tUserinfo);
		if (model!=null) {
			meta.setRescode(0);
			map.put("tUserinfo", model);
		}else{
			meta.setRes1(3);
			meta.setMsg("信息异常");
		}
		map.put("meta", meta);
		return map;
	}
	@RequestMapping("/myinfo")
	@ResponseBody
	public Map<String, Object> myinfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		TUser tUser=myCacheUtil.getSessionUser();
		map.put("tUser", tUser);
		List<TUserinfo> list=tUserinfoService.findByUserId(tUser.getId());
		if (list!=null&&list.size()>0) {
			map.put("tUserinfo", list.get(0));
		}
//		map.put(key, value)
		meta.setRescode(0);
		map.put("meta", meta);
		return map;
	}

	
	
}
