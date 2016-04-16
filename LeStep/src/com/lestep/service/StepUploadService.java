package com.lestep.service;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.app.IntentService;
import android.content.Intent;

import com.google.gson.Gson;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;
import com.lepower.utils.LogUtils;
import com.lestep.App;
import com.lestep.dao.IStepDao;
import com.lestep.dao.impl.StepDaoImpl;
import com.lestep.model.Step;
import com.lestep.utils.StepUrls;

/**
 * 每小时同步一次今日数据
 */
public class StepUploadService extends IntentService {
	private Gson gson;
	private IStepDao stepDao;
	private IUserDao userDao;
	private Step todayStep;
	private User user;

	public StepUploadService() {
		this("StepSynchService");
		// TODO Auto-generated constructor stub
	}

	public StepUploadService(String name) {
		super(name);
		initData();
		gson = new Gson();
		// TODO Auto-generated constructor stub
	}

	private void initData() {
		userDao = new UserDaoImpl();
		user = userDao.getUserNow();
		stepDao = new StepDaoImpl(user.getUserId());
		todayStep = stepDao.findToday();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		RequestParams params = new RequestParams(StepUrls.STEP_UP);
		params.setCharset("utf-8");
		params.addParameter("userId", user.getUserId());
		params.addParameter("stepId", todayStep.getStepId());
		params.addParameter("date", todayStep.getDay());
		params.addParameter("startTime", todayStep.getStartTime());
		params.addParameter("endTime", todayStep.getEndTime());
		params.addParameter("distance", todayStep.getDistance());
		params.addParameter("steps", todayStep.getSteps());
		params.addParameter("second", todayStep.getStepTime());
		params.addParameter("speed", todayStep.getSpeed());
		params.addParameter("calorie", todayStep.getCaloria());
		params.addParameter("isToday", todayStep.isToday());
		x.http().post(params, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				todayStep.setSynch(true);
				stepDao.saveOrUpdate(todayStep);
				LogUtils.e(response);
			}
		});
	}

}
