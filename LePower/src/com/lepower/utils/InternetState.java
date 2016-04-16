package com.lepower.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetState {

	public static boolean isNetWorkConnected(Context context){
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);//这是一个系统服务类,专门用于管理网络连接的
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info != null) {
				return info.isAvailable();  //判断网络是否存在
			} else {
				Toast.makeText(context, "网络异常,请检查网络", Toast.LENGTH_LONG).show();
			}
		}
		return false;
		
	}
}
