package com.lepower.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;

import com.lepower.R;

/**
 * 
 * 显示加载进度条
 *
 */
public class ShowProDialog {
	
	public static void showProgressDialog(ProgressDialog progressDialog){
		progressDialog.setCanceledOnTouchOutside(false);//可以解决弹出进度框后触摸屏幕消失的问题
		progressDialog.show();
		progressDialog.setContentView(R.layout.dialog);
	}
	
	public static void dissmissProgressDialog(ProgressDialog progressDialog){
		progressDialog.dismiss();
	}
}
