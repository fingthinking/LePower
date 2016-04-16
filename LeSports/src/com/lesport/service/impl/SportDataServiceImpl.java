package com.lesport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.SportDataMapper;
import com.lesport.mapper.WalkMapper;
import com.lesport.model.SportData;
import com.lesport.model.UserInfo;
import com.lesport.model.UserWalk;
import com.lesport.service.SportDataService;
import com.lesport.service.WalkService;

@Service
@Transactional // 此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class SportDataServiceImpl implements SportDataService {

	@Resource
	private SportDataMapper mapper;

	@Override
	public List<SportData> getSportData(String userId,String beginDate, String endDate) {
		
		return mapper.getSportData(userId,beginDate,endDate);
	}

	@Override
	public UserInfo getRunDis(String userId,String date) {
		return mapper.getRunDis(userId,date);
	}

	@Override
	public List<UserInfo> getRunRange(String date) {
		return mapper.getRunRange(date);
	}

}
