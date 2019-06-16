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

import com.zuoke.dao.core.THouseDescDao;
import com.zuoke.model.core.THouse;
import com.zuoke.model.core.THouseDesc;

@Transactional
@Service("tHouseDescService")
public class THouseDescService {
	private Logger logger = LoggerFactory.getLogger(THouseDescService.class);
	@Autowired
	private THouseDescDao dao;
	
	public List<THouseDesc> findAll() {
		return dao.findAll();
	}
	public THouseDesc findOne(Integer id){
		return dao.findOne(id);
	}
	public THouseDesc findByHouseId(int houseId){
		THouse tHouse=new THouse();
		tHouse.setId(houseId);
		List<THouseDesc> list= dao.findByTHouse(tHouse);
		if (list!=null&&list.size()>0) {
			
			return list.get(0);
		}
		return null;
	}
	public THouseDesc insert(THouseDesc model){
		logger.info("添加"+model);
		THouse tHouse=new THouse();
		tHouse.setId(model.getHouseId());
		model.settHouse(tHouse);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public THouseDesc update(THouseDesc model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public THouseDesc delete(THouseDesc model){
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
	public Page<THouseDesc> findAll(PageRequest prequest){
		return dao.findAll(prequest);
	}
}
