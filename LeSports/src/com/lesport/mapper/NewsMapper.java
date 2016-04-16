package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.Manager;
import com.lesport.model.News;

/**
 * 
* @ClassName: NewsMapper 
* @Description: 管理员mapper代理
* @author Qinhaitao
* @date 2016年3月10日 上午10:10:50 
*
 */
public interface NewsMapper {
	public News getHealthyInfo(String newsId) throws Exception;//获取健康资讯接口
	public List<News> findAllNews() throws Exception;//获取所有健康资讯
	public int addNews(News news)throws Exception;
	//public String addNews(String newsId,String title, String picUrl,String content,String startTime,String endTime,String createdDate)throws Exception;
	//public void addNews(@Param("newsId")String newsId,@Param("title")String title,@Param("content")String content, @Param("startTime")String startTime, @Param("endTime")String endTime,@Param("picUrl")String picUrl,@Param("createdDate")String createdDate) throws Exception;//添加健康资讯接口
	public int deleteNews(String newsId) throws Exception;//删除健康资讯接口
	public int updateNews(News news)throws Exception;//更改健康资讯接口
	//public void updateNews(@Param("newsId")String newsId,@Param("title")String title,@Param("content")String content, @Param("startTime")String startTime, @Param("endTime")String endTime,@Param("picUrl")String picUrl,@Param("createdDate")String createdDate) throws Exception;//更改健康资讯接口
	public List<News> getNews(@Param("startTime")String startTime,@Param("endTime")String endTime) throws Exception;//查看健康资讯接口
}
