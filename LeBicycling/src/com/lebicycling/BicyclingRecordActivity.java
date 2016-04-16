package com.lebicycling;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lebicycling.adapter.MyPagerAdapter;
import com.lebicycling.entity.Bicycling;
import com.lebicycling.fragment.DataRecordFragment;
import com.lebicycling.utils.MyDate;
import com.lebicycling.utils.ShareUtils;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;
import com.lepower.utils.ImageUtils;
import com.lepower.widget.RoundImageView;

public class BicyclingRecordActivity extends FragmentActivity {

	 ViewPager pager = null; 
	 ArrayList<Fragment> fragments = null; 
	 MyPagerAdapter adapter = null;
	 // 用于查看历史的轨迹图标跳转
	 private ImageView mapHistory = null; 
	 private ImageView share = null;
	 RoundImageView image;
	 View view = null;
	 TextView tvHeight,tvWeight,tvLevel,tvName;
	 public final long DAY_SECOND = 86400000;
	 // 可以查看的天数
	 public final int DAY = 7;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bicycling_record);
		initFragment();
		view = findViewById(R.id.recordLayout);
		adapter = new MyPagerAdapter(getSupportFragmentManager(),fragments);
		pager.setAdapter(adapter);
		mapHistory = (ImageView) findViewById(R.id.map_history);
		share = (ImageView) findViewById(R.id.iv_share);
		pager.setCurrentItem(fragments.size()-1);
		
		
		image = (RoundImageView) findViewById(R.id.iv_image);
		tvHeight = (TextView) findViewById(R.id.body_height);
		tvWeight = (TextView) findViewById(R.id.body_weight);
		tvLevel = (TextView) findViewById(R.id.tv_level);
		tvName = (TextView) findViewById(R.id.tv_name);
		
		//  显示用户信息
		UserDaoImpl dao = new UserDaoImpl();
		User user = dao.getUserNow();
		// 判断用户是否为空
		if(user != null){
			tvName.setText(""+user.getNickName());
			tvWeight.setText("体重:"+user.getWeight());
			tvLevel.setText("等级:"+user.getLevel());
			tvHeight.setText("身高:"+user.getHeight());
			String imgURL = user.getImgURL();
			ImageUtils.loadImage(imgURL, image);
			
		}else{
			tvName.setText("游客");
			tvWeight.setText("体重:未知");
			tvLevel.setText("等级:0");
			tvHeight.setText("身高:未知");
		}
		
		
		
	}
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
		mapHistory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 获得当前的fragment的引用
				DataRecordFragment currentFragment = (DataRecordFragment) fragments.get(pager.getCurrentItem());
				Intent intent = new Intent(BicyclingRecordActivity.this,MapHistoryActivity.class);
				ArrayList<Bicycling> biclist = currentFragment.getBicyclingList();
				intent.putParcelableArrayListExtra("times", currentFragment.getItemList());
				intent.putParcelableArrayListExtra("biclist", biclist);
//				Log.i("Log", "currentFragment:"+currentFragment.getItemList());
				startActivity(intent);
			}
		});
		
		// 点击可以分享到QQ朋友圈
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String temp = "temp";
				ShareUtils.screenShot(BicyclingRecordActivity.this,view,temp);
				File file = Environment.getExternalStorageDirectory();
				Uri.Builder builder = new  Uri.Builder();
				Uri uri = builder.appendPath(file+"/temp.jpg").build();
				ShareUtils.shareImage(BicyclingRecordActivity.this, uri, "share");
				
			}
		});
	}

	private void initFragment() {
		pager = (ViewPager) findViewById(R.id.pager);
		fragments = new ArrayList<Fragment>();
		
		// 根据天数动态的生成fragment，并把日期传入fragment
		for(int i=0;i<DAY;i++){
			Bundle bag = new Bundle();
			bag.putString("date", MyDate.getDate(System.currentTimeMillis()-(DAY-i-1)*DAY_SECOND));
			DataRecordFragment day = new DataRecordFragment();
			day.setArguments(bag);
			fragments.add(day);
		}
	}
	

}
