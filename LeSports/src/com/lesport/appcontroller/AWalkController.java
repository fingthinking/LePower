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

import com.lesport.model.UserRun;
import com.lesport.model.UserWalk;
import com.lesport.service.RunService;
import com.lesport.service.WalkService;
import com.lesport.util.Utility;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/walk")
public class AWalkController {

	@Autowired
	private WalkService walkService;

	/**
	 * 计步模块接口
	 * 
	 * @param request
	 * @return
	 */

	/**
	 * 1．添加计步数据接口 addWalkInfo
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addWalkInfo")
	@ResponseBody
	public String addWalkInfo(UserWalk userWalk) {
		try {
			// 获取传递参数,封装成对象
			if (walkService.IsExist(userWalk) != null) {
				if (walkService.update(userWalk)) {
					return Utility.packReturnJson(0, "", "");
				} else {
					return Utility.packReturnJson(1, "", "");
				}
			} else {
				userWalk.setWalkId(Utility.getRowId());
				userWalk.setCreatedDate(Utility.getFormattedCurrentDateAndTime());
				if (walkService.save(userWalk)) {
					return Utility.packReturnJson(0, "", "");
				} else {
					return Utility.packReturnJson(1, "", "");
				}
			}

		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			return Utility.packReturnJson(1, "exception", "");
		}
	}

	/**
	 * 2．查看计步信息接口 getWalkInfo
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getWalkInfo")
	@ResponseBody
	public String getWalkInfo(HttpServletRequest request) throws ParseException {
		
	
		System.out.println("已进入：getWalkInfo");
		
		String userId = request.getParameter("userId");
		String date = request.getParameter("date");
		String all = request.getParameter("all");

		List<UserWalk> userWalk = walkService.findById(userId, date, all);
		if (userWalk != null) {
			return Utility.packReturnJson(0, "", userWalk);
		} else {
			return Utility.packReturnJson(1, "", "");
		}
	}
}
