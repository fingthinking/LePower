package com.lebicycling.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

public class ShareUtils {
	/**
	 * ���ַ���
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
	  * ͼƬ����
	  * @param context
	  * @param uri	�ļ���uri·��
	  * @param title	����ʱ����
	  */
	 public static void shareImage(Context context, Uri uri, String title) {
	        Intent shareIntent = new Intent();
	        shareIntent.setAction(Intent.ACTION_SEND);
	        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
	        shareIntent.setType("image/jpeg");
	        context.startActivity(Intent.createChooser(shareIntent, title));
	    }
	 
	 // view�ǵ�ǰ�����ĸ�����
	 public static void screenShot(Context context,View view,String name){
			view.setDrawingCacheEnabled(true);
			view.buildDrawingCache();
			Bitmap bitmap = view.getDrawingCache();
			try{
				File file = Environment.getExternalStorageDirectory();
				if(file.exists()){
					FileOutputStream out = new FileOutputStream(new File(file,name+".jpg"));
					bitmap.compress(CompressFormat.JPEG, 100, out);
					out.flush();
					out.close();
				}else{
					Toast.makeText(context, "�����ֻ���֧���ڴ濨", Toast.LENGTH_SHORT).show();
				}
			}catch(Exception e){
				e.printStackTrace();
				
			}
			
		}
	 
	 
	 
	 
	 
	 
}
