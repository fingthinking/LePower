package com.lepower.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lepower.R;


@SuppressLint("ResourceAsColor")
public class TitleBar extends RelativeLayout {

	private View titleBarView;
	private LayoutInflater layoutInflater;
	private TextView leftStr;
	private ImageView rightImage;
	private TextView centerTitle;
	private TextView rightStr;
	private RelativeLayout allView;
	
	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		InitTitleBarView(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		InitTitleBarView(context);
	}

	public TitleBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		InitTitleBarView(context);
	}
	
	public void InitTitleBarView(Context context){
		layoutInflater=LayoutInflater.from(context);
		titleBarView=layoutInflater.inflate(R.layout.say_title, this);
		leftStr=(TextView)titleBarView.findViewById(R.id.say_back);
		rightStr=(TextView)titleBarView.findViewById(R.id.say_publish);
		centerTitle=(TextView)titleBarView.findViewById(R.id.say_title);

	}
	
	/**
	 * 
	 * @param title
	 * @param leftImages
	 * @param onclick
	 */
	/*public void showLeft(String title,Drawable leftImages,OnClickListener onclick){
		centerTitle.setText(title);
		centerTitle.setVisibility(View.VISIBLE);
		leftImage.setImageDrawable(leftImages);
		leftImage.setVisibility(View.VISIBLE);
		leftImage.setOnClickListener(onclick);
	}*/
	
	/**
	 *
	 * @param title
	 * @param leftImages
	 * @param onclick
	 */
	/*public void showReft(String title,int rightImages,OnClickListener onclick){
		centerTitle.setText(title);
		centerTitle.setVisibility(View.VISIBLE);
		rightImage.setImageResource(rightImages);
		rightImage.setVisibility(View.VISIBLE);
		rightImage.setOnClickListener(onclick);
	}*/
	
	/**
	 * 
	 * @param title
	 * @param leftImages
	 * @param rightImages
	 * @param leftClicki
	 * @param rightClick
	 */
	/*public void showLeftAndRight(String title,Drawable leftImages,Drawable rightImages,OnClickListener leftClicki,OnClickListener rightClick){
		centerTitle.setText(title);
		centerTitle.setVisibility(View.VISIBLE);
		
		leftStr.setImageDrawable(leftImages);
		leftStr.setVisibility(View.VISIBLE);	
		leftStr.setOnClickListener(leftClicki);
		
		rightImage.setImageDrawable(rightImages);
		rightImage.setVisibility(View.VISIBLE);
		rightImage.setOnClickListener(rightClick);
	}*/
	
	/**
	 * 
	 * @param title
	 * @param rightStr
	 * @param leftImages
	 * @param leftClick
	 * @param rightClick
	 */
	public void showLeftStrAndRightStr(OnClickListener leftClick,OnClickListener rightClick){
		
		leftStr.setOnClickListener(leftClick);
		rightStr.setOnClickListener(rightClick);
		//rightStr.setTextSize(16);
	}
	
	/**
	 * 
	 * @param color
	 */
	public void setBgColor(int color){
		allView.setBackgroundColor(color);
	}
	
	/**
	 * ֻ
	 * @param title
	 */
	public void showCenterTitle(String title){
		centerTitle.setText(title);
		centerTitle.setVisibility(View.VISIBLE);
	}

	
	
	

}
