package com.lepower.dao;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.PrivateCredentialPermission;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lepower.callback.HttpCallback;
import com.lepower.callback.HttpCallbackListener;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageArray;
import com.lepower.model.MessageObj;
import com.lepower.model.RecordInfo;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.SumPathEffect;
import android.util.Log;
import android.widget.Toast;

public final class RecordInfoDAO {

	//
	//
	private static RecordInfoDAO instance;
	// private SQLiteDatabase db;
	DBOpenHelper dbHelper;

	/*
	 * 将构造方法私有化
	 */
	public RecordInfoDAO(Context context) {
		dbHelper = new DBOpenHelper(context);
		// db=dbHelper.getWritableDatabase();
	}

	/*
	 * 获取RecordInfoDAO的实例
	 */
	public synchronized static RecordInfoDAO getInstance(Context context) {
		if (instance == null) {
			instance = new RecordInfoDAO(context);
		}
		return instance;
	}

	// 记录存储到数据库
	public void addRecordInfo(RecordInfo recordInfo) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("userId", recordInfo.getUserId());
		values.put("startTime", recordInfo.getStartTime());
		values.put("endTime", recordInfo.getEndTime());
		values.put("situpCount", recordInfo.getsitupCount());
		values.put("calorie", recordInfo.getCalorie());
		values.put("date", recordInfo.getDate());
		//数据库中没有这条数据就插入，否则不插入
		if(!db.query("record_info", null, "startTime = ?",
				new String[] { recordInfo.getStartTime() }, null, null, null).moveToNext()) {
			db.insert("record_info", null, values);
		}
		else {
			//ToastUtils.showShort("数据库中已有此条数据！");
		}
//		
		

	}

	// 删除记录表中的数据
	public void deleteRecordData(String id) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("record_info", "id = ?", new String[] { id + "" });

	}

	// 清空数据库
	public void deleteAll() {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("record_info", null, null);

	}

	// 查询所有仰卧起坐数据,查询
	/*public ArrayList<RecordInfo> getRecordData() {

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ArrayList<RecordInfo> recordList = new ArrayList<RecordInfo>();
		Cursor cursor = db.query("record_info", null, null, null, null, null,
				null);

		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String userId = cursor.getString(cursor.getColumnIndex("userId"));
			String startTime = cursor.getString(cursor
					.getColumnIndex("startTime"));
			String endTime = cursor.getString(cursor.getColumnIndex("endTime"));
			int situpCount = cursor.getInt(cursor.getColumnIndex("situpCount"));
			int calorie = cursor.getInt(cursor.getColumnIndex("calorie"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			RecordInfo info = new RecordInfo(id, userId, startTime, endTime,
					situpCount, calorie, date);
			recordList.add(info);
		}
		cursor.close();
		return recordList;
	}*/

	// 查询指定日期的运动数据,从数据库
	public ArrayList<RecordInfo> getSitUpDataByDateFromDB(String date) {
		ArrayList<RecordInfo> recordList = new ArrayList<RecordInfo>();
		// 先在本地数据库查找
		recordList = getdateFromDB(date);
		return recordList;
	}

	//

	public ArrayList<RecordInfo> getSitUpDataByDateFromServer(String date) {
		ArrayList<RecordInfo> recordList = new ArrayList<RecordInfo>();

		deleteAll();
		User user = new UserDaoImpl().getUserNow();
		if (user != null) {
			String userId = user.getUserId();
			getDataFromServer(userId, date);// 从服务器查询的数据还是加载在了本地，所以还要再回在数据库
			getdateFromDB(date);
		}

		return recordList;
	}

	// 从数据库查询
	public ArrayList<RecordInfo> getdateFromDB(String date) {
		ArrayList<RecordInfo> recordList = new ArrayList<RecordInfo>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		Cursor cursor = db.query("record_info", null, "date = ?",
				new String[] { date }, null, null, "id desc");
		
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String userId = cursor.getString(cursor.getColumnIndex("userId"));
			String startTime = cursor.getString(cursor
					.getColumnIndex("startTime"));
			String endTime = cursor.getString(cursor.getColumnIndex("endTime"));
			int situpCount = cursor.getInt(cursor.getColumnIndex("situpCount"));
			int calorie = cursor.getInt(cursor.getColumnIndex("calorie"));
			RecordInfo info = new RecordInfo(id, userId, startTime, endTime,
					situpCount, calorie, date);
			recordList.add(info);
		}
		cursor.close();
		return recordList;
	}

//	// 从服务器查询
	public void getDataFromServer(String userId, String date) {
		// TODO Auto-generated method stub

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("date", date);
		HttpUtils.get(LeUrls.GET_SITUP_INFO, params,
				new HttpCallback<String>() {
					public void onSuccess(String response) {
						LogUtils.e(response);
						Gson gson = new Gson();
						Type type = new TypeToken<MessageArray<RecordInfo>>() {
						}.getType();
						MessageArray<RecordInfo> infoMsg = gson.fromJson(
								response, type);
						if (infoMsg.getResCode().equals("0")) {
							List<RecordInfo> infos = infoMsg.getData();
							for (RecordInfo info : infos) {
								LogUtils.e(info);
								addRecordInfo(info);
							}

						} else {
							ToastUtils.showShort(infoMsg.getResMsg());
						}
					}

					@Override
					public void onError(Throwable e, boolean arg1) {
						if (!HttpUtils.isNetWork(App.context)) {
							ToastUtils.showShort("请检查网络状况");
						} else {
							LogUtils.e(e.getMessage());
						}
					}
				});

	}
	// 查询指定日期的运动数据
	public int[] getSumOfDate(String date) {

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		ArrayList<RecordInfo> recordList = new ArrayList<RecordInfo>();
		// Cursor cursor = db.query("record_info", null, "date = ?", new
		// String[]{date}, null, null, null);
		Cursor cursor = db
				.rawQuery(
						"select sum(situpCount),sum(calorie) from record_info where date= ?",
						new String[] { date });

		int[] sumOfDay = { 0, 0 };
		int i = 0;
		while (cursor.moveToNext()) {
			sumOfDay[i] = cursor.getInt(i);
			// Log.d("heheda", sumOfDay[i]+"");
			i++;
		}
		cursor.close();
		return sumOfDay;
	}

}
