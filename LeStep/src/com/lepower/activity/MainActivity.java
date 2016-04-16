package com.lepower.activity;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.lestep.App;
import com.lestep.R;
import com.lestep.activity.StepActivity;
import com.lestep.model.Step;
import com.lestep.service.StepDownloadService;

public class MainActivity extends BaseActivity {

	IUserDao userDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// 闹铃
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		manager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis()+3000, App.syncTime, App.pendingIntent);

		Intent intent = new Intent(MainActivity.this, StepActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
		finish();
	}

	private void click(View view) {
		userDao.deleteUserNow();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
}
