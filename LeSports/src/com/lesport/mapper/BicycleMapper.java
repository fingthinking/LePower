package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.UserBicycle;
import com.lesport.model.UserRun;

public interface BicycleMapper {
	boolean save(UserBicycle userBicycle);
	List<UserBicycle> findById(@Param("userId") String userId,@Param("date") String date);
	List<UserBicycle> findCoordById(@Param("userId") String userId,@Param("date") String date);
}
