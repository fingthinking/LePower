package com.lepower.dao;

import com.lepower.model.QQUserInfo;
import com.lepower.model.User;
import com.lepower.model.WeiBoUserInfo;

public interface IUserDao {
	/**
	 * 查看当前登陆的用户
	 * @return
	 */
	User getUserNow();
	/**
	 * 增加或更新登陆的用户
	 */
	void saveUserNow(User user);
	/**
	 * 删除当前登陆用户
	 */
	void deleteUserNow();
	/**
	 * 登陆过的所有用户
	 */
	//void saveUser(User user);
//	/**
//	 * 更新用户资料
//	 */
//	void updateUser(User user);
	/**
	 * 保存qq信息
	 * @param info
	 */
	void saveQQInfo(QQUserInfo info);
	/**
	 * 获取qq信息
	 * @return
	 */
	QQUserInfo getQQInfo();
	/**
	 * 保存微博信息
	 * @param info
	 */
	void saveWeiboInfo(WeiBoUserInfo info);
	/**
	 * 获取微博信息
	 * @return
	 */
	WeiBoUserInfo getWeiboInfo();
}
