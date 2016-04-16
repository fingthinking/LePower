package com.lepower.model;

import java.io.Serializable;

public class Favour implements Serializable{
	
	private String id;
	private String friendId;
	private String createDate;
	private String favourUId;
	private String favourName;
	private String friendOwner;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getFavourUId() {
		return favourUId;
	}

	public void setFavourUId(String favourUId) {
		this.favourUId = favourUId;
	}

	public String getFavourName() {
		return favourName;
	}

	public void setFavourName(String favourName) {
		this.favourName = favourName;
	}

	public String getFriendOwner() {
		return friendOwner;
	}

	public void setFriendOwner(String friendOwner) {
		this.friendOwner = friendOwner;
	}

}
