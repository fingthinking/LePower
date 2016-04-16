<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%
	String basepath = request.getContextPath() + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>乐动力后台管理</title>

<link href="<%=basepath%>/manacss/bootstrap.min.css" rel="stylesheet">

<link href="<%=basepath%>/manacss/admin.css" rel="stylesheet">

<link href="<%=basepath%>/manacss/plugins/morris.css" rel="stylesheet">

<link href="<%=basepath%>/manacss/font-awesome.min.css" rel="stylesheet"
	type="text/css">
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
function del(managerId){
	//alert(managerId);
	if(!window.confirm('你确定要删除吗？')){
        return;
     }
	$.post("<%=basepath%>manager/deleteManager?managerId="+managerId, function(data) {
			if ("success" == data) {
				alert("删除成功");
				window.location.reload();
			} else {
				alert("删除失败");
			}
		});
}

function updateManager(i){
	<%-- var form = document.forms[0];
	form.action = "<%=basepath%>manager/updateManager";
	form.method="post";
	form.submit(); --%>

	var manaId=document.getElementById("manaId"+i).value;
	var manaName=document.getElementById("manaName"+i).value;
	var manaPwd=document.getElementById("manaPwd"+i).value;
	
   	$.ajax({
   		url: "<%=basepath%>manager/updateManager",
   		data:{managerId:manaId,managerName:manaName,managerPwd:manaPwd},
   		type: "POST",
   		success:function(data){
   			alert("更新成功");
   	   	},
   		error:function(er){
   			alert("更新失败");
   		}
   		});
}

</script>
<title>资讯管理</title>
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
							<li><i class="fa fa-dashboard"></i> <a
								href="../managePages/index.jsp">主页</a></li>
							<li class="active"><i class="fa fa-table"></i><a
								href="getAllManager">管理员信息管理</a></li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-12">
						<h2>管理员列表</h2>
						<br>
						<div style="margin: 10px 5px;">
							<a href="<%=basepath%>managePages/table_manager_add.jsp"> <input
								type="button" value="新 增" class="btn btn-primary btn-warning " /></a>
						</div>
						<form action="" class="form-horizontal">
							<div class="table-responsive">
								<table class="table table-bordered table-hover"
									id="table_manager">
									<thead>
										<tr>
											<th>序号</th>
											<th hidden="true">ID</th>
											<th>用户名</th>
											<th>密码</th>
											<th>权限</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<%int i = 1;%>
										<c:forEach items="${list}" var="list">
											<form action="" class="form-horizontal">
											<tr>
												<td><%=i++%></td>
												<td hidden="true"><input type="text" name="managerId" id="manaId<%=i%>"
													value="${list.managerId}" /></td>
												<td><input type="text" name="managerName" id="manaName<%=i%>"
													value="${list.managerName}" class="form-control" /></td>
												<td><input type="text" name="managerPwd" id="manaPwd<%=i%>"
													value="${list.managerPwd}" class="form-control" /></td>
												<td>${list.authority}</td>
												<td>
													<button type="button" value="更新" class="btn btn_update"
														onclick="updateManager(<%=i%>)">更新</button>
													<button type="button" value="删除" class="btn btn_del"
														onclick="del('${list.managerId}')">删除</button>
												</td>
											</tr>
											</form>
											
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
<script type="text/javascript">
<%-- 
$(function(){
	$('button').click(function(){
		var a=this.parentNode.parentNode;
		
		var b=a.cells[1].firstChild.value;
		var c=a.cells[2].firstChild.value;
		var d=a.cells[3].firstChild.value;
		var ids=a.cells[0].innerText;
	$.post(
			"<%=basepath%>manager/updateManager", {
				b : b,
				c : c,
				ids : ids
			}, function(data) {
				alert("修改成功");
			});

		});

	}); --%>
</script>
</html>