package com.le.run.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "Le_Run.db";
	private static final String RUN_TABLE_NAME = "Run";
	private static final String LATLNG_TABLE_NAME = "LatLng";
	
	// 跑步数据
	private String CREATE_RUN_TABLE = "CREATE TABLE IF NOT EXISTS "+RUN_TABLE_NAME 
				                +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+"userId INTEGER,"+"totalDistance REAL,"
				                +"currentSpeed REAL,"+"averageSpeed REAL,"+"calorie REAL,"+"alltime REAL,"+"date TEXT)";
		
	// 经纬度数据
	private String CREATE_LATLNG_TABLE = "CREATE TABLE IF NOT EXISTS "+LATLNG_TABLE_NAME
				                +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+"runId INTEGER,"+"latitude REAL,"+"longtitude REAL)";
	
	public MyDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_LATLNG_TABLE);
		db.execSQL(CREATE_RUN_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
