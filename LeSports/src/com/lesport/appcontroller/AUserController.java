package com.lesport.appcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesport.service.UserService;

@Controller
@RequestMapping("/user1")
public class AUserController {

	@Autowired
	private UserService userService;
	
	
	/**
	 * 接口测试用例
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/isLogin")
	@ResponseBody
	public String isLogin(HttpServletRequest request){
		
		String name=request.getParameter("name");
		
		if(("admin").equals(name))
		{
			return "success";
		}else
		{
			return "error";
		}
	}
}
