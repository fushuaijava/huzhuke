package com.zuoke.dao.core;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TProduct;


public interface TProductDao extends JpaRepository<TProduct, Integer>,JpaSpecificationExecutor<TProduct>{
	public List<TProduct> findByProductTypeOrderByUpdateTimeDesc(String productType);
	public Page<TProduct> findByProductTypeOrderByUpdateTimeDesc(String productType,Pageable pageable);
}
