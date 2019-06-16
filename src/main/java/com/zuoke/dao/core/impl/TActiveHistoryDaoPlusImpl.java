package com.zuoke.dao.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.zuoke.dao.impl.BaseDao;
import com.zuoke.model.core.TActive;
import com.zuoke.model.core.TActiveHistory;
import com.zuoke.model.core.TUser;

@Repository("tActiveHistoryDaoPlus")
public class TActiveHistoryDaoPlusImpl extends BaseDao {

	 
	public Page<TActiveHistory> search(Integer userId,Integer activeId,PageRequest prequest){
		StringBuilder builder = new StringBuilder("from TActiveHistory o,TUser u,TActive a where o.userId=u.id and o.activeId=a.id ");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (userId!=null) {
			builder.append(" and o.userId = :userId ");
			params.put("userId", userId);
		}
		if (activeId!=null) {
			builder.append(" and o.activeId = :activeId ");
			params.put("activeId", activeId);
		}
		builder.append(" order by o.updateTime desc ");
		List<Object> list=findBySQL("select o,u,a "+builder.toString(), prequest.getPageNumber(), prequest.getPageSize(), params);
		List<TActiveHistory> tActiveHistorys=null;
		if (list!=null) {
			tActiveHistorys=new ArrayList<TActiveHistory>();
			for (Object object : list) {
				Object[] objects=(Object[])object;
				TActiveHistory tActiveHistory=(TActiveHistory)objects[0];
				TUser tUser=(TUser)objects[1];
				TActive tActive=(TActive)objects[2];
				tActiveHistory.settUser(tUser);
				tActiveHistory.settActive(tActive);
				tActiveHistorys.add(tActiveHistory);
			}
		}
		Page<TActiveHistory> page = new PageImpl<TActiveHistory>(tActiveHistorys,prequest,countBySQL("select count(*) " + builder.toString(),  params));   
        return page;
	} 

}
