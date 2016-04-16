package com.lepower.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import android.util.Xml;

import com.lepower.model.YanzhengmaModel;

public class YanZhengMaUtils {
	private static final String YanzhengmaUsername = "cf_452088406";
	private static final String YanzhengmaPsw = "lai1314";

	public static int sendRequestWithHttpClient(final String phoneNum) {
		final int numcode = (int) ((Math.random() * 9 + 1) * 100000);
		LogUtils.e(numcode);
		ToastUtils.showShort("yanzhengmautils"+numcode);
		new Thread(new Runnable() {
			public void run() {
				try {
//					String url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
//					String content = "您的验证码是：" + numcode + "。请不要把验证码泄露给其他人。";
//					Map<String, String> data = new HashMap<String, String>();
//					data.put("account", YanzhengmaUsername);
//					data.put("password", YanzhengmaPsw);
//					data.put("mobile", phoneNum);
//					data.put("content", content);
//					DefaultHttpClient httpClient = new DefaultHttpClient();
//					HttpPost httpPost = new HttpPost(url);
//					ArrayList<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
//					for (Map.Entry<String, String> m : data.entrySet()) {
//						postData.add(new BasicNameValuePair(m.getKey(), m
//								.getValue()));
//					}
//
//					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
//							postData, HTTP.UTF_8);
//					httpPost.setEntity(entity);
//
//					HttpResponse response = httpClient.execute(httpPost);
//					HttpEntity httpEntity = response.getEntity();
//
//					InputStream is = httpEntity.getContent();
					List<YanzhengmaModel> yanzhengma = new ArrayList<YanzhengmaModel>();
//					yanzhengma = getYanzhengmas(is);

					// 验证码成功
					Observable.just(yanzhengma)
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Action1<List<YanzhengmaModel>>() {

								@Override
								public void call(List<YanzhengmaModel> result) {
									
									ToastUtils
									.showShort("验证码已发送，请查看手机信息");
//									LogUtils.e(result.toString());
									// TODO Auto-generated method stub
									for (int i = 0; i < result.size(); i++) {
										if (result.get(i).getCode() == 2) {
											ToastUtils
													.showShort("验证码已发送，请查看手机信息");
										} else {
											ToastUtils
													.showShort("请检查账号和密码正确性，且手机号码是否正常！！！！");
										}
									}
								}
							});
					// textView.setText(result);
				} catch (Exception e) {
					// textView.setText(e.toString());
				}
			}
		}).start();
		return numcode;
	}

	private static List<YanzhengmaModel> getYanzhengmas(InputStream xml)
			throws Exception {
		List<YanzhengmaModel> yanzhengmas = null;
		YanzhengmaModel yanzhengma = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");// 为Pull解析器设置要解析的XML数据
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				yanzhengmas = new ArrayList<YanzhengmaModel>();
				break;

			case XmlPullParser.START_TAG:
				if ("SubmitResult".equals(pullParser.getName())) {
					yanzhengma = new YanzhengmaModel();
				}
				if ("msg".equals(pullParser.getName())) {
					String msg = pullParser.nextText();
					yanzhengma.setMsg(msg);
				}
				if ("smsid".equals(pullParser.getName())) {
					String smsid = pullParser.nextText();
					yanzhengma.setSmsid(smsid);
				}
				if ("code".equals(pullParser.getName())) {
					String code = pullParser.nextText();
					yanzhengma.setCode(Integer.valueOf(code));
				}
				break;

			case XmlPullParser.END_TAG:
				if ("SubmitResult".equals(pullParser.getName())) {
					yanzhengmas.add(yanzhengma);
					yanzhengma = null;
				}
				break;
			}
			event = pullParser.next();
		}
		return yanzhengmas;

	}
}
