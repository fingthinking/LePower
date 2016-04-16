package com.le.run;


import java.util.ArrayList;
import java.util.List;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.le.run.db.LagLngData;
import com.le.run.db.RunData;
import com.le.run.entity.MyLatLng;
import com.le.run.entity.Run;
import com.le.run.listener.MyLocationListener;
import com.le.run.utils.ShareUtils;


import android.app.Activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


public class RunDetialRecordActivity extends Activity {

	protected MapView mMapView = null;
	protected BaiduMap mBaiduMap = null;
	protected LocationClient mClient = null;
	protected MyLocationListener mYlistener;
	View view = null;

	ArrayList<Run> listRun = null;
	ArrayList<MyLatLng> listLanLng = null;
	private ArrayList<LatLng> pointsDraw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.run_record);

		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		mYlistener = new MyLocationListener(mMapView);
		mBaiduMap.setMyLocationEnabled(true);
		mClient = new LocationClient(this);
		mClient.registerLocationListener(mYlistener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // ��GPS
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		mClient.setLocOption(option);
		mClient.start();

		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		int runIdBack = bundle.getInt("runId");
		System.out.println("runId" + runIdBack);
		RunData runData = new RunData(getApplicationContext());
		listRun = runData.getRunData();
		LagLngData lagLngData = new LagLngData(getApplicationContext());
		listLanLng = lagLngData.getLagLngFromRunId(runIdBack);

		TextView distance = (TextView) findViewById(R.id.record_totalDistance);
		TextView averageSpeed = (TextView) findViewById(R.id.record_averagespeed);
		TextView currentSpeed = (TextView) findViewById(R.id.record_currentSpeed);
		TextView calorie = (TextView) findViewById(R.id.record_calorie);
		TextView time = (TextView) findViewById(R.id.record_time);
        
		Run run = new Run(0, null, 0, 0, 0, 0, 0, null);
		for (int i = 0; i < listRun.size(); i++) {
			if (listRun.get(i).getRunId() == runIdBack) {
				run = listRun.get(i);
			}
		}

		distance.setText("" + run.getTotalDistance());
		averageSpeed.setText("" + run.getAverageSpeed());
		currentSpeed.setText("" + run.getCurrentSpeed());
		calorie.setText("" + run.getCalorie());
		time.setText("" + run.getTime());

		pointsDraw = new ArrayList<LatLng>();
		for (int i = 0; i < listLanLng.size(); i++) {
			pointsDraw.add(new LatLng(listLanLng.get(i).getLatitude(),
					listLanLng.get(i).getLongitude()));
			System.out.println("��γ��" + listLanLng.get(i).getLatitude() + "::"
					+ listLanLng.get(i).getLongitude());
		}

		// ����Markerͼ��
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.run_startend);
		// ����MarkerOption�������ڵ�ͼ�����Marker
		OverlayOptions option_1 = new MarkerOptions().position(
				pointsDraw.get(0)).icon(bitmap);
		// �ڵ�ͼ�����Marker������ʾ
		mBaiduMap.addOverlay(option_1);

		List<Integer> colors = new ArrayList<Integer>();
		colors.add(Color.GREEN);
		if (pointsDraw.size() > 1) {
			OverlayOptions ooPolyline = new PolylineOptions().width(12)
					.colorsValues(colors).points(pointsDraw);
			// ����ڵ�ͼ
			Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
		}

		int j = pointsDraw.size() - 1;
		OverlayOptions option_2 = new MarkerOptions().position(
				pointsDraw.get(j)).icon(bitmap);
		// �ڵ�ͼ�����Marker������ʾ
		mBaiduMap.addOverlay(option_2);

	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		 //view = LayoutInflater.from(this).inflate(R.layout.run_record, null);
		view = getWindow().getDecorView();
		ImageView run_recordScreen = (ImageView) findViewById(R.id.run_recordscreen);
		run_recordScreen.setOnClickListener(new OnClickListener() {
			Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/temp.jpg");

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShareUtils.screenShot(view);
				ShareUtils.shareImage(RunDetialRecordActivity.this, uri, null);

			}
		});
		mMapView.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		// mMapView.onDestroy();
		mBaiduMap.setMyLocationEnabled(false);

	}


}
