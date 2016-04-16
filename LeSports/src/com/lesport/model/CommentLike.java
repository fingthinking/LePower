package com.lesport.model;

/*
 * Author:刘亚中
 * 日期：2016-3-8
 * 版本：v1.0
 * 
 * */
public class CommentLike {

	String circleId;//动态id
	String userName;//用户名
	String content;//内容
	String publishTime;//发表的时间
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	@Override
	public String toString() {
		return "CommentLike [circleId=" + circleId + ", userName=" + userName + ", content=" + content
				+ ", publishTime=" + publishTime + "]";
	}
	
}
