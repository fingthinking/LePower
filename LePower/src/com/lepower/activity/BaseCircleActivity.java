package com.lepower.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lepower.R;
import com.lepower.adapter.FriendAdapter;
import com.lepower.adapter.FriendAdapter.FlushListView;
import com.lepower.callback.MyCommonCallback;
import com.lepower.model.Favour;
import com.lepower.model.FriendAction;
import com.lepower.model.Reply;
import com.lepower.model.User;
import com.lepower.utils.BeanUtil;
import com.lepower.utils.EmojWindow;
import com.lepower.utils.EmojWindow.ReplySend;
import com.lepower.utils.FriendActionTool;
import com.lepower.utils.LeUrls;
import com.lepower.utils.NetUtils;
import com.lepower.widget.XListView;
import com.lepower.widget.XListView.IXListViewListener;

public abstract class BaseCircleActivity extends Activity implements
		IXListViewListener, OnClickListener ,ReplySend{

	public static final String LEPOWER_MYCIRCLE = "MY_CIRCLE"; // 自己的朋友圈
	public static final String LEPOWER_PERSONAL = "PERSONAL_CIRCLE"; // 只有自己或某一个朋友的

	private static final String BASEACTIVITY_REPLY_BACK="BaseActivity.replyBack";
	
	private static final String TAG = "BaseActivity";

	private String circleType;// 从 LEPOWER_MYCIRCLE 和 LEPOWER_PERSONAL 取值

	private XListView listView;
	private PopupWindow window;// 评论window

	private ImageButton back;// 返回按钮
	private ImageButton more;// 弹出更多
	private FriendAction friend;
	private Reply reply;
	private TextView discuss;// 评论
	private TextView favuor;// 赞
	private TextView transmit; // 转发
	private TextView favuorCancle;// 取消赞
	private RelativeLayout topLayout;

	// ==============回复==============
	private EditText replyEdit;// 回复框
	// ====================回复完结================

	private FriendAdapter adpter;

	private List<FriendAction> list;// 动态数据

	private InputMethodManager manager;

	private PopupWindow galleryWindow;
	
	private int lastPosition;// listView item最后所在的位置
	private int lastY;// listView item最后所在的y坐标

	private LinearLayout head_top_bg;
	
	private EmojWindow emojWindow;
	
	private List<FriendAction> newDatas=new ArrayList<FriendAction>();
	
	private ProgressBar progressBar;
	
	private int pageNow=1;
	private int pageSize=10;
	private String userId;
	
	
	/**
	 * 头部用户信息部分
	 */

	private View headView;// 头部view
	private ImageView userPhoto;// 头像头像
	private TextView userName;// 用户名字
	private TextView newsNum;// 最新动态数量
	private TextView favourNum;// 最新评论、点赞数量

	private User userNow;
	
	private Context context;
	private ImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);

		
		Log.e("BaseActivity", "OnCreate()");
		
		context=this;;
		options = new ImageOptions.Builder()
		.setImageScaleType(ScaleType.CENTER_CROP).setIgnoreGif(false)
		.setCircular(true).setRadius(20).setSquare(false)
		.setLoadingDrawableId(R.drawable.loding)
		.setFailureDrawableId(R.drawable.user_logo).build();

		userNow=new User();
		userNow.setImgURL("nullx");
		
		//注册评论返回后操作的广播
		IntentFilter filter=new IntentFilter(BASEACTIVITY_REPLY_BACK);
		registerReceiver(replyBackReceiver, filter);
		
		initGalleryWindow();
		
		emojWindow=EmojWindow.getInstance(this);
		emojWindow.setReplySend(this);
		initViews();
		adpter.setData(list);
		adpter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		onRefresh();
	}
	/**
	 * 设置当前活动类型
	 * @param circleType
	 */
	public void setCircleType(String circleType){
		this.circleType=circleType;
	}

	/**
	 * 设置当前用户
	 * @param userNow
	 */
	public void setUserNow(User userNow) {
		this.userNow = userNow;
		x.image().bind(userPhoto, userNow.getImgURL(), options);
	}
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public User getUserNow() {
		return userNow;
	}
	
	
	/**
	 * 新数据到达
	 * @param datas
	 */
	public void onDataArrived(List<FriendAction> datas) {
		progressBar.setVisibility(View.GONE);
		list = datas;
		if(list!=null&&list.size()>0&&circleType.equals("PERSONAL_CIRCLE")){
			x.image().bind(userPhoto, userNow.getImgURL(), options);
		}
		userName.setText(userNow.getNickName());
		adpter.setUser(userNow);
		adpter.setData(list);
		adpter.notifyDataSetChanged();
	}
	
	/**
	 * 加载跟多返回
	 * @param datas
	 */
	public void onLoadMoreArrived(List<FriendAction> datas){
		int position=list.size();
		list.addAll(datas);
		adpter.notifyDataSetChanged();
		listView.setSelection(position);
	}

	public String getCircleType() {
		return circleType;
	}

	/**
	 * 初始化控件
	 */
	private void initViews() {
		initPopWindow();// 初始化弹出窗
		topLayout = (RelativeLayout) findViewById(R.id.friend_circle);
		listView = (XListView) findViewById(R.id.friend_list);
		progressBar=(ProgressBar) findViewById(R.id.progressBar);
		// ===============刷新
		
		// ================
		headView = LayoutInflater.from(this).inflate(R.layout.friend_head_item,
				null);
		userPhoto = (ImageView) headView.findViewById(R.id.user_photo);
		x.image().bind(userPhoto, userNow.getImgURL(), options);
		userName = (TextView) headView.findViewById(R.id.user_name);
//		newsNum = (TextView) headView.findViewById(R.id.news_num);

//		favourNum = (TextView) headView.findViewById(R.id.favour_num);

		head_top_bg = (LinearLayout) headView.findViewById(R.id.top_bg);

		listView.addHeaderView(headView);
		adpter = new FriendAdapter(this,window,galleryWindow, new Myflush());// 初始化适配器
		adpter.setUser(userNow);
		adpter.setData(list);

		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);

		listView.setAdapter(adpter);
		listView.setXListViewListener(this);
		listView.setFocusableInTouchMode(false);
		back = (ImageButton) findViewById(R.id.friend_back);
		more = (ImageButton) findViewById(R.id.friend_more);
		back.setVisibility(View.GONE);
		more.setOnClickListener(this);

//		postInitViews();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				
				if (id < 0)
					return;
				
				// 从现有缓存中获取
				Intent intent = new Intent(BaseCircleActivity.this,
						SingleActivity.class);
				intent.putExtra("friend", list.get((int)id));
				startActivityForResult(intent,0);
			}
		});

	}

	/**
	 * 初始化popWindow
	 */
	private void initPopWindow() {

		View view = getLayoutInflater().inflate(R.layout.friend_reply, null);
		window = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		window.setAnimationStyle(R.style.reply_window_anim);

		favuorCancle = (TextView) view.findViewById(R.id.favuor_cancle);
		window.setBackgroundDrawable(getResources().getDrawable(R.color.black));
		window.setOutsideTouchable(true);

		favuorCancle.setOnClickListener(this);

		View editView = getLayoutInflater().inflate(
				R.layout.friend_replay_input, null);
	}

	public void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime("刚刚");
	}

	/**
	 * 刷新
	 */
	@Override
	public void onRefresh() {
		pageNow=1;
		refreshInBackground();
		onLoad();
	}

	/**
	 * 加载更多
	 */
	@Override
	public void onLoadMore() {
		loadMoreInBackground();
		onLoad();
	}

	/*
	 * 后台更新朋友圈
	 */

	public void refreshInBackground() {
		
		final BeanUtil util = BeanUtil.getInstance();
		Map<String, String> map=new HashMap<String, String>();
		map.put("userId",userId);
		//重置当前页
		pageNow=1;
		map.put("pageNow", pageNow+"");
		map.put("pageSize", pageSize+"");
		
		String myUrl=LeUrls.CIRCLE_GETFRIEND;
		
		String perUrl=LeUrls.CIRCLE_GETMYCIRCLE;
		String url="";
		
		if(circleType.equals(LEPOWER_MYCIRCLE)){
			url=myUrl;
		}else if(circleType.equals(LEPOWER_PERSONAL)){
			url=perUrl;
		}
		
		
		NetUtils.get(url, map, new MyCommonCallback<String>(){
			
			@Override
			public void onSuccess(String result) {
				
				System.out.println(result);
				
				newDatas = util.getFriends(context,result);
				onDataArrived(newDatas);
			}
			
			@Override
			public void onError(Throwable throwable, boolean isOnCallback) {
				System.out.println(throwable.toString());
			}
		});
	}

	/**
	 * 设置用户id
	 * @param userId
	 */
	public void setUserId(String userId){
		this.userId=userId;
	}
	
	/*
	 * 后台加载更多
	 */
	public void loadMoreInBackground() {
		
		final BeanUtil util = BeanUtil.getInstance();
		Map<String, String> map=new HashMap<String, String>();
		map.put("userId",userId);
		//设置最大值
		if(pageNow<Integer.MAX_VALUE-100){
			pageNow++;		}
		map.put("pageNow", pageNow+"");
		map.put("pageSize", pageSize+"");
		
		
		String myUrl=LeUrls.CIRCLE_GETFRIEND;
		String perUrl=LeUrls.CIRCLE_GETMYCIRCLE;
		String url="";
		
		if(circleType.equals(LEPOWER_MYCIRCLE)){
			url=myUrl;
		}else if(circleType.equals(LEPOWER_PERSONAL)){
			url=perUrl;
		}
		
		NetUtils.get(url, map, new MyCommonCallback<String>(){
			
			@Override
			public void onSuccess(String result) {
				newDatas = util.getFriends(context,result);
				onLoadMoreArrived(newDatas);
			}
			
			@Override
			public void onError(Throwable throwable, boolean isOnCallback) {
				System.out.println(throwable.toString());
			}
		});
	}

	
	/**
	 * 初始化GalleryWindow
	 */
	public void initGalleryWindow() {
		View gView = LayoutInflater.from(context).inflate(
				R.layout.activity_photo_gallery, null);
		galleryWindow = new PopupWindow(gView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		galleryWindow.setBackgroundDrawable(new BitmapDrawable());
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Log.e("BaseActivity", "onDestroy()");
		
		//取消广播
		unregisterReceiver(replyBackReceiver);
	}
	
	
	private class Myflush implements FlushListView {

		@Override
		public void flush() {

		}

		/**
		 * 点击评论按钮的评论
		 */
		@Override
		public void showDiscussDialog(View v) {
			BaseCircleActivity.this.friend = (FriendAction) v.getTag();			
			System.out.println(TAG + "  : " + friend.getFriendId());
			reply = new Reply();
			reply.setFriendId(friend.getFriendId());
			reply.setUserId(userNow.getUserId());
			reply.setOwnerId(friend.userId);
			reply.setReplyName("");
			reply.setSendName(userNow.getNickName());
			reply.setReplyUserId(friend.getUserId());
			emojWindow.showWindow(topLayout, reply);
			
			if (window.isShowing()) {
				window.dismiss();
			}
		}

		@Override
		public void getReplyByTrendId(Object tag) {

		}

		@Override
		public void getViewPosition(int position) {

		}

		/**
		 * 取消赞
		 */
		@Override
		public void delParise(FriendAction friend) {

			int location = FriendActionTool.getPositionFavours(
					friend.favourList,userNow.getUserId());
			if (location != -1) {
				Favour expireFavour = friend.favourList.get(location);
				friend.favourList.remove(location);
				friend.setFavourName(BeanUtil.getFavouName(friend.favourList));
				adpter.notifyDataSetChanged();
				
				// 更新到服务器
				FriendActionTool.cancelFavour2Server(TAG, expireFavour);
			}

		}

		@Override
		public void showCancle(FriendAction friend) {

		}

		@Override
		public void saveReply(Reply reply) {

		}

		/**
		 * 添加赞
		 */
		@Override
		public void addTrendParise(FriendAction friend) {

			Favour favour = new Favour();
			favour.setFavourName(userNow.getNickName());
			favour.setFavourUId(userNow.getUserId());
			favour.setFriendId(friend.friendId);
			favour.setFriendOwner(friend.userId);
			// 判断原列表是否为空
			if (friend.favourList == null) {
				friend.favourList = new ArrayList<Favour>();
			}
			friend.favourList.add(favour);

			friend.setFavourName(BeanUtil.getFavouName(friend.favourList));
			adpter.notifyDataSetChanged();

			// 跟新赞到服务器
			FriendActionTool.sendFavour2Server(TAG, favour);

		}

		@Override
		public void delTrendById(String trendId) {

		}

		@Override
		public void showDel(TextView view, String userId) {

		}

		@Override
		public void handReply(Reply reply) {

		}

		/**
		 * 点击评论条目的评论，即对评论的评论
		 */
		@Override
		public void replyOtherReplay(View v) {
			BaseCircleActivity.this.friend = (FriendAction) v.getTag();

			Reply replyTemp = (Reply) v.getTag(R.id.a_reply);

			String id = replyTemp.getUserId();
			String name = replyTemp.getSendName();
			reply = new Reply();

			reply.setFriendId(friend.getFriendId());
			reply.setUserId(userNow.getUserId());
			reply.setOwnerId(friend.userId);
			reply.setReplyName(name);
			reply.setSendName(userNow.getNickName());
			reply.setReplyUserId(id);
			
			emojWindow.showWindow(v, reply);
		}

		/**
		 * 跳转到个人主页 ，只有对应用户的动态，无其好友动态。
		 */
		@Override
		public void goToPersonnal(View v) {
			
			FriendAction tempFriend=(FriendAction) v.getTag();
			
			if(circleType.equals("PERSONAL_CIRCLE")){
				if(!tempFriend.getUserId().equals(userNow.getUserId())){
					User tempUser=new User();
					tempUser.setUserId(tempFriend.getUserId());
					tempUser.setNickName(tempFriend.getNickName());
					tempUser.setImgURL(tempFriend.getPhoto());
					Intent intent = new Intent(BaseCircleActivity.this,
							PersonalCircleActivity.class);
					intent.putExtra("user", tempUser);
					startActivity(intent);
				}
			}else{
				
				User tempUser=new User();
				tempUser.setUserId(tempFriend.getUserId());
				tempUser.setNickName(tempFriend.getNickName());
				tempUser.setImgURL(tempFriend.getPhoto());
				Intent intent = new Intent(BaseCircleActivity.this,
						PersonalCircleActivity.class);
				intent.putExtra("user", tempUser);
				startActivity(intent);
				
			}
			
			
			
		}

		/**
		 * 转发  位置和可见性 目前制作测试用
		 */
		@Override
		public void transmitFriend(View v) {

			FriendAction friendT=(FriendAction) v.getTag();
			//添加到后台服务器
			FriendActionTool.transmitFriend(TAG, friendT.friendId, 
					friendT.userId, userNow.getUserId(), 
					"上海", "0");
			
			try {
				Thread.sleep(1000);
				onRefresh();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			Toast.makeText(BaseCircleActivity.this,"转发完成",Toast.LENGTH_SHORT).show();
			
		}
	}
	
	/**
	 * 点击发送按钮后回调
	 */
	public String onSend(Reply reply) {
		
//		System.out.println(TAG+ "presend : " + friend);
		
		this.reply=reply;
		postReplay();
//		System.out.println(TAG+ "postsend : " + friend);
		return "";
	}
	

	/**
	 * 发表评论
	 */
	public void postReplay() {
		
//		System.out.println("TAG : post" + friend /*friend.friendId*/);
		
		if (friend.replyList == null) {
			friend.replyList = new ArrayList<Reply>();
		}

		friend.replyList.add(reply);
		
//		System.out.println("CCC  1: " + reply);
		
		adpter.notifyDataSetChanged();
		listView.setSelection(friend.position);

		// 发送评论到服务器
		FriendActionTool.sendReply2Server(TAG, reply,BASEACTIVITY_REPLY_BACK,this,friend.friendId);
		
		
//		System.out.println("friend id 1: " + friend.friendId);
	}

	/**
	 * 评论插入数据库后将 id更新到对应的评论
	 */
	private BroadcastReceiver replyBackReceiver =new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String friendId=intent.getStringExtra("friendId");
			
			String replyId=intent.getStringExtra("replyId");
			int index=FriendActionTool.getPositionFriendActions(list, friendId);
			
			if(index!=-1){
				FriendAction friendx=list.get(index);
				reply.setId(replyId);
				int indexr=friendx.replyList.indexOf(reply);
				System.out.println("CCC 3: " + reply);
				if(indexr!=-1){
					friendx.replyList.set(indexr, reply);
									
					adpter.notifyDataSetChanged();
				}
			}
			
		}
	};
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.friend_more:
			show();
			break;
		case R.id.smail:
			break;
		default:
			break;
		}

	}

	/**
	 * 发表动态
	 */
	private void show() {
		Intent intent = new Intent();
		intent.setClass(this, PublishedActivity.class);
		Log.d(TAG,"PublishedActivity");
		startActivityForResult(intent, 5);
	}
	
	
	/**
	 * 更新数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(resultCode){
		case RESULT_OK:
			this.friend=(FriendAction) data.getSerializableExtra("friend");
			adpter.notifyDataSetChanged();
			break;
		case 3:
			onRefresh();
		}
		
		//onRefresh();
		
	}
	
	@Override
	public void onBackPressed() {
		if(galleryWindow.isShowing()){
			galleryWindow.dismiss();
		}else{
			super.onBackPressed();
		}
		
	}
	
	@Override
	protected void onPause() {
		if(galleryWindow.isShowing()){
			galleryWindow.dismiss();
		}
		super.onPause();
	}
}

