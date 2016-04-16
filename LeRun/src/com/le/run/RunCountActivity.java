package com.le.run;

import com.le.run.fragment.RunStartFragment;
import com.le.run.fragment.RunPauseFragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RunCountActivity extends LocationActivity {
	boolean flag = false;
	private TextView textView;//����ʱ
	private int i = 3;
	Fragment running = new RunStartFragment();
	private Intent intent;
	private LocationManager locationManager;
	boolean flagGPS = false;
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	   FragmentManager fm = getFragmentManager();
		   FragmentTransaction ft = fm.beginTransaction();
		   ft.replace(R.id.display_control, running);
		   ft.commit();			
		   intent = new Intent(RunCountActivity.this,RunTraceActivity.class);
		   
		   AlertDialog.Builder dialogGps = new AlertDialog.Builder(this); 
		   locationManager = (LocationManager)getSystemService(Service.LOCATION_SERVICE);
	    	if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	    		dialogGps.setTitle("GPS提示");
	    		dialogGps.setMessage("ֻ打开GPS才能精准定位哦！");
	    		dialogGps.setCancelable(false);
	    		dialogGps.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub						
					}					
				});
                dialogGps.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
                dialogGps.show();
			//	Toast.makeText(getApplicationContext(), "���GPS����!", Toast.LENGTH_SHORT).show();           
				return;        
			}
	    	else flagGPS = true;
    }        
    
    MyCount mc = null;
       @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();  	
           final ImageView runStart = (ImageView)running.getView().findViewById(R.id.run_start);   
           if(flagGPS == true){
        	   runStart.setOnClickListener(new OnClickListener() { 
     			  int flag = 0;
     			@Override
     			public void onClick(View v) {
     				// TODO Auto-generated method stub
     				  switch(v.getId()){
     				  case R.id.run_start:
     					  if(flag == 0){
     						  Button button = (Button) running.getView().findViewById(R.id.setMapType);
     						    button.setVisibility(View.INVISIBLE);
     						    textView = (TextView) running.getView().findViewById(R.id.setCount);
     						    runStart.setImageResource(R.drawable.run_immediatelystart);				  
     						    mc = new MyCount(4000, 1000);
     						    mc.start();
     						   
     						    flag++;
     						    break;
     					  }
     				  }
     					   	  
     			}			
     		});
           }		  
		ImageView runCancel = (ImageView) running.getView().findViewById(R.id.run_cancle);
		runCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mc!=null)
				mc.cancel();
				finish();
			}
		});
		   
    }
       class MyCount extends CountDownTimer {               
		
		public MyCount(long millisInFuture, long countDownInterval) {     
               super(millisInFuture, countDownInterval);     
           }     
           @Override     
           public void onFinish() {                   
        	   startActivity(intent);	
        	   finish();
           }     
           @Override     
           public void onTick(long millisUntilFinished) {
        	  if(i>0){
        		  textView.setText(""+i--);
           	   textView.setTextColor(Color.RED);
   			   textView.setTextSize((float) 120.0);;
   			 
        	  }                   
           }    
       }
       @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub	   
    	super.onDestroy();
    }
}
