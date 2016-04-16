package com.lesport.webcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import com.lesport.model.News;
import com.lesport.service.NewsService;
import com.lesport.util.UploadImage;
import com.lesport.util.Utility;

@Controller
@RequestMapping("/system")
public class NewsController {
	@Autowired
	private NewsService newsService;
	//每页显示的资讯条数
	private int pageSizeInt=5;
	@RequestMapping("/getAllNews")
    public String getAllNews(HttpServletRequest request){
		
		try {
			List<News> news = newsService.findAllNews();
			//查出的总记录条数
			int totalRecord=news.size();
	//		System.out.println("----总记录数----"+totalRecord);
			int totalPage=(int)(Math.ceil((double)totalRecord/pageSizeInt));
			//System.out.println("---------"+totalPage);
		//	System.out.println("----总页数----"+totalPage);
			List<News> newsList=new ArrayList<News>();
			Iterator<News> it = news.iterator();
			if(totalRecord<pageSizeInt)
			{
				while(it.hasNext())
				{
					News newItem=it.next();
					newsList.add(newItem);
				}
			}else{
				for(int i=0;i<pageSizeInt;i++)
				{
					News newItem=it.next();
					newsList.add(newItem);
				}
			}
		//	System.out.println("----第一页记录数----"+newsList.size());
			
			request.setAttribute("newsList", newsList);
			request.setAttribute("pageNow", 1);
			request.setAttribute("totalPage", totalPage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "managePages/table_course";
	}
	@RequestMapping(value = "/nextPage")
	public String nextPage(String pageNow,String totalPage,HttpServletRequest request)
	{
		try {
		//	System.out.println("+++++++"+totalPage);
			List<News> news=newsService.findAllNews();
			int totalRecord=news.size();
			//当前页码
			int pageNowInt=Integer.parseInt(pageNow);
			//总页数
			int totalPageInt=Integer.parseInt(totalPage);
			//下一页页码
			pageNowInt=(pageNowInt+1)>totalPageInt? totalPageInt:pageNowInt+1;
			//剩余记录条数
			int remainRecord=totalRecord-(pageNowInt-1)*pageSizeInt;
			List<News>newsList=new ArrayList<News>();
			if(remainRecord<pageSizeInt)
			{
				for(int i=(pageNowInt-1)*pageSizeInt;i<remainRecord+(pageNowInt-1)*pageSizeInt;i++)
				{
					News newItem=new News();
					newItem=news.get(i);
					newsList.add(newItem);
				}
			}else {
				for(int i=(pageNowInt-1)*pageSizeInt;i<pageNowInt*pageSizeInt;i++)
				{
					News newItem=new News();
					newItem=news.get(i);
					newsList.add(newItem);
				}
			}
			if((pageNowInt+1)>totalPageInt)
			{
				request.setAttribute("pageNow", totalPageInt);
				request.setAttribute("newsList", newsList);
				request.setAttribute("totalPage", totalPage);
			}
			else
			{
				request.setAttribute("pageNow", pageNowInt);
				request.setAttribute("newsList", newsList);
				request.setAttribute("totalPage", totalPage);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
		return "managePages/table_course";
	}
	@RequestMapping(value = "/prePage")
	public String prePage(String pageNow,String totalPage,HttpServletRequest request)
	{
		try {
			List<News> news=newsService.findAllNews();
			int totalRecord=news.size();
			int totalPageInt=Integer.parseInt(totalPage);
			int pageNowInt=Integer.parseInt(pageNow);
			//上一页页码
			pageNowInt=(pageNowInt-1)<=1 ?1:pageNowInt-1;
			List<News> newsList=new ArrayList<News>();
			for(int i=(pageNowInt-1)*pageSizeInt;i<(pageNowInt)*pageSizeInt;i++)
			{
				News newItem=new News();
				newItem=news.get(i);
			    newsList.add(newItem);
			}
			if((Integer.parseInt(pageNow)-1)>0)
			{
				
				request.setAttribute("pageNow", pageNowInt);
				request.setAttribute("newsList", newsList);
				request.setAttribute("totalPage", totalPage);
			}
			else {
				request.setAttribute("pageNow", 1);
				request.setAttribute("newsList", newsList);
				request.setAttribute("totalPage", totalPage);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "managePages/table_course";
	}
	@RequestMapping("/deleteNews")
	public void deleteNews(String newsId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html;charset=utf-8");
        String result ="error";
		try {
			if(newsService.deleteNews(newsId)==1){
				result = "success";
				
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			PrintWriter out = response.getWriter();
			out.write(result);
		}
	}
	@RequestMapping("/getInfo")
	public String getInfo(String newsId,HttpServletRequest request) throws Exception
	{
		//System.out.println("++++++++++++++"+newsId);
		String date=new Date().toLocaleString();
		if(newsId==null)
		{
			News news=new News();
			newsId=Utility.getRowId();
			news.setNewsId(newsId);
			news.setCreatedDate(date);
			request.setAttribute("news",news );
			request.setAttribute("status", 0);
			return "managePages/editNews";
		}
		else {
				News news=new News();
				news=newsService.getHealthyInfo(newsId);
				String sStime=news.getStartTime();
				System.out.println("sStime:"+sStime);
				String eEtime=news.getEndTime();
				news.setStartTime(sStime.substring(0, 10));
				news.setEndTime(eEtime.substring(0, 10));
				request.setAttribute("news",news);
				request.setAttribute("status", 1);
				return "managePages/editNews";
		}
	}
	@RequestMapping("/updateNews")
	public String updateNews(News news,String picUrl,String stTime,String enTime,String sTime,String eTime,String state,@RequestParam("pirUrl")MultipartFile pirUrl,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
	//	System.out.println(picUrl+news.getContent()+"----------"+state.equals("1"));
		news.setCreatedDate(new Date().toLocaleString());
		if(state.equals("1"))
		{
			news.setPicUrl(picUrl);
		}else{
			
			String newName=UploadImage.uploadPicture(request, pirUrl);
		//	System.out.println("-----"+ newName);
			news.setPicUrl(newName);
		}
		
		if(!("").equals(sTime))
		{
			news.setStartTime(sTime);
		}else{
			news.setStartTime(stTime);
		}
		if(!("").equals(eTime))
		{
			news.setEndTime(eTime);
		}else{
			
			news.setEndTime(enTime);
		}
		
		System.out.println("++++++++"+news.getStartTime());
		System.out.println("---------"+news.getTitle());
		//System.out.println("++++++++"+news.getStartTime().equals(""));
		newsService.updateNews(news.getNewsId(), news.getTitle(), news.getPicUrl(), news.getContent(), news.getStartTime(), news.getEndTime(), news.getCreatedDate());
			return "redirect:/system/getAllNews";
	}
	@RequestMapping("/addNews")
	public String addNews(News news,@RequestParam("pirUrl")MultipartFile pirUrl,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		
		String newName=UploadImage.uploadPicture(request, pirUrl);
		//System.out.println("-----"+ newName);
		news.setPicUrl(newName);
		newsService.addNews(news.getNewsId(), news.getTitle(), news.getPicUrl(), news.getContent(), news.getStartTime(), news.getEndTime(), news.getCreatedDate());
		return "redirect:/system/getAllNews";
	}
	@RequestMapping("/getNews")
	public String getNews(String startTime,String endTime,HttpServletRequest request) throws Exception
	{
		try {
			
			List<News> news=newsService.getNews(startTime, endTime);
			//查出的总记录条数
			int totalRecord=news.size();
			System.out.println("----总记录数----"+totalRecord);
			int totalPage=(int)(Math.ceil((double)totalRecord/pageSizeInt));
			//System.out.println("---------"+totalPage);
			//System.out.println("----总页数----"+totalPage);
			List<News> newsList=new ArrayList<News>();
			Iterator<News> it = news.iterator();
			if(totalRecord<pageSizeInt)
			{
				while(it.hasNext())
				{
					News newItem=it.next();
					newsList.add(newItem);
				}
			}else{
				for(int i=0;i<pageSizeInt;i++)
				{
					News newItem=it.next();
					newsList.add(newItem);
				}
			}
		//	System.out.println("----第一页记录数----"+newsList.size());
			
			request.setAttribute("newsList", newsList);
			request.setAttribute("pageNow", 1);
			request.setAttribute("totalPage", totalPage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "managePages/table_course";
	}
	
}
