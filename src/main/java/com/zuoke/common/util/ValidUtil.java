package com.zuoke.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.zuoke.model.Meta;
@Repository
public class ValidUtil {
	@Autowired
	private MyCacheUtil myCacheUtil;
	/**
	 * 校验参数是否合法
	 * 
	 * @param userid
	 * @param password
	 * @param key
	 * @param logintime
	 * @param md5key
	 * @return 校验通过返回空对象 ，如果不合法 返回 Meta对象 
	 */
	public  Meta valid(String userid, String password, String key, String logintime, String md5key) {
		return validInit(userid, password, key, logintime, md5key);
	}
	public  Meta validSms(String userid, String password, String key, String logintime, String md5key) {
		Meta meta=validInit(userid, password, key, logintime, md5key);
		if (meta==null) {
			meta=new Meta();
			String code=myCacheUtil.getSmscode();
			String mobile=myCacheUtil.getSendcodeMobile();
			if (StringUtils.isEmpty(code)||myCacheUtil.IsSmscodeouttime()) {
				meta.setRescode(3);
				meta.setMsg("短信验证码已过期");
			}else if(!userid.equals(mobile)){
				meta.setRescode(3);
				meta.setMsg("短信验证手机和登录手机不一致，请重新输入");
			}else if(code.equals(password)){
				return null;
			}else{
				meta.setRescode(3);
				meta.setMsg("短信验证码不正确，请重新输入");
			}
		}
		return meta;
	}
	private  Meta validInit(String userid, String password, String key, String logintime, String md5key) {
		Meta meta = new Meta();
		if (StringUtils.isEmpty(userid)) {
			meta.setRescode(3);
			meta.setMsg("用户id不能为空");
		} else if (StringUtils.isEmpty(password)) {
			meta.setRescode(3);
			meta.setMsg("密码不能为空");
		} else if (StringUtils.isEmpty(key)) {
			meta.setRescode(3);
			meta.setMsg("密匙校验不合法");
		} else if (StringUtils.isEmpty(logintime)) {
			meta.setRescode(3);
			meta.setMsg("密匙校验不合法");
		}else if (StringUtils.isEmpty(md5key)) {
			meta.setRescode(3);
			meta.setMsg("密匙校验不合法");
		} else {
			System.out.println(userid.trim());
			System.out.println(key.trim());
			System.out.println(logintime.trim());
			String md5String = MD5Util.MD5(userid.trim() + key.trim() + logintime.trim());
			System.out.println(md5String.trim());
			if (md5key.equals(md5String)||md5key.contains(md5String)) {
				return null;
			}else{
				meta.setRescode(3);
				meta.setMsg("密匙校验不合法");
			}
		}
		return meta;
	}

}
