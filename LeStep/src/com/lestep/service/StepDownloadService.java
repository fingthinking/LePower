package com.lestep.service;

import java.lang.reflect.Type;

import org.xutils.x;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageArray;
import com.lepower.model.User;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LogUtils;
import com.lepower.utils.ToastUtils;
import com.lestep.App;
import com.lestep.dao.IStepDao;
import com.lestep.dao.impl.StepDaoImpl;
import com.lestep.model.Step;
import com.lestep.utils.DateUtils;
import com.lestep.utils.StepUrls;

/**
 * 下载所有的
 * @author fing
 *
 */
public class StepDownloadService{
	
	public static void download(final HttpCallback<Step> callback) {
		IUserDao userDao = new UserDaoImpl();
		final User user = userDao.getUserNow();
		if (!App.isTempUser) {
				RequestParams params = new RequestParams(StepUrls.STEP_DOWN);
				params.setCharset("utf-8");
				params.addParameter("userId", user.getUserId());
				params.addParameter("date", DateUtils.getDate(System.currentTimeMillis(), "yyyy-MM-dd"));
				x.http().post(params, new HttpCallback<String>(){
					@Override
					public void onSuccess(String response) {
						// TODO Auto-generated method stub
						LogUtils.e(response);
						Gson gson = new Gson();
						Type type = new TypeToken<MessageArray<Step>>() {
						}.getType();
					
						MessageArray<Step> userMsg = gson.fromJson(response, type);
						if (userMsg.getResCode().equals("0")) {
							Step step = userMsg.getData().get(0);
							callback.onSuccess(step);
							
						} else {
							
						}
					}
					@Override
					public void onError(Throwable e, boolean arg1) {
						if(!HttpUtils.isNetWork(App.context)){
							ToastUtils.showShort("同步数据失败，请检查网络状况");
						}else{
							LogUtils.e(e.getMessage());
						}
						
					}
				});
		}
	}

}
