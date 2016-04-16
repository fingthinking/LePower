package com.lesport.model;

import java.sql.Timestamp;
import java.util.List;

/**
 * @entity name: Circle
 * @author: 刘衍庆
 * @version: V1.0
 * @create date: 2016.3.6
 * 
 * 
 * @author 刘衍庆
 * @version: 2.0
 * @create date:2016年3月7日
 * @detail: 添加了两个list字段
 *
 *@author 邓献文
 *@version:3.0
 *@created date:2016/3/8
 *@detail:添加nickName字段
 */

public class Circle {	
	
	String circleId;	//动态id
	String userId;		//用户id，发表动态的用户的id
	String picUrl;		//图片
	String content;		//内容
	String  publishDate;	//发表日期
	String publishAddr;	//发表位置
	String scopeFlag;	//可见范围标志
	String  createDate;	//创建日期
	String deleteFlag;	//是否删除，默认为未删除：N
	
	//另加
	String userName;
	String nickName;
	String imgUrl;
	
	List<Comment> comments;//评论列表
	List<CirLike> cirLikes;//点赞列表
	
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublishAddr() {
		return publishAddr;
	}
	public void setPublishAddr(String publishAddr) {
		this.publishAddr = publishAddr;
	}
	public String getScopeFlag() {
		return scopeFlag;
	}
	public void setScopeFlag(String scopeFlag) {
		this.scopeFlag = scopeFlag;
	}
	
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<CirLike> getCirLikes() {
		return cirLikes;
	}
	public void setCirLikes(List<CirLike> cirLikes) {
		this.cirLikes = cirLikes;
	}	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "Circle [circleId=" + circleId + ", userId=" + userId + ", picUrl=" + picUrl + ", content=" + content
				+ ", publishDate=" + publishDate + ", publishAddr=" + publishAddr + ", scopeFlag=" + scopeFlag
				+ ", createDate=" + createDate + ", deleteFlag=" + deleteFlag + ", userName=" + userName + ", nickName="
				+ nickName + ", imgUrl=" + imgUrl + ", comments=" + comments + ", cirLikes=" + cirLikes + "]";
	}

	
}
