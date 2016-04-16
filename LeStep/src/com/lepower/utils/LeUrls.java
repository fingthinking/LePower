package com.lepower.utils;

public class LeUrls {
	public static final String BASE_IMAGE_URL = "http://192.168.1.100:8080/LeSports/";
	public static final String BASE_USER_URL = "http://192.168.1.100:8080/LeSports/userInfo/";
	
	
	public static final String DEFAULT_HEAD_URL = BASE_IMAGE_URL+"img/defaultImg.jpg";	// 默认头像路径
	public static final String UPLOAD_IMG = BASE_IMAGE_URL+"circle/uploadImg";	// 上传图片的路径
	
	public static final String LOGIN_URL = BASE_USER_URL+"login";// 登录接口
	public static final String LOGIN_QQ_URL = BASE_USER_URL+"loginWithQQ";// QQ登录接口
	public static final String LOGIN_WEIBO_URL = BASE_USER_URL+"loginWithWeibo";// QQ登录接口
	public static final String BIND_QQ_URL = BASE_USER_URL+"bindQQAccount";	// QQ绑定接口
	public static final String BIND_WEIBO_URL = BASE_USER_URL+"bindWeiBoAccount";	// QQ绑定接口
	public static final String FIND_PWD_BY_PHONE = BASE_USER_URL+"findPasswordByPhone";	// 找回密码Tel
	public static final String FIND_PWD_BY_EMAIL = BASE_USER_URL+"findPasswordByEmail";//邮箱找回密码接口
	public static final String EXIST_USER = BASE_USER_URL+"isExistUser";// 手机注册接口
	public static final String REQUEST_EMAIL_CODE = BASE_USER_URL+"getVerCodeByEmail";//获取邮箱验证码接口
	public static final String REGISITER_URL = BASE_USER_URL+"register";//注册接口
	public static final String UPDATE_PHONE_NUM = BASE_USER_URL+"updateUserPhone";	//更新手机号
	public static final String UPDATE_EMAIL_NUM = BASE_USER_URL+"updateUserEmail";	//更新邮箱号
	public static final String UPDATE_PWD_NUM = BASE_USER_URL+"setNewPassword";		// 更新密码
	public static final String UPDATE_USER_INFO = BASE_USER_URL+"updateUserInfo";	// 修改个人信息
	
	public static final String GET_USER_INFO = BASE_USER_URL+"getUserInfo";
	
}
