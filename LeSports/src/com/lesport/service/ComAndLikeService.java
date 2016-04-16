package com.lesport.service;

import java.util.List;

import com.lesport.model.ComAndLike;

public interface ComAndLikeService {

	public List<ComAndLike> getCommonAndLike(String circleId);
}
