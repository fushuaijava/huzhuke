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
import com.zuoke.model.core.THelpInfo;
import com.zuoke.model.core.THelpInfoReply;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.THelpInfoService;
import com.zuoke.service.core.THelpInfoReplyService;

@Controller
@RequestMapping("helpinforeply")
public class THelpInfoReplyController {
	private Logger logger = LoggerFactory.getLogger(THelpInfoReplyController.class);

	@Autowired
	private THelpInfoService tHelpInfoService;
	@Autowired
	private THelpInfoReplyService tHelpInfoReplyService;
	@Autowired
	private MyCacheUtil myCacheUtil;
	/**
	 * 添加帮助回复
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> add(THelpInfoReply tHelpInfoReply,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		return tHelpInfoReplyService.createTHelpInfoReply(tHelpInfoReply);
		/*Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		THelpInfo tHelpInfo=tHelpInfoService.findOne(tHelpInfoReply.getHelpInfoId());
		if (tHelpInfo!=null) {
			tHelpInfoReply.setToUserId(tHelpInfo.getUserId());
			tHelpInfoReply.setUserId(tUser.getId());
			tHelpInfoReply.setParentId(tHelpInfo.getId());
			tHelpInfoReply=tHelpInfoReplyService.insert(tHelpInfoReply);
			map.put("tHelpInfo", tHelpInfoReply);
			meta.setRescode(0);
		}else{
			meta.setMsg("数据异常，请稍后重试！");
			meta.setRescode(3);
		}
		map.put("meta", meta);
		return map;*/

	}
	/**
	 * 修改帮助回复
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(THelpInfoReply tHelpInfoReply,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		//TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
//		THelpInfo tHelpInfo=tHelpInfoService.findOne(tHelpInfoReply.getHelpInfoId());
//		tHelpInfo.setReviceCount(tHelpInfo.getReviceCount()+1);
//		tHelpInfo=tHelpInfoService.update(tHelpInfo);
//		tHelpInfoReply.setHelpInfoId(tHelpInfo.getId());
//		tHelpInfoReplyService.update(tHelpInfoReply);
//		meta.setRescode(0);
//		map.put("meta", meta);
//		map.put("tHelpInfo", tHelpInfo);
		return map;

	}
	/**
	 * 帮助回复分页
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("page/{helpinfoid}/{pageId}")
	@ResponseBody
	public Map<String, Object> page(@PathVariable("helpinfoid") Integer helpinfoid,@PathVariable("pageId")Integer pageId,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<THelpInfoReply> page =null;
		String sortStr="updateTime";
		if (pageId==null) {
			pageId=0;
		}
		if (limit==null) {
			limit=5;
		}
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(pageId, limit, sort);
		page=tHelpInfoReplyService.findByHelpInfoId(helpinfoid, prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;
	}
	/**
	 * 帮助回复分页
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping("myhomepage")
	@ResponseBody
	public Map<String, Object> myhomepage(Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<THelpInfoReply> page =null;
		String sortStr="updateTime";
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=5;
		}
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, sort);
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
			
			page=tHelpInfoReplyService.myHelpInfoReplys(tUser.getId(), prequest);
			meta.setRescode(0);
			map.put("page", page);
		}else{
			meta.setRescode(3);
			meta.setMsg("好像登录信息过期了哦，请您重启该应用试试。");
		}
		map.put("meta", meta);
		return map;
	}
	/**
	 * 我的帮助主页
	 * @return
	 */
	@RequestMapping("myhome")
	public ModelAndView myHome(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("app/home/myhome");
		return modelAndView;
	}
}
