package com.zuoke.dao.system;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.zuoke.model.system.SysModule;


public interface SysModuleDao extends JpaRepository<SysModule, Integer>,JpaSpecificationExecutor<SysModule>{
	@Query("select m from SysModule m ")
	public List<SysModule> findByUserid(String userid);
	public List<SysModule> findAll();
	
}
