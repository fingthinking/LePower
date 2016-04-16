package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.UserBicycle;
import com.lesport.model.UserPushup;
import com.lesport.model.UserRun;

public interface PushupMapper {
	boolean save(UserPushup String);
	List<UserPushup> findById(@Param("userId") String userId,@Param("date") String date);
}
