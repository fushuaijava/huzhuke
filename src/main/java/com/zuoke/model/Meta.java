package com.zuoke.model;

public class Meta {
	//0:为成功1:为成功，2,测试，3，失败,40001：系统内部错误，40002:系统运行异常，41001：没有登录；41002：没有权限；
	private Integer rescode;
	// //这里是成功或者失败时展示给用户的提示信息文案
	private String msg;
	//需要前端重置跳转的链接，留空则停留原页面
	private String url;
	private Integer res1;
	private String res2;
	private Object object;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getRescode() {
		return rescode;
	}
	public void setRescode(Integer rescode) {
		this.rescode = rescode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getRes1() {
		return res1;
	}
	public void setRes1(Integer res1) {
		this.res1 = res1;
	}
	public String getRes2() {
		return res2;
	}
	public void setRes2(String res2) {
		this.res2 = res2;
	}
	
}
