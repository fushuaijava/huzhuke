package com.zuoke.dao.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TSignHistory;

public interface TSignHistoryDao extends JpaRepository<TSignHistory, Integer>,JpaSpecificationExecutor<TSignHistory>{
	public List<TSignHistory> findByUserId(int userid);
	public List<TSignHistory> findByUserIdAndCreateTimeGreaterThan(int userid,Date createTime);
}
