<%@page import="com.lesport.model.News"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%
	String basepath = request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html >

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="<%=basepath%>pages/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>pages/css/font-awesome-min.css" />
<script type="text/javascript" src="<%=basepath%>pages/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basepath%>pages/js/bootstrap.min.js"></script>
<title>编辑用户</title>

<!-- Bootstrap Core CSS -->
<link href="<%=basepath%>/manacss/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="<%=basepath%>/manacss/admin.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="<%=basepath%>/manacss/font-awesome.min.css" rel="stylesheet"
	type="text/css">

<script type="text/javascript">	
	function opearateInfo(status,picUrl,startTime,endTime)
	{
		//alert(startTime);
		var sTime=document.getElementById("startTime").value;
		var eTime=document.getElementById("endTime").value;
		
		if(sTime!=""&&eTime!=""&&(sTime>eTime))
		{
		alert("开始时间不能大于结束时间！");	return;
		}	
		if(sTime!=""&&eTime==""&&sTime>endTime)
		{
			alert("开始时间不能大于结束时间");return;
		}
		if(sTime==""&&eTime!=""&&eTime<startTime)
		{
			alert("开始时间不能大于结束时间");return;
		}
			//||eTime<startTime
		if(status==0)
			{
			
				var form = document.forms[0];
				if((document.getElementById("startTime").value=="")||(document.getElementById("endTime").value=="")||document.getElementById("startTime").value>document.getElementById("endTime").value)
				{
					alert("请输入时间,并且开始时间不能大于结束时间");
					
				}
				else
					{
						form.action = "<%=basepath%>system/addNews";
						form.method="post";
						form.submit();
					}
			}else
				{		
					if(document.getElementById("pirUrl").value=="")
						{
							var form = document.forms[0];
							form.action = "<%=basepath%>system/updateNews?picUrl="+picUrl+"&state=1"+"&stTime="+startTime+"&enTime="+endTime+"&sTime="+sTime+"&eTime="+eTime;
							form.method="post";
							form.submit();
						}else
							{
								var form = document.forms[0];
								form.action = "<%=basepath%>system/updateNews?state=2"+"&stTime="+startTime+"&enTime="+endTime+"&sTime="+sTime+"&eTime="+eTime;
								form.method="post";
								form.submit();
							}
				}
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
					<div class="col-lg-12 ">
						<h1 class="page-header">编辑资讯</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a
								href="<%=basepath%>managePages/index.jsp">主页</a></li>
							<li><i class="fa fa-table"></i><a
								href="<%=basepath%>system/getAllNews">消息资讯</a></li>
							<li class="active"><i class="fa fa-table"></i><a
								href="<%=basepath%>system/getInfo">消息资讯</a></li>
						</ol>
					</div>

					<div class="col-lg-12">
						<form action="" name="newsForm" method="post" enctype="multipart/form-data" id="form-news">
							<div class="container-fluid news-section">
								<input type="button" value="编辑"
									onclick="opearateInfo('${status}','${news.picUrl}','${news.startTime }','${news.endTime }')"
									class="btn btn-primary btn-warning" /> <br /> <br />
								<div class="form-horizontal">
									标题 &nbsp;&nbsp;&nbsp;<input type="text" name="title"
										value="${news.title }" class="form-control"
										style="width: 80%; display: inline;" />
								</div>
								<br />
								<div class="form-horizontal">
									图片&nbsp;&nbsp;&nbsp;<input type="file" name="pirUrl" id="pirUrl"
										style="display: inline;" />
								</div>
								<br />
								<div class="form-horizontal" hidden="true">
									资讯Id&nbsp;<input type="text" name="newsId"
										value="${news.newsId }" readonly class="form-control"
										style="width: 80%; display: inline;" />
								</div>
								<br />
								<div class="form-horizontal">
									开始时间&nbsp;<input type="date" name="startTime" class="form-control"
									id="startTime" value="${news.startTime}" placeholder="开始时间"
										style="width: 20%; display: inline;" />
									&nbsp;&nbsp;结束时间&nbsp; <input type="date" name="endTime" id="endTime"
										value="${news.endTime}" class="form-control" 
										style="width: 20%; display: inline;" />&nbsp;&nbsp;
										<%-- 创建时间&nbsp;--%>
									<input type="text" name="createdDate" 
										value="${news.createdDate }" class="form-control"
										style="width: 20%; display: inline;visibility:hidden" /> 
								</div>
								<br/>
								<div>
									<label class="control-label">内容</label>
									<textarea name="content" value="${news.content }" rows="20" class="form-control">${news.content }</textarea>
								</div>
							</div>
							</form>
					</div>
				</div>
			</div>
		</div>
		<!-- /.row -->

		<%-- 	<form action="" name="newsForm" method="post"
					enctype="multipart/form-data" id="form-news">
					<div class="container-fluid news-section">
						<div class="col-lg-12">
							<div class="form-horizontal">
								标题 <input type="text" name="title" value="${news.title }"
									class="form-control" style="width: 80%; display: inline;" /> <input
									type="button" value="编辑" onclick="opearateInfo(${status})"
									class="btn btn-primary btn-warning" />
							</div>

						</div>
						<div class="row">
							<div class="col-lg-8 col-md-8 col-xs-12">
								<div class="hidden-lg hidden-md">
									<!-- 响应式，缩小时做一个图片导入 -->
								</div>
							</div>
							<div class="col-lg-4 col-md-4 col-xs-12">
								<div class="hidden-xs"></div>
							</div>
							<div class="col-lg-8 col-md-8 col-xs-12"></div>
						</div>
					</div> --%>








		<%-- <div class="form-group">
				<span class="input-group-addon">资讯Id</span>
				<input type="text" name="newsId" value="${news.newsId }" readonly class="form-control" />
			</div>
			<div class="form-group">
				<span class="input-group-addon">标题</span>
				<input type="text" name="title" value="${news.title }" class="form-control"/>
			</div>
			<div class="form-group">
			<span class="input-group-addon">图片</span>
				<input type="file" name="pirUrl" id="pirUrl" value="${news.picUrl}" class="form-control"/>
			</div>
			<div class="form-group">
			<span class="input-group-addon">内容</span>
				<input type="text" name="content" id="content" value="${news.content }"class="form-control" />
			</div>
			<div class="form-group">
			<span class="input-group-addon">开始时间</span>
				<input type="date" name="startTime" id="startTime" defaultValue="${news.startTime }"class="form-control" />
			</div>
			<div class="form-group">
			<span class="input-group-addon">结束时间</span>
				<input type="date" name="endTime" id="endTime" value="${news.endTime }" class="form-control"/> </div>
			<div class="form-group">
			<span class="input-group-addon">创建时间</span>
				<input type="text" name="createdDate" value="${news.createdDate }" readonly class="form-control" />
				<input type="button" value="编辑" onclick="opearateInfo('${status}','${news.picUrl}')"class="form-control" />
			</div> --%>
<%-- ${news.picUrl} --%>
		<!-- 	</form> -->

	</div>
	<!-- /.container-fluid -->

	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="<%=basepath%>managePages/js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="<%=basepath%>js/bootstrap.min.js"></script>

</body>

</html>