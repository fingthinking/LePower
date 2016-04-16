package com.lepower.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.model.FriendInfo;
import com.lepower.model.Message;
import com.lepower.model.SelfInfo;

/**
 * 进行json解析数据
 * @author Administrator
 *
 */
public class FriendInfo_JsonParse {
	
	
	public static int count = 0;

	public static List<FriendInfo> friendInfos;
	
	public static List<SelfInfo> selfInfos;
	
	/**
	 * 解析数据
	 * @param result 需要解析的字符串
	 * @return 解析后的list集合
	 */
	public static List<FriendInfo> jsonParse(String result) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Type type = (Type) new TypeToken<Message<FriendInfo>>() {}.getType();
//		List<FriendInfo> friendInfos = gson.fromJson(result, type);
		Message<FriendInfo> message = gson.fromJson(result, type);
		friendInfos = new ArrayList<FriendInfo>();
		for (int i = 0;i<message.getData().size();i++) {
			FriendInfo info = message.getData().get(i);
			friendInfos.add(info);
		}
		return friendInfos;
	}
	
	public static String jsonParseToString(String result){
		String resCode = null;
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(result);
			resCode = jsonObject.getString("resCode");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		Log.d("heheda", "resCode"+resCode);
		return resCode;
	}
	
	/**
	 * 解析数据
	 * @param result 需要解析的字符串
	 * @return 解析后的list集合
	 */
	public static List<SelfInfo> jsonParseToSelf(String result) {
		// TODO Auto-generated method stub
		Log.e("HEHEDA", result);
		Gson gson = new Gson();
		Type type = (Type) new TypeToken<Message<SelfInfo>>() {}.getType();
//		List<FriendInfo> friendInfos = gson.fromJson(result, type);
		Message<SelfInfo> message = gson.fromJson(result, type);
		selfInfos = new ArrayList<SelfInfo>();
		for (int i = 0;i<message.getData().size();i++) {
			SelfInfo info = message.getData().get(i);
			selfInfos.add(info);
		}
		return selfInfos;
	}
	
	
}

