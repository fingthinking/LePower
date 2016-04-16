package com.le.run.utils;

import java.text.NumberFormat;

public class MyFormat {

	static NumberFormat nFormat;
	// 取两位小数
	public static double roundOff(double temp){
		nFormat = NumberFormat.getInstance();
		nFormat.setMaximumFractionDigits(2);
		Double value = Double.valueOf(nFormat.format(temp));
		return value;
	}
	
	// 将总秒数转变成时、分、秒(00:00:00)
	public static String getClock(int count){
		String time = null;
		if(count == 0){
			time = "00:00:00";
		}else if(count < 60 ){
			time = "00:"+"00:"+(count<10 ? ("0"+count):count);
		}else{
			time = (count/3600 < 10 ? ("0"+count/3600):count/3600)+":"+
					(count/60<10 ? ("0"+count/60):count/60)+":"+
					(count%60 < 10 ? ("0"+count%60):count%60);

		}
		return time;
	}
	
}
