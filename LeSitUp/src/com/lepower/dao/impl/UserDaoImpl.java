package com.lepower.dao.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lepower.App;
import com.lepower.dao.IUserDao;
import com.lepower.model.QQUserInfo;
import com.lepower.model.User;
import com.lepower.model.WeiBoUserInfo;
import com.lepower.utils.LogUtils;

public class UserDaoImpl implements IUserDao {
	private static Gson gson;

	public UserDaoImpl() {
		gson = new Gson();
	}

	@Override
	public User getUserNow() {
		// TODO Auto-generated method stub
		SharedPreferences pref = App.context.getSharedPreferences("userNow",
				Context.MODE_PRIVATE);
		User user = gson.fromJson(pref.getString("user", "{}"), User.class);
		if (TextUtils.isEmpty(user.getUserId())) {
			LogUtils.e("user.getUserId:"+user.getUserId());
			return null;
		}
		return user;
	}

	@Override
	public void saveUserNow(User user) {
		// TODO Auto-generated method stub
		SharedPreferences pref = App.context.getSharedPreferences("userNow",
				Context.MODE_PRIVATE);
		pref.edit().putString("user", gson.toJson(user)).commit();
	}

	@Override
	public void deleteUserNow() {
		// TODO Auto-generated method stub
		SharedPreferences pref = App.context.getSharedPreferences("userNow",
				Context.MODE_PRIVATE);
		pref.edit().clear().commit();
	}

	@Override
	public void saveQQInfo(QQUserInfo info) {
		// TODO Auto-generated method stub
		SharedPreferences pref = App.context.getSharedPreferences("userNow",
				Context.MODE_PRIVATE);
		pref.edit().putString("qq_info", gson.toJson(info)).commit();
	}

	@Override
	public QQUserInfo getQQInfo() {
		// TODO Auto-generated method stub
		SharedPreferences pref = App.context.getSharedPreferences("userNow",
				Context.MODE_PRIVATE);
		QQUserInfo info = gson.fromJson(pref.getString("qq_info", "{}"),
				QQUserInfo.class);
		if (TextUtils.isEmpty(info.getOpenid())) {
			return null;
		}
		return info;
	}

	@Override
	public void saveWeiboInfo(WeiBoUserInfo info) {
		// TODO Auto-generated method stub
		SharedPreferences pref = App.context.getSharedPreferences("userNow",
				Context.MODE_PRIVATE);
		pref.edit().putString("weibo_info", gson.toJson(info)).commit();
	}

	@Override
	public WeiBoUserInfo getWeiboInfo() {
		// TODO Auto-generated method stub
		SharedPreferences pref = App.context.getSharedPreferences("userNow",
				Context.MODE_PRIVATE);
		WeiBoUserInfo info = gson.fromJson(pref.getString("weibo_info", "{}"),
				WeiBoUserInfo.class);
		if (TextUtils.isEmpty(info.getId())) {
			return null;
		}
		return info;
	}

}
