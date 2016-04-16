package com.lesport.mapper;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.SportTotal;

public interface SportTotalMapper {
	SportTotal getRunInfo(@Param("userId") String userId);
	SportTotal getWalkInfo(@Param("userId") String userId);
	SportTotal getBikeInfo(@Param("userId") String userId);
	SportTotal getPushupInfo(@Param("userId") String userId);
	SportTotal getSitupInfo(@Param("userId") String userId);
	SportTotal getJumpInfo(@Param("userId") String userId);
}
