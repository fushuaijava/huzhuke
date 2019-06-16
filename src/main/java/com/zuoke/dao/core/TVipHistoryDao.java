package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TVipHistory;

public interface TVipHistoryDao extends JpaRepository<TVipHistory, Integer>,JpaSpecificationExecutor<TVipHistory>{
	public List<TVipHistory> findByUserId(int userid);
}
