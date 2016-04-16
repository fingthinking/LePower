package com.lepower.activity;

import java.util.Timer;


import java.util.TimerTask;

import javax.security.auth.PrivateCredentialPermission;
import javax.security.auth.callback.Callback;

import com.lesitup.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InstructionsActivity extends Activity{
	
	 Handler handler;
	 boolean imgFlag = true;
	 ImageView img;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
       
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions);
        
        img = (ImageView) findViewById(R.id.instruction);
        
        
        handler = new Handler(new Handler.Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(imgFlag) {
					img.setImageResource(R.drawable.instructions);
					imgFlag = false;
				} else 
				{
					img.setImageResource(R.drawable.instructions2);
					imgFlag = true;
				}
				return true;
			}
		});
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				handler.sendEmptyMessage(1);
			}
		}, 0, 800);
	}

}
