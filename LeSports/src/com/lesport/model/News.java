package com.lesport.model;

import java.util.Date;

/**
 * 
* @ClassName: News 
* @Description: news实体类
* @author Qiuzg
* @date 2016年3月6日 下午9:10:24 
*
 */
public class News {

	private String newsId;//主键
	private String title;//标题
	private String picUrl;//图片
	private String content;//内容
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String activeFlag;//是否有效，默认为Y
	private String createdDate;//创建日期
	private String deleteFlag;//是否删除，默认为N
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
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
	@Override
	public String toString() {
		return "News [newsId=" + newsId + ", title=" + title + ", picUrl=" + picUrl + ", content=" + content
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", activeFlag=" + activeFlag + ", createdDate="
				+ createdDate + ", deleteFlag=" + deleteFlag + "]";
	}
	public News() {
		super();
		// TODO Auto-generated constructor stub
	}
	public News(String newsId, String title, String picUrl, String content, String startTime, String endTime,
			String activeFlag, String createdDate, String deleteFlag) {
		super();
		this.newsId = newsId;
		this.title = title;
		this.picUrl = picUrl;
		this.content = content;
		this.startTime = startTime;
		this.endTime = endTime;
		this.activeFlag = activeFlag;
		this.createdDate = createdDate;
		this.deleteFlag = deleteFlag;
	}
	
	
}
