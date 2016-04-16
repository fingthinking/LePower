<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basepath = request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>

<head>
<title>基础代谢计算</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/ordinary.css" />
<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/tool.css" />
<script type="text/javascript" src="<%=basepath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basepath%>/js/bootstrap.min.js"></script>

<style type="text/css">
.input {
	width: 170px;
	background: none;
	text-decoration: inherit;
	border-bottom: 1px solid #005aa7;
	border-top: 0px;
	border-left: 0px;
	border-right: 0px;
	text-align: center;
	color: #FF6666;
}

.h {
	display: inline;
}
</style>

<script type="text/javascript">
	function checkNum() {
		/* alert("执行了"); */
		var age = document.getElementById("age").value;//年龄
		var h = document.getElementById("height").value;//身高
		var w = document.getElementById("weight").value;//体重
		var re = /^[0-9]+(.[0-9]{2})?$/; //判断字符串是否为数字
		/* alert("呦呦呦呦呦"); */
		if (age == "" || h == "" || w == "") {
			alert("输入不能为空");
			return false;
		}
		if (!(re.test(age) && re.test(h) && re.test(w))) {
			alert("只能输入数字");
			document.getElementById("age").value = "";
			document.getElementById("height").value = "";
			document.getElementById("weight").value = "";
			return false;
		}
		document.calorieForm.submit();		
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
			<div class="col-md-8 col-xs-12 tool-section">
				<div class="tool-title">
					<img src="<%=basepath%>/img/tool_calorie.jpg" /> 
					<span><h3>基础代谢率</h3></span>
				</div>
				<div class="tool-content">
					<div class="tool-block tool-border">
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基础代谢率（BMR）是指人体在清醒而又极端安静的状态下，不受肌肉活动、环境温度、食物及精神紧张等影响时的能量代谢率。基础代谢率对减肥有非常大的影响，每天适量的运动有助于提高身体的基础代谢率，而节食(极端是绝食)会降低人的基础代谢率。
						</p>
					</div>

					<div class="tool-block tool-subtitle">
						<h3>请输入您的性别、年龄、身高和体重</h3>
					</div>

					<form action="../webtools/calorie" method="post" id="calorieForm" name="calorieForm">
						<div class="text-left " style="margin-top: 15px;">
							<div class="form-horizontal">
								<div class="tool-block">
									<span>性别</span>
									<input type="radio" name="gender" id="optionsRadios1" value="men" data-toggle="radio" style="width: 10px; margin-left: 30px;">
									<span>男</span>
									<input type="radio" name="gender" id="optionsRadios2" value="women" data-toggle="radio" style="width: 10px; margin-left: 30px;">
									<span>女</span>
								</div>

								<div class="tool-block">
									<span>年龄</span>
									<input class="tool-input form-control " type="text" name="age" id="age" onclick="clearResult()" />
									<span>岁</span>
									<span>身高</span>
									<input class="tool-input form-control " type="text" name="hei" id="height" onclick="clearResult()" />
									<span>cm</span>
									<span>体重</span>
									<input class="tool-input form-control " type="text" name="wei" id="weight" onclick="clearResult()" />
									<span>kg</span>
								</div>

								<div class="tool-block">
									<input type="button" class="input-button button-glow" id="result1" onclick="checkNum()" value="确  定"></input>
								</div>

								<div class="tool-block">
									<span>您的基础代谢率为</span>
									<input class="input-answer" type="text" style="margin-left: 4px;" value="${calorie }" id="result" readOnly="readOnly" />
									<span>卡</span>
								</div>
							</div>
						</div>
					</form>

					<div class="tool-block">
						<p>小贴士：通过性别，年龄，身高和体重能粗略计算基础代谢率。</p>
					</div>
					<hr class="tool-hr" />
				</div>
			</div>

			<div class="col-md-4 col-xs-12">
			<div class="hidden-xs tool-sidebar">
				<div class="tool-heading">健康工具栏</div>
				<ul class="tool-tabs">
					<li class="tool-tab"><a href="../pages/tool_BMI.jsp">身体质量指数</a></li>
					<li class="tool-tab"><a href="../pages/tool_calculate.jsp">标准体重计算</a></li>
					<li class="tool-tab"><a href="../pages/tool_range.jsp">健康体重范围</a></li>
					<li class="tool-tab"><a href="javascript:;">基础代谢计算</a></li>
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