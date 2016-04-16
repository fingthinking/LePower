package com.lepower.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.lepower.widget.RoundImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * 异步加载图片
 * @author Administrator
 *
 */
public class AsyncTaskImageLoad extends AsyncTask<String,Integer,Bitmap>{
	
	private ImageView mImage = null;
	
	public AsyncTaskImageLoad(ImageView img){
		mImage = img;
	}

	//运行在子线程中
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(params[0]);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				InputStream input = conn.getInputStream();
				Bitmap map = BitmapFactory.decodeStream(input);
				return map;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(Bitmap result){
		if (mImage != null && result != null) {
			mImage.setImageBitmap(result);
		}
		super.onPostExecute(result);
	}
	
	
}
