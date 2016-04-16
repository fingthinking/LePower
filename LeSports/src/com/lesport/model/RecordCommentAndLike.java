package com.lesport.model;


/**
 * @entity name: RecordCommentAndLike
 * @detail:记录所有的点赞和评论记录
 * @author: 刘衍庆
 * @version: V1.0
 * @create date: 2016.3.8
 *
 */

public class RecordCommentAndLike {
	
	String recordCommentAndLikeId;	//记录id
	String circleId;				//动态id
	String commentId;				//评论id
	String publisherId;				//发表者id
	String content;					//评论内容
	String publishDate;				//发表时间
	String deleteFlag;				//是否删除
	String ownerId;					//动态拥有者id
	public String getRecordCommentAndLikeId() {
		return recordCommentAndLikeId;
	}
	public void setRecordCommentAndLikeId(String recordCommentAndLikeId) {
		this.recordCommentAndLikeId = recordCommentAndLikeId;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	@Override
	public String toString() {
		return "RecordCommentAndLike [recordCommentAndLikeId=" + recordCommentAndLikeId + ", circleId=" + circleId
				+ ", commentId=" + commentId + ", publisherId=" + publisherId + ", content=" + content
				+ ", publishDate=" + publishDate + ", deleteFlag=" + deleteFlag + ", ownerId=" + ownerId + "]";
	}
	
	
	
	
	
}
