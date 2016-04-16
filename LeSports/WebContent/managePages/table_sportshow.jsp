<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%
	String basepath = request.getContextPath() + "/";
%>

<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
<!DOCTYPE html>
<!-- <html lang="en"> -->

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>运动数据管理</title>

<link href="<%=basepath%>/manacss/bootstrap.min.css" rel="stylesheet">

<link href="<%=basepath%>/manacss/admin.css" rel="stylesheet">

<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!--font  -->
<link href="<%=basepath%>/css/font-awesome-min.css" rel="stylesheet">

<!-- jQuery -->
<script src="../managePages/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../managePages/js/bootstrap.min.js"></script>

<!-- Morris Charts JavaScript -->
<script src="js/plugins/morris/raphael.min.js"></script>
<script src="js/plugins/morris/morris.min.js"></script>
<script src="js/plugins/morris/morris-data.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		$("#run").show();
		$("#walk").hide();
		$("#situp").hide();
		$("#pushup").hide();
		$("#jump").hide();
		$("#bicycle").hide();
	});

	function show(id) {
		//	alert("nihao");
		if (id == 0) {

			//	alert("你好");
			//	alert("进了吗");
			$("#run").show();
			$("#walk").hide();
			$("#situp").hide();
			$("#pushup").hide();
			$("#jump").hide();
			$("#bicycle").hide();
		} else if (id == 1) {

			//	alert("你好");
			$("#run").hide();
			$("#walk").show();
			$("#situp").hide();
			$("#pushup").hide();
			$("#jump").hide();
			$("#bicycle").hide();
		} else if (id == 2) {
			//	alert("你好");
			$("#run").hide();
			$("#walk").hide();
			$("#situp").show();
			$("#pushup").hide();
			$("#jump").hide();
			$("#bicycle").hide();
		} else if (id == 3) {

			//	alert("你好");
			$("#run").hide();
			$("#walk").hide();
			$("#situp").hide();
			$("#pushup").show();
			$("#jump").hide();
			$("#bicycle").hide();
		} else if (id == 4) {
			//		alert("你好");
			$("#run").hide();
			$("#walk").hide();
			$("#situp").hide();
			$("#pushup").hide();
			$("#jump").show();
			$("#bicycle").hide();
		} else if (id == 5) {
			//	alert("你好");
			$("#run").hide();
			$("#walk").hide();
			$("#situp").hide();
			$("#pushup").hide();
			$("#jump").hide();
			$("#bicycle").show();
		}
	}
</script>

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
						<h1 class="page-header">运动数据分析</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a
								href="../managePages/index.jsp">主页</a></li>
							<li class="active"><i class="fa fa-table"></i><a>运动数据分析</a></li>
						</ol>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-4">
						<h5>
							<font style="color: #555555; font-weight: bold; font-size: 20px;">点击查看要显示的数据</font>
						</h5>
					</div>
				</div>
				<br/>
				<div class="form-horizontal">

					<a href=javascript:void(0) onclick="show(0)"><input
						type="button" value="跑 步" class="btn btn_show0" /></a>

					<a href=javascript:void(0) onclick="show(1)"><input
						type="button" value="计 步" class="btn btn_show1" /></a>
						
					<a href=javascript:void(0) onclick="show(2)"><input
						type="button" value="仰卧起坐" class="btn btn_show2" /></a>
						
				    <a href=javascript:void(0) onclick="show(3)"><input
						type="button" value="俯卧撑" class="btn btn_show3" /></a>
						
				    <a href=javascript:void(0) onclick="show(4)"><input
						type="button" value="跳 绳" class="btn btn_show4" /></a>
					
					<a href=javascript:void(0) onclick="show(5)"><input
						type="button" value="骑自行车" class="btn btn_show5" /></a>
				</div>
				<br/>
				<div class="row" id="run">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table_show" border="1"
								bordercolor="#990000">
								<thead>
									<tr>
										<th>序号</th>
										<th>用户名</th>
										<th>运动次数</th>
										<th>&nbsp;运动距离（米）</th>
										<th>运动时间</th>
										<th>消耗卡路里</th>
									</tr>
								</thead>
								<tbody>
									<%
										int r = 1;
									%>
									<c:if test="${!empty sportlist1}">
										<c:forEach items="${sportlist1}" var="sports">
											<tr>
												<%-- <td>${sports.userId }</td> --%>
												<td><%=r++%></td>
												<td>${sports.userName }</td>
												<td>${sports.sportCount}</td>
												<td>${sports.sportDis}</td>
												<td>${sports.sportTime}</td>
												<td>${sports.sportCal}</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>

				</div>
				<div class="row" id="walk">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table_show" border="1"
								bordercolor="#990000">
								<thead>
									<tr>
										<th>序号</th>
										<th>用户名</th>
										<th>运动次数</th>
										<th>运动距离（米）</th>
										<th>运动步数</th>
										<th>运动时间</th>
										<th>消耗卡洛里</th>
									</tr>
								</thead>
								<tbody>
									<%
										int w = 1;
									%>
									<c:if test="${!empty sportlist2}">
										<c:forEach items="${sportlist2}" var="sports">
											<tr>
												<%-- <td>${sports.userId }</td> --%>
												<td><%=w++%></td>
												<td>${sports.userName }</td>
												<td>${sports.sportCount}</td>
												<td>${sports.sportDis}</td>
												<td>${sports.sportSteps}</td>
												<td>${sports.sportTime}</td>
												<td>${sports.sportCal}</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>

				</div>
				<div class="row" id="situp">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table_show" border="1"
								bordercolor="#990000">
								<thead>
									<tr>
										<th>序号</th>
										<th>用户名</th>
										<th>运动个数</th>
										<th>运动次数</th>
										<th>消耗卡洛里</th>
									</tr>
								</thead>
								<tbody>
									<%
										int s = 1;
									%>
									<c:if test="${!empty sportlist3}">
										<c:forEach items="${sportlist3}" var="sports">
											<tr>
												<%-- <td>${sports.userId }</td> --%>
												<td><%=s++%></td>
												<td>${sports.userName }</td>
												<td>${sports.sportNum }</td>
												<td>${sports.sportCount}</td>
												<td>${sports.sportCal}</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="row" id="pushup">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table_show" border="1"
								bordercolor="#990000">
								<thead>
									<tr>
										<th>序号</th>
										<th>用户名</th>
										<th>运动个数</th>
										<th>运动次数</th>
										<th>消耗卡洛里</th>
									</tr>
								</thead>
								<tbody>
									<%
										int p = 1;
									%>
									<c:if test="${!empty sportlist4}">
										<c:forEach items="${sportlist4}" var="sports">
											<tr>
												<%-- <td>${sports.userId }</td> --%>
												<td><%=p++%></td>
												<td>${sports.userName }</td>
												<td>${sports.sportNum }</td>
												<td>${sports.sportCount}</td>
												<td>${sports.sportCal}</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="row" id="jump">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table_show" border="1"
								bordercolor="#990000">
								<thead>
									<tr>
										<th>序号</th>
										<th>用户名</th>
										<th>运动个数</th>
										<th>运动次数</th>
										<th>消耗卡洛里</th>
									</tr>
								</thead>
								<tbody>
									<%
										int j = 1;
									%>
									<c:if test="${!empty sportlist5}">
										<c:forEach items="${sportlist5}" var="sports">
											<tr>
												<%-- 	<td>${sports.userId }</td> --%>
												<td><%=j++%></td>
												<td>${sports.userName }</td>
												<td>${sports.sportNum }</td>
												<td>${sports.sportCount}</td>
												<td>${sports.sportCal}</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="row" id="bicycle">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table_show" border="1"
								bordercolor="#990000">
								<thead>
									<tr>
										<th>序号</th>
										<th>用户名</th>
										<th>运动次数</th>
										<th>运动距离</th>
										<th>运动时间</th>
										<th>消耗卡洛里</th>
									</tr>
								</thead>
								<tbody>
									<%
										int b = 1;
									%>
									<c:if test="${!empty sportlist6}">
										<c:forEach items="${sportlist6}" var="sports">
											<tr>
												<%-- <td>${sports.userId }</td> --%>
												<td><%=b++%></td>
												<td>${sports.userName }</td>
												<td>${sports.sportCount }</td>
												<td>${sports.sportDis}</td>
												<td>${sports.sportTime}</td>
												<td>${sports.sportCal}</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- /.row -->

			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->



</body>

</html>