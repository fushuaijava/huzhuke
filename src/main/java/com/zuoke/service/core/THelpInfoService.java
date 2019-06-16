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
import com.zuoke.dao.core.TUserDao;
import com.zuoke.dao.core.impl.THelpInfoReplyDaoPlusImpl;
import com.zuoke.model.Meta;
import com.zuoke.model.core.THelpInfo;
import com.zuoke.model.core.THelpInfoReply;
import com.zuoke.model.core.TUser;

@Transactional
@Service("tHelpInfoService")
public class THelpInfoService {
	private Logger logger = LoggerFactory.getLogger(THelpInfoService.class);
	@Autowired
	private THelpInfoDao dao;
	@Autowired
	private TUserDao tUserDao;
	@Autowired
	private THelpInfoReplyDao tHelpInfoReplyDao;
	@Autowired
	private THelpInfoReplyService tHelpInfoReplyService;
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private TCoinHistoryService tCoinHistoryService;
	public THelpInfo get(Integer id){
		return dao.findOne(id);
	}
	
	public Map<String, Object> createHelpInfo(THelpInfo tHelpInfo){
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta =new Meta();
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
			tUser.getCoin();
			if (tHelpInfo.getCoin()>=0&&tUser.getCoin()>=tHelpInfo.getCoin()) {
				tUser.setCoin(tUser.getCoin()-tHelpInfo.getCoin());
				tUser.setLockCoin(tUser.getLockCoin()+tHelpInfo.getCoin());
				tUser.setUpdateTime(new Date());
				tHelpInfo.settUser(tUser);
				tHelpInfo.setUserId(tUser.getId());
				tHelpInfo=insert(tHelpInfo);
				tUserDao.save(tUser);
				myCacheUtil.setSessionUser(tUser);
				map.put("tHelpInfo", tHelpInfo);
				meta.setRescode(0);
			}else{
				meta.setRescode(3);
				meta.setMsg("您的悬赏金币大于您当前可用的金币数哦，请您先到个人中心充值金币。");
			}
			
		}else{
			meta.setRescode(3);
			meta.setMsg("好像登录信息过期了哦，请您重启该应用试试。");
		}
		map.put("meta", meta);
		return map;
	}
	public THelpInfo findOne(Integer id){
		Object object=dao.get(id);
		Object[] objects=(Object[])object;
		if (objects!=null) {
			THelpInfo tHelpInfo=(THelpInfo)objects[0];
			TUser tUser=(TUser)objects[1];
			tHelpInfo.settUser(tUser);
			if (tHelpInfo.getHelpUserId()>0) {
				TUser helpUser=tUserDao.findOne(tHelpInfo.getHelpUserId());
				tHelpInfo.setHelpUser(helpUser);
			}
			return tHelpInfo;
		}
		return null;
	}
	public THelpInfo insert(THelpInfo model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public THelpInfo update(THelpInfo model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public THelpInfo updatestate(THelpInfo model){
		THelpInfo tHelpInfo=dao.findOne(model.getId());
		if (model!=null) {
			if (tHelpInfo!=null) {
				if (model.getHelpUserId()>0) {
					tHelpInfo.setHelpUserId(model.getHelpUserId());
					tHelpInfo.setHelpReplyId(model.getHelpReplyId());
				}
				tHelpInfo.setState(tHelpInfo.getState()<model.getState()?model.getState():tHelpInfo.getState());
				tHelpInfo.setUpdateTime(new Date());
				return dao.save(tHelpInfo);
			}
		}
		return null;
	}
	
	public THelpInfo delete(THelpInfo model){
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
	public Page<THelpInfo> findAll(PageRequest prequest){
		return transObject(dao.searchAll(prequest),true);
	}
	
	public Page<THelpInfo> askHelpInfos(Integer userId,PageRequest prequest){
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
		 //Page<THelpInfo> helpInfos=tHelpInfoReplyDaoPlus.askHelpInfos(prequest,tUser.getId());
			Page<THelpInfo> page=dao.findByUserIdOrderByIdDesc(userId, prequest);
			List<THelpInfo> list=page.getContent();
			if (list!=null&&list.size()>0) {
				for (THelpInfo tHelpInfo : list) {
					if (tHelpInfo.getState()>=2) {
						tHelpInfo.setHelpUser(tUserDao.findOne(tHelpInfo.getHelpUserId()));
					}else if(tHelpInfo.getState()==1){
						tHelpInfo.settHelpInfoReplys(tHelpInfoReplyService.findByHelpInfoId(tHelpInfo.getId(), new PageRequest(0, 10)));
					}
				}
			}
		 return page;
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * @param pageobject
	 * @param isSetUser  是否是设置求助者
	 * @return
	 */
	private Page<THelpInfo> transObject(Page<Object[]> pageobject,boolean isSetUser) {
		List<THelpInfo> tHelpInfos=new ArrayList<THelpInfo>();
		if (pageobject!=null&&pageobject.getContent()!=null) {
			List<Object[]> list=pageobject.getContent();
			for (Object[] objects : list) {
				THelpInfo tHelpInfo=(THelpInfo)objects[0];
				TUser tUser=(TUser)objects[1];
				if (isSetUser) {
					tHelpInfo.settUser(tUser);
				}else{
					tHelpInfo.setHelpUser(tUser);
					if (tHelpInfo.getState()==1) {
						Page<THelpInfoReply> tHelpInfoReplys=tHelpInfoReplyDao.findByHelpInfoIdAndIsdeleteAndParentIdIsNull(tHelpInfo.getId(), 0, new PageRequest(0, 10));
						tHelpInfo.settHelpInfoReplys(tHelpInfoReplys);
					}
				}
				tHelpInfos.add(tHelpInfo);
			}
		}
		Page<THelpInfo> page=new PageImpl<THelpInfo>(tHelpInfos,new PageRequest(pageobject.getNumber(), pageobject.getSize()), pageobject.getTotalElements());
		return page;
	}

	public Meta helpSendCoin(int id) {
		// TODO Auto-generated method stub
		THelpInfo tHelpInfo=findOne(id);
		int coin=tHelpInfo.getCoin();
		//TUser helpUser=tHelpInfo.getHelpUser();
		Meta meta=new Meta();
		if (coin>0) {
			if (tHelpInfo.getUserId()>0&&tHelpInfo.getHelpUserId()>0) {
				meta=tCoinHistoryService.transCoin(tHelpInfo.getUserId(), tHelpInfo.getHelpUserId(), coin, "0", "trade", "求助悬赏交易");
			}else{
				meta.setRescode(3);
				meta.setMsg("怎么没有找到用户呢，亲请您拨打客服MM电话，让她帮您解决这个问题。");
			}
		}else{
			meta.setRescode(0);
			meta.setMsg("金币为0");
		}
		if (meta.getRescode()==0) {
			tHelpInfo.setState(6);
			tHelpInfo=updatestate(tHelpInfo);
			meta.setRescode(0);
			meta.setMsg("更新成功");
		}else{
			return meta;
		}
		return meta;
	}
	
}
