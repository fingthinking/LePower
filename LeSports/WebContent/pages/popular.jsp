<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basepath = request.getContextPath() + "/";
%>
<!doctype html>
<html>

<head>
<title>手机应用</title>
<meta charset=UTF-8">
<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/ordinary.css" />
<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/popular.css" />
<link rel="stylesheet" href="<%=basepath%>/css/popular_style.css">
<script type=text/javascript src="<%=basepath%>/js/menu-js.js"></script>
<meta name=GENERATOR content="MSHTML 10.00.9200.16843">
<script type="text/javascript" src="<%=basepath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basepath%>/js/bootstrap.min.js"></script>
<script type="text/javascript">
	//如果用户没有登录，则不能进入乐友圈
	function remindLogin() {
		alert("请先登陆,才能进入乐友圈！");
	}

	//如果用户没有登录，则不能进入我的运动页面
	function remindLogin2() {
		alert("请先登陆,才能进入我的运动！");
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

	var situp=document.getElementById("app_situp");
	var situpImg=document.getElementById("situp");
	var bike=document.getElementById("app_bike");
	var bikeImg=document.getElementById("bike");
	var push=document.getElementById("app_pushup");
	var pushImg=document.getElementById("pushup");
	/*修改图片src*/	
	
	function onMovOver1(obj){
		obj.src="<%=basepath%>/img/popular_qrCode1.png";
	}
	
	function onMovOut1(obj){
		obj.src="<%=basepath%>/img/title_icon.jpg";
	}
	
	function onMovOver2(obj){
		obj.src="<%=basepath%>/img/popular_qrCode1.png";
	}
	
	function onMovOut2(obj){
		obj.src="<%=basepath%>/img/title_icon.jpg";
	}
	
	function onMovOver3(obj){
		obj.src="<%=basepath%>/img/popular_qrCode1.png";
	}
	
	function onMovOut3(obj){
		obj.src="<%=basepath%>/img/title_icon.jpg";
	}
	
</script>
</head>


<body>
	<nav class="nav navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">

			<div class="navbar-divider">
				<button class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand"> <img
					src="<%=basepath%>/img/logo_min.png" class="img-responsive"
					alt="logo" />
				</a>
			</div>

			<!-- 导航栏 开始 -->
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="<%=basepath%>pages/index.jsp">首页</a></li>
					<!-- userId为空时，说明用户未登录 -->
					<c:choose>
						<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
							<li><a href="<%=basepath%>websport/sportChart">我的运动</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="#" onclick="remindLogin2()">我的运动</a></li>
						</c:otherwise>
					</c:choose>

					<li><a href="<%=basepath%>weblecourse/showNews">Le课堂</a></li>

					<!-- userId为空时，说明用户未登录 -->
					<c:choose>
						<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
							<li><a href="../webcircle/comeIntoCircle">乐友圈</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="" onclick="remindLogin()">乐友圈</a></li>
						</c:otherwise>
					</c:choose>
					<!-- 显示下载链接的二维码图片 -->
					<li><a href="<%=basepath%>pages/popular.jsp">手机应用</a></li>
				</ul>

				<ul class="nav navbar-nav navbar-right" id="toolbar">
					<!-- 当用户未登录时，显示登陆和注册； 登陆以后，显示用户昵称 -->
					<c:choose>
						<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
							<li onmouseover="show()" onmouseout="hide()"><a id="login"
								href="#" class="navbar-link">${sessionScope.loginUserInfo.nickName}</a>
								<ul id="testId" style="display: none;">
									<li><a href="../webUserInfo/showUserInfo">个人中心</a></li>
									<li><a href="<%=basepath%>pages/updatePassword.jsp">修改密码</a></li>
									<li><a href="../webUserInfo/logout">退出</a></li>
								</ul></li>
						</c:when>
						<c:otherwise>
							<li><a id="login" href="<%=basepath%>pages/login.jsp"
								class="navbar-link">登录</a> <a
								href="<%=basepath%>pages/register.jsp" class="navbar-link">注册</a></li>
						</c:otherwise>
					</c:choose>
				</ul>

			</div>
			<!-- 导航栏 结束 -->

		</div>
	</nav>

	<div class="carousel-section">
		<section id="rt-showcase-surround">
			<div id="rt-showcase" class="slider-container rt-overlay-dark">
				<div class="rt-container slider-container">
					<div class="rt-grid-12 rt-alpha rt-omega">
						<div class="csslider1 autoplay">
							<!--自动播放-->
							<input name="cs_anchor1" autocomplete="off" id="cs_slide1_0"
								type="radio" class="cs_anchor slide"> <input
								name="cs_anchor1" autocomplete="off" id="cs_slide1_1"
								type="radio" class="cs_anchor slide"> <input
								name="cs_anchor1" autocomplete="off" id="cs_slide1_2"
								type="radio" class="cs_anchor slide"> <input
								name="cs_anchor1" autocomplete="off" id="cs_play1" type="radio"
								class="cs_anchor" checked> <input name="cs_anchor1"
								autocomplete="off" id="cs_pause1" type="radio" class="cs_anchor">
							
							<ul>
								<div class="carousel-content">
									<!--放入主APP图片-->
									<img src="<%=basepath%>/img/popular_bg_1.jpg">
								</div>
								<li class="num0 img">
									<!--放入主APP图片--> <img src="<%=basepath%>/img/popular_bg_2.jpg"
									alt="mainApp" title="Clouds" />
								</li>
								<li class="num1 img">
									<!--放入跑步图片--> <img src="<%=basepath%>/img/popular_bg_3.jpg"
									alt="runApp" title="Typewriter" />
								</li>
								<li class="num2 img">
									<!--放入计步图片--> <img src="<%=basepath%>/img/popular_bg_2.jpg"
									alt="walkApp" title="Bicycle" />
								</li>
							</ul>
							
							<div class="cs_description">
								<label class="num0"> <span class="cs_title"><span
										class="cs_wrapper">主app口号</span></span>

								</label> <label class="num1"> <span class="cs_title"><span
										class="cs_wrapper">生命，永不止步</span></span>

								</label> <label class="num2"> <span class="cs_title"><span
										class="cs_wrapper">奔跑吧，兄弟</span></span>
								</label>
							</div>
						
							<div class="cs_bullets">
								<label class="num0" for="cs_slide1_0"> <!--主APP的logo-->
									<span class="cs_point"></span> <span class="cs_thumb"><img
										src="<%=basepath%>/img/popular_sm_1.jpg" alt="lw" title="乐动力" /></span>
								</label> <label class="num1" for="cs_slide1_1"> <!--计步App的logo-->
									<span class="cs_point"></span> <span class="cs_thumb"><img
										src="<%=basepath%>/img/popular_sm_2.jpg" alt="Typewriter"
										title="乐计步" /></span>
								</label> <label class="num2" for="cs_slide1_2"> <!--跑步App的logo-->
									<span class="cs_point"></span> <span class="cs_thumb"><img
										src="<%=basepath%>/img/popular_sm_3.jpg" alt="" title="乐跑" /></span>
								</label>
							</div>

						</div>

					</div>
					<div class="clear"></div>
				</div>
			</div>			
		</section>
	</div>
	
	<div class="app-content">
		<div class="container-fluid">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<h3>点击图片扫描二维码下载^-^</h3>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4 app-block" id="app_situp">
				<div class="app-icon">
					<img src="<%=basepath%>/img/title_icon.jpg" id="situp" onmouseover="onMovOver1(this)" onmouseout="onMovOut1(this)"/>
				</div>
				<h3>仰卧起坐</h3>
				<p>在哪都能做的健身运动</p>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4 app-block" id="app_bike">
				<div class="app-icon">
					<img src="<%=basepath%>/img/title_icon.jpg" id="bike"  onmouseover="onMovOver2(this)" onmouseout="onMovOut2(this)"/>
				</div>
				<h3>骑行</h3>
				<p>爱生活，爱骑行</p>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4 app-block" id="app_pushup">
				<div class="app-icon">
					<img src="<%=basepath%>/img/title_icon.jpg"id="pushup"  onmouseover="onMovOver3(this)" onmouseout="onMovOut3(this)"/>
				</div>
				<h3>俯卧撑</h3>
				<p>高质量是我们的追求</p>
			</div>
		</div>
	</div>
</body>

</html>