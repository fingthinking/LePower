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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lepower.R;
import com.lepower.adapter.FensiAdapter;
import com.lepower.model.FriendInfo;
import com.lepower.utils.FriendInfo_JsonParse;
import com.lepower.utils.GetObservable;
import com.lepower.utils.LeUrls;
import com.lepower.utils.NetWorkUtil;
import com.lepower.utils.ShowProDialog;
import com.lepower.utils.ToastUtils;

/**
 * 备注：如果要在这个界面显示数据,那么必须取得上个页面的数据,然后根据这个数据,拼接url，获取数据信息
 * @author menhao
 *
 */
public class FensiActivity extends Activity {
	
	private static final String TAG1 = "userId";
	
	private static final String TAG2 = "friendId";
		
	private static final String TAG3 = "friendFlag";
	
	private ListView mFensiList;
	
	private ImageView mIvFensi;
	
	private String result;
	
	private List<FriendInfo> friendInfos;
	
	private FriendInfo friendInfo = null;
	
	private FensiAdapter adapter;
	
	private String userId;
	
	private ProgressDialog progressDialog;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fensi);
		initUI();
		initData();
		getDataFromBack();
	}

	/**
	 * 显示粉丝列表
	 * @param friendInfos
	 */
	protected void showFensiList(List<FriendInfo> friendInfos) {
		adapter = new FensiAdapter(getActivity(), friendInfos,userId);
		mFensiList.setAdapter(adapter);
		
	}

	/**
	 * 从后台获取数据
	 */
	private void getDataFromBack() {
		String url1 = LeUrls.FANS_LIST + userId + "&flag=1";
		if (NetWorkUtil.isNetWork(getActivity())) {  //网络通
			ShowProDialog.showProgressDialog(progressDialog);
			getFansFriendInfo(url1);
		}else {
			ToastUtils.showToastShort(getActivity(), "网络不可连接");
		}
		
	}

	/**
	 * 从服务器获取粉丝信息
	 * @param url1
	 */
	private void getFansFriendInfo(String url1) {
		Observable observable = GetObservable.getObservable(url1);
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
				result = arg0;//获取从服务器返回的结果
				friendInfos = FriendInfo_JsonParse.jsonParse(result);
				ShowProDialog.dissmissProgressDialog(progressDialog);
				showFensiList(friendInfos);
			}
			
		};
		observable.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(subscriber);	
	}

	private void initData() {
		Intent intent = getIntent();
		userId = intent.getStringExtra(TAG1);
		friendInfos = new ArrayList<FriendInfo>();
	}

	private void initUI() {
		progressDialog = new ProgressDialog(getActivity());
		mIvFensi = (ImageView)findViewById(R.id.iv_fensi_back);
		onFensiBackClickListener();
		mFensiList = (ListView)findViewById(R.id.lv_fensi);
		
		/**
		 * 点击每个条目时调用,进入好友详情
		 */
		mFensiList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//获取当前的对象
				FriendInfo friendInfo = (FriendInfo) mFensiList.getItemAtPosition(position);
				//获取当前对象的Id
				//然后通过这个Id进入另一个好友详情的页面Activity
				Intent intent = new Intent(getActivity(),FriendContent.class);
				intent.putExtra(TAG2, friendInfo.getUserId());
				intent.putExtra(TAG3, friendInfo.getFlag());
				startActivity(intent);
			}
		});
	}

	/**
	 * 返回到我的详情里面
	 */
	private void onFensiBackClickListener() {
		mIvFensi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	private FensiActivity getActivity(){
		return FensiActivity.this;
	}
}
