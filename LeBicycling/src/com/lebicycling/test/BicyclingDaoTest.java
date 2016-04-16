package com.lebicycling.test;

import java.util.ArrayList;

import android.test.AndroidTestCase;
import android.util.Log;

import com.lebicycling.db.BicyclingDao;
import com.lebicycling.entity.Bicycling;

public class BicyclingDaoTest extends AndroidTestCase {

	public void testAdd(){
		BicyclingDao dao = BicyclingDao.getInstance(getContext());
		
		for(int i=0;i<9;i++){
			Bicycling bicycling = new Bicycling(i+20,21+"",(i+5)*3.55,(i+1)*2.4,(i+1)*2.2,(i+1)*3.3,(i+2)*2.1,"2016-03-"+(i+10));
			dao.addBicyclingData(bicycling);
		}
		
	}
	
	public void testDelete(){
		BicyclingDao dao = BicyclingDao.getInstance(getContext());
		dao.deleteBicyclingData(5, "2016-03-05");
	}
	
	public void testQueryAll(){
		BicyclingDao dao = BicyclingDao.getInstance(getContext());
		ArrayList<Bicycling> list = dao.getBicyclingData();
		for(int i=0;i<list.size();i++){
			Log.i("Log", list.get(i).toString());
		}
	}
	
	
	public void testQueryByDate(){
		BicyclingDao dao = BicyclingDao.getInstance(getContext());
		ArrayList<Bicycling> list = dao.getBicyclingDataByDate("2016-03-06", 6+"");
		for(int i=0;i<list.size();i++){
			Log.i("Log", list.get(i).toString());
		}
	}
	
	
}
