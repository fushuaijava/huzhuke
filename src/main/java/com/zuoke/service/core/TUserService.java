package com.zuoke.service.core;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zuoke.dao.core.TUserDao;
import com.zuoke.model.core.TUser;

@Transactional
@Service("tUserService")
public class TUserService {
	private Logger logger = LoggerFactory.getLogger(TUserService.class);
	@Autowired
	private TUserDao dao;
	
	public List<TUser> findAll() {
		return dao.findAll();
	}
	public TUser findOne(Integer id){
		return dao.findOne(id);
	}
	public TUser findByUserId(String userid){
		List<TUser> list=dao.findByUserid(userid);
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	public TUser findByOpenid(String openid){
		List<TUser> list=dao.findByOpenid(openid);
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	public TUser insert(TUser model){
		logger.info("添加"+model);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public TUser update(TUser model){
		logger.info("修改"+model);
		logger.info("修改Id"+model.getId());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}
	public TUser delete(TUser model){
		if(model!=null){
			logger.info("删除"+model);
			logger.info("删除Id"+model.getId());
			model=dao.findOne(model.getId());
			model.setIsdelete(1);
			model.setUpdateTime(new Date());
			dao.save(model);
		}
		return model;
	}
	/**
	 * 创建用户
	 * @param userid
	 * @param password
	 * @return
	 */
	public TUser create(String userid, String password) {
		// TODO Auto-generated method stub
		TUser tUser=new TUser();
		tUser.setCreateTime(new Date());
		tUser.setUpdateTime(new Date());
		tUser.setPassword(password);
		tUser.setUserid(userid);
		tUser.setNickname(userid);
		tUser.setMobilephone(userid);
		
		tUser=insert(tUser);
		return tUser;
	}
	public TUser create(TUser tUser){
		tUser.setCreateTime(new Date());
		tUser.setUpdateTime(new Date());
		return insert(tUser);
	}
	public List<TUser> findByIdIn(Integer[] uids){
		return dao.findByIdIn(uids);
	}
}
