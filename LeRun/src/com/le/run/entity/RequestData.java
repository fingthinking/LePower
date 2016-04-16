package com.le.run.entity;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.model.LatLng;
import com.le.run.entity.MyLatLng;
import com.le.run.entity.Run;

import android.util.Log;




public class RequestData {

	ArrayList<Run> list = null;
	ArrayList<MyLatLng> latList = null;
	private ArrayList<MyLatLng> mylatlist;
	
	
   
	/**
	 * ��¼����
	 * @param name  �û������˺ţ�
	 * @param password ����
	 * @return ����ʵ����ַ���
	 */
    public String loginLeRun(String name,String password){
    	String sResponse = null;
    	HttpClient client = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost("http://www.baidu.com");
    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("username",name));
    	params.add(new BasicNameValuePair("password", password));
    	try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse response = client.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity httpEntity = response.getEntity();
				sResponse = EntityUtils.toString(httpEntity,"utf-8");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return sResponse;
    }
	
    /**
     * �ϴ�����
     * @param Run 
     * @return
     */
    public int sendRunning(Run runData,ArrayList<LatLng> list){
    	int responseCode = -1;
    	HttpClient client = new DefaultHttpClient();
    	Log.d("JSON1", "AAAAAAAAA1111111111111111");
    	HttpPost httpPost = new HttpPost("http://192.168.1.100:8080/LeSports/run/addRunInfo");
    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	StringBuffer buffer = new StringBuffer();
    	
    	// ����γ��ƴ�ӳ��ַ����ϴ�
    	for(int i=0;i<list.size();i++){
    		LatLng latlng = list.get(i);
    		buffer.append(latlng.latitude+","+latlng.longitude+"#");
    	}
    	
//    	params.add(new BasicNameValuePair("bicyclingId", ""+bicycling.getBicyclingId()));
    	params.add(new BasicNameValuePair("userId", ""+runData.getUserId()));
    	params.add(new BasicNameValuePair("distance", runData.getTotalDistance()+""));
    	params.add(new BasicNameValuePair("currentSpeed", runData.getCurrentSpeed()+""));
    	params.add(new BasicNameValuePair("averageSpeed", runData.getAverageSpeed()+""));
    	params.add(new BasicNameValuePair("calorie", runData.getCalorie()+""));
    	params.add(new BasicNameValuePair("second", runData.getTime()+""));
    	params.add(new BasicNameValuePair("date", runData.getDate()));
    	params.add(new BasicNameValuePair("coordinate",buffer.toString()));
    	
    	try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
			httpPost.setEntity(entity);
			Log.d("JSON2", "BBBBBBBBB222222222222222222222");
			HttpResponse response = client.execute(httpPost);
			Log.d("JSON2", "CCCCCCCCCC3333333333");
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity httpEntity = response.getEntity();
				String sResponse = EntityUtils.toString(httpEntity,"utf-8"); 
				Log.d("JSON", sResponse);
				responseCode = parseSendRunningJSON(sResponse);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("Exception", e.toString());
			e.printStackTrace();
		}
    	Log.d("CodeCode", ""+responseCode);
    	return responseCode;
    }
	

   


	// ����ָ�������ܲ�������
	public  ArrayList<Run> sendRequest(int userId,String date){
		try{
			HttpClient httpClient = new DefaultHttpClient();
			// �˴�ƴ�ӵ���URL��֪����ȷ���
			HttpGet httpGet = new HttpGet("http://10.6.11.57:8080/LeSports/run/getRunInfo?userId="+userId+"&date="+date);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = httpResponse.getEntity();	
				String response = EntityUtils.toString(entity,"utf-8");
				list = parseMyJSON(response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	/*
	// ����ָ���ܲ�id�����о�γ����Ϣ�����ڻ����ܲ��켣
	public ArrayList<MyLatLng> requestMyLatLng(int hisBicycling){
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://10.6.11.37:8080/Hello/latlng.json");
		try {
			HttpResponse response = client.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity,"utf-8");
				latList = parseLatJSON(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return latList;
	}*/
	
	

    // ����ָ�������ܲ���Ϣ
	public ArrayList<Run> parseMyJSON(String json) {
//        Log.i("Log", "parseMyJSON done");
		ArrayList<Run> runList = new ArrayList<Run>();
			try {
				JSONObject object = new JSONObject(json);
				// ���ȴӷ��ص�json���л�ȡdata�ֶε��ַ���֮���ٽ���json����
				String jsonData = object.getString("data");
				JSONArray  jsonArray = new JSONArray(jsonData);
				for(int i=0; i<jsonArray.length();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					double totalDistance = jsonObject.getDouble("distance");
					double currentSpeed = jsonObject.getDouble("currentSpeed");
					double averageSpeed = jsonObject.getDouble("averageSpeed");
					double calorie = jsonObject.getDouble("calorie");
					double time = jsonObject.getDouble("second");
					String latLng = jsonObject.getString("coordinate");
					
					mylatlist = new ArrayList<MyLatLng>();
					String[] latLngs = latLng.split("#");
					
					for(int j=0;j<latLngs.length;j++){
						String[] perLat = latLngs[j].split(",");
						MyLatLng myLat = new MyLatLng(Double.valueOf(perLat[0]), Double.valueOf(perLat[1]));
						mylatlist.add(myLat);
					}
					
					Run running = new Run(totalDistance,currentSpeed ,averageSpeed,calorie, time, mylatlist);
					runList.add(running);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.i("Log", "exception");
				e.printStackTrace();
			}

		return runList;
	}
	
	
	
	 private int parseSendRunningJSON(String sResponse) {
		 
		 int responseCode = -1;
		 
			try {
				 JSONObject jsonObject = new JSONObject(sResponse);
				 responseCode = jsonObject.getInt("resCode");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return responseCode;
		}
	
	// ������γ����Ϣ
	public ArrayList<MyLatLng> parseLatJSON(String jsonDate) {

		ArrayList<MyLatLng> list = new ArrayList<MyLatLng>();
		try {
			JSONArray jsonArray = new JSONArray(jsonDate);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				double latitude = jsonObject.getDouble("latitude");
				double longitude = jsonObject.getDouble("longitude");
				MyLatLng MyLatLng = new MyLatLng(latitude, longitude);
				list.add(MyLatLng);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return list;
	}

}
