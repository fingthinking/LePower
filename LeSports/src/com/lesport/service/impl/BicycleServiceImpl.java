package com.lesport.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.set.SynchronizedSortedSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.BicycleMapper;
import com.lesport.mapper.RunMapper;
import com.lesport.model.User;
import com.lesport.model.UserBicycle;
import com.lesport.model.UserRun;
import com.lesport.service.BicycleService;
import com.lesport.service.RunService;

@Service
@Transactional // 此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class BicycleServiceImpl implements BicycleService {

	@Resource
	private BicycleMapper mapper;

	public boolean save(UserBicycle userBicycle) {

		// System.out.println(userRun.toString());
		return mapper.save(userBicycle);
	}

	@Override
	public List<UserBicycle> findById(String userId,String date){
			return mapper.findById(userId,date);
	}

	@Override
	public List<UserBicycle> findCoordById(String userId, String date) {
		return mapper.findCoordById(userId,date);
	}

}
