package com.lepower.activity;

import java.io.File;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.lesitup.R;
import com.lepower.dao.RecordInfoDAO;
import com.lepower.model.RecordInfo;
import com.lepower.utils.MyDate;
import com.lepower.utils.ShareUtils;

public class RecordGraphicActivity extends Activity implements OnClickListener{
	private BarChart barChart;
	public TextView tvMonth;
	View view;
	final Calendar calendar = Calendar.getInstance();     
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_record_graphic);
		view= new View(this);
		Button btnShare = (Button) findViewById(R.id.btn_share);
		btnShare.setOnClickListener(this);

		
		tvMonth =  (TextView) findViewById(R.id.tvMonth);
        //获取系统时间
        
        tvMonth.setText(sdf.format(calendar.getTime()));
        showGraphic(tvMonth.getText());
        
        Button preButton = (Button) findViewById(R.id.button_pre);
        preButton.setOnClickListener(this); 
        
        Button nextButton = (Button) findViewById(R.id.button_next);
        nextButton.setOnClickListener(this); 
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_next:
			calendar.add(Calendar.MONTH, +1);	
			String selectDateString=sdf.format(calendar.getTime());//2016-03
		    tvMonth.setText(selectDateString);
		    showGraphic(tvMonth.getText());
			break;
		case R.id.button_pre:
			calendar.add(Calendar.MONTH, -1);		        
			String selectDateString1=sdf.format(calendar.getTime());//2016-03
	        tvMonth.setText(selectDateString1);
	        showGraphic(tvMonth.getText());
	        break;
		case R.id.btn_share:
			ShareUtils.shareImage(this, Uri.parse(getFilePath()), "From LeSitUp's Share");
		default:
			break;
		}
	}

		public void showGraphic(CharSequence charSequence) {
			
			barChart=(BarChart) findViewById(R.id.aChart);
			String queryMonth = (String) tvMonth.getText();
			BarData barData=getBarData(queryMonth);
			ColorTemplate mColorTemplate=new ColorTemplate();
			barChart.setDrawBarShadow(false);
	        barChart.setDrawValueAboveBar(true);
	        barChart.setDescription("");
	        
	        barChart.setMaxVisibleValueCount(60);//显示在柱形图上的数字

	        // scaling can now only be done on x- and y-axis separately
	        //barChart.setPinchZoom(true);
	        // draw shadows for each bar that show the maximum value
	        barChart.setDrawBarShadow(false);//柱形图阴影
	        
	        //设置X轴
	        XAxis xAxis = barChart.getXAxis(); 
	        xAxis.setPosition(XAxisPosition.BOTTOM);  

	        barChart.setDrawGridBackground(false);
	        barChart.setTouchEnabled(true); // 设置是否可以触摸      
	        barChart.setDragEnabled(true);// 是否可以拖拽      
	        barChart.setScaleEnabled(true);// 是否可以缩放  
	        barChart.setDrawBorders(false);  ////是否在折线图上添加边框   
	        barChart.setDrawGridBackground(false); // 是否显示表格颜色  
//	        barChart.setNoDataTextDescription("You need to provide data for the chart.");   
	        
	        barChart.animateY(2500);//动画效果
			barChart.setData(barData);
			
		}

	/**
	 * 从数据库查询到当月的数据，按天数
	 * @param month
	 * @return
	 */
	private BarData getBarData(String month) {
		// TODO Auto-generated method stub
		List<String> xValues=new ArrayList<String>();
		ArrayList<BarEntry> yValues=new ArrayList<BarEntry>();
		String date;
		int maxDayOfMonth=dayOfMonth(month);
		
		for(int i=1;i<=maxDayOfMonth;i++){
			xValues.add(i-1+"");//我们这个是天数,
			if(i < 10)	{//i==6,正确的是这个2016-03    -06,2016-03-6，如果不小于10
			date=month+"-0"+i;
			} else {
			date =month+"-"+i; 
			}
			
			//按日期查询当日的数据总量
			int[] sumOfDayData=RecordInfoDAO.getInstance(getApplicationContext()).getSumOfDate(date);//这里不一定写对了
			yValues.add(new BarEntry(sumOfDayData[0], i));//一个数
			
			
		}
		
		BarDataSet barDataSet = new BarDataSet(yValues,"仰卧起坐数据统计图"); 
		barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
		barDataSet.setDrawValues(true);
		
		ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
		barDataSets.add(barDataSet);
		
		BarData barData = new BarData(xValues,barDataSets);
		return barData;
	}

	/**
	 * 判断一个月有几天
	 * @param month
	 * @return
	 */
	private int dayOfMonth(String month) {
		//根据年月得到天数
		int yearInt=Integer.valueOf((month.substring(0, 4))).intValue();//substring截断String
		int monthInt=Integer.valueOf((month.substring(5, 7))).intValue();
		Log.d("heheda", yearInt+"年");
		Log.d("heheda", monthInt+"月");
		//获取当前时间  
        Calendar cal = Calendar.getInstance();  
        //下面可以设置月份，注：月份设置要减1，所以设置1月就是1-1，设置2月就是2-1，如此类推  
        //cal.set(yearInt, monthInt-1);//可能需要减1
        cal.set(Calendar.YEAR, yearInt);
        cal.set(Calendar.MONTH,monthInt-1);
        //得到一个月最最后一天日期(31/30/29/28)  
        int MaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
        Log.d("heheda", MaxDay+"最大天数");
        //按你的要求设置时间  
		return MaxDay;
	}
	//截屏并保存图片
	private void screenShot(){
		
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		try {
			File file = Environment.getExternalStorageDirectory();
			if(file.exists()) {
				OutputStream out = new FileOutputStream(new File(file,"temp.jpg"));
				bitmap.compress(CompressFormat.JPEG, 100, out);
				out.flush();
				out.close();
			} else {
				Toast.makeText(getApplicationContext(), "你的手机不支持内存卡", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	// 获取分享图片的路径
	private String getFilePath(){
		File file = Environment.getExternalStorageDirectory();
		if(file.exists()){
			return file.getAbsolutePath();  
		}
		return null;
	}
	
	

	
}
	
