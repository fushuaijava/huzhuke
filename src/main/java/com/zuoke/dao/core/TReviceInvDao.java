package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zuoke.model.core.TReviceInv;

public interface TReviceInvDao extends JpaRepository<TReviceInv, Integer>,JpaSpecificationExecutor<TReviceInv>{
	public List<TReviceInv> findByToUserId(int toUserId);
	//public List<TReviceInv> findByInvitationIdAndIsDeleteAndParentIdIsNull(int invitationId,int isdelete);
	
	public Page<TReviceInv> findByInvitationIdAndIsDeleteAndParentIdIsNull(int invitationId,int isdelete,Pageable pageable);
	@Query("select r,t from TReviceInv r,TUser t where r.sendUserId=t.id and r.invitationId=:invitationId and r.parentId=0 and r.isDelete=0")
	public Page<Object[]> searchByInvitationId(@Param("invitationId")int invitationId,Pageable pageable);
	public Page<TReviceInv> findByParentIdAndIsDelete(int parentId,int isdelete,Pageable pageable);
	@Query("select r,t,to from TReviceInv r,TUser t,TUser to where r.sendUserId=t.id and r.toUserId=to.id and r.parentId=:parentId  and r.isDelete=0 ")
	public Page<Object[]> searchByParentId(@Param("parentId")int parentId,Pageable pageable);
}
