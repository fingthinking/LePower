package com.lepower.model;

import java.io.Serializable;
import java.util.List;

import android.view.View;

/**
 * 朋友信息model
 * 
 * @author 
 * 
 */

public class FriendAction implements Serializable{
	public String id;
	public String photo;// 头像icon
	public int shareType;// 0-是自己发送的 1-分享连接 2-分享歌曲 3-分享视频
	public String userName;// 名字，可能为邮箱或者手机号
	
	public String nickName;  //昵称
	public String linkUrl;// 连接url
	// public Bitmap linkIcon;// 链接图标
	// public String linkDescription;// 链接描述
	public String contentText;// 发表的文字内容
	public String sendDate;// 发表时间
	public String favourName;// 点赞的名字
	public List<Favour> favourList;  //点赞列表
	public int favourCount;  //点赞数量
	public List<Reply> replyList;// 回复的listView
	public List<String> images;
	public List<Photos> photos;
	public String userId;//用户id
	public String friendId;//动态id
	public int position;//记录item位置
	public String address;
	public int replyCount;
	public String scopeFlag; //可见标志 
	
	
	public List<Photos> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photos> photos) {
		this.photos = photos;
	}
	
	public int getFavourCount() {
		return favourCount;
	}
	public void setFavourCount(int favourCount) {
		this.favourCount = favourCount;
	}
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getShareType() {
		return shareType;
	}
	public void setShareType(int shareType) {
		this.shareType = shareType;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getFavourName() {
		return favourName;
	}
	public void setFavourName(String favourName) {
		this.favourName = favourName;
	}
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
	}
	public List<Favour> getFavourList() {
		return favourList;
	}
	public void setFavourList(List<Favour> favourList) {
		this.favourList = favourList;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public String getScopeFlag() {
		return scopeFlag;
	}
	public void setScopeFlag(String scopeFlag) {
		this.scopeFlag = scopeFlag;
	}
	
}
