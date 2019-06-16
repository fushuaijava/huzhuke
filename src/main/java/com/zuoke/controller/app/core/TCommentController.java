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

import com.zuoke.model.Meta;
import com.zuoke.model.core.TComment;
import com.zuoke.service.core.TCommentService;

@Controller
@RequestMapping("comment")
public class TCommentController {
	private Logger logger = LoggerFactory.getLogger(TCommentController.class);

	@Autowired
	private TCommentService tCommentService;
	/**
	 * 添加帮助回复
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> add(TComment tComment,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		//TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tComment=tCommentService.insert(tComment);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tHelpInfo", tComment);
		return map;

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
	public Map<String, Object> update(TComment tComment,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("tComment begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		tComment=tCommentService.update(tComment);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tComment", tComment);
		return map;

	}
	/**
	 * 帮助回复分页
	 * @param start
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/page")
	@ResponseBody
	public Map<String, Object> page(Integer houseid,Integer start,Integer limit) {
		logger.info("tComment begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		Page<TComment> page =null;
		String sortStr="updateTime";
		if (start==null) {
			start=0;
		}
		if (limit==null) {
			limit=5;
		}
		Sort sort = new Sort(Direction.DESC, sortStr);
		PageRequest prequest = new PageRequest(start, limit, sort);
		page=tCommentService.findByHouseid(houseid,prequest);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("page", page);
		return map;

	}
}
