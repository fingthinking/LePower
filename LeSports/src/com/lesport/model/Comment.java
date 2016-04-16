package com.lesport.model;

/**
 * @entity name: Comment
 * @author: 刘衍庆
 * @version: V1.0
 * @create date: 2016.3.6
 *
 */

public class Comment {
	
	String commentId;	//评论id
	String circleId;	//动态id
	String commentUId;	//评论者id
	String content;		//评论内容
	String commentTime;	//评论时间
	String replyUId;	//被回复者id
	String createDate;	//创建日期
	String deleteFlag;	//是否删除，默认为未删除：N
	
	//另加
	String commentUserName;//评论者用户名
	String commentNickName;		//评论者昵称
	String commentImgUrl;		//评论者头像
	String replyUserName;	//被回复者用户名
	String replyNickName;	//被回复者昵称	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getCommentUId() {
		return commentUId;
	}
	public void setCommentUId(String commentUId) {
		this.commentUId = commentUId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}
	public String getReplyUId() {
		return replyUId;
	}
	public void setReplyUId(String replyUId) {
		this.replyUId = replyUId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getCommentUserName() {
		return commentUserName;
	}
	public void setCommentUserName(String commentUserName) {
		this.commentUserName = commentUserName;
	}
	public String getCommentNickName() {
		return commentNickName;
	}
	public void setCommentNickName(String commentNickName) {
		this.commentNickName = commentNickName;
	}
	public String getCommentImgUrl() {
		return commentImgUrl;
	}
	public void setCommentImgUrl(String commentImgUrl) {
		this.commentImgUrl = commentImgUrl;
	}
	public String getReplyUserName() {
		return replyUserName;
	}
	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	public String getReplyNickName() {
		return replyNickName;
	}
	public void setReplyNickName(String replyNickName) {
		this.replyNickName = replyNickName;
	}
	
	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", circleId=" + circleId + ", commentUId=" + commentUId
				+ ", content=" + content + ", commentTime=" + commentTime + ", replyUId=" + replyUId + ", createDate="
				+ createDate + ", deleteFlag=" + deleteFlag + ", commentUserName=" + commentUserName
				+ ", commentNickName=" + commentNickName + ", commentImgUrl=" + commentImgUrl + ", replyUserName="
				+ replyUserName + ", replyNickName=" + replyNickName + "]";
	}

	
	
	
	
}
