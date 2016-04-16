package com.lepower.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

/**
 * 网络访问工具,具体功能: 1.get 请求 2.post 请求 3.上传文件 4.下载文件
 * 
 * 网络返回的结果在CommonCallback 里
 * 
 * @author Administrator
 *
 */
public class NetUtils {

	/**
	 * HTTP get请求
	 * 
	 * @param url
	 *            服务器 地址
	 * @param map
	 *            参数 键/值对 ，可能发给服务器的必要参数
	 * @param callback
	 *            回调方法
	 * @return
	 */
	public static <T> Cancelable get(String url, Map<String, String> map,
			CommonCallback<T> callback) {

		RequestParams params = new RequestParams(url);

		if (map == null || map.size() < 1)
			return null;

		for (Map.Entry<String, String> entry : map.entrySet()) {
			params.addBodyParameter(entry.getKey(), entry.getValue());
			// params.addQueryStringParameter(entry.getKey(), entry.getValue());
		}

		Cancelable cancelable = x.http().get(params, callback);

		return cancelable;
	}

	/**
	 * HTTP post请求
	 * 
	 * @param url
	 *            服务器地址
	 * @param map
	 *            参数 键/值对，可能发给服务器的必要参数
	 * @param callback
	 *            回调方法 , 可以在这里处理结果和异常
	 * @return
	 */
	public static <T> Cancelable post(String url, Map<String, String> map,
			CommonCallback<T> callback) {
		RequestParams params = new RequestParams(url);

		if (map == null || map.size() < 1)
			return null;

		for (Map.Entry<String, String> entry : map.entrySet()) {
			params.addBodyParameter(entry.getKey(), entry.getValue());
		}

		Cancelable cancelable = x.http().post(params, callback);

		return cancelable;
	}

	/**
	 * 上传文件 /图片
	 * 
	 * @param url
	 *            服务器地址
	 * @param parameters
	 *            可能发给服务器的必要参数，如果除了图片和文件外
	 *            没有其他数据要发给服务器，此参数设为null,即可。
	 * @param files
	 *            上传的文件列表（一个或多个）
	 * @param callback
	 *            回调方法 , 可以在这里处理结果和异常
	 * @return
	 */
	public static <T> Cancelable uploadFile(String url,
			Map<String, String> parameters, List<String> filePath,
			CommonCallback<T> callback) {
		RequestParams params = new RequestParams(url);
		List<File> files=new ArrayList<File>();
		if(filePath!=null&&filePath.size()>0){
			for (String path:filePath)
			{
				files.add(new File(path));
			}
		}
		/**
		 * 添加可能的必要参数，例如parameters可能是: parameters.put("userId","153532");
		 * parameters.put("userName","Jake"); parameters.put("age","23");
		 * 
		 */
		if (parameters != null && parameters.size() > 0) {
			for (Map.Entry<String, String> parameter : parameters.entrySet()) {
				params.addBodyParameter(parameter.getKey(),
						parameter.getValue());
			}
		}

		if(files!=null&&files.size()>0){
			// 插入图片
			int key = 0;
			for (File file : files) {
				params.addBodyParameter((key++) + "", file);
				// params.addParameter(entry.getKey(), entry.getValue());
			}
		}

		params.setMultipart(true);
		LogUtils.e("uploading-----------"+filePath);
		Cancelable cancelable = x.http().post(params, callback);
		return cancelable;
	}

	
	/**
	 * 上传文件 /图片
	 * 
	 * @param url
	 *            服务器地址
	 * @param parameters
	 *            可能发给服务器的必要参数，
	 *            如果除了图片和文件外
	 *            没有其他数据要发给服务器，此参数设为null即可。
	 * @param files
	 *            上传的文件地址的列表（一个或多个），
	 * @param callback
	 *            回调方法 , 可以在这里处理结果和异常
	 * @return
	 */
	public static <T> Cancelable uploadFileByUrls(String url,
			Map<String, String> parameters, List<String> sfiles,
			CommonCallback<T> callback) {
		RequestParams params = new RequestParams(url);

		/**
		 * 添加可能的必要参数，例如parameters可能是: parameters.put("userId","153532");
		 * parameters.put("userName","Jake"); parameters.put("age","23");
		 * 
		 */
		if (parameters != null && parameters.size() > 0) {
			for (Map.Entry<String, String> parameter : parameters.entrySet()) {
				params.addBodyParameter(parameter.getKey(),
						parameter.getValue());
			}
		}

		if (sfiles == null || sfiles.size() < 1)
			return null;

		List<File> files=new ArrayList<File>();
		
		// 插入图片
		for(String s:sfiles){
			files.add(new File(s));
		}
		
		int key = 0;
		for (File file : files) {
			params.addBodyParameter((key++) + "", file);
			// params.addParameter(entry.getKey(), entry.getValue());
		}

		params.setMultipart(true);

		Cancelable cancelable = x.http().post(params, callback);
		return cancelable;
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            服务器地址
	 * @param saveFilePath
	 *            下载文件将要保存的位置
	 * @param callback
	 *            回调方法 , 可以在这里处理结果和异常
	 * @return
	 */
	public static <T> Cancelable downloadFile(String url, String saveFilePath,
			CommonCallback<T> callback) {
		RequestParams params = new RequestParams(url);
		params.setAutoResume(true);
		params.setSaveFilePath(saveFilePath);

		Cancelable cancelable = x.http().get(params, callback);
		return cancelable;
	}

}
