package com.zuoke.dao.core;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zuoke.model.core.THelpInfo;

public interface THelpInfoDao extends JpaRepository<THelpInfo, Integer>,JpaSpecificationExecutor<THelpInfo>{

	@Query("select c,t from THelpInfo c,TUser t where c.userId=t.id and c.state<2  ")
	public Page<Object[]> searchAll(Pageable pageable);
	public Page<THelpInfo> findByUserIdOrderByIdDesc(Integer userId,Pageable pageable);
	@Query("select c,t from THelpInfo c , TUser t where c.helpUserId=t.id  and c.userId=:userId ")
	public Page<Object[]> askHelpInfos(@Param("userId")Integer userId,Pageable pageable);
	@Query("select c,t from THelpInfo c,TUser t where c.userId=t.id and c.id=:id  ")
	public Object get(@Param("id")Integer id);
}
