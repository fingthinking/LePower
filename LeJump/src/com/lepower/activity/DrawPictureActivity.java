package com.lepower.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lepower.R;
import com.lepower.adapter.JumpDataAdapter;
import com.lepower.dao.impl.QueryRecord;
import com.lepower.utils.Jumper;

public class DrawPictureActivity extends Activity
{
	private BarChart barChart;
	private BarData data;
    private BarDataSet dataSet;//������ʾ����ƣ�����״ͼ����ɫ�����ݣ���˵����Ϣ
    private int[] jumpdata = new int[7];
    private String[] date = new String[7];
    private List<Jumper> jumpers;
    private Adapter adapter;
    private ListView listView;
    private GestureDetector mDetector;
    private String userId;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.countlist);
		barChart = (BarChart) findViewById(R.id.chart);
		Intent intent = getIntent();
		jumpdata = intent.getIntArrayExtra("jumpdata");
		date = intent.getStringArrayExtra("date");
		userId = intent.getStringExtra("userId");
		 mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener()
			{
				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2,
						float velocityX, float velocityY) {
					
					if (e1.getRawX()-e2.getRawX()>100) {
						Intent intent = new Intent(DrawPictureActivity.this,
								JumpMainActivity.class);
						startActivity(intent);
						return true;
					}
					
					return super.onFling(e1, e2, velocityX, velocityY);
				}
			});
		
		date = QueryRecord.get7DayDate();
		int s = 0;
		for (String dat : date)
		{
			jumpdata[s] = QueryRecord.QueryTotalForDay(this,userId, dat);
			s++;
		}
		
		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();//���BarEntry����BarEntry���������״ͼ�ĸ߶ȺͶ�Ӧ��λ��
		ArrayList<String> xVals = new ArrayList<String>();//�������ǩ
        for(int i=0;i<7;i++){
            float profit= jumpdata[6-i];
            entries.add(new BarEntry(profit,i));
            xVals.add(date[6-i]);
        }
        dataSet = new BarDataSet(entries, "最近一周跳绳个数");//��״ͼ���ݺ�˵����Ϣ
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);//��״ͼ��ɫ
        data = new BarData(xVals, dataSet);//��X�������ݹ���
        barChart.setData(data);//�����ݴ�����״ͼ
        barChart.animateX(1000);//X��������ʾ�ٶ�
        barChart.animateY(1000);//Y��������ʾ�ٶ�
        barChart.setDescription("");
        
        
        //listView��ʾ�˶�����
        jumpers = new ArrayList<Jumper>();
        listView = (ListView) findViewById(R.id.listview_count);
        //��ȡ������˶�����
        Cursor cursor = QueryRecord.QueryRecordByTime(this, 1,userId);
        while (cursor.moveToNext())
		{
			String todaydate = cursor.getString(cursor.getColumnIndex("date"));
			String todayjump = cursor.getString(cursor.getColumnIndex("jumpCount"));
			Jumper jumper = new Jumper(todaydate, todayjump);
			jumpers.add(jumper);
		}
        
        adapter =new JumpDataAdapter(this, jumpers);
        listView.setAdapter(null);
        listView.setAdapter((ListAdapter) adapter);
        TextView tv_total = (TextView) findViewById(R.id.total);
        tv_total.setText("今天运动"+QueryRecord.countForOneDay(this,userId)+"次,共跳绳"+jumpdata[0]+"个"+"\n消耗卡路里"+jumpdata[0]*0.0916+"Kcal");
       
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
