package com.zuoke.dao.core;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zuoke.model.core.TGuestOrder;

public interface TGuestOrderDao extends JpaRepository<TGuestOrder, Integer>,JpaSpecificationExecutor<TGuestOrder>{
	@Query("select c,h,t from TGuestOrder c,THouse h,TUser t where c.houseId=h.id and c.masterUserId=t.id and c.guestUserId=:userId order by c.createTime desc  ")
	public Page<Object[]> searchByGuestUserIdAll(@Param("userId")int guestUserId,Pageable pageable);
	@Query("select c,h,t from TGuestOrder c,THouse h,TUser t where c.houseId=h.id and c.guestUserId=t.id and c.masterUserId=:userId order by c.createTime desc  ")
	public Page<Object[]> searchByMasterUserIdAll(@Param("userId")int masterUserId,Pageable pageable);
	public List<TGuestOrder> findByOrderStateAndUpdateTimeLessThan(int orderstate,Date updateTime);
	
}
