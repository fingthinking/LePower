package com.lepower.activity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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

@ContentView(R.layout.activity_update_tel)
public class UpdateTelActivity extends BaseActivity {

	private String phoneNum;
	private int numcode;
	private String newPhoneNum;
	@ViewInject(R.id.tel)
	private TextView etPhoneNum;
	@ViewInject(R.id.new_tel)
	private EditText etNewPhoneNum;
	@ViewInject(R.id.yanzhengma)
	private EditText etYanZheng;

	private IUserDao userDao;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		phoneNum = getIntent().getStringExtra("phoneNum");
		LogUtils.e(phoneNum);
		if (!TextUtils.isEmpty(phoneNum)) {
			etPhoneNum.setText(phoneNum);
		}
		userDao = new UserDaoImpl();
		etNewPhoneNum.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean focus) {
				// TODO Auto-generated method stub
				if (focus) {
					LogUtils.e("修改了验证码");
					numcode = -9923748;
				}
			}
		});

	}

	@Event(value = { R.id.back, R.id.get_yanzhengma, R.id.update })
	private void click(View view) {
		switch (view.getId()) {
		case R.id.back: {
			finish();
		}
			break;
		case R.id.get_yanzhengma: {
			newPhoneNum = etNewPhoneNum.getText().toString().trim();
			if (newPhoneNum.length() != 11 || !TextUtils.isDigitsOnly(phoneNum)) {
				ToastUtils.showShort("请正确输入11位手机号");
				break;
			} else if (HttpUtils.isNetWork(App.context)) {
				numcode = YanZhengMaUtils.sendRequestWithHttpClient(phoneNum);
			} else {
				ToastUtils.showShort("无网络连接");
			}
		}
			break;
		case R.id.update: {
			newPhoneNum = etNewPhoneNum.getText().toString().trim();
			if (newPhoneNum.length() != 11 || !TextUtils.isDigitsOnly(phoneNum)) {
				ToastUtils.showShort("请正确输入11位手机号");
				break;
			}
			String yanzheng = etYanZheng.getText().toString().trim();
			if (!yanzheng.equals("")) {
				if (Integer.parseInt(yanzheng) != numcode) {
					ToastUtils.showShort("验证码输入不正确");
					break;
				}
			} else {
				ToastUtils.showShort("验证码不能为空");
				break;
			}
			user = userDao.getUserNow();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", user.getUserId());
			params.put("newUserPhone", newPhoneNum);
			HttpUtils.post(LeUrls.UPDATE_PHONE_NUM, params,
					new HttpCallback<String>() {
						@Override
						public void onSuccess(String response) {
							// TODO Auto-generated method stub
							ToastUtils.showShort(response);
							Gson gson = new Gson();
							Type type = new TypeToken<MessageObj<User>>() {
							}.getType();
							MessageObj<User> userMsg = gson.fromJson(response,
									type);
							if (userMsg.getResCode().equals("0")) {
								ToastUtils.showShort("手机号码修改成功");
								ToastUtils.showShort(userMsg.getResMsg());
								user.setPhoneNum(newPhoneNum);
								userDao.saveUserNow(user);
								Intent intent = new Intent(
										UpdateTelActivity.this,
										UpdateUserInfoActivity.class);
								intent.putExtra("phone", newPhoneNum);
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
}
