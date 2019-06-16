package com.zuoke.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuoke.dao.core.TGuestOrderDao;
import com.zuoke.dao.core.THouseDao;
import com.zuoke.dao.core.TUserDao;
import com.zuoke.model.core.TGuestOrder;
import com.zuoke.model.core.THouse;
import com.zuoke.model.core.TUser;

@Service("tGuestOrderCommentTask")
public class TGuestOrderCommentTask {
	@Autowired
	private TGuestOrderDao tGuestOrderDao;
	@Autowired
	private THouseDao tHouseDao;
	@Autowired
	private TUserDao tUserDao;
	
	public void commentjob() {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -7);
		List<TGuestOrder> list=tGuestOrderDao.findByOrderStateAndUpdateTimeLessThan(4, calendar.getTime());
		for (TGuestOrder tGuestOrder : list) {
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
		}
	}

}
