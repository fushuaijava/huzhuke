package com.zuoke.service.core;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuoke.dao.core.TPhotoRelationDao;
import com.zuoke.model.core.TPhotoRelation;

@Transactional
@Service("tPhotoRelationService")
public class TPhotoRelationService {
	private Logger logger = LoggerFactory.getLogger(TPhotoRelationService.class);
	@Autowired
	private TPhotoRelationDao dao;
	public List<TPhotoRelation> findAll() {
		return dao.findAll();
	}
	public List<TPhotoRelation> findByGroupTypeAndRelationTableId(String groupType,int relationTableId) {
		return dao.findByGroupTypeAndRelationTableId(groupType, relationTableId);
	}
	public TPhotoRelation findOne(Integer id){
		return dao.findOne(id);
	}
	public TPhotoRelation insert(TPhotoRelation model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public TPhotoRelation update(TPhotoRelation model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public TPhotoRelation delete(TPhotoRelation model){
		if(model!=null){
			logger.info("删除"+model);
			logger.info("删除Id"+model.getId());
			model=dao.findOne(model.getId());
			model.setIsDelete(1);
			model.setUpdateTime(new Date());
			dao.save(model);
		}
		return model;
	}
}
