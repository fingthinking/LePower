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
import android.widget.TextView;

import com.lepower.App;
import com.lepower.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageObj;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.TextViewUtils;
import com.lepower.utils.ToastUtils;

@ContentView(R.layout.bind_email)
public class BindEmailActivity extends BaseActivity {
	@ViewInject(R.id.bind_email)
	private EditText et_email;
	@ViewInject(R.id.bind_email_pwd)
	private EditText et_pwd;
	@ViewInject(R.id.bind_title_email)
	private TextView tv_title;// 绑定标题

	private String qqNum;
	private String weiboNum;
	private IUserDao userDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		userDao = new UserDaoImpl();
		Intent i = getIntent();
		qqNum = i.getStringExtra("qqNum");
		weiboNum = i.getStringExtra("weiboNum");
		if (qqNum != null && !qqNum.equals("")) {
			tv_title.setText("QQ账号绑定");
		} else {
			tv_title.setText("微博账号绑定");
		}

	}

	@Event(value = { R.id.bind_bind, R.id.bind_back_back,
			R.id.bind_switch_to_tel })
	private void click(View view) {
		switch (view.getId()) {
		case R.id.bind_bind: {
			if (TextViewUtils.checkEmpty(et_email, et_pwd)) {
				ToastUtils.showShort("手机号或密码不能为空");
				break;
			}
			String email = et_email.getText().toString().trim();
			String userPwd = et_pwd.getText().toString().trim();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("qqNum", qqNum);
			params.put("weiboNum", weiboNum);
			params.put("email", email);
			params.put("userPwd", userPwd);
			String url;
			if(!TextUtils.isEmpty(qqNum)){
				url = LeUrls.BIND_QQ_URL;
			}else{
				url = LeUrls.BIND_WEIBO_URL;
			}
			HttpUtils.post(url, params,
					new HttpCallback<String>() {
						@Override
						public void onSuccess(String response) {
							// TODO Auto-generated method stub
							LogUtils.e(response);
							// ///
							// TODO Auto-generated method stub
							Gson gson = new Gson();
							Type type = new TypeToken<MessageObj<User>>() {
							}.getType();
						
							MessageObj<User> userMsg = gson.fromJson(response, type);
							if (userMsg.getResCode().equals("0")) {
								// 成功了，保存用户数据
								userDao.saveUserNow(userMsg.getData());
								LogUtils.e(response);
								Intent intent = new Intent(BindEmailActivity.this, MainActivity.class);
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
		case R.id.bind_back_back: {
			Intent intent = new Intent(BindEmailActivity.this,
					SelectActivity.class);
			startActivity(intent);
			finish();
		}
			break;
		case R.id.bind_switch_to_tel: {
			Intent intent = new Intent(BindEmailActivity.this,
					BindTelActivity.class);
			intent.putExtra("qqNum", qqNum);
			intent.putExtra("weiboNum", weiboNum);
			startActivity(intent);
			finish();
		}
			break;
		default:
			break;
		}
	}

}
