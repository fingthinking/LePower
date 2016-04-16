package com.le.run.db;

import java.util.ArrayList;

import com.le.run.entity.Run;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RunData {
	 private MyDatabaseHelper dbHelper;
     private static final String RUN_TABLE = "Run";
     
     public RunData(Context context) {
 		dbHelper = new MyDatabaseHelper(context);
 	}
     
     public void addRunData(int runId,String userId, double totalDistance, double currentSpeed,
 			double averageSpeed, double calorie, double alltime, String date){		
 			SQLiteDatabase db = dbHelper.getWritableDatabase();
 			ContentValues values = new ContentValues();
 			values.put("_id", runId);
 			values.put("userId", userId);
 			values.put("totalDistance", totalDistance);
 			values.put("currentSpeed", currentSpeed);
 			values.put("averageSpeed", averageSpeed);
 			values.put("calorie", calorie);
 			values.put("alltime", alltime);
 			values.put("date", date);
 			db.insert(RUN_TABLE, "_id", values);
 	}
     public void deleteRunData(int runId,String date){		
 		SQLiteDatabase db = dbHelper.getWritableDatabase();
 		db.delete(RUN_TABLE, "_id = ? and date = ?", new String[]{runId+"",date});		
 	}
     
     
     public ArrayList<Run> getRunData(){
 		
 		SQLiteDatabase db = dbHelper.getReadableDatabase();
 		ArrayList<Run> runList = new ArrayList<Run>();
 		Cursor cursor = db.query(RUN_TABLE, null, null, null, null, null, null);
 		
 		while(cursor.moveToNext()){
 			String userId = cursor.getString(cursor.getColumnIndex("userId"));
 			int runId = cursor.getInt(cursor.getColumnIndex("_id"));
 			double totalDistance = cursor.getDouble(cursor.getColumnIndex("totalDistance"));
 			double currentSpeed = cursor.getDouble(cursor.getColumnIndex("currentSpeed"));
 			double averageSpeed = cursor.getDouble(cursor.getColumnIndex("averageSpeed"));
 			double calorie = cursor.getDouble(cursor.getColumnIndex("calorie"));
 			double time = cursor.getDouble(cursor.getColumnIndex("alltime"));
 			String date = cursor.getString(cursor.getColumnIndex("date"));
 			Run run = new Run(runId,userId,totalDistance,currentSpeed,averageSpeed,calorie,time,date);
 			runList.add(run);
 		}
 		cursor.close();
 		return runList;
 	}
     
     
     public ArrayList<Run> getRunDataByDate(String date){
			
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			ArrayList<Run> runList = new ArrayList<Run>();
			Cursor cursor = db.query(RUN_TABLE, null, "date = ?", new String[]{date}, null, null, null);
			
			while(cursor.moveToNext()){
				String userId = cursor.getString(cursor.getColumnIndex("userId"));
				int runId = cursor.getInt(cursor.getColumnIndex("_id"));
				double totalDistance = cursor.getDouble(cursor.getColumnIndex("totalDistance"));
				double currentSpeed = cursor.getDouble(cursor.getColumnIndex("currentSpeed"));
				double averageSpeed = cursor.getDouble(cursor.getColumnIndex("averageSpeed"));
				double calorie = cursor.getDouble(cursor.getColumnIndex("calorie"));
				double time = cursor.getDouble(cursor.getColumnIndex("time"));
				Run run = new Run(runId,userId,totalDistance,currentSpeed,averageSpeed,calorie,time,date);
				runList.add(run);
			}
			cursor.close();
			return runList;
		}
	
}
