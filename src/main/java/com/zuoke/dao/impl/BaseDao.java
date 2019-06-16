package com.zuoke.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


public class BaseDao {
	@PersistenceContext 
	protected EntityManager em; 
	private Logger logger = LoggerFactory.getLogger(BaseDao.class);
	
	/**
	 * 
	 * @param sql 
	 * @param pageId 页码
	 * @param rowNum 行数
	 * @param params 参数列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Object> findBySQL(String sql, int pageId, int rowNum,Map<String, Object> params) {
		try {
			logger.info("sql:"+sql+",\n pageid:"+pageId+",\n rownum:"+rowNum+",\n params="+params.toString());
			Query query = em.createQuery(sql);
			if (params!=null) {
				Set<String> keySet=params.keySet();
				for (String keyString : keySet) {
					query.setParameter(keyString, params.get(keyString));
				}
				
			}
				query.setFirstResult((pageId) * rowNum).setMaxResults(rowNum);
			List result = query.getResultList();
			if (result!=null) {
				result.size();
				logger.info("result:"+result.size());
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
		}
	}
	@SuppressWarnings("rawtypes")
	public List findByNativeSQL(String sql, int pageId, int rowNum,Map<String, Object> params) {
		try {
			logger.info("sql:"+sql+",\n pageid:"+pageId+",\n rownum:"+rowNum+",\n params="+params.toString());
			Query query = em.createNativeQuery(sql);
			if (params!=null) {
				Set<String> keySet=params.keySet();
				for (String keyString : keySet) {
					query.setParameter(keyString, params.get(keyString));
				}
			}
				query.setFirstResult((pageId) * rowNum).setMaxResults(rowNum);
			List result = query.getResultList();
			if (result!=null) {
				result.size();
				logger.info("result:"+result.size());
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
		}
	}
	/**
	 * 
	 * @param sql 
	 * @param pageId 页码
	 * @param rowNum 行数
	 * @param params 参数列表
	 * @return
	 */
	public Long countBySQL(String sql,  Map<String, Object> params) {
		try {
			logger.info("sql:"+sql);
			Query query = em.createQuery(sql);
			if (params!=null) {
			Set<String> keySet=params.keySet();
			for (String keyString : keySet) {
				query.setParameter(keyString, params.get(keyString));
			}
			}
			Long result = (Long) query.getSingleResult();
			logger.info("result:"+result);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
		}
	}
	public void save(Object object){
		em.persist(object);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> findNativeSql(String sql){
		javax.persistence.Query query=em.createNativeQuery(sql);
		return query.getResultList();
	}	
}
