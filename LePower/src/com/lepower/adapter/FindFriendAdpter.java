package com.lepower.adapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lepower.R;
import com.lepower.model.FriendInfo;
import com.lepower.utils.AsyncTaskImageLoad;
import com.lepower.utils.FriendInfo_JsonParse;
import com.lepower.utils.HttpUtils;
import com.lepower.utils.LeUrls;
import com.lepower.utils.NetWorkUtil;
import com.lepower.utils.ToastUtils;
import com.lepower.widget.RoundImageView;

/**
 * 已成功测试
 * @author menhoa
 *
 */
public class FindFriendAdpter extends BaseAdapter{
	
	List<FriendInfo> friendInfos = new ArrayList<FriendInfo>();
	
	FriendInfo friendInfo;
	
	Context context;
	
	String userId;
	
	String resCode;
	
	String url1 = "http://192.168.1.100/LeSports/friend/addFriend?userId=";//关注好友
	
	String url2 = "http://192.168.1.100/LeSports/friend/deleteFriend?userId=";//取消关注
	
	//应该还有把用户id传进来
	public FindFriendAdpter(List<FriendInfo> friendInfos, Context context,String userId) {
		super();
		this.friendInfos = friendInfos;
		this.context = context;
		this.userId = userId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return friendInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return friendInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		friendInfo = friendInfos.get(position);
		Log.d("MainActivity", friendInfo.toString());
		final ViewHolder holder;
		if (convertView != null) {
			holder = (ViewHolder)convertView.getTag();
		}else {
			convertView = View.inflate(context, R.layout.fensi_item, null);
			holder = new ViewHolder();
			holder.tv_nickName = (TextView)convertView.findViewById(R.id.tv_nickName);
			holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
			holder.tv_age = (TextView)convertView.findViewById(R.id.tv_age);
			holder.iv_friend = (RoundImageView)convertView.findViewById(R.id.iv_friend);
			holder.iv_guanzhu = (ImageView)convertView.findViewById(R.id.iv_guanzhu);
			holder.pb = (ProgressBar)convertView.findViewById(R.id.progress_bar);
			convertView.setTag(holder);
		}
		//加载图片资源
		LoadImage(holder.iv_friend,friendInfo.getImgURL());
		holder.tv_nickName.setText(friendInfo.getNickName());
		holder.tv_age.setText(friendInfo.getAge()+"");
		holder.tv_sex.setText(friendInfo.getSex());
		if ("0".equals(friendInfo.getFlag())) { //关注状态
			holder.iv_guanzhu.setImageResource(R.drawable.left_icon_follow_down);
		}else { //不关注状态
			holder.iv_guanzhu.setImageResource(R.drawable.left_icon_follow);
		}
		holder.iv_guanzhu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				friendInfo = friendInfos.get(position); //通过点击的position来获取信息
				Log.d("heheda", "friendInfo"+friendInfo.getUserId());
				String uid = friendInfo.getUserId();
				Log.d("heheda", "friendInfo.getFlag()="+friendInfo.getFlag());
				//如果网络可用
				if (NetWorkUtil.isNetWork(context)) {
					holder.iv_guanzhu.setVisibility(View.GONE);
					holder.pb.setVisibility(View.VISIBLE);
					if ("0".equals(friendInfo.getFlag())) {
						final String url22 = LeUrls.DELETE_FRIEND + userId + "&friendId=" + friendInfo.getUserId();
						Observable.create(new Observable.OnSubscribe<String>() {

							@Override
							public void call(Subscriber<? super String> subscriber) {
								// TODO Auto-generated method stub
								Log.d("heheda", Thread.currentThread().getName());
								String result = null;
								result = HttpUtils.getResult(url22);
								subscriber.onNext(result);
							}
						}).subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Subscriber1());
					}else if ("1".equals(friendInfo.getFlag())) {
						final String url11 = LeUrls.ADD_FRIEND + userId + "&friendId=" + friendInfo.getUserId();
						Log.d("heheda", "url1="+url1);
						Observable.create(new Observable.OnSubscribe<String>() {

							@Override
							public void call(Subscriber<? super String> subscriber) {
								// TODO Auto-generated method stub
								Log.d("heheda", Thread.currentThread().getName());
								String result = null;
								result = HttpUtils.getResult(url11);
								subscriber.onNext(result);
							}
						}).subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Subscriber1());
					}
				}else {  //网络不可用的状态
					ToastUtils.showToast(context, "网络不可连接");
				}
			}

			/**
			 * 关注成功了
			 */
			private void Success(){
				Log.d("heheda", "关注成功");
				holder.pb.setVisibility(View.GONE);//进度条隐藏
				holder.iv_guanzhu.setImageResource(R.drawable.left_icon_follow_down);
				holder.iv_guanzhu.setVisibility(View.VISIBLE);	//取消关注按钮显示
				friendInfo.setFlag("0");//将标志位设置为关注状态
			}
			
			/**
			 * 取消关注了
			 */
			private void Fail(){
				Log.d("heheda", "关注失败");
				holder.pb.setVisibility(View.GONE);
				holder.iv_guanzhu.setImageResource(R.drawable.left_icon_follow);
				holder.iv_guanzhu.setVisibility(View.VISIBLE);
				friendInfo.setFlag("1");//将标志位设置为取消关注状态
			}
			
			final class Subscriber1 implements rx.Observer<String> {
			
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
							Success();
						}else if ("1".equals(resCode)) {
							Fail();
						}
					}
			}
			
		});	
		return convertView;
	}
	
	//加载头像
	private void LoadImage(RoundImageView iv_friend, String path) {
		//异步加载图片资源
		AsyncTaskImageLoad async = new AsyncTaskImageLoad(iv_friend);
		//执行异步加载,并把图片的路径传送过去
		async.execute(path);
	}

	static class ViewHolder{
		TextView tv_nickName;
		TextView tv_sex;
		TextView tv_age;
		RoundImageView iv_friend;
		ImageView iv_guanzhu;
		ProgressBar pb;
	}
}

