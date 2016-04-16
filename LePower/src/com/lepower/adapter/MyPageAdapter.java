package com.lepower.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MyPageAdapter extends PagerAdapter {

	private ArrayList<View> listViews;// content

	private int size;// 页数

	public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
														// 初始化viewpager的时候给的一个页面
		this.listViews = listViews;
		size = listViews == null ? 0 : listViews.size();
	}

	public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
		this.listViews = listViews;
		size = listViews == null ? 0 : listViews.size();
	}

	public int getCount() {// 返回数量
		return size;
	}

	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
		((ViewPager) arg0).removeView(listViews.get(arg1 % size));
	}

	public void finishUpdate(View arg0) {
	}

	public Object instantiateItem(View arg0, int arg1) {// 返回view对象
		try {
			((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

		} catch (Exception e) {
		}
		return listViews.get(arg1 % size);
	}

	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
