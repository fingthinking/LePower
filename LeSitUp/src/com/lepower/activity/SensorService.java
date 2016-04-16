package com.lepower.activity;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

public class SensorService extends Service {

	private SensorManager sensorManager;
//	private AccelerometerBinder mBinder = new AccelerometerBinder();
	public static float a,aTemp=0;// 临时存储当前获得的整体加速度
	private int length=24;
	private float[] accelerationValuesArray=new float[length];//存储一组24个加速度值
	private int accelerationValuesArrayIndex=0;//记录加速度数组的当前位置
	private  int countSteps=0;
	
	private long intervalTime = 66;// 1000毫秒，频率要达到15Hz左右
	private int sIndex=0;
	
	private Timer timer = new Timer();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		//一种基于手机传感器自相关分析的计步器实现方法。。。
		
		super.onCreate();
		// 服务被创建的时候进行初始化// 拿到传感器管理器
		sensorManager = (SensorManager) (getSystemService(Context.SENSOR_SERVICE));
		// 拿到加速度传感器
		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// 注册加速度传感器
		sensorManager.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		Log.d("hehedaonStartCommand", "onStartCommand方法");
		// 开启一个计时器
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				accelerationValuesArray[accelerationValuesArrayIndex++]=a;//取当前时刻的这个整体加速度,index再自增
				//Log.d("jiasudu", "取得一次的加速度："+a);//
				if(accelerationValuesArrayIndex==length){//当下标为length=24的时候,需要进行一次判断了。// 下面开始7次循环运算
//					int[] bLength=new int[7];//记录7次中每一次数组的长度，下面的写法就没必要记录了
					float[] bStandardDeviation=new float[8];//记录7个bArray的标准差
					float[] abCorrelationCoefficient=new float[8];//记录a和b的相关系数
					for(int i=1;i<=7;i++){
						float[] aArray=new float[i+5];
						float[] bArray=new float[i+5];//定义两个长度为i+5的数组
//						int bLength=i+5;
						for(int j=0;j<=i+4;j++){//赋值
							aArray[j]=accelerationValuesArray[j];
							bArray[j]=accelerationValuesArray[j+7];
						}
						//计算b的标准差
						bStandardDeviation[i]=getStandardDeviation(bArray);
						//Log.d("dayin", "第"+i+"个标准差是"+bStandardDeviation[i]);
						//计算a、b的相关系数
						abCorrelationCoefficient[i]=getCorrelationCoefficient(aArray,bArray);
						//Log.d("dayin", "第"+i+"个相关系数是"+abCorrelationCoefficient[i]);
					}
					//取得最大的相关系数和对应的数组长以及b的标准差
					int index=getMaxCorCoeIndex(abCorrelationCoefficient);
					float abCorrelationCoefficientMax=abCorrelationCoefficient[index];
					//Log.d("bbiaozhuncha", "ab的最大相关系数:"+abCorrelationCoefficientMax);
					int bLength=index+5;//对应的数组长度
					float bStandardDeviationMax=bStandardDeviation[index];
					//Log.d("abxiangguanxishu", "b对应的标准差:"+bStandardDeviationMax);
					//Log.d("duiyingindex", "对应的下标:"+index);
					//根据bLength的值改变记录数组accelerationValuesArray的值，执行移动
					moveAccelerationValuesArray(bLength);//这里不需要传入accelerationValuesArray
					//如果满足条件，说明当前是在走路，步数加1
					if(bStandardDeviationMax>0.7f && abCorrelationCoefficientMax> 0.80f){
						//Log.d("jibu", "计了一步");
						countStep();//计一步
					}
//					Log.d("suanfabufen", "suanfazaiyunxing");
				}
			}
		};
		timer.schedule(timerTask, 0, intervalTime);
		return super.onStartCommand(intent, flags, startId);// 这里已经return
	}
	/**
	 * 根据传入的bLength对数组进行移动，这里不需要传入accelerationValuesArray，而是直接对其进行修改
	 * @param bLength 
	 */
	protected void moveAccelerationValuesArray(int bLength) {
		// TODO Auto-generated method stub
		for(int i=0;i<length-bLength;i++){
			accelerationValuesArray[i]=accelerationValuesArray[i+bLength];

		}
		accelerationValuesArrayIndex=length-bLength;//这句话就相当于删除了后面的元素
	}

	/**
	 * 加一步
	 */
	protected void countStep() {
		// TODO Auto-generated method stub
		countSteps++;
		Log.d("countSteps", countSteps+"");
//		MainActivity.handleMessage
		Intent intent =new Intent();
		intent.putExtra("countSteps", countSteps);
		intent.setAction("com.example.situptest.StepCountBro");
		sendBroadcast(intent);
		
	}

	/**
	 * 返回最大的相关系数对应的下标
	 * @param floatArray
	 * @return
	 */
	protected int getMaxCorCoeIndex(float[] floatArray) {
		// TODO Auto-generated method stub
		int index=1;//这里是因为上面的数组我们多定义了一个位置，从1开始才存放了值
		for(int i=2;i<floatArray.length;i++){
			//Log.d("zhaochuzuidazhi", "找最大值得时候，第"+i+"个值为"+floatArray[i]);
			if(floatArray[index]<floatArray[i]){
				
				index=i;
			}
		}
		return index;
	}

	/**
	 * 计算两个数组的相关系数
	 * @param aArray
	 * @param bArray
	 * @return
	 */
	protected float getCorrelationCoefficient(float[] aArray, float[] bArray) {
		//这里我们用简单相关系数，计算公式(sqrt(Σ(xi-ux)(yi-uy)))/(n*σx*σb)
		// TODO Auto-generated method stub
		float aMean=getMean(aArray);
		float bMean=getMean(bArray);
		
		float aStandardDeviation=getStandardDeviation(aArray);
		float bStandardDeviation=getStandardDeviation(bArray);
		float sum=0;
		for(int i=0;i<aArray.length;i++){
			sum+=(aArray[i]-aMean)*(bArray[i]-bMean);
			
		}
		return (float) Math.abs((sum/(aArray.length*aStandardDeviation*bStandardDeviation)));
	
	}
	/**
	 * 计算数组的平均值
	 * @param array
	 * @return
	 */
	private float getMean(float[] array) {
		// TODO Auto-generated method stub 计算公式(Σ(xi)/n
		float sum=0;
		for(int i=0;i<array.length;i++){
			sum+=array[i];
		}
		return sum/array.length;
	}
	/**
	 * 计算一个数组的标准差
	 * @param array
	 * @return
	 */
	protected float getStandardDeviation(float[] array) {//这个公式不知道对不对
		// TODO Auto-generated method stub σx=sqrt((Σ(xi-ux)^2)/n) 
		float bMean=getMean(array);
		float sum=0;
		for(int i=0;i<array.length;i++){
			sum+=(array[i]-bMean)*(array[i]-bMean);//sum+=(bi-u)^2,累加
		}
		return (float) Math.sqrt(sum/array.length);//sqrt(sum/n)
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopSelf();
		timer.cancel();
		if (sensorManager != null) {
			sensorManager.unregisterListener(listener);
		}
	}

	private SensorEventListener listener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			float xValue = event.values[0];// 分别获得三个轴的数据
			float yValue = event.values[1];
			float zValue = event.values[2];
//			float g = 9.8f;
//			float zValue = zValueg ;// 修正垂直加速度，重力加速度会影响这个值。
			
			a= (float) Math.sqrt(yValue * yValue + zValue * zValue + xValue * xValue);// 计算得到整体加速度
//			if(sIndex==2){//达到2说明采集了三次
//				a=aTemp/3;//更新一次数据，用平均值
//				Log.d("a", "取一次数据的时间"+(new Date()).getTime());
//			}
//			sIndex=(sIndex++)%3;//先自增再取余
//			
////			Log.d("heheda", a + "");
//			
//			
			//Log.d("a", "监听一次的时间"+(new Date()).getTime());
		}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub

		}
	};
}
