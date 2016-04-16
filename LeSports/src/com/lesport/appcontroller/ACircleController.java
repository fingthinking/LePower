package com.lesport.appcontroller;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lesport.model.CirLike;
import com.lesport.model.Circle;
import com.lesport.model.Comment;
import com.lesport.model.CommentLike;
import com.lesport.model.RecordCommentAndLike;
import com.lesport.service.CircleService;
import com.lesport.util.Utility;
import com.sun.org.apache.bcel.internal.generic.INEG;

import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/circle")
public class ACircleController {
	
	@Autowired
	private CircleService circleService;
	
	
	//获取好友动态列表，乐友圈接口1
	@RequestMapping(value="/getFriendCircle", method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getFriendCircle(HttpServletRequest request)
	{
		
		String userId = request.getParameter("userId");
		String pageSize = request.getParameter("pageSize");
		String pageNow = request.getParameter("pageNow");
		
		int offset = Integer.parseInt(pageSize)*(Integer.parseInt(pageNow)-1); //偏移量		
		int pageSizeInt = Integer.parseInt(pageSize);	//页面大小
		
		System.out.println("测试输入参数：offset: "+offset +"  pagesize: "+pageSizeInt);		
		
		//调用service层方法获取好友动态列表
		List<Circle> circlelList = circleService.getFriendCircle(userId,offset,pageSizeInt);
		
		String friendCircleJson = Utility.packReturnJson(0, "", circlelList);
		
		//输出测试
		System.out.println("返回json为： "+friendCircleJson);
		
		return friendCircleJson;
		
	}
		
	//获取好友动态列表，乐友圈接口1
	@RequestMapping(value="/getFriendCircle", method=RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getFriendCircle2(String userId, String pageNow, String pageSize)
	{		
		int offset = Integer.parseInt(pageSize)*(Integer.parseInt(pageNow)-1); //偏移量		
		int pageSizeInt = Integer.parseInt(pageSize);	//页面大小
		
		System.out.println("测试输入参数：offset: "+offset +"  pagesize: "+pageSizeInt);		
		
		//调用service层方法获取好友动态列表
		List<Circle> circlelList = circleService.getFriendCircle(userId,offset,pageSizeInt);
		
		String friendCircleJson = Utility.packReturnJson(0, "", circlelList);
		
		//输出测试
		System.out.println("返回json为： "+friendCircleJson);
		
		return friendCircleJson;
	}
		
		
	//获取动态详情，乐友圈接口2
	@RequestMapping(value="/getCircleDetail",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCircleDetail(String circleId)
	{
		System.out.println("测试输入参数：circleId:" +circleId);		
		
		//调用service层方法获取动态详情
		Circle circlelDetail = circleService.getCircleDetail(circleId);
		
		String circleDetailJson= Utility.packReturnJson(0, "", circlelDetail);
		
		//输出测试
		System.out.println("返回json为： "+circleDetailJson);
		
		return circleDetailJson;
	}
	
	//获取我的动态，乐友圈接口3
	@RequestMapping(value="/getMyCircle",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getMyCircle(String userId, String pageNow, String pageSize)
	{
		System.out.println("测试输入参数：userId:" +userId);		
		
		System.out.println("pagesize: "+pageSize);
		System.out.println("pageNow: "+pageNow);
		
		
		int offset = Integer.parseInt(pageSize)*(Integer.parseInt(pageNow)-1); //偏移量		
		int pageSizeInt = Integer.parseInt(pageSize);
		
		//调用service层方法获取我的动态列表
		List<Circle> myCirclels = circleService.getMyCircle(userId,offset,pageSizeInt);
		
		String myCirclesJson= Utility.packReturnJson(0, "", myCirclels);
		
		//输出测试
		System.out.println("返回json为： "+myCirclesJson);
		
		return myCirclesJson;
	}
	
	
	
	//发表动态，乐友圈接口4
	@RequestMapping(value="/publishCircle",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String publishCircle2(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
		
		System.out.println("进入发表动态");
		
		
		//需要后台获取的参数
		String circleId = Utility.getRowId();
		String publishDate = Utility.getFormattedCurrentDateAndTime();
		String createDate = Utility.getFormattedCurrentDateAndTime();
		
		// 获取参数
		String userId = request.getParameter("userId");
		String content = request.getParameter("content");
		String publishAddr = request.getParameter("publishAddr");
		String scopeFlag = request.getParameter("scopeFlag");
		
		String picUrl = circleService.saveImags((MultipartHttpServletRequest)request, userId);
		
		System.out.println();
		
		System.out.println("picUrl: "+picUrl);
		
		System.out.println();
		
		String postString = userId+":"+content+":"+picUrl;
		
		System.out.println("POST请求时间："+new Date().toString());
		System.out.println("获取的POST参数为： "+userId+"--"+content+"--"+publishAddr);
		System.out.println();
		
		response.setCharacterEncoding("utf-8");
		Circle circle = new Circle();
		//将参数封装在circle中
		circle.setCircleId(circleId);
		circle.setUserId(userId);
		circle.setContent(content);
		circle.setPicUrl(picUrl);
		circle.setPublishDate(publishDate);
		circle.setPublishAddr(publishAddr);
		circle.setScopeFlag(scopeFlag);
		circle.setCreateDate(createDate);
		
		//调用service层方法发表动态啊，返回影响行数
		int effectedRows = circleService.addCircle(circle);
		
		String publishCircleJson;
		
		//根据影响行数判断是否插入成功
		if(effectedRows == 0)
		{
			publishCircleJson = Utility.packReturnJson(1, "动态发布失败", "");
		}
		else 
		{
			publishCircleJson = Utility.packReturnJson(0, "", "");			
		}
			
		//测试性结果输出
		System.out.println("发表动态： "+publishCircleJson);
		
		return postString;
		
	}
	
	
	//发表动态，乐友圈接口4
	@RequestMapping(value="/publishCircle",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String publishCircle(String userId, String content, String publishAddr, String scopeFlag)
	{
		//需要后台获取的参数
		String circleId = Utility.getRowId();
		String publishDate = Utility.getFormattedCurrentDateAndTime();
		String createDate = Utility.getFormattedCurrentDateAndTime();
		
	/*	System.out.println("GET请求时间："+new Date().toString());
		System.out.println("获取的GET参数为： "+userId+"--"+content+"--"+publishAddr);
		System.out.println();*/
		
		Circle circle = new Circle();
		//将参数封装在circle中
		circle.setCircleId(circleId);
		circle.setUserId(userId);
		circle.setContent(content);
		circle.setPublishDate(publishDate);
		circle.setPublishAddr(publishAddr);
		circle.setScopeFlag(scopeFlag);
		circle.setCreateDate(createDate);
		
		//调用service层方法发表动态啊，返回影响行数
		int effectedRows = circleService.addCircle(circle);
		
		String publishCircleJson;
		
		//根据影响行数判断是否插入成功
		if(effectedRows == 0)
		{
			publishCircleJson = Utility.packReturnJson(1, "动态发布失败", "");
		}
		else 
		{
			publishCircleJson = Utility.packReturnJson(0, "", "");			
		}
		
		//测试性结果输出
		System.out.println("发表动态： "+publishCircleJson);
		
		return publishCircleJson;
		
	}

	
	//发表评论，乐友圈接口5
	@RequestMapping(value="/publishComment", method=RequestMethod.GET,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String publishComment(String  circleId, String commentUId, String content, String repliedUserId, String ownerId)
	{
		//需要后台获取的参数
		String commentId = Utility.getRowId();
		String commentTime = Utility.getFormattedCurrentDateAndTime();
		String createDate = Utility.getFormattedCurrentDateAndTime();
		
		Comment comment = new Comment();
		
		//将数据封装在comment中
		comment.setCommentId(commentId);
		comment.setCircleId(circleId);
		comment.setCommentUId(commentUId);
		comment.setContent(content);
		comment.setCommentTime(commentTime);
		comment.setReplyUId(repliedUserId);
		comment.setCreateDate(createDate);
		
		//调用service层方法发表评论，返回影响行数
		int effectedRows = circleService.addComment(comment);
		
		String publishCommentJson;
		
		//根据影响行数判断是否插入成功
		if(effectedRows == 0)	//评论发表失败
		{
			publishCommentJson = Utility.packReturnJson(1, "发表评论失败", "");
//			publishCommentJson = "{\"resCode\":1,\"resMsg\":\"\",\"data\":}";;			
		}
		else //评论发表成功
		{
			//需要后台获取的参数
			String recordCommentAndLikeId = Utility.getRowId();
			
			RecordCommentAndLike recordCommentAndLike = new RecordCommentAndLike();
			
			//将数据封装在recordCommentAndLike中
			recordCommentAndLike.setRecordCommentAndLikeId(recordCommentAndLikeId);
			recordCommentAndLike.setCircleId(circleId);
			recordCommentAndLike.setCommentId(commentId);		//这里的commentId就是上面生成的评论id
			recordCommentAndLike.setPublisherId(commentUId);	//这里的发表者id就是以上发表评论者的id
			recordCommentAndLike.setContent(content);
			recordCommentAndLike.setPublishDate(createDate);	//这里的发表时间就是以上评论记录创建的时间
			recordCommentAndLike.setOwnerId(ownerId);
			
			//向recordCommentAndLike表插入相应记录
			int effectedRows2 = circleService.addRecordCommentAndLike(recordCommentAndLike);
			
//			publishCommentJson = Utility.packReturnJson(0, "", "");	
			publishCommentJson = "{\"resCode\":0,\"resMsg\":\"\",\"data\":{\"commentId\":"+commentId+"}}";
			
		}
			
		
		//测试性结果输出
		System.out.println("发表评论： "+publishCommentJson);
		
		return publishCommentJson;
	}
	
	
	//发表评论，乐友圈接口5
	@RequestMapping(value="/publishComment", method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String publishComment2(HttpServletRequest request)
	{
		
		System.out.println("jinru111");
		
		
		//需要后台获取的参数
		String commentId = Utility.getRowId();
		String commentTime = Utility.getFormattedCurrentDateAndTime();
		String createDate = Utility.getFormattedCurrentDateAndTime();
		
		//获取参数
		String  circleId = request.getParameter("circleId");
		String commentUId = request.getParameter("commentUId");
		String content =  request.getParameter("content");
		String repliedUserId = request.getParameter("repliedUserId");	
		String ownerId = request.getParameter("ownerId");	//动态拥有者id
		
		System.out.println("canshu: "+circleId+commentId+content+"   ownerId :  "+ownerId);
		
		Comment comment = new Comment();
		
		//将数据封装在comment中
		comment.setCommentId(commentId);
		comment.setCircleId(circleId);
		comment.setCommentUId(commentUId);
		comment.setContent(content);
		comment.setCommentTime(commentTime);
		comment.setReplyUId(repliedUserId);
		comment.setCreateDate(createDate);
		
		//调用service层方法发表评论，返回影响行数
		int effectedRows = circleService.addComment(comment);
		
		String publishCommentJson;
		
		//根据影响行数判断是否插入成功
		if(effectedRows == 0)	//评论发表失败
		{
			publishCommentJson = Utility.packReturnJson(1, "发表评论失败", "");
		}
		else //评论发表成功
		{
			//需要后台获取的参数
			String recordCommentAndLikeId = Utility.getRowId();
			
			RecordCommentAndLike recordCommentAndLike = new RecordCommentAndLike();
			
			//将数据封装在recordCommentAndLike中
			recordCommentAndLike.setRecordCommentAndLikeId(recordCommentAndLikeId);
			recordCommentAndLike.setCircleId(circleId);
			recordCommentAndLike.setCommentId(commentId);		//这里的commentId就是上面生成的评论id
			recordCommentAndLike.setPublisherId(commentUId);	//这里的发表者id就是以上发表评论者的id
			recordCommentAndLike.setContent(content);
			recordCommentAndLike.setPublishDate(createDate);	//这里的发表时间就是以上评论记录创建的时间
			recordCommentAndLike.setOwnerId(ownerId);
			
			//向recordCommentAndLike表插入相应记录
			int effectedRows2 = circleService.addRecordCommentAndLike(recordCommentAndLike);
			
//			publishCommentJson = Utility.packReturnJson(0, "", "");			
			publishCommentJson = "{\"resCode\":0,\"resMsg\":\"\",\"data\":{\"commentId\":"+commentId+"}}";
		}
			
		
		//测试性结果输出
		System.out.println("发表评论： "+publishCommentJson);
		
		return publishCommentJson;
	}
	
	
	//统计新动态和点赞数量，乐友圈接口11
	@RequestMapping(value="/getCountOfComAndLike",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCountOfComAndLike(String userId)
	{
		int countfNew = circleService.getCountOfComAndLike(userId);
		
		String returnString = "{\"countOfComAndLike\":"+countfNew+"}";
		
		String countOfComAndLikeJson = Utility.packReturnJson(0, "", returnString);
		
		//测试性输出
		System.out.println("countOfComAndLikeJson: "+countOfComAndLikeJson);
		
		return countOfComAndLikeJson;
	}
	
	//测试性上传图片 2016年3月14日11:03:09
	//发表动态，乐友圈接口4
	@RequestMapping(value="/uploadImg",method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadImg(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
	
		System.out.println("收到img请求："+new Date().toString());
		
		String picUrl = circleService.saveImags((MultipartHttpServletRequest)request,"");
		
		String picUrlNew = picUrl.substring(1,picUrl.length()-1);	
		
		System.out.println("返回picUrl: "+picUrlNew);
		
		return picUrlNew;
		
	}
	
	
	
	
	
	/*----------------------------------以下为刘亚中所写---------------------------------------------------*/
	
	/*
	 * 
	 * 转发动态
	 **/
	@RequestMapping(value="/forwardLike",method = RequestMethod.GET)
	@ResponseBody
	public String dispathSurge(String oldCircleId,String userId,String newUserId,String location,String scopeFlag)
	{
		System.out.println("---------------"+oldCircleId);		
		
		Circle circleSurge=circleService.getToDispathSurge(oldCircleId);
		//System.out.println(JSONSerializer.toJSON(circleSurge));
		circleSurge.setUserId(newUserId);
		circleSurge.setCircleId(Utility.getRowId());
		String date=new Date().toLocaleString();
		circleSurge.setPublishDate(date);
		circleSurge.setComments(null);
		circleSurge.setPublishAddr(location);
		circleSurge.setScopeFlag("1");
		circleSurge.setCreateDate(date);
		boolean state=circleService.saveDispathSurge(circleSurge);
		if(state)
		{
			System.out.println("转发成功");
			return  "{\"resCode\":0,\"resMsg\":\"\",\"data\":{}}";
		}else
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";	
	}
	
	
	/*
	 * 
	 * 转发动态
	 **/
	@RequestMapping(value="/forwardLike",method = RequestMethod.POST)
	@ResponseBody
	public String dispathSurge2(HttpServletRequest request)
	{		
		String oldCircleId = request.getParameter("oldCircleId");
		String userId = request.getParameter("userId");
		String newUserId = request.getParameter("newUserId");
		String location = request.getParameter("location");
//		String scopeFlag = request.getParameter("scopeFlag");
				
		System.out.println("---------------"+oldCircleId);
		Circle circleSurge=circleService.getToDispathSurge(oldCircleId);
		//System.out.println(JSONSerializer.toJSON(circleSurge));
		circleSurge.setUserId(newUserId);
		circleSurge.setCircleId(Utility.getRowId());
		String date=new Date().toLocaleString();
		circleSurge.setPublishDate(date);
		circleSurge.setComments(null);
		circleSurge.setPublishAddr(location);
		circleSurge.setScopeFlag("1");
		circleSurge.setCreateDate(date);
		boolean state=circleService.saveDispathSurge(circleSurge);
		if(state)
		{
			System.out.println("转发成功");
			return  "{\"resCode\":0,\"resMsg\":\"\",\"data\":{}}";
		}else
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";	
	}
	
	
	@RequestMapping("/deleteLike")
	@ResponseBody
	/*
	 * 取消点赞
	 * */
	public String deletePraise(String circleId,String userId)
	{
		boolean state=circleService.deletePraise(circleId, userId);
		if(state)
		{
			System.out.println("-------------------"+"取消成功");
			return "{\"resCode\":0,\"resMsg\":\"\",\"data\":{}}";
		}else {
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";
		}
	}
	
	
	@RequestMapping("/deleteComment")
	@ResponseBody
	/*
	 * 删除评论
	 * */
	public String deleteComment(String commentId)
	{
		boolean state=circleService.deleteComment(commentId);
		if(state)
		{
			System.out.println("-------------------"+"取消成功");
			return "{\"resCode\":0,\"resMsg\":\"\",\"data\":{}}";
		}else {
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";
		}
	}
	@RequestMapping("/deleteCircle")
	@ResponseBody
	/*
	 * 删除动态
	 * */
	public String deleteCircle(String circleId)
	{
		System.out.println("删除动circleId态："+circleId);
		
		boolean state=circleService.deleteCircle(circleId);
		if(state)
		{
			System.out.println("-------------------"+"删除动态成功");
			return "{\"resCode\":0,\"resMsg\":\"\",\"data\":{}}";
		}else {
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";
		}
	}
	@RequestMapping("/addLike")
	@ResponseBody
	/*
	 * 点赞
	 * */
	public String addLike(String circleId,String userId,String ownerId)
	{
		CirLike cirLike=new CirLike();	
		String  date=new Date().toLocaleString();
		//将点赞保存到新评论和点赞表中
		circleService.addPraiseRemind(Utility.getRowId(), circleId, userId, date,ownerId);
		cirLike.setCirLikeId(Utility.getRowId());
		cirLike.setCreateDate(date);
		cirLike.setLikeUId(userId);
		cirLike.setCircleId(circleId);
		boolean state=circleService.addLike(cirLike);
		if(state)
		{
			System.out.println("-------------------"+"点赞成功");
			return "{\"resCode\":0,\"resMsg\":\"\",\"data\":{}}";
		}else {
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";
		}
	}
	/*
	 * 获取评论内容和点赞者接口
	 * */
	@RequestMapping("/getContentOfCirAndLike")
	@ResponseBody
	public String getContentOfCirAndLike(String userId)
	{
		List<CommentLike> commentLikes=circleService.getContentOfCirAndLike(userId);
		circleService.setSawFlag(userId);
		System.out.println(JSONSerializer.toJSON(commentLikes));
		if(!commentLikes.isEmpty())
			return "\"resCode\":0,\"resMsg\":\"\",\"data\":"+JSONSerializer.toJSON(commentLikes);
		else
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";
	}
}
