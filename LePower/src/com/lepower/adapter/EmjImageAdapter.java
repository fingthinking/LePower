package com.lepower.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lepower.R;

public class EmjImageAdapter extends BaseAdapter {

	private List<Integer> emjs;
	private List<String> names;
	private Context context;
	
	private float ratio=1.5f;
	
	public EmjImageAdapter(List<Integer> emjs,List<String> names,Context context,float ratio) {
		this.emjs=emjs;
		this.context=context;
		this.names=names;
		this.ratio=ratio;
	}
	
	@Override
	public int getCount() {
		return emjs.size();
	}

	@Override
	public Object getItem(int position) {
		return emjs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.item_image, null);
			holder.image=(ImageView) convertView.findViewById(R.id.image_item);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		Bitmap bm;
		if(position==emjs.size()-1){
			
			//delete
			bm=BitmapFactory.decodeResource(context.getResources(), 
					R.drawable.emoji_delete);
		}else{
			bm=BitmapFactory.decodeResource(context.getResources(), 
					emjs.get(position));
		}
		
		holder.resourcesId=emjs.get(position);
		holder.name=names.get(position);
		
		holder.image.setImageBitmap(bm);
		int heigh=bm.getHeight();
		int width=bm.getWidth();
//		if(position!=emjs.size()-1){
//			holder.image.setLayoutParams(new RelativeLayout.
//					LayoutParams(castInt(width), castInt(heigh)));
//		}else{
//			holder.image.setLayoutParams(new RelativeLayout.
//					LayoutParams((int)(width*1.2f),(int)(heigh*1.2f)));
//		}
		
		holder.image.setLayoutParams(new RelativeLayout.
				LayoutParams(castInt(width), castInt(heigh)));
		
		return convertView;
	}
	
	
	private int castInt(int size){
		return (int)(size*ratio);
	}
	
	
	public class ViewHolder{
		public ImageView image;
		public int resourcesId;
		public String name;
	}

}
