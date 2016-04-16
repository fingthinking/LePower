package com.lepower.utils;

import android.text.TextUtils;
import android.widget.TextView;

public class TextViewUtils {
	public static boolean checkEmpty(TextView... views){
		for(TextView view : views){
			LogUtils.e(view.getText().toString());
			if(TextUtils.isEmpty(view.getText().toString().trim())){
				LogUtils.e(view.getText().toString());
				return true;
			}
		}
		return false;
	}
}
