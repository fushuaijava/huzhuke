package com.zuoke.service.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zuoke.dao.core.TCommentDao;
import com.zuoke.dao.core.TGuestOrderDao;
import com.zuoke.dao.core.THouseDao;
import com.zuoke.dao.core.TUserDao;
import com.zuoke.model.core.TComment;
import com.zuoke.model.core.TGuestOrder;
import com.zuoke.model.core.THouse;
import com.zuoke.model.core.TUser;

@Transactional
@Service("tCommentService")
public class TCommentService {
	private Logger logger = LoggerFactory.getLogger(TCommentService.class);
	@Autowired
	private TCommentDao dao;
	@Autowired
	private TGuestOrderDao tGuestOrderDao;
	@Autowired
	private THouseDao tHouseDao;
	@Autowired
	private TUserDao tUserDao;
	
	public TComment findOne(Integer id){
		return dao.findOne(id);
	}
	public Page<TComment> findByHouseid(int houseid,PageRequest prequest){
		return searchByHouseid(houseid,prequest);
	}
	public Page<TComment> searchByHouseid(int houseid,PageRequest prequest){
		Page<Object[]> pageobject=dao.searchByHouseid(houseid,prequest);
		return transObject(pageobject);
	}
	
	private Page<TComment> transObject(Page<Object[]> pageobject) {
		List<TComment> tComments=new ArrayList<TComment>();
		if (pageobject!=null&&pageobject.getContent()!=null) {
			List<Object[]> list=pageobject.getContent();
			for (Object[] objects : list) {
				TComment tComment=(TComment)objects[0];
				TUser tUser=(TUser)objects[1];
				tComment.settUser(tUser);
				tComments.add(tComment);
			}
		}
		Page<TComment> page=new PageImpl<TComment>(tComments,new PageRequest(pageobject.getNumber(), pageobject.getSize()), pageobject.getTotalElements());
		return page;
	}
	public TComment insert(TComment model){
		logger.info("添加"+model);
		TGuestOrder tGuestOrder=tGuestOrderDao.findOne(model.getOrderId());
		model.setCreateTime(new Date());
		model.setHouseid(tGuestOrder.getHouseId());
		model.setIsDelete(0);
		model.setOrderId(tGuestOrder.getId());
		model.setUpdateTime(new Date());
		model.setUserId(tGuestOrder.getGuestUserId());
		model=dao.save(model);
		tGuestOrder.setOrderState(5);
		THouse tHouse=tHouseDao.findOne(tGuestOrder.getHouseId());
		TUser guestUser=tUserDao.findOne( tGuestOrder.getGuestUserId());
		if (guestUser!=null) {
			guestUser.setVisitCount(guestUser.getVisitCount()+1);
			tUserDao.save(guestUser);
		}
		TUser acceptUser=tUserDao.findOne( tGuestOrder.getMasterUserId());
		if (acceptUser!=null) {
			acceptUser.setAcceptCount(acceptUser.getAcceptCount()+1);
			tUserDao.save(acceptUser);
		}
		tHouse.setTotal(tHouse.getTotal()+1);
		tHouse.setCommentScore(tHouse.getCommentScore()+1);
		tGuestOrderDao.save(tGuestOrder);
		return model;
	}
	public TComment update(TComment model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	

}
