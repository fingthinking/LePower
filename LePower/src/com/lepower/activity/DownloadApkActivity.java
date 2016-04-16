package com.lepower.activity;

import java.io.File;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lepower.R;
import com.lepower.utils.IOSDCard;
import com.lepower.widget.ProgressButton;
import com.lepower.widget.ProgressButton.OnProgressButtonClickListener;

public class DownloadApkActivity extends Activity {
	private TextView tvSportName, tvSlogan, tvSportIntro;
	private ImageView ivSportIcon;
	private int flagSport;// 当前是哪个运动，相当于index
	
	//运动app的各项信息，需要更改
	private String[] sportNameArray = { "乐计步", "乐跑", "乐骑行", "乐跳绳", "俯卧撑",
			"仰卧起坐" };
	private String[] sloganArray = { "生命，永不止步", "跑步是另一个 维度的旅行，奔跑吧，兄弟！",
			"爱生活，爱骑行", "欢乐跳跳，健康哈哈笑", "高质量是我们的永恒的追求", "在哪都能做的健身运动" };
	private int[] sportIconIdArray = { R.drawable.atype_walk,
			R.drawable.atype_running, R.drawable.atype_bicycling,
			R.drawable.atype_jumprope, R.drawable.atype_dive,
			R.drawable.atype_ski };
	private String[] sportIntroArray = { "走走走走走", "跑起来，一定要跑起来", "骑行app伴你同行",
			"跳绳app简直了。。。", "俯卧撑app集成了高精度的智能算法，能够准确地记录你每一次俯卧撑数量，还不快下载！！！",
			"仰卧起坐app是一款。。。的软件，对很好用的软件，不信你就用一下试试！" };
	private String[] urlDownload = {
			"http://192.168.1.100:8080/LeSports/resources/APK/LeStep.apk",
			"http://192.168.1.100:8080/LeSports/resources/APK/LeRun.apk",
			"http://192.168.1.100:8080/LeSports/resources/APK/LeBicycle.apk",
			"http://192.168.1.100:8080/LeSports/resources/APK/LeJump.apk",
			"http://192.168.1.100:8080/LeSports/resources/APK/LePushup.apk",
			"http://192.168.1.100:8080/LeSports/resources/APK/LeSitUp.apk" };

	private ProgressButton progressButton;// 自定义的进度条按钮
	private IOSDCard ioSdCard = new IOSDCard();// sd卡操作对象
	private String urlString;// 下载地址
	private String fileName;// 文件名称
	private long fileSize=0;// 文件总大小
	private long downLoadFileSize;// 已经下载的文件大小
	private boolean fileIsExist = false;// 文件是否已经存在
	private boolean loading = false;// 是否正在下载
	private final int FLAG_BEGIN = 0, FLAG_UPDATE = 1, FLAG_FINISH = 2;
	private BroadcastReceiver broadcastReceiver;
	private SharedPreferences sharedPre;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case FLAG_BEGIN:
					loading = true;
					progressButton.setMax((int) (fileSize / 1024) );//
					break;
				case FLAG_UPDATE:
						progressButton.setMax((int) (fileSize / 1024) );//
						loading=true;
					progressButton.setProgress((int) (Math.ceil(downLoadFileSize / 1024)));// 向上取整
					progressButton.setText((int) Math.ceil(downLoadFileSize
							* 100 / fileSize)
							+ "%");
					break;
				case FLAG_FINISH:
					progressButton.setText("安装");
					loading = false;
					fileIsExist = true;// 下载完成了就设置为存在
					break;
				default:
					break;
				}
			}
			super.handleMessage(msg);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_download_apk);
		//初始化View和数据
		initView();
		//注册广播接受者，内部类广播接受者需要用动态方法注册
		broadcastReceiver=new DownloadBroadcastReceiver();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction("com.downloadapkservice."+fileName.substring(0,fileName.indexOf("."))+".PRE_BROADCAST");
		registerReceiver(broadcastReceiver, intentFilter);
		
		//这里是进度条按钮的点击事件
		progressButton.setOnProgressButtonClickListener(new OnProgressButtonClickListener() {// 这里只能重写这个点击方法才行
			@Override
			public void onClickListener() {
				
				
				//应该先判断文件是否正在下载，如果是的话，则无法响应其操作
				if (fileIsExist) {
					
					openAndInstall();//存在就直接打开安装就可以了
//					Log.d("shac", "文件存在？");
//					return;
				} else if(!loading){//如果不存在就下载
					loading=true;//设置正在下载中
					Toast.makeText(DownloadApkActivity.this, "正在启用下载，请稍后。。。", 1);
					progressButton.setText("开始下载....");
					Intent intent =new Intent(DownloadApkActivity.this,DownloadApkService.class);
					Bundle bundle=new Bundle();
					bundle.putString("urlString", urlString);
					bundle.putString("fileName", fileName);//这里传名字，在Seivice那边再new一个
					intent.putExtras(bundle);
					startService(intent);
					
				}
			}
		});
	}

	/**
	 * 初始化View和参数
	 */
	private void initView() {
		// TODO Auto-generated method stub
		tvSportName = (TextView) findViewById(R.id.tvSportName);
		tvSlogan = (TextView) findViewById(R.id.tvSlogan);
		tvSportIntro = (TextView) findViewById(R.id.tvSportIntro);
		ivSportIcon = (ImageView) findViewById(R.id.ivSportIcon);
		// 获取intent传过来的参数
		Bundle bundle = getIntent().getExtras();
		flagSport = bundle.getInt("flagSportAct");
		// 根据flagSport确定开启的是哪个运动。
		tvSportName.setText(sportNameArray[flagSport]);
		tvSlogan.setText(sloganArray[flagSport]);
		tvSportIntro.setText(sportIntroArray[flagSport]);
		ivSportIcon.setImageResource(sportIconIdArray[flagSport]);

		progressButton = (ProgressButton) findViewById(R.id.progressButton1);
		urlString = urlDownload[flagSport];//
		//获取到下载的文件名之后，用文件名来标识SharedPreference和IntentService
		fileName = urlString.substring(urlString.lastIndexOf("/") + 1);// 获取到下载文件的名称
		//先从SharePre中取得两个数据,跟fileName有关
		sharedPre=getSharedPreferences("downloadapk_"+fileName.substring(0,fileName.indexOf("."))+"_data", Context.MODE_PRIVATE);
		fileSize=sharedPre.getLong("fileSize", 0);//更新数据
		downLoadFileSize=sharedPre.getLong("downloadFileSize", 0);
		if (ioSdCard.isFileExist("/lepowerdownload/" + fileName)) {
			progressButton.setText("安装");
			fileIsExist = true;
		} else {
			progressButton.setText("立即下载");
			fileIsExist = false;
		}
		progressButton.setTextSize(40);
	}

	/**
	 * 发送消息
	 * 
	 * @param flag
	 */
	private void sendMsg(int flag) {
		// TODO Auto-generated method stub
		Message msg = Message.obtain();
		msg.what = flag;
		handler.sendMessage(msg);
	}

	/**
	 * 打开并安装应用
	 */
	private void openAndInstall() {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(
				Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/lepowerdownload/" + fileName)), type);
		startActivity(intent);
	}
	
	/*
	 * 广播接收者
	 */
	public class DownloadBroadcastReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle bundle=intent.getExtras();
			int code=bundle.getInt("code");
			fileSize=bundle.getLong("fileSize");//总大小
			downLoadFileSize=bundle.getLong("downLoadFileSize");
//			Log.d("haha", "接收到广播了"+fileSize+":"+downLoadFileSize+":"+code);
			sendMsg(code);
		}
	}
	/*
	 * 需要注销广播接受者，如果正在下载需要记录下载情况，才能在还未完成下载时再次进入继续更新UI
	 * (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(broadcastReceiver);
		Editor editor=sharedPre.edit();
		if(loading){
			editor.putLong("fileSize", fileSize);
			editor.putLong("downloadFileSize",downLoadFileSize);
			editor.commit();
		}
		super.onDestroy();
		
	}
}
