package com.lesport.service;

import java.util.List;

import com.lesport.model.UserRun;
import com.lesport.model.UserWalk;


public interface WalkService {
	String IsExist(UserWalk userWalk);
	boolean update(UserWalk userWalk);
	boolean save(UserWalk userWalk);
	List<UserWalk> findById(String userId,String date,String all);
}
