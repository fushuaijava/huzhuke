package com.zuoke.controller.wx;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("wxmain")
public class WxMainController {
	@RequestMapping(value = "/checktoken")
	@ResponseBody
	public String checktoken(String signature,String timestamp,String nonce,String echostr){
		return echostr;
	}
	

}
