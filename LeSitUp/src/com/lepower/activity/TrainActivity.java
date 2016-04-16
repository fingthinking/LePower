package com.lepower.activity;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.App;
import com.lesitup.R;
import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.RecordInfoDAO;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageObj;
import com.lepower.model.RecordInfo;
import com.lepower.model.User;
import com.lepower.activity.SensorService;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LogUtils;
import com.lepower.utils.MyDate;
import com.lepower.utils.ToastUtils;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TrainActivity extends Activity {

	public static final int UPDATE_STEP_COUNT=1;
	public static TextView tvStepCount;
	private StepCountReceiver stepCountReceiver=null;//广播接受者
	
	
	private static  int count = 0;
	private MyDate date;
	private int calorie;
	private int countSteps = 0;
	private RecordInfo info = new RecordInfo(null, null, null, 0, 0, null);//类实例化的时候 一定要初始化 否则会空指针错误
	public static Handler handler=new Handler(){
	public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case UPDATE_STEP_COUNT:
				//在此更新UI
				count=msg.arg1;
				Log.d("TrainActivity", "计数的个数为"+count);
				tvStepCount.setText(""+count);//更新步数
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_train);
		//添加测试数据
		
		
		Date curDate=new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = formatter.format(curDate);
		
		//LogUtils.e(startTime);
		info.setStartTime(startTime);
		
		tvStepCount = (TextView) findViewById(R.id.tvStepCount);
		SharedPreferences preferences = this.getSharedPreferences("share",MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);
        Editor editor = preferences.edit();
        if(isFirstRun) {
        	startActivity(new Intent(this,InstructionsActivity.class));
        	editor.putBoolean("isFirstRun", false);
        	editor.commit();
        	
        }
		Button trainStop = (Button) findViewById(R.id.train_stop);
		//完成按钮
		trainStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Date curDate=new Date(System.currentTimeMillis());
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				info.setEndTime(formatter.format(curDate));
				
				info.setSitupCount(count);
				info.setCalorie(count * 10);
				info.setDate(MyDate.getDate());
					
					
				if (count != 0) {
					//保存到数据库
					setRecordInfo();
					//数据同步到服务器
					
					LogUtils.e("数据上传到数据库");
					if(HttpUtils.isNetWork(getApplicationContext())) {
						postDataToServer();
						LogUtils.e("数据传到服务器");
					}
					
					Intent stopIntent = new Intent(TrainActivity.this,SensorService.class);
					stopService(stopIntent);
					finish();
					
				}else{
					finish();
				}
			}
		});		
		
		//启动监听加速度的服务
		Intent startIntent=new Intent(TrainActivity.this,SensorService.class);
		startService(startIntent);
		//注册广播接受者
		stepCountReceiver=new StepCountReceiver();
		IntentFilter filter=new IntentFilter("com.example.situptest.StepCountBro");
		TrainActivity.this.registerReceiver(stepCountReceiver, filter);
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		unregisterReceiver(stepCountReceiver);
		
	}
	
	public void postDataToServer() {
		String address = LeUrls.ADD_SITUP_INFO;
		Map<String, Object>params = new HashMap<String, Object>();
		User user =new UserDaoImpl().getUserNow();
		if(user != null) { 
		String userId =user.getUserId(); 
		
		params.put("userId", userId);
		params.put("startTime", info.getStartTime());
		params.put("endTime", info.getEndTime());
		params.put("situpCount", info.getsitupCount());
		params.put("calorie", info.getCalorie());
		params.put("date", info.getDate());
		
		
		LogUtils.e(params);
		HttpUtils.post(address, params, 
				new HttpCallback<String>(){
			@Override
			public void onSuccess(String response) {
				LogUtils.e("response", response);
				try {
					JSONObject jsonObject = new JSONObject(
							response);
					String resCode = jsonObject
							.getString("resCode");
					if (resCode.equals("0")) {
						
						ToastUtils.showShort("训练数据已经上传到服务器");
					}else{
						ToastUtils.showShort(jsonObject.getString("resMsg"));
					}
				} catch (Exception e) {
					LogUtils.e(e.getMessage());
				}
				
			}
			@Override
				public void onError(Throwable e, boolean arg1) {
						
				if(!HttpUtils.isNetWork(App.context)){
					ToastUtils.showShort("请检查网络状况");
				}else{
					LogUtils.e(e.getMessage());
				}
			}
		});
		}
		
	}
	//新产生的数据保存到数据库
	public void setRecordInfo() {
		//Log.d("heheda", "asdfasdfasdf");
		
		RecordInfoDAO infoDAO = RecordInfoDAO.getInstance(this);
		infoDAO.addRecordInfo(info);
	}
	
	public class StepCountReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			//
			countSteps=intent.getIntExtra("countSteps",0);
			Message msg=new Message();
			msg.what=UPDATE_STEP_COUNT;
			msg.arg1=countSteps;
			handler.sendMessage(msg);
		}
	}
}