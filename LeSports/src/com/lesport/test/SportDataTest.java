package com.lesport.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lesport.mapper.SportDataMapper;
import com.lesport.mapper.SysManageMapper;
import com.lesport.model.ManageCircle;
import com.lesport.model.UserInfo;
import com.lesport.util.Utility;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/spring-mvc.xml","classpath*:config/spring-common.xml",
"classpath*:config/mybatis-config.xml"})
public class SportDataTest {
	
	@Autowired
	private SportDataMapper mapper;
	
	//测试查看动态信息接口 getCircleInfo
	@Test 
	public void testGetRunRange()
	{		
		String userId=null;
		String beginTime=null;
		String endTime=null;
		System.out.println("gdg");
		List<UserInfo> dai=mapper.getRunRange("2016-03-14");//mapper.getRunDis("1", "2016-03-14");
		System.out.println(dai.size());
		//List<ManageCircle> circles= mapper.getCircleInfo(userId, beginTime, endTime);
		
		//System.out.println(Utility.packReturnJson(1, "", circles));
	}
	
	
}
