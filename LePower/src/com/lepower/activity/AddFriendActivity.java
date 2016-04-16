package com.lepower.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lepower.R;
import com.lepower.model.LocationInfo;
import com.lepower.utils.LocationUtil;
import com.sina.weibo.sdk.WeiboAppManager.WeiboInfo;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class AddFriendActivity extends Activity implements OnClickListener{
	
	/**
	 * app_id
	 */
	private static final String APP_ID = "wxd60e6ab8ff8e4bb2";
	
	/**
	 * 第三方app和微信通信的openapi接口
	 */
	private IWXAPI api;
	
	/*
	 *  申请的QQid  
	 */
    public static String mAppid = "1105135015";
    
    private IUiListener shareListener;
    
    private Tencent mTencent; 
	
	private LinearLayout mllwechat;
	
	private LinearLayout mllqq;
	
	private LinearLayout mllweibo;
	
	private LinearLayout mllfindFriend;
	
	private LinearLayout mllneib;
	
	private LinearLayout mllwechatCircle;
	
	private ImageView mlvBack;
	
	/**
	 * 微博APIID
	 */
	private static String mWeiboId = "3510452910";
	
	/** 
	 * 微博微博分享接口实例
	 * 
	 **/
    private IWeiboShareAPI  mWeiboShareAPI = null;
    
    /** 
     * 微博应用信息
     *  
     **/
    private WeiboInfo mWeiboInfo;
    
    public ShearMessageReceiver shearMessageReceiver;
        
    public static final String ACTION_SHEAR_RESULT = "extend_third_share_result";
	
    public static final String SHARE_APP_NAME = "shareAppName";
    public static final String PARAM_SHARE_FROM = "share_from";//标记分享来源点
    public static final String EXTEND_SHARE_570 = "extend_share_570";//标记分享来源点
    
    private boolean flag = true;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_friend);
		initWeChat();
		initView();
	}

	private void initWeChat() {
		//通过WXAPIFactory工厂,获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, APP_ID,true);
				
		//将应用的appId注册到微信
		api.registerApp(APP_ID);
		
	}

	private void initView() {
		mllwechat = (LinearLayout)findViewById(R.id.id_wechat_item);
		mllwechat.setOnClickListener(this);
		mllqq = (LinearLayout)findViewById(R.id.id_qqfriend);
		mllqq.setOnClickListener(this);
		mllfindFriend = (LinearLayout)findViewById(R.id.id_find_friend_item);
		mllfindFriend.setOnClickListener(this);
		mllweibo = (LinearLayout)findViewById(R.id.id_weibo_item);
		mllweibo.setOnClickListener(this);
		mllneib = (LinearLayout)findViewById(R.id.id_neib_item);
		mllneib.setOnClickListener(this);
		mllwechatCircle = (LinearLayout)findViewById(R.id.id_wechatcircle_item);
		mllwechatCircle.setOnClickListener(this);
		mlvBack = (ImageView)findViewById(R.id.iv_add_friend_back);
		mlvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.id_wechat_item:
			//分享一个信息给微信好友
			shareToWechatFriend();
			break;
		case R.id.id_qqfriend:
			Log.d("MainActivity", "QQ分享");
			mTencent = Tencent.createInstance(mAppid, getActivity());
			//分享一个信息给QQ好友
			shareToQQFriebd();
			break;
		case R.id.id_find_friend_item:
			Log.d("MainActivity", "找好友");
			intent = new Intent(getActivity(),FindFriendActivity.class);
			startActivity(intent);
			break;
		case R.id.id_weibo_item:
			Log.d("MainActivity", "微博分享");
			//分享一个信息给微博好友
			shareToWeiboFriend();
			break;
		case R.id.id_neib_item:
			//进入附近好友列表页面
			intent = new Intent(getActivity(),NebiFriendListActivity.class);
			startActivity(intent);
			break;
		case R.id.id_wechatcircle_item:
			Log.d("MainActivity", "分享给朋友圈");
			shareTowechatCircle();
			break;
		case R.id.iv_add_friend_back:
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 分享一个信息给微博好友
	 */
	private void shareToWeiboFriend() {
		//创建微博分享接口实例
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(getActivity(), mWeiboId);
		mWeiboShareAPI.registerApp();
		
		shearMessageReceiver = new ShearMessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_SHEAR_RESULT);
		registerReceiver(shearMessageReceiver, filter);//注册广播
		flag = false;//设置标志位为false
		
		Bundle bundle = new Bundle();
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		bundle.putString(WBConstants.SDK_WEOYOU_SHARETITLE, "我最近在玩乐动力");
		bundle.putString(WBConstants.SDK_WEOYOU_SHAREDESC, "记录运动轨迹，享受运动人生，约泡神器，乐动力你值得拥有");
		bundle.putString(WBConstants.SDK_WEOYOU_SHAREURL, "www.baidu.com");
		bundle.putString(SHARE_APP_NAME, "乐动力");
        bundle.putString("shareBackScheme", "weiboDemo://share");
        bundle.putString(PARAM_SHARE_FROM, EXTEND_SHARE_570);
        bundle.putByteArray(WBConstants.SDK_WEOYOU_SHAREIMAGE, bitMapToBytes(bitmap));
		mWeiboShareAPI.shareMessageToWeiyou(getActivity(), bundle);
	}
	
	public byte[] bitMapToBytes(Bitmap  bitmap) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            return  os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("Weibo.ImageObject", "put thumb failed");
        }finally{
            try {
                if(os!=null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       return null;
    }
	
	/**
	 *微博中利用了广播接收者来接收分享之后的信息
	 */
	class ShearMessageReceiver extends BroadcastReceiver {
        
        // 这里可以用handler  处理
        @Override
        public void onReceive(Context context, Intent intent) {
         //resultCode    分享状态：0成功；1失败；2取消；
         //actionCode    分享完成：0，返回app；1，留在微博
        Bundle bundle  =   intent.getExtras();
        
        if(bundle!=null){
          final  int  resultCode = bundle.getInt("resultCode",-1);
          final  int  actionCode = bundle.getInt("actionCode",-1);
          String  resultStr = "";
          String  actionStr = "";
          switch (resultCode) {
            case 0:
                resultStr ="成功";
                break;
            case 1:
                resultStr ="失败";
                break;
            case 2:
                resultStr ="取消";
                break;
            default:
                break;
          }
          
          switch (actionCode) {
              case 0:
                  actionStr ="返回乐动力";
                  break;
              case 1:
                  actionStr ="留在微博";
                  break;
              default:
                  break;
          }
          final String resultShowStr =resultStr ;
        }else{
            Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_LONG).show();
        }
        }
        }
    

	/**
	 * 分享信息到QQ
	 */
	private void shareToQQFriebd() {
		// TODO Auto-generated method stub
		Bundle params = new Bundle();
		//要分享的类型,在这里的值表示图文分享(必要)
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		//要分享的标题(必要)
		params.putString(QQShare.SHARE_TO_QQ_TITLE, "我最近在玩乐动力");
		//要分享的摘要(可选要)
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "记录运动轨迹，享受运动人生，约跑神器，你值得拥有");
		//这天分享消息被好友点击后跳转的URL(必要)
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://192.168.1.100:8080/LeSports/resources/APK/LeStep.apk");
		//分享的图片的URL或者本地路径(必要)
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://h.hiphotos.baidu.com/zhidao/wh=450,600/sign=3dc4538262d0f703e6e79dd83dca7d0b/7a899e510fb30f24f570e996c895d143ac4b03b8.jpg");
		mTencent.shareToQQ(getActivity(), params, shareListener);
		shareListener = new IUiListener() {
			
			@Override
			public void onError(UiError arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(Object arg0) {
				// TODO Auto-generated method stub
				Log.d("TAG", "成功分享到QQ");
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		};
	}

	/**
	 * 分享到微信好友
	 */
	private void shareToWechatFriend() {
		// TODO Auto-generated method stub
		String url = "http://192.168.1.100:8080/LeSports/resources/APK/LeStep.apk";//收到分享的好友点击信息会跳转到这个地址去
		//初始化一个WXWebpageObject对象,并将跳转的url写进去
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = url;
        
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                localWXWebpageObject);
        localWXMediaMessage.title = "我最近在玩乐动力";//不能太长，否则微信会提示出错
        localWXMediaMessage.description = "记录运动轨迹，享受运动人生，乐动力你值得拥有:http://192.168.1.100:8080/LeSports/resources/APK/LeStep.apk";
        
        //将一个图片文件解码成一个Bitmap对象
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        localWXMediaMessage.thumbData = getBitmapBytes(bmp, false);//将Bitmap对象转换成一个byte数组
        
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.scene = SendMessageToWX.Req.WXSceneSession;//分享给好友
//        localReq.scene = SendMessageToWX.Req.WXSceneTimeline;//设置发送到朋友圈
        localReq.transaction = System.currentTimeMillis() + "";//唯一标识一个请求
        localReq.message = localWXMediaMessage;
        api.sendReq(localReq);
	}
	
	/**
	 * 分享到朋友圈
	 */
	private void shareTowechatCircle(){
		String url = "http://192.168.1.100:8080/LeSports/resources/APK/LeStep.apk";//收到分享的好友点击信息会跳转到这个地址去
		//初始化一个WXWebpageObject对象,并将跳转的url写进去
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = url;
        
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                localWXWebpageObject);
        localWXMediaMessage.title = "我最近在玩乐动力";//不能太长，否则微信会提示出错
        localWXMediaMessage.description = "记录运动轨迹，享受运动人生，乐动力你值得拥有:http://192.168.1.100:8080/LeSports/resources/APK/LeStep.apk";
        
        //将一个图片文件解码成一个Bitmap对象
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        localWXMediaMessage.thumbData = getBitmapBytes(bmp, false);//将Bitmap对象转换成一个byte数组
        
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.scene = SendMessageToWX.Req.WXSceneTimeline;//设置发送到朋友圈
        localReq.transaction = System.currentTimeMillis() + "";//唯一标识一个请求
        localReq.message = localWXMediaMessage;
        api.sendReq(localReq);
	}
	
	// 需要对图片进行处理，否则微信会在log中输出thumbData检查错误
    private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);//创建一个本地Bitmap,用于显示
        Canvas localCanvas = new Canvas(localBitmap);//初始化一个画布
        int i;
        int j;
        //要使i<j
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        } else {
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,
                    80, 80), null);//针对传进来的bitmap进行重新画图,画一个位图,第三个参数是需要重新绘制图片的位置
            if (paramBoolean) //返回的false
                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);//压缩图片
            localBitmap.recycle();//回收位图
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                e.printStackTrace();
            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }
    
    private AddFriendActivity getActivity(){
    	return AddFriendActivity.this;
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	if (!flag) {
			unregisterReceiver(shearMessageReceiver);
		}
    }
    
}
