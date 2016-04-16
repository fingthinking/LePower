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
import android.widget.EditText;
import android.widget.Toast;

import com.lepower.App;
import com.lepower.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.callback.HttpCallback;
import com.lepower.model.MessageObj;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;

@ContentView(R.layout.activity_psw_by_email)
public class PswByEmailActivity extends BaseActivity {

	@ViewInject(R.id.update_email)
	private EditText et_email;
	@ViewInject(R.id.yanzhengma)
	private EditText et_yanzhengma;
	@ViewInject(R.id.password)
	private EditText et_password;
	@ViewInject(R.id.repassword)
	private EditText et_repassword;

	private String yanzhengma;

	/* 获得控件 */
	public void initControls() {
		et_email = (EditText) findViewById(R.id.update_email);
		et_yanzhengma = (EditText) findViewById(R.id.yanzhengma);
		et_password = (EditText) findViewById(R.id.password);
		et_repassword = (EditText) findViewById(R.id.repassword);

	}

	@Event(value = { R.id.get_yanzhengma, R.id.update, R.id.update_switch,
			R.id.back })
	private void click(View view) {
		switch (view.getId()) {
		case R.id.back:// 返回
			Intent intent = new Intent(PswByEmailActivity.this,
					LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.update_switch:// 切换至手机号修改密码
			Intent intent1 = new Intent(PswByEmailActivity.this,
					PswByTelActivity.class);
			startActivity(intent1);
			break;
		case R.id.get_yanzhengma:// 邮箱获取验证码
			if (et_email.getText().toString().trim().equals("")) {
				ToastUtils.showShort("邮箱地址不能为空");
			} else if (isEmail(et_email.getText().toString().trim()) == false) {
				ToastUtils.showShort("邮箱格式不正确");
			} else {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("email", et_email.getText().toString().trim());
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
										yanzhengma = jsonObject
												.getString("verCode");
										ToastUtils.showShort("验证码已发送至邮箱,请查看");
									}else{
										ToastUtils.showShort(jsonObject.getString("resMsg"));
									}
								} catch (Exception e) {
									LogUtils.e(e.getMessage());
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
		case R.id.update:// 修改
			if (basicEmailUpdate()) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("email", et_email.getText().toString().trim());
				params.put("userPwd", et_password.getText().toString().trim());
				HttpUtils.post(LeUrls.FIND_PWD_BY_EMAIL, params,
						new HttpCallback<String>() {
							@Override
							public void onSuccess(String response) {
								// TODO Auto-generated method stub
								LogUtils.e(response);
								 Gson gson = new Gson();
								 Type type = new TypeToken<MessageObj<User>>(){}.getType();
								 MessageObj<User> userMsg = gson.fromJson(response,type);
								 if(userMsg.getResCode().equals("0")){
									 ToastUtils.showShort(userMsg.getResMsg());
									 Intent intent = new Intent(PswByEmailActivity.this,LoginActivity.class);
									 startActivity(intent);
									 finish();
								 }else{
									 ToastUtils.showShort(userMsg.getResMsg());
								 }
							}
						});
			
			}

			break;

		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
	}

	// 邮箱找回密码基本信息验证
	public boolean basicEmailUpdate() {
		if (et_email.getText().toString().trim().equals("")) {
			Toast.makeText(this, "邮箱地址不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (isEmail(et_email.getText().toString().trim()) == false) {
			Toast.makeText(this, "邮箱地址填写有误", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!et_yanzhengma.getText().toString().trim().equals(yanzhengma)) {
			Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
		}
		if (et_password.getText().toString().trim().equals("")
				|| et_password.getText().toString().trim().length() < 6
				|| et_password.getText().toString().trim().length() > 20) {
			Toast.makeText(this, "请输入6-20位密码", Toast.LENGTH_LONG).show();
			return false;
		}
		if (!(et_password.getText().toString().trim().equals(et_repassword
				.getText().toString().trim()))) {
			Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	// 判断格式是否为Email
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

}
