package com.lesport.service;

/**
 * 
* @ClassName: NewsMapper 
* @Description: zz
* @author Qinhaitao
* @date 2016年3月10日 上午10:10:50 
*
 */
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.News;

public interface NewsService {
	public News getHealthyInfo(String newsId) throws Exception;//获取健康资讯接口
	public int addNews(@Param("newsId")String newsId,@Param("title")String title,@Param("picUrl")String picUrl,@Param("content")String content, @Param("startTime")String startTime, @Param("endTime")String endTime,@Param("createdDate")String createdDate) throws Exception;//添加健康资讯接口
	//public String addNews(String newsId,String title, String picUrl,String content,String startTime,String endTime,String createdDate)throws Exception;
	public int deleteNews(String newsId) throws Exception;//删除健康资讯接口
	public int updateNews(@Param("newsId")String newsId,@Param("title")String title,@Param("picUrl")String picUrl,@Param("content")String content, @Param("startTime")String startTime, @Param("endTime")String endTime,@Param("createdDate")String createdDate) throws Exception;//更改健康资讯接口
	public List<News> getNews(String startTime,String endTime) throws Exception;//查看健康资讯接口
	public List<News> findAllNews() throws Exception;//获取所有健康资讯
}
