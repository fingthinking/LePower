package com.lesport.service;

import java.util.List;

import com.lesport.model.UserBicycle;
import com.lesport.model.UserPushup;


public interface PushupService {
	boolean save(UserPushup userPushup);
	List<UserPushup> findById(String userId,String date);
}
