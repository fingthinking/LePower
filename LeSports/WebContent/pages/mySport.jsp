<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html >
<html>
<head>
<title>我的运动</title>
<base href="<%=basePath%>pages">
<meta charset="utf-8" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/ordinary.css" />
<link rel="stylesheet" href="css/font-awesome-min.css" />
<link rel="stylesheet" href="css/sport.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script src="js/highcharts.js"></script>
<script src="js/sport_chart.js"></script>
<script src="js/jquery-ui.min.js"></script>

<script type="text/javascript">
	//如果用户没有登录，则不能进入乐友圈
	function remindLogin() {
		alert("请先登陆,才能进入乐友圈！");
	}
	var toolbar = document.getElementById("toolbar");
	var li = toolbar.getElementsByTagName("li");
	var userMenu = li.getElementsByTagName("ul");
	alert(userMenu);

	function show() {
		$("#testId").css("display", "block");
		$("#login").text('${sessionScope.loginUserInfo.leNum}');

	}
	userMenu.addEventListener("mousemove", show());

	function hide() {
		$("#testId").css("display", "none");
		$("#login").text('${sessionScope.loginUserInfo.nickName}');
	}
	userMenu.addEventListener("mouseout", hide());
	
</script>
</head>


<body>
	<nav class="nav">
		<div class="burger">
			<div class="burger__patty"></div>
		</div>
		<ul class="nav__list">
			<li class="nav_item"><a href="<%=basePath%>pages/index.jsp">首页</a></li>
			<li class="nav_item"><a href="<%=basePath%>weblecourse/showNews">Le课堂</a></li>
			<!-- 未登录时不允许进入乐友圈 -->
			<c:choose>
				<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
					<li class="nav_item"><a
						href="<%=basePath%>webcircle/comeIntoCircle">乐友圈</a></li>
				</c:when>
				<c:otherwise>
					<li class="nav_item"><a href="#" onclick="remindLogin()">乐友圈</a></li>
				</c:otherwise>
			</c:choose>

			<!-- 显示下载链接的二维码图片 -->
			<li class="nav_item"><a href="<%=basePath%>pages/popular.jsp">手机应用</a></li>

		</ul>
	</nav>

	<div class="title-section">
		<div class="container sport-background">
			<div class="row">
				<div class="col-md-10 col-sm-10 col-xs-10">
					<div class="font">
						<img src="<%=basePath%>img/mySport_title_font.png" style="margin-top: -130px; margin-left: 50px;">
					</div>
				</div>
			
				<div class="col-md-2 col-sm-2 col-xs-2 title-user">
					<div class="navbar-default" style="background:rgba(255,255,255,0.5);">
						<ul class="nav navbar-nav navbar-right" id="toolbar">
						<!-- 当用户未登录时，显示登陆和注册； 登陆以后，显示用户昵称 -->						
							<li onmouseover="show()" onmouseout="hide()"><a id="login"
								href="javascript:;" style="color:#ff6600" class="navbar-link">${sessionScope.loginUserInfo.nickName}</a>
								<ul id="testId" style="display: none;">
									<li><a href="<%=basePath%>webUserInfo/showUserInfo">个人中心</a></li>
									<li><a href="<%=basePath%>pages/updatePassword.jsp">修改密码</a></li>
									<li><a href="<%=basePath%>webUserInfo/logout">退出</a></li>
								</ul>
							</li>					                               
						</ul>
					</div>
				</div>
		
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-sm-9 col-md-8">
				<div id="home-section">
					<div class="home-content">
						<hr class="top-hr">
						<div class="date_analy">
							<p>截止目前，您本月运动数据如下：</p>
						</div>
						<div class="date_analy">
							<p id="info">&nbsp;&nbsp;&nbsp;&nbsp;登录才可以看见信息哦！</p>
						</div>
					</div>

					<div class="sport_chart">
						<div id="sum_cal"></div>
						<br />
						<div id="step_chart"></div>
						<br />
						<div id="mile_chart"></div>
					</div>
				</div>

			</div>

			<div class="col-xs-12 col-sm-3 col-md-4">
				<div class="hidden-xs" id="friendlist_section">
					<div class="person-list" id="user_name">
						<c:if test="${!empty imgURL }">
							<img src="${imgURL}">
						</c:if>
						<c:if test="${empty imgURL }">
							<img src="<%=basePath%>img/defaultImg.jpg">
						</c:if>

						<h1>${nickName}</h1>
						<p>
							今日共跑了<strong>${disToday}</strong>km
						</p>
					</div>

					<div class="friend-list">
						<div class="title-list">
							<h1>里程排行榜</h1>
						</div>

						<c:if test="${empty runRange }">
							<p style="text-align: center; padding-top:20px">
								<strong style="font-size: 25px;">今日尚无运动信息 </strong>
							</p>
						</c:if>
						<ul>
							<c:if test="${!empty runRange }">
								<c:forEach items="${runRange}" var="runUser">
									<li class="list-tab"><img src="${runUser.imgURL}">
										<p>
											<strong>${runUser.nickName } </strong>${runUser.runDistance }km
										</p></li>
								</c:forEach>
							</c:if>
						</ul>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var menu = document.querySelector(".nav__list");
	var burger = document.querySelector(".burger");
	var openMenu = function() {
		burger.classList.toggle('burger--active');
		menu.classList.toggle('nav__list--active');
	};
	burger.addEventListener('click', openMenu, false);
</script>
</html>