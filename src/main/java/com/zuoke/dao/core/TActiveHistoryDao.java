package com.zuoke.dao.core;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TActiveHistory;

public interface TActiveHistoryDao extends JpaRepository<TActiveHistory, Integer>,JpaSpecificationExecutor<TActiveHistory>{
	public Page<TActiveHistory> findByuserIdOrderByInsertTime(int userId,Pageable pageable);
	public List<TActiveHistory> findByUserIdAndActiveId(Integer userId,Integer activeId);
	
}
