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

import com.lepower.R;
import com.lepower.model.Photos;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private List paths;
	private boolean localtion;// true是本地图片 false 是网络图片
	
	
	private AddImage add;

	public ImageAdapter(Context context, List paths, boolean localtion) {
		this.context = context;
		this.paths = paths;
		this.localtion = localtion;
	}

	public void setAddImage(AddImage add) {
		this.add = add;
	}


	public void setData(List<String> paths) {
		this.paths = paths;
	}

	@Override
	public int getCount() {
		return paths == null ? 0 : paths.size();
	}

	@Override
	public Object getItem(int position) {
		return paths == null ? null : paths.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = new Holder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.friend_images, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Photos ph = (Photos) paths.get(position);
		
		ImageOptions options=new ImageOptions.Builder().setIgnoreGif(false).
		        setImageScaleType(ScaleType.CENTER_CROP).
				setLoadingDrawableId(R.drawable.loding).
				setFailureDrawableId(R.drawable.load_failed).
				build();

//		holder.imageView.setScaleType(ScaleType.FIT_XY);
		
		//context.getAssets()+"/one_t.jpg"
		x.image().bind(holder.imageView, ph.max, options, new CommonCallback<Drawable>() {
			
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
		
//		finalBit.display(holder.imageView, ph.max);
		return convertView;
	}

	private class Holder {
		public ImageView imageView;
	}

	/**
	 * 加载图片接口 
	 * 
	 * @author
	 * 
	 */
	public interface AddImage {
		public void addImage(ImageView view, String path);
	}
	
	
	private Runnable r=new Runnable() {
		
		@Override
		public void run() {
			
		}
	};
}
