package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.CirLike;
import com.lesport.model.Circle;
import com.lesport.model.Comment;
import com.lesport.model.CommentLike;
import com.lesport.model.RecordCommentAndLike;
import com.lesport.model.UserInfo;

public interface CircleManageMapper {
	
	//获取每页动态列表
	public List<Circle> getCircleList(@Param("nickName") String nickName,@Param("offset") int offset, @Param("pageSizeInt") int pageSizeInt);
	
	//获取乐友圈动态总数
	public int getCountOfCircle(@Param("nickName") String nickName);
	
	//根据昵称搜索动态
	public List<Circle> findCirByName(String nickName);
	
	//删除动态
	boolean deleteCircle(String circleId);
}
