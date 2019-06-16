package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TMember;

public interface TMemberDao extends JpaRepository<TMember, Integer>,JpaSpecificationExecutor<TMember>{
	public List<TMember> findByUserId(int userid);
}
