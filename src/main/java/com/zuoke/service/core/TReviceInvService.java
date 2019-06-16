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

import com.zuoke.dao.core.TReviceInvDao;
import com.zuoke.model.core.THelpInfoReply;
import com.zuoke.model.core.TReviceInv;
import com.zuoke.model.core.TUser;
@Transactional
@Service("tReviceInvService")
public class TReviceInvService {
	private Logger logger = LoggerFactory.getLogger(TReviceInvService.class);
	@Autowired
	private TReviceInvDao dao;
	public List<TReviceInv> findAll() {
		return dao.findAll();
	}
	public TReviceInv findOne(Integer id){
		return dao.findOne(id);
	}
	
	public List<TReviceInv> findByUserId(int userid){
		return dao.findByToUserId(userid);
	}
	public Page<TReviceInv> findByinvitationId(int invitationId,PageRequest prequest){
		return transObjectReply(dao.searchByInvitationId(invitationId, prequest));
	}
	public Page<TReviceInv> findByParentId(int parentId,PageRequest prequest){
		return transObjectReply(dao.searchByParentId(parentId, prequest));
	}
	public TReviceInv insert(TReviceInv model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public TReviceInv update(TReviceInv model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public TReviceInv delete(TReviceInv model){
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
	private Page<TReviceInv> transObjectReply(Page<Object[]> pageobject) {
		List<TReviceInv> replys=new ArrayList<TReviceInv>();
		if (pageobject!=null&&pageobject.getContent()!=null) {
			List<Object[]> list=pageobject.getContent();
			for (Object[] objects : list) {
				TReviceInv reply=(TReviceInv)objects[0];
				TUser tUser=(TUser)objects[1];
				reply.setSendUser(tUser);
				if (objects.length>2) {
					TUser toUser=(TUser)objects[2];
					reply.setToUser(toUser);
				}
				replys.add(reply);
			}
		}
		Page<TReviceInv> page=new PageImpl<TReviceInv>(replys,new PageRequest(pageobject.getNumber(), pageobject.getSize()), pageobject.getTotalElements());
		return page;
	}
	
	public Page<TReviceInv> findAll(PageRequest prequest){
		return dao.findAll(prequest);
	}
}
