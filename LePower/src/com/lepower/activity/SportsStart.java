package com.lepower.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lepower.App;
import com.lepower.R;
import com.lepower.model.User;

public class SportsStart extends Activity {
	private ImageView ivCancel, ivWalk, ivRunning, ivBicycling, ivJumpRope,
			ivPushUp, ivSitUp;
	//各项运动对应的ImageView对象数组
	private ImageView[] imageViewArray = { ivCancel, ivWalk, ivRunning,
			ivBicycling, ivJumpRope, ivPushUp, ivSitUp };
	//各项运动对应的 图片资源id数组
	private int[] imageViewIdArray = { R.id.ivCancel, R.id.ivWalk,
			R.id.ivRunning, R.id.ivBicycling, R.id.ivJumpRope, R.id.ivPushUp,
			R.id.ivSitUp };
	// 各项运动是对应的应用包名 数组
	private String[] packageNameArray = { "", "com.lestep",
			"com.le.run", "com.lebicycling",
			"com.lejump", "com.le.pushup",
			"com.lesitup" };
//	//记录点击的是哪个按钮，根据tag位来判断
//	private int flagSportAct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tab3);
		transparent();// 使控件也透明
		initView();// 初始化
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		// TODO Auto-generated method stub
		for (int i = 0; i < imageViewArray.length; i++) {
			imageViewArray[i]=(ImageView) findViewById(imageViewIdArray[i]);
			imageViewArray[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					//记录点击的是哪一个按钮
					int flagSportAct = Integer.valueOf(view.getTag().toString());
					if (flagSportAct == 0) {//标志位是0说明点击的是退出键
						finish();
					} else if (isAppInstalled(getApplicationContext(),packageNameArray[flagSportAct])) {

//						Log.d("heheda", msg)
						startAnotherApp(packageNameArray[flagSportAct], "com.lepower.activity.LoginActivity");// 类名是包名加对应的主activity名字。启动对应的应用
//						startAnotherApp("com.lepower.activity", "com.lepower.activity.LoginActivity");
//						startAnotherApp(packageNameArray[flagSportAct], packageNameArray[flagSportAct] + ".MainActivity");// 类名是包名加对应的主activity名字。启动对应的应用
						
						
					} else {
						Intent intent=new Intent(getApplicationContext(), DownloadApkActivity.class);
						Bundle bundle=new Bundle();
						bundle.putInt("flagSportAct", flagSportAct-1);//因为这边多了一个按钮，所以传过去的值要减去1
						intent.putExtras(bundle);//把标志位也传给DownloadApkActivity
						startActivity(intent);
					}
				}
			});
		}
	}

	/**
	 * 该方法才能使得控件也变成半透明
	 */
	private void transparent() {
		Window window = getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		wl.alpha = 0.92f;// 设置透明度
		window.setAttributes(wl);
	}

	/**
	 * 判断应用是否安装
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public boolean isAppInstalled(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已经安装的包
		List<String> pName = new ArrayList<String>();
		if (pinfo != null) {
			for (int ii = 0; ii < pinfo.size(); ii++) {
				pName.add(pinfo.get(ii).packageName);// 将包名字符串保存起来
			}
		}
		return pName.contains(packageName);// 返回是否已经安装
	}

	/**
	 * 启动另外一个app，根据包名和类名
	 * 
	 * @param packageName
	 * @param className
	 */
	public void startAnotherApp(String packageName, String className) {
		
		Intent intent=getPackageManager().getLaunchIntentForPackage(packageName);
		SharedPreferences pref = App.context.getSharedPreferences("userNow",Context.MODE_PRIVATE);
		//将主app的用户信息传到子app中，以达到免密码登陆
		intent.putExtra("MainUser", pref.getString("user", "{}"));
		
		startActivity(intent);
	}
}