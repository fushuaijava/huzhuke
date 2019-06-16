package com.zuoke.service.core;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuoke.dao.core.TVipHistoryDao;
import com.zuoke.dao.core.TUserDao;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TVipHistory;
import com.zuoke.model.core.TUser;

@Transactional
@Service("tVipHistoryService")
public class TVipHistoryService {
	private Logger logger = LoggerFactory.getLogger(TVipHistoryService.class);
	@Autowired
	private TVipHistoryDao dao;
	@Autowired
	private TUserDao tUserDao;
	public List<TVipHistory> findAll() {
		return dao.findAll();
	}
	public TVipHistory findOne(Integer id){
		return dao.findOne(id);
	}
	public List<TVipHistory> findByUserId(int userid){
		return dao.findByUserId(userid);
	}
	public TVipHistory insert(TVipHistory model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		
		return dao.save(model);
	}
	public Meta addVipDate(int userId,int count,String  channel){
		TUser tUser=tUserDao.findOne(userId);
		return addVipDate(tUser,count,channel);
	}
	public Meta addVipDate(TUser tUser,int count,String  channel){
		logger.info("进入增加vip 天数方法");
		Meta meta=new Meta();
		if (tUser!=null) {
			
			Date oldvipdate=tUser.getVipEndTime();
			if (oldvipdate==null||oldvipdate.before(new Date())) {
				oldvipdate=new Date();
			}
			TVipHistory tVipHistory=new TVipHistory();
			tVipHistory.setChannel(channel);
			tVipHistory.setCount(count);
			tVipHistory.setCreateTime(new Date());
			if ("recharge".equals(channel)) {
				tVipHistory.setDescInfo("充值会员服务");
			}else if("conversion".equals(channel)){
				tVipHistory.setDescInfo("兑换会员服务");
			}
			tVipHistory.setIsadd(1);
			tVipHistory.setOldEndDate(oldvipdate);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(oldvipdate);
			calendar.add(Calendar.DATE, count);
			tVipHistory.setNewEndDate(calendar.getTime());
			tUser.setVipEndTime(calendar.getTime());
			tUserDao.save(tUser);
			dao.save(tVipHistory);
			meta.setRescode(0);
		}else{
			meta.setRescode(3);
			meta.setMsg("出错了，请您联系客服解决该问题，给您造成的不变，请您谅解。");
		}
		return meta;
	}

}
