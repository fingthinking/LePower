package com.lesport.webcontroller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lesport.model.Manager;
import com.lesport.model.News;
import com.lesport.service.ManagerService;
import com.lesport.service.NewsService;
import com.lesport.util.Utility;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private ManagerService managerService;
	
	/**
	 * 获取所有管理员列表
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getAllManager")
	public ModelAndView getAllManager() throws Exception{
		List<Manager> list=managerService.findAllManager();
		//System.out.println("----------"+list);
		ModelAndView mav=new ModelAndView("managePages/table_manager");
		mav.addObject("list",list);
		return mav;
		
	}
	@RequestMapping("/addManager")
	public  String addManager(Manager manager,HttpServletRequest  request ) throws Exception {

		manager.setManagerId(Utility.getRowId());
		manager.setCreatedDate(Utility.getFormattedCurrentDateAndTime());
		managerService.insertManager(manager);
	
		return "redirect:/manager/getAllManager";
	}
	
	@RequestMapping(value="/deleteManager")
	public void delManager(String  managerId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		System.out.println(managerService.deleteManager(managerId));
		String result="error";
		try{
			if (managerService.deleteManager(managerId)==1) {
				result="success";
				System.out.println(result);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			PrintWriter out = response.getWriter();
			out.write(result);
		}
		
	}
	
	@RequestMapping("/updateManager")
	public String updateManager(Manager manager,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		System.out.println(manager.getManagerId());
		managerService.updateManager(manager);
		System.out.println(managerService.updateManager(manager));
		return "redirect:/manager/getAllManager";
	}
//	@RequestMapping("/addManager")
//	public String addNews(Manager manager) throws Exception
//	{
//		managerService.insertManager(manager.getManagerName(), manager.getManagerPwd(), manager.getAuthority());
//		return "redirect:/system/getAllNews";
//	}
	@RequestMapping("/getManager")
	public String getManager(String managerId,HttpServletRequest request) throws Exception
	{
		request.setAttribute("manager", managerService.findManagerById(managerId));
		request.setAttribute("status", 1);
		return"/update_manager";
	}


}
