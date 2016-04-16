package com.lepower.utils;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import rx.Observable;
import rx.Subscriber;
import android.util.Log;

public class GetObservable {
	
	/**
	 * 只是将从服务器返回的结果回调过去,不直接进行解析,解析放在回调中进行
	 * @param url
	 * @return
	 */
	public static Observable getObservable(final String url){
		Observable observable = null;
		Log.d("heheda", url);
		observable = Observable.create(new Observable.OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> subscriber) {
				// TODO Auto-generated method stub
				String result = null;
				result = HttpUtils.getResult(url);
				subscriber.onNext(result);
				Log.d("heheda", "回调了");
			}
		});
		return observable;
	}

}
