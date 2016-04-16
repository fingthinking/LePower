package com.lestep.dao;

import java.util.List;

import com.lestep.model.Step;

public interface IStepDao {
	/**
	 * 插入或更新步子
	 * @param step
	 */
	void saveOrUpdate(Step step);
	/**
	 * 删除步子
	 */
	void deleteNotToday();
	/**
	 * 找出今日的记录
	 * @return
	 */
	Step findToday();
	/**
	 * 找出今日所有的记录
	 * @return
	 */
	List<Step> findAllToday();
	/**
	 * 找出所有天数的记录
	 * @return
	 */
	List<Step> findAllIsDay();
	/**
	 * 找出所有未同步的天
	 * @return
	 */
	List<Step> findDayNotSynch();
	/**
	 * 删除今日数据
	 */
	void deleteToday();
}
