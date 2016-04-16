package com.lestep.broadcastreceiver;

import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lestep.App;
import com.lestep.service.SensorService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StepBroadcastReceiver extends BroadcastReceiver {
	
	private IUserDao userDao;
	@Override
	public void onReceive(Context context, Intent arg1) {
		userDao = new UserDaoImpl();
		// TODO Auto-generated method stub
		if(userDao.getUserNow()!=null){
			context.startService(new Intent(context, SensorService.class));
		}
	}

}
