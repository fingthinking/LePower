package com.lesport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lesport.model.Friendsreturn;
import com.lesport.model.UserFriend;
import com.lesport.model.UserInfo;
import com.lesport.model.UserInfo2;
/**
 * @文件名 IUserFriendMapper
 * @author 斌
 * @version V.1
 * @日期 2016.3.5
 *
 */
public interface IUserFriendMapper {
	
	public void addFriend(@Param("userFriend") UserFriend userFriend);
	public void deleteFriend(@Param("userId") String userId,@Param("friendId") String friendId);
	public List<UserInfo> findFriendNearby();
	public int updateLngLat(@Param("userId") String userId,@Param("lng")String lng,@Param("lat")String lat);
	public List<UserInfo> myFriendsList(@Param("userId") String userId,@Param("flag") String flag);
	public List<UserInfo> myFansList(@Param("userId") String userId,@Param("flag") String flag);
	public UserInfo getFriendInfo(@Param("userId") String userId);
	public List<UserInfo2> searchFriend(@Param("le_id")String le_id, @Param("userId")String userId);
	public List<UserInfo2> getAll(String le_id);
	
	//public List<UserInfo> recommendFriends(@Param("userId") String userId);
}
