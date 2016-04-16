package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.CaoStuTemp;
import com.lesport.model.UserInfo;
//import com.lesport.service.impl.用户信息服务接口实现类;

/**
 * 
 * @author 曹汝帅
 * @version V.1
 * @see 用户信息服务接口实现类
 * @日期： 2016-03-05
 * --------------------
 * @author 邱志国
 * @描述：增加一个方法getUserInfo_Name_NotNull
 * 		增加另一个方法getUserInfo_Name_IsNull
 * @日期：2016-03-10
 * 
 */

public interface IUserInfoMapper {
	
	public boolean addUserInfo(UserInfo userInfo);
	
	public boolean deleteUserInfo(int userId);
	public UserInfo findUserInfoById(String userId);
	public List<UserInfo> getAllUserInfo();
	
	public UserInfo loginWeb(@Param("userName") String userName,@Param("userPwd") String userPwd);
	public UserInfo login(@Param("userName") String userName,@Param("userPwd")String userPwd);
	public UserInfo getUserInfo(@Param("userName")String userName);		//返回用户信息
	
	public String findUserIdByPhone(@Param("phoneNum") String phoneNum);
	public String findUserIdByEmail(@Param("email") String email);
	public boolean setNewPassword(@Param("userId") String userId,@Param("userPwd") String userPwd);
	
	public String checkUserIdAndUserPwd(@Param("userId") String userId,@Param("userPwd") String userPwd);
	public String checkUserNameAndUserPwd(@Param("userName")String userName,@Param("userPwd") String userPwd);
	
	public void register(@Param("userInfo") UserInfo userInfo);
	public void caoStuTemp(@Param("crsh") CaoStuTemp crsh);			//测试
	public String isExistStu(@Param("str1") String str1);			//测试
	
	public boolean updateUserPhone(@Param("userId") String userId,@Param("newUserPhone") String newUserPhone);
	public boolean updateUserEmail(@Param("userId") String userId,@Param("newUserEmail") String newUserEmail);
	public boolean updateUserInfo(@Param("userInfo") UserInfo userInfo);
	public boolean updateUserQQNum(@Param("userId") String userId,@Param("qqNum") String qqNum);
	public boolean updateUserWeiboNum(@Param("userId") String userId,@Param("weiboNum") String weiboNum);
	
	public String isExistUser(@Param("userName") String userName);

	

	
	
	//获取用户信息
	public List<UserInfo> getUserInfo_Name_NotNull(@Param("userName") String userName) throws Exception;
	public List<UserInfo> getUserInfo_Name_IsNull() throws Exception;
	public void updateUserInfoFromPersonalCenter(UserInfo userInfo)throws Exception;

	
	
	//另加
	public int isExistPhone(String phone_num);
	
	public int isExistEmail(String email);

	public String isExistQQ(@Param("qqNum")String qqNum);

	public String isExistWeibo(@Param("weiboNum")String weiboNum);

	public int isExistLeNum(@Param("leNumTemp")String leNumTemp);
	
	public int updateImgUrl(String imgUrl , String userId);
	
	public int updateQQNum(String qqNum, String userId);
	
	public int updateWeiBoNum(String weiboNum, String userId);
	
	

	
	
}
