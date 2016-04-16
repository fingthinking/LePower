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
import com.lepower.utils.YanZhengMaUtils;

@ContentView(R.layout.activity_register_tel)
public class RegisterTelActivity extends BaseActivity {

	@ViewInject(R.id.register_tel)
	private EditText et_telephone;
	@ViewInject(R.id.register_yanzhengma)
	private EditText et_yanzhengma;
	@ViewInject(R.id.register_password)
	private EditText et_password;
	@ViewInject(R.id.register_repassword)
	private EditText et_repassword;

	private int numcode;

	private IUserDao userDao;

	public static final int SHOW_RESPONSE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userDao = new UserDaoImpl();
		et_telephone.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean focus) {
				// TODO Auto-generated method stub
				if(focus){
					LogUtils.e("修改了验证码"+focus);
					numcode = -797265;
				}
			}
		});
	}

	/**
	 * 事件监听
	 * 
	 * @param view
	 */
	@Event(value = { R.id.get_yanzhengma, R.id.register_next_step,
			R.id.register_switch_to_email, R.id.register_back})
	private void click(View view) {
		switch (view.getId()) {
		case R.id.register_next_step: {
			
			
			final String phoneNum = et_telephone.getText().toString().trim();
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
			params.put("userName", phoneNum);
			params.put("userPwd", passwd);
			HttpUtils.post(LeUrls.EXIST_USER, params,
					new HttpCallback<String>() {

						@Override
						public void onSuccess(String response) {
							LogUtils.e(response);
							
							 Gson gson = new Gson();
							 Type type = new TypeToken<MessageObj<User>>(){}.getType();
							 MessageObj<User> userMsg = gson.fromJson(response,type);
							 if(userMsg.getResCode().equals("0")){
								 Intent intent = new Intent(RegisterTelActivity.this,
											RegisterUserInfoActivity.class);
									intent.putExtra("fromTel", true);
									intent.putExtra("phoneNum", phoneNum);
									intent.putExtra("userPwd", passwd);
									if(getIntent().getBooleanExtra("qq", false)){
										intent.putExtra("qq", true);
									}
									 if(getIntent().getBooleanExtra("weibo", false)){
										 intent.putExtra("weibo", true);
									 }
									startActivity(intent);
									finish();
							 }else{
								 ToastUtils.showShort("该手机号已注册，请登录或修改手机号");
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
		case R.id.register_back: {
			Intent intent = new Intent(RegisterTelActivity.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
		}
			break;
		case R.id.register_switch_to_email: {
			 Intent intent = new Intent(RegisterTelActivity.this,
			 RegisterEmailActivtiy.class);
			 if(getIntent().getBooleanExtra("qq", false)){
					intent.putExtra("qq", true);
				}
			 if(getIntent().getBooleanExtra("weibo", false)){
				 intent.putExtra("weibo", true);
			 }
			 startActivity(intent);
			 finish();
		}
			break;
		case R.id.get_yanzhengma: {
			String phoneNum = et_telephone.getText().toString().trim();
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
		default:
			break;
		}
	}

}
