package com.zuoke.service.core;


import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zuoke.dao.core.TActiveDao;
import com.zuoke.model.core.TActive;

@Transactional
@Service("tActiveService")
public class TActiveService {
	private Logger logger = LoggerFactory.getLogger(TActiveService.class);
	@Autowired
	private TActiveDao dao;
	
	public Page<TActive> findByOrderByInsertTimeDesc(PageRequest page){
		return dao.findByOrderByInsertTimeDesc(page);
	}
	
	public TActive save(TActive tActive){
		tActive.setInsertTime(new Date());
		tActive.setUpdateTime(new Date());
		tActive.setIsDelete(0);
		dao.save(tActive);
		return tActive;
	}
	
	public TActive findOne(Integer id){
		return dao.findOne(id);
		
	}
	
}
