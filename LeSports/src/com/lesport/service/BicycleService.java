package com.lesport.service;

import java.util.List;

import com.lesport.model.UserBicycle;


public interface BicycleService {
	boolean save(UserBicycle userBicycle);
	List<UserBicycle> findById(String userId,String date);
	List<UserBicycle> findCoordById(String userId,String date);
}
