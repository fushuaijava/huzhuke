package com.zuoke.common.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.zuoke.dao.core.TDataDictionaryDao;
import com.zuoke.model.core.TDataDictionary;

/**
 * @author fu
 *
 */
@Repository
public class ConfigUtil {
	private static  Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	private final static String Delimiter = "$#$";
	@Autowired
	private  TDataDictionaryDao tDataDictionaryDao;
	private Map<String, String> map;
	private Calendar outtimeDate;

	public  String getValue(String config, String key) {
		if (map==null||outtimeDate.after(Calendar.getInstance())) {
			initmap();
		}
		String keyName = config+Delimiter+key;
		if(map.containsKey(keyName)){
			return map.get(keyName);
		}else{
			logger.warn(String.format("警告配置字典表的 key:【%s】未设置！", keyName));
			return null;
		}
	
	}
	
	
	public void initmap(){
		List<TDataDictionary> list=tDataDictionaryDao.findAll();
		map=new HashMap<String, String>();
		for (TDataDictionary tDataDictionary : list) {
			map.put(tDataDictionary.getDataGroup()+Delimiter+tDataDictionary.getDataKey(), tDataDictionary.getDataValue())	;
		}
		outtimeDate=Calendar.getInstance();
		String keyouttimeminute=map.get("system$#$keyouttimeminute");
		outtimeDate.add(Calendar.MINUTE, NumberUtils.parseNumber(keyouttimeminute, Integer.class));
		
	}
	
}
