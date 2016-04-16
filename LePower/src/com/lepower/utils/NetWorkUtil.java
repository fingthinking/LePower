package com.lepower.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类(用于判断是否有网络)
 * @author Administrator
 *
 */
public class NetWorkUtil {

	public static boolean isNetWork(Context context){
		ConnectivityManager manager = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		}
		return false;
	}

	
}
