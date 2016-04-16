package com.lepower.activity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lepower.R;
import com.lepower.callback.HttpCallback;
import com.lepower.model.MessageObj;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;

@ContentView(R.layout.activity_update_pwd)
public class UpdatePwdActivity extends BaseActivity {

	private String userId;
	@ViewInject(R.id.old_pwd)
	private EditText etOldPwd;
	@ViewInject(R.id.new_pwd)
	private EditText etNewPwd;
	@ViewInject(R.id.new_repwd)
	private EditText etReNewPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userId = getIntent().getStringExtra("userId");

	}
	
	@Event(value={R.id.back_pwd,R.id.change_pwd})
	private void click(View view){
		switch (view.getId()) {
		case R.id.back_pwd:
			finish();
			break;
		case R.id.change_pwd:{
			if(check()){
				String oldPwd = etOldPwd.getText().toString().trim();
				String newPwd = etNewPwd.getText().toString().trim();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", userId);
				params.put("oldPwd", oldPwd);
				params.put("newPwd", newPwd);
				HttpUtils.post(LeUrls.UPDATE_PWD_NUM, params, new HttpCallback<String>() {
					@Override
					public void onSuccess(String response) {
						// TODO Auto-generated method stub
						 Gson gson = new Gson();
						 Type type = new TypeToken<MessageObj<User>>(){}.getType();
						 MessageObj<User> userMsg = gson.fromJson(response,type);
						 if(userMsg.getResCode().equals("0")){
							 ToastUtils.showShort("密码修改成功");
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
			
			
		}
			break;
		default:
			break;
		}
	}
	/**
	 * 验证条件
	 * @return
	 */
	private boolean check(){
		String oldPwd = etOldPwd.getText().toString().trim();
		String newPwd = etNewPwd.getText().toString().trim();
		String reNewPwd = etReNewPwd.getText().toString().trim();
		if(oldPwd.length() < 6 || oldPwd.length() > 20){
			ToastUtils.showShort("密码长度在6~20位");
			return false;
		}
		if(newPwd.length() < 6 || newPwd.length() > 20){
			ToastUtils.showShort("密码长度在6~20位");
			return false;
		}
		if(!newPwd.equals(reNewPwd)){
			ToastUtils.showShort("两次密码输入不一致");
			return false;
		}
		return true;
	}
}
