package com.lesport.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.taglibs.standard.lang.jstl.BooleanLiteral;
import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lesport.appcontroller.ACircleController;
import com.lesport.service.CircleService;
import com.lesport.util.SendEmail;
import com.lesport.util.Utility;

import net.sf.json.JSONObject;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/spring-mvc.xml","classpath*:config/spring-common.xml",
"classpath*:config/mybatis-config.xml"})
public class ACircleControllerTest {
	
	@Autowired
	private ACircleController aCircleController;
	
	@Autowired
	private CircleService circleService;
	
	//测试获取好友列表
	@Test
	public void testGetFriendCircle() throws IOException
	{
			
		/*String userId= "123";
        String pageSize = "1";
        String pageNow = "2";
        
        URL url = new URL("http://localhost:8080/circle/getFriendCircle");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 提交模式haha
        conn.setDoOutput(true);// 是否输入参数liuyanqishihuaidan

        StringBuffer params = new StringBuffer();
        // 表单参数与get形式一样
        params.append("userId").append("=").append(userId).append("&")
              .append("pageSize").append("=").append(pageSize).append("&")
              .append("pageNow").append("=").append(pageNow);
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        InputStream inStream=conn.getInputStream();*/
		
		
		aCircleController.getFriendCircle2("123", "1", "40");
	
	}
	
	
	//测试获取动态详情
	@Test
	public void testGetCircleDetail()
	{		
		aCircleController.getCircleDetail("009");
	}
	
	//测试获取动态详情
	@Test
	public void testGetMyCircle()
	{		
		/*aCircleController.getMyCircle("456");*/
	}
	
	//测试发表动态
	@Test
	public void testPublishCircle()
	{	
		
		String userId = "c56";
		String content = "动态c56222";
		String picUrl = "picc56";
		String publishAddr = "江苏省苏州市";
		String scopeFlag = "";
		
		aCircleController.publishCircle(userId,content,publishAddr,scopeFlag);
		
	}
	
	
	//测试发表评论
	@Test
	public void testPublishComment()
	{	
		String circleId = "015";
		String commentUId = "c56";
		String content = "pinglunneirong00099999";
		String repliedUserId = "";		
		String ownerId = "c56";
		aCircleController.publishComment(circleId, commentUId, content, repliedUserId,ownerId);
	}
	
	
	//测试获取新的评论和点赞数量
	@Test
	public void getCountOfComAndLike()
	{
		String userId = "c56";
		
		aCircleController.getCountOfComAndLike(userId);
	}
	
	@Test
	public void otherTest()
	{		
		JSONObject object = new JSONObject();
		
		object.put("resCode", 0);
		object.put("resMsg", "rt");
		
		String rt = "{\"count\":"+1+"}";
		
		object.put("data",rt);
		
		System.out.println(object.toString());	
	}
	
	@Test
	public void testDeleteCircle2()
	{
		String circleId = "f37833d7c6334450860cbb88542867e3"; 
		boolean getState = circleService.deleteCircle(circleId);
		
		System.out.println("state: "+getState);
		
	}

	
	@Test
	public void testData()
	{
		System.out.println(Utility.packReturnJson(0, "fg", ""));
		
	}
	
	@Test
	public void testSendCode()
	{
		SendEmail.sendCode("793586443@qq.com", "您的验证码为:123476");
		
	}
	
	@Test
	public void testDigit()
	{
		for (int i = 0; i < 6; i++) {
			
		
			System.out.println(Utility.generateCode(6));
		}
		
	}


	
	/*------------------------------以下为刘亚中所写---------------------------------------------*/

	@Test
	public void testdispathSurge()
	{
		aCircleController.dispathSurge("009", "456", "645", "henan","1");
	}
	@Test
	public void testDeletePraise()
	{
	
		aCircleController.deletePraise("009", "a12");
	}
	@Test
	public void testDeleteComment()
	{
		aCircleController.deleteComment("c001");
	}
	@Test
	public void testDeleteCircle()
	{
		aCircleController.deleteCircle("009");
	}
//	@Test
	/*public void testAddLike()
	{
		//aCircleController.addLike("010", "a12");
	}*/
	@Test 
	public void testGetCommentLike()
	{
		aCircleController.getContentOfCirAndLike("c56");
	}
	
	

}
