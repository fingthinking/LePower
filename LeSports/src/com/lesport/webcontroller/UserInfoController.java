package com.lesport.webcontroller;

import java.util.Date;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lesport.model.UserInfo;
import com.lesport.service.CircleService;
import com.lesport.service.IUserInfoService;
import com.lesport.util.EncodeByMD5;
import com.lesport.util.GetRandNum;
import com.lesport.util.SendEmail;
import com.lesport.util.Utility;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;

/**
 * 
 * @author 曹汝帅
 * @version V.1
 * @描述：控制器，响应页面请求，采用注解的形式与配置文件结合 @日期：2016-03-05 
 * @author 刘衍庆
 * @date 2016年3月15日08:59:58
 *
 *
 */

@Controller
@RequestMapping("/webUserInfo")
public class UserInfoController {

	@Autowired
	private IUserInfoService userInfoService;
	
	@Autowired
	private CircleService circleService;

	/**
	 * @描述： Web端登录方法
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request,HttpSession session) {
		
		String loginName = request.getParameter("loginName");
		String loginPwd =  request.getParameter("loginPwd");

		System.out.println("come into login");

		// 登陆成功时，获取该用户的信息
		UserInfo loginUserInfo = userInfoService.loginWeb(loginName, EncodeByMD5.createPwdToDB(loginPwd));

		String returnString = null;
		
		if (null != loginUserInfo) {
			//将用户信息放入session中			
			session.setAttribute("loginUserInfo", loginUserInfo);
			
			returnString = Utility.packReturnJson(0, "", "");
			
		} 
		else 
		{
			returnString = Utility.packReturnJson(1, "登陆失败", "");
		}

		return returnString;

	}

	/**
	 * @描述： Web端注册方法
	 * 
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value="/register",method= RequestMethod.POST)
	@ResponseBody
	public String register(HttpServletRequest request, HttpSession httpSession)
	{

		System.out.println("come into register");
		
		String userId = Utility.getRowId();
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String nickName = request.getParameter("nickName");
		String password = request.getParameter("password");
		
		//设定默认信息
		String sex = "男";			//web端注册性别默认为男		
		String imgURL = "http://192.168.1.100:8080/LeSports/img/defaultImg.jpg";	//添加默认头像	
		String birthday = "1993-01-01";
		String height = "175";
		String weight = "70";
		String createDate = Utility.getFormattedCurrentDateAndTime();
		
		//生成乐动力号
		String leNum = null;				
		while(true)
		{		
			leNum = Utility.getLeNum();
			
			//验证是否已存在该乐动力号
			int count = userInfoService.isExistLeNum(leNum);
			
			if(count == 0)
			{
				break;
			}
		}
		
		System.out.println("注册时，获取的数据： "+phoneNumber+"---"+email+"----"+nickName+"---"+password);
				
		//如果邮箱不为空，则用邮箱进行注册，此时验证邮箱是否存在
		if (!("".equals(email))) {
			
			int countEmail = userInfoService.isExistEmail(email);
			
			if(countEmail != 0)//邮箱已存在
			{
				 return Utility.packReturnJson(1, "邮箱已存在", "");
			}			
		}
		
		//如果手机号不为空，则用手机号进行注册，此时验证手机号是否存在
		if (!("".equals(phoneNumber))) 
		{			
			int countPhone = userInfoService.isExistPhone(phoneNumber);
			
			if(countPhone != 0)//手机号已存在
			{
				return Utility.packReturnJson(2, "手机号已存在", "");
			}
			
		 }		
		
		//没有重名邮箱或者手机号，建立新用户
		UserInfo registerUserInfo = new UserInfo();		
		registerUserInfo.setUserId(userId);
		registerUserInfo.setNickName(nickName);
		registerUserInfo.setUserPwd(EncodeByMD5.createPwdToDB(password));
		registerUserInfo.setEmail(email);
		registerUserInfo.setPhoneNum(phoneNumber);
		registerUserInfo.setLeNum(leNum);
		
		registerUserInfo.setSex(sex);
		registerUserInfo.setImgURL(imgURL);
		registerUserInfo.setBirthday(birthday);
		registerUserInfo.setHeight(height);
		registerUserInfo.setWeight(weight);
		registerUserInfo.setCreatedDate(createDate);
		registerUserInfo.setSportType("4000");
		
		
		// 添加用户
		userInfoService.register(registerUserInfo);	
		
		String returnString  = Utility.packReturnJson(0, "", "");
		
		System.out.println("returnString:"+returnString);

		return returnString;
	}
	

	// 找回密码时，验证邮箱或者手机号是否存在 2016年3月14日21:43:41
	@RequestMapping(value = "/validatePhoneOrEmail", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String validatePhoneOrEmail(HttpServletRequest request, HttpSession session) {

		System.out.println("come into validate");

		String phoneOrEmial = (String) request.getParameter("phoneOrEmial");

		System.out.println("获取的phoneOrEmial： " + phoneOrEmial);

		if (phoneOrEmial.contains("@")) // 如果包含@符号，则该字符串为邮箱
		{
			int countEmial = userInfoService.isExistEmail(phoneOrEmial);

			if (countEmial == 0) {
				return Utility.packReturnJson(1, "邮箱不存在", "");
			}
			// 邮箱存在，该该邮箱发验证码
			String code = Utility.generateCode(6); // 生成六位随机验证码

			// 将生成的验证码存入session
			session.setAttribute("code", code);

			SendEmail.sendCode(phoneOrEmial, "您的验证码为:" + code);

		} else // 如果不包含@符号，则该字符串为手机
		{
			int countPhone = userInfoService.isExistPhone(phoneOrEmial);

			if (countPhone == 0) {
				return Utility.packReturnJson(1, "手机不存在", "");
			}
			// 手机号存在，给该手机发验证码

		}

		return Utility.packReturnJson(0, "", "");
	}

	// 忘记密码之重设密码
	@RequestMapping(value = "/resetPwdWhenForget")
	public ModelAndView resetPwdWhenForget(HttpServletRequest request, HttpSession session) {

		System.out.println("resetPwdWhenForget");

		String phoneOrEmail = (String) request.getParameter("phoneOrEmailIn"); // 手机号或者邮箱
		String newPassword = (String) request.getParameter("tx_password"); // 新密码
		String getCode = (String) request.getParameter("txt_registersecurity"); // 输入的验证码
		String code = (String) session.getAttribute("code"); // 后台生成的验证码

		ModelAndView mav = new ModelAndView();

		if (!getCode.equals(code)) // 验证码错误
		{
			mav.setViewName("pages/forgetPassword?flag=2");
		}

		String userId = null;

		// 根据手机号码获取用户id
		if (phoneOrEmail.contains("@")) {
			userId = userInfoService.findUserIdByEmail(phoneOrEmail);
		} else {
			userId = userInfoService.findUserIdByPhone(phoneOrEmail);
		}

		// 根据用户id为用户设置新密码
		boolean state = userInfoService.setNewPassword(userId, EncodeByMD5.createPwdToDB(newPassword));

		if (state) // 找回成功
		{
			mav.setViewName("pages/login");
		} else// 找回失败
		{
			mav.setViewName("pages/forgetPassword?flag=1");
		}
		return mav;
	}

	
	//退出系统
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session)
	{
		//删除该用户在session中的uerId
		session.removeAttribute("loginUserInfo");
		
		ModelAndView mav = new ModelAndView("pages/index");	
		
		System.out.println("已退出");
		
		return mav;
	}
	
	
	//修改密码（用户登录后才能操作）
	@RequestMapping("/updatePassword")
	@ResponseBody
	public String updatePassword(HttpServletRequest request, HttpSession session)
	{
		//从session中获取userId
		
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		String userId = loginUserInfo.getUserId();
		
		
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");	
		
		//验证原密码是否正确
		String getUserId = userInfoService.checkUserIdAndUserPwd(userId, EncodeByMD5.createPwdToDB(oldPassword));
		
		boolean isUpdateSuccess = false;
		
		String returnString = null;
		
		if(userId.equals(getUserId))//原密码正确
		{
			isUpdateSuccess = userInfoService.setNewPassword(userId, EncodeByMD5.createPwdToDB(newPassword));	
			
			if(isUpdateSuccess)//密码修改成功
			{
				returnString = Utility.packReturnJson(0, "密码修改成功", "");
				
				//密码修改成功，重新登录----》清除session信息
				session.removeAttribute("loginUserInfo");	
			}
			else//密码修改失败
			{
				returnString = Utility.packReturnJson(1, "密码修改失败", "");
			}		
		}
		else//原密码错误
		{
			returnString = Utility.packReturnJson(1, "原密码错误", "");
		}	
		
		return returnString;
		
	}
	
	
	//注册时验证邮箱是否存在
	@RequestMapping("/validateEmail")
	@ResponseBody
	public String validateEmail(HttpServletRequest request)
	{
		String email = request.getParameter("email");
		
		System.out.println("进入验证函数： "+email);
		
		int count = userInfoService.isExistEmail(email);
		
		System.out.println("count: "+count);
		
		String returnString = null;
		
		if(count == 0)//邮箱目前不存在
		{
			returnString=  Utility.packReturnJson(0, "", "");
		}
		else //邮箱已存在
		{
			returnString=  Utility.packReturnJson(1, "邮箱已存在", "");
		}
		
		return returnString;
	}
	
	
	
	//注册时验证手机是否存在
	@RequestMapping("/validatePhone")
	@ResponseBody	
	public String validatePhone()
	{
		
		return "";
	}
		

	@RequestMapping("/getVerCodeByEmailWeb")
	public void getVerCodeByEmail(String email, HttpServletRequest request) {
		// 第一步：对参数（邮箱）进行格式验证，确保是真实有效的邮箱
		if (!SendEmail.checkEmail(email)) {
			System.out.println("邮箱格式错误，对于Web端的邮箱格式论证，初步认定为在jsp页面中通过JS实现");
		}
		// 第二步：调用生成验证码函数，获得验证码
		String verCode = GetRandNum.getRandNum();

		// 第三步：将验证码作为邮件信息发送到用户邮箱
		SendEmail.send(email, verCode);
		System.out.println("验证码已经发送到邮箱，请接收查看");
	}
	
	
	//---------------------------------------------------------------------------------//
	//-----------------------------以下是邱志国同学所写-----------------------------------//
	//----------------------------------------------------------------------------------//
	
	
	@RequestMapping("/updateUserInfo")
	public ModelAndView updateUserInfo(String userId, String nickName, String gender, String phoneNum, String emailNum,
			String birthday, String weiboNum, String qqNum, String province, String city,String src, HttpSession session) throws Exception {
		String sex = "";
		System.out.println("imageUrl是：" + src);
		System.out.println("userId是：" + userId);
		System.out.println("nickName是：" + nickName);
		// System.out.println(gender);
		if (gender != null) {
			if (gender.length() == 3) {// 传递过来的性别为男
				System.out.println("gender是：" + "男");
				sex = "男";
			} else {
				System.out.println("gender是：" + "女");
				sex = "女";
			}
		}
		System.out.println("phoneNum是：" + phoneNum);
		System.out.println("emailNum是：" + emailNum);
		System.out.println("birthday是：" + birthday);
		System.out.println("weiboNum是：" + weiboNum);
		System.out.println("qqNum是：" + qqNum);
		System.out.println("province是：" + province);
		System.out.println("city是：" + city);

		UserInfo userInfo = userInfoService.findUserInfoById(userId);
		userInfo.setNickName(nickName);
		userInfo.setSex(sex);
		userInfo.setPhoneNum(phoneNum);
		userInfo.setEmail(emailNum);
		userInfo.setBirthday(birthday);
		userInfo.setWeiboNum(weiboNum);
		userInfo.setQqNum(qqNum);
		userInfo.setProvince(province);
		userInfo.setCity(city);
		userInfoService.updateUserInfoFromPersonalCenter(userInfo);
		
		//更新成功，修改session信息
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		
		loginUserInfo.setNickName(nickName);
		loginUserInfo.setSex(sex);
		loginUserInfo.setBirthday(birthday);
		loginUserInfo.setWeiboNum(weiboNum);
		loginUserInfo.setQqNum(qqNum);
		loginUserInfo.setProvince(province);
		loginUserInfo.setCity(city);
		//修改后存回session 
		session.setAttribute("loginUserInfo", loginUserInfo);
		
		System.out.println("userInfo是：" + userInfo);
		ModelAndView modelAndView = new ModelAndView("redirect:../webUserInfo/showUserInfo");
		return modelAndView;
	}
	
	@RequestMapping("/showUserInfo")
	public ModelAndView showUserInfo(HttpSession session){
			
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		
		String userId = loginUserInfo.getUserId();
		
		UserInfo userInfo = userInfoService.findUserInfoById(userId);
		
		System.out.println("输出imgurl: "+userInfo.getImgURL());
		
		String birthday = userInfo.getBirthday();
		System.out.println("birthday:" + birthday);
		if (birthday != null) {
			birthday = birthday.substring(0, 10);	
		}
		System.out.println(birthday);
		userInfo.setBirthday(birthday);
		System.out.println(userInfo);
		ModelAndView modelAndView = new ModelAndView("pages/personal_center");
		modelAndView.addObject("user", userInfo);
		return modelAndView;
	}
	
	
	//个人中心之上传头像
	@RequestMapping(value="/updateImg", method=RequestMethod.POST)
	@ResponseBody
	public String updateImg(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	{
		//获取用户id
		UserInfo loginUserInfo = (UserInfo) session.getAttribute("loginUserInfo");
		String userId = loginUserInfo.getUserId();
		
		System.out.println("收到webimg请求："+new Date().toString());
		
		String imgUrl = circleService.saveImags((MultipartHttpServletRequest)request,"");
		
		imgUrl = imgUrl.substring(0,imgUrl.length()-1); //将结尾多余的星号删除		
		
		String prePath = "http://192.168.1.100:8080/LeSports"; //为了APP端能够获取，需要加上服务器地址和项目名称
		
		imgUrl = prePath+ imgUrl;		
		
		int count = userInfoService.updateImgUrl(imgUrl, userId);	
		
		System.out.println("count : "+count);
		
		if(count == 0)
		{
			return Utility.packReturnJson(1, "", "");
		}
		else//修改成功，同时更新session中的头像信息
		{
			loginUserInfo.setImgURL(imgUrl);
			session.setAttribute("loginUserInfo", loginUserInfo);		
			
			return Utility.packReturnJson(0, "", "");
		}
		
	}
	
	
	
	
	
}
