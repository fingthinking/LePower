package com.lepower.activity;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class JumpService extends Service
{

	public int getJumpCount()
	{
		return jumpCount;
	}

	public void setJumpCount(int jumpCount)
	{
		this.jumpCount = jumpCount;
	}

	private int pre = -4;
	private float max, localgravity;
	private int jumpCount, jumpCounti, jumpCountj, jumpCountz;
	private float gravity[] = new float[3];
	private SensorEventListener listener;

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

		new Thread(new Runnable()
		{
			public void run()
			{
				startJump();
			}
		}).start();

	}

	public void startJump()
	{
		// ��ȡ�������Ĺ���������
		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// ͨ����������ü��ٶȴ���������
		Sensor gravitySensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(listener = new SensorEventListener()
		{

			// �����������ֱ仯ʱ����
			@Override
			public void onSensorChanged(SensorEvent event)
			{
				gravity[0] = event.values[0];
				gravity[1] = event.values[1];
				gravity[2] = event.values[2];
				// ��õ�ʱ���������ֵ
				if (localgravity == 0)
				{
					localgravity = max;
				}
				// �����������ٶ�
				max = (float) Math.sqrt(gravity[0] * gravity[0] + gravity[1]
						* gravity[1] + gravity[2] * gravity[2]);
				// ���˵����������ϵ��Ӳ�
				if (max > 10.39 && Math.abs(gravity[0]) >= 10)
				{
					pre++;
					if (pre > 0)
					{
						jumpCounti++;
					}
				}
				else if (max > 10.39 && Math.abs(gravity[1]) >= 10)
				{
					pre++;
					if (pre > 0)
					{
						jumpCountj++;
					}
				}
				else if (max > 10.39 && Math.abs(gravity[2]) >= 10)
				{
					pre++;
					if (pre > 0)
					{
						jumpCountz++;
					}
				}

				if (jumpCounti > jumpCountj)
				{
					jumpCount = jumpCounti;
				}
				else
				{
					jumpCount = jumpCountj;
				}

				if (jumpCount < jumpCountz)
				{
					jumpCount = jumpCountz;
				}
				// ͨ���㲥����MainActivity����������ֵ
				Intent intent = new Intent();
				intent.putExtra("jumpcount", jumpCount);
				intent.setAction("com.xufan.JumpCountService");
				sendBroadcast(intent);

			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy)
			{

			}
		}, gravitySensor, 156000);

	}

	@Override
	public void onDestroy()
	{
		// ��ȡ�������Ĺ���������
		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// ͨ����������ü��ٶȴ���������
		sensorManager.unregisterListener(listener);
		super.onDestroy();
	}

}
