package com.lesport.webcontroller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lesport.model.CirLike;
import com.lesport.model.Circle;
import com.lesport.model.Comment;
import com.lesport.model.RecordCommentAndLike;
import com.lesport.model.UserInfo;
import com.lesport.service.CircleManageService;
import com.lesport.service.CircleService;
import com.lesport.util.Utility;

@Controller
@RequestMapping("/webcirManage")
public class CircleManageController {

	@Autowired
	private CircleManageService circleManageService;
	
	int pageSizeInt = 9;
	
	//获取乐友圈管理列表
	@RequestMapping(value="/getAllCircle")
	public ModelAndView getAllCircle(HttpServletRequest request, HttpSession session)
	{	
		String nickName= request.getParameter("nickName");
//		List<Circle> circles = new ArrayList<>();
//		circles = circleManageService.findCirByName(nickName);
		
		int offset = 0;
		//获取乐友圈动态总数
		int totalRecord = circleManageService.getCountOfCircle(nickName);
		int totalPage = ((int) Math.ceil((double)totalRecord/pageSizeInt));		
		totalPage = (totalPage == 0) ? 1 : totalPage;		
		//获取每页动态列表
		List<Circle> circles = circleManageService.getCircleList(nickName,offset, pageSizeInt);	
		
		//定义ModelAndView，并将参数存入其中
		ModelAndView mav = new ModelAndView("managePages/table_circle");	
		mav.addObject("circles", circles);
		mav.addObject("pageNow",1);
		mav.addObject("totalPage",totalPage);		
		return mav;
	}
	
	//下一页
	@RequestMapping(value="/nextPage")
	public ModelAndView nextPage(HttpServletRequest request,String pageNow, String totalPage)
	{
		String nickName= request.getParameter("nickName");
		//当前页码
		int pageNowInt = Integer.parseInt(pageNow);
		
		//总页数
		int totalPageInt = Integer.parseInt(totalPage);
		
		//下一页页码
		pageNowInt = (pageNowInt+1)>totalPageInt ? totalPageInt : pageNowInt+1;
		
		//偏移量
		int offset = (pageNowInt-1)*pageSizeInt ;
	
		//获取动态
		List<Circle> circles = circleManageService.getCircleList(nickName,offset, pageSizeInt);	
		
		//定义ModelAndView，并将参数存入其中
		ModelAndView mav = new ModelAndView("managePages/table_circle");	
		mav.addObject("circles", circles);
		mav.addObject("pageNow",pageNowInt);
		mav.addObject("totalPage", totalPage);		
		return mav;
	}
	
	//上一页
	@RequestMapping(value="/prePage")
	public ModelAndView prePage(HttpServletRequest request,String pageNow, String totalPage)
	{
		String nickName= request.getParameter("nickName");
		//当前页码
		int pageNowInt = Integer.parseInt(pageNow);
		
		//上一页页码
		pageNowInt = (pageNowInt-1)<1 ? 1 : pageNowInt-1;
		
		//偏移量
		int offset = (pageNowInt-1)*pageSizeInt ;
		
		//获取动态
		List<Circle> circles = circleManageService.getCircleList(nickName,offset, pageSizeInt);	
		
		//定义ModelAndView，并将参数存入其中
		ModelAndView mav = new ModelAndView("managePages/table_circle");	
		mav.addObject("circles", circles);
		mav.addObject("pageNow",pageNowInt);
		mav.addObject("totalPage", totalPage);			

		return mav;
	}
	
	
	@RequestMapping(value="/deleteCircle")
	public void deleteCircle(String  circleId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String result="error";
		try{
			if (circleManageService.deleteCircle(circleId)) {
				result="success";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			PrintWriter out = response.getWriter();
			out.write(result);
		}
		
	}
	
/*	//findCirByName根据昵称搜索用户动态信息
	@RequestMapping(value = "/findCirByName")
	public ModelAndView showUserInfo(HttpServletRequest request) throws Exception{
		String nickName= request.getParameter("nickName");
	
		List<Circle> circles = new ArrayList<>();
		circles = circleManageService.findCirByName(nickName);
		
		ModelAndView modelAndView = new ModelAndView("managePages/table_circle");
		if(circles!=null)
		{
		modelAndView.addObject("circles", circles);
		}
		return modelAndView;
	}*/
}
