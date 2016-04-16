package com.lesport.model;

/**
 * @entity name: CirLike
 * @author: 刘衍庆
 * @version: V1.0
 * @create date: 2016.3.6
 *
 */

public class CirLike {
	
	String cirLikeId;	//点赞id
	String circleId;	//动态id
	String likeUId;		//点赞者id
	String createDate;	//创建日期
	String deleteFlag;	//是否删除，默认为未删除：N
	
	//另加
	String likeUserName;	//点赞者用户名
	String likeNickName;	//点赞者昵称
	public String getCirLikeId() {
		return cirLikeId;
	}
	public void setCirLikeId(String cirLikeId) {
		this.cirLikeId = cirLikeId;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public String getLikeUId() {
		return likeUId;
	}
	public void setLikeUId(String likeUId) {
		this.likeUId = likeUId;
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
	public String getLikeUserName() {
		return likeUserName;
	}
	public void setLikeUserName(String likeUserName) {
		this.likeUserName = likeUserName;
	}
	public String getLikeNickName() {
		return likeNickName;
	}
	public void setLikeNickName(String likeNickName) {
		this.likeNickName = likeNickName;
	}
	
	@Override
	public String toString() {
		return "CirLike [cirLikeId=" + cirLikeId + ", circleId=" + circleId + ", likeUId=" + likeUId + ", createDate="
				+ createDate + ", deleteFlag=" + deleteFlag + ", likeUserName=" + likeUserName + ", likeNickName="
				+ likeNickName + "]";
	}


}
