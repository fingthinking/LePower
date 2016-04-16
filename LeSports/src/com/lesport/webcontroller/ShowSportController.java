package com.lesport.webcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lesport.model.SportTotal;
import com.lesport.service.ShowSportDataService;
import com.sun.mail.iap.Response;

@Controller
@RequestMapping("/showsport")
public class ShowSportController {

	@Autowired
	private ShowSportDataService showService;
	
	@RequestMapping("/data")
	public String  showSportData(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		PrintWriter out=response.getWriter();
			List<SportTotal> sportRun=showService.showRun();
			System.out.println("1++++++++"+sportRun);
			if(!sportRun.isEmpty())
			{
				//System.out.println(sportRun.isEmpty());
				request.setAttribute("sportlist1", sportRun);
			}
			List<SportTotal> sportWalk=showService.showWalk();
			System.out.println("2++++++++"+sportWalk);
			if(!sportWalk.isEmpty())
			{
				request.setAttribute("sportlist2", sportWalk);
			}
			List<SportTotal> sportSitUp=showService.showSitUp();
			System.out.println("3++++++++"+sportSitUp);
			if(!sportSitUp.isEmpty())
			{
				request.setAttribute("sportlist3", sportSitUp);
			}
			List<SportTotal> sportPushUp=showService.showPushUp();
			System.out.println("4++++++++"+sportPushUp);
			if(!sportPushUp.isEmpty())
			{
				request.setAttribute("sportlist4", sportPushUp);
			}
			List<SportTotal> sportJump=showService.showJump();
			System.out.println("5++++++++"+sportJump);
			if(!sportJump.isEmpty())
			{
				request.setAttribute("sportlist5", sportJump);
			}
			List<SportTotal> sportBicycle=showService.showBicycle();
			System.out.println("6++++++++"+sportBicycle);
			if(!sportBicycle.isEmpty())
			{
				request.setAttribute("sportlist6", sportBicycle);
			}
		return "managePages/table_sportshow";
	}
}
