package com.lepower.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.Context;
import android.text.BoringLayout.Metrics;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lepower.R;
import com.lepower.adapter.ReplyAdapter.ReplayCallback;
import com.lepower.model.FriendAction;
import com.lepower.model.Photos;
import com.lepower.model.Reply;
import com.lepower.model.User;
import com.lepower.utils.BeanUtil;
import com.lepower.utils.FriendActionTool;
import com.lepower.widget.MyDialog;
import com.lepower.widget.MyGridView;
import com.lepower.widget.MyListView;

public class FriendAdapter extends BaseAdapter implements OnClickListener,
		ReplayCallback {

	private Reply reply;
	private FriendAction friend;
	private Context context;
	private PopupWindow window; // 操作菜单窗
	private MyListView replyList; // 评论列表
	private ReplyAdapter adapter; // 评论adapter
	private List<FriendAction> list; // 朋友状态列表

	private GalleryAdapter galleryAdapter;
	private Gallery gallery;
	private TextView propotion;
	
	private static final String TAG="FriendAdapter";
	
	public static final int DELETE_FRIEND = 0;// 删除说说
	public static final int DELETE_REPLY = 1;// 删除评论

	private int flag = DELETE_FRIEND;// 取值为 上述两个值
	private Activity activity;
	private FlushListView flush; // 用于数据更新
	private MyDialog delDialog; // 删除对话框

	private PopupWindow galleryWindow;

	private ImageOptions options;

	private User user;
	
	public FriendAdapter(Context context, PopupWindow window,PopupWindow galleryWindow,
			 FlushListView flush) {
		this.context = context;
		this.window = window;
		this.flush = flush;
		this.galleryWindow=galleryWindow;
		options = new ImageOptions.Builder()
				.setImageScaleType(ScaleType.CENTER_CROP).setIgnoreGif(false)
				.setCircular(true).setRadius(20).setSquare(false)
				.setLoadingDrawableId(R.drawable.loding)
				.setFailureDrawableId(R.drawable.user_logo).build();

		iniDelDialog();
		
		activity=(Activity) context;
		
		View gView=galleryWindow.getContentView();
		
		gallery = (Gallery) gView.findViewById(R.id.photo_gallery);
	}
	
	/**
	 * 设置当前用户
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 绑定数据
	 * 
	 * @param photos
	 * @param current
	 */
	public void bindGalleryData(List<Photos> photos, int current) {

		galleryAdapter = new GalleryAdapter(context, photos);
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
	 * 初始化删除dialog
	 */
	private void iniDelDialog() {
		delDialog = new MyDialog(context);
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
				if (flag == DELETE_FRIEND) {
					// 根据说说的id 后台删除说说
					
					String thisId=friend.friendId;
					
					list.remove(friend);
					//在服务器删除说说
					
					FriendActionTool.deleteFriendAction(TAG, thisId);
					
				} else if (flag == DELETE_REPLY) {
					// 根据 回复的id 删除回复
					
					String replyId=reply.getId();
					
					friend.getReplyList().remove(reply);
					//从服务器删除
					FriendActionTool.deleteReply(TAG, replyId);
					
				}
				
				delDialog.dismiss();
				notifyDataSetChanged();
			}
		});
	}

	public void setData(List<FriendAction> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list == null ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.friend_items, null);
			holder = getHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (list != null) {
			friend = list.get(position);
			friend.position = position;

			bindData(holder);
		}
		return convertView;
	}

	/**
	 * 绑定数据
	 * 
	 * @param holder
	 */

	private List<Reply> replys;// 临时引用

	private void bindData(ViewHolder holder) {
		if (friend == null)
			return;

		x.image().bind(holder.photo, friend.photo, options);

		holder.name.setText(friend.getNickName());

		holder.name.setOnClickListener(this);
		holder.name.setTag(friend);
		holder.photo.setOnClickListener(this);
		holder.photo.setTag(friend);

		
		if(friend.scopeFlag.equals("0")){
			holder.source.setVisibility(View.GONE);
		}else{
			holder.source.setVisibility(View.VISIBLE);
			if(friend.scopeFlag.equals("1")){
				holder.source.setText("转载");
			}else{
				holder.source.setText("来自第三方分享");
			}
		}
		
		
		
		
		// 判断此说说是否有内容
		if (isEmpty(friend.contentText)) {
			holder.contentText.setVisibility(View.GONE);
		} else {
			holder.contentText.setVisibility(View.VISIBLE);
		}
		// 设置说说内容
		holder.contentText.setText(BeanUtil.content2Emoj(context, friend.contentText));

		// 判断是否有分享链接
		if (isEmpty(friend.linkUrl)) {
			holder.linkContent.setVisibility(View.GONE);
		} else {
			holder.linkContent.setVisibility(View.GONE);
			holder.linkIcon.setImageResource(R.drawable.icon);
			holder.linkDescription.setText("我只是做个测试");
		}
		// 说说发表的时间 ： 天 ，小时， 分钟，刚刚
		holder.sendDate.setText(BeanUtil.handTime(friend.sendDate));

		// 判断是否有点赞
		if (isEmpty(friend.favourName)) {
			holder.favourTemp.setVisibility(View.GONE);
		} else {
			holder.favourTemp.setVisibility(View.VISIBLE);
			if (listIsEmpty(friend.replyList)) {// 只有点赞 则不显示分隔线
				holder.praiseLine.setVisibility(View.GONE);
			} else {
				holder.praiseLine.setVisibility(View.VISIBLE);
			}
		}

		// 判断点赞 与回复是否都没有内容
		if (isEmpty(friend.favourName) && listIsEmpty(friend.replyList)) {
			holder.replyContent.setVisibility(View.GONE);
		} else {
			holder.replyContent.setVisibility(View.VISIBLE);
		}

		// 判断是否有回复内容
		if (listIsEmpty(friend.replyList)) {
			holder.replyList.setVisibility(View.GONE);
		} else {
			holder.replyList.setVisibility(View.VISIBLE);
		}
		// 设置点赞人名
		holder.favourName.setText(friend.favourName + "觉得很赞");

		// 分享类型
		holder.shareType.setVisibility(View.GONE);
		// 判断是否有发表图片
		if (listIsEmpty(friend.images)) {
			holder.images.setVisibility(View.GONE);
		} else {
			holder.images.setVisibility(View.VISIBLE);
			final List<Photos> list = getPhotos(friend.images);
			// 这里自己创造图片 用于测试

			// final ArrayList<Photos> list = getPhotos();
			if (list != null) {
				holder.images.setVisibility(View.VISIBLE);
				holder.images
						.setAdapter(new ImageAdapter(context, list, false));
				holder.images.setOnItemClickListener(new OnItemClickListener() {// 设置监听器
							// ，点击进入大图
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								bindGalleryData(list, position);
								galleryWindow.showAtLocation(view,
										Gravity.CENTER, 0, 0);
							}
						});
			}
		}
		adapter = new ReplyAdapter(context, this, friend);
		replys = friend.replyList;
		// =========判断是否有更多回复=========
		checkMoreReply(holder);
		// ==============================

		// ==================评论==================

		adapter.setData(replys);
		holder.replyList.setAdapter(adapter);
		holder.replyList.setTag(replys);
		holder.replyList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				replys = (List<Reply>) parent.getTag();
				reply = replys.get(position);

				// flush.handReply(replys,position);
				/**
				 * 获取评论的listView
				 */
				// flush.handReply(MyListView(parent),position);

				flush.handReply(reply);// 处理评论
			}
		});
		// ==================评论end==================
		/**
		 * 首先判断此朋友圈是否为登陆用户自己
		 */
		if (friend.userId.equals(user.getUserId())) {
			holder.delText.setVisibility(View.VISIBLE);
			holder.delText.setTag(friend.id);
			holder.delText.setOnClickListener(this);
			holder.delText.setTag(friend);
		} else {
			holder.delText.setVisibility(View.GONE);
		}
		// flush.showDel(holder.delText, friend.userId);

		// 根据这个按钮 可以获取对应的条目位置信息
		holder.replyIcon.setTag(friend);
		holder.replyIcon.setOnClickListener(this);
	}
	
	/**
	 * 显示操作窗体
	 * 
	 * @param view
	 */
	private void showDialog(View view) {
		
		int width = view.getWidth();
		friend = (FriendAction) view.getTag();
		flush.showCancle(friend);// 显示或者隐藏赞
		int[] location = new int[2];
		view.getLocationInWindow(location);
		int x = location[0] - dip2px(context, width) - width+100;
		int y = location[1] - 20;
		View v = window.getContentView();
		TextView discuss = (TextView) v.findViewById(R.id.discuss);
		TextView favuor = (TextView) v.findViewById(R.id.favuor);
		TextView favuorCancle = (TextView) v.findViewById(R.id.favuor_cancle);
		TextView transmit = (TextView) v.findViewById(R.id.transmit);

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

		// 取消赞
		favuorCancle.setOnClickListener(this);
		// 评论
		discuss.setOnClickListener(this);
		// 赞
		favuor.setOnClickListener(this);
		// 转发
		transmit.setOnClickListener(this);
		// 注意这里Tag有位置信息
		discuss.setTag(view.getTag());
		favuor.setTag(view.getTag());
		favuorCancle.setTag(view.getTag());
		transmit.setTag(view.getTag());

		DisplayMetrics outMetrics=new DisplayMetrics();
		
		activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		
		window.showAtLocation(view, Gravity.NO_GRAVITY, outMetrics.widthPixels, y);
	}

	//
	private void checkMoreReply(ViewHolder holder) {
		int replyCount = friend.replyCount;
		if (replyCount > 10 && replyCount != friend.replyList.size()) {// 最多显示10条
			holder.replyMore.setVisibility(View.VISIBLE);
			holder.replyMore.setTag(friend.friendId);
			holder.replyMore.setOnClickListener(this);
		} else {
			holder.replyMore.setVisibility(View.GONE);
		}
	}

	private class ViewHolder {
		public TextView shareType;// 分享类型
		public ImageView photo;// 头像
		public TextView name;// 名字
		public TextView contentText;// 文字内容
		public ImageView linkIcon;// 链接图标
		public TextView linkDescription;// 链接描述
		public TextView sendDate;// 发表时间
		public TextView delText;// 删除文字
		public ImageButton replyIcon;// 回复icon
		public TextView favourName;// 点赞的名字
		public MyListView replyList;// 回复的listView
		public LinearLayout linkContent;// 链接内容
		public LinearLayout replyContent;// 回复布局
		public LinearLayout favourTemp;// 点赞布局
		public TextView replyMore;// 更多回复
		public MyGridView images;// 发表的图片 最多8张

		public TextView source;  //说说来源
		public View praiseLine;// 点赞下面的线
	}

	/**
	 * 初始化ViewHolder
	 * 
	 * @param convertView
	 * @return
	 */
	private ViewHolder getHolder(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.source=(TextView) convertView.findViewById(R.id.source);
		holder.shareType = (TextView) convertView.findViewById(R.id.share_type);
		holder.photo = (ImageView) convertView.findViewById(R.id.photo);
		holder.name = (TextView) convertView.findViewById(R.id.name);
		holder.contentText = (TextView) convertView
				.findViewById(R.id.content_text);
		holder.linkIcon = (ImageView) convertView.findViewById(R.id.link_icon);
		holder.linkDescription = (TextView) convertView
				.findViewById(R.id.link_description);
		holder.sendDate = (TextView) convertView.findViewById(R.id.date);
		holder.delText = (TextView) convertView.findViewById(R.id.delete);
		holder.replyIcon = (ImageButton) convertView
				.findViewById(R.id.reply_icon);
		holder.favourName = (TextView) convertView
				.findViewById(R.id.favuor_name);
		holder.replyList = (MyListView) convertView
				.findViewById(R.id.reply_list);
		holder.linkContent = (LinearLayout) convertView
				.findViewById(R.id.link_content);
		holder.replyContent = (LinearLayout) convertView
				.findViewById(R.id.reply_content);
		holder.favourTemp = (LinearLayout) convertView
				.findViewById(R.id.favour_temp);
		holder.replyMore = (TextView) convertView.findViewById(R.id.reply_more);
		holder.images = (MyGridView) convertView
				.findViewById(R.id.image_content);
		holder.praiseLine = convertView.findViewById(R.id.praise_line);
		return holder;
	}

	/**
	 * 处理图片数据
	 * 
	 * @param photo
	 * @return
	 */
	private List<Photos> getPhotos(List<String> images) {

		return BeanUtil.getPhotos(images);

	}

	/**
	 * 回调接口 实现数据刷新
	 * 
	 * @author jiangyue
	 * 
	 */
	public interface FlushListView {
		public void flush();// 刷新数据

		public void showDiscussDialog(View v);// 显示评论对话框

		public void getReplyByTrendId(Object tag);// 根据动态id获取评论回复

		public void getViewPosition(int position);

		public void delParise(FriendAction friend);// 删除点赞

		public void showCancle(FriendAction friend);// 显示或者隐藏赞

		public void saveReply(Reply reply);// 保存回复信息

		public void addTrendParise(FriendAction friend);// 添加点赞

		public void delTrendById(String trendId);// 根据id删除动态

		public void showDel(TextView view, String userId);// 显示删除按钮

		public void handReply(Reply reply);// 处理评论

		public void replyOtherReplay(View v);

		public void goToPersonnal(View v);
		
		public void transmitFriend(View v);
	}

	/**
	 * 判断指定的字符串是否是 正确的（不为“”、null 、“null”）
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

	private void toast(String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	public int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reply_icon:// 显示评论窗口
			// 测试点击评论按钮 何其对应的item位置 tag里有friend
			showDialog(v);
			break;
		case R.id.delete:// 删除 说说
			friend = (FriendAction) v.getTag();
			flag = DELETE_FRIEND;
			delDialog.show();
			break;
		case R.id.discuss:// 评论 说说
			flush.showDiscussDialog(v);
			break;
		case R.id.favuor_cancle:// 取消点赞
			friend = (FriendAction) v.getTag();
			flush.delParise(friend);
			window.dismiss();
			break;
		case R.id.favuor:// 点赞 说说
			friend = (FriendAction) v.getTag();
			flush.addTrendParise(friend);
			window.dismiss();
			break;
		case R.id.transmit://转发
			flush.transmitFriend(v);
			window.dismiss();
			break;
		case R.id.reply_more:// 点赞 说说
			flush.getReplyByTrendId(v.getTag());
			break;
		case R.id.photo:
		case R.id.name:
			flush.goToPersonnal(v);
			break;
		default:
			break;
		}
	}

	// 处理评论的评论
	@Override
	public void onReplayClick(Reply reply, FriendAction friend) {
		// toast(friend.getName()+": "+ friend.position);
		this.reply = reply;
		this.friend = friend;
		// 删除自己发出的评论
		if (reply.getUserId().equals(user.getUserId())) {
			flag = DELETE_REPLY;
			delDialog.show();
		} else {
			// 对评论的评论
			View v = new View(context);
			v.setTag(R.id.a_reply, reply);
			v.setTag(friend);
			flush.replyOtherReplay(v);
		}
	}

	/**
	 * 用户点击 评论中的用户名时跳转到他的主页
	 */
	@Override
	public void onUserNameClick(String userId) {
		View v = new View(context);
		
		int index=userId.indexOf(":");
		FriendAction tempFriend=new FriendAction();
		tempFriend.setUserId(userId.substring(0, index));
		tempFriend.setNickName(userId.substring(index+1, userId.length()));
		
		v.setTag(tempFriend);
		flush.goToPersonnal(v);
	}
}
