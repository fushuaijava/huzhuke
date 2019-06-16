package com.zuoke.common.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.response.OpenimUsersAddResponse;
@Repository
public class AlbcIMUtil {
	@Autowired
	private  SystemParamUtil systemParamUtil;
	private static  Logger logger = LoggerFactory.getLogger(AlbcIMUtil.class);
	public void addUser(String userid,String nickName,String mobile){
		addUser(userid, nickName, mobile, null);
	}
	public void addUser(String userid,String nickName,String mobile,String headimg){
		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", systemParamUtil.getAlbcappkey(),systemParamUtil.getAlbcappSecret());
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();
		Userinfos obj3 = new Userinfos();
		obj3.setNick(nickName);
		obj3.setIconUrl(headimg);
		//obj3.setEmail("uid@taobao.com");
		obj3.setMobile(mobile);
		//obj3.setTaobaoid("tbnick123");
		obj3.setUserid(userid);
		obj3.setPassword(getIMPWD(userid));
	/*	obj3.setRemark("demo");
		obj3.setExtra("{}");
		obj3.setCareer("demo");
		obj3.setVip("{}");
		obj3.setAddress("demo");
		obj3.setName("demo");
		obj3.setAge(123L);
		obj3.setGender("M");
		obj3.setWechat("demo");
		obj3.setQq("demo");
		obj3.setWeibo("demo");*/
		OpenimUsersAddResponse rsp;
		List<Userinfos> list2 = new ArrayList<Userinfos>();
		list2.add(obj3);
		req.setUserinfos(list2);
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
	}
	public void updateUser(String userid,String nickName,String mobile){
		TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",  systemParamUtil.getAlbcappkey(),systemParamUtil.getAlbcappSecret());
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();
		List<Userinfos> list2 = new ArrayList<Userinfos>();
		req.setUserinfos(list2);
		Userinfos obj3 = new Userinfos();
		list2.add(obj3);
		obj3.setNick(nickName);
		//obj3.setIconUrl("http://xxx.com/xxx");
		//obj3.setEmail("uid@taobao.com");
		obj3.setMobile(mobile);
		//obj3.setTaobaoid("tbnick123");
		obj3.setUserid(userid);
		obj3.setPassword(getIMPWD(userid));
	/*	obj3.setRemark("demo");
		obj3.setExtra("{}");
		obj3.setCareer("demo");
		obj3.setVip("{}");
		obj3.setAddress("demo");
		obj3.setName("demo");
		obj3.setAge(123L);
		obj3.setGender("M");
		obj3.setWechat("demo");
		obj3.setQq("demo");
		obj3.setWeibo("demo");*/
		OpenimUsersAddResponse rsp;
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
	}
	public String getIMPWD(String userid){
		return MD5Util.MD5(userid+"huzhuke");
	}

}
