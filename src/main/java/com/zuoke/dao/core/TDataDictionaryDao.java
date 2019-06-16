package com.zuoke.dao.core;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TDataDictionary;

public interface TDataDictionaryDao extends JpaRepository<TDataDictionary, Integer>,JpaSpecificationExecutor<TDataDictionary>{
	public TDataDictionary findByDataKey(String dataKey);
}
