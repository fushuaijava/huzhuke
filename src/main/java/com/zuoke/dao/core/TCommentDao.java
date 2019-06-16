package com.zuoke.dao.core;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zuoke.model.core.TComment;

public interface TCommentDao extends JpaRepository<TComment, Integer>,JpaSpecificationExecutor<TComment>{
	//public Page<TComment> findByHouseid(int houseid,Pageable pageable);
	@Query("select c,t from TComment c,TUser t where c.userId=t.id and c.houseid=:houseid order by c.createTime desc  ")
	public Page<Object[]> searchByHouseid(@Param("houseid")int houseid,Pageable pageable);
	@Query("select c,t from TComment c,TUser t where c.userId=t.id order by c.createTime desc  ")
	public Page<Object[]> searchAll(Pageable pageable);
}
