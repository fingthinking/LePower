package com.lesport.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lesport.model.UserInfo;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {		
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();

        // 获得用户请求的URI
        String path = servletRequest.getRequestURI();
        
        
        //从session里取用户UserId
        UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
        
        String userId = null;
        
        if(null != loginUserInfo)
        {
        	userId = loginUserInfo.getUserId();
        }
        	
        
        if((path.indexOf("/friend_circle.jsp") > -1 || path.indexOf("/mySport.jsp") > -1 || path.indexOf("/personal_center.jsp") > -1) 
        		&& (userId == null || "".equals(userId)))
        {
        	
        	servletResponse.sendRedirect("/LeSports/pages/login.jsp");            
            return;
        	
        }
        else if((path.contains("webcircle") || path.contains("websport") || path.contains("webUserInfo/showUserInfo")) 
        		&& (userId == null || "".equals(userId)) )
        {
        
        	servletResponse.sendRedirect("/LeSports/pages/login.jsp");        
        	return;
        }        
        else
        {        	
        	chain.doFilter(request, response);
        }
        
      
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
