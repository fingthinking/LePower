package com.lesport.service;

import java.util.List;

import com.lesport.model.UserRun;


public interface RunService {
	boolean save(UserRun userRun);
	List<UserRun> findById(String userId,String beginDate,String endDate);
	boolean delete(String runId);
}
