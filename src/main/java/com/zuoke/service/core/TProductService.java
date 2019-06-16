package com.zuoke.service.core;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zuoke.dao.core.TProductDao;
import com.zuoke.model.core.TProduct;

@Transactional
@Service("tProductService")
public class TProductService {
	private Logger logger = LoggerFactory.getLogger(TOrderService.class);
	@Autowired
	private TProductDao dao;
	public Page<TProduct> findByProductTypeOrder(String productType,PageRequest prequest){
		return dao.findByProductTypeOrderByUpdateTimeDesc(productType, prequest);
	}
	public List<TProduct> findByProductTypeOrder(String productType){
		return dao.findByProductTypeOrderByUpdateTimeDesc(productType);
	}

}
