package com.zuoke.service.core;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.common.util.SystemParamUtil;
import com.zuoke.dao.core.THouseDao;
import com.zuoke.dao.core.TUserDao;
import com.zuoke.dao.core.impl.THouseDaoPlusImpl;
import com.zuoke.model.core.THouse;
import com.zuoke.model.core.TUser;

@Transactional
@Service("tHouseService")
public class THouseService {
	private Logger logger = LoggerFactory.getLogger(THouseService.class);
	@Autowired
	private THouseDao dao;
	@Autowired
	private TUserDao tUserDao;
	@Autowired
	private THouseDaoPlusImpl tHouseDaoPlus;
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private TCoinHistoryService tCoinHistoryService;
	@Autowired
	private SystemParamUtil systemParamUtil;
	public List<THouse> findAll() {
		return dao.findAll();
	}
	public THouse findOne(Integer id){
		 THouse house=dao.findOne(id);
		 TUser tUser=myCacheUtil.getSessionUser();
		 if (tUser!=null&&tUser.getId()==house.getUserId()) {
			
		}else{
			tUser=tUserDao.findOne(house.getUserId());
		}
		 house.settUser(tUser);
		 return house;
	}
	public List<THouse> findByUserId(int userid){
		return dao.findByUserId(userid);
	}
	public THouse insert(THouse model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		model.setIsdelete(0);
		model.setSearchString(getSearchString(model));
		model=dao.save(model);
		tCoinHistoryService.addCoin(model.getUserId(), systemParamUtil.gethouseInfoSendCoin(), "1", 1);
		return model;
	}
	public THouse update(THouse model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		THouse tHouse=dao.findOne(model.getId());
		tHouse.setSearchString(getSearchString(model));
		tHouse.setPicture1(StringUtils.isEmpty(model.getPicture1())?null:model.getPicture1());
		tHouse.setPicture2(StringUtils.isEmpty(model.getPicture2())?null:model.getPicture2());
		tHouse.setPicture3(StringUtils.isEmpty(model.getPicture3())?null:model.getPicture3());
		tHouse.setPicture4(StringUtils.isEmpty(model.getPicture4())?null:model.getPicture4());
		tHouse.setPicture5(StringUtils.isEmpty(model.getPicture5())?null:model.getPicture5());
		tHouse.setCompressImage1(StringUtils.isEmpty(model.getCompressImage1())?null:model.getCompressImage1());
		tHouse.setCompressImage2(StringUtils.isEmpty(model.getCompressImage2())?null:model.getCompressImage2());
		tHouse.setCompressImage3(StringUtils.isEmpty(model.getCompressImage3())?null:model.getCompressImage3());
		tHouse.setCompressImage4(StringUtils.isEmpty(model.getCompressImage4())?null:model.getCompressImage4());
		tHouse.setCompressImage5(StringUtils.isEmpty(model.getCompressImage5())?null:model.getCompressImage5());
		tHouse.setCanlive(model.getCanlive());
		tHouse.setProvince(model.getProvince());
		tHouse.setSmalldes(model.getSmalldes());
		tHouse.setNearby(model.getNearby());
		tHouse.setCate(model.getCate());
		tHouse.setCity(model.getCity());
		tHouse.setAddress(model.getAddress());
		tHouse.setUpdateTime(new Date());
		tHouse.setMinCoin(model.getMinCoin());
		tHouse.setAdviseCoin(model.getAdviseCoin());
		return dao.save(tHouse);
	}
	public THouse delete(THouse model){
		if(model!=null){
			logger.info("删除"+model);
			logger.info("删除Id"+model.getId());
			model=dao.findOne(model.getId());
			model.setIsdelete(1);
			model.setUpdateTime(new Date());
			dao.save(model);
		}
		return model;
	}
	/**
	 * 拼接查询字符串
	 * @param model
	 * @return
	 */
	private String getSearchString(THouse model){
		StringBuilder searchString=new StringBuilder(model.getCountry()+model.getProvince()+model.getCity()+model.getAddress());
		if (model.getHousePlace()!=null) {
			searchString.append(model.getHousePlace());
		}
		if (model.getNearby()!=null) {
			searchString.append(model.getNearby());
		}
		if (model.getSpecial()!=null) {
			searchString.append(model.getSpecial());
		}
		if (model.getCate()!=null) {
			searchString.append(model.getCate());
		}
		if (model.getSmalldes()!=null) {
			searchString.append(model.getSmalldes());
		}
		return searchString.toString();
	}
	public Page<THouse> findBySearchString(String searchString,Integer start,Integer limit){
		return tHouseDaoPlus.search(new PageRequest(start, limit),searchString);
	}
	public Page<THouse> findAll(PageRequest prequest){
		return tHouseDaoPlus.findAll(prequest);
	}
}
