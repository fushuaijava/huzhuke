package com.zuoke.service.core;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuoke.dao.core.TCoinHistoryDao;
import com.zuoke.dao.core.TUserDao;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TCoinHistory;
import com.zuoke.model.core.TOrder;
import com.zuoke.model.core.TProduct;
import com.zuoke.model.core.TUser;

@Transactional
@Service("tCoinHistoryService")
public class TCoinHistoryService {
	private Logger logger = LoggerFactory.getLogger(TCoinHistoryService.class);
	@Autowired
	private TCoinHistoryDao dao;
	@Autowired
	private TUserDao tUserDao;
	public List<TCoinHistory> findAll() {
		return dao.findAll();
	}
	public TCoinHistory findOne(Integer id){
		return dao.findOne(id);
	}
	public List<TCoinHistory> findByUserId(int userid){
		return dao.findByUserId(userid);
	}
	public TCoinHistory insert(TCoinHistory model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		
		return dao.save(model);
	}
	/**
	 * 
	 * @param formUserid
	 * @param touserId
	 * @param coin
	 * @param transtype 0，从锁定金币扣减，1，从个人金币直接扣减，2，自动扣减
	 * @param channel
	 * @param Describe
	 * @return
	 */
	public  Meta transCoin(int formUserid,int touserId,int coin,String tradeType,String channel,String describe){
		Meta meta=new Meta();
		TUser tUser=tUserDao.findOne(formUserid);
		TUser toUser=tUserDao.findOne(touserId);
		int oldcoin1=tUser.getCoin();
		int oldlockcoin1=tUser.getLockCoin();
		int oldcoin2=toUser.getCoin();
		int oldlockcoin2=toUser.getLockCoin();
		int newcoin1=0;
		int newlockcoin1=0;
		int newcoin2=0;
		int newlockcoin2=0;
		if ("0".equals(tradeType)) {
			
			int lockCoin=tUser.getLockCoin()-coin;
			if (lockCoin>=0) {
				tUser.setLockCoin(lockCoin);
				toUser.setCoin(toUser.getCoin()+coin);
				tUserDao.save(tUser);
				tUserDao.save(toUser);
				newcoin1=tUser.getCoin();
				newlockcoin1=tUser.getLockCoin();
				newcoin2=toUser.getCoin();
				newlockcoin2=toUser.getLockCoin();
				
				
			
			}else{
				meta.setRescode(3);
				meta.setMsg("扣减金币大于被锁定金币");
			}
		}
		if (true) {
			TCoinHistory tCoinHistory1=new TCoinHistory();
			tCoinHistory1.setChannel(channel);
			tCoinHistory1.setCount(coin);
			tCoinHistory1.setCreateTime(new Date());
			tCoinHistory1.setDescInfo(describe);
			tCoinHistory1.setIsadd(0);
			tCoinHistory1.setOldcoin(oldcoin1);
			tCoinHistory1.setOldLockCoin(oldlockcoin1);
			tCoinHistory1.setNewLockCoin(newlockcoin1);
			tCoinHistory1.setNewcoin(newcoin1);
			tCoinHistory1.setIsSystem("0");
			tCoinHistory1.setTradeType(tradeType);
			tCoinHistory1.setTradeuser(toUser.getNickname());
			tCoinHistory1.setTradeUserId(toUser.getId());
			tCoinHistory1.setUpdateTime(new Date());
			tCoinHistory1.setUserId(tUser.getId());
			TCoinHistory tCoinHistory2=new TCoinHistory();
			tCoinHistory2.setChannel(channel);
			tCoinHistory2.setCount(coin);
			tCoinHistory2.setCreateTime(new Date());
			tCoinHistory2.setDescInfo(describe);
			tCoinHistory2.setIsadd(1);
			tCoinHistory2.setOldcoin(oldcoin2);
			tCoinHistory2.setOldLockCoin(oldlockcoin2);
			tCoinHistory2.setNewLockCoin(newlockcoin2);
			tCoinHistory2.setNewcoin(newcoin2);
			tCoinHistory2.setIsSystem("0");
			tCoinHistory2.setTradeType(tradeType);
			tCoinHistory2.setTradeuser(tUser.getNickname());
			tCoinHistory2.setTradeUserId(tUser.getId());
			tCoinHistory2.setUpdateTime(new Date());
			tCoinHistory2.setUserId(toUser.getId());
			dao.save(tCoinHistory1);
			dao.save(tCoinHistory2);
		}
		return null;
	}
/**
 * 只可用于充值，兑换，系统赠送
 * @param userId 用户id
 * @param count 数量
 * @param tradeType 交易类型 0交易，1系统赠送，2兑换，3充值
 * @param isAdd 0减，1增加
 * @return
 */
	public Meta addCoin(int userId, int count,String tradeType,int isAdd) {
		Meta meta=new  Meta();
		TUser tUser=tUserDao.findOne(userId);
		//增加金币
		TCoinHistory tCoinHistory=new TCoinHistory();
		tCoinHistory.setChannel("sign");
		tCoinHistory.setCount(count);
		int sendCoin=count;
		if (isAdd==0) {
			if (tUser.getCoin()<count) {
				meta.setRescode(3);
				meta.setRes1(tUser.getCoin());
				meta.setMsg("金币余额不足");
				return meta;
			}
			sendCoin=-count;
		}
		tCoinHistory.setCreateTime(new Date());
		tCoinHistory.setOldcoin(tUser.getCoin());
		tCoinHistory.setOldLockCoin(tUser.getLockCoin());
		tCoinHistory.setNewcoin(tUser.getCoin()+sendCoin);
		tCoinHistory.setNewLockCoin(tUser.getLockCoin());
		tCoinHistory.setIsSystem("1");
		tCoinHistory.setTradeType(tradeType);
		if ("3".equals(tradeType)) {
			tCoinHistory.setDescInfo("金币充值");
		}else if ("2".equals(tradeType)) {
			if (isAdd==0) {
				
				tCoinHistory.setDescInfo("金币兑换");
			}else{
				tCoinHistory.setDescInfo("金币兑换回退");
			}
		}else if ("1".equals(tradeType)) {
			tCoinHistory.setDescInfo("系统赠送");
		}
		tCoinHistory.setTradeuser("system");
		tCoinHistory.setUpdateTime(new Date());
		tCoinHistory.setUserId(tUser.getId());
		tUser.setCoin(tUser.getCoin()+sendCoin);
		tUserDao.save(tUser);
		logger.info("添加金币");
		dao.save(tCoinHistory);
		meta.setRescode(0);
		meta.setRes1(tUser.getCoin());
		return meta;
	}

}
