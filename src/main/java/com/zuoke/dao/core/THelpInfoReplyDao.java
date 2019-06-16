package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zuoke.model.core.THelpInfoReply;

public interface THelpInfoReplyDao extends JpaRepository<THelpInfoReply, Integer>,JpaSpecificationExecutor<THelpInfoReply>{
	public List<THelpInfoReply> findByUserId(int userid);

	public List<THelpInfoReply> findByToUserId(int userid);
	public Page<THelpInfoReply> findByHelpInfoIdAndIsdeleteAndParentIdIsNull(int helpInfoId,int isdelete,Pageable pageable);
	@Query("select r,t from THelpInfoReply r,TUser t where r.userId=t.id and r.helpInfoId=:helpInfoId and r.parentId=0 order by r.createTime desc ")
	public Page<Object[]> findByHelpInfoId(@Param("helpInfoId")int helpInfoId,Pageable pageable);
	public Page<THelpInfoReply> findByParentIdAndIsdelete(int parentId,int isdelete,Pageable pageable);
	@Query("select r,c,t from THelpInfo c ,TUser t,THelpInfoReply r where r.userId=:userId and c.userId=t.id and r.helpInfoId=c.id ")
	public Page<Object[]> myHelpInfoReplys(@Param("userId")Integer userId,Pageable pageable);
}
