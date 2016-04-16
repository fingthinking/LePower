package com.lesport.model;

import java.util.List;

public class CircleDetail {
	
	String userId;
	String userName;
	String imgUrl;
	
	Circle circle;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	@Override
	public String toString() {
		return "CircleDetail [userId=" + userId + ", userName=" + userName + ", imgUrl=" + imgUrl + ", circle=" + circle
				+ "]";
	}

	
	
	

}
