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

import com.zuoke.dao.core.THouseDao;
import com.zuoke.dao.core.TUserProductDao;
import com.zuoke.model.core.THouse;
import com.zuoke.model.core.TUserProduct;

@Transactional
@Service("tUserProductService")
public class TUserProductService {
	private Logger logger = LoggerFactory.getLogger(TUserProductService.class);
	@Autowired
	private TUserProductDao dao;
	@Autowired
	private THouseDao tHouseDao;
	
	public List<TUserProduct> findAll() {
		return dao.findAll();
	}
	public TUserProduct findOne(Integer id){
		TUserProduct tUserProduct=dao.findOne(id);
		THouse tHouse=tHouseDao.findOne(tUserProduct.getHouseId());
		tUserProduct.settHouse(tHouse);
		return tUserProduct;
	}
	public List<TUserProduct> findByHouseId(int houseId){
		List<TUserProduct> list= dao.findByHouseId(houseId);
		if (list!=null&&list.size()>0) {
			
			return list;
		}
		return null;
	}
	public TUserProduct insert(TUserProduct model){
		logger.info("添加"+model);
//		THouse tHouse=new THouse();
//		tHouse.setId(model.getHouseId());
//		model.settHouse(tHouse);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public TUserProduct update(TUserProduct model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public TUserProduct delete(TUserProduct model){
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
	public Page<TUserProduct> findAll(PageRequest prequest){
		return dao.findAll(prequest);
	}
}
