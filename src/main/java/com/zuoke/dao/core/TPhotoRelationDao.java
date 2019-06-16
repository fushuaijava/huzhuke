package com.zuoke.dao.core;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TPhotoRelation;


public interface TPhotoRelationDao extends JpaRepository<TPhotoRelation, Integer>,JpaSpecificationExecutor<TPhotoRelation>{
	public List<TPhotoRelation> findByGroupTypeAndRelationTableId(String groupType,int relationTableId);
	
}
