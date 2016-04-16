<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basepath = request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>

<head>
<title>身体质量指数</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/ordinary.css" />
<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/tool.css" />
<script type="text/javascript" src="<%=basepath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basepath%>/js/bootstrap.min.js"></script>

<script type="text/javascript">
	function checkNum() {
		
		var w = document.getElementById("weight").value;//体重
		var h = document.getElementById("height").value;//身高
		var re = /^[0-9]+(.[0-9]{2})?$/; //判断字符串是否为数字
		
		if (w == "" || h == "") {
			alert("输入不能为空");
			return false;
		}
		if (!(re.test(w) && re.test(h))) {
			alert("只能输入数字");
			document.getElementById("weight").value = "";
			document.getElementById("height").value = "";
			return false;
		}
		document.bmiForm.submit();	
	}

	function clearResult() {
		document.getElementById("result").value = "";
		document.getElementById("bmi").value = "";
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
					<img src="<%=basepath%>/img/tool_bmi.jpg" /><span><h3>BMI身体质量指数</h3></span>
				</div>
				<div class="tool-content">
					<div class="tool-block tool-border">
						<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;BMI(Body
							Mass Index)
							即BMI指数，也叫身体质量指数，是衡量是否肥胖和标准体重的重要指标。适用范围：18至65岁的人士。儿童、发育中的青少年、孕妇、乳母、老人及身型健硕的运动员除外。世界卫生组织认为BMI指数保持在22左右是比较理想的。
						</p>
					</div>

					<div class="tool-block tool-subtitle">
						<h3>请输入您的身高和体重</h3>
					</div>

					<form action="../webtools/bmitools" method="post" id="bmiForm"
						name="bmiForm">
						<div class="text-left " style="margin-top: 15px;">
							<div class="form-horizontal">
								<span>身高</span> <input class="tool-input form-control "
									type="text" name="hei" id="height" onclick="clearResult()" />
								<span>cm</span> <span style="margin-left: 70px;">体重</span> <input
									class="tool-input form-control " type="text" name="wei"
									id="weight" onclick="clearResult()" /> <span>kg</span>
								<div class="text-left">
									<input type="button" class="input-button button-glow"
										style="margin-top: 2em;" onclick="checkNum()" value="确   定"></input>
								</div>
								<div style="margin-top: 2em;" class="tool-block">
									<span>您的BMI身体质量指数为</span> <input class="input-answer"
										type="text" value="${bmi }" id="result" readOnly="readOnly" />
								</div>
							</div>
						</div>

					</form>

					<div class="tool-block">
						<p>小贴士：通过身高体重计算你的BMI值，从而得出你的身体状况评估，高于24你就是超重啦!</p>
					</div>

					<hr class="tool-hr" />

					<div class="row">
						<div class=col-md-12>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th colspan="4">成年人身体质量指数</th>
										</tr>
										<tr>
											<th>轻体重BMI</th>
											<th>健康体重BMI</th>
											<th>超重BMI</th>
											<th>肥胖BMI</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>BMI <18.5</td>
											<td>18.5≤BMI <24</td>
											<td>24≤BMI <28</td>
											<td>28≤BMI</td>
										</tr>
									</tbody>
								</table>
								<div class="table-tip">
									<pre>资料来源：
										• 《中国居民膳食指南》（2007）P72——中国营养学会
										• 《中国成年人超重和肥胖症预防控制指南》（2003年）卫生部疾病控制司
									</pre>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
			<div class="col-md-4 col-xs-12">
				<div class="hidden-xs tool-sidebar">
					<div class="tool-heading">健康工具栏</div>
					<ul class="tool-tabs">
						<li class="tool-tab"><a href="javascript:;">身体质量指数</a></li>
						<li class="tool-tab"><a href="../pages/tool_calculate.jsp">标准体重计算</a></li>
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