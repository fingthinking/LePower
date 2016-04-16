package com.lebicycling.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lebicycling.entity.Bicycling;

public class BicyclingDao {

	private DBOpenHelper helper;

	private static BicyclingDao instance;

	public static final String Bicycling_TABLE = "Bicycling";
	//	public static final String LATLNG_TABLE = "LatLng";


	private BicyclingDao(Context context) {
		helper = new DBOpenHelper(context);

	}

	public synchronized static BicyclingDao getInstance(Context context){
		if(instance == null){
			instance = new BicyclingDao(context);
		}
		return instance;
	}
	
    // ���ﳵ���в�������
	public void addBicyclingData(Bicycling bicycling){
		
			SQLiteDatabase db = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("_id", bicycling.getBicyclingId());
			values.put("userId", bicycling.getUserId());
			values.put("totalDistance", bicycling.getTotalDistance());
			values.put("currentSpeed", bicycling.getCurrentSpeed());
			values.put("averageSpeed", bicycling.getAverageSpeed());
			values.put("calorie", bicycling.getCalorie());
			values.put("time", bicycling.getTime());
			values.put("date", bicycling.getDate());
			db.insert(Bicycling_TABLE, "_id", values);

	}
	
	
	public void deleteBicyclingData(int bicyclingId,String date){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(Bicycling_TABLE, "_id = ? and date = ?", new String[]{bicyclingId+"",date});
		
	}
	
    public void deleteBicyclingAll(String date){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(Bicycling_TABLE, "date = ?", new String[]{date});
		
	}
	
	
	public ArrayList<Bicycling> getBicyclingData(){
		
		SQLiteDatabase db = helper.getReadableDatabase();
		ArrayList<Bicycling> BicyclingList = new ArrayList<Bicycling>();
		Cursor cursor = db.query(Bicycling_TABLE, null, null, null, null, null, null);
		
		while(cursor.moveToNext()){
			String userId = cursor.getString(cursor.getColumnIndex("userId"));
			int bicyclingId = cursor.getInt(cursor.getColumnIndex("_id"));
			double totalDistance = cursor.getDouble(cursor.getColumnIndex("totalDistance"));
			double currentSpeed = cursor.getDouble(cursor.getColumnIndex("currentSpeed"));
			double averageSpeed = cursor.getDouble(cursor.getColumnIndex("averageSpeed"));
			double calorie = cursor.getDouble(cursor.getColumnIndex("calorie"));
			double time = cursor.getDouble(cursor.getColumnIndex("time"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			Bicycling Bicycling = new Bicycling(bicyclingId,userId,totalDistance,currentSpeed,averageSpeed,calorie,time,date);
			BicyclingList.add(Bicycling);
		}
		cursor.close();
		return BicyclingList;
	}
	
	
		public ArrayList<Bicycling> getBicyclingDataByDate(String date,String userId){
			
			SQLiteDatabase db = helper.getReadableDatabase();
			ArrayList<Bicycling> BicyclingList = new ArrayList<Bicycling>();
			Cursor cursor = db.query(Bicycling_TABLE, null, "date = ? and userId = ?", new String[]{date,userId}, null, null, null);
			
			while(cursor.moveToNext()){
				int bicyclingId = cursor.getInt(cursor.getColumnIndex("_id"));
				double totalDistance = cursor.getDouble(cursor.getColumnIndex("totalDistance"));
				double currentSpeed = cursor.getDouble(cursor.getColumnIndex("currentSpeed"));
				double averageSpeed = cursor.getDouble(cursor.getColumnIndex("averageSpeed"));
				double calorie = cursor.getDouble(cursor.getColumnIndex("calorie"));
				double time = cursor.getDouble(cursor.getColumnIndex("time"));
				Bicycling Bicycling = new Bicycling(bicyclingId,userId,totalDistance,currentSpeed,averageSpeed,calorie,time,date);
				BicyclingList.add(Bicycling);
			}
			cursor.close();
			return BicyclingList;
		}
	
	
	
	



}
