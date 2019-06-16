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
import com.zuoke.model.core.THelpInfo;
import com.zuoke.model.core.TUser;

@Repository("tHelpInfoReplyDaoPlus")
public class THelpInfoReplyDaoPlusImpl extends BaseDao {

	
	
	public Page<THelpInfo> askHelpInfos(PageRequest prequest,Integer userId){
		StringBuilder builder = new StringBuilder("select c,tu from THelpInfo c left  join tUser tu on c.helpUserId=tu.id  where c.userId=:userId  ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		builder.append(" order by c.createTime desc ");
		List<Object> list=findBySQL(builder.toString(), prequest.getPageNumber(), prequest.getPageSize(), params);
		List<THelpInfo> tHelpInfos=null;
		if (list!=null) {
			tHelpInfos=new ArrayList<THelpInfo>();
			for (Object object : list) {
				Object[] objects=(Object[])object;
				THelpInfo tHelpInfo=(THelpInfo)objects[0];
				TUser tUser=(TUser)objects[1];
				tHelpInfo.settUser(tUser);
				tHelpInfos.add(tHelpInfo);
			}
		}
		Page<THelpInfo> page = new PageImpl<THelpInfo>(tHelpInfos,new PageRequest(prequest.getPageNumber(),prequest.getPageSize()),30);   
        return page;
	} 

}
