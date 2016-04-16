package com.lepower.callback;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.lepower.activity.LoginActivity;
import com.lepower.activity.SelectActivity;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LogUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * 微博监听
 * @author fing
 *
 */
public class LeWeiboListener implements WeiboAuthListener {
	
	public LeWeiboListener() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onComplete(Bundle values) {
		Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values);
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			String path="https://api.weibo.com/2/users/show.json?access_token=" + mAccessToken.getToken() + "&uid=" + mAccessToken.getUid();
			HttpUtils.get(path, null, new HttpCallback<String>(){
				@Override
				public void onSuccess(String response) {
					// TODO Auto-generated method stub
					LogUtils.e(response);
				}
			});
			
		}

	} 
	@Override
	public void onWeiboException(WeiboException e) {
		// TODO Auto-generated method stub
		e.printStackTrace();
	}

}
