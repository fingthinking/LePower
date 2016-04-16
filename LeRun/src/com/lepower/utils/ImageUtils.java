package com.lepower.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

import com.lepower.App;
import com.lepower.callback.HttpCallback;

public class ImageUtils {
	/**
	 * 远程加载图片到imageView
	 * 
	 * @param url
	 * @param imgView
	 */
	public static void loadImage(String url, final ImageView imgView) {

		x.image().loadDrawable(url, new ImageOptions.Builder().build(),
				new CommonCallback<Drawable>() {

					@Override
					public void onSuccess(Drawable drawable) {
						// TODO Auto-generated method stub
						imgView.setImageDrawable(drawable);
					}

					@Override
					public void onFinished() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(Throwable arg0, boolean arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onCancelled(CancelledException arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	/**
	 * 上传图片
	 * 
	 * @param url
	 * @param filePath
	 */
	public static void uploadImage(String url, String filePath,
			HttpCallback<String> callback) {
		RequestParams params = new RequestParams(url);
		File bitmap = new File(filePath);
		if (bitmap.exists()) {
			params.addBodyParameter("bitmap", bitmap);
			params.setMultipart(true);
		}
		x.http().post(params, callback);

	}

	/**
	 * 上传图片
	 * 
	 * @param url
	 * @param filePath
	 */
	public static void uploadImage(String url, Bitmap bitmap,
			HttpCallback<String> callback) {
		RequestParams params = new RequestParams(url);

		params.addBodyParameter(url, saveBitmap(bitmap));
		params.setMultipart(true);
		x.http().post(params, callback);

	}

	private static File saveBitmap(Bitmap bitmap) {
		if (Tools.hasSdcard()) {
			File tempFile = new File(Environment.getExternalStorageDirectory()
					+ "/" + "temp.jpg");
			try {
				bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(
						tempFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tempFile;
		}
		return null;
	}

}
