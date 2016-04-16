package com.lepower.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lepower.callback.MyCommonCallback;
import com.lepower.model.Favour;
import com.lepower.model.FriendAction;
import com.lepower.model.Reply;

/**
 * 对说说的网络操做类
 * 
 * @author Administrator
 *
 */
public class FriendActionTool {

	/**
	 * 将评论内容发送到服务器
	 * 
	 * @param TAG
	 * @param reply
	 */
	public static void sendReply2Server(final String TAG, Reply reply,
			final String myAction,final Context context,final String friendId) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("circleId", reply.getFriendId());
		parameters.put("commentUId", reply.getUserId());
		parameters.put("content", reply.getContent());
		parameters.put("repliedUserId", reply.getReplyUserId());
		parameters.put("ownerId", reply.getOwnerId());
		String url = LeUrls.CIRCLE_PUBLISH_COMMENT;
		
		NetUtils.post(url, parameters, new MyCommonCallback<String>(){
			
			@Override
			public void onSuccess(String result) {
				BeanUtil beanUtil=BeanUtil.getInstance();
				String replyId=beanUtil.getReplyId(result);
				
				Intent intent=new Intent(myAction);
				intent.putExtra("friendId", friendId);
				intent.putExtra("replyId", replyId);
				
				context.sendBroadcast(intent);
			}
			
			@Override
			public void onError(Throwable throwable, boolean isOnCallback) {
				Log.e(TAG, throwable.toString());
			}
		});

	}

	/**
	 * 点赞
	 * 
	 * @param TAG
	 * @param favour
	 */
	public static void sendFavour2Server(final String TAG, Favour favour) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("circleId", favour.getFriendId());
		parameters.put("userId", favour.getFavourUId());
		parameters.put("ownerId", favour.getFriendOwner());
		String url = LeUrls.CIRCLE_ADDLIKE;
		doPost(url, parameters, TAG);
	}

	/**
	 * 取消 赞
	 * 
	 * @param TAG
	 * @param favour
	 */
	public static void cancelFavour2Server(final String TAG, Favour favour) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("circleId", favour.getFriendId());
		parameters.put("userId", favour.getFavourUId());

		String url = LeUrls.CIRCLE_DELETELIKE;

		doGet(url, parameters, TAG);
	}
	
	/**
	 * 转发动态
	 * @param TAG
	 * @param oldCircleId
	 * @param userId
	 * @param newUserId
	 * @param location
	 * @param scopeFlag
	 */
	public static void transmitFriend(final String TAG,
			String oldCircleId,String userId,String newUserId,
			String location,String scopeFlag
			){
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("oldCircleId", oldCircleId);
		parameters.put("userId", userId);
		parameters.put("newUserId", newUserId);
		parameters.put("location", location);
		parameters.put("scopeFlag", scopeFlag);
		
		String url=LeUrls.CIRCLE_TRANSMIT;
		
		doPost(url, parameters, TAG);
		
	}

	/**
	 * 删除说说
	 * @param TAG
	 * @param friendId
	 */
	public static void deleteFriendAction(final String TAG, String friendId) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("circleId", friendId);

		String url = LeUrls.CIRCLE_DELETECIRCLE;

		doGet(url, parameters, TAG);
	}
	
	/**
	 * 删除评论
	 * @param TAG
	 * @param replyId
	 */
	public static void deleteReply(final String TAG, String replyId){
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("commentId", replyId);
		
		String url = LeUrls.CIRCLE_DELETECOMMENT;
		doGet(url, parameters, TAG);
	}
	
	
	/**
	 * 处理get操作
	 * 
	 * @param url
	 * @param parameters
	 * @param TAG
	 */
	private static void doGet(String url, Map<String, String> parameters,
			final String TAG) {

		NetUtils.get(url, parameters, new MyCommonCallback<String>() {
			@Override
			public void onError(Throwable throwable, boolean isOnCallback) {
				Log.e(TAG, throwable.toString());
			}
		});
	}

	/**
	 * 处理post操作
	 * 
	 * @param url
	 * @param parameters
	 * @param TAG
	 */
	public static void doPost(String url, Map<String, String> parameters,
			final String TAG) {
		NetUtils.post(url, parameters, new MyCommonCallback<String>() {
			@Override
			public void onError(Throwable throwable, boolean isOnCallback) {
				Log.e(TAG, throwable.toString());
			}
		});
		
	}

	/**
	 * 判断以此编号的用户是否点过赞
	 * 
	 * @param favours
	 * @param userId
	 * @return
	 */
	public static int getPositionFavours(List<Favour> favours, String userId) {
		if (favours == null || favours.size() < 1) {
			return -1;
		} else {
			int len = favours.size();
			for (int i = 0; i < len; i++) {
				if (favours.get(i).getFavourUId().equals(userId)) {
					return i;
				}
			}

			return -1;
		}
	}
	
	/**
	 * 检查对应的friendId是否在List里
	 * 
	 * @param friendActions
	 * @param friendId
	 * @return
	 */
	public static int getPositionFriendActions(List<FriendAction> friendActions,String friendId){
		if(friendActions==null||friendActions.size()<1){
			return -1;
		}else{
			int len=friendActions.size();
			for(int i=0;i<len;i++){
				if(friendActions.get(i).friendId.equals(friendId)){
					return i;
				}
			}
			
			return -1;
		}
		
	}

}
