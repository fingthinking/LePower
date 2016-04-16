package com.lepower.utils;

import java.util.StringTokenizer;

public class LeUrls {

	public static final String BASE_SERVER="http://192.168.1.100:8080/LeSports/";
	
	
	public static final String BASE_IMAGE_URL = "http://192.168.1.100:8080/LeSports/";
	public static final String BASE_USER_URL = "http://192.168.1.100:8080/LeSports/userInfo/";
	
	public static final String GET_USER_INFO = BASE_USER_URL+"getUserInfo";
	
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
	public static final String BIND_QQ_URL2 = BASE_USER_URL+"bindQQAccount2";	// QQ绑定接口
	public static final String BIND_WEIBO_URL2 = BASE_USER_URL+"bindWeiBoAccount2";	// 微博绑定接口
	public static final String UNBIND_QQ = BASE_USER_URL+"unbindQQAccount";			// 解绑QQ
	public static final String UNBIND_WEIBO = BASE_USER_URL+"unbindWeiBoAccount";   // 解绑微博
	
	
	// =========================================  乐友圈 ==============================
	
	public static final String CIRCLE_PUBLISH_COMMENT=BASE_SERVER+"circle/publishComment";//发表评论
	public static final String CIRCLE_ADDLIKE=BASE_SERVER+"circle/addLike";//赞
	public static final String CIRCLE_DELETELIKE=BASE_SERVER+"circle/deleteLike";//取消赞
	public static final String CIRCLE_DELETECIRCLE=BASE_SERVER+"circle/deleteCircle";//删除动态
	public static final String CIRCLE_DELETECOMMENT=BASE_SERVER+"circle/deleteComment";//删除评论
	public static final String CIRCLE_GETFRIEND=BASE_SERVER+"circle/getFriendCircle";//获取好友动态
	public static final String CIRCLE_GETMYCIRCLE=BASE_SERVER+"circle/getMyCircle";
	public static final String CIRCLE_PUBLISH=BASE_SERVER+"circle/publishCircle";
	public static final String CIRCLE_TRANSMIT=BASE_SERVER+"circle/forwardLike";

	// ========================================	 好友管理 ================================
	public static final String ADD_FRIEND = BASE_SERVER + "friend/addFriend?userId=";
	public static final String DELETE_FRIEND = BASE_SERVER + "friend/deleteFriend?userId=";
	public static final String FANS_LIST = BASE_SERVER + "friend/myFriendsOrFansList?userId=";
	public static final String FIND_LIST = BASE_SERVER + "friend/findFriends?";
	public static final String GUANZHU_LIST = BASE_SERVER + "friend/myFriendsOrFansList?userId=";
	public static final String NEBI_LIST = BASE_SERVER + "friend/findFriendNearby?userId=";
	public static final String FRIEND_INFO = BASE_SERVER + "friend/getFriendInfo?userId=";
	public static final String UPDATE_WEIZHI = BASE_SERVER + "friend/updateLngLat?userId=";
	
	public static final String SCORE_QUERY=BASE_SERVER + "sport/getSportInfo?userId=";

	
}

