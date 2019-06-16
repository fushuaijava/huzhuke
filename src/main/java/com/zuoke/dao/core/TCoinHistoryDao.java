package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TCoinHistory;

public interface TCoinHistoryDao extends JpaRepository<TCoinHistory, Integer>,JpaSpecificationExecutor<TCoinHistory>{
	public List<TCoinHistory> findByUserId(int userid);
}
