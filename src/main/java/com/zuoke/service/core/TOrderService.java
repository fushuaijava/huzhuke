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
import com.zuoke.common.util.SystemParamUtil;
import com.zuoke.dao.core.TOrderDao;
import com.zuoke.dao.core.TProductDao;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TCoinHistory;
import com.zuoke.model.core.TOrder;
import com.zuoke.model.core.TProduct;
import com.zuoke.model.core.TUser;

@Transactional
@Service("tOrderService")
public class TOrderService {
	private Logger logger = LoggerFactory.getLogger(TOrderService.class);
	@Autowired
	private TOrderDao dao;
	@Autowired
	private TProductDao tProductDao;
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private TUserService tUserService;
	@Autowired
	private TCoinHistoryService tCoinHistoryService;
	@Autowired
	private SystemParamUtil systemParamUtil;
	@Autowired
	private TVipHistoryService tVipHistoryService;

	public TOrder insert(TOrder model) {
		logger.info("添加" + model);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		model.setIsDelete(0);
		return dao.save(model);
	}

	public TOrder get(int id) {
		return dao.findOne(id);
	}

	public TOrder findByOrderNo(String orderNo) {
		return dao.findByOrderNo(orderNo);
	}

	public Map<String, Object> create(TOrder tOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		logger.info("添加" + tOrder);
		TUser tUser = myCacheUtil.getSessionUser();
		if (tUser != null) {

			if (tOrder.getProductId() > 0) {
				TProduct tProduct = tProductDao.findOne(tOrder.getProductId());
				tOrder.setUserId(tUser.getId());
				tOrder.settUser(tUser);
				tOrder.setOrderMoney(tProduct.getNowMoney());
				tOrder.setOrderState(0);
				tOrder.setPayMoney(tProduct.getNowMoney());
				tOrder.setProductName(tProduct.getProductName());
				tOrder.settProduct(tProduct);
				tOrder.setOrderNo(systemParamUtil.getOrderNo());

				try {

					tOrder = insert(tOrder);
					if (tOrder != null) {
						map.put("tOrder", tOrder);
						meta.setRescode(0);
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.info(e.getMessage(), e);
					meta.setRescode(3);
					meta.setMsg("咦，数据出错了，请您稍后重试，谢谢您的配合，构建最好的互助旅行生态圈，我们一起努力！");
				}
			} else {
				meta.setRescode(3);
				meta.setMsg("咦，数据出错了，请联系客服解决！");
			}

		} else {
			meta.setRescode(3);
			meta.setMsg("亲，您还没有登录，请登录后重试！");
		}
		map.put("meta", meta);
		return map;
	}

	public TOrder update(TOrder model) {
		logger.info("修改" + model);
		logger.info("修改Id" + model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}

	public TOrder delete(TOrder model) {
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

	public Page<TOrder> searchByUserIdAll(int userId, PageRequest prequest) {
		return transObject(dao.searchByUserIdAll(userId, prequest));
	}

	private Page<TOrder> transObject(Page<Object[]> pageobject) {
		List<TOrder> tOrders = new ArrayList<TOrder>();
		if (pageobject != null && pageobject.getContent() != null) {
			List<Object[]> list = pageobject.getContent();
			for (Object[] objects : list) {
				TOrder tOrder = (TOrder) objects[0];
				TProduct tProduct = (TProduct) objects[1];
				tOrder.settProduct(tProduct);
				tOrders.add(tOrder);
			}
		}
		Page<TOrder> page = new PageImpl<TOrder>(tOrders, new PageRequest(pageobject.getNumber(), pageobject.getSize()),
				pageobject.getTotalElements());
		return page;
	}

	/**
	 * 处理订单
	 * 
	 * @param tOrder
	 * @return
	 */
	public Map<String, Object> dispose(int orderId) {
		return dispose(dao.findOne(orderId));
	}

	/**
	 * 处理订单
	 * 
	 * @param tOrder
	 * @return
	 */
	public Map<String, Object> dispose(TOrder tOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		if (tOrder.getOrderState() == 3) {
			if (tOrder.getOrderType() == 1) {
				meta = rechargeCoin(tOrder);
			} else if (tOrder.getOrderType() == 2) {
				meta = rechargeVip(tOrder);
			}
		}
		return map;
	}

	public Meta rechargeVip(TOrder tOrder) {
		logger.info("充值vip进入");
		Meta meta = new Meta();
		TProduct tProduct = tProductDao.findOne(tOrder.getProductId());
		if (tProduct != null && tOrder != null) {
			return tVipHistoryService.addVipDate(tOrder.getUserId(), tProduct.getProductValue(), "recharge");
		} else {
			logger.error("充值金币，订单产品为空或者订单为空");
		}
		return meta;
	}

	/**
	 * 充值金币
	 * 
	 * @param tOrder
	 * @return
	 */
	public Meta rechargeCoin(TOrder tOrder) {
		Meta meta = new Meta();
		TProduct tProduct = tProductDao.findOne(tOrder.getProductId());
		if (tProduct != null && tOrder != null) {

			return tCoinHistoryService.addCoin(tOrder.getUserId(), tProduct.getProductValue(), "3", 1);
		} else {
			logger.error("充值金币，订单产品为空或者订单为空");
		}
		return meta;
	}

}
