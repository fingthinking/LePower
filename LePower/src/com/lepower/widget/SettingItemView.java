package com.lepower.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lepower.R;

public class SettingItemView extends RelativeLayout{
	
	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.lepower";
	
	private String mTitle;
	
	private TextView tvTitle;
	
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//先有值,再初始化view
		mTitle = attrs.getAttributeValue(NAMESPACE, "title");
		initView();
	}

	/**
	 * 初始化布局
	 */
	private void initView(){
		//将自定义好的布局文件设置给当前的SettingItemView
		View.inflate(getContext(), R.layout.view_setting_item, this);
		tvTitle = (TextView)findViewById(R.id.tv_title);
		setTitle(mTitle);
	}
	
	public void setTitle(String title){
		tvTitle.setText(title);
	}

}
