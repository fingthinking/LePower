package com.lesport.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.IUserInfoMapper;
import com.lesport.model.CaoStuTemp;
import com.lesport.model.UserInfo;
import com.lesport.service.IUserInfoService;
import com.lesport.util.Utility;


/**
 * 
 * @author 曹汝帅
 * @version V.1
 * @see 用户信息服务接口实现类
 * @日期： 2016-03-05
 * --------------------
 * @author 邱志国
 * @描述：增加一个方法String getUserByName(String userName)
 * @日期：2016-03-10
 * 
 */

@Service
@Transactional			//此处不再进行创建SqlSession和提交事务，都已交由spring去管理了
public class UserInfoServiceImpl implements IUserInfoService {
	
	@Resource
	private IUserInfoMapper userInfoMapper;

	@Override
	public boolean addUserInfo(UserInfo userInfo) {
		
		return userInfoMapper.addUserInfo(userInfo);
	}

	@Override
	public boolean deleteUserInfo(int userId) {
		
		return userInfoMapper.deleteUserInfo(userId);
	}

	@Override
	public UserInfo findUserInfoById(String userId) {
		
		UserInfo userInfo = userInfoMapper.findUserInfoById(userId);
		return userInfo;
	}

	@Override
	public List<UserInfo> getAllUserInfo() {
		
		List<UserInfo> allUserInfo = userInfoMapper.getAllUserInfo();
		return allUserInfo;
	}
/**
 * 上面的是demo
 */
	
	
	
	@Override
	public UserInfo loginWeb(String userName, String userPwd) {

		return userInfoMapper.loginWeb(userName,userPwd);
	}

	@Override
	public UserInfo login(String userName, String userPwd) {

		return userInfoMapper.login(userName, userPwd);
	}

	@Override
	public String findUserIdByPhone(String phoneNum) {

//		System.out.println("this is serviceImpl findUserIdByPhone");
		return userInfoMapper.findUserIdByPhone(phoneNum);
	}
	
	@Override
	public String findUserIdByEmail(String email) {

		return userInfoMapper.findUserIdByEmail(email);
	}

	@Override
	public boolean setNewPassword(String userId,String userPwd) {

		return userInfoMapper.setNewPassword(userId,userPwd);
	}

	@Override
	public String checkUserIdAndUserPwd(String userId, String oldPwd) {

		//为了防止Mapper层出错，我将参数设置为与UserInfo对象一致的属性名
		String userPwd = oldPwd;
		return userInfoMapper.checkUserIdAndUserPwd(userId,userPwd);
	}

	@Override
	public String checkUserNameAndUserPwd(String userName, String userPwd) {

		//这些位置到最后都要加MD5加密操作后再调Mapper层
		return userInfoMapper.checkUserNameAndUserPwd(userName,userPwd);
	}
	
	@Override
	public void register(UserInfo userInfo) {

		userInfoMapper.register(userInfo);
	}

	//测试
	@Override
	public void caoStuTemp(CaoStuTemp crsh) {

		userInfoMapper.caoStuTemp(crsh);
	}

	@Override
	public boolean updateUserPhone(String userId, String oldUserPhone, String newUserPhone) {

		return userInfoMapper.updateUserPhone(userId,newUserPhone);
	}

	@Override
	public boolean updateUserEmail(String userId, String newUserEmail) {

		return userInfoMapper.updateUserEmail(userId,newUserEmail);
	}

	@Override
	public boolean updateUserInfo(UserInfo userInfo) {
		
		return userInfoMapper.updateUserInfo(userInfo);
	}
	
	@Override
	public boolean updateUserQQNum(String userId,String qqNum) {

		return userInfoMapper.updateUserQQNum(userId,qqNum);
	}
	
	@Override
	public boolean updateUserWeiboNum(String userId, String weiboNum) {

		return userInfoMapper.updateUserWeiboNum(userId,weiboNum);
	}
	
	//测试
	@Override
	public String isExistStu(String str1) {
		
		return userInfoMapper.isExistStu(str1);
	}

	@Override
	public String isExistUser(String userName) {

		return userInfoMapper.isExistUser(userName);
	}




	@Override
	public UserInfo getUserInfo(String userName) {
		
		return userInfoMapper.getUserInfo(userName);
	}




	@Override
	public int isExistPhone(String phone_num) {
		
		return userInfoMapper.isExistPhone(phone_num);
	}



	@Override
	public int isExistEmail(String email) {
		return userInfoMapper.isExistEmail(email);
	}



	@Override
	public String isExistQQ(String qqNum) {

		return userInfoMapper.isExistQQ(qqNum);
	}







	@Override
	public List<UserInfo> getUserByName(String userName){
		List<UserInfo> userInfoList = new ArrayList<>();
		try {
			if(("").equals(userName)||userName==null){
				System.out.println(userName);
				System.out.println("为空");
				System.out.println("执行前");
				List<UserInfo> list = userInfoMapper.getUserInfo_Name_IsNull();
				
				System.out.println(list);
				System.out.println("执行后");
				userInfoList = userInfoMapper.getUserInfo_Name_IsNull();
			}else{
				System.out.println("不为空");
				System.out.println(userInfoMapper.getUserInfo_Name_NotNull(userName));
				userInfoList.addAll(userInfoMapper.getUserInfo_Name_NotNull(userName));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("执行了异常");
			userInfoList = null;
		} finally {
			return userInfoList;
		}

	}



	@Override
	public String isExistWeibo(String weiboNum) {
		// TODO Auto-generated method stub
		return userInfoMapper.isExistWeibo(weiboNum);
	}



	@Override
	public void updateUserInfoFromPersonalCenter(UserInfo userInfo) throws Exception {
		userInfoMapper.updateUserInfoFromPersonalCenter(userInfo);
		return ;
	}

	@Override
	public int isExistLeNum(String leNumTemp) {

		return userInfoMapper.isExistLeNum(leNumTemp);
	}

	@Override
	public int updateImgUrl(String imgUrl, String userId) {
		
		return userInfoMapper.updateImgUrl(imgUrl, userId);
	}

	@Override
	public int updateQQNum(String qqNum, String userId) {
		
		return userInfoMapper.updateQQNum(qqNum, userId);
	}

	@Override
	public int updateWeiBoNum(String weiboNum, String userId) {
		
		return userInfoMapper.updateWeiBoNum(weiboNum, userId);
	}

}
