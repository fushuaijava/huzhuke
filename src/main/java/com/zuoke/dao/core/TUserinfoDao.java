package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TUserinfo;

public interface TUserinfoDao extends JpaRepository<TUserinfo, Integer>,JpaSpecificationExecutor<TUserinfo>{
	public List<TUserinfo> findByUserId(int userid);
	public List<TUserinfo> findByIdcard(String idCard);
	public Page<TUserinfo> findByIsValidInfo(int isValidInfo,Pageable pageable);
}
