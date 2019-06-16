package com.zuoke.service.core;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zuoke.dao.core.THouseFacilityDao;
import com.zuoke.model.core.THouse;
import com.zuoke.model.core.THouseFacility;

@Transactional
@Service("tHouseFacilityService")
public class THouseFacilityService {
	private Logger logger = LoggerFactory.getLogger(THouseFacilityService.class);
	@Autowired
	private THouseFacilityDao dao;
	
	public List<THouseFacility> findAll() {
		return dao.findAll();
	}
	public THouseFacility findOne(Integer id){
		return dao.findOne(id);
	}
	public THouseFacility findByHouseId(int houseId){
		List<THouseFacility> list= dao.findByHouseId(houseId);
		if (list!=null&&list.size()>0) {
			
			return list.get(0);
		}
		return null;
	}
	public THouseFacility insert(THouseFacility model){
		logger.info("添加"+model);
//		THouse tHouse=new THouse();
//		tHouse.setId(model.getHouseId());
//		model.settHouse(tHouse);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public THouseFacility update(THouseFacility model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public THouseFacility delete(THouseFacility model){
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
	public Page<THouseFacility> findAll(PageRequest prequest){
		return dao.findAll(prequest);
	}
}
