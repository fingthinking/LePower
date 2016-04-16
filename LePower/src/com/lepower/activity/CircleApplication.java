package com.lepower.activity;

import org.xutils.x;

import android.app.Application;

public class CircleApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		x.Ext.init(this);
		x.Ext.setDebug(false);
	}
}
