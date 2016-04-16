package com.lepower.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lepower.callback.MyCommonCallback;
import com.lepower.model.FriendAction;
import com.lepower.model.User;
import com.lepower.utils.BeanUtil;
import com.lepower.utils.LeUrls;
import com.lepower.utils.NetUtils;

/**
 * 只有本人的所有说说（如，点击自己昵称或头像时）， 或者只有好友的说说（如，点击好友昵称或头像时）。 
 * @author Administrator
 *
 */
public class PersonalCircleActivity extends BaseCircleActivity{

	private Context context;
	
	private static final String TAG="PersonalCircleActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context=this;
		Log.e("Personalctivity", "OnCreate()");
		getData();
	}

	public void getData() {
		
		Intent intent=getIntent();
		
		final User user=(User) intent.getSerializableExtra("user");
		String userId=user.getUserId();
		
		setUserId(userId);
		setCircleType(BaseCircleActivity.LEPOWER_PERSONAL);
		
		final BeanUtil beanUtil=BeanUtil.getInstance();
		//服务器端个人主页地址
		String url=LeUrls.CIRCLE_GETMYCIRCLE;
		
		Map<String, String> parameters=new HashMap<String, String>();
		
		
		parameters.put("userId",userId);
		parameters.put("pageNow", "1");
		parameters.put("pageSize", "10");
		
		NetUtils.get(url,parameters, new MyCommonCallback<String>(){
			
			@Override
			public void onSuccess(String result) {
				
				List<FriendAction> datas;
				System.out.println(result);
				datas=beanUtil.getFriends(context, result);
				setUserNow(user);
				onDataArrived(datas);
			}
			
			@Override
			public void onError(Throwable throwable, boolean isOnCallback) {
				Log.e(TAG, throwable.toString());
				
				RuntimeException qe=new RuntimeException(throwable);
				throw qe;
			}
			
		});
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.e("Personalctivity", "onDestory");
		super.onDestroy();
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	}
}
