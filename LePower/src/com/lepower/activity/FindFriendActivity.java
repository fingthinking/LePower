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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.lepower.R;
import com.lepower.adapter.FindFriendAdpter;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.FriendInfo;
import com.lepower.model.User;
import com.lepower.utils.FriendInfo_JsonParse;
import com.lepower.utils.GetObservable;
import com.lepower.utils.LeUrls;
import com.lepower.utils.NetWorkUtil;
import com.lepower.utils.ShowProDialog;
import com.lepower.utils.ToastUtils;

public class FindFriendActivity extends Activity implements OnClickListener{
	
	private static final String TAG1 = "friendId";
	
	private static final String TAG2 = "friendFlag";
	
	private String URL = "http://192.168.1.100/LeSports/friend/findFriends?";
		
	private ListView mLvfindFriend;
	
	private ImageView mIvBack;
	
	private Button mBtnSearch;
	
	private EditText mEtContent;
	
	private List<FriendInfo> friendInfos;
	
	private FindFriendAdpter adapter;
	
	private String userId;
	
	private ProgressDialog progressDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.find_friend);
		friendInfos = new ArrayList<FriendInfo>();
		getUserId();
		initUI();		
	}
	
	private void getUserId() {
		//获取当前用户
		IUserDao iUserDao = new UserDaoImpl();
		User userNow = iUserDao.getUserNow();
		userId = userNow.getUserId();
	}

	/**
	 * 判断用户有没有在EditText中写数据
	 */
	private boolean isEditTextHasContent() {
		if (TextUtils.isEmpty(mEtContent.getText().toString())) {  //EditText中的数据为空
			return true;
		}
		return false;
	}
		

	private void initUI() {
		mIvBack = (ImageView)findViewById(R.id.iv_find_friend_back);
		mIvBack.setOnClickListener(this);
		mEtContent = (EditText)findViewById(R.id.et_content);
		mBtnSearch = (Button)findViewById(R.id.btn_search);
		mBtnSearch.setOnClickListener(this);
		mLvfindFriend = (ListView)findViewById(R.id.lv_find_friend);
		progressDialog = new ProgressDialog(getActivity());
		setFindListItemClick();
	}

	/**
	 * 为FriendListView每个Item设置监听
	 */
	private void setFindListItemClick() {
		mLvfindFriend.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//获取当前对象
				FriendInfo friendInfo = (FriendInfo)mLvfindFriend.getItemAtPosition(position);
				//获取当前对象的id
				//然后通过这个Id进入另一个好友详情的页面Activity
				Intent intent = new Intent(getActivity(),FriendContent.class);
				intent.putExtra(TAG1, friendInfo.getUserId());
				intent.putExtra(TAG2, friendInfo.getFlag());
				startActivity(intent);
			}
		});
	}

	private FindFriendActivity getActivity(){
		return FindFriendActivity.this;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_find_friend_back:
			finish();
			break;
		case R.id.btn_search://点击搜索好友,出现搜索出来的好友列表
			Log.d("MainActivity", "进入搜索");
			searchFriend();
			break;
		default:
			break;
		}		
	}

	/**
	 * 找好友
	 */
	private void searchFriend() {
		if (NetWorkUtil.isNetWork(getActivity())) {  //先判断网络是否存在
//			然后判断一下搜索内容不能为空
			if (isEditTextHasContent()) {
				ToastUtils.showToastShort(getActivity(), "搜索内容不能为空");
			}else {
				//向服务器传递数据,并得到服务器返回的数据
//				String urlString = "http://192.168.1.117:8080/LeSports/friend/findFriends?le_id=" + mEtContent.getText().toString() + "&userId="+userId;
				String URL1 = LeUrls.FIND_LIST + "le_id=" + mEtContent.getText().toString() + "&userId="+userId;
				ShowProDialog.showProgressDialog(progressDialog);
				Observable observable = GetObservable.getObservable(URL1);
				observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<String>() {
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
						Log.d("heheda", result);
						friendInfos = FriendInfo_JsonParse.jsonParse(result);
						Log.d("heheda", "diaoyong");
						ShowProDialog.dissmissProgressDialog(progressDialog);
						showFindFriendList(friendInfos);
					}
					
				});
			}
		}else {
			ToastUtils.showToastShort(getActivity(), "网络不可连接");
		}

	}

	protected void showFindFriendList(List<FriendInfo> friendInfos2) {
		adapter = new FindFriendAdpter(friendInfos, getActivity(),userId);
		mLvfindFriend.setAdapter(adapter);
	}
}
