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

import com.lepower.R;
import com.lepower.adapter.NeibFriendAdapter;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.FriendInfo;
import com.lepower.model.LocationInfo;
import com.lepower.model.User;
import com.lepower.utils.FriendInfo_JsonParse;
import com.lepower.utils.GetObservable;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LocationUtil;
import com.lepower.utils.NetWorkUtil;
import com.lepower.utils.ShowProDialog;
import com.lepower.utils.ToastUtils;

/**
 * 
 * 1、首先获取上个Activity传过来的位置信息数据
 * 2、通过这个数据向服务器查询
 * 3、获取服务器返回的数据(放在list集合中)
 * 4、显示
 * 5、点击某个条目进入好友详情(也可以进行关注好友、取消关注操作)
 *
 */
public class NebiFriendListActivity extends Activity {
	
	private static final String TAG1 = "friendId";
	
	private static final String TAG2 = "friendFlag";
	
	private String URL = "http://192.168.1.100/LeSports/friend/findFriendNearby?userId=";
	
	private ListView mLvNebi;
	
	private List<FriendInfo> friendInfos = new ArrayList<FriendInfo>();
	
	private FriendInfo friendInfo;
	
	private LocationInfo locationInfo = null;
		
	private NeibFriendAdapter adapter;
	
	private ImageView ivBack;
	
	private ProgressDialog progressDialog;
	
	private String userId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.nebi_friend);
		initUI();
		getUserId();
		initData(); //首先获取上个Activity传过来的位置信息数据
	}

	/**
	 * 获取该用户的UserId
	 */
	private void getUserId() {
		//获取当前用户
		IUserDao iUserDao = new UserDaoImpl();
		User userNow = iUserDao.getUserNow();
		userId = userNow.getUserId();
	}

	private void initUI() {
		mLvNebi = (ListView)findViewById(R.id.lv_nebi);
		progressDialog = new ProgressDialog(getActivity());
		ivBack = (ImageView)findViewById(R.id.iv_add_friend_back);
		setBackClick();
		setListItemClick();
	}
	
	/**
	 * 返回上层页面
	 */
	private void setBackClick() {
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	/**
	 * 对ListView中的每一项设置监听
	 */
	private void setListItemClick() {
		mLvNebi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//获取当前对象
				FriendInfo friendInfo = (FriendInfo)mLvNebi.getItemAtPosition(position);
				//获取当前对象的Id
				//然后通过这个Id进入另一个好友详情的页面Activity
				Intent intent = new Intent(getActivity(),FriendContent.class);
				intent.putExtra(TAG1, friendInfo.getUserId());
				intent.putExtra(TAG2, friendInfo.getFlag());
				startActivity(intent);
			}
		});
	}

	/**
	 * 通过查询网络,如果网络通访问服务器
	 */
	private void initData() {
		locationInfo = LocationUtil.getLocation(getActivity());
		if (locationInfo != null && NetWorkUtil.isNetWork(getActivity())) { //网络通
			ShowProDialog.showProgressDialog(progressDialog);//显示加载框
			//首先获取位置信息
			//拼接URL
			String URL1 = LeUrls.NEBI_LIST + userId + "&longitude=" + locationInfo.getLongitude() + "&latitude=" + locationInfo.getLatitude();
			Log.e("heheda", URL1);
			//从服务器得到数据
			getNebiFriendInfo(URL1);
		}else {
			ToastUtils.showToast(getActivity(), "请检查网络或无法获取位置信息");
		}		
	}
	
	private void getNebiFriendInfo(String url) {
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
			public void onNext(String result) {
				// TODO Auto-generated method stub
				//做更新UI操作
				friendInfos = FriendInfo_JsonParse.jsonParse(result);
				ShowProDialog.dissmissProgressDialog(progressDialog);
				showNebiList(friendInfos);
			}
			
		};

		observable.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(subscriber);
	}
	
	/**
	 * 显示附近好友列表
	 * @param friendInfos
	 */
	protected void showNebiList(List<FriendInfo> friendInfos) {
		adapter = new NeibFriendAdapter(getActivity(), friendInfos,userId);
		mLvNebi.setAdapter(adapter);
	}

	private NebiFriendListActivity getActivity(){
		return NebiFriendListActivity.this;
	}
}
