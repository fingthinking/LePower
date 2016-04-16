package com.lepower.callback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageObj;
import com.lepower.model.User;
import com.lepower.model.WeiBoUserInfo;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * 微博监听
 * @author fing
 *
 */
public class LeWeiboAuthListener implements WeiboAuthListener {
	HttpCallback<String> callback;
	IUserDao userDao;
	
	public LeWeiboAuthListener(HttpCallback<String> callback) {
		// TODO Auto-generated constructor stub
		this.callback = callback;
		userDao = new UserDaoImpl();
	}
	
	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		 ToastUtils.showShort("授权取消");
	}

	@Override
	public void onComplete(Bundle values) {
		Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values);
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			String path="https://api.weibo.com/2/users/show.json?access_token=" + mAccessToken.getToken() + "&uid=" + mAccessToken.getUid();
			
			final String id = values.getString("uid");
//			ToastUtils.showShort("id是"+id);
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("weiboNum", id);
			HttpUtils.post(LeUrls.LOGIN_WEIBO_URL, params, new HttpCallback<String>(){
				
				public void onSuccess(String response) {
					Gson gson = new Gson();
					Type type = new TypeToken<MessageObj<User>>() {
					}.getType();
				
					MessageObj<User> userMsg = gson.fromJson(response, type);
					if (userMsg.getResCode().equals("0")) {
						// 成功了，保存用户数据
						userDao.saveUserNow(userMsg.getData());
						LogUtils.e(response);
						callback.onSuccess(null);
					} else {
						callback.onSuccess(id);
					}
				}
				@Override
				public void onError(Throwable e, boolean arg1) {
					if(!HttpUtils.isNetWork(App.context)){
						ToastUtils.showShort("请检查网络状况");
					}else{
						LogUtils.e(e.getMessage());
					}
				}
				
			});
			
			HttpUtils.get(path, null, new HttpCallback<String>(){
				@Override
				public void onSuccess(String response) {
					// TODO Auto-generated method stub
//					LogUtils.e(response);
					Gson gson = new Gson();
					WeiBoUserInfo weiboInfo = gson.fromJson(response, WeiBoUserInfo.class);
					userDao.saveWeiboInfo(weiboInfo);
				}
				@Override
				public void onError(Throwable e, boolean arg1) {
					if(!HttpUtils.isNetWork(App.context)){
						ToastUtils.showShort("请检查网络状况");
					}else{
						LogUtils.e(e.getMessage());
					}
				}
			});
			
		}

	} 
	@Override
	public void onWeiboException(WeiboException e) {
		// TODO Auto-generated method stub
		ToastUtils.showShort(e.getMessage());
	}

}
