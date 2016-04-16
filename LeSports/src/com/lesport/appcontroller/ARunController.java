package com.lesport.appcontroller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.set.SynchronizedSortedSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesport.model.UserRun;
import com.lesport.service.RunService;
import com.lesport.util.Utility;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/run")
public class ARunController {

	@Autowired
	private RunService runService;

	/**
	 * 跑步模块接口
	 * 
	 * @param request
	 * @return
	 */

	/**
	 * 1．添加跑步数据接口 addRunInfo
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addRunInfo", method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addRunInfo(UserRun userRun) {
		try {
			// 获取传递参数,封装成对象
			userRun.setRunId(Utility.getRowId());
			userRun.setCreatedDate(Utility.getFormattedCurrentDateAndTime());
			if (runService.save(userRun)) {
				return Utility.packReturnJson(0,"","");
			} else {
				return Utility.packReturnJson(1,"","");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			return Utility.packReturnJson(1,"","");
		}
	}
	/**
	 * 2．查看跑步信息接口 getRunInfo
	 * 
	 * @param request 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/getRunInfo", method=RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getRunInfo(HttpServletRequest request) throws ParseException {
		String userId = request.getParameter("userId");
		String date = request.getParameter("date");
		
		Date begin = (new SimpleDateFormat("yyyy-MM-dd")).parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(begin);
		cal.add(Calendar.DATE, 1);
		String beginDate=new SimpleDateFormat("yyyy-MM-dd").format(begin);
		String endDate=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		
		List<UserRun> userRun = runService.findById(userId,beginDate,endDate);
		if (userRun != null) {
			JSONArray json=JSONArray.fromObject(userRun);
			return Utility.packReturnJson(0,"",userRun);
		} else
		{
			return Utility.packReturnJson(1,"","");
		}
		
		// String a = request.getParameter("a");
		// response.setContentType("text/html");
		// response.setCharacterEncoding("UTF-8"); // 解决中文乱码问题
		// PrintWriter out = response.getWriter();
		//
		// if (a != null && a.equals("1")) {
		// Map map = new HashMap();
		// map.put("name", "Dana、Li");
		// map.put("age", new Integer(22));
		// map.put("Provinces", new String("广东省"));
		// map.put("citiy", new String("珠海市"));
		// map.put("Master", new String("C、C++、Linux、Java"));
		// JSONObject json = JSONObject.fromObject(map);
		// out.write("{resCode:1,resMsg:\"success\",data:[" + json.toString() +
		// "]}");
		// } else {
		// out.write(Utility.getRowId() +
		// "{resCode:0,resMsg:\"error\",data:null}");
		// }
		// out.flush();
		// out.close();
	}

	/**
	 * 3．删除跑步记录接口 deleteRunInfo
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteRunInfo")
	@ResponseBody
	public String deleteRunInfo(HttpServletRequest request) {
		String runId = request.getParameter("runId");		
		if (runService.delete(runId)) {
			return Utility.packReturnJson(0,"","");
		} else {
			return Utility.packReturnJson(1,"","");
		}
	}
}
