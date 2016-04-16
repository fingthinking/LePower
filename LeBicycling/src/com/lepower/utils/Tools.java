package com.lepower.utils;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Environment;

public class Tools {
	/**
	 * 检查是否存在SDCard
	 * @return
	 */
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
//	public static Bitmap toRoundBitmap(Bitmap bitmap){
//		int width = bitmap.getWidth();
//		int height = bitmap.getHeight();
//		int r = width <= height ? width : height;
//		Bitmap backgroundBitmap = Bitmap.createBitmap(r, r, Config.ARGB_8888);
//		
//		Canvas canvas = new Canvas(backgroundBitmap);
//		Paint paint = new Paint();
//		// 设置边缘光滑去锯齿
//		paint.setAntiAlias(true);
//		
//		RectF rectF = new RectF(0, 0, r, r);
//		canvas.drawRoundRect(rectF, r/2, r/2, paint);
//		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//		canvas.drawBitmap(bitmap, null, rectF,paint);
//		return backgroundBitmap;
//		
//	}
}
