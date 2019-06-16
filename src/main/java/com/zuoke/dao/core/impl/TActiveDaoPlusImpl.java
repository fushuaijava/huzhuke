package com.zuoke.dao.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.zuoke.dao.impl.BaseDao;
import com.zuoke.model.core.TActive;
import com.zuoke.model.core.TUser;

@Repository("tActiveDaoPlus")
public class TActiveDaoPlusImpl extends BaseDao {

	public Map<String, Object> search(String searchString, int pageId, int rowNum) {
		String[] searcharray = searchString.split(" ");
		StringBuilder builder = new StringBuilder("from TActive o where 1=1 ");
		Map<String, Object> params = new HashMap<String, Object>();
		for (int i = 0; i < searcharray.length; i++) {
			String tempSearch = searcharray[i];
			if (!StringUtils.isEmpty(tempSearch)) {
				builder.append(" and o.searchString like :tempSearch" + i + " ");
				params.put("tempSearch" + i, "%" + tempSearch + "%");
			}
		}
		builder.append("order by o.updateTime desc ");
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("success", "true");
		rtnMap.put("total", countBySQL("select count(*) " + builder.toString(), params).intValue());
		rtnMap.put("rows", findBySQL(builder.toString(), pageId, rowNum, params));
		return rtnMap;
	}
	public Page<TActive> findAll(PageRequest prequest){  
        return search(prequest, null);
	} 
	public Page<TActive> search(PageRequest prequest,String searchString){
		StringBuilder builder = new StringBuilder("select o,u from TActive o,TUser u where o.userId=u.id ");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (!StringUtils.isEmpty(searchString)) {
			String[] searcharray = searchString.split(" ");
			for (int i = 0; i < searcharray.length; i++) {
				String tempSearch = searcharray[i];
				if (!StringUtils.isEmpty(tempSearch)) {
					builder.append(" and o.searchString like :tempSearch" + i + " ");
					params.put("tempSearch" + i, "%" + tempSearch + "%");
				}
			}
		}
		builder.append(" order by o.updateTime desc ");
		List<Object> list=findBySQL(builder.toString(), prequest.getPageNumber(), prequest.getPageSize(), params);
		List<TActive> tActives=null;
		if (list!=null) {
			tActives=new ArrayList<TActive>();
			for (Object object : list) {
				Object[] objects=(Object[])object;
				TActive tActive=(TActive)objects[0];
				TUser tUser=(TUser)objects[1];
				tActive.settUser(tUser);
				tActives.add(tActive);
			}
		}
		Page<TActive> page = new PageImpl<TActive>(tActives,prequest,countBySQL("select count(*) " + builder.toString(),  params));   
        return page;
	} 

}
