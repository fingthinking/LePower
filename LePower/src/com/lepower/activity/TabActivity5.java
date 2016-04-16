package com.lepower.activity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lepower.R;
import com.lepower.callback.HttpCallback;
import com.lepower.callback.LeQQListener;
import com.lepower.callback.LeWeiboAuthListener;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageObj;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

public class TabActivity5 extends Activity implements OnClickListener {

	private RelativeLayout rlPersonDataSet, rlRecommend,
			rlAppUpdate, rlAboutLePower;
	
	TextView btnQQ;
	TextView btnWeibo;
	
	
	IUserDao userDao;
	User user;

	/** AuthInfo对象 */
	private AuthInfo mAuthInfo;// 微博
	/** SsoHandler对象 */
	private SsoHandler mSsoHandler;
	private LeWeiboAuthListener mWeiboListener;
	private static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";// 微博redirect_url
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";// 微博的请求权限
	// QQ授权使用
	private Tencent mTencent;
	private IUiListener qqLoginListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab5);
		initView();
		
		userDao = new UserDaoImpl();
		user = userDao.getUserNow();

		if (TextUtils.isEmpty(user.getQqNum())) {
			btnQQ.setText("绑定");
		} else {
			btnQQ.setText("解绑");
		}

		// QQ授权初始化；
		mTencent = Tencent.createInstance(App.QQ_APP_ID,
				TabActivity5.this);
		qqLoginListener = new LeQQListener(mTencent,
				new HttpCallback<String>() {
					@Override
					public void onSuccess(String qqOpenId) {
						// TODO Auto-generated method stub
						if (qqOpenId == null) { // 授权成功跳转进入成功页面
							ToastUtils.showShort("对不起，该QQ已绑定过其他帐号");
						} else { // 未绑定过开始绑定
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("qqNum", qqOpenId);
							params.put("userId", user.getUserId());
							HttpUtils.post(LeUrls.BIND_QQ_URL2, params,new HttpCallback<String>(){
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
										ToastUtils.showShort("绑定成功");
										btnQQ.setText("解绑");
									}else{
										ToastUtils.showShort(userMsg.getResMsg());
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
							
							
						}
					}

				});
		

		if (TextUtils.isEmpty(user.getWeiboNum())) {
			btnWeibo.setText("绑定");
		} else {
			btnWeibo.setText("解绑");
		}

		// 微博授权初始化
		// 实例化AuthInfo AuthListener SsoHandler对象
		mAuthInfo = new AuthInfo(TabActivity5.this, App.WEIBO_APPKEY,
				REDIRECT_URL, SCOPE);
		mSsoHandler = new SsoHandler(TabActivity5.this, mAuthInfo);
		mWeiboListener = new LeWeiboAuthListener(new HttpCallback<String>() {
			@Override
			public void onSuccess(String weiboId) {
				// TODO Auto-generated method stub
				if (weiboId == null) { // 授权成功跳转进入成功页面
					btnWeibo.setText("解绑");
					ToastUtils.showShort("对不起，该微博已绑定过其他帐号");
				} else { // 未绑定过，开始绑定
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("weiboNum", weiboId);
					params.put("userId", user.getUserId());
					HttpUtils.post(LeUrls.BIND_WEIBO_URL2, params,new HttpCallback<String>(){
						@Override
						public void onSuccess(String response) {
							// TODO Auto-generated method stub
							LogUtils.e("heheheh"+response);
							Gson gson = new Gson();
							Type type = new TypeToken<MessageObj<User>>() {
							}.getType();
							
							MessageObj<User> userMsg = gson.fromJson(response, type);
							if (userMsg.getResCode().equals("0")) {
								// 成功了，保存用户数据
								userDao.saveUserNow(userMsg.getData());
								LogUtils.e(response);
								ToastUtils.showShort("绑定成功");
								btnWeibo.setText("解绑");
							}else{
								ToastUtils.showShort(userMsg.getResMsg());
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
					
				}
			}

		});

	}

	private void initView() {
		// TODO Auto-generated method stub
		rlPersonDataSet = (RelativeLayout) findViewById(R.id.rlPersonDataSet);
		rlRecommend = (RelativeLayout) findViewById(R.id.rlRecommend);
		rlAppUpdate = (RelativeLayout) findViewById(R.id.rlAppUpdate);
		rlAboutLePower = (RelativeLayout) findViewById(R.id.rlAboutLePower);
		btnQQ = (TextView) findViewById(R.id.btn_qq);
		btnWeibo = (TextView)findViewById(R.id.btn_weibo);
		
		btnQQ.setOnClickListener(this);
		btnWeibo.setOnClickListener(this);
		rlPersonDataSet.setOnClickListener(this);
		rlRecommend.setOnClickListener(this);
		rlAppUpdate.setOnClickListener(this);
		rlAboutLePower.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		user = userDao.getUserNow();
		switch (view.getId()) {
		case R.id.rlPersonDataSet:
			Log.d("heheda", "rlPersonDataSet");
			Intent intent=new Intent(this,UpdateUserInfoActivity.class);
			startActivity(intent);
			
			break;
			
		case R.id.btn_qq: {
			if (TextUtils.isEmpty(user.getQqNum())) {
				if (!mTencent.isSessionValid()) {
					mTencent.login(TabActivity5.this, "all",
							qqLoginListener);
				}
			} else {
				// btnQQ.setText("解绑QQ");
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", user.getUserId());
				HttpUtils.post(LeUrls.UNBIND_QQ, params,
						new HttpCallback<String>() {
							@Override
							public void onSuccess(String response) {
								// TODO Auto-generated method stub
								LogUtils.e(response);
								Gson gson = new Gson();
								Type type = new TypeToken<MessageObj<User>>() {
								}.getType();

								MessageObj<User> userMsg = gson.fromJson(
										response, type);
								if (userMsg.getResCode().equals("0")) {
									// 成功了，保存用户数据
									userDao.saveUserNow(userMsg.getData());
									ToastUtils.showShort("解绑成功");
								}
								btnQQ.setText("绑定");
							}
						});
			}

		}
			break;
		case R.id.btn_weibo:{
			if (TextUtils.isEmpty(user.getWeiboNum())) {
				mSsoHandler.authorize(mWeiboListener);
			} else {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", user.getUserId());
				HttpUtils.post(LeUrls.UNBIND_WEIBO, params,
						new HttpCallback<String>() {
							@Override
							public void onSuccess(String response) {
								// TODO Auto-generated method stub
								LogUtils.e(response);
								Gson gson = new Gson();
								Type type = new TypeToken<MessageObj<User>>() {
								}.getType();

								MessageObj<User> userMsg = gson.fromJson(
										response, type);
								if (userMsg.getResCode().equals("0")) {
									// 成功了，保存用户数据
									userDao.saveUserNow(userMsg.getData());
									btnWeibo.setText("绑定");
								}
							}
						});

			}
		}
		break;

		case R.id.rlRecommend:
			Log.d("heheda", "rlRecommend");
			break;
		case R.id.rlAppUpdate:
			Log.d("heheda", "rlAppUpdate");
			break;
		case R.id.rlAboutLePower:
			Log.d("heheda", "rlAboutLePower");
			break;
		default:
			break;

		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		// 获取QQ的回调信息
		Tencent.onActivityResultData(requestCode, resultCode, data,
				qqLoginListener);
		if (mTencent != null) {
			mTencent.onActivityResult(requestCode, resultCode, data);
		}
		// 获取微博的回调信息
		mSsoHandler.authorizeCallBack(requestCode, resultCode, data);

		super.onActivityResult(requestCode, resultCode, data);
	}
}
