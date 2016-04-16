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
<title>健康资讯</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/ordinary.css" />
<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />
<link rel="stylesheet" href="<%=basepath%>/css/lecourse.css" />
<script type="text/javascript" src="<%=basepath%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basepath%>/js/bootstrap.min.js"></script>

<style type="text/css">
.bubtn {
	background: none;
	border: none;
	text-decoration: inherit;
}

.btntitle {
	background: none;
	border: none;
	text-decoration: inherit;
}
</style>

<script type="text/javascript">
	//如果用户没有登录，则不能进入乐友圈
	function remindLogin() {
		alert("请先登陆,才能进入乐友圈！");
	}

	//如果用户没有登录，则不能进入我的运动页面
	function remindLogin2() {
		alert("请先登陆,才能进入我的运动！");
	}

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

<body style="background: rgba(133, 193, 204, 0.6);">

	<nav class="nav">
		<div class="burger">
			<div class="burger__patty"></div>
		</div>

		<ul class="nav__list">
			<li class="nav_item"><a href="<%=basepath%>pages/index.jsp">首页</a></li>
			<!-- userId为空时，说明用户未登录 -->
			<c:choose>
				<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
					<li class="nav_item"><a
						href="<%=basepath%>websport/sportChart">我的运动</a></li>
				</c:when>
				<c:otherwise>
					<li class="nav_item"><a href="#" onclick="remindLogin2()">我的运动</a></li>
				</c:otherwise>
			</c:choose>
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

	<div class="title-section">
		<div class="container lecourse-background">
			<div class="row">
				<div class="col-md-10 col-sm-10 col-xs-10">
					<div class="font">
						<img src="<%=basepath%>img/lecourse_title_font.png"
							style="margin-top: -130px; margin-left: 50px;">
					</div>
				</div>

				<!-- 如果用户未登录，不显示个人中心 -->
				<c:if test="${not empty sessionScope.loginUserInfo.nickName}">
					<div class="col-md-2 col-sm-2 col-xs-2 title-user">
						<div class="navbar-default"
							style="background: rgba(0, 0, 0, 0.5);">
							<ul class="nav navbar-nav navbar-right" id="toolbar">
								<!-- 当用户未登录时，显示登陆和注册； 登陆以后，显示用户昵称 -->
								<li onmouseover="show()" onmouseout="hide()"><a id="login"
									href="javascript:;" style="color: #FFCC66" class="navbar-link">${sessionScope.loginUserInfo.nickName}</a>
									<ul id="testId" style="display: none;">
										<li><a href="<%=basepath%>webUserInfo/showUserInfo">个人中心</a></li>
										<li><a href="<%=basepath%>pages/updatePassword.jsp">修改密码</a></li>
										<li><a href="<%=basepath%>webUserInfo/logout">退出</a></li>
									</ul></li>
							</ul>
						</div>
					</div>
				</c:if>

			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row" id="home-section">
			<div class="col-md-10 col-sm-12">
				<div class="quick-list">
					<div class="col-md-12 col-sm-12 col-xs-12 panel-heading">快速预览</div>
					<!-- 新闻快速浏览 -->
					<div class="col-xs-12 col-sm-9 col-md-8 carousel-section">
						<section class="hidden-xs quick-turn">
							<div id="carousel-new" class="carousel slide"
								data-ride="carousel">

								<!-- 添加轮播时的圆点 -->
								<ol class="carousel-indicators">
									<c:set var="flag" value="1" />
									<c:set var="sum" value="0" />

									<c:forEach var="news" items="${news}">
										<c:choose>
											<c:when test="${sum == 0 }">
												<li data-target="#carousel-new" data-slide-to="${sum }"
													class="active"></li>
											</c:when>
											<c:otherwise>
												<li data-target="#carousel-new" data-slide-to="${sum }"></li>
											</c:otherwise>
										</c:choose>

										<c:set var="sum" value="${sum+flag}" />
									</c:forEach>
								</ol>

								<div class="carousel-inner" role="listbox">
									<c:set var="flag" value="1" />
									<c:set var="sum" value="0" />
									<c:forEach var="news" items="${news}">

										<c:set var="sum" value="${sum+flag}" />
										<c:choose>
											<c:when test="${sum==1 }">
												<div class=" item active">
													<img style="width: 100%; height: 320px;"
														src="../${news.picUrl }" alt="" />

													<div class="carousel-caption">
														<form action="../weblecourse/showItemNews" method="post">
															<input type="hidden" name="id" value="${news.newsId }" />
															<a href="#">
																<button type="submit" class="bubtn text-center"
																	id="btn_detail">
																	<h2>${news.title}</h2>
																</button>
															</a>
														</form>
													</div>

												</div>
											</c:when>
											<c:otherwise>
												<div class=" item ">
													<img style="width: 80%; height: 320px; margin: 0 10%;"
														src="../${news.picUrl }" alt="" />

													<div class="carousel-caption">
														<form action="../weblecourse/showItemNews" method="post">
															<input type="hidden" name="id" value="${news.newsId }" />
															<a href="#">
																<button type="submit" class="bubtn text-center"
																	id="btn_detail">
																	<h2>${news.title}</h2>
																</button>
															</a>
														</form>
													</div>

												</div>
											</c:otherwise>

										</c:choose>

									</c:forEach>

								</div>

								<a class="left carousel-control" href="#carousel-new"
									role="button" data-slide="prev"> <span
									class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
									<span class="sr-only">往前</span>
								</a> <a class="right carousel-control" href="#carousel-new"
									role="button" data-slide="next"> <span
									class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
									<span class="sr-only">往后</span>

								</a>

							</div>
						</section>
					</div>
					<div class="col-xs-12 col-sm-3 col-md-4" id="hotspot"
						style="height: 82%;">
						<div class="panel-body">
							<ul class="nav-stacked">
								<c:forEach var="news" items="${news}">
									<form action="../weblecourse/showItemNews" method="post">
										<input type="hidden" name="id" value="${news.newsId }" />
										<li style="height: 50px;"><a href="#"><input
												type="submit" class="bubtn text-left" id="btn_detail"
												value=${news.title}></input></a></li>
									</form>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<div class="quick-content">
					<!-- 具体内容页 -->
					<div class="new-content-menu">
						<!-- <div id="page-list"></div> -->
						<div class="new-list ">
							<ul>
								<!-- 遍历所有新闻资讯 -->
								<c:forEach var="news" items="${news}">
									<li class="new-item" data-post-id="">
										<div class="new-info col-sm-12 col-md-6">
											<div class=" img-list">
												<form action="../weblecourse/showItemNews" method="post">
													<input type="hidden" name="id" value="${news.newsId }" />
													<li><a href="#"><input type="submit" class="bubtn"
															id="btn_detail" value=${news.title}></input></a></li>
												</form>

												<!-- 截取内容的一部分展示到页面上 -->
												<%
													News newsItem = (News) pageContext.getAttribute("news");
														String id = newsItem.getNewsId();
														String title = newsItem.getTitle();
														String content = newsItem.getContent();
														String subContent = null;
														String date = newsItem.getCreatedDate();
														if ((content.length() >= 0) && (content.length() < 60)) {
															subContent = content;
														} else {
															subContent = content.substring(0, 60) + "......";
														}
														System.out.println(subContent);
												%>
												<p>
													&nbsp;&nbsp;&nbsp;&nbsp;<%=subContent%>......
												</p>

												<form action="../weblecourse/showItemNews" method="post">
													<input type="hidden" name="id" value="${news.newsId }" />
													<input type="submit" class="btn_sub" id="btn_detail"
														value="查看更多>>"></input>
												</form>

											</div>
										</div>
										<div class="col-xs-12 col-sm-12 col-md-6">
											<div class="new-photo" data-post-id="" data-source="">
												<img src="../${news.picUrl}" />
												<!-- <a href="#" target="_blank"> 
										</a> -->
											</div>
										</div>
										<p class="new-footer">
											<span id="time" class="new-time"
												datetime="2016-03-05T22:02:32">${news.createdDate }</span>
										</p>
									</li>
								</c:forEach>
							</ul>

							<!-- 上下页栏 开始 -->
							<div class="ds-paginator">
								<div class=" ds-border"></div>
								<!-- 上一页标签 -->
								<c:url var="prePage" value="/weblecourse/prePage">
									<c:param name="pageNow" value="${pageNow }"></c:param>
									<c:param name="totalPage" value="${totalPage }"></c:param>
								</c:url>

								<!-- 下一页标签 -->
								<c:url var="nextPage" value="/weblecourse/nextPage">
									<c:param name="pageNow" value="${pageNow }"></c:param>
									<c:param name="totalPage" value="${totalPage }"></c:param>
								</c:url>

								<a href=${prePage }>上一页 </a> 目前你位于：${pageNow }&nbsp;&nbsp;共有${totalPage}页

								<a href=${nextPage }>下一页 </a>
							</div>
						</div>
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