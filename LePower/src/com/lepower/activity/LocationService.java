package com.lepower.activity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.LocationInfo;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LocationUtil;
import com.lepower.utils.NetWorkUtil;

public class LocationService extends Service{
	
	private static String URL = "";
	
//	private static long mIntervalTime = 1800000l;//半小时
	
	private static long mIntervalTime = 60000l;//60s
	
	private LocationInfo info;
	
	private TimerTask task;
	
	private Timer timer;
	
	private String userId;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("heheda", "jinrufuwu");
		//获取用户Id
		IUserDao iUserDao=new UserDaoImpl();
		User userNow=iUserDao.getUserNow();
		userId = userNow.getUserId(); 
		timer = new Timer();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				startTimerTask();
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 开启一个定时器,半小时更新一次经纬度并传递给后台
	 */
	private void startTimerTask() {
		// TODO Auto-generated method stub
		task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				info = LocationUtil.getLocation(getService()); //更新经纬度	
				if (info != null && NetWorkUtil.isNetWork(getService())){
					String URL1 = LeUrls.UPDATE_WEIZHI + userId + "&longitude=" + info.getLongitude() + "&latitude=" + info.getLatitude();//拼接访问地址
					Log.d("heheda", URL1);
					HttpUtils.getResult(URL1);
				}
				
			}
		};	
		timer.schedule(task, 0, mIntervalTime);
	}
	
	private LocationService getService(){
		return LocationService.this;
	}
	
	//停止服务和定时器
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		timer.cancel();//取消定时器
		super.onDestroy();
	}

}
