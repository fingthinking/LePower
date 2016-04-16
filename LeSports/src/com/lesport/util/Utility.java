package com.lesport.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.print.attribute.standard.RequestingUserName;

import net.sf.json.JSONObject;

public class Utility {
	
	
	/**
	 * 生成主键工具方法
	 * @return
	 */
	public static String getRowId()
	{
	    String str = UUID.randomUUID().toString(); 
	    String uuidStr=str.replace("-", "");
	    return uuidStr;		
	}
	
	
	/**
	 * 获取(yyyy-MM-dd HH:mm:ss)格式的当前时间
	 * @return String
	 */
	public static String getFormattedCurrentDateAndTime()
	{		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Date date = new Date(System.currentTimeMillis());				
		return formatter.format(date);
	}
	
	/**
	 * 随机生成乐动力号方法
	 * @return
	 */
	public static String getLeNum()
	{
		return (UUID.randomUUID().getMostSignificantBits()+"").replace("-","").substring(1, 7);
	}
	
	
	/**
	 * 封装返回的json字符串
	 * @return String
	 * 
	 * 
	 * 返回格式形如：
	 * {
	 * 		“resCode”:1, 
	 * 		“resMsg”:””,	
	 * 		“data”: {}
	 * }
	 * 
	 */
	public static String packReturnJson(int resCode, String resMsg, Object data)
	{
		JSONObject object = new JSONObject();
		
		if(null == data)
		{
			data = "";
		}
		
		object.put("resCode", resCode);
		object.put("resMsg", resMsg);
		object.put("data",data);
		
		return object.toString();
	}
	
	/**
	 * 封装返回的json字符串
	 * @return String
	 * 
	 * 
	 * 返回格式形如：
	 * {
	 * 		“resCode”:1, 
	 * 		“resMsg”:””,	
	 * 		“data”: {}
	 * }
	 * 
	 */
	public static String generateCode(int digit)
	{
		Random random = new Random();
		String generateString = "";
		for (int i = 0; i < digit; i++) {
			
			char c = (char)(random.nextInt(10)+'0');
			
	       
			generateString += c;
	    }
	    return generateString;	
	}

	

}
