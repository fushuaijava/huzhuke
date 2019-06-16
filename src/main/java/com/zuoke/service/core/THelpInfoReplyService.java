package com.zuoke.service.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.dao.core.THelpInfoDao;
import com.zuoke.dao.core.THelpInfoReplyDao;
import com.zuoke.model.Meta;
import com.zuoke.model.core.THelpInfo;
import com.zuoke.model.core.THelpInfoReply;
import com.zuoke.model.core.TUser;
@Transactional
@Service("tHelpInfoReplyService")
public class THelpInfoReplyService {
	private Logger logger = LoggerFactory.getLogger(THelpInfoReplyService.class);
	@Autowired
	private THelpInfoReplyDao dao;
	@Autowired
	private THelpInfoDao tHelpInfoDao;
	@Autowired
	private MyCacheUtil myCacheUtil;
	public List<THelpInfoReply> findAll() {
		return dao.findAll();
	}
	public THelpInfoReply findOne(Integer id){
		return dao.findOne(id);
	}
	
	public List<THelpInfoReply> findByUserId(int userid){
		return dao.findByToUserId(userid);
	}
	public Page<THelpInfoReply> findByHelpInfoId(int helpinfoid,PageRequest prequest){
		//return dao.findByHelpInfoIdAndIsdeleteAndParentIdIsNull(helpinfoid, 0,prequest);
		Page<Object[]> page=dao.findByHelpInfoId(helpinfoid, prequest);
		
		return transObjectReply(page);
	}
	public Page<THelpInfoReply> findByParentId(int parentId,PageRequest prequest){
		return dao.findByParentIdAndIsdelete(parentId, 0,prequest);
	}
	/**
	 * 添加帮助回复
	 * @param tHelpInfoReply
	 * @return
	 */
	public Map<String, Object> createTHelpInfoReply(THelpInfoReply tHelpInfoReply){
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
			
		
		THelpInfo tHelpInfo=tHelpInfoDao.findOne(tHelpInfoReply.getHelpInfoId());
		//只用用户没有接受求助或取消求助的时候可以回复
		if (tHelpInfo!=null&&tHelpInfo.getState()<2) {
			tHelpInfo.setState(1);
			tHelpInfoDao.save(tHelpInfo);
			tHelpInfoReply.setToUserId(tHelpInfo.getUserId());
			tHelpInfoReply.setUserId(tUser.getId());
			tHelpInfoReply.setHelpInfoId(tHelpInfo.getId());
			tHelpInfoReply.setParentId(0);
			tHelpInfoReply=insert(tHelpInfoReply);
			map.put("tHelpInfo", tHelpInfoReply);
			meta.setRescode(0);
		}else{
			meta.setMsg("您好！该用户已经被救助，但仍感谢您的热心帮忙。");
			meta.setRescode(3);
		}
		}else{
			meta.setRescode(3);
			meta.setMsg("好像登录信息过期了哦，请您重启该应用试试。");
		}
		map.put("meta", meta);
		return map;

	}
	public Page<THelpInfoReply> myHelpInfoReplys(Integer userId,PageRequest prequest){
		 Page<THelpInfoReply> myhelpInfoReplys=transObject(dao.myHelpInfoReplys(userId,prequest),true);
		 return myhelpInfoReplys;
	}
	private Page<THelpInfoReply> transObject(Page<Object[]> pageobject,boolean isSetUser) {
		List<THelpInfoReply> tHelpInfoReplys=new ArrayList<THelpInfoReply>();
		if (pageobject!=null&&pageobject.getContent()!=null) {
			List<Object[]> list=pageobject.getContent();
			for (Object[] objects : list) {
				THelpInfoReply tHelpInfoReply=(THelpInfoReply)objects[0];
				THelpInfo tHelpInfo=(THelpInfo)objects[1];
				TUser tUser=(TUser)objects[2];
				if (isSetUser) {
					tHelpInfo.settUser(tUser);
				}else{
					tHelpInfo.setHelpUser(tUser);
				}
				tHelpInfoReply.settHelpInfo(tHelpInfo);
				tHelpInfoReplys.add(tHelpInfoReply);
			}
		}
		Page<THelpInfoReply> page=new PageImpl<THelpInfoReply>(tHelpInfoReplys,new PageRequest(pageobject.getNumber(), pageobject.getSize()), pageobject.getTotalElements());
		return page;
	}
	private Page<THelpInfoReply> transObjectReply(Page<Object[]> pageobject) {
		List<THelpInfoReply> tHelpInfoReplys=new ArrayList<THelpInfoReply>();
		if (pageobject!=null&&pageobject.getContent()!=null) {
			List<Object[]> list=pageobject.getContent();
			for (Object[] objects : list) {
				THelpInfoReply tHelpInfoReply=(THelpInfoReply)objects[0];
				TUser tUser=(TUser)objects[1];
				tHelpInfoReply.settUser(tUser);
				tHelpInfoReplys.add(tHelpInfoReply);
			}
		}
		Page<THelpInfoReply> page=new PageImpl<THelpInfoReply>(tHelpInfoReplys,new PageRequest(pageobject.getNumber(), pageobject.getSize()), pageobject.getTotalElements());
		return page;
	}
	public THelpInfoReply insert(THelpInfoReply model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		model.setIsdelete(0);
		return dao.save(model);
	}
	public THelpInfoReply update(THelpInfoReply model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public THelpInfoReply delete(THelpInfoReply model){
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
	
	
	public Page<THelpInfoReply> findAll(PageRequest prequest){
		return dao.findAll(prequest);
	}
}
