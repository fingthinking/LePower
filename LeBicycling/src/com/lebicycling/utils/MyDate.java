package com.lebicycling.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    
	public static final long DAY = 86400000;
	
	// ��ȡ��ǰ����
	public static String getDate(long day){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date(day));
		return date;
	}
	
	
	// ��ʱ�����ܲ���ID
	public static int getBicyclingId(){
		SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");
		String id = sdf.format(new Date());
		int runId = Integer.valueOf(id);
		return runId;
				
	}
	
	
	
	
	
	
	
	
	
	
}
