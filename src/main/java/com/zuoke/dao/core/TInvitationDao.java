package com.zuoke.dao.core;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.zuoke.model.core.TInvitation;

public interface TInvitationDao extends JpaRepository<TInvitation, Integer>,JpaSpecificationExecutor<TInvitation>{
	@Query("select c,t from TInvitation c,TUser t where c.userId=t.id and c.isdelete=0 order by c.createTime desc  ")
	public Page<Object[]> searchAll(Pageable pageable);
	@Query("select c,t from TInvitation c,TUser t where c.userId=t.id and c.id=?1  ")
	public Object get(Integer id);
	
}
