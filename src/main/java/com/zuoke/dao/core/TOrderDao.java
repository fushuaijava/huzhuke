package com.zuoke.dao.core;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zuoke.model.core.TOrder;

public interface TOrderDao extends JpaRepository<TOrder, Integer>,JpaSpecificationExecutor<TOrder>{
	@Query("select c,t from TOrder c,TProduct t where c.productId=t.id and c.userId=:userId order by c.createTime desc  ")
	public Page<Object[]> searchByUserIdAll(@Param("userId")int userId,Pageable pageable);
	public TOrder findByOrderNo(String orderNo);
}
