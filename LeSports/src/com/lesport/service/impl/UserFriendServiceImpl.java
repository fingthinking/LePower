package com.lesport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.IUserFriendMapper;
import com.lesport.model.Friendsreturn;
import com.lesport.model.UserFriend;
import com.lesport.model.UserInfo;
import com.lesport.model.UserInfo2;
import com.lesport.service.IUserFriendService;
/**
 * @文件名 UserFriendServiceImpl
 * @author 斌
 * @version V.1
 * @日期 2016.3.5
 *
 */

@Service
@Transactional
public class UserFriendServiceImpl implements IUserFriendService{
	@Resource
	private IUserFriendMapper mapper;

	@Override
	public void addFriend(UserFriend userFriend) {
		
		mapper.addFriend(userFriend);
	}

	@Override
	public void deleteFriend(String userId, String friendId) {
		mapper.deleteFriend(userId, friendId);	
	}

	@Override
	public List<UserInfo> findFriendNearby() {
		List<UserInfo> findFriendNearbylist=mapper.findFriendNearby();
		return findFriendNearbylist; 
	}

	@Override
	public int updateLngLat(String userId, String lng, String lat) {
		return mapper.updateLngLat(userId, lng, lat);
	}

	@Override
	public List<UserInfo> myFriendsList(String userId, String flag) {
		List<UserInfo> myFriendsOrFanslist=mapper.myFriendsList(userId, flag);
		return myFriendsOrFanslist;
	}
	
	@Override
	public List<UserInfo> myFansList(String userId, String flag) {
		List<UserInfo> myFriendsOrFanslist=mapper.myFansList(userId, flag);
		return myFriendsOrFanslist;
	}

	@Override
	public UserInfo getFriendInfo(String userId) {
		UserInfo getFriendInfolist=mapper.getFriendInfo(userId);
		return getFriendInfolist;
	}
	
	@Override
	public List<UserInfo2> searchFriend(String le_id, String userId) {
		// TODO Auto-generated method stub
		List<UserInfo2> userInfos=mapper.searchFriend(le_id, userId);
		//System.out.println("-----------"+le_id+","+userId);
		//System.out.println(JSONSerializer.toJSON(userInfos));
		return userInfos;
	}

	@Override
	public List<UserInfo2> getAll(String le_id) {
		// TODO Auto-generated method stub
		List<UserInfo2> userInfo2s =mapper.getAll(le_id);
		return userInfo2s;
	}

	
	
	
//	@Override
//	public List<UserInfo> recommendFriends(String userId) {
//		List<UserInfo> recommendfriends=mapper.recommendFriends(userId);
//		return recommendfriends;
//	}

}
