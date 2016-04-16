package com.lebicycling.utils;

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

import android.util.Log;

import com.lebicycling.entity.Bicycling;
import com.lebicycling.entity.MyLatLng;


public class RequestData {

	ArrayList<Bicycling> list = null;
	ArrayList<MyLatLng> latList = null;
	private ArrayList<MyLatLng> mylatlist;
	
	
   
	
	
    /**
     * �ϴ�����
     * @param bicycling 
     * @return
     */
    public int sendBicycling(Bicycling bicycling,ArrayList<MyLatLng> list){
    	int responseCode = -1;
    	HttpClient client = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost("http://"+Contant.IP+"/LeSports/bicycle/addBicycleInfo");
    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	StringBuffer buffer = new StringBuffer();
    	
    	// ����γ��ƴ�ӳ��ַ����ϴ�
    	for(int i=0;i<list.size();i++){
    		MyLatLng latlng = list.get(i);
    		buffer.append(latlng.getLatitude()+","+latlng.getLongitude()+"#");
    	}
    	
//    	params.add(new BasicNameValuePair("bicyclingId", ""+bicycling.getBicyclingId()));
    	params.add(new BasicNameValuePair("userId", ""+bicycling.getUserId()));
    	params.add(new BasicNameValuePair("distance", bicycling.getTotalDistance()+""));
    	params.add(new BasicNameValuePair("currentSpeed", bicycling.getCurrentSpeed()+""));
    	params.add(new BasicNameValuePair("averageSpeed", bicycling.getAverageSpeed()+""));
    	params.add(new BasicNameValuePair("calorie", bicycling.getCalorie()+""));
    	params.add(new BasicNameValuePair("second", bicycling.getTime()+""));
    	params.add(new BasicNameValuePair("date", bicycling.getDate()));
    	params.add(new BasicNameValuePair("coordinate",buffer.toString()));
    	
    	try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse response = client.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == 200){
				HttpEntity httpEntity = response.getEntity();
				String sResponse = EntityUtils.toString(httpEntity,"utf-8"); 
				responseCode = parseSendBicyclingJSON(sResponse);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return responseCode;
    }
	

   


	// ����ָ�������ﳵ������
	public  ArrayList<Bicycling> sendRequest(String userId,String date){
		try{
			HttpClient httpClient = new DefaultHttpClient();
			// �˴�ƴ�ӵ���URL��֪����ȷ���
			HttpGet httpGet = new HttpGet("http://"+Contant.IP+"/LeSports/bicycle/getBicycleInfo?userId="+userId+"&date="+date);
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
	
	
//	public ArrayList<MyLatLng> requestMyLatLng(int hisBicycling){
//		HttpClient client = new DefaultHttpClient();
//		HttpGet httpGet = new HttpGet("http://10.6.11.37:8080/Hello/latlng.json");
//		try {
//			HttpResponse response = client.execute(httpGet);
//			if(response.getStatusLine().getStatusCode() == 200){
//				HttpEntity entity = response.getEntity();
//				String result = EntityUtils.toString(entity,"utf-8");
//				latList = parseLatJSON(result);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		return latList;
//	}
	
	

    
	public ArrayList<Bicycling> parseMyJSON(String json) {
//        Log.i("Log", "parseMyJSON done");
		ArrayList<Bicycling> bicyclinglist = new ArrayList<Bicycling>();
			try {
				JSONObject object = new JSONObject(json);
				
				String jsonData = object.getString("data");
				JSONArray  jsonArray = new JSONArray(jsonData);
				for(int i=0; i<jsonArray.length();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					double totalDistance = jsonObject.getDouble("distance");
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
					
					Bicycling bicycling = new Bicycling(totalDistance, calorie, time, mylatlist);
					bicyclinglist.add(bicycling);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.i("Log", "exception");
				e.printStackTrace();
			}

		return bicyclinglist;
	}
	
	
	
	 private int parseSendBicyclingJSON(String sResponse) {
		 
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
