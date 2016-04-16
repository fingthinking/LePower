package com.lesport.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lesport.model.CirLike;
import com.lesport.model.Circle;
import com.lesport.model.Comment;
import com.lesport.model.CommentLike;
import com.lesport.model.RecordCommentAndLike;
import com.lesport.model.UserInfo;

public interface CircleManageService {
	
	//获取每页动态列表
	public List<Circle> getCircleList(String nickName,int offset, int pageSizeInt);
	
	//获取乐友圈动态总数
	public int getCountOfCircle(String nickName);
	
	//根据昵称搜索动态
	public List<Circle> findCirByName(String nickName);
	
	//删除动态
	boolean deleteCircle(String circleId);
}
