package com.lestep.widget;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class ClockView extends View{
	
	// 设置画笔
	private Paint paint;
	private Bitmap backBitmap;
	// 设置宽、高
	private int mWidth;
	private int mHeight;
	// 运动计步的完成度
	private float complete = 0.0f;
	private float scale;
	private static Context mContext;

	// 本次运动的幅度
	private float beginStep = 0.0f;	// 开始计步的幅度
	
	public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	public ClockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public ClockView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	public void setComplete(float complete){
		this.complete = complete;
		invalidate();
	}
	
	public void setBeginStep(float begin){
		this.beginStep = begin;
		invalidate();
	}
	
	public static int px2dp(float pxValue){
	    final float scale = mContext.getResources().getDisplayMetrics().density; 
	    return (int)(pxValue/scale+0.5f); 
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// 将背景图片画到画布上
		mWidth = getMeasuredWidth();
		mHeight = getMeasuredWidth();
		scale = px2dp(mWidth)/150f;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(6*scale);
		// 外边框阴影效果从白到灰再到白
		SweepGradient w2g2w = new SweepGradient(mHeight/2, mHeight/2, new int[]{Color.GRAY,Color.GRAY,Color.GRAY,Color.GRAY,Color.GRAY,0xfff0f0f0,0xfff0f0f0,0xfff0f0f0,Color.GRAY}, null);
		paint.setShader(w2g2w);
		canvas.drawCircle(mHeight/2, mHeight/2, mWidth/2-10*scale, paint); 	// 已经用去 9
		// 绘制最外面的环
		paint.setStrokeWidth(30*scale);
		paint.setShader(null);
		paint.setColor(Color.WHITE);
		canvas.drawCircle(mHeight/2, mHeight/2, mWidth/2-26*scale, paint);	// 
		// 绘制内部的环 灰色
		paint.setColor(0xffa3a3a3);
		paint.setStrokeWidth(20*scale);
		canvas.drawCircle(mHeight/2, mHeight/2, mWidth/2-49*scale, paint);
		// 最内部的环 灰色
		paint.setColor(0xffd7d7d7);
		paint.setStyle(Style.FILL);
		canvas.drawCircle(mHeight/2, mHeight/2, mWidth/2-49*scale, paint);

		// 弧形
		RectF rectRoundF = new RectF(26*scale,25*scale, mWidth-26*scale,mWidth-25*scale);
		paint.setStrokeWidth(29*scale);
		paint.setStyle(Style.STROKE);
		SweepGradient color = new SweepGradient(mHeight/2, mHeight/2, new int[]{
				0xff48a9dc,0xff8867af,0xffc9427c,0xffea804a,0xffeac23d,0xfface173,0xff4fe9cc,0xff48c9dc,0xff48a9dc
		}, null);
		paint.setShader(color);
		canvas.drawArc(rectRoundF, -90, complete*360, false, paint);
		// 动态变化的圈
//		if(beginStep < 100){
//			paint.setColor(0xff4fe9cc);
//			paint.setStyle(Style.FILL);
//			paint.setShader(null);
//			canvas.drawCircle(mHeight/2, mWidth/2, (mWidth/2-30)*beginStep/100, paint);
//		}
	}
	
}
