package com.zuoke.dao.system;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.system.SysUser;


public interface SysUserDao extends JpaRepository<SysUser, Integer>,JpaSpecificationExecutor<SysUser>{
	public List<SysUser> findByAccount(String Account);
	public List<SysUser> findAll();
	
}
