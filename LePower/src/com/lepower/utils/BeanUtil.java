package com.lepower.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;

import com.lepower.model.Expressions;
import com.lepower.model.Favour;
import com.lepower.model.FriendAction;
import com.lepower.model.Photos;
import com.lepower.model.Reply;

public class BeanUtil {
	
	private static final String TAG="BeanUtil";  //tag
	private static final int COUNT=8;  //显示的点赞人数量  ,默认8条
	
	static float ratio=0.6f;
	
	private static Pattern pattern = Pattern.compile("\\[emoji_\\w{5}]");//表情名模式
	
	private final String LOCAL_TIME = "local_time";
	private final String LAST_ID = "last_id";
	private static BeanUtil instance;
	private Context context;
	
	
	private BeanUtil() {
	}

	public static  synchronized BeanUtil getInstance() {
		if (instance == null) {
			instance = new BeanUtil();
		}
		return instance;
	}

	/**
	 * json数据转为friendaction model对象
	 * 
	 * @param str
	 * @return
	 * @throws JSONException
	 */
	public FriendAction json2Friend(JSONObject json) throws Exception {
		FriendAction friendAction = new FriendAction();
		
		friendAction.friendId=friendAction.id=json.getString("circleId");
		friendAction.contentText=json.getString("content");
		friendAction.photo=json.getString("imgUrl");
		friendAction.nickName=json.getString("nickName");
		friendAction.address=json.getString("publishAddr");
		String time=json.getString("publishDate");
		friendAction.sendDate=time.substring(0, time.length()-1);
		friendAction.scopeFlag=json.getString("scopeFlag");
		friendAction.userName=json.getString("userName");
		friendAction.userId=json.getString("userId");
		
		friendAction.favourList=json2Favour(json.getJSONArray("cirLikes"));
		friendAction.replyList=json2Reply(json.getJSONArray("comments"));
		//暂时不用此字段
//		friendAction.photos=getPhotos()
		
		friendAction.images=json2Image(json.getString("picUrl"));
		
		friendAction.replyCount=friendAction.
				replyList==null? 0:friendAction.replyList.size();
		friendAction.favourCount=friendAction.
				favourList==null?0:friendAction.favourList.size();
		
		friendAction.favourName=getFavouName(friendAction.favourList);
	
		return friendAction;
	}
	
	/**
	 * 将图片返回的字符串解析成一个个url地址， 默认分隔符是： * 
	 * 
	 * @param strImages
	 * @return
	 */
	private List<String> json2Image(String strImages) {
		if(strImages==null||strImages.length()<1){
			return null;
		}else{
			List<String> images=new ArrayList<String>();
			
			StringTokenizer tokenizer=new StringTokenizer(strImages, "*");
			while(tokenizer.hasMoreElements()){
				images.add(tokenizer.nextToken());
			}
			
			return images;
		}
	}

	/**
	 * 将图片返回的字符串解析成一个个Photo对象列表， 默认分隔符是： * 
	 * @param strImages
	 * @return
	 */
	public static List<Photos> getPhotos(List<String> images){
		
		if(images==null||images.size()<1){
			return null;
		}else{
			List<Photos> photos=new ArrayList<Photos>();
			for(String url:images){
				
				Photos p=new Photos();
				p.max=p.min=LeUrls.BASE_SERVER+url;
				
				photos.add(p);
			}
			
			return photos;
		}
	}
	
	
	
	/**将回复json数组转换成回复列表
	 * 
	 * @param array
	 * @return
	 */
	private List<Reply> json2Reply(JSONArray array) {
		if(array==null||array.length()<1){
			return null;
		}else{
			List<Reply> replies=new ArrayList<Reply>();
			int length=array.length();
			Reply reply;
			JSONObject json;
			for(int i=0;i<length;i++){
				reply=new Reply();
				try {
					
					json=array.getJSONObject(i);
					reply.setId(json.getString("commentId"));
					reply.setFriendId(json.getString("circleId"));
					reply.setSendName(json.getString("commentNickName"));
					reply.setUserId(json.getString("commentUId"));
					reply.setReplyName(json.getString("replyNickName"));
					reply.setReplyUserId(json.getString("replyUId"));
					reply.setContent(json.getString("content"));
					reply.setCommentTime(json.getString("commentTime"));
					
					replies.add(reply);
					
				} catch (JSONException e) {
					Log.e(TAG, "回复转换失败...");
					e.printStackTrace();
				}
			}
			
			return replies;
		}
		
	}

	/**
	 * 将json 点赞 转换成java列表
	 * 
	 * @param array
	 * @return
	 */
	public List<Favour> json2Favour(JSONArray array){
		if(array==null||array.length()<1){
			return null;
		}else{
			List<Favour> favours=new ArrayList<Favour>();
			int length=array.length();
			
			JSONObject json;
			Favour favour=null;
			for(int i=0;i<length;i++){
				favour=new Favour();
				try {
					json=array.getJSONObject(i);
					favour.setId(json.getString("cirLikeId"));
					favour.setFriendId(json.getString("circleId"));
					favour.setFavourUId(json.getString("likeUId"));
					favour.setFavourName(json.getString("likeNickName"));
					favour.setCreateDate(json.getString("createDate"));
					
					favours.add(favour);
				} catch (JSONException e) {
					e.printStackTrace();
					Log.e(TAG, "点赞转换失败....");
				}
			}
			
			return favours;
		}
		
	}

	/**将 点赞列表中的人名转换成 拼接成字符串。
	 * 默认显示COUNT :(8)条 
	 * @param pariseArray
	 * @return
	 * @throws Exception
	 */
	public static  String getFavouName(List<Favour> favours) {
		StringBuilder sb = new StringBuilder();
		if(favours==null||favours.size()<1){
			return "";
		}else{
			int length=favours.size()-1;
			int count=0;
			for(int i=length;i>=0;i--,count++){
				sb.append(favours.get(i).getFavourName());
				
				if(count<COUNT){
					sb.append(",");
				}else{  //默认显示8条人名
					break;
				}
			}
			
			
			return sb.toString();
		}
	}

	/**
	 * 根据json数据转换为model集合
	 * 
	 * @param obj
	 * @param db
	 * @param shared
	 * @return
	 */
	public List<FriendAction> getFriends(Context context,String obj) {
		
		this.context=context;
		List<FriendAction> friendActions=null;
		
		try {
			
			JSONObject json=new JSONObject(obj);
			if(checkStatus(json)){
				JSONArray array = json.getJSONArray("data");
				int length=array.length();
				JSONObject actionJson;
				FriendAction friendAction;
				
				friendActions=new ArrayList<FriendAction>();
				
				for(int i=0;i<length;i++){
					actionJson=array.getJSONObject(i);
					friendAction=json2Friend(actionJson);
					friendActions.add(friendAction);
				}
				
			}
			
			return friendActions;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "动态列表加载失败....");
		}
		
		return null;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public FriendAction getSingleFriend(String obj){
		
		List<FriendAction> friendActions=null;
		
		try {
			FriendAction friendAction=null;
			
			JSONObject json=new JSONObject(obj);
			if(checkStatus(json)){
				JSONObject actionJson=json.getJSONObject("data");
				friendAction=json2Friend(actionJson);
				
			}
			
			return friendAction;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "单个动态加载失败....");
		}
		
		return null;
	}
	
	/**
	 * 处理时间
	 * 
	 * @param string
	 * @return
	 */
	public static String handTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (time == null || "".equals(time.trim())) {
			return "";
		}
		try {
			Date date = format.parse(time);
			long tm = System.currentTimeMillis();// 当前时间戳
			long tm2 = date.getTime();// 发表动态的时间戳
			long d = (tm - tm2) / 1000;// 时间差距 单位秒
			if ((d / (60 * 60 * 24)) > 0) {
				return d / (60 * 60 * 24) + "天前";
			} else if ((d / (60 * 60)) > 0) {
				return d / (60 * 60) + "小时前";
			} else if ((d / 60) > 0) {
				return d / 60 + "分钟前";
			} else {
				// return d + "秒前";
				return "刚刚";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 获取服务器返回的 评论id
	 * @param result
	 * @return
	 */
	public String getReplyId(String result){
		
		try {
			
			JSONObject obj=new JSONObject(result);
			if(checkStatus(obj)){
				
				JSONObject json=obj.getJSONObject("data");
				String replyId=json.getString("commentId");
				return replyId;
				
			}else{
				return null;
			}
			
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		
		return null;
		
	}
	
	
	/**
	 * 先对服务器返回的数据信息判断， 查看是否出错。
	 * @param json
	 * @return
	 */
	public boolean checkStatus(JSONObject json){
		try {
			int resCode=json.getInt("resCode");
			
			if(resCode==1){
				String resMsg=json.getString("resMsg");
				
//				Toast.makeText(context,resMsg, Toast.LENGTH_SHORT).show();
				
				return false;
			}else{
				return true;
			}
			
			
			
		} catch (JSONException e) {
			Log.e(TAG,e.toString());
		}
		return false;
	}
	
	/**
	 * 将表情填充到对应的表情符号
	 * 
	 * @param context
	 * @param content  要转换的文本
	 * @return
	 */
	public static SpannableStringBuilder content2Emoj(Context context,String content) {

		SpannableStringBuilder sb = new SpannableStringBuilder(content);
		Matcher matcher = pattern.matcher(content);

		
		while (matcher.find()) {
			String emojName = matcher.group();
			int start = matcher.start();
			int end = matcher.end();

//			ImageSpan ss=new ImageSpan(null);
			
//			Bitmap bm=BitmapFactory.decodeResource(context.getResources(),
//					Expressions.EMOJMAP.get(emojName));
//			int width=bm.getWidth();
//			int height=bm.getHeight();
			
//			bm.setWidth((int)(ratio*width));
//			bm.setHeight((int)(ratio*height));
			
//			ImageSpan span=new ImageSpan(bm); 
			
			Drawable drawable=context.getResources().
					getDrawable(Expressions.EMOJMAP.get(emojName));
			
			drawable.setBounds(0, 0,castInt(drawable.getIntrinsicWidth()),castInt(drawable.getIntrinsicHeight()));
			
			ImageSpan spans=new ImageSpan(drawable);
			
//			ImageSpan spans = new ImageSpan(context,
//					Expressions.EMOJMAP.get(emojName));
			
			sb.setSpan(spans, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		return sb;
	}
	
	private  static int castInt(int size){
		return (int)(size*ratio);
	}
	
}
