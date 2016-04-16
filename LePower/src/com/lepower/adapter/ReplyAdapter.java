package com.lepower.adapter;


import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lepower.R;
import com.lepower.model.FriendAction;
import com.lepower.model.Reply;
import com.lepower.utils.BeanUtil;

/**
 * 回复适配器
 * 
 * @author 
 * 
 */
public class ReplyAdapter extends BaseAdapter{

	private Context context;

	private List<Reply> list;

	private Reply reply;

	private ReplayCallback callback;
	
	private FriendAction friend;// 当用户点击评论内容时 ，可以记录此评论属于哪个说说，或在说说listView中的位置,
	                      //当callback回调时，将它转给回调者，详见MyContentSpan类的 onClick()方法。
	public ReplyAdapter(Context context,ReplayCallback callback,FriendAction friend) {
		this.context = context;
		this.callback=callback;
		this.friend=friend;
	}
	
	public void setCallback(ReplayCallback callback) {
		this.callback = callback;
	}
	
	public void setFriend(FriendAction friend) {
		this.friend = friend;
	}
	
	public FriendAction getFriend() {
		return friend;
	}

	public void setData(List<Reply> list) {
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
					R.layout.friend_reply_item, null);
			holder = getViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (list != null) {
			reply = list.get(position);
			bindData(holder);
		}
		return convertView;
	}

	/**
	 * 绑定数据
	 * 
	 * @param holder
	 */
	private void bindData(ViewHolder holder) {
		if (reply == null)
			return;
		//评论的头部
		String userSend=getHTML(reply.getUserId(), reply.getSendName());
		String userReply="";
		// 对评论者的评论 
		if(!reply.getUserId().equals(reply.getReplyUserId())){
			if(reply.getReplyUserId()!=null&&reply.getReplyName()!=null){
				userReply=getHTML(reply.getReplyUserId(), reply.getReplyName());
				userReply="回复"+userReply;
			}
		}else{
			userReply+="回复";
		}
		
		String user=userSend+userReply;
		String content=":"+reply.getContent();
		
		Spannable userLink=(Spannable) Html.fromHtml(user);
		int end=userLink.length();
		//用户名链接
		URLSpan[] urls=userLink.getSpans(0, end, URLSpan.class);
		
		SpannableStringBuilder replayHead=new SpannableStringBuilder(userLink);
		replayHead.clearSpans();
		
		// 每一个评论者作为一部分
		for(URLSpan url:urls){
			MyURLSpan span=new MyURLSpan(url);
			replayHead.setSpan(span, userLink.getSpanStart(url),userLink.getSpanEnd(url), 
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		//评论的内容部分当做另一部分
		
//		replyContent.clearSpans();
		SpannableStringBuilder replyContent=new SpannableStringBuilder(BeanUtil.content2Emoj(context, content));
		replyContent.setSpan(new MyContentSpan("",reply),0,content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		
		holder.content.setText("");
		holder.content.append(replayHead);//添加头
		holder.content.append(replyContent);//添加内容部分
		
		holder.content.setMovementMethod(LinkMovementMethod.getInstance()); //拥有点击属性
		
	}
	
	/**
	 * 自定义URLSpan 用来处理TextView 中的局部用户名超链接
	 * @author 
	 *
	 */
	private class MyURLSpan extends URLSpan {

		//用户编号
		private String userId;
		
		public MyURLSpan(URLSpan url) {
			super(url.getURL());
			this.userId = url.getURL();
		}

		/**
		 * 根据用户id 跳转到他的朋友圈
		 */
		@Override
		public void onClick(View widget) {
			
			callback.onUserNameClick(userId);
			
			Toast.makeText(context, userId, Toast.LENGTH_SHORT).show();
			
			widget.clearFocus();
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setUnderlineText(false);
			ds.setColor(Color.BLUE);
			ds.setShader(null);
		}

	}

	/**
	 * 用来处理用户点击TextView 评论内容时的行为
	 * @author Administrator
	 *
	 */
	private class MyContentSpan extends URLSpan{

		private String url;
		private Reply reply;
		
		public MyContentSpan(String url,Reply replyt) {
			super(url);
			this.url=url;
			this.reply=replyt;
		}
		
		
		@Override
		public void onClick(View widget) {
			callback.onReplayClick(this.reply,friend);
			widget.invalidate();
		}
		
		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setShader(null);
		}
		
	}
	
	/**
	 * 自定义回调接口
	 * @author Administrator
	 *
	 */
	public interface ReplayCallback{
		//评论
		void onReplayClick(Reply reply,FriendAction friend);
		//跳转到主页
		void onUserNameClick(String userId);
	}
	
	/**
	 * 初始化viewHolder
	 * 
	 * @param convertView
	 * @return
	 */
	private ViewHolder getViewHolder(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.content=(TextView) convertView.findViewById(R.id.content);
		return holder;
	}

	private class ViewHolder {
		public TextView content;// 回复的内容（包含名字和内容）
	}

	/**
	 * 
	 * @param id  用户编码
	 * @param name  用户名
	 * @return id 和 name 拼接的html链接
	 */
	private String getHTML(String id, String name) {
		String html = "<a href='" + id+":"+name+ "'>" + name + "</a>";
		return html;
	}

	
	/**
	 * 判断指定的字符串是否是 正确的（不为“”、null 、“null”）
	 * 
	 * @param str
	 * @return
	 */
	private boolean isEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str))
			return false;
		return true;
	}
	
	
}
