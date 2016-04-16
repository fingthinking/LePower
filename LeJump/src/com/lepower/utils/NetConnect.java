package com.lepower.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lepower.dao.impl.JumpBean;
import com.lepower.dao.impl.JumpSQLiteHelper;
import com.lepower.dao.impl.QueryRecord;

public class NetConnect
{
	private static String data = null;
	//�ϴ�����ǰ���ж�����
	public static String HttpRequest(final Context mContext,final String userId)
	{

		new Thread()
		{
			@Override
			public void run()
			{
				JumpSQLiteHelper jumpSQLiteHelper = new JumpSQLiteHelper(mContext,
						"JumpData.db", null, 1);
				jumpSQLiteHelper.getWritableDatabase();
				String path = "http://192.168.1.100:8080/LeSports/jump/getJumpOrder";
//				String path = "http://10.6.11.57:8080/LeSports/jump/addJumpInfo";
//				String path = "http://10.6.11.57:8080/LeSports/jump/getJumpInfo";
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(path);
				int lastOrder = QueryRecord.getLastOrder(mContext, userId);
				// ��װ������
				BasicNameValuePair nameValuePair = new BasicNameValuePair(
						"userId", userId);
//				BasicNameValuePair nameValuePair1 = new BasicNameValuePair(
//						"jumpCount", ""+23);
//				BasicNameValuePair nameValuePair2 = new BasicNameValuePair(
//						"order", ""+2);
				List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(nameValuePair);
//				parameters.add(nameValuePair1);
//				parameters.add(nameValuePair2);

				try
				{
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
							parameters, "utf-8");
					httpPost.setEntity(entity);
					HttpResponse httpResponse = httpClient.execute(httpPost);
					if (httpResponse.getStatusLine().getStatusCode() == 200)
					{
						InputStream is = httpResponse.getEntity().getContent();

						String string = Utils.getTextFromInputStream(is);
						JSONObject jsonObject=new JSONObject(string);
						String data=jsonObject.getString("data");
						if (data == null || "".equals(data)) {
							data = 0+"";
						}
						System.out.println("====="+lastOrder+"====="+data);
						if (lastOrder>Integer.parseInt(data))
						{
							for (int i = Integer.parseInt(data)+1; i <= lastOrder; i++)
							{
								updateData(mContext,userId,i);
							}
						}else if(lastOrder<Integer.parseInt(data)){
							//������������д�ر���
							downloadData(mContext,userId,lastOrder);
						}
						
					}else {
						System.out.println("��ַ����ȷ"+httpResponse.getStatusLine().getStatusCode());
					}

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}.start();

		return null;
	}
	
	public static void updateData(Context mContext,String user,int orderId){
		
		JumpSQLiteHelper jumpSQLiteHelper = new JumpSQLiteHelper(mContext,
				"JumpData.db", null, 1);
		SQLiteDatabase db = jumpSQLiteHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM JumpUser WHERE orderId = ? and userId=?",
				new String[] {""+orderId,user});
		System.out.println("��=====����=========");
		BasicNameValuePair nameValuePair = null;
		BasicNameValuePair nameValuePair1= null;
		BasicNameValuePair nameValuePair2= null;
		BasicNameValuePair nameValuePair3= null;
		BasicNameValuePair nameValuePair4= null;
		if (cursor.moveToNext())
		{
			String userId = cursor.getString(cursor.getColumnIndex("userId"));
			String order = cursor.getString(cursor.getColumnIndex("orderId"));
			String jumpCount = cursor.getString(cursor.getColumnIndex("jumpCount"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String kcal = cursor.getString(cursor.getColumnIndex("kcal"));
			nameValuePair = new BasicNameValuePair(
					"userId", userId);
			nameValuePair1 = new BasicNameValuePair(
					"orderId", order);
			nameValuePair2 = new BasicNameValuePair(
					"jumpCount", jumpCount);
			nameValuePair3 = new BasicNameValuePair(
					"date", date);
			nameValuePair4 = new BasicNameValuePair(
					"calorie", kcal);
		}
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(nameValuePair);
		parameters.add(nameValuePair1);
		parameters.add(nameValuePair2);
		parameters.add(nameValuePair3);
		parameters.add(nameValuePair4);
		
		
		
		String path = "http://192.168.1.100:8080/LeSports/jump/addJumpInfo";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);
		
		UrlEncodedFormEntity entity;
		try
		{
			entity = new UrlEncodedFormEntity(
					parameters, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200)
			{
				InputStream is = httpResponse.getEntity().getContent();

				String string = Utils.getTextFromInputStream(is);
				JSONObject jsonObject=new JSONObject(string);
				String resCode=jsonObject.getString("resCode");
				if ("1".equals(resCode))
				{
					Toast.makeText(mContext, "网络连接失败", Toast.LENGTH_SHORT).show();
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//�������أ���Ҫ���ڸ����豸��½��ʱ�򣬽�����������д�ص�����
	public static void downloadData(Context mContext,String userId,int lastOrder){
		String path = "http://192.168.1.100:8080/LeSports/jump/getJumpInfo";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);
		System.out.println("������=========");
		BasicNameValuePair nameValuePair = new BasicNameValuePair(
				"userId", userId);
		BasicNameValuePair nameValuePair1 = new BasicNameValuePair(
				"order", ""+lastOrder);
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(nameValuePair);
		parameters.add(nameValuePair1);
		try
		{
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					parameters, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200)
			{
				InputStream is = httpResponse.getEntity().getContent();

				String string = Utils.getTextFromInputStream(is);
				parseJson(mContext,string);
				
				
			}else {
				System.out.println("网络连接失败"+httpResponse.getStatusLine().getStatusCode());
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void parseJson(Context mContext,String jsonString){
		Gson gson = new Gson();
		JsonParser jp = new JsonParser();
		JsonElement message = jp.parse(jsonString);
		JsonObject jsObject = message.getAsJsonObject();
		
		if (jsObject.get("data") != null && !jsObject.get("data").equals("") && !jsObject.get("data").equals("null")) {
            data = jsObject.get("data").toString();
		}
		if (data != null) {
			List<JumpBean> jumpList = gson.fromJson(data, new TypeToken<List<JumpBean>>() {
					}.getType());
			for (JumpBean j : jumpList) {
				String date = null;
				if (j.getDate() != null) {
					date = j.getDate().toString().replace("00:00:00.0", "").trim();
				}
				JumpSQLiteHelper jumpSQLiteHelper = new JumpSQLiteHelper(mContext,
						"JumpData.db", null, 1);
				SQLiteDatabase db = jumpSQLiteHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("date", date);
				values.put("userId", j.getUserId());
				values.put("orderId", j.getOrderId());
				values.put("kcal", j.getCalorie());
				values.put("jumpCount", j.getJumpCount());
				db.insert("JumpUser", null, values);
			}
		}
	}
	
}
