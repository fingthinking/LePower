<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basepath = request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>

<head>
<title>标准体重</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/ordinary.css" />
<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/tool.css" />
<script type="text/javascript" src="<%=basepath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basepath%>/js/bootstrap.min.js"></script>

<script type="text/javascript">
	function checkNum() {
		
		var age = document.getElementById("age").value;//年龄
		var h = document.getElementById("height").value;//身高
		var re = /^[0-9]+(.[0-9]{2})?$/; //判断字符串是否为数字
		
		if (age == "" || h == "") {
			alert("输入不能为空");
			return false;
		}
		if (!(re.test(age) && re.test(h))) {
			alert("只能输入数字");
			document.getElementById("age").value = "";
			document.getElementById("height").value = "";
			return false;
		}
		document.calForm.submit();		
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
					<img src="<%=basepath%>/img/tool_calculate.jpg" /> <span><h3>标准体重</h3></span>
				</div>
				<div class="tool-content">
					<div class="tool-block tool-border">
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标准体重是反映和衡量一个人健康状况的重要标志之一。过胖和过瘦都不利于健康，也不会给人以健美感。不同体型的大量统计材料表明，反映正常体重较理想和简单的指标，可用身高体重的关系来表示。
						</p>
					</div>
					<div class="tool-block tool-subtitle">
						<h3>请输入您的年龄和身高</h3>
					</div>
					<form action="../webtools/calculate" method="post" id="calForm" name="calForm">
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
									<input class="tool-input form-control" type="text" name="age" id="age" onclick="clearResult()" />
									<span>岁</span>
									<span style="margin-left: 70px;">身高</span>
									<input class="tool-input form-control" type="text" name="hei" id="height" onclick="clearResult()" />
									<span>cm</span>
								</div>

								<div class="tool-block">
									<input type="button" class="input-button button-glow" onclick="checkNum()" value="确   定"></input>
								</div>

								<div class="tool-block">
									<span>您的年龄身高对应的标准体重为</span>
									<input class="input-answer" type="text" style="margin-left: 4px;" value="${stanard }" id="result" readOnly="readOnly" />
									<span>kg</span>
								</div>

							</div>
						</div>
					</form>
					
					<div class="tool-block">
						<pre>小贴士：输入年龄和身高，最后计算得出标准体重。
							       计算适用范围：
							       女性：19岁到69岁，身高在152cm到176cm。
							       男性：19岁到69岁，身高在152cm到188cm。
						</pre>
					</div>

					<hr class="tool-hr" />
				</div>
			</div>
			<div class="col-md-4 col-xs-12">
			<div class="hidden-xs tool-sidebar">
				<div class="tool-heading">健康工具栏</div>
				<ul class="tool-tabs">
					<li class="tool-tab"><a href="../pages/tool_BMI.jsp">身体质量指数</a></li>
					<li class="tool-tab"><a href="javascript:;">标准体重计算</a></li>
					<li class="tool-tab"><a href="../pages/tool_range.jsp">健康体重范围</a></li>
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