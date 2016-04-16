<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%
	String basepath = request.getContextPath() + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<script type="text/javascript" src="js/jquery.js"></script>
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
<script src="../managePages/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../managePages/js/bootstrap.min.js"></script>

<!-- Morris Charts JavaScript -->
<script src="js/plugins/morris/raphael.min.js"></script>
<script src="js/plugins/morris/morris.min.js"></script>
<script src="js/plugins/morris/morris-data.js"></script>

<style type="text/css">
.td {
	width: 200px;
}
</style>
<script type="text/javascript">
	function del(newsId) {
		//alert(newsId);
		$.post("<%=basepath%>system/deleteNews?newsId=" + newsId,
				function(data) {
					//alert("你好");
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
						<h1 class="page-header">消息资讯</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a
								href="../managePages/index.jsp">主页</a></li>
							<li class="active"><i class="fa fa-table"></i><a
								href="../system/getAllNews">消息资讯</a></li>
						</ol>
					</div>
				</div>
				<!-- /.row -->
				<div class="row">
					<div class="col-lg-12">
						<div class="row">
							<h2>消息资讯列表</h2>
<%-- 							<form action="<%=basepath%>system/getNews">
								开始时间:<input type="date" name="startTime"
									defaultValue ="2016-01-01 00:00:00" /> 结束时间:<input type="date"
									name="endTime" defaultValue="2016-01-01 00:00:00" /> 
									<input type="submit" value="提交" />
							</form> --%>
							<br />
							<div class="table-responsive" style="margin: 10px 5px;">
								<form action="<%=basepath%>system/getNews" method="post"
									class="form-horizontal">

									开始时间<input type="date" name="startTime"
										value="" class="form-control"
										style="width: 16%; display: inline" />
									&nbsp;&nbsp;&nbsp;&nbsp;结束时间<input type="date" name="endTime"
										value="" class="form-control"
										style="width: 16%; display: inline" /> <input type="submit"
										value="查询" class="btn btn-primary btn-warning" /> <br />
								</form>
							</div>
						</div>
						<button type="button"
							onclick="javascript:window.open('<%=basepath%>/system/getInfo')"
							class="btn btn-primary" style="right: 0px;">插入文章</button>
						<div>
							<table class="table table-bordered table-hover" border="1"
								bordercolor="#990000" id="table_course">
								<thead>
									<tr>
										<th style="width:35px">序号</th>
										<th>标题</th>
										<th>图片</th>
										<th>内容</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>创建时间</th>
									<!-- 	<th>是否有效</th> -->
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty newsList}">
										<c:forEach items="${newsList}" var="news" varStatus="index">
											<tr>
												<%-- <td>${news.newsId }</td> --%>
												<td>${index.count}</td>
												<!-- 	<td>(count++)</td> -->
												<td>${news.title }</td>

												<td><img src="<%=basepath%>${news.picUrl}"
													width="100px" height="100px"></img></td>
												<td class="format_content">${news.content}...</td>

												<td>${news.startTime}</td>
												<td>${news.endTime}</td>
												<td>${news.createdDate}</td>
											<%-- 	<td>${news.deleteFlag}</td> --%>
												<td>
													<button type="button" class="btn btn_update"
														onclick="javascript:window.open('<%=basepath%>/system/getInfo?newsId=${news.newsId}')">更新</button>
													<button type="button" onclick="del('${news.newsId}')"
														class="btn btn_del">删除</button>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
							<div class="ds-paginator">
								<div class=" ds-border"></div>
								<!-- 上一页标签 -->
								<c:url var="prePage" value="/system/prePage">
									<c:param name="pageNow" value="${pageNow }"></c:param>
									<c:param name="totalPage" value="${totalPage }"></c:param>
								</c:url>

								<!-- 下一页标签 -->
								<c:url var="nextPage" value="/system/nextPage">
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