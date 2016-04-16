package com.lebicycling.utils;

public class DateFormChange {

	public static String getNewForm(String date){
		String[] strings = date.split("-");
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<strings.length;i++){
			buffer.append(strings[i]);
		}
		return buffer.toString();
	}
	
}
