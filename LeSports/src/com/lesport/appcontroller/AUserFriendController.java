package com.lesport.appcontroller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lesport.model.UserFriend;
import com.lesport.model.UserInfo;
import com.lesport.model.UserInfo2;
import com.lesport.service.CircleService;
import com.lesport.service.IUserFriendService;
import com.lesport.util.GetDistanceOfLngLat;
import com.lesport.util.Utility;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
/**
 * @author 斌
 * @version V.1
 *
 */
@Controller
@RequestMapping("/friend")
public class AUserFriendController {
	@Autowired
	private IUserFriendService userFriendService;// userFriendService对象
	@Autowired
	private CircleService circleService;//circleService对象

	@RequestMapping(value = "/addFriend", produces = "text/html;charset=UTF-8")
	@ResponseBody
	// 关注好友 http://IP: 端口/LeSports/friend/addFriend
	public String addFriends(String userId, String friendId) {
		try {
			UserFriend userFriend = new UserFriend();
			// 向userFriend表里面加入信息
			userFriend.setUserId(userId);
			userFriend.setFriendId(friendId);
			userFriend.setFriendFlag("0");
			userFriend.setUserFriendId(Utility.getRowId());
			userFriend.setCreatedDate(Utility.getFormattedCurrentDateAndTime());
			userFriend.setDeleteFlag("N");
			
			// 将数据写入数据库
			userFriendService.addFriend(userFriend);
			// 控制台输出
			System.out.println("addFriend-----userId" + userId + "测试：friendId---" + friendId);
			// 返回数据给APP端
			String str = "{\"resCode\":\"0\",\"resMsg\":\"关注成功success\",\"data\":[]}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		} catch (Exception e) {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"关注失败\",\"data\":[]}";
			JSONObject jsonStr = JSONObject.fromObject(str);
			return jsonStr.toString();
		}
	}

	@RequestMapping(value = "/deleteFriend", produces = "text/html;charset=UTF-8")
	@ResponseBody
	// 取消关注  http://IP: 端口/LeSports/friend/deleteFriend
	public String deleteFriend(String userId, String friendId) {
		
		//向userFriend表里面删除信息
		userFriendService.deleteFriend(userId, friendId);
		//控制台输出
		System.out.println("deleteFriend-----userId" + userId + "测试：friendId---" + friendId);
		//返回数据给APP端
		String str = "{\"resCode\":\"1\",\"resMsg\":\"取消关注成功success\",\"data\":[]}";
		JSONObject jsonStr = JSONObject.fromObject(str);
		return jsonStr.toString();
	}

	@RequestMapping(value = "/findFriendNearby", produces = "text/html;charset=UTF-8")
	@ResponseBody
	// 发现附近用户并且判断是否是好友   http://IP: 端口/LeSports/friend/findFriendNearby
	public String findFriendNearby(String userId, String longitude, String latitude) {
		// 获得所有用户信息,放入userInfo中
		List<UserInfo> userInfo = userFriendService.findFriendNearby();
		// 获得该搜索用户的所有好友信息，放入userFriendsInfo中
		List<UserInfo> userFriendsInfo = userFriendService.myFriendsList(userId, "0");
		//设置迭代器
		Iterator iterator = userInfo.iterator();
		Iterator iteratorFriend = userFriendsInfo.iterator();
		
		JSONObject jsonuserfindFriendNearby = null;
		JSONObject jsonuserfindFriend = null;
		
		JSONArray jsonArray = new JSONArray();
		// 遍历所有用户信息
		System.out.println("longitude---"+longitude+"---latitude---"+latitude);
		while (iterator.hasNext()) {
			UserInfo userInfoNearby = (UserInfo) iterator.next();//取出一个用户的信息
			jsonuserfindFriendNearby = JSONObject.fromObject(userInfoNearby);//将其转换成JSONObject对象
			
			String aa = (String) jsonuserfindFriendNearby.get("lng");// 用户当前径度
			String bb = (String) jsonuserfindFriendNearby.get("lat");// 用户当前纬度
			String id = (String) jsonuserfindFriendNearby.get("userId");//该搜索用户id

			if (!aa.isEmpty() && !bb.isEmpty() && !longitude.isEmpty() && !latitude.isEmpty()) {
				//将String类型的经纬度换成double类型
				double a = Double.valueOf(aa).doubleValue();
				double b = Double.valueOf(bb).doubleValue();
				double c = Double.valueOf(longitude).doubleValue();
				double d = Double.valueOf(latitude).doubleValue();
				//计算用户与附近用户距离
				double distance = GetDistanceOfLngLat.Distance(a, b, c, d);
				//筛选出附近用户
				if (distance < 200 && !id.equals(userId)) {
					// 获得年龄
					String birth = (String) jsonuserfindFriendNearby.get("birthday");
					if (!birth.isEmpty()) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
						java.util.Date birthd = null;
						try {
							birthd = sdf.parse(birth);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = new Date(System.currentTimeMillis());
						int age = date.getYear() - birthd.getYear();
						jsonuserfindFriendNearby.put("age", age);
					}
					jsonuserfindFriendNearby.discard("birthday");
					//由于前面搜索的所有用户，要排除掉该搜索用户自己
					String userid = (String) jsonuserfindFriendNearby.get("userId");// 用户id
					jsonuserfindFriendNearby.put("flag", "1");
					if (null != userid) {
						iteratorFriend = userFriendsInfo.iterator();
						while (iteratorFriend.hasNext()) {
							UserInfo userFriend = (UserInfo) iteratorFriend.next();
							if (userFriend != null) {
								jsonuserfindFriend = JSONObject.fromObject(userFriend);
								String friendid = (String) jsonuserfindFriend.get("userId");// 好友id
								if (friendid.equals(userid)) {
									jsonuserfindFriendNearby.put("flag", "0");
								}
							}
						}
					}
					jsonArray.add(jsonuserfindFriendNearby);
				}
			}
		}
		//控制台输出
		System.out.println("findFriendNearby------userId" + userId + "测试：longitude---" + longitude+ "latitude---"+ latitude);
		//返回数据给APP端
		String str="{\"resCode\":\"0\",\"resMsg\":\"\",\"data\":"+jsonArray.toString()+"}";
		System.out.println(str);
		return str;

	}

	@RequestMapping(value = "/updateLngLat", produces = "text/html;charset=UTF-8")
	@ResponseBody
	// 更新经纬度   http://IP: 端口/LeSports/friend/updateLngLat
	public String updateLngLat(String userId, String longitude, String latitude) {
		int flag = userFriendService.updateLngLat(userId, longitude, latitude);
	
		//控制台输出
		System.out.println("updateLngLat-----userId"+userId + "测试：flag---" + flag+"测试：longitude---" + longitude+"latitude---"+ latitude);
		if (flag == 1) {
			String str = "{\"resCode\":\"0\",\"resMsg\":\"经纬度更新成功this is ok\",\"data\":[]}";
			JSONObject json = JSONObject.fromObject(str);
			return str;
		} else {
			String str = "{\"resCode\":\"1\",\"resMsg\":\"经纬度更新失败operation is not well\",\"data\":[]}";
			JSONObject json = JSONObject.fromObject(str);
			return str;
		}
	}

	@RequestMapping(value = "/myFriendsOrFansList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	// 我的好友列表/粉丝列表接口  http://IP: 端口/LeSports/friend/myFriendsOrFansList
	public String myFriendsOrFansList(String userId, String flag) {
		JSONArray jsonArray = new JSONArray();
		// 我的好友列表
		if (flag.equals("0")) {
			List<UserInfo> userFriendInfo = userFriendService.myFriendsList(userId, flag);// 获得所有好友
			Iterator iterator = userFriendInfo.iterator();
			JSONObject jsonmyFriends = null;
			// 遍历所有好友信息
			while (iterator.hasNext()) {
				UserInfo userInfomyFriends = (UserInfo) iterator.next();
				jsonmyFriends = JSONObject.fromObject(userInfomyFriends);
				String birth = (String) jsonmyFriends.get("birthday");
				if (!birth.isEmpty()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
					java.util.Date birthd = null;
					try {
						birthd = sdf.parse(birth);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date(System.currentTimeMillis());
					int age = date.getYear() - birthd.getYear();
					jsonmyFriends.put("age", age);	
				}
				jsonmyFriends.put("flag", "0");
				//控制台输出
				System.out.println("myFriendsList-----userId" + userId + "测试：flag---" + flag+"---好友标志"+jsonmyFriends.get("flag"));
				jsonmyFriends.discard("birthday");
				jsonArray.add(jsonmyFriends);
			}
		}
		if (flag.equals("1")) {
			List<UserInfo> userFansInfo = userFriendService.myFansList(userId, flag);// 获得所有粉丝
			Iterator iterator = userFansInfo.iterator();
			JSONObject jsonmyFans = null;
			// 遍历所有粉丝信息
			while (iterator.hasNext()) {
				UserInfo userInfomyFans = (UserInfo) iterator.next();
				jsonmyFans = JSONObject.fromObject(userInfomyFans);
				String birth = (String) jsonmyFans.get("birthday");
				if (!birth.isEmpty()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
					java.util.Date birthd = null;
					try {
						birthd = sdf.parse(birth);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date(System.currentTimeMillis());
					int age = date.getYear() - birthd.getYear();
					jsonmyFans.put("age", age);
				}
					jsonmyFans.put("flag", "1");
					//获得粉丝ID
					String fanid = (String) jsonmyFans.get("userId");
					List<UserInfo> userFriendInfo = userFriendService.myFriendsList(userId, flag);// 获得所有好友
					Iterator iterator1 = userFriendInfo.iterator();
					JSONObject jsonfans = null;
					// 遍历所有好友信息
					while (iterator1.hasNext()) {
						UserInfo usermyFriends = (UserInfo) iterator1.next();
						JSONObject jsonFriends = JSONObject.fromObject(usermyFriends);
						if(fanid.equals(jsonFriends.get("userId"))){
							jsonmyFans.put("flag", "0");
						}
					}

				//控制台输出
				System.out.println("myFansList-----" + userId + "测试：flag---" + flag+"---好友标志"+jsonmyFans.get("flag")+jsonmyFans.get("nickName"));
				jsonmyFans.discard("birthday");
				jsonArray.add(jsonmyFans);
			}
		}
		//返回数据给APP端
		String str="{\"resCode\":\"0\",\"resMsg\":\"\",\"data\":"+jsonArray.toString()+"}";
		return str;
	}

	@RequestMapping(value = "/getUserImInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	// 用户粉丝、好友、动态数  http://IP: 端口/LeSports/friend/getUserImInfo
	public String getUserImInfo(String userId) {
		List<UserInfo> userFriendsInfo = userFriendService.myFriendsList(userId, "0");// 获得所有好友
		List<UserInfo> userFansInfo = userFriendService.myFansList(userId, "1");// 获得所有粉丝

		String friendsnum = String.valueOf(userFriendsInfo.size());// 获得所有好友数量
		String fansnum = String.valueOf(userFansInfo.size());// 获得所有粉丝数量
		// 获得用户的所有动态数量
		String dongtaishu = null;
		try {
			dongtaishu = String.valueOf(circleService.getCountOfAllcircle(userId));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.put("countOfGuanzhu", friendsnum);
		json.put("countOfFensi", fansnum);
		json.put("countOfDongtai", dongtaishu);
		json.put("uId", userId);
		// 控制台输出
		System.out.println("getUserImInfo---userId" + userId + "测试：--" + json.toString());
		// 返回数据给APP端
		String str = "{\"resCode\":\"0\",\"resMsg\":\"\",\"data\":[" + json + "]}";
		return str;
	}
	
	
	@RequestMapping(value = "/getFriendInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	// 好友详情  http://IP: 端口/LeSports/friend/getFriendInfo
	public String getFriendInfo(String userId) {
		UserInfo UserFriendInfo = userFriendService.getFriendInfo(userId);// 获得好友信息
		List<UserInfo> userFriendsInfo = userFriendService.myFriendsList(userId, "0");// 获得所有好友
		List<UserInfo> userFansInfo = userFriendService.myFansList(userId, "1");// 获得所有粉丝
		
		String friendsnum = String.valueOf(userFriendsInfo.size());// 获得所有好友数量
		String fansnum = String.valueOf(userFansInfo.size());// 获得所有粉丝数量

		JSONObject jsonmyFans = JSONObject.fromObject(UserFriendInfo);
		String birth = (String) jsonmyFans.get("birthday");
		//判断生日存不存在
		jsonmyFans.put("age", null);
		if (!birth.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
			java.util.Date birthd = null;
			try {
				birthd = sdf.parse(birth);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			int age = date.getYear() - birthd.getYear();
			jsonmyFans.put("age", age);
		}
		jsonmyFans.discard("birthday");
		jsonmyFans.put("countOfFriend", friendsnum);
		jsonmyFans.put("countOfFan", fansnum);
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonmyFans);
		//控制台输出
		System.out.println("getFriendInfo------"+ userId + "测试："+jsonmyFans.toString());
		//返回数据给APP端
		String str="{\"resCode\":\"0\",\"resMsg\":\"\",\"data\":"+jsonArray.toString()+"}";
		return str;
	}

	/*
	 * 通过乐动力号或者昵称获取好友信息  by刘亚中
	 */
	@RequestMapping(value="/findFriends", produces = "text/html;charset=UTF-8")
	@ResponseBody
	//http://IP: 端口/LeSports/friend/findFriends
	public String searchFriend(String le_id,String userId)
	{		
		try {
			String strTemp = new String(le_id.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println("转换后的le_id" + strTemp);
			le_id = strTemp;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<UserInfo2> userInfos=userFriendService.searchFriend(le_id, userId);
		System.out.println("输出le_id"+le_id);
		
		
		//System.out.println(userInfos);
		List<UserInfo2> userInfo2s =userFriendService.getAll(le_id);
		Iterator it = userInfos.iterator();
		List<UserInfo2> saveData=new ArrayList<UserInfo2>();
		List<UserInfo2> delData=new ArrayList<UserInfo2>();
		if(!userInfos.isEmpty())
		{
			while(it.hasNext())
		   {
				 UserInfo2 obj = (UserInfo2)it.next();
				 System.out.println(obj);
				 delData.add(obj);
				 obj.setFlag("0");
				 saveData.add(obj);
				 System.out.println("-------++++++++"+obj);
		   }
			userInfos.removeAll(delData);
			userInfos.addAll(saveData);
		Iterator it2=userInfo2s.iterator();
		while(it2.hasNext())
		{
			UserInfo2 obj2=(UserInfo2)it2.next();
			
			Iterator it3=userInfos.iterator();
			boolean state=false;
			while(it3.hasNext())
			{
				UserInfo2 obj3=(UserInfo2)it3.next();
				if(!(obj2.getUserId().equals(obj3.getUserId()))&&(!(obj2.getUserId().equals(userId))))
				{
					state=true;
					continue;
				}
				else
					{
						state=false;
						break;
					}
			}
			if(state)
				userInfos.add(obj2);
		 }
		}
		else
		{
			Iterator it2=userInfo2s.iterator();
			while(it2.hasNext())
			{
				UserInfo2 obj2=(UserInfo2)it2.next();
					userInfos.add(obj2);
			 }
		}
		String  json=JSONSerializer.toJSON(userInfos).toString();
		System.out.println(json);
		if(!userInfos.isEmpty())
		{
			String str="{\"resCode\":\"0\",\"resMsg\":\"\",\"data\":"+json+"}";
			System.out.println("findFriends------userId"+ userId + "测试："+str);
			return str;
		}else {
			json="{\"resCode\":1,\"resMsg\":\"\",\"data\":[]}";
			System.out.println("findFriends------userId"+ userId);
			return json;
		}
	}
}
	
	
	
	
//	@RequestMapping("/recommendFriend")
//	@ResponseBody
//	//http://IP: 端口/LeSports/friend/recommendFriend
//	public String recommendFriend(String userId)
//	{
//		//获得所有用户信息
//		List<UserInfo> userInfo=userFriendService.recommendFriends(userId);
//		// 获得该搜索用户的所有好友信息
//		List<UserInfo> userFriendsInfo = userFriendService.myFriendsList(userId, "0");
//		//设置迭代器
//		Iterator iterator = userInfo.iterator();
//		Iterator iteratorFriend = userFriendsInfo.iterator();
//				
//		JSONObject jsonuserInfo = null;
//		JSONObject jsonuserFriendsInfo = null;
//				
//		JSONArray jsonArray = new JSONArray();
//		// 遍历所有用户信息
//			while (iterator.hasNext()) {
//				UserInfo userInfoone = (UserInfo) iterator.next();//取出一个用户的信息
//				jsonuserInfo = JSONObject.fromObject(userInfoone);//将其转换成JSONObject对象
//				
//			}
//		
//		
//		return userId;
//	}

