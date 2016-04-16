package com.lestep.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	// 格式化日期为指定格式的String
	public static String getDate(long time, String format) {
		return getDate(new Date(time), format);
	}

	// 格式化日期为指定格式的String
	public static String getDate(Date date, String format) {
		DateFormat sDateFormat = new SimpleDateFormat(format, Locale.CHINA);
		return sDateFormat.format(date);
	}
	
	// 通过毫秒计算时间
	public static String getTimeFromMili(long milisecond){
		StringBuilder sb = new StringBuilder();
		if(milisecond > 60 * 60 * 1000){
			long hour = milisecond / (60 * 60 * 1000);
			sb.append(hour+"小时");
			milisecond %= 60 * 60 * 1000;
		}
		if(milisecond > 1000 * 60){
			long minute = milisecond / (60 * 1000);
			sb.append(minute+"分");
			milisecond %= 60*1000;
		}
		if(milisecond > 1000){
			long second = milisecond / 1000;
			sb.append(second+"秒");
		}else{
			sb.append("0秒");
		}
		return sb.toString();
		
	}
	
	// 通过毫秒计算时间
	public static String getTimeFromSecond(long second) {
		StringBuilder sb = new StringBuilder();
		if (second > 60 * 60) {
			long hour = second / (60 * 60);
			sb.append(hour + "小时");
			second %= 60 * 60 ;
		}
		if (second > 60 ) {
			long minute = second / 60 ;
			sb.append(minute + "分");
			second %= 60 ;
		}
		sb.append(second+"秒");
		return sb.toString();

	}
	
//	public static String convertDay(String day, String formatFirst, String formatSecond){
//		Calendar calendar = 
//	}
}
