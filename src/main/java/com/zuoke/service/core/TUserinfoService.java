package com.zuoke.service.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zuoke.common.util.MyCacheUtil;
import com.zuoke.common.util.SystemParamUtil;
import com.zuoke.dao.core.TUserDao;
import com.zuoke.dao.core.TUserinfoDao;
import com.zuoke.model.Meta;
import com.zuoke.model.core.TUser;
import com.zuoke.model.core.TUserinfo;

@Transactional
@Service("tUserinfoService")
public class TUserinfoService {
	private Logger logger = LoggerFactory.getLogger(TUserinfoService.class);
	@Autowired
	private TUserinfoDao dao;
	@Autowired
	private TUserDao tUserDao;
	@Autowired
	private MyCacheUtil myCacheUtil;
	@Autowired
	private TCoinHistoryService tCoinHistoryService;
	@Autowired
	private SystemParamUtil systemParamUtil;

	public List<TUserinfo> findAll() {
		return dao.findAll();
	}

	public TUserinfo findOne(Integer id) {
		return dao.findOne(id);
	}

	public List<TUserinfo> findByUserId(int userid) {
		return dao.findByUserId(userid);
	}
	

	public TUserinfo insert(TUserinfo model) {
		logger.info("添加" + model);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		return dao.save(model);
	}

	public Map<String, Object> save(TUserinfo model) {
		Map<String, Object> map = new HashMap<String, Object>();
		Meta meta = new Meta();
		TUser tUser = myCacheUtil.getSessionUser();
		if (tUser != null) {
			logger.info("修改" + model);
			logger.info("修改Id" + model.getId());
			logger.info("修改Id" + tUser.getId());
			model.setUserId(tUser.getId());
			model.setCreateTime(new Date());
			model.setUpdateTime(new Date());
			model.setIsdelete(0);
			model.setIsValidInfo(0);
			if (model.getId() <= 0) {
				List<TUserinfo> list = dao.findByIdcard(model.getIdcard());
				if (list != null && list.size() > 0) {
					meta.setRescode(3);
					meta.setMsg("亲，您的身份证已经被验证过，不能重复验证，如有疑问请联系我们 。");
					map.put("meta", meta);
					return map;
				}
			}
			dao.save(model);
			meta.setRescode(0);
			meta.setMsg("亲，实名信息已经提交，我们会尽快处理，构建最好的互助旅行生态圈，我们一起努力 ！");
			map.put("meta", meta);
		} else {
			meta.setRescode(3);
			meta.setMsg("用户登录信息过期，请在首页点击用户头像进行登陆.");
		}
		return map;
	}

	public TUserinfo delete(TUserinfo model) {
		if (model != null) {
			logger.info("删除" + model);
			logger.info("删除Id" + model.getId());
			model = dao.findOne(model.getId());
			model.setIsdelete(1);
			model.setUpdateTime(new Date());
			dao.save(model);
		}
		return model;
	}

	public Page<TUserinfo> findNoValid(PageRequest prequest) {
		// TODO Auto-generated method stub

		return dao.findByIsValidInfo(0, prequest);
	}

	public TUserinfo validUserInfo(TUserinfo tUserinfo) {
		if (tUserinfo != null && tUserinfo.getId() > 0) {
			TUserinfo model = dao.findOne(tUserinfo.getId());
			if (model!=null&&tUserinfo.getIsValidInfo()==1) {
				TUser tUser=tUserDao.findOne(model.getUserId());
				tUser.setIsValidInfo(1);
				tUserDao.save(tUser);
				tCoinHistoryService.addCoin(tUser.getId(), systemParamUtil.getUserInfoSendCoin(), "1", 1);
			}
			model.setIsValidInfo(tUserinfo.getIsValidInfo());
			dao.save(model);
			return model;
		}
		return null;
	}

}
