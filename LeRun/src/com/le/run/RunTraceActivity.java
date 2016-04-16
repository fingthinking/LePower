package com.le.run;


import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.baidu.mapapi.utils.DistanceUtil;
import com.le.run.RunCountActivity.MyCount;
import com.le.run.db.LagLngData;
import com.le.run.db.RunData;
import com.le.run.entity.RequestData;
import com.le.run.entity.Run;
import com.le.run.fragment.RunPauseFragment;
import com.le.run.fragment.RunStartFragment;
import com.le.run.utils.MyDate;
import com.le.run.utils.MyFormat;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;


public class RunTraceActivity extends LocationActivity {
	/*protected BaiduMap mBaiduMap;
	protected MapView mMapView = null;*/
	private LocationManager locationManager;
	private final int UPDATE_LOCATION = 10;
	private final int UPDATE_TIME = 12;
	private final int SendDataNet = 13;
	TextView currentSpeed,averageSpeedText,calorieText,totaDistance,time;
	ImageView runCancel,runContinue,runPause,runEnd;
	AlertDialog.Builder dialogSaveData;
	Fragment runPauseContinue = new RunPauseFragment();
	boolean flag = true;
	
	ArrayList<LatLng> pointsAll = new ArrayList<LatLng>();//存放经纬度
	ArrayList<LatLng> pointsDraw = new ArrayList<LatLng>();
	
	int i = 0;//划线数组标志位
	LatLng arg0=null,arg1 = null;
	double time0,time1 = 0,time2 = 0;
	double distance = 0,mySpeed = 0,averageSpeed = 0,calorie = 0,allTime = 0,date = 0;
	int runId = 0;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	dialogSaveData = new AlertDialog.Builder(this); 
    	FragmentManager fm1 = getFragmentManager();
    	FragmentTransaction ft1 = fm1.beginTransaction();
    	ft1.replace(R.id.display_control, runPauseContinue);
		ft1.commit();
		if(timeThread == null){
			newThread();
			timeThread.start();
		}
		
		final User  user = new UserDaoImpl().getUserNow();
		
		System.out.println("AAAAAAAAA"+user.getUserId());
		runId = MyDate.getRunId();
		dialogSaveData .setTitle("数据保存提示");
		dialogSaveData .setMessage("数据云端同步，是否保存数据？");
		dialogSaveData .setCancelable(false);
		dialogSaveData .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if(distance*1000 > 2){
					//保存到数据库
					saveLagLngData(runId,pointsAll);
					RunData runData = new RunData(getApplicationContext());
							runData.addRunData(runId, user.getUserId(), distance, mySpeed, averageSpeed, calorie, count, MyDate.getDate());
							
					//发送到服务器
					final Run run = new Run(runId, user.getUserId(), distance, mySpeed, averageSpeed, calorie, count, MyDate.getDate()); 
					
					Thread thread = new Thread(){
						public void run() {
							RequestData requestData = new RequestData();
							int	flagNet= requestData.sendRunning(run, pointsAll);
							Log.d("responceCode", "flagnet = "+flagNet);
							Message message = new Message();
							Bundle bundle = new Bundle();
							bundle.putInt("reCode", flagNet);
							message.setData(bundle);
							message.what = SendDataNet;
							handler.sendMessage(message);						
						};
					};				
					thread.start();
					
				
						//	Toast.makeText(getApplicationContext(), "数据已云端###########同步成功！", 0).show();
							finish();
				}
				else Toast.makeText(getApplicationContext(), "距离太短数据未保存！", 0).show();
			}					
		});
		
		dialogSaveData .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				RunData rundata = new RunData(getApplicationContext());
				pointsAll.clear();
				finish();
			}
		});
		
    	locationManager = (LocationManager)getSystemService(Service.LOCATION_SERVICE);
    	if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			Toast.makeText(getApplicationContext(), "请打开GPS导航!", Toast.LENGTH_SHORT).show();           
			return;        
		}
    	time0 = time1 = time2 = System.currentTimeMillis();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
			
    }
	
	
	/* (non-Javadoc)
	 * @see com.le.run.LocationActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		currentSpeed = (TextView)runPauseContinue.getView().findViewById(R.id.currentSpeed);
		averageSpeedText = (TextView)runPauseContinue.getView().findViewById(R.id.averagespeed);
		calorieText = (TextView)runPauseContinue.getView().findViewById(R.id.calorie);
		totaDistance = (TextView)runPauseContinue.getView().findViewById(R.id.totalDistance);
		time = (TextView)runPauseContinue.getView().findViewById(R.id.time);
		/*runCancel = (ImageView) runPauseContinue.getView().findViewById(R.id.run_cancle);
		runContinue = (ImageView) runPauseContinue.getView().findViewById(R.id.run);*/
		runPause = (ImageView) runPauseContinue.getView().findViewById(R.id.run_pause);
		runEnd = (ImageView) runPauseContinue.getView().findViewById(R.id.run_end);
		 
		runPause.setOnClickListener(new OnClickListener() {	
			boolean flag1 = true;
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(flag1 == true){
				        flag = false;
				        runPause.setImageResource(R.drawable.run_continue);
				        flagCount = false;
				        timeThread = null;
					    flag1 = false;
				 }
				 
				 else if(flag1 == false){
					    flag = true;
				        runPause.setImageResource(R.drawable.run_pause);
				        flagCount = true;
				        timeThread = null;
				        count--;
				        newThread();
						timeThread.start();
				        flag1 = true;
				 }	        
					  
				  }	
		});
		
		runEnd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				flagCount = false;
				dialogSaveData .show();
			}
		});
	}
	
	
	
	private  LocationListener locationListener = new LocationListener() {
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
		
		String latLng;
		if (location != null) {   
			double lat = location.getLatitude();    
			double lng = location.getLongitude(); 
			
			LatLng latLng2 = new LatLng(lat, lng);
			CoordinateConverter converter = new CoordinateConverter();
			converter.from(CoordType.GPS);
			converter.coord(latLng2);
			LatLng desLatLng = converter.convert();
			
			location.setLatitude(desLatLng.latitude);
	        location.setLongitude(desLatLng.longitude);
	        pointsAll.add(new LatLng(location.getLatitude(),location.getLongitude()));
			
			// 更新坐标
			Message msg = new Message();
			
			msg.obj = location;
			msg.what = UPDATE_LOCATION;
			handler.sendMessage(msg);
			
			  latLng = "Latitude:" + lat + "  Longitude:" + lng;    
        } else {    

        latLng = "Can't access your location";  

        } 
      //  Toast.makeText(getApplicationContext(), "Your Location:" +latLng,Toast.LENGTH_LONG).show();
       

		}
	
	
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
	if(flag == true){	    	
			switch(msg.what){
			case UPDATE_LOCATION:
				Location location = (Location) msg.obj;
			    updateUi(location);
			    break;
			case UPDATE_TIME:
				time.setText(MyFormat.getClock(count));
				break;
			case SendDataNet:
			    Bundle bundle = msg.getData();
			    int i_1 = bundle.getInt("reCode");
				if(i_1==0){
					Toast.makeText(getApplicationContext(), "数据已云端同步成功！", 0).show();
					finish();
				}
				else if(i_1==-1)
					Toast.makeText(getApplicationContext(), "数据云端同步不成功！", 0).show();
				    finish();
				break;
			}
			
				
		}		
			super.handleMessage(msg);	 
		}
	};
	
	
	public void updateUi(Location location){
	       
		    	//计算距离
		       
		        if(pointsAll.size()<=1){
		        	 arg0 = arg1 = new LatLng(location.getLatitude(),location.getLongitude());
		        	 time0 = time1 = time2 = System.currentTimeMillis();
		          }
		        
		        else {
		        	arg1 = new LatLng(location.getLatitude(),location.getLongitude());
		            time2 = System.currentTimeMillis();
		        
		        
		        mySpeed = DistanceUtil.getDistance(arg0, arg1)/(time2-time1)*60;//表示km.min
		        mySpeed = Math.round(mySpeed*100)/100.0;
		        currentSpeed.setText(""+mySpeed);
		        
		        distance += DistanceUtil.getDistance(arg0, arg1);
		       
		        
		        allTime = (time2-time0)/1000.0;//s
		        averageSpeed = distance/allTime/1000*3600;//表示km.h
		        averageSpeed = Math.round(averageSpeed*100)/100.0;
		        
		        
		        averageSpeedText.setText(""+averageSpeed);
		        
		        calorie = Math.round((1.036*60*distance/1000)*100)/100.0;
		        calorieText.setText(""+calorie);
		       
		        
		        distance = Math.round((distance/1000)*100)/100.0;
		        totaDistance.setText(""+distance);
		        
		       
		        arg0 = arg1;
		        time1 = time2;
		        }       
		    	List<Integer> colors = new ArrayList<Integer>();
				
				if(mySpeed<=1){
					colors.add(Integer.valueOf(Color.RED));
				}
				else if(mySpeed<=1.5){
					colors.add(Integer.valueOf(Color.MAGENTA));
				}
				else if(mySpeed<2)
				colors.add(Integer.valueOf(Color.YELLOW));
				else colors.add(Integer.valueOf(Color.GREEN));
				pointsDraw.add(pointsAll.get(i++));
				
				//构建Marker图标  
        		BitmapDescriptor bitmap = BitmapDescriptorFactory  
        		    .fromResource(R.drawable.run_startend);  
        		//构建MarkerOption，用于在地图上添加Marker  
        		OverlayOptions option_1 = new MarkerOptions()  
        		    .position(pointsAll.get(0))  
        		    .icon(bitmap);  
        		//在地图上添加Marker，并显示  
        		mBaiduMap.addOverlay(option_1);
        		
        		
				if(pointsDraw.size()>=2){					
				OverlayOptions ooPolyline = new PolylineOptions().width(12)
				        .colorsValues(colors).points(pointsDraw);
				//添加在地图中
				Polyline  mPolyline = (Polyline)mBaiduMap.addOverlay(ooPolyline);
				pointsDraw.clear();
				pointsDraw.add(pointsAll.get(i-1));
        	
	}
}
	int count = 0;
	boolean flagCount = true;
	Thread timeThread  = null;
	private void newThread() {
		timeThread = new Thread(){
			public void run() {
				while(flagCount){
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
					//Log.i(TAG, "current Thread:"+Thread.currentThread().getName()+","+Thread.currentThread().getState());
				}
			};
		};
	}
	
	public void saveLagLngData(int runId,ArrayList<LatLng> latLng){
		        LagLngData laglngdata = new LagLngData(getApplicationContext());
		        laglngdata.addLatLngList(runId, latLng);
	}
	
	
    protected void onDestroy() {
    	super.onDestroy();
    	if(locationManager != null){
    		locationManager.removeUpdates(locationListener);
    	}
    };
		
 }
    
