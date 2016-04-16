<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页</title>
	
  </head>
  
  <body>
   	<a href="<%=basePath%>user/getAllUser">进入用户管理页</a>
   	<div style="border-color: red; width: 100%;">
   		<p>曹汝帅测试区域</p>
   		<hr/>
   		<p>登录</p>
   		<form action="<%=basePath%>userInfo/loginWeb/" method="post">
   			用户名：<input type="text" name="userName" />
   			密码：<input type="password" name="userPwd" />
   			<input type="submit" name="提交" />
   			<!-- 采用EL插入java代码，当loginFault非空是则输出登录失败的提示信息 -->
   			<!-- 另外，通过后台的loginUserInfo对象获得登录系统的用户信息 -->
   		</form>
   		<hr/>
   		<% String manager="mr";%>
   		管理员：<%= manager %>
   		<%!
   			int number = 0;
   			int count() {
				number++;
				return number;
   			}
   		%>
   		输出number的值：<%= count() %>
   		<hr/>
   	</div>
   	<!-- 动态转发测试 -->
   	<div style="margin-top: 20px">测试转发动态功能
   	<form action="circle/forwardLike" method="post">
   	原动态Id：<input type="text" name="oldCircleId"/><br/>
   	用户Id：<input type="text" name="userId"/><br/>
   	转发者Id：<input type="text" name="newUserId"/><br/>
   	位置：<input type="text" name="location"/><br/>
   	<input type="submit" name="提交"/>
   	</form>
   	</div>
   	<div style="margin-top: 20px">
   	测试取消点赞
   	<form action="circle/deleteLike" method="post">
   	动态Id：<input type="text" name="circleId"/><br/>
   	用户Id：<input type="text" name="userId"/><br/>
   	<input type="submit" name="提交"/>
   	</form>
   	</div>
   	<div style="margin-top: 20px">
   	测试删除评论
   	<form action="circle/deleteComment" method="post">
   	评论Id：<input type="text" name="commentId"/><br/>
   	<input type="submit" name="提交"/>
   	</form>
   	</div>
   	<div style="margin-top: 20px">
   	测试删除动态
   	<form action="circle/deleteCircle" method="post">
   	动态Id：<input type="text" name="circleId"/><br/>
   	<input type="submit" name="提交"/>
   	</form>
   	</div>
   	<div style="margin-top: 20px">
   	测试获取新评论和点赞
   	<form action="circle/getContentOfCirAndLike" method="post">
   	动态Id：<input type="text" name="userId"/>
   	<input type="submit" name="提交"/>
   	</form>
   	</div>
   	<div style="margin-top: 20px">
   	测试点赞
   	<form action="circle/addLike" method="post">
   	动态Id：<input type="text" name="circleId"/><br/>
   	用户Id：<input type="text" name="userId"/>
   	拥有者Id：<input type="text" name="ownerId"/>
   	<input type="submit" name="提交"/>
   	</form>
   	</div>
   	<div style="margin-top: 20px">
   	测试搜索好友
   	<form action="friend/findFriends" method="post">
   	乐动力Id：<input type="text" name="le_id"/><br/>
   	用户Id：<input type="text" name="userId"/>
   	<input type="submit" name="提交"/>
   	</form>
   	</div>
  </body>
</html>
