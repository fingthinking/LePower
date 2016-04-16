package com.lesport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.PushupMapper;
import com.lesport.mapper.SitupMapper;
import com.lesport.model.UserPushup;
import com.lesport.model.UserSitup;
import com.lesport.service.PushupService;
import com.lesport.service.SitupService;

@Service
@Transactional // 此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class SitupServiceImpl implements SitupService {

	@Resource
	private SitupMapper mapper;

	public boolean save(UserSitup userSitup) {
		return mapper.save(userSitup);
	}

	@Override
	public List<UserSitup> findById(String userId,String date){
			return mapper.findById(userId,date);
	}
}
