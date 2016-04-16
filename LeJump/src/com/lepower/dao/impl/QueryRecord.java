package com.lepower.dao.impl;

import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lepower.utils.Utils;

public class QueryRecord
{
	// ͨ��������������ѯ��¼���紫��������3���Ͳ�ѯ���������˶���¼
	public static Cursor QueryRecordByTime(Context context, int day , String userId)
	{

		JumpSQLiteHelper jumpSQLiteHelper = new JumpSQLiteHelper(context,
				"JumpData.db", null, 1);
		SQLiteDatabase db = jumpSQLiteHelper.getWritableDatabase();
		// ��ѯ���ݿ�
		Cursor cursor = db
				.rawQuery(
						"SELECT * FROM JumpUser WHERE date BETWEEN (select date(?,?)) AND ? AND userId=? order by orderId ASC",
						new String[] { Utils.getTime(), "-" + day + " day",
								Utils.getTime(),userId });
		return cursor;
	}
	//��ȡĳ�������ĸ���
	public static int QueryTotalForDay(Context context, String userId,String date)
	{

		int total = 0;
		JumpSQLiteHelper jumpSQLiteHelper = new JumpSQLiteHelper(context,
				"JumpData.db", null, 1);
		SQLiteDatabase db = jumpSQLiteHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT jumpCount FROM JumpUser WHERE date = ?and userId=? ",
				new String[] { date,userId });
		if (cursor == null)
		{
			return total;
		}
		else
		{
			while (cursor.moveToNext())
			{
				total += cursor.getInt(cursor.getColumnIndex("jumpCount"));
			}
		}
		return total;
	}

	//��ȡ������˶�����
	public static int countForOneDay(Context mContext,String userId)
	{
		int count1 = 0;
		JumpSQLiteHelper jumpSQLiteHelper = new JumpSQLiteHelper(mContext,
				"JumpData.db", null, 1);
		SQLiteDatabase db = jumpSQLiteHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT jumpCount FROM JumpUser WHERE date = ? and userId=?",
				new String[] { Utils.getTime(),userId });
		if (cursor == null)
		{
			return count1;
		}
		else
		{
			while (cursor.moveToNext())
			{
				count1++;
			}
		}
		return count1;
	}

	// ����������������ַ���
	public static String[] get7DayDate()
	{
		String[] dayDate = new String[7];
		for (int i = 0; i < 7; i++)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - i);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DATE);
			if (month <= 9 && day <= 9)
			{
				dayDate[i] = year + "-0" + month + "-0" + day;
			}
			else if (month <= 9 && day > 9)
			{
				dayDate[i] = year + "-0" + month + "-" + day;
			}
			else if (month > 9 && day > 9)
			{
				dayDate[i] = year + "-" + month + "-" + day;
			}
			else
			{
				dayDate[i] = year + "-" + month + "-0" + day;
			}

		}

		return dayDate;
	}
	
	//��ȡ�ֻ��˵��ܵ��˶�����
	public static int getLastOrder(Context mContext ,String name){
		JumpSQLiteHelper jumpSQLiteHelper = new JumpSQLiteHelper(mContext,
				"JumpData.db", null, 1);
		SQLiteDatabase db = jumpSQLiteHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT orderId FROM JumpUser WHERE userId = ?",
				new String[] {name});
		int max = 0;
		while(cursor.moveToNext()){
			if (max < cursor.getInt(0))
			{
				max = cursor.getInt(0);
			}
		}
		return max;
		
	}
	
}
