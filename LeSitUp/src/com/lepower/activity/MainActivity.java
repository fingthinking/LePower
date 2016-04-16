package com.lepower.activity;


import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.lesitup.R;
import com.lepower.dao.RecordInfoDAO;
import com.lepower.model.RecordInfo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener{
	
	//private String startTime;	//起始时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        /*for(int i=1;i<30;i++){
		String userId="73cb8a3c2a464263a7956807dll88979";
		String startTime;
		String stopTime;
		if(i<10){
			startTime="2016-03-0"+i+" 12:12:12";
			stopTime="2016-03-0"+i+" 13:12:12";
			
		}else{
		startTime="2016-03-"+i+" 12:12:12";
		stopTime="2016-03-"+i+" 13:12:12";

		}
		
		int number=(int) (100*Math.random()+10);
		int calorie=number*7;
		String date=startTime.substring(0, startTime.lastIndexOf(" "));
	
	
	RecordInfo recordInfo=new RecordInfo(userId, startTime, stopTime, number, calorie, date);
		
		RecordInfoDAO infoDAO = RecordInfoDAO.getInstance(this);
		
		infoDAO.addRecordInfo(recordInfo);
		//postDataToServer();
	}*/
		
        
        findViewById(R.id.main_train).setOnClickListener(this);
			
        findViewById(R.id.main_recordday).setOnClickListener(this); 
        
        findViewById(R.id.main_recordmonth).setOnClickListener(this); 
        
        findViewById(R.id.main_exit).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_train:
			
			Intent intent=new Intent(MainActivity.this,TrainActivity.class);
			startActivity(intent);
			break;
		case R.id.main_recordday:
			Intent intent2=new Intent(MainActivity.this,RecordListActivity.class);
			startActivity(intent2);
			break;
		case R.id.main_recordmonth:
			Intent intent3=new Intent(MainActivity.this,RecordGraphicActivity.class);
			startActivity(intent3);
			break;
		case R.id.main_exit:
			finish();
			break;
		default:
			break;
		}
	}

    /**
     * A placeholder fragment containing a simple view.
     */
    
}
