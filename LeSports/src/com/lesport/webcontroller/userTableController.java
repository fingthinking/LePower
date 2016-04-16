package com.lesport.webcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lesport.model.UserInfo;
import com.lesport.service.IUserInfoService;

@Controller
@RequestMapping("/webManage")
public class userTableController {

	@Autowired
	private IUserInfoService userInfo;
	
	@RequestMapping(value = "/allUser")
	public ModelAndView showUserInfo(HttpServletRequest request) throws Exception{
		//System.out.println("nickName:" + nickName);
		String nickName= request.getParameter("nickName");
		List<UserInfo> userInfos = new ArrayList<>();
		userInfos = userInfo.getUserByName(nickName);
		//System.out.println("userInfo:" + userInfos.size());
//		System.out.println(userInfos);
		
		ModelAndView modelAndView = new ModelAndView("managePages/table_user");
		if(userInfos!=null)
		{
		modelAndView.addObject("userInfos", userInfos);
		modelAndView.addObject("size",userInfos.size());
		}
		return modelAndView;
	}
}
