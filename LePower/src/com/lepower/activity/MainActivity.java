package com.lepower.activity;

import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.lepower.R;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {
	//
	//V6是整合前备份
	private TabHost menuTabHost;

	private Class activitys[] = { ScoreMainActivity.class, MyCircleActivity.class,SportsStart.class, SelfActivity.class, TabActivity5.class };
	private String title[] = { "成绩", "乐圈", "开始", "乐我", "设置" };
	private int images[] = { R.drawable.item_menu_score,
			R.drawable.item_menu_runner, R.drawable.menu_start_35,R.drawable.item_menu_profile, R.drawable.item_menu_more };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initTabView();
		
//		View view=menuTabHost.getTabWidget().getChildAt(2);
//		view.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				menuTabHost.setCurrentTab(2);
//				Log.d("heheda", "点击了开始tabWidget");
//				Intent intent=new Intent(getApplicationContext(),SportsStart.class);
//				startActivity(intent);
//			}
//		});
		
	}

	@SuppressLint("NewApi")
	private void initTabView() {
		// TODO Auto-generated method stub
		this.menuTabHost = (TabHost) findViewById(R.id.menuTabhost);
		menuTabHost.setup(this.getLocalActivityManager());
		// 创建标签
		for (int i = 0; i < activitys.length; i++) {
			// 实例化一个View作为tab的标签布局
			View view;
			TabSpec spec;
			Intent intent;
			if(i!=2){
				view = View.inflate(this, R.layout.tab_layout, null);
			}else{
				view = View.inflate(this, R.layout.tab_layout_mid, null);
			}
			RadioButton rbMenu=(RadioButton) view.findViewById(R.id.rbMenu);
			rbMenu.setCompoundDrawablesWithIntrinsicBounds(null,  getResources().getDrawable(images[i]), null, null);
			rbMenu.setText(title[i]);
			// 设置跳转Intent
			intent = new Intent(this, activitys[i]);
			spec = menuTabHost.newTabSpec(title[i]).setIndicator(view).setContent(intent);
			menuTabHost.addTab(spec);
//			if(i==2){
//				
//				
//			}
		}

	}
	
	public void onClickPop(View view){
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(getApplicationContext(),SportsStart.class);
				startActivity(intent);
			}
		});
	}
}
