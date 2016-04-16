package com.lesport.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.Nestable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.ManagerMapper;
import com.lesport.mapper.NewsMapper;
import com.lesport.model.Manager;
import com.lesport.model.News;
import com.lesport.service.ManagerService;
import com.lesport.service.NewsService;
import com.lesport.util.Utility;

/**
 * 
 * @ClassName: ManagerServiceImpl
 * @Description: TODO
 * @author Qinhaitao
 * @date 2016年3月7日 上午11:29:49
 *
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

	@Resource
	private NewsMapper mapper;

	@Override
	public int addNews(String newsId, String title, String picUrl, String content, String startTime, String endTime,
			String createdDate){
		int effectedRows=-1;
		try {
			News news = new News();
			news.setNewsId(newsId);
			news.setTitle(title);
			news.setPicUrl(picUrl);
			news.setContent(content);
			news.setStartTime(startTime);
			news.setEndTime(endTime);
			news.setCreatedDate(createdDate);
			effectedRows=mapper.addNews(news);			
		} catch (Exception e) {
			return effectedRows;
		} finally {
			return effectedRows;
		}
	}

	@Override
	public int updateNews(String newsId, String title, String picUrl, String content, String startTime,
			String endTime, String createdDate) throws Exception {
		int effectedRows = -1;
		try {
			News news = new News();
			news.setNewsId(newsId);
			news.setTitle(title);
			news.setContent(content);
			news.setPicUrl(picUrl);
			news.setStartTime(startTime);
			news.setEndTime(endTime);
			news.setCreatedDate(createdDate);
			effectedRows=mapper.updateNews(news);			
		} catch (Exception e) {
			return effectedRows;
		} finally {
			
			return effectedRows;
		}
		// mapper.updateNews(newsId,title,content,beginTime,endTime,picUrl,createTime);
	}

	@Override
	public News getHealthyInfo(String newsId) throws Exception {
		News news=mapper.getHealthyInfo(newsId);
		return news;
//		String result = "";
//		try {
//			News news = null;
//			news = mapper.getHealthyInfo(newsId);
//			result = "\"newsId\"" + ":"+"\"" + news.getNewsId() + "\",";
//			result += "\"title\"" + ":"+"\"" + news.getTitle() + "\",";
//			result += "\"picUrl\"" +":"+ "\"" + news.getPicUrl() + "\",";
//			result += "\"content\""+":"+"\"" + news.getContent() + "\",";
//			result = "{" + "\"resCode\":" + 0 + "," + "\"resMsg\":" + "\"\"" + "," + "\"data\":[" + result + "]" + "}";
//		} catch (Exception e) {
//			result = Utility.packReturnJson(1, "", "");
//		} finally {
//			return result;
//		}
	}

	@Override
	public int deleteNews(String newsId) throws Exception {
		int effectedRows=-1;
		try {
			effectedRows=mapper.deleteNews(newsId);
			
		} catch (Exception e) {
			return effectedRows;
		} finally {
			return effectedRows;
		}
	}

	@Override
	public List<News> findAllNews() throws Exception {
		List<News> allNews=mapper.findAllNews();
		return allNews;
//		String result = "";
//		String temp = "";// 每遍历一个对象生成的字符串
//		String str = "";// 已遍历到的对象所生成的字符串
//		try {
//			List<News> allNews = mapper.findAllNews();
//			for (News news : allNews) {
//				temp = "{";
//				temp += "\"title\"" +":"+ "\"" + news.getTitle() + "\",";
//				temp += "\"picUrl\"" +":"+ "\"" + news.getPicUrl() + "\",";
//				temp += "\"content\"" +":"+ "\"" + news.getContent() + "\",";
//				temp += "\"startTime\"" +":"+ "\"" + news.getStartTime() + "\",";
//				temp += "\"endTime\"" +":"+ "\"" + news.getEndTime() + "\",";
//				temp += "\"createdDate\"" +":"+ "\"" + news.getCreatedDate() + "\"";
//				temp += "},";
//				str += temp;
//			}
//			str = str.substring(0, str.length() - 2);
//			result = "{" + "\"resCode\":" + 0 + "," + "\"resMsg\":" + "\"\"" + "," + "\"data\":[" + str + "]" + "}";
//		} catch (Exception e) {
//			result = Utility.packReturnJson(1, "", "");
//		} finally {
//			return result;
//		}
	}

	@Override
	public List<News> getNews(String startTime, String endTime) throws Exception {
		 List<News> allnews=mapper.getNews(startTime, endTime);
		 return allnews;
		
//		String result = "";
//		String temp = "";// 每遍历一个对象生成的字符串
//		String str = "";// 已遍历到的对象所生成的字符串
//		try {
//			List<News> allNews = mapper.getNews(startTime, endTime);
//			for (News news : allNews) {
//				temp = "{";
//				temp += "\"newsId\"" +":"+ "\"" + news.getNewsId() + "\",";
//				temp += "\"title\"" + ":"+"\"" + news.getTitle() + "\",";
//				temp += "\"picUrl\"" + ":"+"\"" + news.getPicUrl() + "\",";
//				temp += "\"content\"" +":"+ "\"" + news.getContent() + "\",";
//				temp += "\"startTime\"" +":"+ "\"" + news.getStartTime() + "\",";
//				temp += "\"endTime\"" +":"+ "\"" + news.getEndTime() + "\"";
//				temp += "\"createddate\"" + ":"+"\"" + news.getCreatedDate() + "\"";
//				temp += "},";
//				str += temp;
//			}
//			str = str.substring(0, str.length() - 2);
//			result = "{" + "\"resCode\":" + 0 + "," + "\"resMsg\":" + "\"\"" + "," + "\"data\":[" + str + "]" + "}";
//		} catch (Exception e) {
//			result = Utility.packReturnJson(1, "", "");
//		} finally {
//			return result;
//		}
	}
}
