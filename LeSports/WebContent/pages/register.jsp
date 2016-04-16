<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basepath = request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>

<head>
<title>新用户注册</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.css" />
<link rel="stylesheet" href="<%=basepath%>/css/register.css" />
<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />

<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>


<script type="text/javascript">
	//如果用户没有登录，则不能进入乐友圈
	function remindLogin() {
		alert("请先登陆,才能进入乐友圈！");
	}

	//如果用户没有登录，则不能进入我的运动页面
	function remindLogin2() {
		alert("请先登陆,才能进入我的运动！");
	}
	
	//表单验证
	function validateForm() {
		
		var nickName = $("#nickName").val();
		var email = $("#email").val();
		var phoneNumber = $("#phoneNumber").val();
		var password = $("#password").val();
		var confirmPassword = $("#confirmPassword").val();
		
		if(nickName == "")
		{
			alert("昵称不能为空");
			$("#nickName").focus();
			return false;				
		}			
		
		if(password == "")
		{
			alert("密码不能为空");
			$("#password").focus();
			return false;				
		}	
		if(password.length<6)
		{
			alert("密码长度不能少于六位");
			return false;		
		}	
		if(confirmPassword == "")
		{
			alert("确认密码不能为空");
			$("#confirmPassword").focus();
			return false;				
		}
		if(confirmPassword.length<6)
		{
			alert("确认密码长度不能少于六位");
			return false;		
		}	
		if(confirmPassword != password)
		{
			alert("两次密码输入不一致");
			$("#password").val("");
			$("#confirmPassword").val("");				
			$("#password").focus();				
			return false;				
		}		
		if(email =="" && phoneNumber == "" )
		{
			alert("邮箱和手机号必须填一个");
			$("#email").focus();
			return false;
		}
		
		var reg2 = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;//邮箱验证				
		
		if(email != "" & !reg2.test(email))
		{
			alert("邮箱格式不正确");
			$("#email").val("");
			$("#email").focus();
			return false;		
		}	
		
		var reg1 = /^(1\d{10})$/;//手机验证		
		
		if(phoneNumber != "" & !reg1.test(phoneNumber))
		{
			alert("手机号格式不正确");
			$("#phoneNumber").val("");
			$("#phoneNumber").focus();
			return false;	
		}		
		
		$.post('../webUserInfo/register', 
				   {"nickName":nickName,"email":email, "phoneNumber":phoneNumber, "password":password },
	               function (result) {
					   					   
						var resCode = result.resCode;
						
						if(resCode == 1)//注册成功，刷新页面
						{
							alert("注册失败，邮箱已被注册");	
							$("#email").val("");
							$("#email").focus();
						}
						else if(resCode == 2)
						{							
							alert("注册失败，手机已被注册");	
							$("#phoneNumber").val("");
							$("#phoneNumber").focus();
						}							
						else
						{
							alert("注册成功，请登录");			
							window.location = "<%=basepath%>pages/login.jsp";
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
							<li><a id="login" href="login.jsp" class="navbar-link">登录</a></li>
							<li><a 	class="navbar-link"></a></li>
						</c:otherwise>
					</c:choose>

				</ul>

			</div>

		</div>
	</nav>


	<div class="container-fluid register-bg">
		<div class="col-md-7 col-sm-5"></div>
		<div class="col-md-5 col-sm-7 register-section">
			<h3>新用户注册</h3>
			<div class="form-group">
				<div>
					<input type="text" class="form-control" name="nickName" id="nickName"
						placeholder="昵称"  />
				</div>
			</div>

			<div class="form-group">
				<div>
					<input type="password" class="form-control" name="password" id="password"
						placeholder="请输入登录密码" />
				</div>
			</div>

			<div class="form-group">
				<div>
					<input type="password" class="form-control" name="confirmPassword" id="confirmPassword"
						placeholder="确认登录密码" />
				</div>
			</div>

			<div class="form-group">
				<div>
					<input type="text" class="form-control" name="email" id="email"
						placeholder="邮箱(和手机号必填一个)" />
				</div>
			</div>

			<div class="form-group">
				<div>
					<input type="text" class="form-control" name="phoneNumber" id ="phoneNumber"
						placeholder="手机号(和邮箱必填一个)" />
				</div>
			</div>

			<div class="form-group">
				<div>
					<input type="button" class="btn btn-primary btn-block btn-submit"
						onclick="validateForm()" value="注  册" data-count="60" />
				</div>
			</div>
			
		</div>
	</div>
</body>

</html>