<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String basepath = request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>

<head>
<title>个人中心</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%=basepath%>pages/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>pages/css/ordinary.css" />
<link rel="stylesheet" href="<%=basepath%>pages/css/personal.css" />
<link rel="stylesheet"
	href="<%=basepath%>pages/css/font-awesome-min.css" />
<script type="text/javascript" src="<%=basepath%>pages/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basepath%>pages/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basepath%>pages/js/personal_center_txt.js"></script>

<script type="text/javascript">
	//检查邮箱
	function checkEmail() {
		var email = document.getElementById("inputEmail3").value;
		var spanNode = document.getElementById("emailSpan");

		//编写邮箱的正则       13456@qq.com  13456@qq.net  13456@qq.com.cn
		var reg = /^[a-z0-9]\w+@[a-z0-9]+(\.[a-z]{2,3}){1,2}$/i;
		if (reg.test(email)) {
			//符合规则
			spanNode.innerHTML = "正确".fontcolor("green");
			return true;
		} else {
			//不符合规则
			spanNode.innerHTML = "错误".fontcolor("red");

			return false;
		}
	}

	//检查手机号
	function checkPhone() {
		var phoneNum = document.getElementById("inputTel").value;
		var spanNode = document.getElementById("phoneSpan");
		var reg = /^(?:13\d|15\d|17\d|18\d)-?\d{5}(\d{3}|\*{3})$/;
		if (reg.test(phoneNum)) {
			//符合规则
			spanNode.innerHTML = "正确".fontcolor("green");
			return true;
		} else {
			//不符合规则
			spanNode.innerHTML = "错误".fontcolor("red");
			return false;
		}
	}

	//检查生日的格式
	function checkBirthday() {
		var birthday = document.getElementById("inputDate").value;
		var spanNode = document.getElementById("birthdaySpan");
		if (birthday == "") {
			spanNode.innerHTML = "日期不能为空".fontcolor("red");
			return false;
		} else {
			spanNode.innerHTML = "正确".fontcolor("green");
			return true;
		}
	}

	//检查地址的格式
	function checkLocation() {
		var province = document.getElementById("province").value;
		var city = document.getElementById("city").value;
		var spanNode = document.getElementById("locationSpan");
		if (province == "请选择省份") {
			spanNode.innerHTML = "请选择省份".fontcolor("red");
			return false;
		} else if (city == "请选择市区") {
			spanNode.innerHTML = "请选择城市".fontcolor("red");
			return false;
		} else {
			spanNode.innerHTML = "正确".fontcolor("green");
			return true;
		}
	}

	//提交时检查格式
	function checkAll() {
		// var email = checkEmail(); 
		var birthday = checkBirthday();
		//var location = checkLocation();
		// var phone = checkPhone(); 
		if (birthday && location) {
			return true;
		} else {
			return false;
		}
	}

	// 表单提交的时候是会触发onsubmit事件的，如果onsubmit事件的方法返回是true，那么该表单允许提交，如果返回的是false，该表单不允许提交。	
	function aa() {
		$("#province").val('${user.province}');

		chinaChange($("#province"), $("#city"));

		$("#city").get(0).value = '${user.city}';
	}

	function F_Open_dialog() {
		document.getElementById("btn_file").click();
	}

	//页面初始化时，将用户原来的信息显示出来
	function doInit() {
		var gender = '${user.sex }';
		var province1 = '${user.province}';
		var city1 = '${user.city}';

		//设置性别
		if (gender == "女") {//gender = 1时说明性别为女			
			$("#female").attr("checked", "checked");
		} else {//否则说明性别为男			
			$("#male").attr("checked", "true");
		}

		//如果省份为空，则将显示选择提醒
		if (province1 == "") {
			province1 = "请选择省份";
			city1 = "请选择市区";
		}
		//设置省份
		$("#province").val(province1);
		chinaChange(document.getElementById('province'), document
				.getElementById('city'));
		$("#city").val(city1);
	}

	//点击修改头像时显示相关控件
	function showFileInput() {
		$("#btn_file").css("display", "block");
		$('#btn_file').attr("disabled", false);
		$("#fileSubmit").css("display", "block");
		$('#fileSubmit').attr("disabled", false);
	}

	//点击提交头像信息时的处理
	function updateImg() {
		var formData = new FormData($("#uploadForm")[0]);
		$.ajax({
			url : '../webUserInfo/updateImg',
			type : 'POST',
			data : formData,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			success : function(result) {
			}
		});
	}
</script>


</head>

<body onload="doInit()">
	<nav class="nav">
		<div class="burger">
			<div class="burger__patty"></div>
		</div>
		<ul class="nav__list">
			<li class="nav_item"><a href="<%=basepath%>pages/index.jsp">首页</a></li>
			<li class="nav_item"><a href="<%=basepath%>websport/sportChart">我的运动</a></li>
			<li class="nav_item"><a href="<%=basepath%>weblecourse/showNews">Le课堂</a></li>
			<li class="nav_item"><a
				href="<%=basepath%>webcircle/comeIntoCircle">乐友圈</a></li>
			<li class="nav_item"><a href="<%=basepath%>pages/popular.jsp">手机应用</a></li>
		</ul>
	</nav>

	<div class="top-section header">
		<div class="container">
			<div class="row">
				<h1>个人中心</h1>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row" id="message">
			<p>亲爱的${user.nickName},填写真实的资料，有助于好友找到你哦。${user.sex }</p>
		</div>
		<div class="col-xs-12 col-md-2 col-sm-2">
			<div class="hidden-xs"></div>
		</div>
		<div class="col-xs-12  col-sm-6 col-md-6" id="user_msg">

			<form class="form-horizontal" action="../webUserInfo/updateUserInfo"
				method="post" onsubmit="return checkAll()">

				<div class="form-group" hidden="true">
					<label class="col-sm-2 ">用户ID</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputUser"
							name="userId" placeholder="用户ID" value="${user.userId }"
							readOnly="readonly">
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 ">乐ID</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputUser" name="leId"
							placeholder="乐ID" value="${user.leNum }" readOnly="readonly">
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 ">昵称</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="nickname"
							name="nickName" placeholder="昵称" value="${user.nickName }">
					</div>
				</div>

				<div class="form-group ">
					<label class="col-sm-2 control-label">性别</label>
					<div class="col-sm-10">
						<label class="radio-inline"> <input type="radio"
							name="gender" id="male" value="men">男
						</label> <label class="radio-inline"> <input type="radio"
							name="gender" id="female" value="women">女
						</label>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">手机号</label>
					<div class="col-sm-10">
						<input type="tel" class="form-control" id="inputTel"
							name="phoneNum" placeholder="手机号" value="${user.phoneNum }"
							readOnly="readonly"><span id="phoneSpan"></span>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">邮箱</label>
					<div class="col-sm-10">
						<input type="email" class="form-control" id="inputEmail3"
							name="emailNum" placeholder="邮箱" value="${user.email }"
							readOnly="readonly"><span id="emailSpan"></span>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">出生日期</label>
					<div class="col-sm-10">
						<input type="date" class="form-control" id="inputDate"
							name="birthday" placeholder="出生日期" onblur="checkBirthday()"
							value="${user.birthday }"><span id="birthdaySpan"></span>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">微博号</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputWeibo"
							name="weiboNum" placeholder="微博号" value="${user.weiboNum } "
							readOnly="readonly" />

					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">QQ号</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="inputQQ" name="qqNum"
							placeholder="QQ号" value="${user.qqNum }" onblur="checkQq()"
							readOnly="readonly" /> <span id="qqSpan"></span>

					</div>
				</div>

				<div class="form-group">
					<div class="form-inline">
						<label class="col-sm-2 control-label">地区</label>
						<div class="col-sm-10 ">
							<select id="province" name="province" class="form-control"
								onchange="chinaChange(this,document.getElementById('city'));"
								style="width: 40%; height: 30px;" onblur="checkLocaiton()">
								<option value="请选择省份">请选择省份</option>
								<option value="北京市">北京市</option>
								<option value="天津市">天津市</option>
								<option value="上海市">上海市</option>
								<option value="重庆市">重庆市</option>
								<option value="河北省">河北省</option>
								<option value="山西省">山西省</option>
								<option value="辽宁省">辽宁省</option>
								<option value="吉林省">吉林省</option>
								<option value="黑龙江省">黑龙江省</option>
								<option value="江苏省">江苏省</option>
								<option value="浙江省">浙江省</option>
								<option value="安徽省">安徽省</option>
								<option value="福建省">福建省</option>
								<option value="江西省">江西省</option>
								<option value="山东省">山东省</option>
								<option value="河南省">河南省</option>
								<option value="湖北省">湖北省</option>
								<option value="湖南省">湖南省</option>
								<option value="广东省">广东省</option>
								<option value="海南省">海南省</option>
								<option value="四川省">四川省</option>
								<option value="贵州省">贵州省</option>
								<option value="云南省">云南省</option>
								<option value="陕西省">陕西省</option>
								<option value="甘肃省">甘肃省</option>
								<option value="青海省">青海省</option>
								<option value="台湾省">台湾省</option>
								<option value="广西壮族自治区">广西壮族自治区</option>
								<option value="内蒙古自治区">内蒙古自治区</option>
								<option value="西藏自治区">西藏自治区</option>
								<option value="宁夏回族自治区">宁夏回族自治区</option>
								<option value="新疆维吾尔自治区">新疆维吾尔自治区</option>
								<option value="香港特别行政区">香港特别行政区</option>
								<option value="澳门特别行政区">澳门特别行政区</option>
							</select> <select class="form-control" name="city" id="city"
								style="width: 40%; height: 30px;" onblur="checkLocaiton()">
								<option value="请选择市区">请选择市区</option>
							</select> <span id="locationSpan"></span>
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-offset-7 col-sm-4">
						<button type="submit" class="btn btn-success">提交</button>
					</div>
				</div>

			</form>
		</div>


		<!-- 修改头像 -->
		<form id="uploadForm">
			<div class="col-xs-12 col-sm-4 col-md-4" id="user-pic">
				<p></p>
				<div style="background:rgb(255,255,255);width:150px;height:150px;">
					<img
						src="<%=basepath%>${fn:replace(user.imgURL,'http://192.168.1.100:8080/LeSports', '' ) }"
						name="imgurl">
				</div>

				<div>
					<input type="button" id="btn_update" value="修改头像"
						onclick="showFileInput()" />
				</div>
				<div>
					<input type="file" name="btn_file" id="btn_file"
						style="display: none;" disabled="disabled" /> <input type="submit"
						id="fileSubmit" value="提交" onclick="updateImg()"
						style="display: none;" disabled="disabled" />
				</div>

			</div>
		</form>

	</div>

</body>

<script type="text/javascript">
	var menu = document.querySelector(".nav__list");
	var burger = document.querySelector(".burger");
	var openMenu = function() {
		burger.classList.toggle('burger--active');
		menu.classList.toggle('nav__list--active');
	};
	burger.addEventListener('click', openMenu, false);
</script>

</html>