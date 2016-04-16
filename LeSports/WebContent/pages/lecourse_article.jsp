<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page
	import="com.lesport.model.*,com.lesport.webcontroller.*, java.util.*"%>
	
		<%
	String basepath = request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.min.css" />
	<link rel="stylesheet" href="<%=basepath%>/css/ordinary.css" />
	<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />
	<link rel="stylesheet" href="<%=basepath%>/css/article.css" />
	<script type="text/javascript" src="<%=basepath%>/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basepath%>/js/bootstrap.min.js"></script>
	<title>健康资讯</title>
	
	<script type="text/javascript">
		
		//如果用户没有登录，则不能进入乐友圈
		function remindLogin()
		{
			alert("请先登陆,才能进入乐友圈！");
		}
		
		//如果用户没有登录，则不能进入我的运动页面
		function remindLogin2()
		{
			alert("请先登陆,才能进入我的运动！");
		}
				
	</script>
	
	
</head>

<body>

	<nav class="nav">
		<div class="burger">
			<div class="burger__patty"></div>
		</div>

		<ul class="nav__list">	
			<li class="nav_item"><a href="<%=basepath%>pages/index.jsp">首页</a></li>
				
				<!-- userId为空时，说明用户未登录 -->		
				<c:choose>											
					<c:when test="${ not empty sessionScope.loginUserInfo.userId}" > 						
						<li class="nav_item"><a href="<%=basepath%>websport/sportChart">我的运动</a></li>						
					</c:when>
					<c:otherwise>								
						<li class="nav_item"><a href="#" onclick="remindLogin2()">我的运动</a></li>
					</c:otherwise> 						
				</c:choose>			
				
				<!-- 未登录时不允许进入乐友圈 -->
				<c:choose>
					<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
						<li class="nav_item"><a href="<%=basepath%>webcircle/comeIntoCircle">乐友圈</a></li>
					</c:when>
					<c:otherwise>
						<li class="nav_item"><a href="#" onclick="remindLogin()">乐友圈</a></li>
					</c:otherwise>
				</c:choose>
				
				<!-- 显示下载链接的二维码图片 -->
			<li class="nav_item"><a href="<%=basepath%>pages/popular.jsp">手机应用</a></li>
		</ul>
	</nav>

	<div class="course-section header">
		<div class="container">
			<div class="row ">
				<h1>乐课堂</h1>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row" id="article_section">
			<div id="article_menu">
				<div id="return-list">
					<ul class="return-tabs">
						<li class="return-tab"><a href="<%=basepath%>weblecourse/showNews" class="">乐课堂>></a>
						</li>
					</ul>
				</div>
			</div>
		
			<div class="col-md-12" id="title_section">
				<h1>${newsItem.title } </h1>
			</div>
			
			<div class="col-xs-12 col-sm-12 col-md-12 ">
				<div class="hidden-xs" id="pic_section">
					<img src="../${newsItem.picUrl}" />
				</div>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-12" id="article-content">
				<pre> ${newsItem.content }
				</pre>
			</div>


		</div>
	</div>

	<script type="text/javascript">
		var menu = document.querySelector(".nav__list");
		var burger = document.querySelector(".burger");
		var openMenu = function() {
			burger.classList.toggle('burger--active');
			menu.classList.toggle('nav__list--active');
		};
		burger.addEventListener('click', openMenu, false);
	</script>
</body>
</html>