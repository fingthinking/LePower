package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.UserBicycle;
import com.lesport.model.UserPushup;
import com.lesport.model.UserRun;
import com.lesport.model.UserSitup;

public interface SitupMapper {
	boolean save(UserSitup String);
	List<UserSitup> findById(@Param("userId") String userId,@Param("date") String date);
}
