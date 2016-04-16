package com.lebicycling.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lebicycling.R;
import com.lebicycling.entity.SItem;

public class MySpinnerAdapter extends BaseAdapter {

	private ArrayList<SItem> list; 
	private Context context;
	
	public MySpinnerAdapter(ArrayList<SItem> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflate = LayoutInflater.from(context);
		convertView = inflate.inflate(R.layout.item_spinner, null);
		
			TextView tDescription = (TextView) convertView.findViewById(R.id.item_description);
//			TextView tRunId = (TextView) convertView.findViewById(R.id.item_run_id);
			tDescription.setText(list.get(position).getDescription());
//			tRunId.setText(""+list.get(position).getbicyclingId());
		
		return convertView;
	}

}
