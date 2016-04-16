package com.le.run;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.le.run.listener.MyLocationListener;

public class LocationActivity extends Activity {

	protected MapView mMapView = null;
	protected BaiduMap mBaiduMap = null;
	protected LocationClient mClient = null;
	protected MyLocationListener mYlistener;
	private boolean flagChangeMapType = false,flagZoom = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.run_blank);
		mMapView = (MapView) findViewById(R.id.bmapView);
		
        mBaiduMap = mMapView.getMap();
        mYlistener = new MyLocationListener(mMapView);
        
    	
        int childCount = mMapView.getChildCount();
        View zoom = null;
        for (int i = 0; i < childCount; i++) {
                View child = mMapView.getChildAt(i);
                if (child instanceof ZoomControls) {
                        zoom = child;
                        break;
                }
        }
      //  zoom.setVisibility(View.GONE);
        
		// 定位设置
		mBaiduMap.setMyLocationEnabled(true);
		mClient = new LocationClient(getApplicationContext());
		mClient.registerLocationListener(mYlistener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开GPS
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mClient.setLocOption(option);
		mClient.start();
	}	 
	
	
	
	public void zoomInOut(View v){
		   ImageView zoomInOut = (ImageView) findViewById(R.id.zoom_in_out);
		   FrameLayout frameLayout = (FrameLayout) findViewById(R.id.display_control);
		   if(flagZoom == false){
			   frameLayout.setVisibility(View.GONE);
			   zoomInOut.setImageResource(R.drawable.zoom_out); 
			   flagZoom = true;
		   }
		   else if(flagZoom == true){
			   frameLayout.setVisibility(View.VISIBLE);;
			   zoomInOut.setImageResource(R.drawable.zoom_in); 
			   flagZoom = false;
		   }
		   
	}

	 public void setMapType(View v){
		  Button setMapType = (Button) findViewById(R.id.setMapType);
	        
  	   if(flagChangeMapType == false){
  	        mBaiduMap.setMapType(mBaiduMap.MAP_TYPE_SATELLITE);
  	        setMapType.setText("普通图");
  	        flagChangeMapType = true;
  	   }
  	   else if(flagChangeMapType == true){
  		   mBaiduMap.setMapType(mBaiduMap.MAP_TYPE_NORMAL);
 	           setMapType.setText("卫星图");
 	           flagChangeMapType = false;
  	   }
     }   
	@Override  
	protected void onDestroy() {  
		super.onDestroy();  
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
	//	mMapView.onDestroy();  
		mBaiduMap.setMyLocationEnabled(false);
		mClient.stop();
	}  
	@Override  
	protected void onResume() {  
		super.onResume();  
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
		mMapView.onResume();  
	}  
	@Override  
	protected void onPause() {  
		super.onPause();  
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
		mMapView.onPause();  
	}  
}
