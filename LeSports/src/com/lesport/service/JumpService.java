package com.lesport.service;

import java.util.List;

import com.lesport.model.UserBicycle;
import com.lesport.model.UserJump;
import com.lesport.model.UserPushup;


public interface JumpService {
	boolean save(UserJump userJump);
	List<UserJump> findById(String userId,String order);
	String getJumpOrder(String userId); 
}
