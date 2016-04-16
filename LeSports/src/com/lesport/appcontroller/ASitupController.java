package com.lesport.appcontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesport.model.UserBicycle;
import com.lesport.model.UserPushup;
import com.lesport.model.UserRun;
import com.lesport.model.UserSitup;
import com.lesport.service.BicycleService;
import com.lesport.service.PushupService;
import com.lesport.service.RunService;
import com.lesport.service.SitupService;
import com.lesport.util.Utility;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/situp")
public class ASitupController {

	@Autowired
	private SitupService situpService;

	/**
	 * 仰卧起坐模块接口
	 * 
	 * @param request
	 * @return
	 */

	/**
	 * 1．添加仰卧起坐数据接口 addSitupInfo
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addSitupInfo")
	@ResponseBody
	public String addSitupInfo(UserSitup userSitup) {
		try {
			// 获取传递参数,封装成对象
			userSitup.setSitupId(Utility.getRowId());
			userSitup.setCreatedDate(Utility.getFormattedCurrentDateAndTime());
			if (situpService.save(userSitup)) {
				return Utility.packReturnJson(0,"","");
			} else {
				return Utility.packReturnJson(1,"","");
			}
		} catch (Exception e) {
			return Utility.packReturnJson(1,"","");
		}
	}

	/**
	 * 2．查看仰卧起坐信息接口 getSitupInfo
	 * 
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/getSitupInfo")
	@ResponseBody
	public String getSitupInfo(HttpServletRequest request) throws ParseException {
		String userId = request.getParameter("userId");
		String date = request.getParameter("date");		
		List<UserSitup> userSitup = situpService.findById(userId,date);
		//将时间末尾的0去除
		for (Iterator iterator = userSitup.iterator(); iterator.hasNext();) {
			UserSitup userSitup2 = (UserSitup) iterator.next();			
			userSitup2.setStartTime(userSitup2.getStartTime().substring(0, 19));
			userSitup2.setEndTime(userSitup2.getEndTime().substring(0, 19));			
		}		
		
		if (userSitup != null) {
			JSONArray json=JSONArray.fromObject(userSitup);
			return Utility.packReturnJson(0,"",userSitup);
		} else
		{
			return Utility.packReturnJson(1,"","");
		}
		
	}
}
