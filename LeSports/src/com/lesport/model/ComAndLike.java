package com.lesport.model;

public class ComAndLike {
	
	private String circleId;
	private String userName;
	private String operationTime;
	private String content;
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
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
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ComAndLike() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ComAndLike(String circleId, String userName, String operationTime, String content, String flag) {
		super();
		this.circleId = circleId;
		this.userName = userName;
		this.operationTime = operationTime;
		this.content = content;
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "ComAndLike [circleId=" + circleId + ", userName=" + userName + ", operationTime=" + operationTime
				+ ", content=" + content + ", flag=" + flag + "]";
	}

	
	
	
}
