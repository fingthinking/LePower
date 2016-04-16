package com.lesport.appcontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesport.model.UserBicycle;
import com.lesport.model.UserRun;
import com.lesport.service.BicycleService;
import com.lesport.service.RunService;
import com.lesport.util.Utility;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/bicycle")
public class ABicycleController {

	@Autowired
	private BicycleService bicycleService;

	/**
	 * 骑车模块接口
	 * 
	 * @param request
	 * @return
	 */

	/**
	 * 1．添加骑车数据接口 addBicycleInfo
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addBicycleInfo", method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addBicycleInfo(UserBicycle userBicycle) {
		try {
			// 获取传递参数,封装成对象
			userBicycle.setBicycleId(Utility.getRowId());
			userBicycle.setCreatedDate(Utility.getFormattedCurrentDateAndTime());
			if (bicycleService.save(userBicycle)) {
				return Utility.packReturnJson(0,"","");
			} else {
				return Utility.packReturnJson(1,"","");
			}
		} catch (Exception e) {
			return Utility.packReturnJson(1,"","");
		}
	}

	/**
	 * 2．查看骑车信息接口 getBicycleInfo
	 * 
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/getBicycleInfo", method=RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getBicycleInfo(HttpServletRequest request) throws ParseException {
		
		System.out.println("get request in bicycle---------------");
		
		String userId = request.getParameter("userId");
		String date = request.getParameter("date");		
		String type = request.getParameter("type");	
		
		List<UserBicycle> userBicycle=null;
		if("1".equals(type))
		{
			userBicycle = bicycleService.findCoordById(userId,date);
		}else
		{
			userBicycle = bicycleService.findById(userId,date);
		}
		if (userBicycle != null) {
			
			System.out.println("bicycl result : "+Utility.packReturnJson(0,"",userBicycle));
			
			return Utility.packReturnJson(0,"",userBicycle);
		} else
		{
			return Utility.packReturnJson(1,"","");
		}
		
	}
}
