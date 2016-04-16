package com.lebicycling;

import org.xutils.x;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;
import android.content.Context;

public class App extends Application{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		x.Ext.init(this);
		x.Ext.setDebug(false);
		SDKInitializer.initialize(this);
		context = this;
	}
	
	public static Context context;
	// QQ_APP_ID
	public static final String QQ_APP_ID =  "222222";
	// 微博Appkey
	public static final String WEIBO_APPKEY = "2265454793";
}
