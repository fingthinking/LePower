package com.lepower.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.DbManager;
import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.ex.DbException;
import org.xutils.image.ImageOptions;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lepower.R;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.SelfInfo;
import com.lepower.model.User;
import com.lepower.utils.FriendInfo_JsonParse;
import com.lepower.utils.GetObservable;
import com.lepower.utils.NetWorkUtil;
import com.lepower.utils.ShowProDialog;
import com.lepower.widget.SettingItemView;

/**
 * 自己的个人中心
 * @author menhao
 *
 */
public class SelfActivity extends Activity implements OnClickListener{
	
	private TextView mTvNickName;
	
	private TextView mTvLevel;
	
	private TextView mTvDongtai;
	
	private TextView mTvFensi;
	
	private TextView mTvGuanzhu;
	
	private ImageButton mIbAdd;
	
	private ImageView mIbSelfCenter;
	
	private SettingItemView mSivRun;
	
	private SettingItemView mSivDongtai;
	
	private SettingItemView mSivGuanzhu;
	
	private SettingItemView mSivFenSi;
	
	private String userId;//用户Id
	
	private String userUrl;//用户头像的URL
	
	private String nickName;//昵称
	
	private String level;//等级
	
	private List<SelfInfo> selfInfos = new ArrayList<SelfInfo>();
	
	private String URL = "http://192.168.1.100:8080/LeSports/friend/getUserImInfo?userId=";
	
	private ImageOptions imageOptions;
	
	private User userNow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.self_center);
		initView();
		//获取当前用户
		IUserDao iUserDao = new UserDaoImpl();
		userNow = iUserDao.getUserNow();
		userId = userNow.getUserId();
		Log.d("heheda", userId);
		userUrl = userNow.getImgURL();//获取头像URL
		Log.d("heheda", userUrl);
		nickName = userNow.getNickName();
		level = userNow.getLevel();
		imageOptions = new ImageOptions.Builder()
			.setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))
			//设置显示圆形图片
			.setCircular(true)
			//设置使用缓存
			.setUseMemCache(true)
			//设置支持gif
			.setIgnoreGif(false)
			.build();	

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		try {
			initData();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onResume();
	}

	/**
	 * 取数据放在UI上面
	 * 首先看网络是否连接,如果网络连接正常,访问网络
	 * 如果网路连接异常,从数据库中取出数据
	 * @throws DbException 
	 */
	private void initData() throws DbException {
		DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
		.setDbName("self.db");
		final DbManager dbManager = x.getDb(daoConfig);
		if (NetWorkUtil.isNetWork(getActivity())) {  //网络可用,从网络上获取数据,并存到数据库
			//从网络上获取数据
			String URL1 = URL + userId;
			Log.d("HEHEDA", URL1);
			x.image().bind(mIbSelfCenter, userUrl, imageOptions);
			Observable observable = GetObservable.getObservable(URL1);
			Subscriber subscriber = new Subscriber<String>() {

				@Override
				public void onCompleted() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onError(Throwable arg0) {
					// TODO Auto-generated method stub
					Log.e("heheda", arg0.getMessage());
				}

				@Override
				public void onNext(String result) {
					// TODO Auto-generated method stub
					selfInfos = FriendInfo_JsonParse.jsonParseToSelf(result);
					SelfInfo selfInfo = selfInfos.get(0);
					selfInfo.setId(1);
					Log.e("heheda", selfInfo.toString());
					try {
						dbManager.saveOrUpdate(selfInfo);
						Log.e("heheda", "db");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//Log.e("heheda", e.getMessage());
					}	
					showSelfInfoList(selfInfo);
				}				
			};
			
			observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(subscriber);
		}else {  //网络不可用,从数据库中选取
			x.image().bind(mIbSelfCenter, userUrl, imageOptions);
			SelfInfo selfInfo = dbManager.findFirst(SelfInfo.class);
			showSelfInfoList(selfInfo);
		}
		
	}

	protected void showSelfInfoList(SelfInfo selfInfo) {
		mTvDongtai.setText("动态\n"+selfInfo.getCountOfDongtai());
		mTvFensi.setText("粉丝\n"+selfInfo.getCountOfFensi());
		mTvGuanzhu.setText("关注\n"+selfInfo.getCountOfGuanzhu());
		mTvNickName.setText(nickName);
		mTvLevel.setText(level);
		mTvLevel.setText("");
	}

	private void initView() {
		mTvNickName = (TextView)findViewById(R.id.nickName);
		mTvLevel = (TextView)findViewById(R.id.level);
		mTvDongtai = (TextView)findViewById(R.id.tv_dongtai);
		mTvFensi = (TextView)findViewById(R.id.tv_fensi);
		mTvGuanzhu = (TextView)findViewById(R.id.tv_guanzhu);
		mIbAdd = (ImageButton)findViewById(R.id.add_friend);
		mIbSelfCenter = (ImageView)findViewById(R.id.enter_self_center);
		mSivRun = (SettingItemView)findViewById(R.id.siv_run);
		mSivDongtai = (SettingItemView)findViewById(R.id.siv_dongtai);
		mSivFenSi = (SettingItemView)findViewById(R.id.siv_fensi);
		mSivGuanzhu = (SettingItemView)findViewById(R.id.siv_guanzhu);
		mIbAdd.setOnClickListener(this);
		mIbSelfCenter.setOnClickListener(this);
		mTvDongtai.setOnClickListener(this);
		mTvFensi.setOnClickListener(this);
		mTvGuanzhu.setOnClickListener(this);
		mSivRun.setOnClickListener(this);
		mSivDongtai.setOnClickListener(this);
		mSivGuanzhu.setOnClickListener(this);
		mSivFenSi.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.tv_dongtai:
			intent = new Intent(getActivity(),PersonalCircleActivity.class);
			intent.putExtra("user", userNow);
			startActivity(intent);
			break;
		case R.id.tv_fensi: //进入粉丝列表
			intent = new Intent(getActivity(),FensiActivity.class);
			intent.putExtra("userId", userId);
			startActivity(intent);
			break;
		case R.id.tv_guanzhu: //进入关注列表
			intent = new Intent(getActivity(),GuanzhuActivity.class);
			intent.putExtra("userId", userId);
			startActivity(intent);
			break;
		case R.id.add_friend:
			intent = new Intent(getActivity(),AddFriendActivity.class);
			startActivity(intent);
			break; 
		case R.id.siv_run: //进入我的运动
			intent = new Intent(getActivity(),SportsStart.class);
			startActivity(intent);
			break;
		case R.id.siv_dongtai: //进入我的动态
			intent = new Intent(getActivity(),PersonalCircleActivity.class);
			intent.putExtra("user", userNow);
			startActivity(intent);
			break;
		case R.id.siv_fensi: //进入我的粉丝列表
			intent = new Intent(getActivity(),FensiActivity.class);
			intent.putExtra("userId", userId);
			startActivity(intent);
			break;
		case R.id.siv_guanzhu: //进入我的关注列表
			intent = new Intent(getActivity(),GuanzhuActivity.class);
			intent.putExtra("userId", userId);
			startActivity(intent);
			break;
		case R.id.enter_self_center://进入更新
			intent = new Intent(getActivity(),UpdateUserInfoActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	private SelfActivity getActivity(){
		return SelfActivity.this;
	}
	
}
