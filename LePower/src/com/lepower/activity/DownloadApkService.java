package com.lepower.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.lepower.utils.IOSDCard;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class DownloadApkService extends IntentService {
//	private String urlString;// 下载地址
	private String fileName;// 文件名称
	private int fileSize;// 文件总大小
//	private File saveFile;// 下载的文件
	private long downLoadFileSize;// 已经下载的文件大小
	//通知部分
	private NotificationManager notificationManager;
	private Notification notification;
	private RemoteViews rViews;
	
	private static String intentServiceName="com.lepower.downloadapkserviece";
	
	private int CODE_BEGIN=0;
	private int CODE_UPDATE=1;
	private int CODE_FINISH=2;
	
	public DownloadApkService() {
		super(intentServiceName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle=intent.getExtras();
		String urlString=bundle.getString("urlString");
		//直接获取并生成文件，判断逻辑写在了Activity那边
		fileName=bundle.getString("fileName");//传进来了
		//
		/*notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notification=new Notification(R.drawable.ic_launcher, "下载文件中", System.currentTimeMillis());
		Intent intentNotifi=new Intent(this,DownloadApkActivity.class);//通知
		PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intentNotifi, 0);
		notification.contentIntent=pendingIntent;
		//加载布局文件
//		rViews=new RemoteViews(getPackageName(),R.layout.d);
*/		IOSDCard ioSdCard = new IOSDCard();// sd卡操作对象
		
		if (!ioSdCard.isFileExist("/lepowerdownload")) {// 如果不存在下载目录就创建
			ioSdCard.CreatSDDir("lepowerdownload");
		}
		
		
		File fileSave = ioSdCard.CreatSDFile("lepowerdownload/" + fileName);// 下载的文件,说明文件不存在，且已经准备好下载了
		
		download(urlString,fileSave);
		
	}
	
	
	/**
	 * 传入下载地址，进行下载
	 * @param urlString
	 */
	public void download(String urlString,File saveFile) {

		FileOutputStream output = null;//
		try {// 利用Http协议下载文件 // 创建一个URL对象，和HttpURLConnection对象
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 得到InputStream
			InputStream input = connection.getInputStream();
			fileSize = connection.getContentLength();// 根据响应获取文件大小
			output = new FileOutputStream(saveFile);//
			// 读数据，并存入到SDCard,取得SDCard的路径，Environment.getExternalStorageDirectory()
			byte buf[] = new byte[1024];
			downLoadFileSize = 0;
			//发广播告诉Activity已经开始下载
			Log.d("heheda", "开始下载了");
			sendMyBroadcast(downLoadFileSize,CODE_BEGIN);
			
			do {
				int numread = input.read(buf);
//				Log.d("buf的numread", "numread是："+numread);
				if (numread == -1) {
					break;
				}
				output.write(buf, 0, numread);// 写入文件
				downLoadFileSize += numread;
				sendMyBroadcast(downLoadFileSize,CODE_UPDATE);
				
				
			} while (true);
			sendMyBroadcast(fileSize,CODE_FINISH);//下载完了直接发总大小
//			sendMsg(2);// 下载完成
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(output!=null){
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void sendMyBroadcast(long downloadFileSize,int code){
		Intent intentBro=new Intent("com.downloadapkservice."+fileName.substring(0,fileName.indexOf("."))+".PRE_BROADCAST");
		Bundle bundle=new Bundle();
		bundle.putLong("fileSize", fileSize);
		bundle.putLong("downLoadFileSize", downLoadFileSize);
		bundle.putInt("code", code);
		intentBro.putExtras(bundle);
		sendBroadcast(intentBro);
	}

}
