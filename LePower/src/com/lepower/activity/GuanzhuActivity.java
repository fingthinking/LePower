package com.lepower.activity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.lepower.R;
import com.lepower.adapter.GuanzhuAdapter;
import com.lepower.model.FriendInfo;
import com.lepower.utils.FriendInfo_JsonParse;
import com.lepower.utils.GetObservable;
import com.lepower.utils.LeUrls;
import com.lepower.utils.NetWorkUtil;
import com.lepower.utils.ShowProDialog;
import com.lepower.utils.ToastUtils;

public class GuanzhuActivity extends Activity implements OnClickListener{
	
	private static final String TAG1 = "userId";
	
	private static final String TAG2 = "friendId";
	
	private static final String TAG3 = "friendFlag";

	private ListView mGuanzhuList;
	
	private ImageView mIvGuanzhu;
	
	private String result;
	
	private List<FriendInfo> friendInfos;
	
	private FriendInfo friendInfo = null;
	
	private GuanzhuAdapter adapter;
	
	private String userId;	
	
	private ProgressDialog progressDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guanzhu);
		initUI();
		initData();
		getDataFromBack();
	}

	/**
	 * 从后台返回数据
	 */
	private void getDataFromBack() {
		String url = LeUrls.GUANZHU_LIST + userId + "&flag=0";
		if (NetWorkUtil.isNetWork(getActivity())) {  //网络通
			ShowProDialog.showProgressDialog(progressDialog);
			getGuanzhuFriendInfo(url);
		}else {
			ToastUtils.showToastShort(getActivity(), "网络不可连接");
		}
		
	}
	
	/**
	 * 获取关注好友信息
	 * @param url
	 */
	private void getGuanzhuFriendInfo(String url) {
		Observable observable = GetObservable.getObservable(url);
		Subscriber subscriber = new Subscriber<String>() {

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNext(String arg0) {
				// TODO Auto-generated method stub
				result = arg0;
				friendInfos = FriendInfo_JsonParse.jsonParse(result);
				ShowProDialog.dissmissProgressDialog(progressDialog);
				showGuanzhuList(friendInfos);
			}
		};
		observable.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(subscriber);
		
	}

	/**
	 * 显示关注列表
	 * @param friendInfos
	 */
	protected void showGuanzhuList(List<FriendInfo> friendInfos) {
		adapter = new GuanzhuAdapter(getActivity(), friendInfos,userId);
		mGuanzhuList.setAdapter(adapter);	
	}

	private void initData() {
		Intent intent = getIntent();
		userId = intent.getStringExtra(TAG1);
		friendInfos = new ArrayList<FriendInfo>();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		progressDialog = new ProgressDialog(getActivity());
		mGuanzhuList = (ListView)findViewById(R.id.lv_guanzhu);
		mIvGuanzhu = (ImageView)findViewById(R.id.iv_guanzhu_back);
		mIvGuanzhu.setOnClickListener(this);
		mGuanzhuList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//获取当前的对象
				FriendInfo friendInfo = (FriendInfo) mGuanzhuList.getItemAtPosition(position);
				//获取当前对象的Id
				//然后通过这个Id进入另一个好友详情的页面Activity
				Intent intent = new Intent(getActivity(),FriendContent.class);
				intent.putExtra(TAG2, friendInfo.getUserId());
				intent.putExtra(TAG3, friendInfo.getFlag());
				startActivity(intent);
			}
		});
	}
	
	private GuanzhuActivity getActivity(){
		return GuanzhuActivity.this;
	}

	/**
	 * 返回我的中心
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//返回我的详情页面,此时还要向服务器发送请求
		case R.id.iv_guanzhu_back:
			finish();
			break;

		default:
			break;
		}
		
	}
}
