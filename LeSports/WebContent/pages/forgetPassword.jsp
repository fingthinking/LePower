<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basepath = request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>

<head>
	<title>忘记密码</title>
	<meta charset="utf-8" />
	<link rel="stylesheet" href="<%=basepath%>/css/bootstrap.css" />
	<link rel="stylesheet" href="<%=basepath%>/css/register.css" />
	<link rel="stylesheet" href="<%=basepath%>/css/font-awesome-min.css" />
	<link rel="stylesheet" href="<%=basepath%>/css/sport.css" />
	
	<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/bootstrapValidator.js"></script>

	<script type="text/javascript">
		function sendCode() {
			var phoneOrEmailIn = $("#phoneOrEmailIn").val();
	
			$.post('../webUserInfo/validatePhoneOrEmail', {
				"phoneOrEmial" : phoneOrEmailIn
			}, function(result) {
				var resCode = result.resCode;
	
				if (resCode == 0)//手机号码或者邮箱号码存在
				{
					alert("这是返回存在");
	
					//如果这两个输入框原来已经存在，则先删除在显示
					$("#pwd1").css("display", "block");
					$("#pwd2").css("display", "block");
					$("#submit1").css("display", "block");
	
				} else//手机号码或者邮箱号码不存在
				{
					alert(result.resMsg);
					$("#phoneOrEmailIn").val("");
				}
			}, "json");
	
		}
		
		//---------------------------------------------------------------------//
		//修改密码提交处理
		function submitNewPwd()
		{
			var phoneOrEmail = $("#phoneOrEmailIn").val();
			var vCode = $("#vCode").val();
			var password = $("#txt_password").val();
			var repassword = $("#txt_repassword").val();
			
			if(phoneOrEmail == "")
			{
				alert("请输入邮箱或者手机号");
				return false;
			}
			if(vCode == "")
			{
				alert("请输入验证码");
				return false;
			}
			if(password == "")
			{
				alert("请输入新密码");
				return false;
			}
			if(repassword == "")
			{
				alert("请确认新密码");
				return false;
			}
			if(password != repassword)
			{
				alert("两次输入密码不一致");
			}
			
			 $("form").submit();
			
		}
		
		//---------------------------------------------------------------//
		//当点击邮箱或者手机号的输入框时，移除提示信息（主要用于重置失败时）
		function removeTips()
		{
			//移除提示重置密码失败或者验证码错误的提示信息
			$("#tipPassword").remove();
			$("#tipCode").remove();
		}
		
		
		
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

				<h3 class="center-block">找回密码</h3>
				<c:if test="${flag == 1 }">
					<span id="tipPassword" style="color:red; dispaly:none">密码找回失败，请重新找回</span>
				</c:if>
			
				<form name="register" id="register" action="../webUserInfo/resetPwdWhenForget" method="post">

					<div class="form-group form-horizontal ">
						<div class="input-group ">
							<span class="input-group-addon ">邮箱/手机</span> 
								<input id="phoneOrEmailIn" name="phoneOrEmailIn" type="text" onclick="removeTips()" class="form-control " /> <span class="input-group-btn ">
								<input type="button" value="获取验证码" onclick="sendCode()"	class="btn btn-primary btn-warning " data-count="60 ">
							</span>
						</div>
					</div>

					<div id="code" class="form-group">
						<div class="input-group ">
							<span class="input-group-addon ">验证码</span> <input
								id="vCode" name="txt_registersecurity" type="text" class="form-control " />
						</div>
					</div>
					
					<c:if test="${flag == 2 }">
						<div id="tipCode" class="form-group">
							<div class="input-group ">
								<span class="input-group-addon ">验证码错误，密码找回失败</span>
							</div>
						</div>
					</c:if>

					<div id="pwd1" class="form-group" style="display: none;">
						<div class="input-group ">
							<span class="input-group-addon ">新密码</span> <input
								id="txt_password" name="tx_password" type="password"
								class="form-control " />
						</div>
					</div>
					<div id="pwd2" class="form-group" style="display: none;">
						<div class="input-group ">
							<span class="input-group-addon ">确认密码</span> <input
								id="txt_repassword" name="txt_repassword" type="password"
								class="form-control " />
						</div>
					</div>

					<div id="submit1" class="form-group " style="display: none;">
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
 