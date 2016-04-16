<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%
	String basepath = request.getContextPath() + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>资讯管理</title>

<!-- Bootstrap Core CSS -->
<link href="<%=basepath%>/manacss/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="<%=basepath%>/manacss/admin.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="<%=basepath%>/manacss/font-awesome.min.css" rel="stylesheet"
	type="text/css">

<!--font  -->
<link href="<%=basepath%>/css/font-awesome-min.css" rel="stylesheet">

<!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<!-- Morris Charts JavaScript -->
<script src="js/plugins/morris/raphael.min.js"></script>
<script src="js/plugins/morris/morris.min.js"></script>
<script src="js/plugins/morris/morris-data.js"></script>

<script type="text/javascript">	

function addManager(){
	var managerName=document.getElementById("managerName").value;
	var managerPwd=document.getElementById("managerPwd").value;
	if(managerPwd==""||managerName=="")
	{
		alert("用户名或密码不能为空!");return;
	}
	var form = document.forms[0];
	form.action = "<%=basepath%>manager/addManager";
	form.method="post";
	form.submit();
}
</script>

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation"
			id="nav-section"> <!-- Brand and toggle get grouped for better mobile display -->
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
		<!-- /.navbar-collapse --> </nav>


		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">管理员</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="../managePages/index.jsp">主页</a>
							</li>
							<li><i class="fa fa-table"></i><a href="<%=basepath%>manager/getAllManager">管理员信息管理</a></li>
							<li class="active"><i class="fa fa-table"></i><a
								href="table_manager_add.jsp">新增管理员</a></li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-12">
						<h2>新增管理员</h2>
						<br/>
						<div class="table-responsive" style="margin:10px 5px;">
							<form action="" class="form-horizontal">
								用户名 <input type="text" name="managerName" id="managerName" class="form-control"
									style="width: 25%; display: inline">
								 密码<input type="text" name="managerPwd" id="managerPwd" class="form-control"
									style="width: 25%; display: inline"> 
								<!-- 权限 <input type="text" name="authority" id="c" class="form-control"
									style="width: 25%; display: inline">  -->
								<br/>
								<input type="submit" class="btn btn-primary btn-warning" value="提交" onclick="addManager()" style="margin:10px 0px;" >
							</form>
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