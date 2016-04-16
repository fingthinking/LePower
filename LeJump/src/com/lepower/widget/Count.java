package com.lepower.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

import com.lepower.R;

public class Count extends View
{

	public Count(Context context)
	{
		super(context);
	}

	public Count(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private int jumpCount;
	private int max;
	private float radis;

	public void setMax(int max)
	{
		this.max = max;
		postInvalidate();
	}

	public void setJumpCount(int jumpCount)
	{
		this.jumpCount = jumpCount;
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		float width = canvas.getWidth();
		Paint paint = new Paint();
		paint.setStrokeWidth(20);
		paint.setAlpha(128);
		paint.setColor(Color.parseColor("#00FFFF"));
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		paint.setDither(true);
		canvas.drawCircle((float) (width / 2.0), (float) (float)250,
				(float)200, paint);

		paint.reset();
		paint.setAntiAlias(true);
		paint.setDither(true);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.tiaosheng_bg2);
		paint.setShader(new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP));
		if (jumpCount <= max)
		{
			if (max == 0)
			{
				radis = 0;
			}
			else
			{
				radis = (float) ((jumpCount / (float) max) * 190.0);
			}
		}
		else
		{
			radis = 190;
		}
		canvas.drawCircle((float) (width / 2.0), (float)250, radis,
				paint);

	}
}
