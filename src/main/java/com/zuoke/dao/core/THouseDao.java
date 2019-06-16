package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.zuoke.model.core.THouse;
import com.zuoke.model.core.TUser;

public interface THouseDao extends JpaRepository<THouse, Integer>,JpaSpecificationExecutor<THouse>{
	public Page<THouse> findBySearchString(String searchString, Pageable pageable);
	public List<THouse> findByUserId(int userid);
}
