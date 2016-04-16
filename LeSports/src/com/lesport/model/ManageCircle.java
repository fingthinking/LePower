package com.lesport.model;

import java.util.List;

/**
 *@author 邓献文
 *@version:1.0
 *@created date:2016/3/8
 */

public class ManageCircle {	
	
	String circleId;	//动态id
	String picUrls;		//图片
	String content;		//内容
	String publishTime;	//发表日期
	String location;	//发表位置
	String userName;    //用户名
	String nickName;	//昵称
	String countOfLike;     //点赞数量
	String countOfComment; //评论数量
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getPicUrls() {
		return picUrls;
	}
	public void setPicUrls(String picUrls) {
		this.picUrls = picUrls;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCountOfLike() {
		return countOfLike;
	}
	public void setCountOfLike(String countOfLike) {
		this.countOfLike = countOfLike;
	}
	public String getCountOfComment() {
		return countOfComment;
	}
	public void setCountOfComment(String countOfComment) {
		this.countOfComment = countOfComment;
	}
	@Override
	public String toString() {
		return "ManageCircle [circleId=" + circleId + ", picUrls=" + picUrls + ", content=" + content + ", publishTime="
				+ publishTime + ", location=" + location + ", userName=" + userName + ", nickName=" + nickName
				+ ", countOfLike=" + countOfLike + ", countOfComment=" + countOfComment + "]";
	}
	
	
	
}
