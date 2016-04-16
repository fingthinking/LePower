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
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesport.model.UserBicycle;
import com.lesport.model.UserPushup;
import com.lesport.model.UserRun;
import com.lesport.service.BicycleService;
import com.lesport.service.PushupService;
import com.lesport.service.RunService;
import com.lesport.util.Utility;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/pushup")
public class APushupController {

	@Autowired
	private PushupService pushupService;

	/**
	 * 俯卧撑模块接口
	 * 
	 * @param request
	 * @return
	 */

	/**
	 * 1．添加俯卧撑数据接口 addPushupInfo
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addPushupInfo")
	@ResponseBody
	public String addPushupInfo(UserPushup userPushup) {
		try {
			
			// 获取传递参数,封装成对象
			userPushup.setPushupId(Utility.getRowId());
			userPushup.setCreatedDate(Utility.getFormattedCurrentDateAndTime());
			if (pushupService.save(userPushup)) {
				return Utility.packReturnJson(0,"","");
			} else {
				return Utility.packReturnJson(1,"","");
			}
		} catch (Exception e) {
			return Utility.packReturnJson(1,"","");
		}
	}

	/**
	 * 2．查看俯卧撑信息接口 getPushupInfo
	 * 
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/getPushupInfo")
	@ResponseBody
	public String getPushupInfo(HttpServletRequest request) throws ParseException {
		String userId = request.getParameter("userId");
		String date = request.getParameter("date");		
		List<UserPushup> userPushup = pushupService.findById(userId,date);
		if (userPushup != null) {
			JSONArray json=JSONArray.fromObject(userPushup);
			return Utility.packReturnJson(0,"",userPushup);
		} else
		{
			return Utility.packReturnJson(1,"","");
		}
		
	}
}
