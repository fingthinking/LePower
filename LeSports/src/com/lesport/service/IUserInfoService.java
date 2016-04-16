package com.lesport.service;

import java.util.List;

import com.lesport.model.CaoStuTemp;
import com.lesport.model.UserInfo;
/**
 * 
 * @author 曹汝帅
 * @version V.1
 * @描述： 用户信息服务接口，定义的函数与mapper层接口一致
 * @日期： 2016-03-05
 *----------------
 *@author 邱志国
 *@描述：增加一个方法String getUserByName(String userName)
 *@日期：2016-03-10
 *----------------
 *
 */
public interface IUserInfoService {

	public boolean addUserInfo(UserInfo userInfo);
	
	public boolean deleteUserInfo(int userId);
	public UserInfo findUserInfoById(String userId);
	public List<UserInfo> getAllUserInfo();
	//
	public UserInfo loginWeb(String userName, String userPwd);
	public UserInfo login(String userName,String userPwd);
	
	public UserInfo getUserInfo(String userName);		//返回用户所有信息的方法接口
	
	public String findUserIdByPhone(String phoneNum);
	public String findUserIdByEmail(String email);
	public boolean setNewPassword(String userId, String userPwd);
	
	public String checkUserIdAndUserPwd(String userId, String oldPwd);
	public String checkUserNameAndUserPwd(String userName, String userPwd);
	
	public void register(UserInfo userInfo);
	public void caoStuTemp(CaoStuTemp crsh);		//测试
	public String isExistStu(String str1);			//测试
	
	public boolean updateUserPhone(String userId, String oldUserPhone, String newUserPhone);
	public boolean updateUserEmail(String userId, String newUserEmail);
	public boolean updateUserInfo(UserInfo userInfo);
	public boolean updateUserQQNum(String userId, String qqNum);
	public boolean updateUserWeiboNum(String userId, String weiboNum);
	
	public String isExistUser(String userName);

	
	
	
	/**
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<UserInfo> getUserByName(String userName) throws Exception;
	public void updateUserInfoFromPersonalCenter(UserInfo userInfo)throws Exception;

	
	//另加
	public int isExistPhone(String phone_num);
	
	public int isExistEmail(String email);

	public String isExistQQ(String qqNum);

	public String isExistWeibo(String weiboNum);

	public int isExistLeNum(String leNumTemp);
	
	public int updateImgUrl(String imgUrl , String userId);
	
	public int updateQQNum(String qqNum, String userId);
	
	public int updateWeiBoNum(String weiboNum, String userId);
	
	
	
}
