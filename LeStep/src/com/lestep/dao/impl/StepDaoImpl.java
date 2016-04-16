package com.lestep.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.xutils.DbManager;
import org.xutils.DbManager.DaoConfig;
import org.xutils.DbManager.DbOpenListener;
import org.xutils.x;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import com.lepower.callback.HttpCallback;
import com.lepower.utils.LogUtils;
import com.lestep.dao.IStepDao;
import com.lestep.model.Step;
import com.lestep.service.StepDownloadService;
import com.lestep.utils.DateUtils;

public class StepDaoImpl implements IStepDao {
	private static DaoConfig stepConfig;
	static {
		stepConfig = new DaoConfig().setDbVersion(1).setDbOpenListener(
				new DbOpenListener() {

					@Override
					public void onDbOpened(DbManager db) {
						// TODO Auto-generated method stub
						db.getDatabase().enableWriteAheadLogging();
					}
				});
	}

	public StepDaoImpl(String userId) {
		stepConfig.setDbName("StepDB_" + userId + ".db");
	}

	@Override
	public void saveOrUpdate(Step step) {
		// TODO Auto-generated method stub
		try {
			x.getDb(stepConfig).saveOrUpdate(step);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteNotToday() {
		// TODO Auto-generated method stub
		WhereBuilder builder = WhereBuilder.b("day", "!=",
				DateUtils.getDate(System.currentTimeMillis(), "yyyy-MM-dd"))
				.and("isToday", "!=", true);
		try {
			LogUtils.e("delete",x.getDb(stepConfig).delete(Step.class, builder));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除今日数据
	 */
	@Override
	public void deleteToday() {
		// TODO Auto-generated method stub
		WhereBuilder builder = WhereBuilder.b("day", "=",
				DateUtils.getDate(System.currentTimeMillis(), "yyyy-MM-dd"))
				.and("isToday", "=", true);
		LogUtils.e("deleteToday==========================");
		try {
			x.getDb(stepConfig).delete(Step.class, builder);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public Step findToday() {
		Step todayStep = null;
		try {
			todayStep = x
					.getDb(stepConfig)
					.selector(Step.class)
					.where("day",
							"=",
							DateUtils.getDate(System.currentTimeMillis(),
									"yyyy-MM-dd")).and("isToday", "=", true)
					.findFirst();
			if (todayStep == null) {
				todayStep = new Step();
				todayStep.setStepId(System.currentTimeMillis());
				todayStep.setDay(DateUtils.getDate(System.currentTimeMillis(),
						"yyyy-MM-dd"));
				todayStep.setStartTime("");
				todayStep.setEndTime("");
				todayStep.setToday(true);
				//x.getDb(stepConfig).saveOrUpdate(todayStep);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return todayStep;
	}

	@Override
	public List<Step> findAllToday() {
		// TODO Auto-generated method stub
		List<Step> stepList = null;
		try {
			stepList = x
					.getDb(stepConfig)
					.selector(Step.class)
					.where("day",
							"=",
							DateUtils.getDate(System.currentTimeMillis(),
									"yyyy-MM-dd")).and("isToday", "!=", true)
					.findAll();
		
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LogUtils.e("stepList==null"+(stepList == null));
		if(stepList == null){
			stepList = new ArrayList<Step>();
		}
		return stepList;
	}

	@Override
	public List<Step> findAllIsDay() {
		// TODO Auto-generated method stub
		List<Step> stepList = null;
		try {
			stepList = x.getDb(stepConfig).selector(Step.class)
					.where("isToday", "=", true).orderBy("day").findAll();
			return stepList;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(stepList == null){
			stepList = new ArrayList<Step>();
		}
		return stepList;
	}
	
	@Override
	public List<Step> findDayNotSynch() {
		// TODO Auto-generated method stub
		List<Step> stepList = null;
		try {
			stepList = x.getDb(stepConfig).selector(Step.class)
					.where("isToday", "=", true).and("hasSynch", "=", false).orderBy("day").findAll();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(stepList == null){
			stepList = new ArrayList<Step>();
		}
		return stepList;
	}

}
