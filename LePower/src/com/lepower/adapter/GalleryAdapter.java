package com.lepower.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.lepower.R;
import com.lepower.model.Photos;

public class GalleryAdapter extends BaseAdapter {

	private List<Photos> photos;
	private Context context;
	
	public GalleryAdapter(Context context,List<Photos> photos) {
		this.context=context;
		this.photos=photos;
	}
	
	@Override
	public int getCount() {
		return photos==null? 0:photos.size();
	}

	@Override
	public Object getItem(int position) {
		return photos==null? null:photos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.gallery_item, null);
			holder=new ViewHolder();
			holder.imageView=(ImageView) convertView.findViewById(R.id.gallery_image);
			holder.propotion=(TextView)convertView.findViewById(R.id.gallery_image_propotion);
			
			convertView.setTag(holder);
			
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		final int current=position;
		final int total=getCount();
		
		holder.propotion.setText(1+current+"/"+total);
		
		Photos photo=(Photos) getItem(position);
		
		LogUtil.e(getCount()+" : "+photo.max);
		
		ImageOptions options=new ImageOptions.Builder().setIgnoreGif(false).
		        setImageScaleType(ScaleType.CENTER_CROP).
				setLoadingDrawableId(R.drawable.loding).
				setFailureDrawableId(R.drawable.load_failed).
				build();
		
		
		//x.image().loadDrawable(photo.max, options, null);
		
		//context.getAssets()+"/one_t.jpg"
		x.image().bind(holder.imageView,photo.max, options, new CommonCallback<Drawable>() {
			
			@Override
			public void onSuccess(Drawable arg0) {
				LogUtil.d("下载成功");
			}
			
			@Override
			public void onFinished() {
				LogUtil.d("下载完成");
			}
			
			@Override
			public void onError(Throwable arg0, boolean arg1) {
				LogUtil.e("下载出错 "+arg0.toString());
			}
			
			@Override
			public void onCancelled(CancelledException arg0) {
				LogUtil.d("下载被取消 :" + arg0.toString());
			}
		});
		
		
		return convertView;
	}
	
	
	private class ViewHolder{
		ImageView imageView;
		TextView propotion;
	}
}
