package com.lepower.callback;

public interface HttpCallbackListener {
	
	void onFinish(String response);
	void onError(Exception e);

}
