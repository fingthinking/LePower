package com.lestep.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;
import com.lepower.utils.LogUtils;
import com.lestep.App;
import com.lestep.R;
import com.lestep.dao.IStepDao;
import com.lestep.dao.impl.StepDaoImpl;
import com.lestep.model.Step;
import com.lestep.utils.DateUtils;
import com.lestep.utils.NumFormat;

@ContentView(R.layout.activity_history)
public class HistoryActivity extends BaseActivity implements
		OnChartValueSelectedListener {

	@ViewInject(R.id.bar_chart)
	BarChart barChart;
	@ViewInject(R.id.all_day_meter) 
	TextView txtDayMeter;
	@ViewInject(R.id.all_day_caloria)
	TextView txtDayCaloria;
	@ViewInject(R.id.all_day_times)
	TextView txtDayTimes;
	@ViewInject(R.id.all_day_speed)
	TextView txtDaySpeed;
	@ViewInject(R.id.txt_title)
	TextView txtTitle;
	@ViewInject(R.id.btn_share)
	ImageButton btnShare;
	// 步子数据
	private Step todayStep;
	// 获得所有天的数据
	private List<Step> stepList;
	
	private IStepDao stepDao;
	private IUserDao userDao;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userDao = new UserDaoImpl();
		user = userDao.getUserNow();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// 初始化
		initData();
		initBarChart();
		// 初始化数据
		try {
			initDataSet();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onResume();
	}

	// 初始化BarChart
	private void initBarChart() {
		// 最多显示60个数据
		//barChart.setMaxVisibleValueCount(60);
		// 不允许缩放
		barChart.setPinchZoom(false);
		// 进制双击缩放
		barChart.setDoubleTapToZoomEnabled(false);
		// 设置可缩放
		barChart.setScaleEnabled(true);

		// 禁止拖动
		barChart.setDragEnabled(true);
		// 允许高亮
		barChart.setHighlightEnabled(true);
		// 设置高亮箭头
		// barChart.setDrawHighlightArrow(true);
		// 设置阴影
		barChart.setDrawBarShadow(false);
		// 设置不允许显示网格
		barChart.setDrawGridBackground(false);
		// 设置y轴显示使用的时间
		barChart.animateY(2500);
		// 不设置描述
		barChart.getLegend().setEnabled(false);
		// 设置不显示描述
		barChart.setDescription("");

		// 设置x轴
		XAxis xAxis = barChart.getXAxis();
		// x轴的线不显示
		xAxis.setDrawAxisLine(false);
		// 不允许使用x轴网格
		xAxis.setDrawGridLines(false);
		// 设置标签之间的间距
		xAxis.setSpaceBetweenLabels(0);
		xAxis.setTextColor(Color.WHITE);
		xAxis.setTextSize(18);
		
		xAxis.setPosition(XAxisPosition.BOTTOM);

		// 右侧
		barChart.getAxisRight().setEnabled(false);
		barChart.setDrawHighlightArrow(false);
		
		
		YAxis yAxis = barChart.getAxisLeft();
		// 不画线
		yAxis.setDrawAxisLine(false);
		yAxis.setDrawLabels(false);

		barChart.setData(new BarData());
		barChart.invalidate();
		barChart.setOnChartValueSelectedListener(this);
	}
	
	private void initData(){
		stepDao = new StepDaoImpl(user.getUserId());
		stepList = stepDao.findAllIsDay();
		todayStep = stepList.get(stepList.size()-1);
	}
	/**
	 * 初始化图形的数据
	 * @throws DbException
	 */
	private void initDataSet() throws DbException {
		BarData barData = barChart.getBarData();
		BarDataSet set = barData.getDataSetByIndex(0);
		if (set == null) {
			set = new BarDataSet(new ArrayList<BarEntry>(), "");
			barData.addDataSet(set);
		}
		
//		long oneDay = 1000 * 60 * 60 * 24;
		// stepList = new ArrayList<Step>();
		// for (int i = 0; i < 20; i++) {
		// Step step = new Step();
		// step.setDay(
		// DateUtils.getDate(new Date(System.currentTimeMillis()
		// - (20 - i) * oneDay), "yyyy-MM-dd"));
		// step.setSteps((long) (Math.random() * 20000));
		// stepList.add(step);
		// }
		int count = 0;
		for (Step step : stepList) {
			barData.addXValue(convertDay(step.getDay()));
			barData.addEntry(new BarEntry(step.getSteps(), count++), 0);
		}
		
		while(count++<7){
			barData.addXValue("");
		}
		
		txtTitle.setText(convertDayToTitile(todayStep.getDay()) + "记录:");
		txtDayMeter.setText(todayStep.getDistance() + "米");
		txtDaySpeed.setText(todayStep.getSpeed() + "步/分");
		txtDayTimes
				.setText(DateUtils.getTimeFromMili(todayStep.getStepTime()));
		txtDayCaloria.setText(NumFormat.roundOff(todayStep.getCaloria(),2) + "千卡");
		set.setDrawValues(true);
		set.setValueTextColor(Color.RED);
		set.setValueTextSize(18);
		set.setColors(ColorTemplate.VORDIPLOM_COLORS);
		barChart.notifyDataSetChanged();
		barChart.setVisibleXRangeMaximum(7);
		barChart.highlightValue(set.getEntryCount() - 1, 0);
		barChart.moveViewTo(set.getEntryCount() - 1, 0, AxisDependency.LEFT);
	}

	/**
	 * 将yyyy-MM-dd 转化为 MM/dd,如果是今天，则显示今天
	 * 
	 * @param day
	 * @return
	 */
	private String convertDay(String time) {
		if (time.equals(DateUtils.getDate(System.currentTimeMillis(),
				"yyyy-MM-dd"))) {
			return "今天";
		}
		String[] strArr = time.split("-");
		int month = Integer.parseInt(strArr[1]);
		int day = Integer.parseInt(strArr[2]);
		return month + "/" + day;
	}

	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight highlight) {
		// TODO Auto-generated method stub
//		LogUtils.e("Hightlight", highlight.getXIndex());
//		LogUtils.e("Hightlight", stepList.get(highlight.getXIndex()).getDay());
		
		todayStep = stepList.get(highlight.getXIndex());
		txtTitle.setText(convertDayToTitile(todayStep.getDay()) + "记录");
		txtDayMeter.setText(todayStep.getDistance() + "米");
		txtDaySpeed.setText(todayStep.getSpeed() + "步/分");
		txtDayTimes.setText(DateUtils.getTimeFromMili(todayStep.getStepTime()));
		txtDayCaloria.setText(NumFormat.roundOff(todayStep.getCaloria(),2) + "千卡");

	}

	@Event(value = { R.id.btn_prev,R.id.btn_share})
	private void click(View view) {
		switch (view.getId()) {
		case R.id.btn_prev:
			finish();
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			break;
		case R.id.btn_share:
		{
			Intent intent = new Intent(this,ShareActivity.class);
			intent.putExtra("todayStep", todayStep);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			App.mActivity = this;
			break;
		}
		default:
			break;
		}
	}

	/**
	 * 将yyyy-MM-dd 转化为 MM/dd,如果是今天，则显示今天
	 * 
	 * @param day
	 * @return
	 */
	private String convertDayToTitile(String time) {
		if (time.equals(DateUtils.getDate(System.currentTimeMillis(),
				"yyyy-MM-dd"))) {
			return "今天";
		}
		String[] strArr = time.split("-");
		int month = Integer.parseInt(strArr[1]);
		int day = Integer.parseInt(strArr[2]);
		return month + "月" + day + "日";
	}

}
