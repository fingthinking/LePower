package com.lesport.model;

import java.util.Date;

import com.lesport.util.Utility;

/**
 * 
* @ClassName: Manager 
* @Description: Manage实体类
* @author Qiuzg
* @date 2016年3月6日 下午8:55:44 
*
 */
public class Manager {

	private String managerId;//id主键
	private String managerName;//管理员姓名
	private String managerPwd;//管理员密码 
	private String authority;//管理员权限，0普通管理员，1超级管理员
	private String createdDate;//创建时间
	private String lastLogTime;//最后登录时间
	private String deleteFlag;//是否删除，默认为N
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPwd() {
		return managerPwd;
	}
	public void setManagerPwd(String managerPwd) {
		this.managerPwd = managerPwd;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastLogTime() {
		return lastLogTime;
	}
	public void setLastLogTime(String lastLogTime) {
		this.lastLogTime = lastLogTime;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Manager() {

		this.managerId = Utility.getRowId();
		this.createdDate = Utility.getFormattedCurrentDateAndTime();
		// TODO Auto-generated constructor stub
	}
	public Manager(String managerId, String managerName, String managerPwd, String authority, String createdDate,
			String lastLogTime, String deleteFlag) {
		super();
		this.managerId = managerId;
		this.managerName = managerName;
		this.managerPwd = managerPwd;
		this.authority = authority;
		this.createdDate = createdDate;
		this.lastLogTime = lastLogTime;
		this.deleteFlag = deleteFlag;
	}
	@Override
	public String toString() {
		return "Manager [managerId=" + managerId + ", managerName=" + managerName + ", managerPwd=" + managerPwd
				+ ", authority=" + authority + ", createdDate=" + createdDate + ", lastLogTime=" + lastLogTime
				+ ", deleteFlag=" + deleteFlag + "]";
	}
	
	
}
