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
import org.springframework.web.servlet.ModelAndView;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.SysConstants;
import com.zuoke.model.core.TInvitation;
import com.zuoke.model.core.TReviceInv;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.TInvitationService;
import com.zuoke.service.core.TReviceInvService;
/**
 * 交流中心
 * @author fushu
 *
 */
@Controller
@RequestMapping("invitation")
public class TInvitationController {
	private Logger logger = LoggerFactory.getLogger(TInvitationController.class);

	@Autowired
	private TInvitationService tInvitationService;
	@Autowired
	private TReviceInvService tReviceInvService;
	@Autowired
	private MyCacheUtil myCacheUtil;
	/**
	 * 添加
	 * @param tInvitation
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> add(TInvitation tInvitation,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
		tInvitation.setUserId(tUser.getId());
		tInvitation=tInvitationService.insert(tInvitation);
		/*tReviceInv.setInvitationId(tInvitation.getId());
		tReviceInvService.insert(tReviceInv);*/
		meta.setRescode(0);
		}else{
			meta.setRescode(3);
			meta.setMsg("您的用户信息为空，请重新打开应用再试试。");
		}
		map.put("meta", meta);
		map.put("tInvitation", tInvitation);
		return map;

	}
	/**
	 * 修改
	 * @param tInvitation
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(TInvitation tInvitation,TReviceInv tReviceInv,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tInvitation.settUser(tUser);
		tInvitation=tInvitationService.update(tInvitation);
		tReviceInv.setInvitationId(tInvitation.getId());
		tReviceInvService.update(tReviceInv);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tInvitation", tInvitation);
		return map;

	}
	
	/**
	 * 交流分页
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("page")
	@ResponseBody
	public Map<String, Object> page(Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<TInvitation> page =null;
		String sortStr="updateTime";
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=5;
		}
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, sort);
		page=tInvitationService.findAll(prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}
	/**
	 * 交流详情
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("view/{id}")
	@ResponseBody
	public Map<String, Object> view(@PathVariable Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TInvitation tInvitation=tInvitationService.findOne(id);
		PageRequest prequest = new PageRequest(0, 5);
		Page<TReviceInv> tReviceInvPage=tReviceInvService.findByinvitationId(id, prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tInvitation", tInvitation);
		map.put("tReviceInvPage", tReviceInvPage);
		return map;
	}
	
	/**
	 * 我的交流主页
	 * @return
	 */
	@RequestMapping("myhome")
	public ModelAndView myHome(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("app/invitation/myhome");
		return modelAndView;
	}
}
