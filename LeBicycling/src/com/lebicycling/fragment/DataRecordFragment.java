package com.lebicycling.fragment;


import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lebicycling.R;
import com.lebicycling.db.BicyclingDao;
import com.lebicycling.db.LatLngDao;
import com.lebicycling.entity.Bicycling;
import com.lebicycling.entity.MyLatLng;
import com.lebicycling.entity.SItem;
import com.lebicycling.utils.DateFormChange;
import com.lebicycling.utils.MyDate;
import com.lebicycling.utils.MyFormat;
import com.lebicycling.utils.RequestData;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;

public class DataRecordFragment extends Fragment {

	private TextView tDistance,tTimes,tDuration,tCalorie,tDate;
	private ArrayList<Bicycling> bicyclinglists;
	public static final int DATA_READY = 11;
	public static final int DATA_NET = 10;
	public final int Bar_number = 5;
	private double distance = 0, times = 0, duration = 0, calorie = 0;
	public final long DAY_SECOND = 86400000;
	public String TAG = "Log";
	boolean isCalculated = true;
	String date;
	private BarChart mChart;
	private long tempDate;
	private long nowDate;  

	@Override 
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		date = getArguments().getString("date");
//		  Log.i("Log", "date:"+date);
		final String another = MyDate.getDate(System.currentTimeMillis()-2*DAY_SECOND);
		tempDate = Long.valueOf(DateFormChange.getNewForm(another));
		nowDate = Long.valueOf(DateFormChange.getNewForm(date));
		
        // 如果指定的日期有数据，则删除本地的骑车记录和经纬度信息
		clearData(another);
	}

	// 如果指定的日期有数据，则删除本地的骑车记录和经纬度信息
	private void clearData(final String another) {
		new Thread(){
			public void run() {
				BicyclingDao bicDao = BicyclingDao.getInstance(getActivity());
				UserDaoImpl dao = new UserDaoImpl();
				User user = dao.getUserNow();
				if(user != null){
					ArrayList<Bicycling> biclist = bicDao.getBicyclingDataByDate(another, user.getUserId());
					if(biclist != null){
						LatLngDao latDao = LatLngDao.getInstance(getActivity());
						for(int i=0;i<biclist.size();i++){
							latDao.deleteLatLng(biclist.get(i).getBicyclingId());
						}
						bicDao.deleteBicyclingAll(another);
					}
					
				}
			};
		}.start();
	}


	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.data_record_fragment, container,false);
		initView(view);
		setChart();
		processData();
		return view;

	}


	private void initView(View view) {
		tDistance = (TextView)view.findViewById(R.id.tv_total_distance);
		tTimes = (TextView)view.findViewById(R.id.tv_all_times);
		tDuration = (TextView)view.findViewById(R.id.tv_all_long);
		tCalorie = (TextView) view.findViewById(R.id.tv_all_calorie);
		tDate = (TextView) view.findViewById(R.id.tv_date);
		mChart = (BarChart) view.findViewById(R.id.chart1);
		bicyclinglists = new ArrayList<Bicycling>();
		tDate.setText(date);
	}



	public  Handler handler = new Handler(){

		public void handleMessage(Message msg) {
			switch(msg.what){
			case DATA_READY:
				calculate();
				setDataToChart();
				updateUI();
				break;
			default:
				break;
			}
		}

	};


	private void setChart() {

		mChart.setDescription("(km)");
		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		mChart.setMaxVisibleValueCount(60);
		// scaling can now only be done on x- and y-axis separately
		mChart.setPinchZoom(false);
		mChart.setDrawBarShadow(false);
		mChart.setDrawGridBackground(false);
		mChart.setTouchEnabled(true);
		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setSpaceBetweenLabels(0);
		xAxis.setDrawGridLines(false);

		mChart.getAxisLeft().setDrawGridLines(false);
		// add a nice and smooth animation
		mChart.animateY(2500);
		Legend legend = mChart.getLegend();
		legend.setEnabled(false);

	}

	private RequestData request;

	private void processData() {
		UserDaoImpl dao = new UserDaoImpl();
		final User user = dao.getUserNow();
	
		if(user != null){
			if(nowDate > tempDate){
				
				//本地读取
				new Thread(){
					public void run() {
						bicyclinglists = getData(date,user.getUserId());
						if(bicyclinglists.size() == 0){
							request = new RequestData();
							bicyclinglists  = request.sendRequest(user.getUserId(),date);
							
						}
						Message message = Message.obtain();
						message.what = DATA_READY;
						handler.sendMessage(message);
					}
				}.start();
				
			}else{
				// 网上读取
				new Thread(){
					public void run() {
						request = new RequestData();
						bicyclinglists  = request.sendRequest(user.getUserId(),date);
//					Log.i(TAG, "request list is Empty ?:"+(bicyclinglists==null?true:false)+" "+bicyclinglists);
						
						Message message = Message.obtain();
						message.what = DATA_READY;
						handler.sendMessage(message);
					};
				}.start();
			}
			
		}else{
			Toast.makeText(getActivity(), "用户没有登录", 0).show();
		}

	}

	
	private void setDataToChart() {
		ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
		if(bicyclinglists == null){
			return;
		}
		int size = bicyclinglists.size();

		for (int i = 0; i <size; i++) {
			yValues.add(new BarEntry((float)(bicyclinglists.get(i).getTotalDistance()), i));
		}

		ArrayList<String> xValues = new ArrayList<String>();
		if(size <= Bar_number){
			for(int i=0;i < Bar_number; i++){
				xValues.add("第"+(i+1)+"次");
			}
		}else{
			for (int i = 0; i < size; i++) {
				xValues.add("第"+(i+1)+"次");
			}

		}

		BarDataSet set1 = new BarDataSet(yValues, "Data Set");
		set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
		set1.setDrawValues(false);
		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(set1);
		BarData data = new BarData(xValues, dataSets);
		mChart.setData(data);
		mChart.invalidate();
	};
	
	
	private void updateUI() {
		if(distance == 0){
			tDistance.setText(" 0.00");
		}else{
			tDistance.setText(""+(distance<10 ? " "+MyFormat.roundOff(distance):MyFormat.roundOff(distance)));
		}
		if(duration == 0){
			tDuration.setText("0.00");
		}else{
			tDuration.setText(""+MyFormat.roundOff(duration));

		}
		if(calorie == 0){
			tCalorie.setText("0.00");
		}else{
			tCalorie.setText(""+MyFormat.roundOff(calorie));
		}
		tTimes.setText(""+times);
		
	}

	// ������ݺ󣬽��м���
	private void calculate() {
		if(bicyclinglists == null){
			return;
		}
		times = bicyclinglists.size();
		
		if(isCalculated){
			for(int i=0;i<times;i++){
				distance += bicyclinglists.get(i).getTotalDistance();
				duration += bicyclinglists.get(i).getTime();
				calorie  += bicyclinglists.get(i).getCalorie();
			}
			isCalculated = false;	
		}

	}


	private ArrayList<Bicycling> getData(String date,String userId) {
		BicyclingDao dao = BicyclingDao.getInstance(getActivity());
		LatLngDao latDao = LatLngDao.getInstance(getActivity());
		ArrayList<Bicycling> list = dao.getBicyclingDataByDate(date,userId);
		for(int i=0;i<list.size();i++){
			ArrayList<MyLatLng> latLngList = latDao.getTraceById(list.get(i).getBicyclingId());
			list.get(i).setMylatList(latLngList);
		}
		return list;
	}

	
	public ArrayList<SItem> getItemList(){
		ArrayList<SItem> items = new ArrayList<SItem>();
		for(int i=0;i<bicyclinglists.size();i++){
			SItem item = new SItem("第"+(i+1)+"次骑车", bicyclinglists.get(i).getBicyclingId());
			items.add(item);
		}
		return items;
	}
	
	
	
	public ArrayList<Bicycling> getBicyclingList(){
		return bicyclinglists;
	}
	

}
