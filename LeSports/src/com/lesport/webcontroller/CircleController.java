package com.lesport.webcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lesport.model.CirLike;
import com.lesport.model.Circle;
import com.lesport.model.Comment;
import com.lesport.model.RecordCommentAndLike;
import com.lesport.model.UserInfo;
import com.lesport.service.CircleService;
import com.lesport.util.Utility;

@Controller
@RequestMapping("/webcircle")
public class CircleController {

	@Autowired
	private CircleService circleService;
	
	int pageSizeInt = 3;
	
	//进入乐友圈
	@RequestMapping(value="/comeIntoCircle")
	public ModelAndView comeIntoCircle(HttpServletRequest request, HttpSession session)
	{
		System.out.println("进入乐友圈");
		
		//从session中获取userId
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");		
		String userId = loginUserInfo.getUserId();		
		
		int offset = 0;
		
		//获取动态总数
		int totalRecord = circleService.getCountOfMineAndFriend(userId);
		int totalPage = ((int) Math.ceil((double)totalRecord/pageSizeInt));
		
		totalPage = (totalPage == 0) ? 1 : totalPage;
		
		System.out.println("circle+totalRecord: "+totalRecord);
		
		//获取动态
		List<Circle> circles = circleService.getFriendCircle(userId, offset, pageSizeInt);	
		
		System.out.println("进入时获得的数据： "+Utility.packReturnJson(0, "", circles));
		
		//定义ModelAndView，并将参数存入其中
		ModelAndView mav = new ModelAndView("pages/friend_circle");	
		mav.addObject("circles", circles);
		mav.addObject("pageNow",1);
		mav.addObject("totalPage",totalPage);		
		
		return mav;
	}
	
	//下一页
	@RequestMapping(value="/nextPage")
	public ModelAndView nextPage(String pageNow, String totalPage,HttpSession session)
	{
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		
		String userId = loginUserInfo.getUserId();
		
		//当前页码
		int pageNowInt = Integer.parseInt(pageNow);
		
		//总页数
		int totalPageInt = Integer.parseInt(totalPage);
		
		//下一页页码
		pageNowInt = (pageNowInt+1)>totalPageInt ? totalPageInt : pageNowInt+1;
		
		//偏移量
		int offset = (pageNowInt-1)*pageSizeInt ;
	
		//获取动态
		List<Circle> circles = circleService.getFriendCircle(userId, offset, pageSizeInt);
		
		//定义ModelAndView，并将参数存入其中
		ModelAndView mav = new ModelAndView("pages/friend_circle");		
		mav.addObject("circles", circles);
		mav.addObject("pageNow",pageNowInt);
		mav.addObject("totalPage", totalPage);		
	
		return mav;
	}
	
	//上一页
	@RequestMapping(value="/prePage")
	public ModelAndView prePage(String pageNow, String totalPage,HttpSession session)
	{
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		
		String userId = loginUserInfo.getUserId();

		//当前页码
		int pageNowInt = Integer.parseInt(pageNow);
		
		//上一页页码
		pageNowInt = (pageNowInt-1)<1 ? 1 : pageNowInt-1;
		
		//偏移量
		int offset = (pageNowInt-1)*pageSizeInt ;
		
		//获取动态
		List<Circle> circles = circleService.getFriendCircle(userId, offset, pageSizeInt);
		
		//定义ModelAndView，并将参数存入其中
		ModelAndView mav = new ModelAndView("pages/friend_circle");		
		mav.addObject("circles", circles);
		mav.addObject("pageNow",pageNowInt);
		mav.addObject("totalPage", totalPage);			

		return mav;
	}
	
	//点赞
	@RequestMapping(value="/addLike",method=RequestMethod.POST)
	@ResponseBody
	public String addLike(HttpServletRequest request, HttpSession session)
	{
		System.out.println("到了点赞的从投入了");
		//后台获取的参数
		String cirLikeId = Utility.getRowId();
		
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		
		String userId = loginUserInfo.getUserId();
		
		
		System.out.println("点赞是获取的从session中userId： "+userId);
		
		String date= Utility.getFormattedCurrentDateAndTime();
		
		//从request中获取的参数
		String circleId = (String) request.getParameter("circleId");
		String ownerId = (String) request.getParameter("ownerId");
		
		
		System.out.println("从post获取的circleId： "+circleId);
		System.out.println("从post获取的ownerI: " + ownerId);
		
		CirLike cirLike=new CirLike();
		
		cirLike.setCirLikeId(cirLikeId);
		cirLike.setLikeUId(userId);
		cirLike.setCircleId(circleId);
		cirLike.setCreateDate(date);
		
		System.out.println("点赞id： "+cirLikeId);
		
		//在点赞表中记录点赞信息
		boolean state=circleService.addLike(cirLike);
		
		//将点赞保存到新评论和点赞表中
		String remindId = Utility.getRowId();
		System.out.println("remindId: "+remindId);
		circleService.addPraiseRemind(remindId, circleId, userId, date,ownerId);
		
		if(state)
		{
			System.out.println("-------------------"+"点赞成功");
			return "{\"resCode\":0,\"resMsg\":\"\",\"data\":{}}";
		}else {
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";
		}
		
	}
	
	
	
	
	//发表动态
	@RequestMapping(value="/publishCircle",method = RequestMethod.POST)
	public ModelAndView publishCircle2(HttpServletRequest request, HttpSession session)
	{
		//需要后台获取的参数
		String circleId = Utility.getRowId();
		String publishDate = Utility.getFormattedCurrentDateAndTime();
		String createDate = publishDate;
		
		// 获取参数
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		String userId = loginUserInfo.getUserId();	
		
		String content = request.getParameter("contentIn");
		String publishAddr = "";		//网页发表的动态不存入发表位置
		String scopeFlag = "";			//暂时没有乐友圈权限控制，所以该字段为空
		String picUrl = "";				//网页发表动态暂时不支持上传图片
		
		//String picUrl = circleService.saveImags((MultipartHttpServletRequest)request, userId);
		
		System.out.println("发表动态时，userId为："+userId);
		System.out.println("发表动态时，content为："+content);
	
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
		
		ModelAndView mav = new ModelAndView("redirect:/webcircle/comeIntoCircle");		
		
		return mav;	
	}
	
	
	
	//转发动态
	@RequestMapping(value="/forwardCircle",method = RequestMethod.POST)
	@ResponseBody
	public String orwardCircle(HttpServletRequest request, HttpSession session)
	{
		//需要后台获取的参数
		String circleId = Utility.getRowId();
		String publishDate = Utility.getFormattedCurrentDateAndTime();
		String createDate = publishDate;
		
		// 获取参数
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		String userId = loginUserInfo.getUserId();	
		
		String content = request.getParameter("contentIn");
		String publishAddr = "";		//网页发表的动态不存入发表位置
		String scopeFlag = "";			//暂时没有乐友圈权限控制，所以该字段为空
		String picUrl = "";				//网页发表动态暂时不支持上传图片
		
		//String picUrl = circleService.saveImags((MultipartHttpServletRequest)request, userId);
		
		System.out.println("转发动态时，userId为："+userId);
		System.out.println("转发动态时，content为："+content);
	
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
			publishCircleJson = Utility.packReturnJson(1, "动态转发失败", "");
		}
		else 
		{
			//动态转发成功时，重新进入乐友圈
			
			publishCircleJson = Utility.packReturnJson(0, "", "");	
		}
		
		return publishCircleJson;	
	}

	
	
	
	//发表评论或回复
	@RequestMapping(value="/publishComment",method=RequestMethod.POST)
	@ResponseBody
	public String publishComment(HttpServletRequest request, HttpSession session)
	{
		
		System.out.println("接受到： "+request.getParameter("content"));		
		
		//需要后台获取的参数
		String commentId = Utility.getRowId();
		String commentTime = Utility.getFormattedCurrentDateAndTime();
		String createDate = Utility.getFormattedCurrentDateAndTime();
		
		//取出当前userId
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		String commentUId = loginUserInfo.getUserId();
		
		System.out.println("发表评论时获取的userId： "+commentUId);
		System.out.println("发表评论时获取的commentId： "+commentId);
		
		//获取的参数
		String circleId = (String) request.getParameter("circleId");
		String content = (String) request.getParameter("content");
		String ownerId  = (String) request.getParameter("ownerId");
		String repliedUserId = (String) request.getParameter("repliedUId");
		
		System.out.println("----------------------------------");
		System.out.println("repliedUserId： "+repliedUserId);
		
		System.out.println("11 circleId"+circleId);
		System.out.println("11 content"+content);
		System.out.println("11 ownerId"+ownerId);
		
	
		
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
			recordCommentAndLike.setOwnerId(ownerId);			//动态的拥有者
			
			//向recordCommentAndLike表插入相应记录
			int effectedRows2 = circleService.addRecordCommentAndLike(recordCommentAndLike);
			
//					publishCommentJson = Utility.packReturnJson(0, "", "");	
			publishCommentJson = "{\"resCode\":0,\"resMsg\":\"\",\"data\":{\"commentId\":\""+commentId+"\"}}";
			
		}
			
		
		//测试性结果输出
		System.out.println("发表评论： "+publishCommentJson);
		
		return publishCommentJson;
	}
	
	

	
	
	//删除评论
	@RequestMapping(value="/deleteComment",method = RequestMethod.POST)
	@ResponseBody
	public String deleteComment(HttpServletRequest request)
	{
		String commentId = (String) request.getParameter("commentId");
		
		System.out.println("删除评论commentId："+commentId);
		
		boolean state=circleService.deleteComment(commentId);
		if(state)
		{
			System.out.println("-------------------"+"取消成功");
			return "{\"resCode\":0,\"resMsg\":\"\",\"data\":{}}";
		}else {
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";
		}
	}
	
	
	//删除动态
	@RequestMapping(value="/deleteCircle",method = RequestMethod.POST)
	@ResponseBody	
	public String deleteCircle(HttpServletRequest request)
	{
		String circleId = (String) request.getParameter("circleId");
		
		System.out.println("删除动circleId态："+circleId);
		
		boolean state=circleService.deleteCircle(circleId);
		
		System.out.println("state: "+state);
		
		if(state)
		{
			System.out.println("-------------------"+"删除动态成功");
			return "{\"resCode\":0,\"resMsg\":\"\",\"data\":{}}";
		}else {
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";
		}
	}
	
	
	
	 //取消点赞
	@RequestMapping(value="/deleteLike", method=RequestMethod.POST )
	@ResponseBody
	public String deletePraise(HttpServletRequest request, HttpSession session)
	{
		//从页面获取circleId
		String circleId = (String) request.getParameter("circleId");
		//从session中获取userId
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		
		String userId = loginUserInfo.getUserId();	
		
		System.out.println("进入取消点赞接口，并获得参数：userId："+userId+" circleId"+circleId);
		
		boolean state=circleService.deletePraise(circleId, userId);
		
		if(state)
		{
			System.out.println("-------------------"+"取消成功");
			return "{\"resCode\":0,\"resMsg\":\"\",\"data\":{}}";
		}else {
			return "{\"resCode\":1,\"resMsg\":\"\",\"data\":{}}";
		}
	}
	
	//==================================================================================================//
	//====================================以下为后台页面controller========================================//
	//==================================================================================================//
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
