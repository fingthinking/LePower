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
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lepower.R;
import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageObj;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.lepower.utils.YanZhengMaUtils;

@ContentView(R.layout.activity_update_email)
public class UpdateEmailActivity extends BaseActivity {

	private String email;
	private String numcode;
	private String newEmail;
	@ViewInject(R.id.old_email)
	private TextView etEmail;
	@ViewInject(R.id.new_email)
	private EditText etNewEmail;
	@ViewInject(R.id.yanzhengma_email)
	private EditText etYanZheng;

	private IUserDao userDao;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		email = getIntent().getStringExtra("email");
		if (!TextUtils.isEmpty(email)) {
			etEmail.setText(email);
		}
		userDao = new UserDaoImpl();
		etNewEmail.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean focus) {
				// TODO Auto-generated method stub
				if (focus) {
					LogUtils.e("修改了验证码");
					numcode = "---";
				}
			}
		});

	}

	@Event(value = { R.id.email_back, R.id.get_email_yanzhengma,
			R.id.email_update })
	private void click(View view) {
		switch (view.getId()) {
		case R.id.email_back: {
			finish();
		}
			break;
		case R.id.get_email_yanzhengma: {
			if (etNewEmail.getText().toString().trim().equals("")) {
				ToastUtils.showShort("邮箱地址不能为空");
			} else if (isEmail(etNewEmail.getText().toString().trim()) == false) {
				ToastUtils.showShort("邮箱格式不正确");
			} else {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("email", etNewEmail.getText().toString().trim());
				HttpUtils.post(LeUrls.REQUEST_EMAIL_CODE, params,
						new HttpCallback<String>() {
							@Override
							public void onSuccess(String response) {
								// TODO Auto-generated method stub
								try {
									JSONObject jsonObject = new JSONObject(
											response);
									String resCode = jsonObject
											.getString("resCode");
									if (resCode.equals("0")) {
										numcode = jsonObject
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
		}
		case R.id.email_update: {
			String yanzheng = etYanZheng.getText().toString().trim();
			if (!yanzheng.equals("")) {
				if (!yanzheng.equals(numcode)) {
					ToastUtils.showShort("验证码输入不正确");
					break;
				}
			} else {
				ToastUtils.showShort("验证码不能为空");
				break;
			}
			newEmail = etNewEmail.getText().toString().trim();
			user = userDao.getUserNow();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", user.getUserId());
			params.put("newUserEmail", newEmail);
			HttpUtils.post(LeUrls.UPDATE_EMAIL_NUM, params,
					new HttpCallback<String>() {
						@Override
						public void onSuccess(String response) {
							// TODO Auto-generated method stub
							Gson gson = new Gson();
							Type type = new TypeToken<MessageObj<User>>() {
							}.getType();
							MessageObj<User> userMsg = gson.fromJson(response,
									type);
							if (userMsg.getResCode().equals("0")) {
								ToastUtils.showShort("邮箱修改成功");
								ToastUtils.showShort(userMsg.getResMsg());
								user.setEmail(newEmail);
								userDao.saveUserNow(user);
								Intent intent = new Intent(
										UpdateEmailActivity.this,
										UpdateUserInfoActivity.class);
								intent.putExtra("email", newEmail);
								setResult(RESULT_OK, intent);
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
