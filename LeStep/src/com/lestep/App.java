package com.lestep;

import java.util.List;

import org.xutils.x;
import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.lestep.model.Step;

public class App extends Application{
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = this;
		x.Ext.init(this);
		x.Ext.setDebug(true);
		pendingIntent = PendingIntent.getService(context, 0,
				new Intent("com.lestep.service.StepUploadService"), 0);
	}
	// 同步时间，设定每1小时同步一次
	public static long syncTime = 1000; 
	// 全局Context
	public static Context context;
	// 当前显示的Activity
	public static Activity mActivity;
	// 用于同步数据的服务
	public static PendingIntent pendingIntent = null;
	// 今日运动记录
	public static Step todayStep = null;
	// 今日运动所有记录
	public static List<Step> recordList = null;
	// 判断是否是临时用户
	public static boolean isTempUser = false;
	// QQ_APP_ID
	public static final String QQ_APP_ID =  "222222";
	// 微博Appkey
	public static final String WEIBO_APPKEY = "2265454793";
}
