package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TIndividuation;

public interface TIndividuationDao extends JpaRepository<TIndividuation, Integer>,JpaSpecificationExecutor<TIndividuation>{
	public List<TIndividuation> findByUserId(int userid);
}
