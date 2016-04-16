<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basepath = request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>

<head>
	<title>修改密码</title>
	<meta charset="utf-8" />
	<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.css" />
	<link rel="stylesheet" href="<%=basepath%>/css/register.css" />
	<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />
	<link rel="stylesheet" href="<%=basepath%>/css/sport.css" />
	
	<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/bootstrapValidator.js"></script>

	<script type="text/javascript">
		
		//修改密码提交处理
		function submitNewPwd()
		{
			var oldPassword = $("#oldPassword").val();
			var txt_password = $("#txt_password").val();
			var txt_repassword = $("#txt_repassword").val();
			
			//输入验证
			if(oldPassword == "")
			{
				alert("原密码不能为空!");	
				$("#oldPassword").focus();
				return false;
			}
			
			if(oldPassword.length < 6)
			{
				alert("原密码不能少于六位!");	
				$("#oldPassword").focus();
				return false;
			}
			
			if(txt_password == "")
			{
				alert("新密码不能为空!");
				$("#txt_password").focus();
				return false;
			}
			
			if(txt_password.length < 6 )
			{
				alert("新密码不能少于六位!");
				$("#txt_password").focus();
				return false;
			}
			
			if(txt_repassword == "")
			{
				alert("确认密码不能为空!");
				$("#txt_repassword").focus();
				return false;
			}
			
			
			if(txt_repassword.length < 6)
			{
				alert("确认密码不能少于六位!");
				$("#txt_repassword").focus();
				return false;
			}
			
			
			
			if(txt_password != txt_repassword)
			{
				alert("两次输入的密码不一致!");
				$("#txt_password").val("");
				$("#txt_repassword").val("");
				$("#txt_password").focus();
				return false;
			}			
			

			$.post('../webUserInfo/updatePassword', 
				   {"oldPassword":oldPassword,"newPassword":txt_password},
	               function (result) {
					   
						var resCode = result.resCode;
						
						if(resCode == 0)//回复成功，刷新页面
						{
							alert("密码修改成功，进入登录页面!");
							window.location = "<%=basepath%>pages/login.jsp";
						}
						else//回复失败，给出提示
						{
							alert("不好意思，修改失败哦!");
							$("#oldPassword").val("");
							$("#txt_password").val("");
							$("#txt_repassword").val("");
							$("#oldPassword").focus();
						}
	               },
	               "json"
	        );		
		}
		
		//---------------------------------------------------------------//
		
		
		
		//如果用户没有登录，则不能进入乐友圈
		function remindLogin()
		{
			alert("请先登陆,才能进入乐友圈！");
		}
		
		//如果用户没有登录，则不能进入我的运动页面
		function remindLogin2()
		{
			alert("请先登陆,才能进入我的运动！");
		}
		
		
		
	</script>

</head>

<body>
	
	<nav class="nav">
		<div class="burger">
			<div class="burger__patty"></div>
		</div>
		<ul class="nav__list">
			<li class="nav_item"><a href="<%=basepath%>pages/index.jsp">首页</a></li>
			<!-- userId为空时，说明用户未登录 -->		
			<c:choose>											
				<c:when test="${ not empty sessionScope.loginUserInfo.userId}" > 						
					<li class="nav_item"><a href="<%=basepath%>websport/sportChart">我的运动</a></li>						
				</c:when>
				<c:otherwise>								
					<li class="nav_item"><a href="#" onclick="remindLogin2()">我的运动</a></li>
				</c:otherwise> 						
			</c:choose>		
			<li class="nav_item"><a href="<%=basepath%>weblecourse/showNews">Le课堂</a></li>
			<!-- 未登录时不允许进入乐友圈 -->
			<c:choose>
				<c:when test="${ not empty sessionScope.loginUserInfo.userId}">
					<li class="nav_item"><a href="<%=basepath%>webcircle/comeIntoCircle">乐友圈</a></li>
				</c:when>
				<c:otherwise>
					<li class="nav_item"><a href="#" onclick="remindLogin()">乐友圈</a></li>
				</c:otherwise>
			</c:choose>

			<!-- 显示下载链接的二维码图片 -->
			<li class="nav_item"><a href="<%=basepath%>pages/popular.jsp">手机应用</a></li>

		</ul>
	</nav>
	
	<div  id="register">
		<div class="container-fluid">
			<div class="col-md-7 "></div>
			<div class="col-md-5 register-section">

				<h3 class="center-block">修改密码</h3>
			
			
				<form name="register" id="register" action="../webUserInfo/updatePassword" method="post">

					<div class="form-group form-horizontal ">
						<div class="input-group ">
							<span class="input-group-addon ">当前密码</span> 
								<input id="oldPassword" name="oldPassword" type="password" onclick="removeTips()" class="form-control " /> 
								<span class="input-group-btn "></span>
						</div>
					</div>
									
					<div id="pwd1" class="form-group" >
						<div class="input-group ">
							<span class="input-group-addon ">新密码</span>
							 <input	id="txt_password" name="tx_password" type="password" class="form-control " />
						</div>
					</div>
					
					<div id="pwd2" class="form-group">
						<div class="input-group ">
							<span class="input-group-addon ">确认密码</span>
							<input id="txt_repassword" name="txt_repassword" type="password" class="form-control " />
						</div>
					</div>

					<div id="submit1" class="form-group ">
						<input class="btn btn-primary btn-block" type="button"	value="提交 " onclick="submitNewPwd()">
					</div>
					
				</form>
			</div>
		</div>
	</div>

</body>

<script type="text/javascript">

	//导航栏
	var menu = document.querySelector(".nav__list");
	var burger = document.querySelector(".burger");
	var openMenu = function() {
		burger.classList.toggle('burger--active');
		menu.classList.toggle('nav__list--active');
	};
	burger.addEventListener('click', openMenu, false);

</script>

</html>
 