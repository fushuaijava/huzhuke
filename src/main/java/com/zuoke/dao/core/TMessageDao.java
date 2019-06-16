package com.zuoke.dao.core;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zuoke.model.core.TMessage;

public interface TMessageDao extends JpaRepository<TMessage, Integer>,JpaSpecificationExecutor<TMessage>{
	public List<TMessage> findByAcceptUserId(int userid);
	public List<TMessage> findByAcceptUserIdAndSendUserId(int acceptUserId,int sendUserId);
}
