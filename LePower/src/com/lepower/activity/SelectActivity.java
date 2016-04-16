package com.lepower.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lepower.R;
import com.lepower.utils.LogUtils;

public class SelectActivity extends Activity implements OnClickListener{

	private TextView tv_bind;
	private TextView tv_register_bind;
	private ImageView  bt_back;
	
	private String qqNum;
	private String weiboNum;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
		setContentView(R.layout.select);
		tv_bind=(TextView)findViewById(R.id.select_bind);
		tv_register_bind=(TextView)findViewById(R.id.register_bind);
		bt_back=(ImageView)findViewById(R.id.select_back);

		tv_bind.setOnClickListener(this);
		tv_register_bind.setOnClickListener(this);
		bt_back.setOnClickListener(this);
		Intent i = getIntent();
		qqNum = i.getStringExtra("qqNum");
		weiboNum = i.getStringExtra("weiboNum");
		LogUtils.e("qqNum:"+qqNum+",weiboNum:"+weiboNum);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.select_bind:{//绑定按钮
			Intent intent=new Intent(SelectActivity.this,BindTelActivity.class);
			intent.putExtra("qqNum", qqNum);
			intent.putExtra("weiboNum", weiboNum);
			startActivity(intent);
		}
			break;
		case R.id.register_bind:{//绑定并注册按钮
			Intent intent=new Intent(SelectActivity.this,RegisterTelActivity.class);
			if(!TextUtils.isEmpty(qqNum)){
				intent.putExtra("qq", true);
			}else{
				intent.putExtra("weibo", true);
			}
			startActivity(intent);
		}
			break;
		case R.id.select_back://返回
			finish();
			break;
		default:
			break;
		}

	}

}
