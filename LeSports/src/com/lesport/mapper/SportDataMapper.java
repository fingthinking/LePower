package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.SportData;
import com.lesport.model.UserInfo;
import com.lesport.model.UserRun;
import com.lesport.model.UserWalk;

public interface SportDataMapper {
	List<SportData> getSportData(@Param("userId") String userId,@Param("beginDate") String beginDate,@Param("endDate") String endDate);
	UserInfo getRunDis(@Param("userId") String userId,@Param("date") String date);
	List<UserInfo> getRunRange(@Param("date") String date);
}
