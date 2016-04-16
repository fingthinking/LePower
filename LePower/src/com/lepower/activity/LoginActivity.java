package com.lepower.activity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

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
import com.lepower.utils.TextViewUtils;
import com.lepower.utils.ToastUtils;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

//import com.lepower.callback.AuthListener;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
	@ViewInject(R.id.login_username)
	private EditText et_username;
	@ViewInject(R.id.login_password)
	private EditText et_password;
	// QQ授权使用
	private Tencent mTencent;

	/** AuthInfo对象 */
	private AuthInfo mAuthInfo;// 微博
	/** SsoHandler对象 */
	private SsoHandler mSsoHandler;
	private LeWeiboAuthListener mWeiboListener;
	private static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";// 微博redirect_url
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";// 微博的请求权限

	private IUiListener qqLoginListener;

	private IUserDao userDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userDao = new UserDaoImpl();
		if (userDao.getUserNow() != null) {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userDao.getUserNow().getUserId());
			HttpUtils.post(LeUrls.GET_USER_INFO, params,
					new HttpCallback<String>() {
						public void onSuccess(String response) {
							Gson gson = new Gson();
							Type type = new TypeToken<MessageObj<User>>() {
							}.getType();
							MessageObj<User> userMsg = gson.fromJson(response,
									type);
							if (userMsg.getResCode().equals("0")) {
								userDao.saveUserNow(userMsg.getData());
								finish();
							} else {
								ToastUtils.showShort(userMsg.getResMsg());
							}
						}

						@Override
						public void onError(Throwable e, boolean arg1) {
							if (!HttpUtils.isNetWork(App.context)) {
								ToastUtils.showShort("请检查网络状况");
							} else {
								LogUtils.e(e.getMessage());
							}
						}
					});

			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			return;
		}

		initView();

		// QQ授权初始化；
		mTencent = Tencent.createInstance(App.QQ_APP_ID, LoginActivity.this);
		qqLoginListener = new LeQQListener(mTencent,
				new HttpCallback<String>() {
					@Override
					public void onSuccess(String qqOpenId) {
						// TODO Auto-generated method stub
						if (qqOpenId == null) { // 授权成功跳转进入成功页面
							Intent intent = new Intent(LoginActivity.this,
									MainActivity.class);
							startActivity(intent);
							finish();

						} else { // 授权失败进入选择页面
							Intent intent = new Intent(LoginActivity.this,
									SelectActivity.class);
							intent.putExtra("qqNum", qqOpenId);
							startActivity(intent);
						}
					}

					@Override
					public void onError(Throwable e, boolean arg1) {
						if (!HttpUtils.isNetWork(App.context)) {
							ToastUtils.showShort("请检查网络状况");
						} else {
							LogUtils.e(e.getMessage());
						}
					}
				});

		// 微博授权初始化
		// 实例化AuthInfo AuthListener SsoHandler对象
		mAuthInfo = new AuthInfo(LoginActivity.this, App.WEIBO_APPKEY,
				REDIRECT_URL, SCOPE);
		mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
		mWeiboListener = new LeWeiboAuthListener(new HttpCallback<String>() {
			@Override
			public void onSuccess(String weiboId) {
				// TODO Auto-generated method stub
				if (weiboId == null) { // 授权成功跳转进入成功页面
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();

				} else { // 授权失败进入选择页面
					Intent intent = new Intent(LoginActivity.this,
							SelectActivity.class);
					intent.putExtra("weiboNum", weiboId);
					startActivity(intent);
				}
			}

			@Override
			public void onError(Throwable e, boolean arg1) {
				if (!HttpUtils.isNetWork(App.context)) {
					ToastUtils.showShort("请检查网络状况");
				} else {
					LogUtils.e(e.getMessage());
				}
			}
		});

	}

	private void initView() {
		// 初始化从找回密码、注册返回的userName
		Intent intent = getIntent();
		String userName = intent.getStringExtra("userName");
		et_username.setText(userName);
	}

	@Event(value = { R.id.login_forget_password, R.id.login_register,
			R.id.login_login, R.id.login_pass, R.id.login_qq, R.id.login_weibo })
	private void click(View view) {
		switch (view.getId()) {
		// 登陆
		case R.id.login_login: {
			if (TextViewUtils.checkEmpty(et_username, et_password)) {
				ToastUtils.showShort("用户名或密码不能为空");
				break;
			}
			String passwd = et_password.getText().toString().trim();
			if (passwd.length() < 6 || passwd.length() > 20) {
				ToastUtils.showShort("请确保密码在6~20位");
				break;
			}
			String username = et_username.getText().toString().trim();
			// Intent i = getIntent();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userName", username);
			params.put("userPwd", passwd);
			HttpUtils.post(LeUrls.LOGIN_URL, params,
					new HttpCallback<String>() {
						@Override
						public void onSuccess(String response) {
							LogUtils.e(response);
							Gson gson = new Gson();
							Type type = new TypeToken<MessageObj<User>>() {
							}.getType();
							MessageObj<User> userMsg = gson.fromJson(response,
									type);
							if (userMsg.getResCode().equals("0")) {
								LogUtils.e(userMsg.getData().getUserId());
								userDao.saveUserNow(userMsg.getData());
								Intent intent = new Intent(LoginActivity.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								ToastUtils.showShort(userMsg.getResMsg());
							}
						}

						@Override
						public void onError(Throwable e, boolean arg1) {
							if (!HttpUtils.isNetWork(App.context)) {
								ToastUtils.showShort("请检查网络状况");
							} else {
								LogUtils.e(e.getMessage());
							}
						}
					});

		}
			break;
		case R.id.login_forget_password: {
			Intent intent = new Intent(LoginActivity.this,
					PswByTelActivity.class);
			startActivity(intent);
		}
			break;
		// 注册
		case R.id.login_register: {
			Intent intent = new Intent(LoginActivity.this,
					RegisterTelActivity.class);
			startActivity(intent);
			// finish();
		}

			break;
		case R.id.login_pass: {
			Intent intent = new Intent(LoginActivity.this,
					UpdateUserInfoActivity.class);
			startActivity(intent);
			finish();
		}
			break;
		case R.id.login_qq: {
			if (!mTencent.isSessionValid()) {
				mTencent.login(LoginActivity.this, "all", qqLoginListener);
			}
		}
			break;
		case R.id.login_weibo:
			mSsoHandler.authorize(mWeiboListener);
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


	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addCategory(Intent.CATEGORY_HOME);
		startActivity(i);
	}
}
