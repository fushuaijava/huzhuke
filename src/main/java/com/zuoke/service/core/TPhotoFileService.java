package com.zuoke.service.core;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuoke.dao.core.TPhotoFileDao;
import com.zuoke.model.core.TPhotoFile;

@Transactional
@Service("tPhotoFileService")
public class TPhotoFileService {
	private Logger logger = LoggerFactory.getLogger(TPhotoFileService.class);
	@Autowired
	private TPhotoFileDao dao;
	public List<TPhotoFile> findAll() {
		return dao.findAll();
	}
	
	public TPhotoFile findOne(Integer id){
		return dao.findOne(id);
	}
	public TPhotoFile insert(TPhotoFile model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		return dao.save(model);
	}
	public TPhotoFile update(TPhotoFile model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		return dao.save(model);
	}
	public TPhotoFile delete(TPhotoFile model){
		if(model!=null){
			logger.info("删除"+model);
			logger.info("删除Id"+model.getId());
			model=dao.findOne(model.getId());
			model.setIsDelete(1);
			dao.save(model);
		}
		return model;
	}
	
}
