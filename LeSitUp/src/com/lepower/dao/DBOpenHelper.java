package com.lepower.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{
	
	public static final int VERSION=1;//数据库版本
	public static final String DB_NAME="situp_record";//数据库名称
	
	public static final String CREATE_RECORD_INFO="create table record_info("
			+ "id integer primary key autoincrement, "
			+ "userId integer, "
			+ "startTime TEXT, "
			+ "endTime TEXT,"
			+ "situpCount integer, "
			+ "calorie integer, "
			+ "date TEXT)";

	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_RECORD_INFO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		//db.execSQL("drop table if exists Record_info");
		//onCreate(db);
		
	}

}
