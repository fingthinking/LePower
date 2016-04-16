package com.lepower.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.lepower.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
 
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	
	/**
	 * app_id
	 */
	private static final String APP_ID = "wxd60e6ab8ff8e4bb2";
	
	/**
	 * 第三方app和微信通信的openapi接口
	 */
	private IWXAPI api;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry);
		api = WXAPIFactory.createWXAPI(getActivity(), APP_ID,true);
		api.handleIntent(getIntent(), getActivity());
	}
	
	//微信发送的请求将回调到onReq方法
	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub	
		finish();
	}
 
	//发送到微信请求的响应结果将回调到这个方法
	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub	
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK://用户同意
			if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
				Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_LONG).show();
				break;
			}
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
			break;
		default:
			break;
		}
		finish();
	}
	
	private WXEntryActivity getActivity(){
		return WXEntryActivity.this;
	}

}