package com.le.run.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    
	// ��ȡ��ǰ����
	public static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = sdf.format(new Date());
		return date;
	}
	
	// ��ȡ��ǰʱ��
	public static String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time = sdf.format(new Date());
		return time;
	}
	
	// ��ʱ�����ܲ���ID
	public static int getRunId(){
		SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");
		String id = sdf.format(new Date());
		int runId = Integer.valueOf(id);
		return runId;
				
	}
}
