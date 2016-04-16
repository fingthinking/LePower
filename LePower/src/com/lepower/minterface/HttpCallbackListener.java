package com.lepower.minterface;

public interface HttpCallbackListener {
	void onFinish(String response);
	
	void onError(Exception e);
}
