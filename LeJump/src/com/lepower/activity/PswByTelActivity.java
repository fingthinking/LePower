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
import android.widget.EditText;

import com.lepower.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lepower.callback.HttpCallback;
import com.lepower.model.MessageObj;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.lepower.utils.YanZhengMaUtils;

@ContentView(R.layout.activity_pwd_by_tel)
public class PswByTelActivity extends BaseActivity {
	public static final int SHOW_RESPONSE = 0;

	private int numcode;

	@ViewInject(R.id.yanzhengma)
	private EditText et_yanzhengma;
	@ViewInject(R.id.update_tel)
	private EditText et_tel;
	@ViewInject(R.id.password)
	private EditText et_password;
	@ViewInject(R.id.repassword)
	private EditText et_repassword;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Event(value = { R.id.get_yanzhengma, R.id.update_psw,
			R.id.switch_to_email, R.id.back_tel })
	private void click(View view) {
		switch (view.getId()) {
		case R.id.get_yanzhengma: {
			String phoneNum = et_tel.getText().toString().trim();
			if (phoneNum.length() != 11 || !TextUtils.isDigitsOnly(phoneNum)) {
				ToastUtils.showShort("请正确输入11位手机号");
				break;
			} else if (HttpUtils.isNetWork(App.context)) {
				numcode = YanZhengMaUtils.sendRequestWithHttpClient(phoneNum);
			} else {
				ToastUtils.showShort("无网络连接");
			}
		}
			break;
		case R.id.update_psw: {
			String phoneNum = et_tel.getText().toString().trim();
			if (phoneNum.length() != 11 || !TextUtils.isDigitsOnly(phoneNum)) {
				ToastUtils.showShort("请正确输入11位手机号");
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
				if (Integer.parseInt(yanzheng) != numcode) {
					ToastUtils.showShort("验证码输入不正确");
					break;
				}
			} else {
				ToastUtils.showShort("验证码不能为空");
				break;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("phoneNum", phoneNum);
			params.put("userPwd", passwd);
			HttpUtils.post(LeUrls.FIND_PWD_BY_PHONE, params,
					new HttpCallback<String>() {

						@Override
						public void onSuccess(String response) {
							LogUtils.e(response);
							 Gson gson = new Gson();
							 Type type = new TypeToken<MessageObj<User>>(){}.getType();
							 MessageObj<User> userMsg = gson.fromJson(response,type);
							 if(userMsg.getResCode().equals("0")){
								 ToastUtils.showShort(userMsg.getResMsg());
								 Intent intent = new Intent(PswByTelActivity.this,LoginActivity.class);
								 startActivity(intent);
								 finish();
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
			break;
		case R.id.switch_to_email: {
			Intent intent = new Intent(PswByTelActivity.this,PswByEmailActivity.class);
			startActivity(intent);
			finish();
		}
			break;
		case R.id.back_tel: {
			 Intent intent = new Intent(PswByTelActivity.this,LoginActivity.class);
			 startActivity(intent);
			 finish();
		}
			break;

		default:
			break;
		}
	}

}
