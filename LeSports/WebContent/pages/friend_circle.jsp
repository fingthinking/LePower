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
<meta charset="UTF-8">
<title>乐友圈</title>
<link rel="stylesheet" href="<%=basepath%>pages/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=basepath%>pages/css/friendcircle.css" />
<link rel="stylesheet" href="<%=basepath%>pages/css/ordinary.css" />
<link rel="stylesheet"
	href="<%=basepath%>pages/css/font-awesome-min.css" />
<script type="text/javascript" src="<%=basepath%>pages/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basepath%>pages/js/bootstrap.min.js"></script>

<script type="text/javascript">
	//动态回复点击事件响应
	function circleReply(circleId, repliedUId, commentId) {
		//当点击回复时，先将已经弹出的输入框和相关按钮移除
		$("#flagid").remove();

		//定义回复类型，回复类型分为对评论的回复和对动态的回复
		var replyType;

		if (repliedUId == "") //repliedUId为空，说明是对动态的回复
		{
			replyType = "circlefunction" + circleId;
		} else//否则是对评论的回复
		{
			replyType = "commentfunction" + commentId;
		}

		$("#" + replyType)
				.append(
						"<div  id=flagid ><div><textarea rows=3 class=circle-textarea id=inputCircle"+circleId+ "  placeholder=我也说一句 ></textarea></div>"
								+ "<div> <input class=btn-submit type=button id=btnCircle"
								+ circleId
								+ " onclick=btnClick(\'"
								+ circleId
								+ "\',\'"
								+ repliedUId
								+ "\') value=提交 />"
								+ "<input type=button class=btn-cancel id=btnCancel"
								+ circleId
								+ " onclick=btnCancel(\'"
								+ circleId
								+ "\')  value=取消 /> </div></div>");
		//输入框获取焦点
		$("#inputCircle" + circleId).focus();
	}

	//--------------------------------------------------------------------------------------//
	//回复时点击提交的处理
	function btnClick(circleId, repliedUId) {
		var inputContent = $("#inputCircle" + circleId).val();
		var inputUserId = $("#userIdIn" + circleId).val(); //动态拥有者id(ownerId)

		//判断评论是否为空，为空则要求重新输入
		if (inputContent == "") {
			alert("评论不能为空哦~");
			$("#inputCircle" + circleId).focus();
			return false;
		}

		$.post('../webcircle/publishComment', {
			"content" : inputContent,
			"circleId" : circleId,
			"ownerId" : inputUserId,
			"repliedUId" : repliedUId
		}, function(result) {

			var resCode = result.resCode;

			if (resCode == 0)//回复成功，刷新页面
			{
				location.reload();
			} else//回复失败，给出提示
			{
				alert("不好意思，回复失败哦~");
			}
		}, "json");

		//提交的同时将输入框和按钮删除
		$("#inputCircle" + circleId).remove();
		$("#btnCircle" + circleId).remove();
		$("#btnCancel" + circleId).remove();
	}

	//点击取消按钮时的处理
	function btnCancel(circleId) {
		//取消时将输入框和按钮删除
		$("#inputCircle" + circleId).remove();
		$("#btnCircle" + circleId).remove();
		$("#btnCancel" + circleId).remove();
	}

	//--------------------------------------------------------------------------------------------//
	//处理点赞操作
	function sendLike(circleId) {
		var inputUserId = $("#userIdIn" + circleId).val(); //动态拥有者id(ownerId)

		$.post('../webcircle/addLike', {
			"circleId" : circleId,
			"ownerId" : inputUserId
		}, function(result) {
			var resCode = result.resCode;

			if (resCode == 0)//回复成功，刷新页面
			{
				location.reload();
			} else//回复失败，给出提示
			{
				alert("不好意思，回复失败哦~");
			}
		}, "json");
	}

	//-------------------------------------------------------------------------------------------//
	//转发的处理
	function forwardCircle(content) {
		$.post('../webcircle/forwardCircle', {
			"contentIn" : content
		}, function(result) {

			var resCode = result.resCode;
			if (resCode == 0)//转发成功，刷新页面
			{
				alert("转发成功!");
				location.reload();
			} else//转发失败，给出提示
			{
				alert("不好意思，转发失败哦~");
			}

		}, "json");
	}

	//------------------------------------------------------------------------------------------//
	//删除动态的处理
	function deleteCircle(circleId) {
		$.post('../webcircle/deleteCircle', {
			"circleId" : circleId
		}, function(result) {
			var resCode = result.resCode;
			if (resCode == 0)//删除动态成功，刷新页面
			{
				alert("删除成功");
				location.reload();
			} else//回复失败，给出提示
			{
				alert("不好意思，删除动态失败哦~");
			}
		}, "json");
	}

	//-----------------------------------------------------------------------------------------//
	//删除评论的处理
	function deleteComment(commentId) {
		$.post('../webcircle/deleteComment', {
			"commentId" : commentId
		}, function(result) {
			var resCode = result.resCode;
			if (resCode == 0)//删除评论成功，刷新页面
			{
				location.reload();
			} else//删除评论失败，给出提示
			{
				alert("不好意思，删除评论失败哦~");
			}
		}, "json");
	}

	//---------------------------------------------------------------------------//
	//取消点赞的处理
	function deleteLike(circleId) {
		$.post('../webcircle/deleteLike', {
			"circleId" : circleId
		}, function(result) {
			var resCode = result.resCode;
			if (resCode == 0)//取消点赞成功，刷新页面
			{
				location.reload();
			} else//取消点赞失败，给出提示
			{
				alert("不好意思，取消点赞失败哦~");
			}
		}, "json");
	}

	//------------------------------------------------------------------------//
	//发表动态时的处理
	function publishCircle() {
		var content = $("#contentIn").val();

		if (content == "") {
			alert("发表内容不能为空~ ");
			return false;
		}
		$("form").submit();

	}

	//如果用户没有登录，则不能进入乐友圈
	function remindLogin() {
		alert("请先登陆,才能进入乐友圈！");
	}
	var toolbar = document.getElementById("toolbar");
	var li = toolbar.getElementsByTagName("li");
	var userMenu = li.getElementsByTagName("ul");
	alert(userMenu);

	function show() {
		$("#testId").css("display", "block");
		$("#login").text('${sessionScope.loginUserInfo.leNum}');

	}
	userMenu.addEventListener("mousemove", show());

	function hide() {
		$("#testId").css("display", "none");
		$("#login").text('${sessionScope.loginUserInfo.nickName}');
	}
	userMenu.addEventListener("mouseout", hide());
</script>

</head>


<body
	style="background: url(../img/friend_bg.png); background-repeat: no-repeat; background-size: cover; width: 100%;">
	<nav class="nav">
		<div class="burger">
			<div class="burger__patty"></div>
		</div>
		<ul class="nav__list">
			<li class="nav_item"><a href="<%=basepath%>pages/index.jsp">首页</a></li>

			<li class="nav_item"><a href="<%=basepath%>websport/sportChart">我的运动</a></li>

			<li class="nav_item"><a href="<%=basepath%>weblecourse/showNews">Le课堂</a></li>

			<!-- 显示下载链接的二维码图片 -->
			<li class="nav_item"><a href="<%=basepath%>pages/popular.jsp">手机应用</a></li>

		</ul>
	</nav>

	<div class="title-section">
		<div class="container friend-background">
			<div class="row">
				<div class="col-md-10 col-sm-10 col-xs-10">
					<div class="font">
						<img src="<%=basepath%>img/friendcircle_title_font.png"
							style="margin-top: -130px; margin-left: 50px;">
					</div>
				</div>

				<div class="col-md-2 col-sm-2 col-xs-2 title-user">
					<div class="navbar-default"
						style="background: rgba(255, 255, 255, 0.5);">
						<ul class="nav navbar-nav navbar-right" id="toolbar">
							<!-- 当用户未登录时，显示登陆和注册； 登陆以后，显示用户昵称 -->
							<li onmouseover="show()" onmouseout="hide()"><a id="login"
								href="javascript:;" style="color: #99CC99" class="navbar-link">${sessionScope.loginUserInfo.nickName}</a>
								<ul id="testId" style="display: none;">
									<li><a href="<%=basepath%>webUserInfo/showUserInfo">个人中心</a></li>
									<li><a href="<%=basepath%>pages/updatePassword.jsp">修改密码</a></li>
									<li><a href="<%=basepath%>webUserInfo/logout">退出</a></li>
								</ul></li>
						</ul>
					</div>
				</div>

			</div>
		</div>
	</div>

	<div class="row" style="margin-right: 0px; margin-left: 0px;">

		<div class="col-md-8 circle_content ">

			<div class="ds-replybox">
				<img class="ds-avatar" style="width: 120px; height: 120px;"
					src="<%=basepath%>${fn:replace(sessionScope.loginUserInfo.imgURL,'http://192.168.1.100:8080/LeSports', '' ) }" />
				<!-- 发表动态表单 开始 -->

				<form action="../webcircle/publishCircle" method="post">
					<input type="hidden" name="flag" value="" /> <input type="hidden"
						name="circleId" value="" /> <input type="hidden" name="nonce"
						value="" />

					<div class="ds-textarea-wrapper ">
						<textarea id="contentIn" name="contentIn" placeholder="我来说一句"
							style="height: 108px;"></textarea>
					</div>
					<div class="ds-post-toolbar">
						<!-- 用来占位 -->
						<div class="ds-post-options">
							<span></span>
						</div>

						<input class="ds-post-button" type="button" value="发    布"
							onclick="publishCircle()">
						<div class="ds-toolbar-buttons"></div>
					</div>
				</form>
				<!-- 发表动态表单 结束 -->
			</div>

			<div class="ds-comments-info"></div>

			<!-- 显示动态 开始 -->
			<ul class="ds-comments">

				<!-- 遍历所有动态 开始-->
				<c:forEach var="circle" items="${circles}">

					<!-- 生成点赞链接 -->
					<c:url var="addLike" value="/webcircle/addLike">
						<c:param name="userId"
							value="${sessionScope.loginUserInfo.userName }"></c:param>
						<c:param name="circleId" value="${circle.circleId }"></c:param>
						<c:param name="ownerId" value="${circle.userId }"></c:param>
					</c:url>

					<li class="ds-post" data-post-id="">

						<div class="ds-post-self" data-post-id="" data-thread-id=""
							data-root-id="" data-srouce="">
							<div class="ds-avatar" data-user-id="">
								<img
									src="<%=basepath%>${fn:replace(circle.imgUrl,'http://192.168.1.100:8080/LeSports', '' ) }"
									alt="" />
							</div>

							<div class="ds-comment-body">
								<input type="hidden" id="userIdIn${circle.circleId }"
									value=${circle.userId } />
								<div class="ds-comment-header">
									<span class="ds-user-name">${circle.nickName }</span>
								</div>
								<p>${circle.content }</p>
								<div id="circlefunction${circle.circleId}"
									class="ds-comment-footer ds-comment-actions">
									<span class="ds-time"> ${circle.publishDate } </span> <a
										class="ds-post-reply"
										onclick="circleReply('${circle.circleId}','','')"
										style="cursor: pointer"> 回复 </a>
									<!-- 如果当前用户id与某个点赞者id相同，说明该用户已对该用户点赞，此时显示的是取消点赞；否则显示点赞 -->
									<!-- exitId使来标示是否已经点赞 -->
									<c:set var="exitId" value="0"></c:set>
									<c:forEach var="cirLike" items="${circle.cirLikes }">
										<c:if
											test="${cirLike.likeUId == sessionScope.loginUserInfo.userId}">
											<a class="ds-post-likes"
												onclick="deleteLike('${circle.circleId}')"
												style="cursor: pointer"> 取消赞 </a>
											<c:set var="exitId" value="1"></c:set>
										</c:if>
									</c:forEach>
									<!-- 如果此时exitId仍为0，则说明当前用户没有对此条动态点赞 -->
									<c:if test="${exitId == '0'}">
										<a class="ds-post-likes"
											onclick="sendLike('${circle.circleId}')"
											style="cursor: pointer"> 赞 </a>
									</c:if>

									<a class="ds-post-repost"
										onclick="forwardCircle('${circle.content}')"
										style="cursor: pointer"> 转发 </a>

									<!-- 如果动态的发表者id与当前用户id相等，则允许用户删除  -->
									<c:if
										test="${circle.userId  == sessionScope.loginUserInfo.userId }">
										<a class="ds-post-reply"
											onclick="deleteCircle('${circle.circleId}')"
											style="cursor: pointer">删除</a>
									</c:if>

								</div>

								<!-- 列出所有点赞者昵称 -->
								<label class="ds-user-agree"> <c:forEach var="cirLike"
										items="${circle.cirLikes}">									
										${cirLike.likeNickName }							
									</c:forEach> <!-- 如果点赞者为空，则不显示以下几个字 --> <c:if
										test="${not empty circle.cirLikes}">
										觉得很赞!	
									</c:if>
								</label>

							</div>


							<!-- 遍历评论 开始-->
							<ul class="ds-children">

								<c:forEach var="comment" items="${circle.comments}">
									<li class="ds-post" data-post-id="">
										<div class="ds-post-self" data-post-id="" data-thread-id=""
											data-root-id="" data-srouce="">
											<div class="ds-avatar" data-user-id="">
												<img
													src="<%=basepath%>${fn:replace(comment.commentImgUrl,'http://192.168.1.100:8080/LeSports', '' ) }"
													alt="">
											</div>
											<div class="ds-comment-body">
												<div class="ds-comment-header">
													<!-- 如果被回复者为空，则表述为XX评论说；若不为空，则表述为XX回复XX说 -->
													<c:choose>
														<c:when test="${ not empty comment.replyNickName}">
															<span class="ds-user-name" data-qqt-account>${comment.commentNickName }回复${comment.replyNickName }说:</span>
														</c:when>
														<c:otherwise>
															<span class="ds-user-name" data-qqt-account>${comment.commentNickName }评论说:</span>
														</c:otherwise>
													</c:choose>
												</div>
												<p>${comment.content }</p>
												<div id="commentfunction${comment.commentId}"
													class="ds-comment-footer ds-comment-actions">
													<span class="ds-time" publishtime="2016-03-05T22:02:34"
														title="2016年3月5日 下午10:02:34"> ${comment.commentTime }</span>
													<a class="ds-post-reply"
														onclick="circleReply('${circle.circleId}','${comment.commentUId }','${comment.commentId}')"
														style="cursor: pointer"> 回复 </a>
													<!-- 如果评论者id与当前用户id相同，则允许用户删除该评论 -->
													<c:if
														test="${comment.commentUId  == sessionScope.loginUserInfo.userId }">
														<a class="ds-post-reply"
															onclick="deleteComment('${comment.commentId}')"
															style="cursor: pointer">删除</a>
													</c:if>

												</div>
											</div>
										</div>
									</li>

								</c:forEach>

							</ul>
							<!-- 遍历评论 结束-->

						</div>
					</li>

				</c:forEach>
				<!-- 遍历所有动态 结束-->
			</ul>


			<!-- 上下页栏 开始 -->
			<div class="ds-paginator">
				<div class=" ds-border"></div>
				<!-- 上一页标签 -->
				<c:url var="prePage" value="/webcircle/prePage">
					<c:param name="pageNow" value="${pageNow }"></c:param>
					<c:param name="totalPage" value="${totalPage }"></c:param>
				</c:url>

				<!-- 下一页标签 -->
				<c:url var="nextPage" value="/webcircle/nextPage">
					<c:param name="pageNow" value="${pageNow }"></c:param>
					<c:param name="totalPage" value="${totalPage }"></c:param>
				</c:url>

				<a href=${prePage }>上一页 </a> 目前你位于：${pageNow }&nbsp;&nbsp;共有${totalPage}页

				<a href=${nextPage }>下一页 </a>

			</div>
			<!-- 上下页栏 开始 -->

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