package com.lesport.test;

/**
 * 
* @ClassName: NewsMapper 
* @Description: news测试
* @author Qinhaitao
* @date 2016年3月10日 上午10:10:50 
*
 */
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.lesport.service.NewsService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/spring-mvc.xml","classpath*:config/spring-common.xml",
"classpath*:config/mybatis-config.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("config/spring-common.xml")
public class NewsTest{
	
	@Autowired
	private NewsService newsService;
	//@Resource
	//private NewsMapper newsmapper;

	@Test
	public void testAdd() throws Exception{
		System.out.println( newsService.addNews("2","大","家","2016-01-01 15:29:53","2016-01-01 15:29:53","2016-01-01 15:29:53",new Date().toLocaleString()));
		//newsmapper.addNews("20","大","家","2016-01-01 15:29:53","2016-01-01 15:29:53","2016-01-01 15:29:53",new Date().toLocaleString());;
	}
	
	@Test
    public void   testGetHealthyInfo()throws Exception{
		//return "\"resCode\":0,\"resMsg\":\"\",\"data\":"+JSONSerializer.toJSON(allNews);
		System.out.println(newsService.getHealthyInfo("12"));
	}
	
	@Test
	public void testUpdate() throws Exception {

		System.out.println(newsService.updateNews("1","我好","http://10.6.11.23/e/photos/you.jpg","大家好","2016-01-02 15:29:53","2016-01-03 15:29:53","2016-01-05 15:29:53"));
		
	}
	@Test
	public void testDelete() throws Exception{
		System.out.println(newsService.deleteNews("17"));
	}
	
	@Test
    public void testGetNews() throws Exception{
		System.out.println(newsService.getNews("2016-03-09 15:29:53", "2016-03-14 15:29:53"));
	}
	
	@Test
	public void testFindAllNews() throws Exception{	
		System.out.println(newsService.findAllNews());
	}
//	@Test
//	public void testFindById() throws Exception{
//		News News = new News();
//		News = NewsMapper.findNewsById("m02");
//		
//		System.out.println(News);
//	}
//	
//	

}
