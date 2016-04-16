package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.UserRun;
import com.lesport.model.UserWalk;

public interface WalkMapper {
	String IsExist(UserWalk userWalk);
	boolean update(UserWalk userWalk);
	boolean save(UserWalk userWalk);
	List<UserWalk> findById(@Param("userId") String userId,@Param("date") String date,@Param("all") String all);
}
