package com.zuoke.controller.app.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.common.util.SystemParamUtil;
import com.zuoke.common.util.ValidUtil;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TOrder;
import com.zuoke.model.core.TUser;
import com.zuoke.service.core.TCoinHistoryService;
import com.zuoke.service.core.TOrderService;
import com.zuoke.service.core.TUserService;

import cn.com.duiba.credits.sdk.CreditConsumeParams;
import cn.com.duiba.credits.sdk.CreditNotifyParams;
import cn.com.duiba.credits.sdk.CreditTool;
import cn.com.duiba.credits.sdk.SignTool;

@Controller
@RequestMapping("coin")
public class TCoinController {
	private Logger logger = LoggerFactory.getLogger(TCoinController.class);

	@Autowired
	private TUserService tUserService;
	@Autowired
	private ValidUtil validUtil;
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private SystemParamUtil systemParamUtil;
	@Autowired
	private TCoinHistoryService tCoinHistoryService;
	@Autowired
	private TOrderService tOrderService;

	/**
	 * 用户商品兑换
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "cost")
	@ResponseBody
	public Map<String, Object> cost(CreditConsumeParams creditconsumeparams, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("cost coin begin");
		String orderNo = systemParamUtil.getOrderNo();
		Map<String, Object> map = new HashMap<String, Object>();
		if (SignTool.signVerify(systemParamUtil.getDuibaappSecret(), request)) {
			creditconsumeparams.getCredits();
			if (!StringUtils.isEmpty(creditconsumeparams.getUid())) {

				int uid = Integer.parseInt(creditconsumeparams.getUid());
				Meta meta = tCoinHistoryService.addCoin(uid, creditconsumeparams.getCredits().intValue(), "2", 0);
				if (meta.getRescode().intValue() == 0) {
					TOrder tOrder = new TOrder();
					tOrder.setCreateTime(new Date());
					tOrder.setUpdateTime(new Date());
					tOrder.setIsDelete(0);
					tOrder.setOrderState(0);
					tOrder.setOrderType(3);
					tOrder.setOrderMoney(creditconsumeparams.getActualPrice());
					tOrder.setOrderNo(orderNo);
					tOrder.setPayMoney(creditconsumeparams.getActualPrice());
					tOrder.setProductName(creditconsumeparams.getDescription());
					tOrder.setUserId(uid);
					tOrder.setCostCoin(creditconsumeparams.getCredits().intValue());
					tOrderService.insert(tOrder);
					map.put("status", "ok");
					map.put("credits", meta.getRes1());
				} else {
					map.put("status", "fail");
					map.put("errorMessage", "金币余额不足");
					map.put("credits", meta.getRes1());
				}
			} else {
				map.put("status", "fail");
				map.put("errorMessage", "没有找到该用户");
				map.put("credits", 0);
			}

		} else {
			map.put("status", "fail");
			map.put("errorMessage", "密匙校验错误");
			map.put("credits", 0);
		}
		map.put("bizId", orderNo);
		return map;

	}

	@RequestMapping(value = "costresult")
	@ResponseBody
	public String costresult(CreditNotifyParams params, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("cost coin begin");
		Map<String, Object> map = new HashMap<String, Object>();
		if (SignTool.signVerify(systemParamUtil.getDuibaappSecret(), request)) {
			try {
				String orderNum = params.getOrderNum();
				logger.info("orderNum:"+orderNum);
				TOrder tOrder = tOrderService.findByOrderNo(params.getBizId());
				if (tOrder.getOrderState()<2) {
					if (params.isSuccess()) {
						// 兑换成功
						tOrder.setOrderState(2);
						
					} else {
						// 兑换失败，根据orderNum，对用户的金币进行返还，回滚操作
						tOrder.setOrderState(11);
						Meta meta = tCoinHistoryService.addCoin(tOrder.getUserId(), tOrder.getCostCoin(), "2",
								1);
					}
					tOrderService.update(tOrder);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			map.put("status", "fail");
			map.put("errorMessage", "密匙校验错误");
			map.put("credits", 0);
		}
		return "ok";

	}
	/**
	 * 兑吧登录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/duiba/login")
    public String duibaLogin() {
		CreditTool tool=new CreditTool(systemParamUtil.getDuibaappkey(), systemParamUtil.getDuibaappSecret());
		Map params=new HashMap();
		TUser tUser=myCacheUtil.getSessionUser();
		if (tUser!=null) {
			
			params.put("uid",tUser.getId()+"");
			params.put("credits",tUser.getCoin()+"");
			//params.put("appKey", systemParamUtil.getDuibaappkey());
			//params.put("timestamp", new Date().getTime()+"");
			String redirect=systemParamUtil.getDuibaloginredirecturl();
			if(!StringUtils.isEmpty(redirect)){
				//redirect是目标页面地址，默认积分商城首页是：http://www.duiba.com.cn/chome/index
				//此处请设置成一个外部传进来的参数，方便运营灵活配置
				params.put("redirect",redirect);
			}
			String url=tool.buildUrlWithSign("http://www.duiba.com.cn/autoLogin/autologin?",params);
			return "redirect:"+url;
		}else{
			return "redirect:http://hehuhai.com/zuoke/static/htm/index.html";
		}
    }

	/*
	 * private boolean isvalid(CreditConsumeParams creditconsumeparams) { //
	 * boolean isvalid=false; StringBuilder builder = new StringBuilder();
	 * builder.append(creditconsumeparams.getActualPrice());
	 * builder.append(SystemParamUtil.getDuibaappkey());
	 * builder.append(SystemParamUtil.getDuibaappSecret());
	 * builder.append(creditconsumeparams.getCredits());
	 * builder.append(creditconsumeparams.getDescription());
	 * builder.append(creditconsumeparams.getFacePrice());
	 * builder.append(creditconsumeparams.getIp());
	 * builder.append(creditconsumeparams.getOrderNum());
	 * builder.append(creditconsumeparams.getParams()); //
	 * builder.append(creditconsumeparams.getSign());
	 * builder.append(creditconsumeparams.getTimestamp());
	 * builder.append(creditconsumeparams.getType());
	 * builder.append(creditconsumeparams.getUid());
	 * builder.append(creditconsumeparams.getWaitAudit()); logger.info(
	 * "CreditConsumeParams String:" + builder.toString()); String md5String =
	 * MD5Util.MD5(builder.toString());
	 * 
	 * return md5String.equals(creditconsumeparams.getSign()); }
	 */

}
