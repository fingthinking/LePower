package com.lesport.webcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lesport.model.SportData;
import com.lesport.model.UserInfo;
import com.lesport.service.SportDataService;

@Controller
@RequestMapping("/websport")
public class SportChartController {

	@Autowired
	private SportDataService sportService;
	
	
	@RequestMapping("/sportChart")
	public String getUser(HttpServletRequest request,HttpSession session){
		
		//从session中获取userId
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");		
		String userId = loginUserInfo.getUserId();		
		
		String disToday="0";
		String nickName="";
		String imgURL="";
		
		java.sql.Date date=new java.sql.Date(new Date().getTime());
		UserInfo userDis=sportService.getRunDis(userId,date.toString());
		if(userDis!=null)
		{
			if(userDis.getRunDistance()!=null)
			{
				disToday=userDis.getRunDistance();
			}
			nickName=userDis.getNickName();
			imgURL=userDis.getImgURL();
			System.out.println("nickName"+nickName);
		}
		
		//获取跑步排行榜
		List<UserInfo> runRange=sportService.getRunRange(date.toString());
				
		request.setAttribute("nickName",nickName);
		request.setAttribute("disToday",disToday);
		request.setAttribute("imgURL",imgURL);
		request.setAttribute("runRange",runRange);
		System.out.println(imgURL+runRange.size());
		return "/pages/mySport";
	}
	
	/**
	 * 获取本月运动数据
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getSportData")
	public void getSportData(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");		
		String userId = loginUserInfo.getUserId();	
		
		String beginDate=request.getParameter("beginDate");
		String endDate=request.getParameter("endDate");
		response.setContentType("application/json");
		System.out.println(beginDate+","+endDate);
		
		List<SportData> sportData=sportService.getSportData(userId,beginDate, endDate);
		double runCalSum=0;
		double walkCalSum=0;
		double bikeCalSum=0;
		
		String runCal="[";
		String walkCal="[";
		String bikeCal="[";
		
		String runDis="[";
		String walkDis="[";
		String bikeDis="[";
		
		String runTime="[";
		String walkTime="[";
		String bikeTime="[";
		for (SportData sport : sportData) {
			runCalSum+=sport.getRunCal();
			walkCalSum+=sport.getWalkCal();
			bikeCalSum+=sport.getBikeCal();
			
			runCal+=sport.getRunCal()+",";
			walkCal+=sport.getWalkCal()+",";
			bikeCal+=sport.getBikeCal()+",";
			
			runDis+=sport.getRunDis()+",";
			walkDis+=sport.getWalkDis()+",";
			bikeDis+=sport.getBikeDis()+",";
			
			runTime+=sport.getRunTime()+",";
			walkTime+=sport.getWalkTime()+",";
			bikeTime+=sport.getBikeTime()+",";
		}
		runCal=runCal.substring(0, runCal.length()-1);
		walkCal=walkCal.substring(0, walkCal.length()-1);
		bikeCal=bikeCal.substring(0, bikeCal.length()-1);
		
		runDis=runDis.substring(0, runDis.length()-1);
		walkDis=walkDis.substring(0, walkDis.length()-1);
		bikeDis=bikeDis.substring(0, bikeDis.length()-1);
		
		runTime=runTime.substring(0, runTime.length()-1);
		walkTime=walkTime.substring(0, walkTime.length()-1);
		bikeTime=bikeTime.substring(0, bikeTime.length()-1);
		
		runCal+="]";
		walkCal+="]";
		bikeCal+="]";
		
		runDis+="]";
		walkDis+="]";
		bikeDis+="]";
		
		runTime+="]";
		walkTime+="]";
		bikeTime+="]";
		
		String result="{\"runCalSum\":\""+runCalSum+"\",\"walkCalSum\":\""+walkCalSum+"\",\"bikeCalSum\":\""+bikeCalSum+"\","
				+ "\"runCal\":\""+runCal+"\",\"walkCal\":\""+walkCal+"\",\"bikeCal\":\""+bikeCal+"\","
				+ "\"runDis\":\""+runDis+"\",\"walkDis\":\""+walkDis+"\",\"bikeDis\":\""+bikeDis+"\","
				+ "\"runTime\":\""+runTime+"\",\"walkTime\":\""+walkTime+"\",\"bikeTime\":\""+bikeTime+"\""
				+ "}";
	//	System.out.println(result);
		try {
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
