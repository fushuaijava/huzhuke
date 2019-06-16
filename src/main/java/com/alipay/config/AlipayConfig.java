package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088121797874121";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM04lejl6Mcw4oRIaKTg0J9o3etmrpsWFU9RgkNnWxGSLQucPrjDhkAAyned7Zd9XmrNkDrHJR0fqz4/KQJP2qyrP3Gr0b39ZaEyMfz2SVQc+64lKXHQqEHeLXu9kZ4UeUgkltavdymR7APZ1yFzNc+/bz6JRk3XSTglb1tjU1UHAgMBAAECgYA5y7rkaYOCPbE9ogrejHYUvvro5fU8th5wRTtTclNCgpFvGKTNcjtn4REkWPTl2ifyGkyJUTaRmi6nDDCndwfVYcRmHMre8DwHXh+2bhE1LchBssPQsa/v/VyzS8KG8X6ozOPbRhutPNhF4cdL03Df0A5TaTh1omNHDzs0+avn4QJBAPXuVwLkq+sitBynPrxvYHOMo2mZX0sEcNlbw++wgh9GbiA2AZ2/L9eZQoXsfaRj3GSfXaY6QrjNipyAd+Xl2FECQQDVn44R81RpEx8Y932bvM6vMpp0P+WeHKEbgEcTYn5PakW5eIGZflctWsagHcU9CTEMv9MF6w/5yLvbClIejtnXAkEA3L15TBeEWCFmGPHk1a4CnLR3WKlZZzQtNmuvisybfwbwgej3umeIwbPGif6jJ5Kal5aTsEhK6yVSbPbCp4+OgQJANAGzIbWeoDISiQ5/mvGdU9O6/IAt049evYY+R37cB1N1BBFNY+P5L0aBQqvcv95D/bmZk0BF1GbFLS9XDK90YwJAKBXIb8Bk6S04x+u5kAGH5YGTZ4rqgTBXT59jdYf68Hrb8i5N+UyFYSOvu+KOnfQ4TKN2nScjypzFJ/Rr9ioPLA==";
	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
											   

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://hehuhai.com/zuoke/app/pay/notify";
	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://hehuhai.com/zuoke/static/htm/recharge.html";

	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";
		
	// 字符编码格式 目前支持utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "alipay.wap.create.direct.pay.by.user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}

