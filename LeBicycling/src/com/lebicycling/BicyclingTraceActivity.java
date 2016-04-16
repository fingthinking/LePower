package com.lebicycling;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.baidu.mapapi.utils.DistanceUtil;
import com.lebicycling.db.BicyclingDao;
import com.lebicycling.db.LatLngDao;
import com.lebicycling.entity.Bicycling;
import com.lebicycling.entity.MyLatLng;
import com.lebicycling.utils.MyDate;
import com.lebicycling.utils.MyFormat;
import com.lebicycling.utils.RequestData;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;
import com.lepower.utils.ToastUtils;

public class BicyclingTraceActivity extends LocationActivity {

	ArrayList<LatLng> trace,saveTrace;
	private List<Integer> colors;
	public final int UPDATE_LOCATION = 11;
	public final int UPDATE_TIME = 12;
	public final int CLEAR_OK = 13;
	public final int SAVE_DATEBASE_NUM = 200;
	double total_distance = 0; 
	double startTime = 0, endTime=0, interval=0;
	double averageSpeed=0,currentSpeed=0,calorie=0;
	TextView tTotalDistance,tTime,tAverageSpeed,tCurrentSpeed,tCalorie; 
	ImageView startIV,endIV,pauseIV; 
    int bicyclingId;

	private LocationManager locationManager;
	public final static String TAG = "Log";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
		
		
		locationManager = (LocationManager)getSystemService(Service.LOCATION_SERVICE);
		// 判断是否打开GPS    
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			Toast.makeText(getApplicationContext(), "请先打开GPS定位...", Toast.LENGTH_SHORT).show();           
			return;        
		}
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, listener);
		setListener();
		
	   
	}


	int count=0;
	boolean flag = true;
	Thread timeThread  = null;
	// 用于计时的线程
	private void newThread() {

		timeThread = new Thread(){
			public void run() {
				while(flag){
					try {
						count++;
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message msg = Message.obtain();
					msg.arg1 = count;
					msg.what = UPDATE_TIME;
					handler.sendMessage(msg);
//					Log.i(TAG, "current Thread:"+Thread.currentThread().getName()+","+Thread.currentThread().getState());
				}
			};
		};
	}

	private void setListener() {
		startIV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(timeThread == null){
					newThread();
					timeThread.start();
					startIV.setVisibility(View.GONE);
				}

			}
		});

		pauseIV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(flag == false){
					flag = true;
					count--;
					newThread();
					timeThread.start();
					pauseIV.setImageResource(R.drawable.ic_sport_pause_night);
				}else{
					flag = false;
					timeThread = null;
					pauseIV.setImageResource(R.drawable.ic_sport_continue);
				}

			}
		});
		
		endIV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 在退出的时候判读trace是否还有经纬度数据，如果有则保存到本地
				if(trace.size() != 0 ){
					saveToDateBase(2);
				}
				// 保存骑车记录到本地，并同时上传网络
				saveBicyclingData();
				finish();
			}
		});


	}
	// ��ʼ��View
	private void initView() {
		bicyclingId = MyDate.getBicyclingId();
		trace = new ArrayList<LatLng>();  // �洢��γ�����ڻ��ƹ켣
		saveTrace = new ArrayList<LatLng>();
		tTotalDistance = (TextView) findViewById(R.id.totalDistance);
		tTime = (TextView) findViewById(R.id.time);
		tAverageSpeed = (TextView) findViewById(R.id.speed);
		tCurrentSpeed = (TextView) findViewById(R.id.currentSpeed);
		startIV = (ImageView) findViewById(R.id.IVstart);
		endIV = (ImageView) findViewById(R.id.IVfinish);
		pauseIV = (ImageView) findViewById(R.id.IVpause);
		tCalorie = (TextView) findViewById(R.id.calorie);

		colors = new ArrayList<Integer>();
		colors.add(Integer.valueOf(Color.RED));
		//		colors.add(Integer.valueOf(Color.BLUE));
		//		colors.add(Integer.valueOf(Color.YELLOW));
		//		colors.add(Integer.valueOf(Color.GREEN));
	}

	@Override
	protected void onResume() {
		
		super.onResume();
	}


	@Override
	protected void onPause() {
		
		
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

	double tempTime; //tempTime指定的日期
	
	public Handler handler = new Handler(){

		public void handleMessage(Message msg) {
			
			switch(msg.what){
			case UPDATE_LOCATION:
				UpdateUI(msg);
				break;
			case UPDATE_TIME:
				 tTime.setText(MyFormat.getClock(count));
				break;
			case CLEAR_OK:
				saveTrace.clear();
				break;
			default:
				break;
			}


		}

	

		private void UpdateUI(Message msg) {

			// 更新显示的数据
			if(total_distance == 0){
				tTotalDistance.setText("0.00");
			}else{
				tTotalDistance.setText(""+MyFormat.roundOff(total_distance/1000));
			}

			if(averageSpeed == 0 ){
				tAverageSpeed.setText("0.00");
			}else{

				tAverageSpeed.setText(""+MyFormat.roundOff(averageSpeed));
			}

			if(currentSpeed == 0){
				tCurrentSpeed.setText("0.00");
			}else{

				tCurrentSpeed.setText(""+MyFormat.roundOff(currentSpeed));
			}

			if(calorie == 0){
				tCalorie.setText("0.00");
			}else{
				tCalorie.setText(""+MyFormat.roundOff(calorie));
			}
		
		};



	};

	// 位置监听回调函数
	private  LocationListener listener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(Location location) {
			updateLocation(location);


		}
	};


	private void updateLocation(Location location) {
		LatLng newPosition;

		if (location != null) {   
			double lat = location.getLatitude();    
			double lng = location.getLongitude(); 

			//GPS经纬度转变成百度坐标
			LatLng position = new LatLng(lat, lng);
			CoordinateConverter converter = new CoordinateConverter();
			converter.from(CoordType.GPS);
			converter.coord(position);
			newPosition = converter.convert();
			trace.add(newPosition);
			calculateNum(newPosition);
			drawTrace();
			// 发送消息给主线程更新数据
			Message msg = new Message();
			msg.what = UPDATE_LOCATION;
			handler.sendMessage(msg);

		}
		
        // 单经纬度数据达到一定值后，保存如本地数据库中
		saveToDateBase(SAVE_DATEBASE_NUM);
	}

	 // 单经纬度数据达到一定值后，保存如本地数据库中
	private void saveToDateBase(int num) {
		
		if(trace.size() >= num && num >= 2){
			LatLng pre = trace.get(trace.size()-2);
			LatLng last = trace.get(trace.size()-1);
			saveTrace.addAll(trace);

			new Thread(){
				public void run() {
					LatLngDao dao = LatLngDao.getInstance(getApplicationContext());
					dao.addLatLng(bicyclingId, saveTrace);
					// 保存完后清空saveTrace
					Message msg = Message.obtain();
					msg.what = CLEAR_OK;
					handler.sendMessage(msg);
				};
			}.start();
			trace.clear();
			trace.add(pre);
			trace.add(last);
		}
	}

	// 根据位置的变化计算数据
	private void calculateNum(LatLng newPosition) {
		LatLng prePosition;
		double distance = 0;
		int size = trace.size();

		if( size >= 2){
			prePosition = trace.get(size-2);
			endTime = System.currentTimeMillis();
		}else{
			prePosition = newPosition;
			startTime = endTime = System.currentTimeMillis();
		}

		interval = (endTime - startTime)/1000;

		distance = DistanceUtil.getDistance(prePosition, newPosition);
		total_distance =total_distance + distance;

		currentSpeed = distance/((endTime - tempTime)/1000);
		//				Log.i("Log", "total distance:"+total_distance+"  , "+distance);
		tempTime = endTime;


		// 防止数据计算出错
		if(interval == 0){
			averageSpeed = 0;
		}else{
			averageSpeed = total_distance/interval;
		}
		// 卡路里计算
		calorie = 60*(total_distance/1000)*1.036;
	}

    // 在地图上绘制轨迹
	private void drawTrace() {
		if(trace.size() >= 2){
			PolylineOptions plo = new PolylineOptions();
			OverlayOptions ooPolyline = plo.width(10)
					.colorsValues(colors).points(trace);

			mBaiduMap.addOverlay(ooPolyline);	
		}

	}
	
	// 保存骑车记录到本地，并同时上传网络
	private void saveBicyclingData() {
		new Thread(){
			public void run() {
				 // 获取用户的ID
				UserDaoImpl udao = new UserDaoImpl();
				User user = udao.getUserNow();
				if(user !=null){
					BicyclingDao dao = BicyclingDao.getInstance(getApplicationContext());
					
					Bicycling bicycling = new Bicycling(bicyclingId, user.getUserId(), MyFormat.roundOff(total_distance/1000), MyFormat.roundOff(currentSpeed), 
							MyFormat.roundOff(averageSpeed), MyFormat.roundOff(calorie), MyFormat.roundOff(interval/60), MyDate.getDate(System.currentTimeMillis()));
					// 当数据有意义时上传
					if(bicycling.getTotalDistance() != 0){
						dao.addBicyclingData(bicycling);
						
						LatLngDao latDao = LatLngDao.getInstance(getApplicationContext());
						ArrayList<MyLatLng> latList = latDao.getTraceById(bicyclingId);
						// 网络上传数据
						RequestData request = new RequestData();
						request.sendBicycling(bicycling, latList);
						Log.i(TAG, "distance:"+bicycling.getTotalDistance());
					}
					
					
					
				}
				
				
			};
		}.start();
		
	}
	


}
