package com.lesport.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lesport.mapper.IUserInfoMapper;
import com.lesport.model.UserInfo;
import com.lesport.service.IUserInfoService;
import com.lesport.service.ManagerService;
import com.lesport.util.Utility;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/spring-mvc.xml","classpath*:config/spring-common.xml",
"classpath*:config/mybatis-config.xml"})
public class UserInfoTest {

	@Autowired
	private IUserInfoService userInfo;
	@Test
	public void getUserInfoById() throws Exception{
		System.out.println(userInfo.findUserInfoById("1111111"));
	}
	
	@Test
	public void updateUserInfo() throws Exception{
		UserInfo user = new UserInfo();
		user.setUserId("newtest");
		user.setNickName("newtest");
		user.setSex("1");
		user.setPhoneNum("54464645");
		user.setEmail("34646464");
		user.setBirthday("2012-03-23 00:00:00");
		user.setWeiboNum("dsfsdfsfsdf");
		user.setQqNum("24436");
		user.setProvince("江苏");
		user.setCity("南京");
		user.setImgURL("");
		userInfo.updateUserInfoFromPersonalCenter(user);
		
	}
	
//	@Test
//	public void getUserInfo() throws Exception{
//		System.out.println(userInfo.getUserByName("yyyyy").size());
//		System.out.println(userInfo.getUserByName("yyyyy"));
//	}
//	
	
//	@Test
//	public void getAllUserInfo(){
//		System.out.println(userInfo.getAllUserInfo().size());
//		System.out.println(userInfo.getAllUserInfo());
//	}
}
