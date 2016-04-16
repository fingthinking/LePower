package com.le.run.db;

import java.util.ArrayList;

import com.baidu.mapapi.model.LatLng;
import com.le.run.entity.MyLatLng;
import com.le.run.entity.Run;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LagLngData {
       private MyDatabaseHelper dbHelper;
       private static final String LATLNG_TABLE = "LatLng";
       
       public LagLngData(Context context){
    	   dbHelper = new MyDatabaseHelper(context);
       }
       
       public void addLatLng(int runId,double latitude,double longtitude){
   		
   		SQLiteDatabase db = dbHelper.getWritableDatabase();
   		ContentValues values = new ContentValues();
   		values.put("runId", runId);
   		values.put("latitude", latitude);
   		values.put("longtitude", longtitude);
   		db.insert(LATLNG_TABLE, "_id", values);
   	}
   	
   	public void deleteLatLng(int runId){
   		SQLiteDatabase db = dbHelper.getWritableDatabase();
   		db.delete(LATLNG_TABLE, "runId= ?", new String[]{""+runId}); 		
   	}
   	
   	
     public void addLatLngList(int runId,ArrayList<LatLng> list){
		
		for(int i=0;i<list.size();i++){
			LatLng latLng= list.get(i);
			addLatLng(runId,latLng.latitude,latLng.longitude);
		}
	}
     
     public ArrayList<MyLatLng> getLagLngFromRunId(int runId){
  		
  		SQLiteDatabase db = dbHelper.getReadableDatabase();
  		ArrayList<MyLatLng> latLngList = new ArrayList<MyLatLng>();
  		Cursor cursor = db.query(LATLNG_TABLE, null, null, null, null, null, null);
  		
  		while(cursor.moveToNext()){
  			int runId1 = cursor.getInt(cursor.getColumnIndex("runId"));
  			if(runId1 == runId){
  				 double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
  	  	         double longtitude = cursor.getDouble(cursor.getColumnIndex("longtitude"));			
  	  			MyLatLng myLatLng = new MyLatLng(runId1,latitude,longtitude);	  			
  	  			latLngList.add(myLatLng);
  			}
  	       
  		}
  		cursor.close();
  		return latLngList;
  	}
}
