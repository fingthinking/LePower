package com.lepower.activity;

import java.util.List;



import com.lesitup.R;
import com.lepower.model.RecordInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecordinfoAdapter extends ArrayAdapter<RecordInfo>{

	private int resourceId;
	public RecordinfoAdapter(Context context, int textViewResource,
			List<RecordInfo> objects) {
		super(context, textViewResource, objects);
		// TODO Auto-generated constructor stub
		resourceId = textViewResource;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		RecordInfo info = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView startTimeTextView = (TextView) view.findViewById(R.id.StartTime);
		TextView endTimeTextView = (TextView) view.findViewById(R.id.endTime);
		TextView situpCountTextView = (TextView) view.findViewById(R.id.situpCount);
		TextView calorieTextView = (TextView) view.findViewById(R.id.calorie);
		
		startTimeTextView.setText(info.getStartTime());
		endTimeTextView.setText(info.getEndTime());
		situpCountTextView.setText(""+info.getsitupCount());//数字转换成字符串
		calorieTextView.setText(""+info.getCalorie());
		
		return view;
	}
}
