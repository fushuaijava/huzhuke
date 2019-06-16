package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TUserProduct;

public interface TUserProductDao extends JpaRepository<TUserProduct, Integer>,JpaSpecificationExecutor<TUserProduct>{
	public List<TUserProduct> findByHouseId(int houseId);
}
