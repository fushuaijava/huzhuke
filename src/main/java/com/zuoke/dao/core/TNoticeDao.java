package com.zuoke.dao.core;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TNotice;

public interface TNoticeDao extends JpaRepository<TNotice, Integer>,JpaSpecificationExecutor<TNotice>{
	
}
