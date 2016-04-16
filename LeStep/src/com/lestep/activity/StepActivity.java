package com.lestep.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lepower.activity.LoginActivity;
import com.lepower.activity.UpdateUserInfoActivity;
import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.lestep.App;
import com.lestep.R;
import com.lestep.dao.IStepDao;
import com.lestep.dao.impl.StepDaoImpl;
import com.lestep.model.Step;
import com.lestep.service.StepDownloadService;
import com.lestep.service.SynchService;
import com.lestep.utils.DateUtils;
import com.lestep.utils.NumFormat;
import com.lestep.widget.ClockView;

@ContentView(R.layout.activity_step)
public class StepActivity extends BaseActivity {
	float complete = 0.0f;
	@ViewInject(R.id.clockView1)
	ClockView clockView;
	@ViewInject(R.id.lv_record)
	ListView lvRecord;
	@ViewInject(R.id.txt_step_today)
	TextView stepToday;
	@ViewInject(R.id.txt_this_step)
	TextView thisStep;
	@ViewInject(R.id.txt_this_meter)
	TextView thisMeter;
	@ViewInject(R.id.txt_this_caloria)
	TextView thisCaloria;
	@ViewInject(R.id.txt_aim)
	TextView txtAim;
	@ViewInject(R.id.btn_statistic)
	ImageButton btnStatstic;

	private IUserDao userDao;
	private User user;
	List<Map<String, String>> dataList;
	SimpleAdapter lvAdapter;
//	List<Step> recordList;

	private IStepDao stepDao;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// TODO Auto-generated method stub
		App.mActivity = this;
		userDao = new UserDaoImpl();
		user = userDao.getUserNow();
		LogUtils.e("user Now" + user.getUserId());

		initData();
		dataList = new ArrayList<Map<String, String>>();
		lvAdapter = new SimpleAdapter(this, dataList, R.layout.list_view_items,
				new String[] { "txt_time", "txt_record" }, new int[] {
						R.id.txt_time, R.id.txt_record });

		// dataList.add(toRecord(System.currentTimeMillis(),
		// System.currentTimeMillis(), 100));
		lvRecord.setAdapter(lvAdapter);

		dataList.clear();
		for (Step record : App.recordList) {
			dataList.add(toRecord(record));
		}

		lvAdapter.notifyDataSetChanged();

		lvRecord.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {

			}
		});
		txtAim.setText("" + user.getAim());
		long todayAllStep = App.todayStep.getSteps();
		stepToday.setText("" + todayAllStep);
		float complete = (float) todayAllStep / user.getAim();
		clockView.setComplete(complete);
		sendBroadcast(new Intent(
				"com.lestep.broadcastreceiver.StepBroadcastReceiver"));
		registerReceiver(receiver, new IntentFilter(
				"com.lestep.broadcast.stepbroadcast"));

	}

	private void initData() {
		stepDao = new StepDaoImpl(user.getUserId());
		// 初始化今日数据
		SynchService.synchStep(user.getUserId());
		App.todayStep = stepDao.findToday();

		StepDownloadService.download(new HttpCallback<Step>() {
			@Override
			public void onSuccess(Step step) {
				// TODO Auto-generated method stub
				if (step.getSteps() > App.todayStep.getSteps()) {
					stepDao.deleteToday();
					stepDao.saveOrUpdate(step);
					App.todayStep.setCaloria(step.getCaloria());
					App.todayStep.setDistance(step.getDistance());
					App.todayStep.setStepId(step.getStepId());
					App.todayStep.setSteps(step.getSteps());
					App.todayStep.setSpeed(step.getSpeed());
					App.todayStep.setStepTime(step.getStepTime());
				} else {
					stepDao.deleteToday();
					stepDao.saveOrUpdate(App.todayStep);
				}
				clockView.setComplete((float) App.todayStep.getSteps()
						/ user.getAim());
				stepToday.setText("" + App.todayStep.getSteps());
			}
		});

		// 初始化今日数据
		
		App.recordList = stepDao.findAllToday();
	}

	@Event(value = { R.id.btn_statistic, R.id.imgbtn_setting })
	private void click(View view) {
		switch (view.getId()) {
		case R.id.btn_statistic: {
			Intent intent = new Intent(this, HistoryActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
		}
			break;
		case R.id.imgbtn_setting: {

			Intent intent = new Intent(this, UpdateUserInfoActivity.class);
			startActivity(intent);
			break;
		}
		default:
			break;
		}
	}

	/**
	 * 通过开始时间，结束时间，运动的步数来计算显示的数据
	 * 
	 * @param startTime
	 * @param endTime
	 * @param runNum
	 * @return
	 */
	public Map<String, String> toRecord(Step step) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("txt_time", step.getStartTime() + "~" + step.getEndTime()
				+ " 共运动了" + DateUtils.getTimeFromMili(step.getStepTime()));
		map.put("txt_record",
				"本次走了" + step.getSteps() + "步，约" + step.getDistance() + "米，消耗了"
						+ NumFormat.roundOff(step.getCaloria(), 2) + "千卡，平均"
						+ step.getSpeed() + "步/分");
		return map;
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			boolean end = intent.getBooleanExtra("end", false);
			Step record = (Step) intent.getSerializableExtra("step");
			// LogUtils.e("+" + record.getSteps() + "步");
			thisStep.setText("+" + record.getSteps() + "步");
			thisMeter.setText(record.getDistance() + "米");
			thisCaloria.setText(NumFormat.roundOff(record.getCaloria(), 2)
					+ "千卡");
			boolean combineRecord = intent.getBooleanExtra("combine", false);
			// 如果本次的与上一次的不是同一次记录，则插入一条，否则在原基础上继续增加
			if (end) {
				// LogUtils.e("isIdEq",
				// record.getStepId()+"----"+lastStep.getStepId());
				// LogUtils.e("recordList----2",recordList.size());
				if (!combineRecord) {
					dataList.add(toRecord(record));
				} else {
					if (dataList.size() > 0) {
						dataList.remove(dataList.size() - 1);
					}
					dataList.add(toRecord(record));
				}
				lvAdapter.notifyDataSetChanged();
			}
			long todayAllStep = App.todayStep.getSteps();
			// LogUtils.e("todayStep:::::::::"+App.todayStep.getSteps());
			stepToday.setText("" + todayAllStep);
			float complete = (float) todayAllStep / user.getAim();
			clockView.setComplete(complete);
		}

	};

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	};

}
