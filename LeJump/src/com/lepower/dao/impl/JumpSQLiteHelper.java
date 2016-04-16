package com.lepower.dao.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class JumpSQLiteHelper extends SQLiteOpenHelper
{
	public static final String CREATE_JUMPUSER="create table JumpUser (orderId int,userId Long,jumpCount int,date String,kcal double,primary key(orderId,userId))";
	public JumpSQLiteHelper(Context context, String name,CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_JUMPUSER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		
	}

}
