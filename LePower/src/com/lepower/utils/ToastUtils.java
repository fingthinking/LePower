package com.lepower.utils;
import android.content.Context;
import android.widget.Toast;

import com.lepower.App;

public class ToastUtils {
	private static Context mContext = App.context;
	public static void show(Context context, String message, int duration) {
		Toast.makeText(context, message, duration).show();
	}

	public static void show(Context context, int resId, int duration) {
		Toast.makeText(context, resId, duration).show();
	}

	public static void showShort(int resId) {
		show(mContext, resId, Toast.LENGTH_SHORT);
	}

	public static void showShort(String message) {
		show(mContext, message, Toast.LENGTH_SHORT);
	}

	public static void showLong(int resId) {
		show(mContext, resId, Toast.LENGTH_LONG);
	}

	public static void showLong(String message) {
		show(mContext, message, Toast.LENGTH_LONG);
	}
	
	public static void showToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
	
	public static void showToastShort(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
