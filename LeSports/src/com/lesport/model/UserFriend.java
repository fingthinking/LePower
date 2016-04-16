package com.lesport.model;

import java.util.Date;
/**
 * @文件名 UserFriend
 * @author 斌
 * @version V.1
 * @日期 2016.3.5
 *
 */
public class UserFriend {
	private String userFriendId;
	private String userId;
	private String friendId;
	private String friendFlag;		//关注标志
	private String createdDate;		//创建日期
	private String deleteFlag;		//是否删除

	public String getUserFriendId() {
		return userFriendId;
	}

	public void setUserFriendId(String userFriendId) {
		this.userFriendId = userFriendId;
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

	public String getFriendFlag() {
		return friendFlag;
	}

	public void setFriendFlag(String friendFlag) {
		this.friendFlag = friendFlag;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public UserFriend(String userFriendId, String userId, String friendId, String friendFlag, String createdDate,
			String deleteFlag) {
		super();
		this.userFriendId = userFriendId;
		this.userId = userId;
		this.friendId = friendId;
		this.friendFlag = friendFlag;
		this.createdDate = createdDate;
		this.deleteFlag = deleteFlag;
	}

	public UserFriend(){
			super();
	}
	
}
