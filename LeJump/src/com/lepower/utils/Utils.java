package com.lepower.utils;

import android.annotation.SuppressLint;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils
{
	public static String getTextFromInputStream(InputStream is){
		
		byte[] buff = new byte[1024];
		int len =0;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try
		{
			while((len = is.read(buff))!=-1){
				byteArrayOutputStream.write(buff, 0, len);
			}
			String text = new String(byteArrayOutputStream.toString("utf-8"));
			return text;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	//��ȡ��ǰ��ʱ�䣬��ʽ xxxx-xx-xx
	@SuppressLint("SimpleDateFormat")
	public static String getTime(){
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		String time = df.format(date);// new Date()Ϊ��ȡ��ǰϵͳʱ��
		return time;
	}
}
