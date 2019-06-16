package com.zuoke.service.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.common.util.SendSMSUtil;
import com.zuoke.dao.core.TGuestOrderDao;
import com.zuoke.dao.core.THouseDao;
import com.zuoke.dao.core.TUserDao;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TGuestOrder;
import com.zuoke.model.core.THouse;
import com.zuoke.model.core.TUser;
import com.zuoke.model.core.TUserProduct;

@Transactional
@Service("tGuestOrderService")
public class TGuestOrderService {
	private Logger logger = LoggerFactory.getLogger(TGuestOrderService.class);
	@Autowired
	private TGuestOrderDao dao;
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private THouseDao tHouseDao;
	@Autowired
	private TUserDao tUserDao;
	@Autowired
	private SendSMSUtil sendSMSUtil;
	@Autowired
	private TCoinHistoryService tCoinHistoryService;
	@Autowired
	private TUserProductService tUserProductService;

	public TGuestOrder insert(TGuestOrder model) {
		logger.info("添加" + model);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}

	public TGuestOrder get(int id) {
		TGuestOrder tGuestOrder = dao.findOne(id);
		THouse tHouse = tHouseDao.findOne(tGuestOrder.getHouseId());
		TUser guestUser = tUserDao.findOne(tGuestOrder.getGuestUserId());
		TUser masterUser = tUserDao.findOne(tGuestOrder.getMasterUserId());
		tGuestOrder.settHouse(tHouse);
		tGuestOrder.setGuestUser(guestUser);
		tGuestOrder.setMasterUser(masterUser);
		return tGuestOrder;
	}

	/*
	 * public Map<String, Object> create(TGuestOrder model){ Map<String, Object>
	 * map = new HashMap<String, Object>(); Meta meta =new Meta();
	 * logger.info("添加"+model); TUser tUser=myCacheUtil.getSessionUser(); THouse
	 * tHouse=tHouseDao.findOne(model.getHouseId()); if (tHouse==null) {
	 * meta.setRescode(3); meta.setMsg("咦？出错了，请您联系客服MM，我们会尽快解决这个问题。"); }else
	 * if(tUser==null){ meta.setRescode(3);
	 * meta.setMsg("咦？出错了，没找到您的用户信息，请你关闭软件重试。"); }else{ model.setIsDelete(0);
	 * model.setGuestUser(tUser); model.setGuestUserId(tUser.getId());
	 * model.setMasterUser(tHouse.gettUser());
	 * model.setMasterUserId(tHouse.getUserId()); long
	 * count=(model.getGuestEndTime().getTime()-model.getGuestStartTime().
	 * getTime())/1000/60/60/24; model.setPrePay(tHouse.getMinCoin()*count);
	 * model.setTotalPay(model.getPrePay()); model.setOrderState(0);
	 * model.setCreateTime(new Date()); model.setUpdateTime(new Date());
	 * model=dao.save(model); meta.setRescode(0);
	 * meta.setMsg("订单已经提交，我们将会用短信通知对方，您也可以和对方直接联系。"); } return map; }
	 */

	public Map<String, Object> create(TGuestOrder model) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		logger.info("添加" + model);
		TUser tUser = myCacheUtil.getSessionUser();
		THouse tHouse = tHouseDao.findOne(model.getHouseId());
		if (tHouse == null) {
			meta.setRescode(3);
			meta.setMsg("咦？出错了，请您联系客服MM，我们会尽快解决这个问题。");
		} else if (tUser == null) {
			meta.setRescode(3);
			meta.setMsg("咦？出错了，没找到您的用户信息，请你关闭软件重试。");
		} else {
			model.setIsDelete(0);
			model.setGuestUser(tUser);
			model.setGuestUserId(tUser.getId());
			model.setMasterUser(tHouse.gettUser());
			model.setMasterUserId(tHouse.getUserId());
			long count = 1;
			if (model.getGuestStartTime() != null && model.getGuestStartTime() != null) {
				count = (model.getGuestEndTime().getTime() - model.getGuestStartTime().getTime()) / 1000 / 60 / 60 / 24;
			}
			model.setPrePay(tHouse.getMinCoin() * count);
			model.setTotalPay(model.getPrePay());
			if (model.getUserproid()!=null&&model.getUserproid()>0) {
				TUserProduct tUserProduct=tUserProductService.findOne(model.getUserproid());
				model.setPrePay(tUserProduct.getMinCoin()* count);
				model.setTotalPay(model.getPrePay());
			}
			model.setOrderState(0);
			model.setCreateTime(new Date());
			model.setUpdateTime(new Date());
			model = dao.save(model);
			map.put("guestorder", model);
			meta.setRescode(0);
			meta.setMsg("订单已经提交，请尽快完成支付。");
		}
		map.put("meta", meta);
		return map;
	}

	public TGuestOrder update(TGuestOrder model) {
		logger.info("修改" + model);
		logger.info("修改Id" + model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public Map<String, Object> updateState(int id,int orderState) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		TGuestOrder model = dao.findOne(id);
		TUser tUser = myCacheUtil.getSessionUser();
		if (model == null) {
			meta.setRescode(3);
			meta.setMsg("咦？出错了，请您联系客服MM，我们会尽快解决这个问题。");
		} else if (tUser == null) {
			meta.setRescode(3);
			meta.setMsg("咦？出错了，没找到您的用户信息，请你关闭软件重试。");
		} else {
			if (orderState==4) {
				Long payvalue=Math.round(model.getTotalPay());
				tCoinHistoryService.transCoin(model.getGuestUserId(), model.getMasterUserId(), payvalue.intValue(), "0", "trade", "做客交易");
			}
			model.setOrderState(orderState);
			model.setUpdateTime(new Date());
			tUserDao.save(tUser);
			dao.save(model);
			map.put("guestorder", model);
			TUser masterUser = tUserDao.findOne(model.getMasterUserId());
			//发送通知短信
			//sendSMSUtil.guestOrderPrePaySmS(masterUser.getNickname(), tUser.getNickname(),tUser.getMobilephone(), tUser.getUserid());
			meta.setRescode(0);
			meta.setMsg("订单已经支付成功，我们将会用短信通知对方，您也可以和对方直接联系。");
		}
		map.put("meta", meta);
		return map;

	}

	public Map<String, Object> prepay(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		TGuestOrder model = dao.findOne(id);
		TUser tUser = myCacheUtil.getSessionUser();
		if (model == null) {
			meta.setRescode(3);
			meta.setMsg("咦？出错了，请您联系客服MM，我们会尽快解决这个问题。");
		} else if (tUser == null) {
			meta.setRescode(3);
			meta.setMsg("咦？出错了，没找到您的用户信息，请你关闭软件重试。");
		} else if (tUser == null && new Double(tUser.getCoin()) < model.getTotalPay()) {
			meta.setRescode(3);
			meta.setMsg("亲，您的金币不足，请先充值，然后重试");
		} else {
			Long payvalue=Math.round(model.getTotalPay());
			
			tUser.setCoin(tUser.getCoin()-payvalue.intValue());
			tUser.setLockCoin(tUser.getLockCoin()+payvalue.intValue());
			model.setOrderState(1);
			model.setPrePayTime(new Date());
			tUserDao.save(tUser);
			dao.save(model);
			map.put("guestorder", model);
			TUser masterUser = tUserDao.findOne(model.getMasterUserId());
			//发送通知短信
			sendSMSUtil.guestOrderPrePaySmS(masterUser.getNickname(), tUser.getNickname(),tUser.getMobilephone(), tUser.getUserid());
			meta.setRescode(0);
			meta.setMsg("订单已经支付成功，我们将会用短信通知对方，您也可以和对方直接联系。");
		}
		map.put("meta", meta);
		return map;

	}

	public TGuestOrder delete(TGuestOrder model) {
		if (model != null) {
			logger.info("删除" + model);
			logger.info("删除Id" + model.getId());
			model = dao.findOne(model.getId());
			model.setIsDelete(1);
			model.setUpdateTime(new Date());
			dao.save(model);
		}
		return model;
	}

	public Page<TGuestOrder> searchByGuestUserIdAll(TUser guestUser, PageRequest prequest) {
		return transObject(dao.searchByGuestUserIdAll(guestUser.getId(), prequest), guestUser, true);
	}

	private Page<TGuestOrder> transObject(Page<Object[]> pageobject, TUser tUserIn, boolean isguest) {
		List<TGuestOrder> tOrders = new ArrayList<TGuestOrder>();
		if (pageobject != null && pageobject.getContent() != null) {
			List<Object[]> list = pageobject.getContent();
			for (Object[] objects : list) {
				TGuestOrder tOrder = (TGuestOrder) objects[0];
				THouse tHouse = (THouse) objects[1];
				TUser tUser = (TUser) objects[2];
				if (isguest) {
					tOrder.setGuestUser(tUserIn);
					tOrder.setMasterUser(tUser);
				} else {
					tOrder.setGuestUser(tUser);
					tOrder.setMasterUser(tUserIn);
				}
				tOrder.settHouse(tHouse);

				tOrders.add(tOrder);
			}
		}
		Page<TGuestOrder> page = new PageImpl<TGuestOrder>(tOrders,
				new PageRequest(pageobject.getNumber(), pageobject.getSize()), pageobject.getTotalElements());
		return page;
	}

	public Page<TGuestOrder> searchByMasterUserIdAll(TUser masterUser, PageRequest prequest) {
		return transObject(dao.searchByMasterUserIdAll(masterUser.getId(), prequest), masterUser, false);
	}
}
