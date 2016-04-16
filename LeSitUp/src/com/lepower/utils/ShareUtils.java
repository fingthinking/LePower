package com.lepower.utils;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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
}
