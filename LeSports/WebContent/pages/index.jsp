<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basepath = request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>首页</title>
<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/ordinary.css" />
<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/index.css" />
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
					<li><a href="#">首页</a></li>
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

	<div id="home" class="home-section">
		<div class="container">
			<div class="row"></div>
		</div>
	</div>


	<!-- 图片链接 开始 -->
	<div class="divider-grids ">

		<!-- 我的运动 开始 -->
		<c:choose>
			<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
				<a href="<%=basepath%>websport/sportChart">
			</c:when>
			<c:otherwise>
				<a href="#" onclick="remindLogin2()">
			</c:otherwise>
		</c:choose>
		<div class="col-md-4  col-sm-6 divider-grid-info">
			<div class="divider-grid red">
				<div class="divider-grid-img red-grid">
					<img src="<%=basepath%>/img/index_1.jpg" alt="" />
					<h3>我的运动</h3>
					<p>您的专业数据分析师</p>
				</div>
			</div>
		</div>
		</a>
		<!-- 我的运动 结束 -->

		<a href="../weblecourse/showNews">
			<div class="col-md-4 col-sm-6 divider-grid-info">
				<div class="divider-grid yellow">
					<div class="divider-grid-img yellow-grid">
						<img src="<%=basepath%>/img/index_2.jpg" alt="" />
						<h3>Le课堂</h3>
						<p>健康教学，快乐教学</p>
					</div>
				</div>
			</div>
		</a>

		<!-- 乐友圈 开始 -->
		<c:choose>
			<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
				<a href="../webcircle/comeIntoCircle">
			</c:when>
			<c:otherwise>
				<a href="" onclick="remindLogin()">
			</c:otherwise>
		</c:choose>
		<div class="col-md-4 col-sm-12 divider-grid-info">
			<div class="divider-grid blue">
				<div class="divider-grid-img blue-grid">
					<img src="<%=basepath%>/img/index_3.jpg" alt="" />
					<h3>乐友圈</h3>
					<p>独乐乐不如众乐乐</p>
				</div>
			</div>
		</div>
		</a>
		<!-- 乐友圈 结束 -->
	</div>
	<!-- 图片链接 结束 -->

	<!-- 健康工具 开始 -->
	<div class="col-md-12 col-sm-12 divider-grid-info divider-toolbar">
		<div class="toolbar-content">
			<div class="col-md-3 col-sm-6">
				<div class="divider-tool">
					<div class="divider-tool-img">
						<a href="../pages/tool_BMI.jsp" target=_blank>
							<div class="tool-imgdetail">
								<img style="margin-left: 5px;"
									src="<%=basepath%>/img/index_BMI.png" />
							</div>
							<div class="tool-imgdetail">
								<h4>身体质量指数</h4>
							</div>
						</a>
					</div>
				</div>
			</div>

			<div class="col-md-3 col-sm-6">
				<div class="divider-tool">
					<div class="divider-tool-img">
						<a href="../pages/tool_calculate.jsp" target=_blank>
							<div class="tool-imgdetail">
								<img style="margin-left: 5px;" src="<%=basepath%>/img/index_calculate.png" />
							</div>
							<div class="tool-imgdetail">
								<h4>标准体重计算</h4>
							</div>
						</a>
					</div>
				</div>
			</div>


			<div class="col-md-3 col-sm-6">
				<div class="divider-tool">
					<div class="divider-tool-img">
						<a href="../pages/tool_range.jsp" target=_blank>
							<div class="tool-imgdetail">
								<img style="margin-left: 5px;"
									src="<%=basepath%>/img/index_weight.png" />
							</div>
							<div class="tool-imgdetail">
								<h4>健康体重范围</h4>
							</div>
						</a>
					</div>
				</div>
			</div>


			<div class="col-md-3 col-sm-6">
				<div class="divider-tool">
					<div class="divider-tool-img">
						<a href="../pages/tool_calorie.jsp" target=_blank>
							<div class="tool-imgdetail">
								<img style="margin-left: 5px;"
									src="<%=basepath%>/img/index_base.png" />
							</div>
							<div class="tool-imgdetail">
								<h4>基础代谢计算</h4>
							</div>
						</a>
					</div>
				</div>
			</div>

		</div>

	</div>
	<!-- 健康工具 结束 -->

</body>

</html>