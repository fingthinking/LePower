package com.lesport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.WalkMapper;
import com.lesport.model.UserWalk;
import com.lesport.service.WalkService;

@Service
@Transactional // 此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class WalkServiceImpl implements WalkService {

	@Resource
	private WalkMapper mapper;

	@Override
	public boolean save(UserWalk userWalk) {
		return mapper.save(userWalk);
	}
	
	@Override
	public List<UserWalk> findById(String userId, String date,String all){
			return mapper.findById(userId,date,all);
	}

	@Override
	public String IsExist(UserWalk userWalk) {
		return mapper.IsExist(userWalk);
	}

	@Override
	public boolean update(UserWalk userWalk) {
		return mapper.update(userWalk);
	}
}
