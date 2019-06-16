package com.zuoke.dao.core;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TUser;

public interface TUserDao extends JpaRepository<TUser, Integer>,JpaSpecificationExecutor<TUser>{
	public List<TUser> findByUserid(String userid);
	public List<TUser> findByOpenid(String openid);
	public List<TUser> findByIdIn(Integer[] uids);
}
