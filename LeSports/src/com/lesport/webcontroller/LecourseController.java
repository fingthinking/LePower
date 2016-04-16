package com.lesport.webcontroller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lesport.model.News;
import com.lesport.service.NewsService;

/**
 * 获取健康资讯并显示到页面上
 */

@Controller
@RequestMapping("/weblecourse")

public class LecourseController {

	@Autowired
	private NewsService newsService;
	int pageSizeInt = 5;
   /**
    * 
    * @return
    * @throws Exception 
    */
	
	/*@RequestMapping(value="/showNews")
	public  ModelAndView showNews() throws Exception{

	// 每页大小
	

	/**
	 * 
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/showNews")
	public ModelAndView showNews(HttpServletRequest request) throws Exception {

		List<News> news = newsService.findAllNews();
		
		// 查出的总记录条数
		int totalRecord = news.size();
		System.out.println("----总记录数----"+totalRecord);
		// 分页数
		int totalPage = (int) Math.ceil((double) totalRecord / pageSizeInt);
		
		System.out.println("----总页数----"+totalPage);
		
		List<News> news1 = new ArrayList<News>();
		Iterator<News> it = news.iterator();
		if(totalRecord<pageSizeInt){
			for (int i = 0; i < totalRecord; i++) {

				News newsItem = new News();
				newsItem = it.next();
				news1.add(newsItem);
			}
			
		}else{
			
			for (int i = 0; i < pageSizeInt; i++) {

				News newsItem = new News();
				newsItem = it.next();
				news1.add(newsItem);

			}
			
		}
		
		System.out.println("----第一页记录数----"+news1.size());
		
		//获取服务器地址
		/*String path = request.getContextPath();
		String serIp = InetAddress.getLocalHost().getHostAddress();
		path = serIp+":8080"+path;*/
		
//		System.out.println("服务器地址： "+path);
		
		ModelAndView mav = new ModelAndView("pages/lecourse");
		mav.addObject("news", news1);
		mav.addObject("pageNow", 1);
		mav.addObject("totalPage", totalPage);
//		mav.addObject("path", path);

		return mav;

	}
	
	

	@RequestMapping(value = "/nextPage")
	public ModelAndView nextPage(String pageNow, String totalPage,HttpServletRequest request) throws Exception {

		List<News> news = newsService.findAllNews();
		int totalRecord = news.size();
		
		// 当前页码
		int pageNowInt = Integer.parseInt(pageNow);

		// 总页数
		int totalPageInt = Integer.parseInt(totalPage);

		// 下一页页码
		pageNowInt = (pageNowInt + 1) > totalPageInt ? totalPageInt : pageNowInt + 1;	
		System.out.println("--------pageNowInt--------: " + pageNowInt);

		// 剩余记录条数
		int reminderRecord = totalRecord - (pageNowInt-1) * pageSizeInt;
		System.out.println("-------reminderRecord-------- " + reminderRecord);

		List<News> news2 = new ArrayList<News>();
		// Iterator<News> it = news.iterator();

		if (reminderRecord < pageSizeInt) {
			for (int i = ((pageNowInt-1) * pageSizeInt) ; i < (reminderRecord +((pageNowInt-1) * pageSizeInt)); i++) {
				News newsItem = new News();
				newsItem = news.get(i);
//				System.out.println("-------if-------- " + newsItem);
				news2.add(newsItem);
			}

		} else {
			for (int i = ((pageNowInt-1) * pageSizeInt) ; i < (((pageNowInt-1) * pageSizeInt)+pageSizeInt); i++) {

				News newsItem = new News();
				newsItem = news.get(i);
//				System.out.println("第 "+pageNowInt+"页记录："+ newsItem);
				news2.add(newsItem);

			}

		}

		//获取服务器地址
		String path = request.getContextPath();
		String serIp = InetAddress.getLocalHost().getHostAddress();	
		path = serIp+":8080"+path;
		
		
		ModelAndView mav = new ModelAndView("pages/lecourse");

		mav.addObject("news", news2);
		mav.addObject("path", path);
		
		if((Integer.parseInt(pageNow)+1)>totalPageInt){
			mav.addObject("pageNow",totalPageInt );
		}else{
			mav.addObject("pageNow", Integer.parseInt(pageNow) +1);
		}
		
		mav.addObject("totalPage", totalPage);

		// System.out.println("count: "+news.size());

		return mav;
	}

	@RequestMapping(value = "/prePage")
	public ModelAndView prePage(String pageNow, String totalPage,HttpServletRequest request) throws Exception {

		System.out.println("--------pageNow--------: " +Integer.parseInt(pageNow));
		List<News> news = newsService.findAllNews();
		

		// 当前页码
		int pageNowInt = Integer.parseInt(pageNow);
		// 上一页页码
		pageNowInt = (pageNowInt - 1) < 1 ? 1 : pageNowInt - 1;
	
		
//		System.out.println("当前页面（prepage）: " + pageNowInt);
		
		List<News> news3 = new ArrayList<News>();
		//将上一页内容取出
		for(int i=(pageNowInt-1)*pageSizeInt;i<(pageNowInt)*pageSizeInt;i++){
			
			News newsItem = new News();
			newsItem = news.get(i);
//			System.out.println("第 "+pageNowInt+"页记录："+ newsItem);
			news3.add(newsItem);
			
		}
		
		//获取服务器地址
		String path = request.getContextPath();
		String serIp = InetAddress.getLocalHost().getHostAddress();	
		path = serIp+":8080"+path;

		ModelAndView mav = new ModelAndView("pages/lecourse");

		
		mav.addObject("news", news3);
		mav.addObject("path", path);
		
	    if((Integer.parseInt(pageNow)-1)<=0){
		   mav.addObject("pageNow", 1);
		}else{
			mav.addObject("pageNow", Integer.parseInt(pageNow) - 1);		
		}
		
		mav.addObject("totalPage", totalPage);

		// System.out.println("count: "+news.size());

		return mav;
	}

	// 点击“查看更多”跳转到的页面
	@RequestMapping(value = "/showItemNews")
	public ModelAndView showItemNews(String id,HttpServletRequest request) throws Exception {
		
		//获取服务器地址
		String path = request.getContextPath();
	    String serIp = InetAddress.getLocalHost().getHostAddress();	
	    path = serIp+":8080"+path;

		News newsItem = newsService.getHealthyInfo(id);
		ModelAndView mav = new ModelAndView("pages/lecourse_article");
		mav.addObject("newsItem", newsItem);
		mav.addObject("path", path);
		return mav;

	}
	
	
}
