package com.zuoke.controller.app.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.SysConstants;
import com.zuoke.model.core.TUserProduct;
import com.zuoke.model.core.THelpInfo;
import com.zuoke.model.core.THouse;
import com.zuoke.model.core.THouseFacility;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.TUserProductService;

@Controller
@RequestMapping("userproduct")
public class TUserProductController {
	private Logger logger = LoggerFactory.getLogger(TUserProductController.class);

	@Autowired
	private TUserProductService tUserProductService;
	@Autowired
	private MyCacheUtil myCacheUtil;
	/**
	 * 添加
	 * @param tUserProduct
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String, Object> add(TUserProduct tUserProduct,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tUserProduct=tUserProductService.insert(tUserProduct);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tUserProduct", tUserProduct);
		return map;

	}
	@RequestMapping(value = "/userproduct/{id}")
	@ResponseBody
	public Map<String, Object> userproduct(@PathVariable int id, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		TUserProduct tUserProduct= tUserProductService.findOne(id);
		if (tUserProduct != null) {
			meta.setRescode(0);
			map.put("tUserProduct", tUserProduct);
			map.put("house", tUserProduct.gettHouse());
			TUser tUser=myCacheUtil.getSessionUser();
			map.put("tUser", tUser);
			if (tUser!=null&&tUser.getId()==tUserProduct.getUserId()) {
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
	@RequestMapping(value = "/houselist/{houseId}")
	@ResponseBody
	public Map<String, Object> houselist(@PathVariable int houseId, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		List<TUserProduct>  list= tUserProductService.findByHouseId(houseId);
		if (list != null) {
			meta.setRescode(0);
			map.put("list", list);
		} else {
			meta.setRescode(3);
			meta.setMsg("无法获取到帮助信息。");
		}
		return map;
	}
	/**
	 * 添加
	 * @param tUserProduct
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(TUserProduct tUserProduct,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("login begin");
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=(TUser)request.getSession().getAttribute(SysConstants.SESSIONUSER);
		tUserProduct=tUserProductService.update(tUserProduct);
		meta.setRescode(0);
		map.put("meta", meta);
		map.put("tUserProduct", tUserProduct);
		return map;

	}

	@RequestMapping("myhome")
	public ModelAndView myHome(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("app/home/myhome");
		return modelAndView;
	}
}
