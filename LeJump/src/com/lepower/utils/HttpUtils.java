package com.lepower.utils;

import java.util.Map;

import org.xutils.x;
import org.xutils.http.RequestParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lepower.callback.HttpCallback;

public class HttpUtils {
	/**
	 * 异步请求
	 * 
	 * @param uri
	 *            请求的uri地址
	 * @param params
	 *            请求的参数
	 * @param callback
	 *            请求的回调函数
	 */
	public static void get(String uri, Map<String, Object> params,
			HttpCallback<?> callback) {
		RequestParams request = new RequestParams(uri);
		request.setCharset("utf-8");
		if (params != null) {
			for (String key : params.keySet()) {
				request.addParameter(key, params.get(key));
			}
		}
		x.http().get(request, callback);
	}

	/**
	 * 异步请求
	 * 
	 * @param uri
	 *            请求的uri地址
	 * @param params
	 *            请求的参数
	 * @param callback
	 *            请求的回调函数
	 */
	public static void post(String uri, Map<String, Object> params,
			HttpCallback<?> callback) {
		RequestParams request = new RequestParams(uri);
		request.setCharset("utf-8");
		if (params != null) {
			for (String key : params.keySet()) {
				request.addParameter(key, params.get(key));
			}
		}
		x.http().post(request, callback);
	}

	/**
	 * 异步请求
	 * 
	 * @param uri
	 *            请求的uri地址
	 * @param params
	 *            请求的参数
	 * @param clz
	 *            请求返回的类型
	 */
	public static <T> T getSync(String uri, Map<String, Object> params,
			Class<T> clz) {
		RequestParams request = new RequestParams(uri);
		request.setCharset("utf-8");
		if (params != null) {
			for (String key : params.keySet()) {
				request.addParameter(key, params.get(key));
			}
		}
		try {
			return x.http().getSync(request, clz);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 异步请求
	 * 
	 * @param uri
	 *            请求的uri地址
	 * @param params
	 *            请求的参数
	 * @param clz
	 *            请求返回的类型
	 */
	public static <T> T postSync(String uri, Map<String, Object> params,
			Class<T> clz) {
		RequestParams request = new RequestParams(uri);
		request.setCharset("utf-8");
		if (params != null) {
			for (String key : params.keySet()) {
				request.addParameter(key, params.get(key));
			}
		}
		try {
			return x.http().postSync(request, clz);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isNetWork(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		}
		return false;
	}

}
