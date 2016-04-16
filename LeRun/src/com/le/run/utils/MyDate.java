package com.le.run.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    
	// 获取当前日期
	public static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = sdf.format(new Date());
		return date;
	}
	
	// 获取当前时间
	public static String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time = sdf.format(new Date());
		return time;
	}
	
	// 用时间标记跑步的ID
	public static int getRunId(){
		SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");
		String id = sdf.format(new Date());
		int runId = Integer.valueOf(id);
		return runId;
				
	}
}
