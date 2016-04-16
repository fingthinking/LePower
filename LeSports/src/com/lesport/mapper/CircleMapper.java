package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.CirLike;
import com.lesport.model.Circle;
import com.lesport.model.Comment;
import com.lesport.model.CommentLike;
import com.lesport.model.RecordCommentAndLike;
import com.lesport.model.UserInfo;

public interface CircleMapper {
	
	//获取好友动态列表，乐友圈接口1
	public List<Circle> getFriendCircle(String userId,int offset, int pageSizeInt);
	
	//获取动态详情，乐友圈接口2
	public Circle getCircleDetail(String circleId);
	
	
	//获取我的动态，乐友圈接口3
	public List<Circle> getMyCircle(String userId,int offset, int pageSizeInt);
	
	//发表动态，乐友圈接口4
	public int addCircle(Circle circle);
	
	//发表评论，乐友圈接口5
	public int addComment(Comment comment);
	
	//当用户发表评论时，同时向le_record_commentandlike表插入相应的记录信息
	public int addRecordCommentAndLike(RecordCommentAndLike recordCommentAndLike);
	
	//获取新的评论和点赞的数量，乐友圈接口10
	public int getCountOfComAndLike(String userId);
	
	
	//获取某个userId的所有动态数量
	public int getCountOfAllcircle(String userId);
	
	
	//获取某个userId本人及好友所有的动态数量
	public int getCountOfMineAndFriend(String userId);
	
	
	//================================以下部分为刘亚中所写================================================//
	
	public Circle getToDispathSurge(String circleId);
	public boolean saveDispathSurge(Circle circle);
	public boolean deletePraise(@Param("circleId")String circleId,@Param("userId")String userId);
	public boolean deleteComment(String commentId);
	public boolean deleteCircle(String circleId);
	public boolean addLike(CirLike cirLike);
	public boolean addPraiseRemind(@Param("rowId")String rowid,@Param("circleId")String circleId,@Param("userId")String userId,@Param("publishDate")String publishDate,@Param("ownerId")String ownerId);
	public List<CommentLike>  getContentOfCirAndLike(String userId);
	public boolean setSawFlag(String userId);

}
