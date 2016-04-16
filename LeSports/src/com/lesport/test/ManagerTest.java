package com.lesport.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lesport.mapper.ManagerMapper;
import com.lesport.model.Manager;
import com.lesport.service.ManagerService;
import com.lesport.service.impl.ManagerServiceImpl;
import com.lesport.util.Utility;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/spring-mvc.xml","classpath*:config/spring-common.xml",
"classpath*:config/mybatis-config.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("config/spring-common.xml")
public class ManagerTest{
	
	@Autowired
	private ManagerService managerMapper;

	@Test
	public void testInsert() throws Exception{
	//	System.out.println(managerMapper.insertManager("ffff","fffff","3333"));
	}

	
	@Test
	public void testDelete() throws Exception{
		System.out.println(managerMapper.deleteManager("666"));
	}
	
	@Test
	public void testFindById() throws Exception{
		System.out.println(managerMapper.findManagerById("m02"));
	}
	
	
	@Test
	public void testFindAll() throws Exception{
//		List<Manager> allManager = new ArrayList<>();
//		allManager = managerMapper.findAllManager();
		System.out.println(managerMapper.findAllManager().size());
		System.out.println(managerMapper.findAllManager());
	}
}
