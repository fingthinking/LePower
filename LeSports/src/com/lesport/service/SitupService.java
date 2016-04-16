package com.lesport.service;

import java.util.List;

import com.lesport.model.UserBicycle;
import com.lesport.model.UserPushup;
import com.lesport.model.UserSitup;


public interface SitupService {
	boolean save(UserSitup userSitup);
	List<UserSitup> findById(String userId,String date);
}
