package com.lepower.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lepower.R;
import com.lepower.utils.Jumper;

public class JumpDataAdapter extends BaseAdapter
{
	private Context mContext;
	private List<Jumper> jumpers = new ArrayList<Jumper>();

	public JumpDataAdapter(Context mContext, List<Jumper> jumpers)
	{
		this.mContext = mContext;
		this.jumpers = jumpers;
	}

	@Override
	public int getCount()
	{
		return jumpers.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		HolderView holderView = null;
		if (convertView == null)
		{
			holderView = new HolderView();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.jumplistview, null);
			holderView.listdate = (TextView) convertView
					.findViewById(R.id.list_date);
			holderView.listjump = (TextView) convertView
					.findViewById(R.id.list_jump);
			convertView.setTag(holderView);
		}
		else
		{
			holderView = (HolderView) convertView.getTag();
		}
			holderView.listdate.setText(jumpers.get(getCount()-1-position).getDate()
					+ "   最近" + (position + 1) + "次:    ");
			holderView.listjump.setText(jumpers.get(getCount()-1-position).getJumpCount()
					+ " 个  ");
		return convertView;
	}

	class HolderView
	{
		TextView listdate;
		TextView listjump;
	}
}
