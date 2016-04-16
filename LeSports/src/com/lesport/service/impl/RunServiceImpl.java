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

import com.lesport.mapper.RunMapper;
import com.lesport.model.User;
import com.lesport.model.UserRun;
import com.lesport.service.RunService;

@Service
@Transactional // 此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class RunServiceImpl implements RunService {

	@Resource
	private RunMapper mapper;

	public boolean save(UserRun userRun) {

		// System.out.println(userRun.toString());
		return mapper.save(userRun);
	}

	@Override
	public List<UserRun> findById(String userId,String beginDate,String endDate){
			return mapper.findById(userId,beginDate,endDate);
	}

	@Override
	public boolean delete(String runId) {
		return mapper.delete(runId);
	}

}
