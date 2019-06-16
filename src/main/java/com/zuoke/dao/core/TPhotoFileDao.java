package com.zuoke.dao.core;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TPhotoFile;


public interface TPhotoFileDao extends JpaRepository<TPhotoFile, Integer>,JpaSpecificationExecutor<TPhotoFile>{
	
}
