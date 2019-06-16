package com.zuoke.service.core;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.dao.core.TSignHistoryDao;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TSignHistory;
import com.zuoke.model.core.TUser;

@Transactional
@Service("tSignHistoryService")
public class TSignHistoryService {
	private Logger logger = LoggerFactory.getLogger(TSignHistoryService.class);
	@Autowired
	private TSignHistoryDao dao;
	@Autowired
	private MyCacheUtil myCacheUtil;
	public List<TSignHistory> findAll() {
		return dao.findAll();
	}
	public TSignHistory findOne(Integer id){
		return dao.findOne(id);
	}
	public List<TSignHistory> findByUserId(int userid){
		return dao.findByUserId(userid);
	}
	public Boolean issign(){
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			List<TSignHistory> list=dao.findByUserIdAndCreateTimeGreaterThan(tUser.getId(), calendar.getTime());
			if (list!=null&&list.size()>0) {
				return true;
			}
		}else{
			return null;
		}
		return false;
	}
	public TSignHistory insert(TSignHistory model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		return dao.save(model);
	}


}
