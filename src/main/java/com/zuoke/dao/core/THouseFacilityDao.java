package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.THouseFacility;

public interface THouseFacilityDao extends JpaRepository<THouseFacility, Integer>,JpaSpecificationExecutor<THouseFacility>{
	public List<THouseFacility> findByUserId(int userid);
	public List<THouseFacility> findByHouseId(int houseId);
}
