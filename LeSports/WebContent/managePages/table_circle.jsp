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

<title>动态管理</title>


<link href="<%=basepath%>/manacss/bootstrap.min.css" rel="stylesheet">


<link href="<%=basepath%>/manacss/admin.css" rel="stylesheet">


<link href="<%=basepath%>/manacss/font-awesome.min.css" rel="stylesheet"
	type="text/css">

<!-- Custom Fonts -->
<link href="<%=basepath%>/manacss/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<!-- jQuery -->
<script src="../managePages/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../managePages/js/bootstrap.min.js"></script>

<!-- Morris Charts JavaScript -->
<script src="js/plugins/morris/raphael.min.js"></script>
<script src="js/plugins/morris/morris.min.js"></script>
<script src="js/plugins/morris/morris-data.js"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<script type="text/javascript">	
function del(circleId){
	//alert(circleId);
	if(!window.confirm('你确定要删除吗？')){
        return;
     }
	$.post("<%=basepath%>webcirManage/deleteCircle?circleId="+circleId, function(data) {
			if ("success" == data) {
				alert("删除成功");
				window.location.reload();
			} else {
				alert("删除失败");
			}
		});
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
						<h1 class="page-header">乐友圈</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a
								href="../managePages/index.jsp">主页</a></li>
							<li class="active"><i class="fa fa-table"></i><a
								href="<%=basepath%>managePages/table_circle.jsp">乐友圈管理</a></li>
						</ol>
					</div>
				</div>
				<!-- /.row -->
				<div class="row">
					<div class="col-lg-13">
						<h2>动态信息</h2>
						<br/>
						<form action="../webcirManage/getAllCircle" method="post">
							<div class="input-group" style="width: 25%">
								<span class="input-group-addon">昵称</span> <input type="text"
									name="nickName" class="form-control" /><span
									class="input-group-btn"><input type="submit" value="查询"
									class="btn btn-primary btn-warning" /></span>
							</div>
						</form>
						<br/>			
	
						<div class="table-responsive">
							<table class="table table-bordered table-hover" id="table_circle">
								<colgroup>

								</colgroup>
								<thead>
									<tr>
										<th class="th_min">序号</th>
										<th class="th_min">用户昵称</th>
										<th class="th_max">动态内容</th>
										<th class="th_min">发表时间</th>
										<th class="th_min">发表位置</th>
										<th class="th_min">操作</th>
									</tr>
								</thead>
								<tbody>
									<%int i=1; %>
									<c:forEach items="${circles }" var="circle">
										<tr>
											<th class="th_min"><%=i++ %></th>
											<th class="th_min">${circle.nickName }</th>
											<th class="th_max">${circle.content }</th>
											<th class="th_min">${circle.publishDate }</th>
											<th class="th_min">${circle.publishAddr }</th>
											<th class="th_min"> <button type="button" onclick="del('${circle.circleId}')"
														class="btn btn_del">删除</button></th>
										</tr>
									</c:forEach>

								</tbody>
							</table>
							<div class="ds-paginator">
								<div class=" ds-border"></div>
								<!-- 上一页标签 -->
								<c:url var="prePage" value="/webcirManage/prePage">
									<c:param name="pageNow" value="${pageNow }"></c:param>
									<c:param name="totalPage" value="${totalPage }"></c:param>
								</c:url>

								<!-- 下一页标签 -->
								<c:url var="nextPage" value="/webcirManage/nextPage">
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
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

</body>

</html>