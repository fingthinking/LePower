package com.lepower.callback;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface ImageCallback {
	public void imageLoad(ImageView imageView, Bitmap bitmap,
			Object... params);
}
