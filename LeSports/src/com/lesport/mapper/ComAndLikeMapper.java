package com.lesport.mapper;

import java.util.List;

import com.lesport.model.ComAndLike;

public interface ComAndLikeMapper {

	public List<ComAndLike> getCommon(String circleId) throws Exception;
	public List<ComAndLike> getLike(String circleId) throws Exception;
}
