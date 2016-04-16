package com.lestep.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class CheckApp {
	public static final String WEI_XIN="com.tencent.mm";
	public static final String QQ = "com.tencent.mobileqq";
	public static final String QQZone = "com.qzone";
	public static final String WEI_BO = "com.sina.weibo";
	private Context context;
	public CheckApp(Context context){
		this.context = context;
	}
	public boolean checkHasInstall(String appPackage){
		try {
			context.getPackageManager().getApplicationInfo(appPackage, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
		
	}

}
