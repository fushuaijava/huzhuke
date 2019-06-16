package com.zuoke.service.core;


import java.util.Date;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuoke.common.util.SystemParamUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TCoinHistory;
import com.zuoke.model.core.TSignHistory;
import com.zuoke.model.core.TUser;

@Transactional
@Service("tUserDoService")
public class TUserDoService {
	private Logger logger = LoggerFactory.getLogger(TUserDoService.class);
	@Autowired
	private TUserService tUserService;
	@Autowired
	private TSignHistoryService tSignHistoryService;
	@Autowired
	private TCoinHistoryService tCoinHistoryService;
	@Autowired
	private SystemParamUtil systemParamUtil;
	
	public Meta doSign(int id){
		logger.info("签到"+id);
		Meta meta=new Meta();
		TUser tuser=tUserService.findOne(id);
		if (tuser!=null) {
			try {
				Boolean issign=tSignHistoryService.issign();
				if (issign!=null&&issign) {
					
					meta.setRescode(3);
					meta.setMsg("今天您已经签到过了，不用重复签到");
				}
			
			//设置签到历史
			TSignHistory tSignHistory=new TSignHistory();
			tSignHistory.setBeforeSignLevel(tuser.getLevel());
			tSignHistory.setUserId(id);
			//增加经验
			tuser.setLevel(tuser.getLevel()+systemParamUtil.getSignSendLevel());
			tSignHistory.setAfterSignLevel(tuser.getLevel());
			//增加金币
			TCoinHistory tCoinHistory=new TCoinHistory();
			tCoinHistory.setChannel("sign");
			int signSendCoin=systemParamUtil.getSignSendCoin();
			tCoinHistory.setCount(signSendCoin);
			tCoinHistory.setCreateTime(new Date());
			tCoinHistory.setDescInfo("签到赠送");
			tCoinHistory.setIsadd(1);
			tCoinHistory.setOldcoin(tuser.getCoin());
			tCoinHistory.setNewcoin(tuser.getCoin()+signSendCoin);
			tCoinHistory.setIsSystem("1");
			tCoinHistory.setTradeType("1");
			tCoinHistory.setTradeuser("system");
			tCoinHistory.setUpdateTime(new Date());
			tCoinHistory.setUserId(tuser.getId());
			tuser.setCoin(tuser.getCoin()+signSendCoin);
			logger.info("添加金币");
			tCoinHistoryService.insert(tCoinHistory);
			logger.info("添加签到记录");
			tSignHistoryService.insert(tSignHistory);
			logger.info("修改用户信息");
			tUserService.update(tuser);
			logger.info("签到完成");
			meta.setRescode(0);
			} catch (Exception e) {
				meta.setRescode(3);
				logger.error(e.getMessage(),e);
				meta.setMsg("签到异常，请稍后重试");
			}
		}else{
			meta.setRescode(3);
			meta.setMsg("没有找到该用户");
		}
		
		return meta;
	}
}
