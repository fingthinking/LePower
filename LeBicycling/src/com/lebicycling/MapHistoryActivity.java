package com.lebicycling;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.lebicycling.adapter.MySpinnerAdapter;
import com.lebicycling.entity.Bicycling;
import com.lebicycling.entity.MyLatLng;
import com.lebicycling.entity.SItem;

public class MapHistoryActivity extends Activity {

	MapView mMapView = null;
	BaiduMap mBaiduMap = null;
	private ArrayList<MyLatLng> mylist;
	private ArrayList<LatLng> baiduList;
	private ArrayList<Integer> colors;
	int history_bicyclingId = 0;
	private Spinner spinner;
	ArrayList<SItem> itemList = null;
	private ArrayList<Bicycling> biclist;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_map_history);
		init();
		setListener();

	}

	private void setListener() {
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SItem item = (SItem)parent.getItemAtPosition(position);
				history_bicyclingId = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	private void init() {
		mMapView = (MapView) findViewById(R.id.map_View);
		mBaiduMap = mMapView.getMap();
		baiduList = new ArrayList<LatLng>();
		colors = new ArrayList<Integer>();
		spinner = (Spinner) findViewById(R.id.sp_choose_runid);
		colors.add(Integer.valueOf(Color.RED));
		itemList = new ArrayList<SItem>();
		mylist = new ArrayList<MyLatLng>();
		Intent intent = getIntent();
		itemList = intent.getParcelableArrayListExtra("times");
		biclist = intent.getParcelableArrayListExtra("biclist");
		MySpinnerAdapter adapter = new MySpinnerAdapter(itemList,this);
		spinner.setAdapter(adapter);

	}





	private void readData() {

		
		List<MyLatLng> list = biclist.get(history_bicyclingId).getMylatList();
		
		if(list == null){
			return;
		}
		
		for(int i= 0;i<list.size();i++){
			mylist.add(list.get(i));
		}
		
		for(int i=0;i<mylist.size();i++){
			LatLng point = new LatLng(mylist.get(i).getLatitude(), mylist.get(i).getLongitude());
			baiduList.add(point);
		}

		if(baiduList.size() == 0){
			Toast.makeText(getApplicationContext(), "当前轨迹不存在", Toast.LENGTH_SHORT).show();
			return;
		}

		drawTrace();


	}
	//  处理按钮点击事件
	public void checkHistory(View v){
		// 从数据库中读取路径轨迹信息
		mylist.clear();
		baiduList.clear();
		mBaiduMap.clear();
		readData();
		// 用于定位轨迹在地图中中心的位置
		isFirstLoc = true;
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mMapView.onDestroy();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mMapView.onResume();
		super.onResume();
	}

	boolean isFirstLoc = true;

	// 绘制在地图上的轨迹
	private void drawTrace() {

		int size = baiduList.size();
		if(size >= 2){
			PolylineOptions plo = new PolylineOptions();
			OverlayOptions ooPolyline = plo.width(10)
					.colorsValues(colors).points(baiduList);

			//添加在地图中
			Polyline  mPolyline = (Polyline)mBaiduMap.addOverlay(ooPolyline);	
		}

		// 第一次打开页面时，将定位的坐标作为屏幕中心
		if(isFirstLoc){
			isFirstLoc = false;		    	
			// 显示当前定位页面
			MapStatus.Builder builder = new MapStatus.Builder();
			// 设置地图中心点
			LatLng position = baiduList.get(size/2);
			builder.target(position).zoom(17.0f);
			mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
		}

		// 往地图上加入图标，表示起始位置
		LatLng first = new LatLng(baiduList.get(0).latitude, baiduList.get(0).longitude);
		LatLng end = new LatLng(baiduList.get(size-1).latitude, baiduList.get(size-1).longitude);
		BitmapDescriptor fDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
		BitmapDescriptor eDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_markb);
		OverlayOptions fOption = new MarkerOptions().position(first)
				.animateType(MarkerAnimateType.grow)
				.icon(fDescriptor);
		OverlayOptions eOption = new MarkerOptions().position(end)
				.animateType(MarkerAnimateType.drop)
				.icon(eDescriptor);
		mBaiduMap.addOverlay(fOption);
		mBaiduMap.addOverlay(eOption);
	}

}
