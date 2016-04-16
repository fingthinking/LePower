package com.lepower.activity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.lepower.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageObj;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;

@ContentView(R.layout.activity_register_email)
public class RegisterEmailActivtiy extends BaseActivity {
	@ViewInject(R.id.register_email_p)
	private EditText et_email;
	@ViewInject(R.id.register_password)
	private EditText et_password;
	@ViewInject(R.id.register_repassword)
	private EditText et_repassword;
	@ViewInject(R.id.register_yanzhengma_email)
	private EditText et_yanzhengma;

	private IUserDao userDao;
	private String yanzhengma; // 验证码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userDao = new UserDaoImpl();
		et_email.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean focus) {
				// TODO Auto-generated method stub
				if (focus) {
					LogUtils.e("修改了验证码");
					yanzhengma = "---";
				}
			}
		});
	}

	@Event(value = { R.id.get_yanzhengma_email, R.id.register_next_step,
			R.id.register_back, R.id.register_switch_to_tel })
	private void click(View view) {
		switch (view.getId()) {
		case R.id.get_yanzhengma_email: {
			String email = et_email.getText().toString().trim();
			if (!isEmail(email)) {
				ToastUtils.showShort("请输入正确的邮箱地址");
				break;
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("email", email);
			HttpUtils.post(LeUrls.REQUEST_EMAIL_CODE, params,
					new HttpCallback<String>() {
						@Override
						public void onSuccess(String response) {
							// TODO Auto-generated method stub
							try {
								JSONObject jsonObject = new JSONObject(response);
								String resCode = jsonObject
										.getString("resCode");
								if (resCode.equals("0")) {
									yanzhengma = jsonObject
											.getString("verCode");
									ToastUtils.showShort("验证码已发送至邮箱,请查看");
								} else {
									ToastUtils.showShort(jsonObject
											.getString("resMsg"));
								}
							} catch (Exception e) {
								LogUtils.e(e.getMessage());
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
		// 下一步的按钮
		case R.id.register_next_step: {
			final String email = et_email.getText().toString().trim();
			if (!isEmail(email)) {
				ToastUtils.showShort("请输入正确的邮箱地址");
				break;
			}
			final String passwd = et_password.getText().toString().trim();
			String rePasswd = et_repassword.getText().toString().trim();
			if (passwd.length() < 6 || passwd.length() > 20) {
				ToastUtils.showShort("请确保密码在6~20位");
				break;
			}
			if (!passwd.equals(rePasswd)) {
				ToastUtils.showShort("两次密码输入不一致");
				break;
			}
			String yanzheng = et_yanzhengma.getText().toString().trim();
			if (!yanzheng.equals("")) {
				if (!yanzheng.equals(yanzhengma)) {
					ToastUtils.showShort("验证码输入不正确");
					break;
				}
			} else {
				ToastUtils.showShort("验证码不能为空");
				break;
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userName", email);
			params.put("userPwd", passwd);
			HttpUtils.post(LeUrls.EXIST_USER, params,
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
								Intent intent = new Intent(
										RegisterEmailActivtiy.this,
										RegisterUserInfoActivity.class);
								intent.putExtra("fromEmail", true);
								intent.putExtra("email", email);
								intent.putExtra("userPwd", passwd);
								if (getIntent().getBooleanExtra("qq", false)) {
									intent.putExtra("qq", true);
								}
								if (getIntent().getBooleanExtra("weibo", false)) {
									intent.putExtra("weibo", true);
								}
								startActivity(intent);
								finish();
							} else {
								ToastUtils.showShort("该邮箱已注册，请登录或修改邮箱");
							}
						}

						@Override
						public void onError(Throwable arg0, boolean arg1) {
							// TODO Auto-generated method stub
							if (HttpUtils.isNetWork(App.context)) {
								ToastUtils.showShort("请检查网络状况");
							}
						}
					});
		}
			break;
		case R.id.register_back: {// 返回
			Intent intent = new Intent(RegisterEmailActivtiy.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
		}
			break;
		case R.id.register_switch_to_tel: {// 切换至手机注册
			Intent intent = new Intent(RegisterEmailActivtiy.this,
					RegisterTelActivity.class);
			if (getIntent().getBooleanExtra("qq", false)) {
				intent.putExtra("qq", true);
			}
			if (getIntent().getBooleanExtra("weibo", false)) {
				intent.putExtra("weibo", true);
			}
			startActivity(intent);
			finish();
		}
			break;
		default:
			break;
		}
	}

	// 判断格式是否为Email
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
