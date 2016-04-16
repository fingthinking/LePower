package com.lesport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.JumpMapper;
import com.lesport.mapper.PushupMapper;
import com.lesport.model.UserJump;
import com.lesport.model.UserPushup;
import com.lesport.service.JumpService;
import com.lesport.service.PushupService;

@Service
@Transactional // 此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class JumpServiceImpl implements JumpService {

	@Resource
	private JumpMapper mapper;

	public boolean save(UserJump userJump) {
		return mapper.save(userJump);
	}

	@Override
	public List<UserJump> findById(String userId,String order){
			return mapper.findById(userId,order);
	}

	@Override
	public String getJumpOrder(String userId) {
		return mapper.getJumpOrder(userId);
	}
}
