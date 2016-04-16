package com.lebicycling.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;

import com.lebicycling.R;

public class MyProgress {

    ProgressDialog progressDialog;
	
	public  void getProgress(Context context){
		progressDialog = new ProgressDialog(context);
		progressDialog.show();
		progressDialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
		progressDialog.setContentView(R.layout.progress);
		
	}
	
	public void cancelDialog(){
		progressDialog.dismiss();
	}
	
	
	
}
