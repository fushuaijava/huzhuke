package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.THouse;
import com.zuoke.model.core.THouseDesc;

public interface THouseDescDao extends JpaRepository<THouseDesc, Integer>,JpaSpecificationExecutor<THouseDesc>{
	public List<THouseDesc> findByUserId(int userid);
	public List<THouseDesc> findByTHouse(THouse tHouse);
}
