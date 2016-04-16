package com.lestep.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import com.lepower.callback.HttpCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.User;
import com.lepower.utils.LogUtils;
import com.lestep.App;
import com.lestep.dao.IStepDao;
import com.lestep.dao.impl.StepDaoImpl;
import com.lestep.model.Step;
import com.lestep.utils.DateUtils;
import com.lestep.utils.NumFormat;

public class SensorService extends Service implements SensorEventListener {
	float[] oriValues = new float[3];
	final int valueNum = 4;
	// 用于存放计算阈值的波峰波谷差值
	float[] tempValue = new float[valueNum];
	int tempCount = 0;
	// 用于计算步子时间的差值
	float[] tempTimeStep = new float[valueNum];
	int tempTime = 0;
	int tempTimeNow = 0;

	// float currentTimeStep = 250;

	// 是否上升的标志位
	boolean isDirectionUp = false;
	// 持续上升次数
	int continueUpCount = 0;
	// 上一点的持续上升的次数，为了记录波峰的上升次数
	int continueUpFormerCount = 0;
	// 上一点的状态，上升还是下降
	boolean lastStatus = false;
	// 波峰值
	float peakOfWave = 0;
	// 波谷值
	float valleyOfWave = 0;
	// 此次波峰的时间
	long timeOfThisPeak = 0;
	// 上次波峰的时间
	long timeOfLastPeak = System.currentTimeMillis();
	// 当前的时间
	long timeOfNow = 0;
	// 当前传感器的值
	float gravityNew = 0;
	// 上次传感器的值
	float gravityOld = 0;
	// 动态阈值需要动态的数据，这个值用于这些动态数据的阈值
	final float initialValue = (float) 1.3;
	// 初始阈值
	float ThreadValue = (float) 2.0;
	// 波峰数值
	final float packValue = 20f;
	// 连续计步的次数才算走了
	public static final int continueStep = 10;
	// 本次连续运行了多少步 要返回，用于显示光晕
	private int stepCount = 0;

	private long newTime;
	private long oldTime;
	// 设置步行对象
	private Step step = new Step();
	//
	private static long beginTime = 0;
	private Step lastStep;

	List<Step> recordList;

	private IStepDao stepDao;
	private IUserDao userDao;
	private User user;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// 与OnStartCommand的区别是，本函数只有在创建到时候调用
		// TODO Auto-generated method stub
		super.onCreate();
		userDao = new UserDaoImpl();
		user = userDao.getUserNow();

		initData();
		// 加载传感器
		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		Sensor sensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_GAME);
		step = new Step();
		step.setStepId(System.currentTimeMillis());
		LogUtils.e("I am Here");
	}

	private void initData() {
		stepDao = new StepDaoImpl(user.getUserId());
		// 初始化今日数据
		if (App.todayStep == null) {
			App.todayStep = stepDao.findToday();
			StepDownloadService.download(new HttpCallback<Step>() {
				@Override
				public void onSuccess(Step step) {
					// TODO Auto-generated method stub
					if(step.getSteps() > App.todayStep.getSteps()){
						stepDao.deleteToday();
						stepDao.saveOrUpdate(step);
						App.todayStep.setCaloria(step.getCaloria());
						App.todayStep.setDistance(step.getDistance());
						App.todayStep.setStepId(step.getStepId());
						App.todayStep.setSteps(step.getSteps());
						App.todayStep.setSpeed(step.getSpeed());
						App.todayStep.setStepTime(step.getStepTime());
					}else{
						stepDao.deleteToday();
						stepDao.saveOrUpdate(App.todayStep);
					}
				}
			});
		}
		// 初始化今日数据
		recordList = App.recordList;
		if (recordList == null) {
			recordList = stepDao.findAllToday();
			App.recordList = recordList;
		}
		// 初始化上一次的步子
		if (recordList.size() == 0) {
			lastStep = new Step();
		} else {
			lastStep = recordList.get(recordList.size() - 1);
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			oriValues[i] = event.values[i];
		}
		// 新的重力加速度
		gravityNew = (float) Math.sqrt(oriValues[0] * oriValues[0]
				+ oriValues[1] * oriValues[1] + oriValues[2] * oriValues[2]);
		detectorNewStep(gravityNew);
	}

	/*
	 * 检测步子，并开始计步 1.传入sersor中的数据 2.如果检测到了波峰，并且符合时间差以及阈值的条件，则判定为1步
	 * 3.符合时间差条件，波峰波谷差值大于initialValue，则将该差值纳入阈值的计算中
	 */
	public void detectorNewStep(float values) {
		if (gravityOld == 0) {
			gravityOld = values;
		} else {
			if (detectorPeak(values, gravityOld)) {
				timeOfLastPeak = timeOfThisPeak;
				timeOfNow = System.currentTimeMillis();
				if (timeOfNow - timeOfLastPeak >= correctTime(timeOfNow
						- timeOfLastPeak)
						&& (peakOfWave - valleyOfWave >= ThreadValue)) {
					timeOfThisPeak = timeOfNow;
					/*
					 * 更新界面的处理，不涉及到算法 一般在通知更新界面之前，增加下面处理，为了处理无效运动： 1.连续记录10才开始计步
					 * 2.例如记录的9步用户停住超过3秒，则前面的记录失效，下次从头开始
					 * 3.连续记录了9步用户还在运动，之前的数据才有效
					 */
					canStep();
				}
				if (timeOfNow - timeOfLastPeak >= correctTime(timeOfNow
						- timeOfLastPeak)
						&& (peakOfWave - valleyOfWave >= initialValue)) {
					timeOfThisPeak = timeOfNow;
					ThreadValue = peakValleyThread(peakOfWave - valleyOfWave);
				}
			}
		}
		gravityOld = values;
	}

	// 能否增加步子
	private void canStep() {
		newTime = System.currentTimeMillis();
		if (stepCount == 0) {
			beginTime = newTime;
			oldTime = beginTime;
		}
		if (stepCount < continueStep) {
			// 连续运动少于continueStep时，不开始计步
			stepCount++;
		} else if (stepCount == continueStep) {
			// 开始计步
			// 连续步行到continueStep，一次增加continueStep步
			// 格式化为HH:mm
			addSteps(continueStep);
			stepCount++;
		} else {
			// 否则增加1步
			addSteps(1);
		}
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				LogUtils.w("newTime:" + newTime + "-beginTime:" + beginTime
						+ "-" + "cha:" + (newTime - beginTime));

				// TODO Auto-generated method stub
				// 如果两步的时间间隔超过3秒钟，不计算步子
				if (stepCount > continueStep
						&& System.currentTimeMillis() - newTime >= 3000) {

					endSteps();
				}

			}
		}, 4000);
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 如果两次运动的时间间隔超过1分钟，则算不同的运动
				if (System.currentTimeMillis() - newTime > 60000) {
					stepCount = 0;
					step = new Step();
					step.setStepId(System.currentTimeMillis());
				}
			}
		}, 65000);
	}

	/**
	 * 开始计步
	 * 
	 * @param startTime
	 *            计步开始的时间
	 * @param nowTime
	 *            现在的时间
	 * @param day
	 */
	private void addSteps(int stepNum) {
		// 设置到step中
		step.setStartTime(DateUtils.getDate(beginTime, "HH:mm"));
		step.setDay(DateUtils.getDate(beginTime, "yyyy-MM-dd"));
		int addStepNum = stepNum;
		float addDistance = (float) (stepNum * user.getHeight() * 0.36 / 100);
		double addCaloria = addDistance / 1000 * user.getWeight() * 1.036;

		long addTime = stepNum == continueStep ? newTime - beginTime : newTime
				- oldTime;
		LogUtils.e("addTime", addTime);
		oldTime = newTime;
		// 添加步子
		step.setSteps(step.getSteps() + addStepNum);
		App.todayStep.setSteps(App.todayStep.getSteps() + addStepNum);
		
		
		// 设置距离:单位为米，人的步长为： 身高*0.36
		step.setDistance((float) NumFormat.roundOff(step.getDistance()
				+ addDistance, 2));
		App.todayStep.setDistance((float) NumFormat.roundOff(App.todayStep.getDistance()
				+ addDistance, 2));
//		LogUtils.e("距离："+App.todayStep.getDistance());
		// 设置卡路里 公式： 体重*距离（km）*1.036
		step.setCaloria((float) (step.getCaloria() + addCaloria));
		LogUtils.e(App.todayStep.getCaloria()+"-Caloria");
		App.todayStep.setCaloria((float) (App.todayStep.getCaloria() + addCaloria));
		// 设置运动时长 （ms）
		step.setStepTime(step.getStepTime() + addTime);
		App.todayStep.setStepTime(App.todayStep.getStepTime() + addTime);

		// 设置步频
		step.setSpeed((long) (step.getSteps() / (step.getStepTime() / 60000f)));

		// 发送数据到Activity中
		Intent intent = new Intent("com.lestep.broadcast.stepbroadcast");
		intent.putExtra("step", step);
		sendBroadcast(intent);
	}

	/**
	 * 结束并保存到数据库
	 * 
	 * @param endTime
	 */
	private void endSteps() {
		step.setEndTime(DateUtils.getDate(newTime, "HH:mm"));

		Intent intent = new Intent("com.lestep.broadcast.stepbroadcast");
		if (lastStep.getStartTime() == step.getStartTime()) {
			intent.putExtra("combine", true);

		} else {
			recordList.add(step);
			lastStep = step;
		}
		// 发送数据到Activity中
		Observable.just(step).subscribeOn(Schedulers.io())
				.subscribe(new Action1<Step>() {

					@Override
					public void call(Step step) {
						// TODO Auto-generated method stub

						App.todayStep.setSpeed(getAveSteed());
						stepDao.saveOrUpdate(step);
						LogUtils.e("todayStep::----"+App.todayStep.getCaloria());
						stepDao.saveOrUpdate(App.todayStep);
					}
				});
		intent.putExtra("end", true);
		intent.putExtra("step", step);
		sendBroadcast(intent);

	}

	private long getAveSteed() {
		long sum = 0;
		for (Step step : recordList) {
			sum += step.getSpeed();
		}
		// LogUtils.e("recordList----1",recordList.size());
		return sum / recordList.size();
	}

	/**
	 * 矫正时间差，用于矫正多个步数之间的时间差值 通过计算多个时间差值的平均数 使得当一次采集时间大于前一次时间的2/3的时候，才算正常行走
	 * 
	 * @param times
	 * @return
	 */
	private float correctTime(float times) {

		// 第一次步子时间差设置为250毫秒
		if (tempTime == 0) {
			tempTimeStep[tempTime++] = times;
			tempTimeNow++;
			return (times + 250) / 3;
		} else if (tempTime < valueNum - 1) {
			tempTimeNow++;
			tempTimeStep[tempTime++] = times;
			return averageValue(tempTimeStep, tempTime);
		} else {
			tempTimeStep[tempTimeNow] = times;
			if (tempTimeNow == valueNum - 1) {
				tempTimeNow = 0;
			} else {
				tempTimeNow++;
			}
			return averageValue(tempTimeStep, tempTime);
		}
	}

	/*
	 * 检测波峰 以下四个条件判断为波峰： 1.目前点为下降的趋势：isDirectionUp为false
	 * 2.之前的点为上升的趋势：lastStatus为true 3.到波峰为止，持续上升大于等于2次 4.波峰值大于20 记录波谷值
	 * 1.观察波形图，可以发现在出现步子的地方，波谷的下一个就是波峰，有比较明显的特征以及差值 2.所以要记录每次的波谷值，为了和下次的波峰做对比
	 */
	public boolean detectorPeak(float newValue, float oldValue) {
		lastStatus = isDirectionUp;
		if (newValue >= oldValue) {
			// 继续在上升状态
			isDirectionUp = true;
			continueUpCount++;
		} else {
			// 下降状态，并将记录上一次上升状态的次数
			continueUpFormerCount = continueUpCount;
			continueUpCount = 0;
			isDirectionUp = false;
		}
		// 不处于上升状态 && 上一次是上升状态 && 上一次上升状态的次数要多余2次 && 上升状态的数值大于20
		if (!isDirectionUp && lastStatus
				&& (continueUpFormerCount >= 5 || oldValue >= packValue)) {
			peakOfWave = oldValue;
			return true;
		} else if (!lastStatus && isDirectionUp) {
			valleyOfWave = oldValue;
			return false;
		} else {
			return false;
		}
	}

	/*
	 * 阈值的计算 1.通过波峰波谷的差值计算阈值 2.记录4个值，存入tempValue[]数组中
	 * 3.在将数组传入函数averageValue中计算阈值
	 */
	public float peakValleyThread(float value) {
		float tempThread = ThreadValue;
		if (tempCount < valueNum) {
			tempValue[tempCount] = value;
			tempCount++;
		} else {
			tempThread = averageValue(tempValue, valueNum);
			for (int i = 1; i < valueNum; i++) {
				tempValue[i - 1] = tempValue[i];
			}
			tempValue[valueNum - 1] = value;
		}
		return tempThread;

	}

	/*
	 * 梯度化阈值 1.计算数组的均值 2.通过均值将阈值梯度化在一个范围里
	 */
	public float averageValue(float value[], int n) {
		float ave = 0;
		for (int i = 0; i < n; i++) {
			ave += value[i];
		}
		ave = ave / valueNum;
		if (ave >= 8)
			ave = (float) 4.3;
		else if (ave >= 7 && ave < 8)
			ave = (float) 3.3;
		else if (ave >= 4 && ave < 7)
			ave = (float) 2.3;
		else if (ave >= 3 && ave < 4)
			ave = (float) 2.0;
		else {
			ave = (float) 1.3;
		}
		return ave;
	}
}
