package com.zuoke.controller.app.core;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.servlet.ModelAndView;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.sun.xml.internal.ws.api.message.AddressingUtils;
import com.wxpay.sdk.WXPay;
import com.wxpay.sdk.WXPayConfig;
import com.wxpay.sdk.WXPayUtil;
import com.zuoke.common.util.AddressUtils;
import com.zuoke.common.util.ConfigUtil;
import com.zuoke.common.util.IpKit;
import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.common.util.SystemParamUtil;
import com.zuoke.model.core.TOrder;
import com.zuoke.service.core.TOrderService;
import com.zuoke.service.core.TProductService;

@Controller
@RequestMapping("wxpay")
public class WxPayController {
	private Logger logger = LoggerFactory.getLogger(WxPayController.class);

	@Autowired
	private TOrderService tOrderService;
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private TProductService tProductService;
	@Autowired
	private SystemParamUtil systemParamUtil;
	@Autowired 
	private ConfigUtil configUtil;
	/**
	 * 支付宝支付订单
	 * @param tHelpInfo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public String add(int orderid,String subject,
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
		  Double total=tOrder.getPayMoney()*100;
			
	        //付款金额，必填
	        String total_fee = total.intValue()+"";
			
	        //收银台页面上，商品展示的超链接，必填
	        String show_url = systemParamUtil.getpayproducturl();
			
	        //商品描述，可空
	        String body = tOrder.getProductName();
			
			
			
			//////////////////////////////////////////////////////////////////////////////////
			
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("device_info", "WEB");
			sParaTemp.put("body", body);
	        sParaTemp.put("out_trade_no", out_trade_no);
	        sParaTemp.put("total_fee", total_fee);
	        //客户端ip
	        sParaTemp.put("spbill_create_ip",IpKit.getRealIp(request));
			sParaTemp.put("notify_url", "http://hehuhai.com/zuoke/app/wxpay/notify");
			sParaTemp.put("trade_type", "MWEB");
			sParaTemp.put("scene_info", "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://hehuhai.com/zuoke/app/wxpay/notify\",\"wap_name\": \"互助客\"}}");
			//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1
	        //如sParaTemp.put("参数名","参数值");
			 try {
				WXPayConfig config=new WXPayConfig(configUtil) ;
				WXPay wxPay=new WXPay(config);
				sParaTemp=wxPay.fillRequestData(sParaTemp);
				Map<String, String> map=wxPay.unifiedOrder(sParaTemp);
				return "redirect:"+map.get("mweb_url")+"&redirect_url="+URLEncoder.encode("http://hehuhai.com/zuoke/static/htm/index.html","UTF-8");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 return "redirect:/testb"; 

	}
	
	@RequestMapping(value = "/notify")
	@ResponseBody
	public String notify(
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("支付回调进入");
		//获取支付宝POST过来反馈信息
		BufferedReader reader;
		try {
			reader=request.getReader();
			String line="";
			StringBuffer inputString=new StringBuffer();
			while ((line=reader.readLine())!=null) {
				inputString.append(line);
			}
			request.getReader().close();
			logger.info("支付返回结果"+inputString.toString());
			Map<String, String> map=WXPayUtil.readStringXmlOut(inputString.toString());
			if ("SUCCESS".equals(map.get("return_code"))) {
				String out_trade_no = map.get("out_trade_no");
				String total_fee = map.get("total_fee");
				TOrder tOrder=tOrderService.findByOrderNo(out_trade_no);
				logger.info("out_trade_no:"+out_trade_no);
				logger.info("total_fee:"+total_fee);
				if (tOrder!=null) {
					logger.info("tOrder.getOrderMoney()"+tOrder.getOrderMoney());
					Double money=tOrder.getOrderMoney()*100;
					if (total_fee.equals(money.intValue()+"")) {
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
			}
			return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		return "fail";
	}
}
