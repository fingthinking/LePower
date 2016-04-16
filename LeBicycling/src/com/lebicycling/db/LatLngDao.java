package com.lebicycling.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baidu.mapapi.model.LatLng;
import com.lebicycling.entity.MyLatLng;




public class LatLngDao {

	private DBOpenHelper helper;
	
	private static LatLngDao instance;
	
	private static final String MYLATLNG_TABLE = "mylatlng";
	
	private LatLngDao(Context context){
		helper = new DBOpenHelper(context);
	}
	
	public synchronized static LatLngDao getInstance(Context context){
		if(instance == null){
			instance = new LatLngDao(context);
		}
		return instance;
	}
	
	public void addLatLng(int bicyclingId,double latitude,double longitude){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("bicyclingId", bicyclingId);
		values.put("latitude", latitude);
		values.put("longitude", longitude);
		db.insert(MYLATLNG_TABLE, "_id", values);
	}
	
	public void deleteLatLng(int bicyclingId){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(MYLATLNG_TABLE, "bicyclingId= ?", new String[]{""+bicyclingId});
		
		
	}
	
	// �����Լ����������Ͱٶȵ�LatLng��ͬ����Ҫ���ٶȵ�����ת����Լ��������ʽ���ܽ������ݿ�Ķ�д
	public void addLatLng(int bicyclingId,ArrayList<LatLng> list){
		ArrayList<MyLatLng> mylist = new ArrayList<MyLatLng>();
		for(int i=0;i<list.size();i++){
			MyLatLng myLatLng = new MyLatLng(list.get(i).latitude, list.get(i).longitude);
			mylist.add(myLatLng);
		}
		addLatLngList(bicyclingId, mylist);
	}
	
	
	
	
	// ���켣����д�����ݿ�
	public void addLatLngList(int bicyclingId,ArrayList<MyLatLng> list){
		
		for(int i=0;i<list.size();i++){
			MyLatLng latLng= list.get(i);
			addLatLng(bicyclingId,latLng.getLatitude(),latLng.getLongitude());
		}
	}
	
	
	public ArrayList<MyLatLng> getTraceById(int bicyclingId){
		ArrayList<MyLatLng> traceList = new ArrayList<MyLatLng>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(MYLATLNG_TABLE, null, "bicyclingId = ?", new String[]{""+bicyclingId}, null, null, null);
		
		while(cursor.moveToNext()){
			double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
			double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
			MyLatLng latlng = new MyLatLng(bicyclingId, latitude, longitude);
			traceList.add(latlng);
		}
		cursor.close();
		
		return traceList;
	}
	
	
	
	
	
	
}
