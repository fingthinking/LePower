package com.lepower.callback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageObj;
import com.lepower.model.QQUserInfo;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LeQQListener implements IUiListener {
	private String url;
	private Tencent mTencent;
	private IUserDao userDao;
	private HttpCallback<String> callback;

	public LeQQListener(Tencent mTencent, HttpCallback<String> callback) {
		// TODO Auto-generated constructor stub
		this.mTencent = mTencent;
		userDao = new UserDaoImpl();
		this.callback = callback;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComplete(Object response) {
		// TODO Auto-generated method stub
		LogUtils.e(response);
		JSONObject json = (JSONObject) response;
		String openid = "";
		try {
			String access_token = json.getString("access_token");
			openid = json.getString("openid");
			url = "https://graph.qq.com/user/get_user_info?access_token="
					+ access_token + "&oauth_consumer_key=" + App.QQ_APP_ID
					+ "&openid=" + openid + "&format=json";

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtils.e("错误");
		}
		if (openid.equals("")) {
			ToastUtils.showShort("授权失败");
			return;
		}
		// 获取资料信息
		final String qqOpenId = openid;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("qqNum", openid);
		HttpUtils.post(LeUrls.LOGIN_QQ_URL, params, new HttpCallback<String>() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Gson gson = new Gson();
				Type type = new TypeToken<MessageObj<User>>() {
				}.getType();
			
				MessageObj<User> userMsg = gson.fromJson(response, type);
				if (userMsg.getResCode().equals("0")) {
					// 成功了，保存用户数据
					userDao.saveUserNow(userMsg.getData());
					LogUtils.e(response);
					callback.onSuccess(null);
					// Intent intent = new Intent(LoginActivity.this,
					// UpdateUserInfoActivity.class);
					// startActivity(intent);
					// finish();
				} else {
					callback.onSuccess(qqOpenId);
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

		Observable.just(url).subscribeOn(Schedulers.io())
				.subscribe(new Action1<String>() {

					@Override
					public void call(String url) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonOb = mTencent.request(url, null,
									Constants.HTTP_GET);
							jsonOb.put("openid", qqOpenId);
							
							Gson gson = new Gson();
							QQUserInfo qqInfo = gson.fromJson(jsonOb.toString()
									.replace("\\", ""), QQUserInfo.class);
							userDao.saveQQInfo(qqInfo);
						} catch (Exception e) {
							if(!HttpUtils.isNetWork(App.context)){
								ToastUtils.showShort("请检查网络状况");
							}else{
								LogUtils.e(e.getMessage());
							}
						}

					}
				});

	}

	@Override
	public void onError(UiError arg0) {
		// TODO Auto-generated method stub

	}

}
