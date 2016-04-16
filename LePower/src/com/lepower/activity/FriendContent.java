package com.lepower.activity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lepower.R;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.FriendInfo;
import com.lepower.model.User;
import com.lepower.utils.AsyncTaskImageLoad;
import com.lepower.utils.FriendInfo_JsonParse;
import com.lepower.utils.GetObservable;
import com.lepower.utils.LeUrls;
import com.lepower.utils.LevelUtils;
import com.lepower.utils.NetWorkUtil;
import com.lepower.utils.ShowProDialog;
import com.lepower.utils.ToastUtils;

/**
 * 显示好友信息详情页面
 * 1、首先获取上个Activity的数据
 * 2、通过数据向服务器发送请求,返回数据
 * 3、在UI上显示数据(好友详情只会返回一个好友的数据)
 * @author menhao
 *
 */
public class FriendContent extends Activity {
	
	private static final String TAG1 = "friendId";
	
	private static final String TAG2 = "friendFlag";
	
	private String flag;
	
	private ImageView mIvphoto;
	
	private ProgressDialog progressDialog;
	
	/**
	 * 昵称
	 */
	private TextView mTvNickname;
	
	/**
	 * 性别
	 */
	private TextView mTvSex;
	
	/**
	 * 年龄
	 */
	private TextView mTvAge;
	
	private Button mBtnAdd;
	
	/**
	 * 运动等级
	 */
	private TextView mTvLevel;
	
	/**
	 * 关注好友数量
	 */
	private TextView mTvFriendCount;
	
	/**
	 * 总消耗卡路里
	 */
	private TextView mTvCalorrieSum;
	
	/**
	 * 总距离
	 */
	private TextView mTvRunDistance;
	
	/**
	 * 总步数
	 */
	private TextView mTvTotalStep;
	
	/**
	 * 总时间
	 */
	private TextView mTvRunTime;
	
	/**
	 * 记录获取的好友Id
	 */
	private String friendId;
	
	/**
	 * 记录好友的flag
	 */
	private String friendFlag;
	
	private String userId;
	
	private FriendInfo info;
	
	private List<FriendInfo> friendInfos;
	
	/**
	 * 关注好友的URL
	 */
	private String url1 = "http://10.6.11.75:8080/LeSports/friend/addFriend?userId=456&friendId=";
	
	/**
	 * 取消关注好友的URL
	 */
	private String url2 = "http://10.6.11.75:8080/LeSports/friend/deleteFriend?userId=456&friendId=";
	
	/**
	 * 好友详情的URL
	 */
	private String url3 = "http://10.6.11.75:8080/LeSports/friend/getFriendInfo?userId=";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friend);
		initView();
		getUserId();
		setListener();
		initData();
		getDataFromBack();	
	}

	private void getUserId() {
		//获取当前用户
		IUserDao iUserDao = new UserDaoImpl();
		User userNow = iUserDao.getUserNow();
		userId = userNow.getUserId();		
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		addOrQuitFriendClick();
	}

	private void initView() {
		friendInfos = new ArrayList<FriendInfo>();
		mIvphoto = (ImageView)findViewById(R.id.iv_enter_photo);
		mTvNickname = (TextView)findViewById(R.id.tv_nickname);
		mTvSex = (TextView)findViewById(R.id.tv_sex);
		mTvAge = (TextView)findViewById(R.id.tv_age);
		mBtnAdd = (Button)findViewById(R.id.btn_add);
		mTvLevel = (TextView)findViewById(R.id.tv_level);
		mTvFriendCount = (TextView)findViewById(R.id.tv_count_of_friend);
		mTvCalorrieSum = (TextView)findViewById(R.id.tv_calorieSum);
		mTvRunDistance = (TextView)findViewById(R.id.tv_allmeter);
		mTvRunTime = (TextView)findViewById(R.id.tv_alltime);
		mTvTotalStep = (TextView)findViewById(R.id.tv_allStep);
		progressDialog = new ProgressDialog(getActivity());
	}

	/**
	 * 添加或取消关注
	 */
	private void addOrQuitFriendClick() {
		mBtnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (NetWorkUtil.isNetWork(getActivity())) {
					if ("0".equals(friendFlag)) {  //关注状态
						String url2 = LeUrls.DELETE_FRIEND + userId + "&friendId=" + friendId;
						Observable observable = GetObservable.getObservable(url2);//这个url是点击不关注
						observable.subscribeOn(Schedulers.io())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Subscriber1());
					}else if ("1".equals(friendFlag)) { //不关注状态
						String url1 = LeUrls.ADD_FRIEND + userId + "&friendId=" + friendId;
 						Observable observable = GetObservable.getObservable(url1);//这个url是点击关注
						observable.subscribeOn(Schedulers.io())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Subscriber1());
					}		
				}else {
					ToastUtils.showToastShort(getActivity(), "网络不可连接");
				}
					
			}
		});
	}
	
	class Subscriber1 implements rx.Observer<String> {
		
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
			String resCode = FriendInfo_JsonParse.jsonParseToString(result);
			if (resCode.equals("0")) {
				mBtnAdd.setText("已关注");
				friendFlag = "0";
			}else if ("1".equals(resCode)) {
				mBtnAdd.setText("未关注");
				friendFlag = "1";
			}
		} 
	}

	private void getDataFromBack() {
		String url3 = LeUrls.FRIEND_INFO + friendId;
		if (NetWorkUtil.isNetWork(getActivity())) {
			ShowProDialog.showProgressDialog(progressDialog);
			Observable observable = GetObservable.getObservable(url3);//这个url是获取好友详情的url
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
					friendInfos.clear();//先清除上一次的数据
					friendInfos = FriendInfo_JsonParse.jsonParse(result);
					info = friendInfos.get(0);
					ShowProDialog.dissmissProgressDialog(progressDialog);
					showContent(info);
				}
			};
			
			observable.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(subscriber);
		}else {
			ToastUtils.showToastShort(getActivity(), "网络不能连接");
		}	
	}

	/**
	 * 显示服务器返回的数据
	 */
	protected void showContent(FriendInfo info) {
		// TODO Auto-generated method stub
		LoadImage(mIvphoto,info.getImgURL());
		mTvNickname.setText(info.getNickName());
		mTvAge.setText(info.getAge());
		mTvSex.setText(info.getSex());
//		mTvLevel.setText(info.getLevel());
		mTvLevel.setText(LevelUtils.getLevel(info.getCalorieSum()));
		mTvFriendCount.setText(info.getCountOfFriend());
		mTvCalorrieSum.setText(info.getCalorieSum());
		mTvRunDistance.setText(info.getRunDistance());
		mTvTotalStep.setText(info.getTotalStep());
		mTvRunTime.setText(info.getRunTime());
		if ("0".equals(friendFlag)) {	//是关注状态
			mBtnAdd.setText("已关注");
		}else if("1".equals(friendFlag)) {
			mBtnAdd.setText("未关注");
		}
	}
	
	//加载头像
	private void LoadImage(ImageView mIvphoto, String path) {
		//异步加载图片资源
		AsyncTaskImageLoad async = new AsyncTaskImageLoad(mIvphoto);
		//执行异步加载,并把图片的路径传送过去
		async.execute(path);
	}

	private void initData() {
		friendId = getIntent().getStringExtra(TAG1);
		friendFlag = getIntent().getStringExtra(TAG2); 
	}
	
	private FriendContent getActivity(){
		return FriendContent.this;
	}

}
