<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basepath = request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>

<head>
<title>健康体重范围</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/ordinary.css" />
<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/tool.css" />
<link
	href="<%=basepath%>/fonts/font-awesome-4.2.0/css/font-awesome.min.css"
	rel="stylesheet" />

<script type="text/javascript" src="<%=basepath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basepath%>/js/bootstrap.min.js"></script>

<script type="text/javascript">
	function checkNum() {		
		var h = document.getElementById("height").value;//身高
		var re = /^[0-9]+(.[0-9]{2})?$/;//判断字符串是否为数字
		
		if (h == "") {
			alert("输入不能为空");
			return false;
		}
		if (!re.test(h)) {
			alert("只能输入数字");
			document.getElementById("height").value = "";
			return false;
		}
		document.rangeForm.submit();		
	}

	function clearResult() {		
		document.getElementById("result").value = "";
	}
	
	//如果用户没有登录，则不能进入乐友圈
	function remindLogin() {
		alert("请先登陆,才能进入乐友圈！");
	}

	//如果用户没有登录，则不能进入我的运动页面
	function remindLogin2() {
		alert("请先登陆,才能进入我的运动！");
	}
</script>

</head>

<body onload="openMenu()">

	<nav class="nav">
		<div class="burger">
			<div class="burger__patty"></div>
		</div>
		<ul class="nav__list">
			<li class="nav_item"><a href="<%=basepath%>pages/index.jsp">首页</a></li>
			<c:choose>
				<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
					<li class="nav_item"><a
						href="<%=basepath%>websport/sportChart">我的运动</a></li>
				</c:when>
				<c:otherwise>
					<li class="nav_item"><a href="#" onclick="remindLogin2()">我的运动</a></li>
				</c:otherwise>
			</c:choose>

			<li class="nav_item"><a href="<%=basepath%>weblecourse/showNews">Le课堂</a></li>
			<!-- 未登录时不允许进入乐友圈 -->
			<c:choose>
				<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
					<li class="nav_item"><a
						href="<%=basepath%>webcircle/comeIntoCircle">乐友圈</a></li>
				</c:when>
				<c:otherwise>
					<li class="nav_item"><a href="#" onclick="remindLogin()">乐友圈</a></li>
				</c:otherwise>
			</c:choose>


			<!-- 显示下载链接的二维码图片 -->
			<li class="nav_item"><a href="<%=basepath%>pages/popular.jsp">手机应用</a></li>
		</ul>
	</nav>
	
	<div class="top-section-1 header">
		<div class="container">
			<div class="row">
				<h1>健康工具</h1>
			</div>
		</div>
	</div>

	<div class=" container-fluid">
		<div class="row">
			<div class="col-md-8 col-sm-9 col-xs-12 tool-section">
				<div class="tool-title">
					<img src="<%=basepath%>/img/tool_range.jpg" /> <span><h3>健康体重范围</h3></span>
				</div>

				<div class="tool-content">
					<div class="tool-block tool-border">
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;体重是反应和衡量一个人健康状况的重要标志之一，过瘦或过胖都不利于健康。健康体重是对应于个体身高的最佳体重值；健康体重不同于标准体重，是一个范围值，处于健康体重范围内的人群，患病的几率较低。其原理是通过BMI身体质量指数反向推算。
						</p>
					</div>

					<div class="tool-block tool-subtitle">
						<h3>请输入您的身高：</h3>
					</div>

					<form action="../webtools/range" method="post" id="rangeForm"
						name="rangeForm">
						<div class="text-left " style="margin-top: 15px;">
							<div class="form-horizontal">

								<div class="tool-block">
									<span>身高</span> <input class="tool-input form-control "
										type="text" name="hei" id="height" onclick="clearResult()" />
									<span>cm</span> <input type="button"
										class="input-button button-glow" onclick="checkNum()"
										value="确  定"></input>
								</div>

								<div class="tool-block">
									<span>您的健康体重范围为</span> <input class="input-answer" type="text"
										value="${result }" id="result" readOnly="readOnly" /> <span>kg</span>
								</div>
							</div>
						</div>
					</form>

					<div class="tool-block">
						<p>小贴士：健康范围的身体质量指数(BMI)在18.5到24之间，结合身高，就可以计算出健康体重范围。</p>
					</div>
					<hr class="tool-hr" />
					<br />
				</div>

			</div>
			<div class="col-md-4 col-sm-3 col-xs-12">
				<div class="hidden-xs tool-sidebar">
					<div class="tool-heading">健康工具栏</div>
					<ul class="tool-tabs">
						<li class="tool-tab"><a href="../pages/tool_BMI.jsp">身体质量指数</a></li>
						<li class="tool-tab"><a href="../pages/tool_calculate.jsp">标准体重计算</a></li>
						<li class="tool-tab"><a href="javascript:;">健康体重范围</a></li>
						<li class="tool-tab"><a href="../pages/tool_calorie.jsp">基础代谢计算</a></li>
					</ul>
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