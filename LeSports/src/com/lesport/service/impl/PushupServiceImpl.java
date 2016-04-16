package com.lesport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.PushupMapper;
import com.lesport.model.UserPushup;
import com.lesport.service.PushupService;

@Service
@Transactional // 此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class PushupServiceImpl implements PushupService {

	@Resource
	private PushupMapper mapper;

	public boolean save(UserPushup userPushup) {
		return mapper.save(userPushup);
	}

	@Override
	public List<UserPushup> findById(String userId,String date){
			return mapper.findById(userId,date);
	}
}
