package com.lesport.appcontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesport.model.SportTotal;
import com.lesport.model.UserRun;
import com.lesport.service.RunService;
import com.lesport.service.SportTotalService;
import com.lesport.util.Utility;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/sport")
public class SportTotalController {

	@Autowired
	private SportTotalService sportService;

	/**
	 * 运动数据统计模块接口
	 * 
	 * @param request
	 * @return
	 */


	/**
	 * 统计运动信息接口 getSportInfo
	 * 
	 * @param request 
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/getSportInfo")
	@ResponseBody
	public String getSportInfo(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		List<SportTotal> sportTotal = new ArrayList<SportTotal>();
		
		SportTotal run=sportService.getRunInfo(userId);
		SportTotal walk=sportService.getWalkInfo(userId);
		SportTotal bike=sportService.getBikeInfo(userId);
		SportTotal pushup=sportService.getPushupInfo(userId);
		SportTotal jump=sportService.getJumpInfo(userId);
		SportTotal situp=sportService.getSitupInfo(userId);
		if(run!=null) 	sportTotal.add(run);	
		if(walk!=null)   sportTotal.add(walk);	
		if(bike!=null)	sportTotal.add(bike);	
		if(pushup!=null)	sportTotal.add(pushup);	
		if(jump!=null)	sportTotal.add(jump);	
		if(situp!=null)	sportTotal.add(situp);	
		
		if (sportTotal != null) {
			return Utility.packReturnJson(0,"",sportTotal);
		} else
		{
			return Utility.packReturnJson(1,"","");
		}
	}
	
	
	/**
	 * 获取个人运动总信息接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSportTotalInfo")
	@ResponseBody
	public String getSportTotalInfo(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		List<SportTotal> sportTotal = new ArrayList<SportTotal>();
		
		SportTotal run=sportService.getRunInfo(userId);
		SportTotal walk=sportService.getWalkInfo(userId);
		SportTotal bike=sportService.getBikeInfo(userId);
		SportTotal pushup=sportService.getPushupInfo(userId);
		SportTotal jump=sportService.getJumpInfo(userId);
		SportTotal situp=sportService.getSitupInfo(userId);
		
		double TotalDistance=Double.parseDouble(run.getSportDis())+Double.parseDouble(walk.getSportDis())+Double.parseDouble(bike.getSportDis());
		double TotalCalorie=Double.parseDouble(run.getSportCal())+Double.parseDouble(walk.getSportCal())+Double.parseDouble(bike.getSportCal())+
				Double.parseDouble(pushup.getSportCal())+Double.parseDouble(jump.getSportCal())+Double.parseDouble(situp.getSportCal());
		int TotalSteps=Integer.parseInt(walk.getSportSteps());
		double TotalTime=Double.parseDouble(walk.getSportTime())+Double.parseDouble(walk.getSportTime());
		TotalTime=TotalTime/3600;
//		if(run!=null) 	sportTotal.add(run);	
//		if(walk!=null)   sportTotal.add(walk);	
//		if(bike!=null)	sportTotal.add(bike);	
//		if(pushup!=null)	sportTotal.add(pushup);	
//		if(jump!=null)	sportTotal.add(jump);	
//		if(situp!=null)	sportTotal.add(situp);	
		String resultJson="\"runDistance\":\""+TotalDistance+"\",\"calorieSum\":\""+TotalCalorie+"\",\"totalStep\":\""+TotalSteps+"\",\"runTime\":\""+TotalTime+"\"";
		System.out.println(resultJson);;
		if (sportTotal != null) {
			return "{resCode:1,resMsg:\"success\",data:[{" +resultJson +"}]}";
			//return Utility.packReturnJson(0,"",sportTotal);
		} else
		{
			return Utility.packReturnJson(1,"","");
		}
	}
	
}

