package com.lesport.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesport.model.UserInfo;
import com.lesport.service.IUserInfoService;
import com.lesport.util.Utility;

/**
 * 
 * @author 曹汝帅
 * @描述：测试日期格式转换
 *
 */
@Controller
@RequestMapping("/DateChange")
public class DateChange {
	
	@Autowired
	private IUserInfoService userInfoService;
	
	// 出生日期格式转换：

	@RequestMapping("/testDate")
	@ResponseBody
	public String dateChange() {
		String birthday = "2012年03月23日";
		String birthdayTemp = birthday.replace("年", "-").replace("月", "-").replace("日", "");
		System.out.println(birthdayTemp);

		// 生成UserInfo实体对象，进行参数传值
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(Utility.getRowId());
		userInfo.setUserName("234234234");
		userInfo.setUserPwd("101011");
		userInfo.setNickName("yyyyy23");
//		userInfo.setImgURL(imgURL);
//		userInfo.setSex(sex);
		userInfo.setBirthday(birthdayTemp);
		userInfo.setCity("苏州");
		userInfoService.register(userInfo);
		return "hello world";
	}

}
