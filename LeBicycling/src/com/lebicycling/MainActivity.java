package com.lebicycling;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lepower.activity.LoginActivity;
import com.lepower.dao.impl.UserDaoImpl;

public class MainActivity extends Activity {
	public final long DAY_SECOND = 86400000;
	
	
	TextView text,welcome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.welcome);
		welcome = (TextView) findViewById(R.id.wel);
		text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserDaoImpl user = new UserDaoImpl();
				user.deleteUserNow();
				Intent intent = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		AnimatorSet set = new AnimatorSet();
		set.playTogether(
				ObjectAnimator.ofFloat(welcome, "scaleX", 1,4.5f,0),
				ObjectAnimator.ofFloat(welcome, "scaleY", 1,4.5f,0),
				ObjectAnimator.ofFloat(welcome, "alpha", 0.5f,1)
				
				);
		set.setDuration(7000).start();
		
		
	}
	
	
	
	public void startRun(View v){
		Intent intent = new Intent(this,BicyclingTraceActivity.class);
		startActivity(intent);
	}
	
	public void queryData(View v){
		
		Intent  intent2 = new Intent(this,BicyclingRecordActivity.class);
		startActivity(intent2);
	}
	
	
	
}
