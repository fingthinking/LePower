package com.lestep.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RecordListAdapter extends BaseAdapter{
	List<Map<String, String>> mapList;

	public RecordListAdapter(Context context, List<Map<String, String>> mapList){
		this.mapList = mapList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mapList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mapList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
