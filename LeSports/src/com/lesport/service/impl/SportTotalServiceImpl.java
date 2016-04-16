package com.lesport.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.SportTotalMapper;
import com.lesport.model.SportTotal;
import com.lesport.service.SportTotalService;

@Service
@Transactional // 此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class SportTotalServiceImpl implements SportTotalService {

	@Resource
	private SportTotalMapper mapper;

	@Override
	public SportTotal getRunInfo(String userId) {
		return mapper.getRunInfo(userId);
	}

	@Override
	public SportTotal getWalkInfo(String userId) {
		return mapper.getWalkInfo(userId);
	}

	@Override
	public SportTotal getBikeInfo(String userId) {
		return mapper.getBikeInfo(userId);
	}

	@Override
	public SportTotal getPushupInfo(String userId) {
		return mapper.getPushupInfo(userId);
	}

	@Override
	public SportTotal getSitupInfo(String userId) {
		return mapper.getSitupInfo(userId);
	}

	@Override
	public SportTotal getJumpInfo(String userId) {
		return mapper.getJumpInfo(userId);
	}

}
