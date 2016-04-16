package com.lesport.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lesport.service.ComAndLikeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/spring-mvc.xml","classpath*:config/spring-common.xml",
"classpath*:config/mybatis-config.xml"})
public class ComAndLikeTest {

	@Autowired
	private ComAndLikeService comAndLikeService;
	
	@Test
	public void testComAndLikeService(){
		System.out.println(comAndLikeService.getCommonAndLike("011"));
	}
}
