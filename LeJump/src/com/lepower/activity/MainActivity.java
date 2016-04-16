package com.lepower.activity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lepower.R;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
	@ViewInject(R.id.textView1)
	TextView tView;
	
	IUserDao userDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userDao = new UserDaoImpl();
		User user = userDao.getUserNow();
		tView.setText(user.toString());
		Intent intent = new Intent(this,JumpMainActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Event(R.id.button1)
	private void click(View view){
		userDao.deleteUserNow();
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
	}
}
