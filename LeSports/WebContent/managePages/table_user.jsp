<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%
	String basepath = request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>资讯管理</title>


<link href="<%=basepath%>/manacss/bootstrap.min.css" rel="stylesheet">


<link href="<%=basepath%>/manacss/admin.css" rel="stylesheet">


<link href="<%=basepath%>/manacss/font-awesome.min.css" rel="stylesheet"
	type="text/css">

<!--font  -->
<link href="<%=basepath%>/css/font-awesome-min.css" rel="stylesheet">
<!-- jQuery -->
<script src="<%=basepath%>managePages/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="<%=basepath%>managePages/js/bootstrap.min.js"></script>

<!-- Morris Charts JavaScript -->
<script src="<%=basepath%>managePages/js/plugins/morris/raphael.min.js"></script>
<script src="<%=basepath%>managePages/js/plugins/morris/morris.min.js"></script>
<script src="<%=basepath%>managePages/js/plugins/morris/morris-data.js"></script>

</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation"
			id="nav-section">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
					<span class="sr-only">导航</span> <span class="icon-bar"></span> <span
						class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.jsp"> <img
					src="<%=basepath%>img/logo_min.png" />
				</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">

				<li class="dropdown" id="nav_username"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"><i
						class="fa fa-user"></i> Bella <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="Sign in.html"><i
								class="fa fa-fw fa-power-off"></i> 退出</a></li>
					</ul></li>
			</ul>
			<!-- 侧边栏 -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
			<ul class="nav navbar-nav side-nav">
				<li><a href="<%=basepath%>managePages/index.jsp">面板<i
						class="fa fa-fw fa-dashboard"></i>
				</a></li>
				<li><a href="<%=basepath%>webManage/allUser">用户管理</a></li>

				<li><a href="<%=basepath%>manager/getAllManager">管理员信息管理</a></li>
				<li><a href="<%=basepath%>system/getAllNews">资讯信息管理</a></li>

				<li><a href="<%=basepath%>webcirManage/getAllCircle">乐友圈管理</a></li>
				<li><a href="<%=basepath%>showsport/data">运动数据管理</a></li>
			</ul>
			</div>
			<!-- /.navbar-collapse -->
		</nav>
		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">用户</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a
								href="../managePages/index.jsp">主页</a></li>
							<li class="active"><i class="fa fa-table"></i><a
								href="allUser">用户管理</a></li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-13">
						<h2>用户列表</h2>
						<br />
						<form action="../webManage/allUser" method="post">
							<div class="input-group" style="width: 25%">
								<span class="input-group-addon">昵称</span> <input type="text"
									name="nickName" class="form-control" /> <span
									class="input-group-btn"><input type="submit" value="查 询"
									class="btn btn-primary btn-warning " /></span>
							</div>
						</form>

						<br />

						<div class="table-responsive">
							<table class="table table-bordered table-hover" id="table_user">
								<colgroup>

								</colgroup>
								<thead>
									<tr>
										<th colspan="16" style="text-align: right">用户数量：${size }</th>
									</tr>
									<tr>
										<th class="th_min">序号</th>
										<th class="th_mid">昵称</th>
										<th class="th_min">性别</th>
										<th class="th_mid">邮箱</th>
										<th class="th_mid">手机</th>
										<th class="th_min">身高</th>
										<th class="th_min">体重</th>
										<th class="th_mid">生日</th>
										<th class="th_min">微博</th>
										<th class="th_mid">QQ</th>
										<th class="th_min">省份</th>
										<th class="th_min">城市</th>
										<th class="th_min">运动标签</th>
										<th class="th_min">等级</th>
										<th class="th_max">创建时间</th>
										<th class="th_max">最后登录时间</th>
									</tr>
								</thead>
								<tbody>
									<%int i=1; %>
									<c:forEach items="${userInfos }" var="userInfo">
										<tr>
											<%-- <th >${userInfo.userId }</th> --%>
											<th><%=i++ %></th>
											<th class="th_mid">${userInfo.nickName }</th>
											<th class="th_min">${userInfo.sex }</th>
											<th class="th_max">${userInfo.email }</th>
											<th class="th_max">${userInfo.phoneNum }</th>
											<th class="th_min">${userInfo.height }</th>
											<th class="th_min">${userInfo.weight }</th>
											<th class="th_max">${userInfo.birthday }</th>
											<th class="th_mid">${userInfo.weiboNum }</th>
											<th class="th_mid">${userInfo.qqNum }</th>
											<th class="th_min">${userInfo.province }</th>
											<th class="th_mid">${userInfo.city }</th>
											<th class="th_mid">${userInfo.sportType }</th>
											<th class="th_min">${userInfo.level }</th>
											<th class="th_max">${userInfo.createdDate }</th>
											<th class="th_max">${userInfo.lastLogTime }</th>

										</tr>
									</c:forEach>

								</tbody>
							</table>
						</div>
					</div>

				</div>

			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

</body>

</html>