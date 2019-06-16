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

import com.zuoke.dao.core.TInvitationDao;
import com.zuoke.model.core.TInvitation;
import com.zuoke.model.core.TUser;
/**
 * 个性化设置
 * @author fushu
 *
 */
@Transactional
@Service("tInvitationService")
public class TInvitationService {
	private Logger logger = LoggerFactory.getLogger(TInvitationService.class);
	@Autowired
	private TInvitationDao dao;
	public TInvitation findOne(Integer id){
		Object[] objects=(Object[])dao.get(id);
		if (objects!=null) {
			TInvitation tInvitation=(TInvitation)objects[0];
			TUser tUser=(TUser)objects[1];
			tInvitation.settUser(tUser);
			return tInvitation;
		}
		return null;
	}
	public TInvitation insert(TInvitation model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public TInvitation update(TInvitation model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public TInvitation delete(TInvitation model){
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
	
	public Page<TInvitation> findAll(PageRequest prequest){
		return transObject(dao.searchAll(prequest));
	}
	private Page<TInvitation> transObject(Page<Object[]> pageobject) {
		List<TInvitation> tInvitations=new ArrayList<TInvitation>();
		if (pageobject!=null&&pageobject.getContent()!=null) {
			List<Object[]> list=pageobject.getContent();
			for (Object[] objects : list) {
				TInvitation tInvitation=(TInvitation)objects[0];
				TUser tUser=(TUser)objects[1];
				tInvitation.settUser(tUser);
				tInvitations.add(tInvitation);
			}
		}
		Page<TInvitation> page=new PageImpl<TInvitation>(tInvitations,new PageRequest(pageobject.getNumber(), pageobject.getSize()), pageobject.getTotalElements());
		return page;
	}
}
