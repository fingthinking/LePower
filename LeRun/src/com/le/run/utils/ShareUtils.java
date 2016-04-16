package com.le.run.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ShareUtils {
	/**
	 * 文字分享
	 * @param context
	 * @param extraText
	 * @param title
	 */
	 public static void share(Context context, String extraText,String title) {
	        Intent intent = new Intent(Intent.ACTION_SEND);
	        intent.setType("text/plain");
	        intent.putExtra(Intent.EXTRA_SUBJECT, title);
	        intent.putExtra(Intent.EXTRA_TEXT, extraText);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(
	                Intent.createChooser(intent, title));
	    }
	 /**
	  * 图片分享
	  * @param context
	  * @param uri	文件的uri路径
	  * @param title	分享时标题
	  */
	 public static void shareImage(Context context, Uri uri, String title) {
	        Intent shareIntent = new Intent();
	        shareIntent.setAction(Intent.ACTION_SEND);
	        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
	        shareIntent.setType("image/jpeg");
	        context.startActivity(Intent.createChooser(shareIntent, title));
	    }
	 
	 
	 

		public static void screenShot(View view) {
			view.setDrawingCacheEnabled(true);
			view.buildDrawingCache();
			Bitmap bitmap = view.getDrawingCache();
			
			Log.e("+I am here+","ehehhe");
			try {
				File file = Environment.getExternalStorageDirectory();
				if (file.exists()) {
					OutputStream out = new FileOutputStream(new File(file,
							"temp.jpg"));
					bitmap.compress(CompressFormat.JPEG, 100, out);
					out.flush();
					out.close();

				} else {
					//Toast.makeText(, "您的手机不支持内存卡", 0).show();

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
