package com.lestep.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.http.RequestParams;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.MessageArray;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LogUtils;
import com.lestep.App;
import com.lestep.dao.IStepDao;
import com.lestep.dao.impl.StepDaoImpl;
import com.lestep.model.Step;
import com.lestep.utils.DateUtils;
import com.lestep.utils.StepUrls;
import com.lestep.utils.ToastUtils;
/**
 * 用户每天进入的时候同步一次未同步数据
 * @author fing
 */
public class SynchService {
	/**
	 * 同步历史数据
	 * @param userId
	 */
	public static void synchStep(final String userId){
		
//		Observable.just(userId)
//				.subscribeOn(Schedulers.io())
//				.flatMap(new Func1<String, Observable<? extends Step>>() {
//
//					@Override
//					public Observable<? extends Step> call(String userId) {
//						// TODO Auto-generated method stub
//						List<Step> stepList = stepDao.findDayNotSynch();
//						LogUtils.e(stepList.size());
//						return Observable.from(stepList);
//					}
//				})
//				.map(new Func1<Step, String>() {
//					@Override
//					public String call(final Step step) {
//						// TODO Auto-generated method stub
//						RequestParams params = new RequestParams(StepUrls.STEP_DOWN);
//						params.setCharset("utf-8");
//						params.addParameter("userId", userId);
//						params.addParameter("date", step.getDay());
//						String response = "";
//						try {
//							 response = x.http().postSync(params, String.class);
//							 step.setSynch(true);
//							 stepDao.saveOrUpdate(step);
//						} catch (Throwable e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						return response;
//					}
//				})
//				.observeOn(AndroidSchedulers.mainThread())
//				.subscribe(new Action1<String>() {
//					@Override
//					public void call(String response) {
//						// TODO Auto-generated method stub
//						ToastUtils.showShort(response);
//						
//					}
//				});
		final IStepDao stepDao = new StepDaoImpl(userId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		HttpUtils.post(StepUrls.STEP_DOWN, params, new HttpCallback<String>(){
			@Override
			public void onSuccess(String response) {
				Gson gson = new Gson();
				Type type = new TypeToken<MessageArray<Step>>() {
				}.getType();
			
				MessageArray<Step> userMsg = gson.fromJson(response, type);
				LogUtils.e(response);
				if (userMsg.getResCode().equals("0")) {
					List<Step> stepList = userMsg.getData();
					for(Step step : stepList){
						LogUtils.e(step.getDay());
						if(step.getDay().equals(DateUtils.getDate(System.currentTimeMillis(), "yyyy-MM-dd"))){
							stepDao.deleteToday();
						}
						stepDao.saveOrUpdate(step);
					}
				} else {
					LogUtils.e(userMsg.getResMsg());
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
