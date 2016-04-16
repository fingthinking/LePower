package com.lepower.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.lepower.callback.MyCommonCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.FriendAction;
import com.lepower.model.User;
import com.lepower.utils.BeanUtil;
import com.lepower.utils.LeUrls;
import com.lepower.utils.NetUtils;

/**
 * 显示自己的乐友圈，里面有自己和朋友的说说
 * @author Administrator
 *
 */
public class MyCircleActivity extends BaseCircleActivity {

	private static String MyCircleTag="MyCircleActivity";
	private Context context;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		Log.e("MyCircleActivity", "onCretae()");
		IUserDao iUserDao=new UserDaoImpl();
		user=iUserDao.getUserNow();
		getData();
	}

	public void getData() {

		context=this;
		
		final BeanUtil util = BeanUtil.getInstance();
		Map<String, String> map=new HashMap<String, String>();
		map.put("userId",user.getUserId());
		map.put("pageNow", "1");
		map.put("pageSize", "8");
		
		setCircleType(BaseCircleActivity.LEPOWER_MYCIRCLE);
		setUserNow(user);
		setUserId(user.getUserId());
		
		NetUtils.get(LeUrls.CIRCLE_GETFRIEND, map, new MyCommonCallback<String>(){
			
			@Override
			public void onSuccess(String result) {
				List<FriendAction> datas;
				datas = util.getFriends(context,result);
				setUserNow(user);
				onDataArrived(datas);
			}
			
			@Override
			public void onError(Throwable throwable, boolean isOnCallback) {
				System.out.println(throwable.toString());
			}
			
		});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("MyCircleActivity", "onDestroy()");
	}
}
