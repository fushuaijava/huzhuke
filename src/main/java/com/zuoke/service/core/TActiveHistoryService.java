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

import com.zuoke.dao.core.TActiveDao;
import com.zuoke.dao.core.TActiveHistoryDao;
import com.zuoke.dao.core.impl.TActiveHistoryDaoPlusImpl;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TActive;
import com.zuoke.model.core.TActiveHistory;

@Transactional
@Service("tActiveHistoryService")
public class TActiveHistoryService {
	
	private Logger logger = LoggerFactory.getLogger(TActiveHistoryService.class);
	
	@Autowired
	private TActiveHistoryDao dao;
	@Autowired
	private TActiveDao tActiveDao;
	@Autowired
	private TActiveHistoryDaoPlusImpl daoPlusImpl;
	
	public Page<TActiveHistory> findByuserIdOrderByInsertTime(int userId,PageRequest pageRequest){
		return dao.findByuserIdOrderByInsertTime(userId, pageRequest);
	}
	
	public Page<TActiveHistory> search(Integer userId,Integer activeId,PageRequest pageRequest){
		logger.info("userId:"+userId+",activeId:"+activeId);
		return daoPlusImpl.search(userId, activeId, pageRequest);
	}
	
	public Meta save(TActiveHistory tActiveHistory){
		Meta meta=new Meta();
		
		
			tActiveHistory.setInsertTime(new Date());
			TActive tActive=tActiveDao.findOne(tActiveHistory.getActiveId());
			List<TActiveHistory> historys=dao.findByUserIdAndActiveId(tActiveHistory.getUserId(),tActiveHistory.getActiveId());
			if (tActive==null) {
				meta.setMsg("没有找到该活动，请您重新选择");
				meta.setRescode(1);
			}else if (tActive.getPeopleTotal()<=tActive.getPeopleNow()) {
				meta.setMsg("该活动名额已经满了，请您选择其他活动");
				meta.setRescode(2);
			}else if (tActive.getEndDate().before(new Date())) {
				meta.setMsg("该活动已经结束，请您选择其他活动");
				meta.setRescode(3);
			}else if (historys!=null&&historys.size()>0) {
				meta.setMsg("您已经参加报名过该活动，请您选择其他活动");
				meta.setRescode(3);
			}else{
				tActive.setPeopleNow(tActive.getPeopleNow()+1);
				tActiveDao.save(tActive);
				tActiveHistory.setIsDelete(0);
				tActiveHistory.setInsertTime(new Date());
				tActiveHistory.setUpdateTime(new Date());
				dao.save(tActiveHistory);
				meta.setRescode(0);
			}
			
		return meta;
	}
	public Meta cannel(Integer id){
		Meta meta =new Meta();
		TActiveHistory tActiveHistory=dao.findOne(id);
		tActiveHistory.setIsDelete(1);
		TActive tActive=tActiveDao.findOne(tActiveHistory.getActiveId());
		tActive.setPeopleNow(tActive.getPeopleNow()-1);
		tActiveDao.save(tActive);
		meta.setRescode(0);
		return meta;
	}

}
