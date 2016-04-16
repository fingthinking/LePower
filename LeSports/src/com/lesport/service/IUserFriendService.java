package com.lesport.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.Friendsreturn;
import com.lesport.model.UserFriend;
import com.lesport.model.UserInfo;
import com.lesport.model.UserInfo2;
/**
 * @文件名 IUserFriendService
 * @author 斌
 * @version V.1
 * @日期 2016.3.5
 *
 */
public interface IUserFriendService {

	public void addFriend(UserFriend userFriend);
	public void deleteFriend(String userId,String friendId);
	public List<UserInfo> findFriendNearby();
	public int updateLngLat(String userId,String lng,String lat);
	public List<UserInfo> myFriendsList(String userId, String flag);
	public List<UserInfo> myFansList(String userId, String flag);
	public UserInfo getFriendInfo(String userId);
	public List<UserInfo2> searchFriend(String le_id,String userId);
	//获取到所有le号或昵称相似的用户信息
    public List<UserInfo2> getAll(String le_id);
	
    //public List<UserInfo> recommendFriends(String userId);
	
}
