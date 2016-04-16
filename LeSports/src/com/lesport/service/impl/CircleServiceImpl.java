package com.lesport.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lesport.mapper.CircleMapper;
import com.lesport.model.CirLike;
import com.lesport.model.Circle;
import com.lesport.model.Comment;
import com.lesport.model.CommentLike;
import com.lesport.model.RecordCommentAndLike;
import com.lesport.service.CircleService;

import net.sf.json.JSONSerializer;

@Service
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class CircleServiceImpl implements CircleService{

	@Resource
	private CircleMapper mapper;

	//获取好友动态列表，乐友圈接口1
	@Override
	public List<Circle> getFriendCircle(String userId, int offset, int pageSizeInt) {

		List<Circle> circles = mapper.getFriendCircle(userId,offset, pageSizeInt);		
		
		return circles;		
		
	}

	//获取动态详情，乐友圈接口2
	@Override
	public Circle getCircleDetail(String circleId) {

		Circle circle = mapper.getCircleDetail(circleId);
		
		return circle;
	}

	//获取我的动态，乐友圈接口3
	@Override
	public List<Circle> getMyCircle(String userId,int offset, int pageSizeInt) {
		
		List<Circle> circles = mapper.getMyCircle(userId,offset,pageSizeInt);
		
		return circles;
	}

	
	//发表动态，乐友圈接口4
	@Override
	public int addCircle(Circle circle) {
		
		int effectedRows = mapper.addCircle(circle);	
		
		
		return effectedRows;
	}

	//发表评论，乐友圈接口5
	@Override
	public int addComment(Comment comment) {
		
		int effectedRows = mapper.addComment(comment);		
		
		return effectedRows;
	}
	
	
	//当用户发表评论时，同时向le_record_commentandlike表插入相应的记录信息
	@Override
	public int addRecordCommentAndLike(RecordCommentAndLike recordCommentAndLike) {
		
		int effectedRows = mapper.addRecordCommentAndLike(recordCommentAndLike);
		
		return effectedRows;
	}

	//获取新的评论和点赞的数量，乐友圈接口10
	@Override
	public int getCountOfComAndLike(String userId) {

		int countOfNew = mapper.getCountOfComAndLike(userId);
		
		return countOfNew;
	}
	
	
	//接收图片，用于头像和动态的图片上传
	@Override
	public String saveImags(MultipartHttpServletRequest request, String userId) {

		//文件存放目录
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		
		//获取文件Map
		Map<String,MultipartFile>  files = request.getFileMap();
		
		String wholePath="";
		
		for (Map.Entry<String, MultipartFile>  file: files.entrySet()) {
			
			String prePath = "/resources/upload/";
			
			//获取文件名
			String originalName = file.getValue().getOriginalFilename();
			
			//获取图片后缀名
			String suffix = originalName.substring(originalName.lastIndexOf(".") + 1);
			
			//生成新的图片名称
			String newName=userId+System.currentTimeMillis()+"."+suffix;
			
			File newFile=new File(path+"/"+newName);				
			
			String  newPath = prePath+ newName+"*";
			
			wholePath += newPath;
			
			try {
				FileUtils.copyInputStreamToFile(file.getValue().getInputStream(),newFile);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		
		//生成存入数据库路径
		
		
		return wholePath;
	}	
	
	
	
	
	//获取某个userId的所有动态数量
	@Override
	public int getCountOfAllcircle(String userId) {
		return mapper.getCountOfAllcircle(userId);
	}

	
	//获取某个userId本人及好友所有的动态数量
	@Override
	public int getCountOfMineAndFriend(String userId) {
		return mapper.getCountOfMineAndFriend(userId);
	}
	
		
	
	/*--------------------------以下为刘亚中所写-----------------------------------------*/
		
	@Override
	public Circle getToDispathSurge(String circleId) {
		// TODO Auto-generated method stub
		Circle circleSurge=mapper.getToDispathSurge(circleId);
		//System.out.println(JSONSerializer.toJSON(circleSurge));
		return circleSurge;
	}

	@Override
	public boolean saveDispathSurge(Circle circle) {
		// TODO Auto-generated method stub
		System.out.println(JSONSerializer.toJSON(circle));
       return mapper.saveDispathSurge(circle);
	}

	@Override
	public boolean deletePraise(String circleId, String userId) {
		// TODO Auto-generated method stub
		//System.out.println("+++++++++++++++++++"+circleId+","+userId);
		boolean state=mapper.deletePraise(circleId, userId);
		return state;
	}

	@Override
	public boolean deleteComment(String commentId) {
		// TODO Auto-generated method stub
//		System.out.println("+++++++++++++++++++"+commentId);
		boolean state=mapper.deleteComment(commentId);
		return state;
	}

	@Override
	public boolean deleteCircle(String circleId) {
		// TODO Auto-generated method stub
		System.out.println("+++++++++++++++++++"+circleId);
		boolean state=mapper.deleteCircle(circleId);
		System.out.println(state);
		return state;
	}

	public boolean addLike(CirLike cirLike) {
		// TODO Auto-generated method stub
		boolean state=mapper.addLike(cirLike);
		return state;
	}

	@Override
	public boolean addPraiseRemind(String rowId, String circleId,String userId,String publishDate,String ownerId) {
		// TODO Auto-generated method stub
		boolean state=mapper.addPraiseRemind(rowId,circleId, userId,publishDate,ownerId);
		return state;
	}

	@Override
	public List<CommentLike> getContentOfCirAndLike(String userId) {
		// TODO Auto-generated method stub
		System.out.println("++++++++++"+userId);
		List<CommentLike> commentLikes=mapper.getContentOfCirAndLike(userId);
		return commentLikes;

	}	
	
	public boolean setSawFlag(String userId)
	{
		boolean state=mapper.setSawFlag(userId);
		return state;		

	}



	
		

	
}
