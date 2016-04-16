package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.UserRun;

public interface RunMapper {
	boolean save(UserRun userRun);
	List<UserRun> findById(@Param("userId") String userId,@Param("beginDate") String beginDate,@Param("endDate") String endDate);
	boolean delete(String runId);
}
