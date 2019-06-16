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
import org.springframework.web.servlet.ModelAndView;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TInvitation;
import com.zuoke.model.core.TReviceInv;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.TInvitationService;
import com.zuoke.service.core.TReviceInvService;

@Controller
@RequestMapping("reviceinv")
public class TReviceInvController {
	private Logger logger = LoggerFactory.getLogger(TReviceInvController.class);

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
	public Map<String, Object> add(TReviceInv tReviceInv,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		//TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
			tReviceInv.setSendUser(tUser);
			tReviceInv.setSendUserId(tUser.getId());
			tReviceInv.getInvitationId();
			TInvitation tInvitation=tInvitationService.findOne(tReviceInv.getInvitationId());
			
			if (tInvitation!=null) {
				if (tReviceInv.getReplyId()==0) {
					
				tReviceInv.setToUserId(tInvitation.getUserId());
				}else{
					TReviceInv r=tReviceInvService.findOne(tReviceInv.getReplyId());
					if (r!=null) {
						tReviceInv.setToUserId(r.getSendUserId());
						tReviceInv.setParentId(r.getParentId()==0?r.getId():r.getParentId());
						r.setReviceCount(r.getReviceCount()+1);
						tReviceInvService.update(r);
					}
				}
			}
			tInvitation.setReviceCount(tInvitation.getReviceCount()+1);
			tInvitationService.update(tInvitation);
			tReviceInv=tReviceInvService.insert(tReviceInv);
			meta.setRescode(0);
		}else{
			meta.setRescode(3);
			meta.setMsg("亲，您没有登录哦，请您返回首页登录之后再进行操作，构建最好的互助旅行生态圈，我们一起努力！");
		}
		map.put("meta", meta);
		map.put("tReviceInv", tReviceInv);
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
	public Map<String, Object> update(TReviceInv tReviceInv,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		//TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		TInvitation tInvitation=tInvitationService.findOne(tReviceInv.getInvitationId());
		tInvitation.setReviceCount(tInvitation.getReviceCount()+1);
		tInvitation=tInvitationService.update(tInvitation);
		tReviceInv.setInvitationId(tInvitation.getId());
		tReviceInvService.update(tReviceInv);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tReviceInv", tInvitation);
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
	public Map<String, Object> page(Integer invitationId,Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<TReviceInv> page =null;
	//	String sortStr="updateTime";
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=5;
		}
//		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, null);
		page=tReviceInvService.findByinvitationId(invitationId, prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}
	/**
	 * 交流分页
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("subpage")
	@ResponseBody
	public Map<String, Object> subpage(Integer parentId,Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<TReviceInv> page =null;
		String sortStr="updateTime";
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=5;
		}
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, sort);
		page=tReviceInvService.findByParentId(parentId, prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}
	/**
	 * 我的交流主页
	 * @return
	 */
	@RequestMapping("myhome")
	public ModelAndView myHome(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("app/home/myhome");
		return modelAndView;
	}
}
