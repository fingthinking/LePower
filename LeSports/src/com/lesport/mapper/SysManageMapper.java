package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.ManageCircle;

public interface SysManageMapper {
	//查看动态信息接口
	List<ManageCircle> getCircleInfo(@Param("userId") String userId,@Param("beginTime") String beginTime,@Param("endTime") String endTime);
}