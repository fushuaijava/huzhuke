package com.zuoke.dao.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TActive;

public interface TActiveDao extends JpaRepository<TActive, Integer>,JpaSpecificationExecutor<TActive>{
	public Page<TActive> findByOrderByInsertTimeDesc(Pageable pageable);
}
