<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basepath = request.getContextPath() + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>登录</title>
<link rel="stylesheet" href="<%=basepath%>pages/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>pages/css/font-awesome-min.css" />
<link rel="stylesheet" href="<%=basepath%>pages/css/login.css" />

<script type="text/javascript" src="<%=basepath%>pages/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basepath%>pages/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basepath%>pages/js/bootstrapValidator.js"></script>

<script>	
 
 	var num1;
 	var num2; 
 
 	//生成随机数，作为验证码
 	$(document).ready(function(){ 		
 		function randomNumber(min, max) {
			return Math.floor(Math.random() * (max - min + 1)
					+ min);
		}
		
 		num1 = randomNumber(1, 50);
 		num2 = randomNumber(1, 50); 		
		$('#safeImg').html(	[ num1, '+',num2, '=' ].join(' '));
 		
 	});
			
			
	//如果用户没有登录，则不能进入乐友圈
	function remindLogin() {
		alert("请先登陆,才能进入乐友圈！");
	}

	//如果用户没有登录，则不能进入我的运动页面
	function remindLogin2() {
		alert("请先登陆,才能进入我的运动！");
	}
	
	//验证登陆
	function validateLogin()
	{		
		var correctSum = num1 + num2;//验证码正确的和
		
		var loginName = $("#loginName").val();
		var loginPwd = $("#loginPwd").val();
		var sum = $("#safePwd").val();//输入的验证码
		
		var reg1 = /^(1\d{10})$/;//手机验证
		var reg2 = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;//邮箱验证		
		
		if(loginName == "")
		{
			alert("登录名不能为空");
			$("#loginName").focus();
			return false;
		}
		if((!reg1.test(loginName)) && (!reg2.test(loginName)))
		{
			alert("请输入正确格式的邮箱或者手机号!");
			$("#loginName").val("");
			$("#loginName").focus();
			return false;
		}
		
		if(loginPwd == "")
		{
			alert("密码不能为空");
			$("#loginPwd").focus();
			return false;
		}
		
		if(loginPwd.length < 6)
		{
			alert("密码不能少于六位");
			$("#loginPwd").focus();
			return false;
		}
		
		
		if(sum == "")
		{
			alert("验证码不能为空");
			$("#safePwd").focus();
			return false;
		}
		
		if(sum != correctSum)
		{
			alert("验证码错误!");
			$("#safePwd").val("");
			$("#safePwd").focus()
			return false;
		}
		
		$.post(
			"../webUserInfo/login",
			{"loginName":loginName, "loginPwd":loginPwd},
			function(result)
			{
				resCode = result.resCode;
				
				if(resCode == 0)//登陆成功
				{
					window.location = "<%=basepath%>pages/index.jsp";
				}
				else
				{
					alert("登陆失败，用户名和密码不匹配!");
					$("#loginName").val("");
					$("#loginPwd").val("");
					$("#loginName").focus();				
				}				
					
			},
			"json"		
		);		
	}	
	
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
	
			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="<%=basepath%>pages/index.jsp">首页</a></li>
	
					<!-- userId为空时，说明用户未登录 -->
					<c:choose>
						<c:when test="${ not empty sessionScope.userId}">
							<li><a href="<%=basepath%>websport/sportChart">我的运动</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="#" onclick="remindLogin2()">我的运动</a></li>
						</c:otherwise>
					</c:choose>
	
					<li><a href="<%=basepath%>weblecourse/showNews">Le课堂</a></li>
	
					<!-- userId为空时，说明用户未登录 -->
					<c:choose>
						<c:when test="${ not empty sessionScope.userId}">
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
						<c:when test="${ not empty sessionScope.userName}">
							<li><a id="login" href="#" class="navbar-link">${sessionScope.userName}</a></li>
						</c:when>
	
						<c:otherwise>
							
							<li><a href="<%=basepath%>pages/register.jsp"
								class="navbar-link">注册</a></li>
							<li><a id="login" class="navbar-link"></a></li>
						</c:otherwise>
					</c:choose>
				</ul>	
			</div>
	
		</div>
	</nav>

	<div class="container-fluid login-bg">
		<div class="col-md-7 col-sm-5"></div>
		<div class="col-md-5 col-sm-7 login-section">
			<h3>欢迎登录乐动力</h3>	
			<div class="form-group">
				<div>
					<input type="text" id="loginName" name="loginName" class="form-control"	placeholder="手机号/邮箱" />
				</div>					
			</div>

			<div class="form-group">
				<div>
					<input type="password" id="loginPwd" name="loginPwd" class="form-control" placeholder="密码" />						
				</div>					
			</div>

			<h4>请将如下计算结果填入文本框内</h4>
			<div class="form-group form-horizontal">
				<div class="input-group">
					<span class="input-group-addon"> <label id="safeImg"
						class="form-img"></label>
					</span> <input type="text" id="safePwd" name="safePwd" class="form-control"	placeholder="验证码" />
				</div>
			</div>

			<div class="form-group">
				<input type="button" onclick="validateLogin()" class="btn btn-primary btn-block btn-block"	value="登   录" />
			</div>
			<div>
				<div class="col-md-9"></div>
				<div class="col-md-3">
					<a href="<%=basepath%>pages/forgetPassword.jsp"
						style="color: red; font-size: 13px; text-align: right;">忘记密码</a>
				</div>
			</div>			
		</div>
	</div>
		
</body>

</html>