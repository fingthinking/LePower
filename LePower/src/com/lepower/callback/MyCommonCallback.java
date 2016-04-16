package com.lepower.callback;

import org.xutils.common.Callback.CommonCallback;

/**
 * 
 * @author Administrator
 *
 * @param <T>  自定义返回值类型  ，如果需要返回json就用String替换 T
 * 例如 ，返回json,   MyCallback<String> myCallback=new MyCallback<String>(), 
 * 然后在 onSuccess(String result) 中解析Json  ,json就在 result里。 
 * 
 */
public class MyCommonCallback<T> implements CommonCallback<T> {

	
	/**
	 * 被取消时 
	 */
	@Override
	public void onCancelled(CancelledException cancelExc) {

	}

	/**
	 * 当访问出错时调用
	 */
	@Override
	public void onError(Throwable throwable, boolean isOnCallback) {

	}

	/**
	 * 当访问结束时调用
	 */
	@Override
	public void onFinished() {

	}

	/**
	 *  当访问成功并返回结果时调用。
	 */
	@Override
	public void onSuccess(T result) {
		
	}

}
