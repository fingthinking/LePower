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
import com.lesport.model.UserJump;
import com.lesport.model.UserPushup;
import com.lesport.model.UserRun;
import com.lesport.service.BicycleService;
import com.lesport.service.JumpService;
import com.lesport.service.PushupService;
import com.lesport.service.RunService;
import com.lesport.util.Utility;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/jump")
public class AJumpController {

	@Autowired
	private JumpService jumpService;

	/**
	 * 跳绳模块接口
	 * 
	 * @param request
	 * @return
	 */

	/**
	 * 1．添加跳绳数据接口 addJumpInfo
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addJumpInfo")
	@ResponseBody
	public String addJumpInfo(UserJump userJump) {
		try {
			// 获取传递参数,封装成对象
			userJump.setJumpId(Utility.getRowId());
			userJump.setCreatedDate(Utility.getFormattedCurrentDateAndTime());
			if (jumpService.save(userJump)) {
				return Utility.packReturnJson(0,"","");
			} else {
				return Utility.packReturnJson(1,"","");
			}
		} catch (Exception e) {
			return Utility.packReturnJson(1,"","");
		}
	}

	/**
	 * 2．查看跳绳信息接口 getJumpInfo
	 * 
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/getJumpInfo")
	@ResponseBody
	public String getJumpInfo(HttpServletRequest request) throws ParseException {
		String userId = request.getParameter("userId");
		String order = request.getParameter("order");		
		List<UserJump> userJump = jumpService.findById(userId,order);
		if (userJump != null) {
			JSONArray json=JSONArray.fromObject(userJump);
			return Utility.packReturnJson(0,"",userJump);
		} else
		{
			return Utility.packReturnJson(1,"","");
		}
	}
	
	/**
	 * 2．获取服务端最大Order接口 getJumpOrder
	 * 
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/getJumpOrder")
	@ResponseBody
	public String getJumpOrder(HttpServletRequest request) throws ParseException {
		String userId = request.getParameter("userId");
		//String date = request.getParameter("date");		
		String order = jumpService.getJumpOrder(userId);
		if (order != null) {
			//JSONArray json=JSONArray.fromObject(order);
			//return Utility.packReturnJson(0,"",order);
			return "{resCode:0,resMsg:\"\",data:\""+order+"\"}";
		} else
		{
			return Utility.packReturnJson(1,"","");
		}
	}
}
