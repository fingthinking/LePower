package com.lesport.appcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesport.model.CaoStuTemp;
import com.lesport.model.UserInfo;
import com.lesport.service.IUserInfoService;
import com.lesport.util.DateChange;
import com.lesport.util.EncodeByMD5;
import com.lesport.util.GetRandNum;
import com.lesport.util.SendEmail;
import com.lesport.util.Utility;

import net.sf.json.JSONObject;
import sun.awt.dnd.SunDragSourceContextPeer;

/**
 * 
 * @author 曹汝帅
 * @描述：响应APP端的服务请求
 * @version V.1 @时间：2016-03-07
 *
 */

@Controller
@RequestMapping("/userInfo")
public class AUserInfoController {

	@Autowired
	private IUserInfoService userInfoService;

	/**
	 * @描述：判断用户是否已经存在【通过】
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/isExistUser", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String isExistUser(String userName) {
		// 在注册之前应该判断用户是否已经存在
		String userFlag = userInfoService.isExistUser(userName);
		if (userFlag == null) {
			// 如果为空，说明用户并不存在，方可以进行注册
			String str = "{\"resCode\":\"0\",\"resMsg\":\"用户名不存在，可以注册\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户已存在，请登录或更换用户名\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		}
	}
	
	//另加
	
	/**
	 * @描述：判断用户是否已经存在【通过】LIU
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/isExistPhone", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String isExistPhone(String userName) {
		// 在注册之前应该判断用户是否已经存在
		int userFlag = userInfoService.isExistPhone(userName);
		if (userFlag == 0) {
			// 如果为空，说明用户并不存在，方可以进行注册
			String str = "{\"resCode\":\"0\",\"resMsg\":\"用户名不存在，可以注册\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} 
		
		if (userFlag == 1) {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户已存在，请更换手机号\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		}
		return "";
	}
	
	/**
	 * @描述：判断用户是否已经存在【通过】 LIU
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/isExistEmail", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String isExistEmail(String userName) {
		// 在注册之前应该判断用户是否已经存在
		int userFlag = userInfoService.isExistEmail(userName);
		if (userFlag == 0) {
			// 如果为空，说明用户并不存在，方可以进行注册
			String str = "{\"resCode\":\"0\",\"resMsg\":\"用户名不存在，可以注册\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} 
		if (userFlag == 1) {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户已存在，请更换邮箱\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		}
		return "";
	}
	
	
	
	

	/**
	 * 
	 * @param phoneNum
	 * @param email
	 * @param userPwd
	 * @param nickName
	 * @param imgURL
	 * @param height
	 * @param weight
	 * @param sex
	 * @param birthday
	 * @param qqNum
	 * @param weiboNum
	 * @param province
	 * @param city
	 * @return
	 */
	@RequestMapping(value = "/register", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String register(String phoneNum, String email, String userPwd, String nickName, String imgURL, String height,
			String weight, String sex, String birthday, String qqNum, String weiboNum, String province, String city) {

		System.out.println("this is register running...");
		
		System.out.println("yzhcueshi， 邮箱： "+email);
		System.out.println("yzhcueshi，手机： "+phoneNum);
		System.out.println("传递过来的性别是" + sex);
		
		System.out.println("性别转换后是：" + sex);
		System.out.println("传递过来的微博是" + weiboNum);
		System.out.println("传递过来的QQ号为： " + qqNum);
		
		
		int emailCount = userInfoService.isExistEmail(email);
		int phoneCount = userInfoService.isExistPhone(phoneNum);
		
		//此处加入对手机和邮箱是否存在的判断，只有当没有注册时才可以进行注册
		
		if(emailCount != 0)
		{
			String str = "{\"resCode\":\"1\",\"resMsg\":\"邮箱已存在\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
			
		}
		else if(phoneCount != 0)
		{
			String str = "{\"resCode\":\"1\",\"resMsg\":\"手机号已存在\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
			
		}			
		
		System.out.println("the birthday is " + birthday);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(Utility.getRowId());
		userInfo.setUserPwd(EncodeByMD5.createPwdToDB(userPwd));	//对用户密码进行加密后，放可以存入数据库，保证用户数据安全
		userInfo.setNickName(nickName);
		userInfo.setImgURL(imgURL);
		userInfo.setSex(sex);
		userInfo.setPhoneNum(phoneNum);
		userInfo.setEmail(email);
		userInfo.setHeight(height);
		userInfo.setWeight(weight);
		userInfo.setWeiboNum(weiboNum);
		userInfo.setQqNum(qqNum);
		userInfo.setBirthday(birthday);
		userInfo.setProvince(province);
		userInfo.setCity(city);
		
		userInfo.setSportType("4000");
		userInfo.setCreatedDate(Utility.getFormattedCurrentDateAndTime());
		//创建生成乐动力号leNum
		String leNumTemp = null;
		boolean flag = true;
		while(flag) {
			leNumTemp = Utility.getLeNum();		//获得乐动力号
			int count = userInfoService.isExistLeNum(leNumTemp);		//查询是否存在乐动力号，因为其生成是随机的。
			
			System.out.println("正式测试： leNUm:"+count);
			
			if(count == 0) {
				flag = false;
			}
		}
		userInfo.setLeNum(leNumTemp);
		System.out.println("生成的乐动力号为：" + userInfo.getLeNum());
		System.out.println("加密后用户的密码为：" + userInfo.getUserPwd());
		
		System.out.println(city);
		System.out.println("the image is : " + userInfo.getImgURL());
		String returnString = null;
		
		if (phoneNum != null) {
			userInfo.setUserName(phoneNum);
			// 注册成功后需要查询到用户当注册的记录返回给用户
			returnString =  registerByPhone(userInfo);

		} else if (email != null) {
			userInfo.setUserName(email);
			returnString =  registerByEmail(userInfo);
		}
		
		return returnString;

	}

	public String registerByPhone(UserInfo userInfo) {

		System.out.println("this is registerByPhone running。。。。 " + userInfo.getUserId() + "  " + userInfo.getUserName()
				+ "  " + userInfo.getUserPwd() + " " + userInfo.getBirthday() + " " + userInfo.getCity());
		// 在注册之前应该判断用户是否已经存在
		String userFlag = userInfoService.isExistUser(userInfo.getPhoneNum());
		if (userFlag == null) {
			// 如果为空，说明用户并不存在，方可以进行注册
			userInfoService.register(userInfo);
			// 注册成功，在这个位置从数据库拉出刚才用户注册（插入数据库）时的用户信息
			UserInfo userMes = userInfoService.getUserInfo(userInfo.getUserName());
			
			String str = "{\"resCode\":\"0\",\"resMsg\":\"注册成功success\"}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户已存在，请登录或更换用户名\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		}

	}

	public String registerByEmail(UserInfo userInfo) {

		System.out.println("this is registerByEmail running.....");
		// 在注册之前应该判断用户是否已经存在
		String userFlag = userInfoService.isExistUser(userInfo.getEmail());
		if (userFlag == null) {
			// 如果为空，说明用户并不存在，方可以进行注册
			userInfoService.register(userInfo);
			UserInfo userMes = userInfoService.getUserInfo(userInfo.getUserName());
			String str = "{\"resCode\":\"0\",\"resMsg\":\"注册成功success\"}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			jsonStr.put("data", userMes);
			
			System.out.println("邮箱注册返回： "+jsonStr.toString());
			
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户已存在，请登录或更换用户名\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			
			System.out.println("邮箱注册返回： "+jsonStr.toString());
			
			return jsonStr.toString();
			// return jsonStr;
		}
	}

	/**
	 * 用少量字段类进行测试注册接口
	 */
	@RequestMapping("/caoStuTemp")
	@ResponseBody
	public String caoStuTemp(String str1, String str3) {
		CaoStuTemp crsh = new CaoStuTemp();
		crsh.setStr1(str1);
		crsh.setStr3(str3);
		System.out.println("this is caoStuTemp");
		System.out.println(crsh.getStr1());
		// 判断用户是否已经存在
		String userId = userInfoService.isExistStu(crsh.getStr1());
		if (userId == null) {
			System.out.println("用户没有注册，然后放可以注册");
			userInfoService.caoStuTemp(crsh);
			System.out.println("----已更新数据库----");
			String str = "{\"resCode\":\"0\",\"resMsg\":\"注册成功success\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		} else {
			System.out.println("用户已经存在，请登录");
			String str = "{\"resCode\":\"0\",\"resMsg\":\"用户已存在，请登录或更换用户名\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}

	}

	/**
	 * @描述：用户登录
	 * @param userName
	 * @param userPwd
	 * @return json数据，传递给APP端
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String login(String userName, String userPwd) {

		System.out.println("this is login running    ");
		System.out.println(userName+"----"+userPwd);
//		UserInfo loginUserInfo = userInfoService.login(userName,userPwd);
		UserInfo loginUserInfo = userInfoService.login(userName,EncodeByMD5.createPwdToDB(userPwd));
		System.out.println("加密后的用户密码为----"+EncodeByMD5.createPwdToDB(userPwd));
		if (loginUserInfo != null) {
			String str = "{\"resCode\":\"0\",\"resMsg\":\"登录成功，欢迎进入\"}";
			JSONObject json = JSONObject.fromObject(str);
			json.put("data", loginUserInfo);
			System.out.println(json);
			return json.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户名或密码不正确\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		}
	}

	/**
	 * @描述：通过手机找回密码
	 * @param phoneNum
	 * @param userPwd
	 * @return json数据，传递给APP端
	 */
	@RequestMapping(value = "/findPasswordByPhone", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findPasswordByPhone(String phoneNum, String userPwd) {
		// 先通过手机号查到用户ID，在这个位置调用findUserIdByPhone方法，返回String类型（因为用户ID为string）
		// 第二步：通过用户ID来找回密码【可以直接更新数据库，进行密码修改，无需返回实体对象，只需要通过APP端密码找回状态（成功/失败）】
		// 注：这里的“修改密码”与另一个修改密码不同，因为用户此时时忘记密码的状态，无需审核旧密码。
		// 另外：APP端设置并实现验证码，所以提交过来的是已经被审核过的信息
		System.out.println("this is findPasswordByPhone running");
		System.out.println("the phoneNum is :" + phoneNum);
		String userId = userInfoService.findUserIdByPhone(phoneNum);
		System.out.println("获得到的用户ID：" + userId);
		if (userId == null) {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"手机号不存在\",\"data\":{}}";
			System.out.println("the result is :   ==   " + str);
			return str;
		} else {
			boolean flag = userInfoService.setNewPassword(userId,EncodeByMD5.createPwdToDB(userPwd)); // 更新新密码，至于返回值，暂时设为布尔类型
			if (flag == true) {
				String str = "{\"resCode\":\"0\",\"resMsg\":\"密码已找回，请重新登录\"}";

				UserInfo userMes = userInfoService.getUserInfo(userId);
				JSONObject jsonStr = JSONObject.fromObject(str);
				// 把用户信息拉出来，返回给用户
				jsonStr.put("data", userMes);
				System.out.println(jsonStr.toString());

				return jsonStr.toString();
			}
			String str = "{\"resCode\":\"1\",\"resMsg\":\"密码找回失败，请稍后重试或用绑定的邮箱找回\",\"data\":{}}";
			System.out.println("the result is :   ==   " + str);
			return str;
		}
	}

	/**
	 * @描述：通过邮箱找回密码
	 * @param email
	 * @param userPwd
	 * @return json数据，传递给APP端
	 */
	@RequestMapping(value = "/findPasswordByEmail", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findPasswordByEmail(String email, String userPwd) {

		System.out.println("this is findPasswordByEmail running");
		String userId = userInfoService.findUserIdByEmail(email);
		// System.out.println("获得到的用户ID：" + userId);
		if (userId == null) {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"邮箱不存在\",\"data\":{}}";
			System.out.println(str);
			return str;
		} else {
			boolean flag = userInfoService.setNewPassword(userId,EncodeByMD5.createPwdToDB(userPwd)); // 更新新密码，至于返回值，暂时设为布尔类型
			if (flag == true) {
				String str = "{\"resCode\":\"0\",\"resMsg\":\"密码已找回，请重新登录\"}";

				UserInfo userMes = userInfoService.getUserInfo(userId);
				JSONObject jsonStr = JSONObject.fromObject(str);
				// 把用户信息拉出来，返回给用户
				jsonStr.put("data", userMes);
				System.out.println(jsonStr.toString());

				return jsonStr.toString();
			}
			String str = "{\"resCode\":\"1\",\"resMsg\":\"密码找回失败，请稍后重试或用绑定的手机号找回\",\"data\":{}}";
			System.out.println(str);
			return str;
		}
	}

	/**
	 * @描述：用户登录进去后进行修改密码操作
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 * @return json数据，传递给APP端
	 */
	@RequestMapping(value="/setNewPassword", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String setNewPassword(String userId, String oldPwd, String newPwd) {
		// 第一步：先通过checkUserIdAndUserPwd方法进行用户ID和旧密码匹配，返回用户ID
		// 第二步：匹配成功后调用setNewPassword方法进行修改密码，更新数据库

		String flag = userInfoService.checkUserIdAndUserPwd(userId,EncodeByMD5.createPwdToDB(oldPwd));
		if (flag != null) { // 用户ID成功返回，说明匹配成功，修改密码，更新数据库
			boolean updateSuccess = userInfoService.setNewPassword(userId, EncodeByMD5.createPwdToDB(newPwd));
			if (updateSuccess) {
				String str = "{\"resCode\":\"0\",\"resMsg\":\"密码修改成功\"}";
				
				UserInfo userMes = userInfoService.getUserInfo(userId);
				JSONObject jsonStr = JSONObject.fromObject(str);
				// 把用户信息拉出来，返回给用户
				jsonStr.put("data", userMes);
				System.out.println(jsonStr.toString());
				return jsonStr.toString();
			} else {
				String str = "{\"resCode\":\"1\",\"resMsg\":\"密码修改失败\",\"data\":{}}";
				return str;
			}
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"旧密码错误\",\"data\":{}}";
			return str;
		}

	}

	/**
	 * @描述：修改个人信息之修改手机号。手机号的验证由APP端实现。因此当APP提交修改时可直接修改【突然感觉不需要审核用户的密码吗？】
	 * 
	 * @param userId
	 * @param oldUserPhone
	 * @param newUserPhone
	 * @return
	 */
	@RequestMapping(value="/updateUserPhone", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateUserPhone(String userId, String oldUserPhone, String newUserPhone) {
		int phoneCount = userInfoService.isExistPhone(newUserPhone);
		
		if(phoneCount != 0)
		{
			String str = "{\"resCode\":\"1\",\"resMsg\":\"手机已存在\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
			
		}
		
		boolean flag = userInfoService.updateUserPhone(userId, oldUserPhone, newUserPhone);
		System.out.println(flag);
		if (flag == true) {
			String str = "{\"resCode\":\"0\",\"resMsg\":\"更新手机号成功\"}";
			UserInfo userMes = userInfoService.getUserInfo(userId);
			JSONObject jsonStr = JSONObject.fromObject(str);
			// 把用户信息拉出来，返回给用户
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"更新手机号失败\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}

	}

	/**
	 * @描述：修改个人信息之修改邮箱
	 * @param userId
	 * @param newUserEmail
	 * @return
	 */
	@RequestMapping(value="/updateUserEmail", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateUserEmail(String userId, String newUserEmail) {
		
		System.out.println("dd修改邮箱： "+newUserEmail);
		
		int emailCount = userInfoService.isExistEmail(newUserEmail);
		
		if(emailCount != 0)
		{
			String str = "{\"resCode\":\"1\",\"resMsg\":\"邮箱已存在\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
			
		}
		
		boolean flag = userInfoService.updateUserEmail(userId, newUserEmail);
		// System.out.println(flag);
		if (flag == true) {
			String str = "{\"resCode\":\"0\",\"resMsg\":\"更新邮箱成功\"}";
			UserInfo userMes = userInfoService.getUserInfo(userId);
			JSONObject jsonStr = JSONObject.fromObject(str);
			// 把用户信息拉出来，返回给用户
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"更新邮箱失败\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}
	}

	/**
	 * @描述：修改个人信息（无需对用户修改权限进行审核，因为只是如身高、体重等非账号安全信息） 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/updateUserInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateUserInfo(String userId, String nickName, String imgURL, String height, String weight,
			String sex, String birthday, String province, String city, String sportType) {
		
		System.out.println("获取userId:"+ userId);
		System.out.println("获取的sex: "+sex);

		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfo.setNickName(nickName);
		userInfo.setImgURL(imgURL);
		userInfo.setSex(sex);
		userInfo.setHeight(height);
		userInfo.setWeight(weight);
		userInfo.setBirthday(birthday);
		userInfo.setProvince(province);
		userInfo.setCity(city);
		userInfo.setSportType(sportType);

		boolean flag = userInfoService.updateUserInfo(userInfo);
		// System.out.println(flag);
		if (flag == true) {
			String str = "{\"resCode\":\"0\",\"resMsg\":\"更新个人信息成功\"}";
			UserInfo userMes = userInfoService.getUserInfo(userId);
			JSONObject jsonStr = JSONObject.fromObject(str);
			// 把用户信息拉出来，返回给用户
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"更新个人信息失败\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}

	}

	/**
	 * @描述：当用户点击获取验证码时触发，向用户传进入来的邮箱发送验证码，并将验证码发送到APP端，以便前端进行匹配验证
	 * @param email
	 * @return 验证码字符串
	 * 
	 */
	@RequestMapping(value = "/getVerCodeByEmail", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getVerCodeByEmail(String email) {
		// public String getVerCodeByEmail(HttpServletRequest request) {

		System.out.println("this is getVerCodeByEmail is running");
		// String email = request.getParameter("email");
		System.out.println("the parame is " + email);
		// 第一步：对参数（邮箱）进行格式验证，确保是真实有效的邮箱
		if (!SendEmail.checkEmail(email)) {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"邮箱格式错误\",\"data\":{}}";
			System.out.println(str);
			return str;
		}

		// 第二步：调用生成验证码函数，获得验证码
		String verCode = GetRandNum.getRandNum();

		System.out.println("获取到的验证码是：" + verCode);
		// 第三步：将验证码作为邮件信息发送到用户邮箱
		SendEmail.send(email, verCode);

		// 此处需要将verCode转成JSON格式，然后拼接传给APP端
		String str = "{\"resCode\":\"0\",\"resMsg\":\"send email is ok\",\"data\":{}}";
		JSONObject jsonVerCode = JSONObject.fromObject(str);
		jsonVerCode.put("verCode", verCode);
		System.out.println(jsonVerCode.toString());
		return jsonVerCode.toString();
	}

	/**
	 * @描述：绑定QQ号
	 * @param userName
	 * @param qqNum
	 * @return
	 */
	@RequestMapping(value="/bindQQAccount", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String bindQQAccount(String userName, String userPwd, String qqNum) {
		// 第一步：对用户的身份权限审核，通过后方可绑定QQ号
		// 第二步：绑定QQ号即为更新用户信息表中的QQ_NUM字段
		System.out.println("this is bindQQAccount running");
		String userId = userInfoService.checkUserNameAndUserPwd(userName, EncodeByMD5.createPwdToDB(userPwd)); // 返回的是UserId
		if (userId != null) { // 注：绝对不能用isEmpty()方法来判断非空，因为当用户名、密码不匹配时，select返回为null，会报空指针异常
			// UserId非空，说明查询成功（用户名、密码匹配正确），可以进行用户绑定QQ号，对数据库进行更新
			// 对于解绑，可以调用同一个接口，对于传递的参数是一个空的字符串对象就可以了。
			boolean flag = userInfoService.updateUserQQNum(userId, qqNum);
			if (flag == true) {
				// 更新成功==绑定完成，可返回成功信息
				String str = "{\"resCode\":\"0\",\"resMsg\":\"绑定QQ号成功\"}";
				UserInfo userMes = userInfoService.getUserInfo(userId);
				JSONObject jsonStr = JSONObject.fromObject(str);
				// 把用户信息拉出来，返回给用户
				jsonStr.put("data", userMes);
				System.out.println(jsonStr.toString());
				return jsonStr.toString();
			} else {
				String str = "{\"resCode\":\"1\",\"resMsg\":\"绑定QQ号失败\",\"data\":{}}";
				JSONObject jsonStr = JSONObject.fromObject(str);
				return jsonStr.toString();
			}
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户名和密码认证失败，请确定用户名和密码正确，重新绑定 \",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}
	}

	/**
	 * @描述：绑定微博号
	 * @param userName
	 * @param WeiboNum
	 * @return
	 */
	@RequestMapping(value="/bindWeiBoAccount", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String bindWeiBoAccount(String userName, String userPwd, String weiboNum) {
		System.out.println("this is bindWeiBoAccount running");
		String userId = userInfoService.checkUserNameAndUserPwd(userName, EncodeByMD5.createPwdToDB(userPwd)); // 返回的是UserId
		if (userId != null) { // 注：绝对不能用isEmpty()方法来判断非空，因为当用户名、密码不匹配时，select返回为null，会报空指针异常
			boolean flag = userInfoService.updateUserWeiboNum(userId, weiboNum);
			if (flag == true) {
				// 更新成功==绑定完成，可返回成功信息
				String str = "{\"resCode\":\"0\",\"resMsg\":\"绑定微博号成功 \"}";
				UserInfo userMes = userInfoService.getUserInfo(userId);
				JSONObject jsonStr = JSONObject.fromObject(str);
				// 把用户信息拉出来，返回给用户
				jsonStr.put("data", userMes);
				System.out.println(jsonStr.toString());
				return jsonStr.toString();
			} else {
				String str = "{\"resCode\":\"1\",\"resMsg\":\"绑定微博号失败\",\"data\":{}}";
				JSONObject jsonStr = JSONObject.fromObject(str);
				return jsonStr.toString();
			}
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户名和密码认证失败，请确定用户名和密码正确，重新绑定 \",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}
	}
	
	
	
	/**
	 * 修改说明：将原来通过用户名密码来绑定，改成直接通过userId绑定
	 */
	//------------------------------------------修改后的绑定QQ和微博------------------------------------------------//
	
	/**
	 * @描述：绑定QQ号
	 * @param userName
	 * @param qqNum
	 * @return
	 */
	@RequestMapping(value="/bindQQAccount2", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String bindQQAccount2(String userId, String qqNum) {
		
		// 绑定QQ号即为更新用户信息表中的QQ_NUM字段
		System.out.println("this is bindQQAccount running");		
	
		// 对于解绑，可以调用同一个接口，对于传递的参数是一个空的字符串对象就可以了。
		int count = userInfoService.updateQQNum(qqNum, userId);
		
		if (count == 1) 
		{
			// 更新成功==绑定完成，可返回成功信息
			String str = "{\"resCode\":\"0\",\"resMsg\":\"绑定QQ号成功\"}";
			UserInfo userMes = userInfoService.getUserInfo(userId);
			JSONObject jsonStr = JSONObject.fromObject(str);
			// 把用户信息拉出来，返回给用户
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} 
		else 
		{
			String str = "{\"resCode\":\"1\",\"resMsg\":\"绑定QQ号失败\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}	
	}

	/**
	 * @描述：绑定微博号
	 * @param userName
	 * @param WeiboNum
	 * @return
	 */
	@RequestMapping(value="/bindWeiBoAccount2", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String bindWeiBoAccount2(String userId, String weiboNum) {
		
		
		System.out.println("unbindweibo get requestParam"+userId+"===="+weiboNum);
		
		int count = userInfoService.updateWeiBoNum(weiboNum, userId);
		
		if (count == 1) {
			// 更新成功==绑定完成，可返回成功信息
			String str = "{\"resCode\":\"0\",\"resMsg\":\"绑定微博号成功 \"}";
			UserInfo userMes = userInfoService.getUserInfo(userId);
			JSONObject jsonStr = JSONObject.fromObject(str);
			// 把用户信息拉出来，返回给用户
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"绑定微博号失败\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}
		
	}
	
	
	//----------------------------------------------------------------------------------------------------------//
	
	

	/**
	 * @描述：解绑QQ号
	 * @param userId
	 * @param userPwd
	 * @return
	 */
	@RequestMapping(value="/unbindQQAccount", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String unbindQQAccount(String userId, String userPwd) {
		
		/*String userIdFlag = userInfoService.checkUserIdAndUserPwd(userId, EncodeByMD5.createPwdToDB(userPwd)); // 返回的是UserId
*/		
		/*if (userIdFlag != null) {*/
			// 对于解绑，可以调用同一个接口，对于传递的参数是一个空的字符串对象就可以了。
		String qqNum = null;
		boolean flag = userInfoService.updateUserQQNum(userId, qqNum);
		if (flag == true) {
			// 更新成功==绑定完成，可返回成功信息
			String str = "{\"resCode\":\"0\",\"resMsg\":\"解绑QQ号成功 unbind QQ account success\"}";
			UserInfo userMes = userInfoService.getUserInfo(userId);
			JSONObject jsonStr = JSONObject.fromObject(str);
			// 把用户信息拉出来，返回给用户
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"解绑QQ号失败 unbind QQ account fault\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}
		/*} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户名和密码认证失败，请确定用户名和密码正确，重新解绑\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}*/
	}

	/**
	 * @描述：解绑微博号
	 * @param userId
	 * @param userPwd
	 * @return
	 */
	@RequestMapping(value="/unbindWeiBoAccount", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String unbindWeiboAccount(String userId, String userPwd) {
		/*String userIdFlag = userInfoService.checkUserIdAndUserPwd(userId, EncodeByMD5.createPwdToDB(userPwd)); // 返回的是UserId
		if (userIdFlag != null) {*/
			// 对于解绑，可以调用同一个接口，对于传递的参数是一个空的字符串对象就可以了。
		String weiboNum = null;
		boolean flag = userInfoService.updateUserWeiboNum(userId, weiboNum);
		if (flag == true) {
			// 更新成功==绑定完成，可返回成功信息
			String str = "{\"resCode\":\"0\",\"resMsg\":\"解绑微博号成功 \"}";
			UserInfo userMes = userInfoService.getUserInfo(userId);
//对拿到的对象中的出生日期进行格式转换
//userMes.setBirthday(DateChange.birthdayChange(userMes.getBirthday()));
			JSONObject jsonStr = JSONObject.fromObject(str);
			// 把用户信息拉出来，返回给用户
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"解绑微博号失败\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}
		/*} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户名和密码认证失败，请确定用户名和密码正确，重新解绑 \",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}*/
	}
	
	/**
	 * @描述：通过第三方QQ登录
	 * @param qqNum
	 * @return
	 */
	@RequestMapping(value="/loginWithQQ", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String loginWithQQ(String qqNum) {
		
		System.out.println("this is loginWithQQ running");
		String resultStr = userInfoService.isExistQQ(qqNum);
		if(resultStr == null) {
			//说明没有此QQ号，需要进行绑定。
			String str = "{\"resCode\":\"1\",\"resMsg\":\"QQ没有进行绑定过\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"0\",\"resMsg\":\"QQ已经进行绑定过\"}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			UserInfo userMes = userInfoService.getUserInfo(resultStr);
			// 把用户信息拉出来，返回给用户
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		}
	}
	
	/**
	 * @描述：通过第三方微博登录
	 * @param weiboNum
	 * @return
	 */
	@RequestMapping(value="/loginWithWeibo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String loginWithWeibo(String weiboNum) {
		
		System.out.println("this is loginWithWeibo running");
		System.out.println("the data is :" + weiboNum);
		String resultStr = userInfoService.isExistWeibo(weiboNum);
		if(resultStr == null) {
			//说明没有此号，需要进行绑定。
			String str = "{\"resCode\":\"1\",\"resMsg\":\"Weibo没有进行绑定过\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"0\",\"resMsg\":\"Weibo已经进行绑定过\"}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			UserInfo userMes = userInfoService.getUserInfo(resultStr);
			// 把用户信息拉出来，返回给用户
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		}
	}
	
	/**
	 * @描述：APP端用来直接获得用户信息，用于非第一次登录时。
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/getUserInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserInfo(String userId) {
		System.out.println("this is getUserInfo running");
		
		if(userId != null) {
			
			UserInfo userMes = userInfoService.getUserInfo(userId);
			//获得用户信息对象后，需要进行非空判断，来保证数据库中存在上面的userId用户。
			if(userMes == null) {
				String str = "{\"resCode\":\"1\",\"resMsg\":\"用户不存在，请注册\",\"data\":{}}";
				JSONObject jsonStr = JSONObject.fromObject(str);
				System.out.println(jsonStr.toString());
				return jsonStr.toString();
			}
			//当从数据库中查询到的结果非空，说明对象里面已经装入了用户信息，直接返回给APP端
			String str = "{\"resCode\":\"0\",\"resMsg\":\"用户信息已返回\"}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			jsonStr.put("data", userMes);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"用户ID为空，请重新登录\",\"data\":{}}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			System.out.println(jsonStr.toString());
			return jsonStr.toString();
		}
	}

}
