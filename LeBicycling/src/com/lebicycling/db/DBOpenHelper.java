package com.lebicycling.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "le_bicycling.db";
	private static final String BICYCLING_TABLE_NAME = "bicycling";
	private static final String MYLATLNG_TABLE_NAME = "mylatlng";
	
	// �����ﳵ��¼��
	private String CREATE_BICYCLING_TABLE = "CREATE TABLE IF NOT EXISTS "+BICYCLING_TABLE_NAME 
			                +"(_id INTEGER PRIMARY KEY,"+"userId TEXT,"+"totalDistance REAL,"
			                +"currentSpeed REAL,"+"averageSpeed REAL,"+"calorie REAL,"+"time REAL,"+"date TEXT)";
	
	// ������¼�ﳵ�켣�ľ�γ�ȱ�
	private String CREATE_MYLATLNG_TABLE = "CREATE TABLE IF NOT EXISTS "+MYLATLNG_TABLE_NAME
			                +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+"bicyclingId INTEGER,"+"latitude REAL,"+"longitude REAL)";
	
	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_MYLATLNG_TABLE);
		db.execSQL(CREATE_BICYCLING_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
