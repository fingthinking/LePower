package com.lebicycling;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.lebicycling.listener.MyLocationListener;

public class LocationActivity extends Activity {

	MapView mMapView = null;
	BaiduMap mBaiduMap = null;
	LocationClient mClient = null;
	MyLocationListener listener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_location);
		mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        listener = new MyLocationListener(mMapView);
        
		// ��λ����
		mBaiduMap.setMyLocationEnabled(true);
		mClient = new LocationClient(this);
		mClient.registerLocationListener(listener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // ��gps
		option.setCoorType("bd09ll"); 
		option.setScanSpan(5000);
		mClient.setLocOption(option);
		mClient.start();

	}

	@Override  
	protected void onDestroy() {  
		super.onDestroy();  
		
		mMapView.onDestroy();  
		mBaiduMap.setMyLocationEnabled(false);
		mClient.stop();
	}  
	@Override  
	protected void onResume() {  
		super.onResume();  
		
		mMapView.onResume();  
	}  
	@Override  
	protected void onPause() {  
		super.onPause();  
		
		mMapView.onPause();  
	}  

	
}
