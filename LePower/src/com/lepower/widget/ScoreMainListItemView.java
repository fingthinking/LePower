package com.lepower.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lepower.R;

public class ScoreMainListItemView extends LinearLayout {
	private Context mContext;
	private AttributeSet mAttrs;
	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.lepower";
	
	private String sTitle,sItem0Value,sItem1Value,sItem2Value,sItem3Value,sItem4,sItem4Value;
	
	private TextView tvTitle,tvItem0Value,tvItem1Value,tvItem2Value,tvItem3Value,tvItem4,tvItem4Value;
	
	public ScoreMainListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		mAttrs=attrs;
//		sTitle = attrs.getAttributeValue(NAMESPACE, "sTitle");
//		sItem0Value = attrs.getAttributeValue(NAMESPACE, "sItem0Value");
//		sItem1Value = attrs.getAttributeValue(NAMESPACE, "sItem1Value");
//		sItem2Value = attrs.getAttributeValue(NAMESPACE, "sItem2Value");
//		sItem3Value = attrs.getAttributeValue(NAMESPACE, "sItem3Value");
//		sItem4 = attrs.getAttributeValue(NAMESPACE, "sItem4");
//		sItem4Value = attrs.getAttributeValue(NAMESPACE, "sItem4Value");
		setMyAttrs(attrs);
		LayoutInflater.from(context).inflate(R.layout.score_main_listitem_view, this);
		
		initView();
		
		setViewText();
		
	}

	private void setViewText() {
		tvTitle.setText(sTitle);
		tvItem0Value.setText(sItem0Value);
		tvItem1Value.setText(sItem1Value);
		tvItem2Value.setText(sItem2Value);
		tvItem3Value.setText(sItem3Value);
		tvItem4.setText(sItem4);
		tvItem4Value.setText(sItem4Value);
	}

	private void initView() {
		tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvItem0Value = (TextView)findViewById(R.id.tvItem0Value);
		tvItem1Value = (TextView)findViewById(R.id.tvItem1Value);
		tvItem2Value = (TextView)findViewById(R.id.tvItem2Value);
		tvItem3Value = (TextView)findViewById(R.id.tvItem3Value);
		tvItem4 = (TextView)findViewById(R.id.tvItem4);
		tvItem4Value = (TextView)findViewById(R.id.tvItem4Value);
	}
	
	public void setMyAttrs(AttributeSet attrs){
		
		TypedArray a = mContext.obtainStyledAttributes(attrs,R.styleable.roundedimageview); 
		
	    sTitle = a.getString(R.styleable.ScoreMainListItemView_sTitle);
		sItem0Value = a.getString(R.styleable.ScoreMainListItemView_sItem0Value);
		sItem1Value = a.getString(R.styleable.ScoreMainListItemView_sItem1Value);
		sItem2Value = a.getString(R.styleable.ScoreMainListItemView_sItem2Value);
		sItem3Value = a.getString(R.styleable.ScoreMainListItemView_sItem3Value);
		sItem4 = a.getString(R.styleable.ScoreMainListItemView_sItem4);
		sItem4Value = a.getString(R.styleable.ScoreMainListItemView_sItem4Value);
		
//		invalidate();
	}
	
	
	public void setMyScoresAttrs(String msTitle,String msItem0Value, String msItem1Value, String msItem2Value, String msItem3Value, String msItem4, String msItem4Value){
		initView();
		tvTitle.setText(msTitle);
		tvItem0Value.setText(msItem0Value);
		tvItem1Value.setText(msItem1Value);
		tvItem2Value.setText(msItem2Value);
		tvItem3Value.setText(msItem3Value);
		tvItem4.setText(msItem4);
		tvItem4Value.setText(msItem4Value);
		
		invalidate();
	}
	
	
	
	
}
