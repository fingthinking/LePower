package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.UserBicycle;
import com.lesport.model.UserJump;
import com.lesport.model.UserPushup;
import com.lesport.model.UserRun;

public interface JumpMapper {
	boolean save(UserJump String);
	List<UserJump> findById(@Param("userId") String userId,@Param("order") String order);
	String getJumpOrder(@Param("userId") String userId); 
}
