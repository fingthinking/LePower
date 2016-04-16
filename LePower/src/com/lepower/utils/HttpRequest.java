package com.lepower.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.lepower.minterface.HttpCallbackListener;

public class HttpRequest {
	public static void sendHttpRequest(final HttpCallbackListener listener,final String urlString){
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection = null;
				InputStream inputStream = null;
				BufferedReader reader = null;
				try {
					String line;
					StringBuffer sb = new StringBuffer();//用于记录下载后的文件
					URL url = new URL(urlString);
					connection = (HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setConnectTimeout(8000);
					inputStream = connection.getInputStream();
					reader = new BufferedReader(new InputStreamReader(inputStream));
					while((line = reader.readLine()) != null){
						sb.append(line);
					}
//					return sb.toString();
					listener.onFinish(sb.toString());
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					listener.onError(e);
				}finally{
					try {
						reader.close();
						inputStream.close();
						connection.disconnect();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			}
			
		}).start();
		
	}
}
