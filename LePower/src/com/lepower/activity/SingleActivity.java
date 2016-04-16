package com.lepower.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lepower.R;
import com.lepower.adapter.FriendAdapter;
import com.lepower.adapter.GalleryAdapter;
import com.lepower.adapter.ImageAdapter;
import com.lepower.adapter.ReplyAdapter;
import com.lepower.adapter.ReplyAdapter.ReplayCallback;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.model.Favour;
import com.lepower.model.FriendAction;
import com.lepower.model.Photos;
import com.lepower.model.Reply;
import com.lepower.model.User;
import com.lepower.utils.BeanUtil;
import com.lepower.utils.EmojWindow;
import com.lepower.utils.EmojWindow.ReplySend;
import com.lepower.utils.FriendActionTool;
import com.lepower.widget.MyDialog;
import com.lepower.widget.MyGridView;
import com.lepower.widget.MyListView;

/**
 * 获取单条说说的id 并将此说说和和其评论一同显示
 * 
 * @author Administrator
 */

public class SingleActivity extends Activity implements ReplayCallback,
		OnClickListener, ReplySend {

	private static final String TAG = "SingleActivity";

	// 评论返回时的广播
	private static final String SINGLE_REPLY_BACK = "SingleActivity.ReplyBack";

	private TextView shareType;// 分享类型
	private ImageView photo;// 头像
	private TextView name;// 名字
	private TextView contentText;// 文字内容
	private ImageView linkIcon;// 链接图标
	private TextView linkDescription;// 链接描述
	private TextView sendDate;// 发表时间
	private TextView delText;// 删除文字
	private ImageButton replyIcon;// 回复icon
	private TextView favourName;// 点赞的名字
	private MyListView replyList;// 回复的listView
	private LinearLayout linkContent;// 链接内容
	private LinearLayout replyContent;// 回复布局
	private LinearLayout favourTemp;// 点赞布局
	private TextView replyMore;// 更多回复
	private MyGridView images;// 发表的图片 最多8张
	private ImageButton back;

	private View praiseLine;// 点赞下面的线

	private RelativeLayout topLayout;

	private PopupWindow window;
	private TextView discuss;// 评论
	private TextView favuor;// 赞
	private TextView transmit; // 转发
	private TextView favuorCancle;// 取消赞
	private EditText replyEdit;
	private Button sendBtn;
	private MyDialog delDialog; // 删除对话框
	private FriendAction friend;
	private Reply reply;
	private ReplyAdapter adapter;
	private List<Reply> replys;

	private TextView reply_more;

	private Gallery gallery;
	private TextView propotion;
	private PopupWindow galleryWindow;
	private GalleryAdapter galleryAdapter;

	private ImageOptions options;

	private final String PERSONAL_DATA_COME = "SingleActivity.data.come";

	private Context context;

	private EmojWindow emojWindow;

	private int flag = FriendAdapter.DELETE_FRIEND;

	private User user;
	private UserDaoImpl userDao;

	private TextView source;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_friend_activity);

		context = this;
		userDao = new UserDaoImpl();
		emojWindow = EmojWindow.getInstance(this);
		emojWindow.setReplySend(SingleActivity.this);

		options = new ImageOptions.Builder()
				.setImageScaleType(ScaleType.CENTER_CROP).setIgnoreGif(false)
				.setCircular(true).setRadius(20).setSquare(false)
				.setLoadingDrawableId(R.drawable.loding)
				.setFailureDrawableId(R.drawable.user_logo).build();

		IntentFilter filter = new IntentFilter(SINGLE_REPLY_BACK);
		registerReceiver(replyBackReceiver, filter);

		Log.e("SingleActivity", "onCreate()");

		reply = new Reply();
		initView();

		// 访问服务器 获取动态详情 ，暂时废弃
		setData();
	}

	/**
	 * 当评论成功将 评论的id 更新到当前列表中
	 */
	private BroadcastReceiver replyBackReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String replyId = intent.getStringExtra("replyId");
			reply.setId(replyId);
			// 更新这条评论
			int index = friend.replyList.indexOf(reply);
			if (index != -1) {
				friend.replyList.set(index, reply);
				adapter.notifyDataSetChanged();
			}
		}
	};

	public void initView() {

		topLayout = (RelativeLayout) findViewById(R.id.friend_circle);
		source=(TextView) findViewById(R.id.source);
		shareType = (TextView) findViewById(R.id.share_type);
		photo = (ImageView) findViewById(R.id.photo);
		name = (TextView) findViewById(R.id.name);
		contentText = (TextView) findViewById(R.id.content_text);
		linkIcon = (ImageView) findViewById(R.id.link_icon);
		linkDescription = (TextView) findViewById(R.id.link_description);
		sendDate = (TextView) findViewById(R.id.date);
		delText = (TextView) findViewById(R.id.delete);
		reply_more = (TextView) findViewById(R.id.reply_more);
		reply_more.setVisibility(View.GONE);
		delText.setOnClickListener(this);
		replyIcon = (ImageButton) findViewById(R.id.reply_icon);
		replyIcon.setOnClickListener(this);
		favourName = (TextView) findViewById(R.id.favuor_name);
		replyList = (MyListView) findViewById(R.id.reply_list);
		linkContent = (LinearLayout) findViewById(R.id.link_content);
		replyContent = (LinearLayout) findViewById(R.id.reply_content);
		favourTemp = (LinearLayout) findViewById(R.id.favour_temp);
		replyMore = (TextView) findViewById(R.id.reply_more);
		images = (MyGridView) findViewById(R.id.image_content);
		praiseLine = findViewById(R.id.praise_line);

		back = (ImageButton) findViewById(R.id.btn_details_back);
		back.setOnClickListener(this);

		initPopWindow();
		initGalleryWindow();

	}

	/**
	 * 放大图片
	 */
	public void initGalleryWindow() {
		View gView = LayoutInflater.from(this).inflate(
				R.layout.activity_photo_gallery, null);
		gallery = (Gallery) gView.findViewById(R.id.photo_gallery);
		galleryWindow = new PopupWindow(gView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		galleryWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * 绑定数据
	 * 
	 * @param photos
	 * @param current
	 */
	public void bindGalleryData(List<Photos> photos, int current) {

		galleryAdapter = new GalleryAdapter(this, photos);
		gallery.setAdapter(galleryAdapter);
		gallery.setSelection(current);

		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				galleryWindow.dismiss();
			}

		});
	}

	/**
	 * 初始化popWindow
	 */
	private void initPopWindow() {

		iniDelDialog();

		View view = getLayoutInflater().inflate(R.layout.friend_reply, null);

		discuss = (TextView) view.findViewById(R.id.discuss);
		favuor = (TextView) view.findViewById(R.id.favuor);
		favuorCancle = (TextView) view.findViewById(R.id.favuor_cancle);
		transmit = (TextView) view.findViewById(R.id.transmit);

		// 取消赞
		favuorCancle.setOnClickListener(this);
		// 评论
		discuss.setOnClickListener(this);
		// 赞
		favuor.setOnClickListener(this);
		// 转发
		transmit.setOnClickListener(this);

		window = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		window.setAnimationStyle(R.style.reply_window_anim);

		window.setBackgroundDrawable(getResources().getDrawable(R.color.black));
		window.setOutsideTouchable(true);

	}

	/**
	 * 显示评论，点赞窗体
	 * 
	 * @param view
	 */
	private void showDialog(View view) {
		int width = view.getWidth();
		int[] location = new int[2];
		view.getLocationInWindow(location);
		int x = location[0] - dip2px(this, width) - width - 80;
		int y = location[1] - 20;

		if (isEmpty(friend.getFavourName())) {
			favuor.setVisibility(View.VISIBLE);
			favuorCancle.setVisibility(View.GONE);
		} else {
			if (FriendActionTool.getPositionFavours(friend.favourList,
					user.getUserId()) != -1) {
				favuorCancle.setVisibility(View.VISIBLE);
				favuor.setVisibility(View.GONE);
			} else {
				favuor.setVisibility(View.VISIBLE);
				favuorCancle.setVisibility(View.GONE);
			}

		}
		DisplayMetrics outMetrics = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		window.showAtLocation(view, Gravity.NO_GRAVITY, outMetrics.widthPixels, y);
	}

	private void toast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public void setData() {

		Intent intent = getIntent();
		friend = (FriendAction) intent.getSerializableExtra("friend");

		user = new User();
		user = userDao.getUserNow();

		if (friend == null)
			return;
		name.setText(friend.nickName);

		x.image().bind(photo, friend.photo, options);

		// 判断此说说是否有内容
		if (isEmpty(friend.contentText)) {
			contentText.setVisibility(View.GONE);
		} else {
			contentText.setVisibility(View.VISIBLE);
		}
		// 设置说说内容
		contentText.setText(BeanUtil.content2Emoj(this, friend.contentText));

		
		if(friend.scopeFlag.equals("0")){
			source.setVisibility(View.GONE);
		}else{
			source.setVisibility(View.VISIBLE);
			if(friend.scopeFlag.equals("1")){
				source.setText("转载");
			}else{
				source.setText("来自第三方分享");
			}
		}
		
		// 判断是否有分享链接
		if (isEmpty(friend.linkUrl)) {
			linkContent.setVisibility(View.GONE);
		} else {
			linkContent.setVisibility(View.GONE);
			linkIcon.setImageResource(R.drawable.icon);
			linkDescription.setText("我只是做个测试");
		}
		// 说说发表的时间 ： 天 ，小时， 分钟，刚刚
		sendDate.setText(BeanUtil.handTime(friend.sendDate));

		judgeFavourAndReply();

		// 分享类型
		shareType.setVisibility(View.GONE);

		// 判断是否有发表图片
		if (listIsEmpty(friend.images)) {
			images.setVisibility(View.GONE);
		} else {
			images.setVisibility(View.VISIBLE);
			final List<Photos> list = getPhotos(friend.images);

			// final List<Photos> list=getPhotos();

			if (list != null) {
				images.setAdapter(new ImageAdapter(this, list, false));
				images.setOnItemClickListener(new OnItemClickListener() {// 设置监听器
					// ，点击进入大图
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						bindGalleryData(list, position);
						galleryWindow
								.showAtLocation(view, Gravity.CENTER, 0, 0);

					}
				});
			}
		}
		adapter = new ReplyAdapter(this, this, friend);
		adapter.setData(friend.replyList);
		replyList.setAdapter(adapter);
		replyList.setTag(replys);

		// ==================评论end==================
		/**
		 * 首先判断此朋友圈是否为登陆用户自己
		 */

		if (friend.userId.equals(user.getUserId())) {
			delText.setVisibility(View.VISIBLE);
			delText.setTag(friend.id);
			delText.setOnClickListener(this);
		} else {
			delText.setVisibility(View.GONE);
		}

		photo.setTag(friend);
		photo.setOnClickListener(this);

		name.setTag(friend);
		name.setOnClickListener(this);

	}

	/**
	 * 添加图片
	 * 
	 * @return
	 */
	public List<Photos> getPhotos(List<String> images) {

		return BeanUtil.getPhotos(images);

	}

	public void judgeFavourAndReply() {

		// 判断是否有点赞
		if (isEmpty(friend.favourName)) {
			favourTemp.setVisibility(View.GONE);
		} else {
			favourTemp.setVisibility(View.VISIBLE);
			if (listIsEmpty(friend.replyList)) {// 只有点赞 则不显示分隔线
				praiseLine.setVisibility(View.GONE);
			} else {
				praiseLine.setVisibility(View.VISIBLE);
			}
		}

		// 判断点赞 与回复是否都没有内容
		if (isEmpty(friend.favourName) && listIsEmpty(friend.replyList)) {
			replyContent.setVisibility(View.GONE);
		} else {
			replyContent.setVisibility(View.VISIBLE);
		}

		// 判断是否有回复内容
		if (listIsEmpty(friend.replyList)) {
			replyList.setVisibility(View.GONE);
		} else {
			replyList.setVisibility(View.VISIBLE);
		}

		// 设置点赞人名
		favourName.setText(friend.favourName + "觉得很赞");
	}

	/**
	 * 判断指定的字符串是否是 正确的（不为""、null 、"null"）
	 * 
	 * @param str
	 * @return
	 */
	private boolean isEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str)
				&& !"[]".equals(str))
			return false;
		return true;
	}

	/**
	 * 判断集合是否是需要的格式 （不为null size>0）
	 * 
	 * @param list
	 * @return
	 */
	private boolean listIsEmpty(List list) {
		if (list != null && list.size() > 0)
			return false;
		return true;
	}

	/**
	 * 初始化删除dialog
	 */
	private void iniDelDialog() {
		delDialog = new MyDialog(this);
		TextView titile = delDialog.getTitle();
		TextView content = delDialog.getContent();
		Button left = delDialog.getLeft();
		Button right = delDialog.getRight();
		titile.setText("提示");
		content.setText("确定删除吗?");
		left.setText("取消");
		right.setText("确定");

		left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				delDialog.dismiss();
			}
		});

		right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (flag == FriendAdapter.DELETE_FRIEND) {
					// 根据说说的id 后台删除说说
					FriendActionTool.deleteFriendAction(TAG, friend.friendId);

					// 销毁此Activity 返回朋友圈列表
					finish();

				} else if (flag == FriendAdapter.DELETE_REPLY) {
					// 根据 回复的id 删除回复

					String replyId = reply.getId();

					friend.getReplyList().remove(reply);
					judgeFavourAndReply();
					// 刷新后重新显示
					adapter.notifyDataSetChanged();
					// 从服务器删除
					FriendActionTool.deleteReply(TAG, replyId);
				}
				delDialog.dismiss();

			}
		});
	}

	/**
	 * 
	 */
	public void postReply() {
		// String text = replyEdit.getText().toString();
		// reply.setContent(text);
		// System.out.println("TAG : post" + friend /*friend.friendId*/);
		if (friend.replyList == null) {
			friend.replyList = new ArrayList<Reply>();
			adapter.setData(friend.replyList);
		}

		friend.replyList.add(reply);
		judgeFavourAndReply();
		adapter.notifyDataSetChanged();

		// 发送评论到服务器
		FriendActionTool.sendReply2Server(TAG, reply, SINGLE_REPLY_BACK, this,
				null);
	}

	@Override
	public void onReplayClick(Reply reply, FriendAction friend) {
		// 刪除
		if (reply.getUserId().equals(user.getUserId())) {
			this.reply = reply;
			flag = FriendAdapter.DELETE_REPLY;
			delDialog.show();

		} else {// 评论
			this.reply = new Reply();
			this.reply.setFriendId(friend.friendId);
			this.reply.setOwnerId(friend.userId);
			this.reply.setSendName(user.getNickName());
			this.reply.setUserId(user.getNickName());
			this.reply.setReplyName(reply.getSendName());
			this.reply.setReplyUserId(reply.getUserId());

			emojWindow.showWindow(photo, reply);

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.reply_icon: // 弹出评论 ，赞选择框
			showDialog(v);
			break;

		case R.id.delete:// 删除说说
			flag = FriendAdapter.DELETE_FRIEND;
			delDialog.show();
			break;
		case R.id.discuss:// 评论 弹出评论窗体
			this.reply = new Reply();
			reply.setFriendId(friend.getFriendId());
			reply.setUserId(user.getUserId());
			reply.setOwnerId(friend.userId);
			reply.setReplyName("");
			reply.setSendName(user.getNickName());
			reply.setReplyUserId(friend.getUserId());

			emojWindow.showWindow(photo, reply);
			if (window.isShowing()) {
				window.dismiss();
			}
			// showDiscuss();
			break;
		case R.id.favuor:// 点赞
			addTrendParise();
			judgeFavourAndReply();
			window.dismiss();
			break;
		case R.id.favuor_cancle:// 取消赞
			delParise();
			judgeFavourAndReply();
			window.dismiss();
			break;
		case R.id.transmit:// 转发
			transmitFriend();
			window.dismiss();
			break;
		case R.id.btn_details_back:
			returnFriend();

			finish();

			break;
		case R.id.photo:// 点击头像 和昵称进入个人主页
		case R.id.name:
			goToPersonnal(v);
			break;
		default:
			break;
		}

	}

	/*
	 * 转发
	 */
	public void transmitFriend() {

		// 添加到后台服务器
		FriendActionTool.transmitFriend(TAG, friend.friendId, friend.userId,
				user.getUserId(), "上海", "0");

		Toast.makeText(this, "转发完成", Toast.LENGTH_SHORT).show();

	}

	/**
	 * 跳转到个人主页 ，只有对应用户的动态，无其好友动态。
	 */
	public void goToPersonnal(View v) {
		Intent intent = new Intent(this, PersonalCircleActivity.class);
		intent.putExtra("user",user);
		startActivity(intent);
	}

	/**
	 * 点赞
	 */
	private void addTrendParise() {

		Favour favour = new Favour();
		favour.setFavourName(user.getNickName());
		favour.setFavourUId(user.getUserId());
		favour.setFriendId(friend.friendId);
		favour.setFriendOwner(friend.userId);
		// 判断原列表是否为空
		if (friend.favourList == null) {
			friend.favourList = new ArrayList<Favour>();
		}
		friend.favourList.add(favour);

		friend.setFavourName(BeanUtil.getFavouName(friend.favourList));

		// 跟新赞到服务器
		FriendActionTool.sendFavour2Server(TAG, favour);
	}

	/**
	 * 取消赞
	 */
	private void delParise() {
		int location = FriendActionTool.getPositionFavours(friend.favourList,
				user.getUserId());
		if (location != -1) {
			Favour expireFavour = friend.favourList.get(location);
			friend.favourList.remove(location);
			friend.setFavourName(BeanUtil.getFavouName(friend.favourList));
			// 更新到服务器
			FriendActionTool.cancelFavour2Server(TAG, expireFavour);
		}
	}

	/**
	 * 销毁时取消广播
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.e("SingleActivity", "onDestroy()");
		unregisterReceiver(replyBackReceiver);
	}

	/**
	 * 点击评论中的用户名跳转到个人主页
	 */
	@Override
	public void onUserNameClick(String userId) {
		Intent intent = new Intent(this, PersonalCircleActivity.class);
		intent.putExtra("userId", userId);
		startActivity(intent);
	}

	/**
	 * 评论框返回
	 */

	@Override
	public String onSend(Reply reply) {
		this.reply = reply;
		postReply();
		return "";
	}

	@Override
	public void onBackPressed() {
		if (galleryWindow.isShowing()) {
			galleryWindow.dismiss();
		} else {
			// 按返回键把更新的数据返回
			returnFriend();
			super.onBackPressed();
		}
	}

	/**
	 * 将最新数据返回
	 */
	private void returnFriend() {
		Intent intent = new Intent();
		intent.putExtra("friend", friend);
		int ss = RESULT_CANCELED;
		setResult(RESULT_OK, intent);
	}

	@Override
	protected void onPause() {
		if (galleryWindow.isShowing()) {
			galleryWindow.dismiss();
		}
		super.onPause();
	}

}
