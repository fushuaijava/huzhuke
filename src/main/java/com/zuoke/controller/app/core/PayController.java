package com.zuoke.controller.app.core;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.apache.commons.httpclient.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.common.util.SystemParamUtil;
import com.zuoke.model.core.TOrder;
import com.zuoke.service.core.TOrderService;
import com.zuoke.service.core.TProductService;

@Controller
@RequestMapping("pay")
public class PayController {
	private Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	private TOrderService tOrderService;
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private TProductService tProductService;
	@Autowired
	private SystemParamUtil systemParamUtil;
	/**
	 * 支付宝支付订单
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public ModelAndView add(int orderid,String subject,
			HttpServletRequest request, HttpServletResponse response) {
		TOrder tOrder=tOrderService.get(orderid);
		//商户订单号，商户网站订单系统中唯一订单号，必填
		  String out_trade_no = tOrder.getOrderNo();
		  if (StringUtils.isEmpty(subject)) {
			
			  switch (tOrder.getOrderType()) {
			  case 1:
				  subject="互助客金币充值";
				  break;
			  case 2:
				  subject="互助客会员充值";
				  break;
			  case 3:
				  subject="互助客兑换支付";
				  break;
			  case 4:
				  subject="互助客商品购买";
				  break;
			  default:
				  subject="互助客订单";
				  break;
			  }
		}
			
	        //订单名称，必填
//	        String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
			
	        //付款金额，必填
	        String total_fee = tOrder.getPayMoney()+"";
			
	        //收银台页面上，商品展示的超链接，必填
	        String show_url = systemParamUtil.getpayproducturl();
			
	        //商品描述，可空
	        String body = tOrder.getProductName();
			
			
			
			//////////////////////////////////////////////////////////////////////////////////
			
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", AlipayConfig.service);
	        sParaTemp.put("partner", AlipayConfig.partner);
	        sParaTemp.put("seller_id", AlipayConfig.seller_id);
	        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", AlipayConfig.payment_type);
			sParaTemp.put("notify_url", AlipayConfig.notify_url);
			sParaTemp.put("return_url", AlipayConfig.return_url);
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("subject", subject);
			sParaTemp.put("total_fee", total_fee);
			sParaTemp.put("show_url", show_url);
			sParaTemp.put("body", body);
			//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1
	        //如sParaTemp.put("参数名","参数值");
			//建立请求
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.addObject("sHtmlText", sHtmlText);
			logger.info("sHtmlText:"+sHtmlText);
			modelAndView.setViewName("alipay/alipayapi");
			return modelAndView;

	}
	
	
	@RequestMapping(value = "/notify")
	public String notify(
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("支付回调进入");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		try {
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");
			TOrder tOrder=tOrderService.findByOrderNo(out_trade_no);
			logger.info("out_trade_no:"+out_trade_no);
			logger.info("total_fee:"+total_fee);
			if (tOrder!=null) {
				 logger.info("tOrder.getOrderMoney()"+tOrder.getOrderMoney());
				if (total_fee.equals(tOrder.getOrderMoney()+"")) {
					tOrder.setOrderState(3);
					tOrder=tOrderService.update(tOrder);
					//对订单后续操作进行处理
					tOrderService.dispose(tOrder);
				}else{
					return "fail";
				}
			}else{
				return "fail";
			}
		//支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码
			
			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			
			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				//如果有做过处理，不执行商户的业务程序
				
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				//如果有做过处理，不执行商户的业务程序
				
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
			}
			
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			
			return "success";	//请不要修改或删除
			
			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
			return "fail";
		}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		return null;
	}
}
