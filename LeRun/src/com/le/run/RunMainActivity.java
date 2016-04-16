package com.le.run;

import com.le.run.utils.ShareUtils;
import com.lepower.activity.LoginActivity;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class RunMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.run_main);
	    ImageView running_light = (ImageView) findViewById(R.id.running_light);
	    ImageView bicycle_light = (ImageView) findViewById(R.id.bicycle_light);
	    running_light.setBackgroundResource(R.drawable.run_start_animation);
	    bicycle_light.setBackgroundResource(R.drawable.run_bicycle_animation);
	    AnimationDrawable ad1 = (AnimationDrawable) running_light.getBackground(); 
	    AnimationDrawable ad2 = (AnimationDrawable) bicycle_light.getBackground();   
    	ad1.start();
    	ad2.start();
	    running_light.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RunMainActivity.this,RunCountActivity.class);
				startActivity(intent);				
			}
		});
        
	    bicycle_light.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RunMainActivity.this,RunRecordActivity.class);
				startActivity(intent);
			}
		});
	    ImageView threePoint = (ImageView) findViewById(R.id.threepoint);
	    final AlertDialog.Builder threePointDialog = new AlertDialog.Builder(this);
	    final IUserDao userDao = new UserDaoImpl();
	    threePointDialog.setTitle("退出乐跑提示");
	    threePointDialog .setMessage("是否退出乐跑登录？");
	    threePointDialog .setCancelable(false);
	    threePointDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				userDao.deleteUserNow();
				Intent intent = new Intent(RunMainActivity.this,LoginActivity.class);
				startActivity(intent);
			}
		});
	    threePointDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	    threePoint.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				threePointDialog.show();
			}
		});
	    
	}
  

   }
 

