package com.lepower.utils;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.lepower.model.LocationInfo;

/**
 * 获取位置信息(经纬度)
 * @author Administrator
 *
 */
public class LocationUtil {
	
	public static LocationInfo info = null;
	
	public static String provider;
	
	public static LocationInfo getLocation(Context context){
		
		//获取位置管理器的实例
		LocationManager locationManager = (LocationManager)context.getSystemService(
				Context.LOCATION_SERVICE);
		
		//获取所有可用的位置提供器
		List<String> providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER; //GPS
		}else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER; //网络
		}else {
			//当没有可用的位置提供器时,给与用户提示,打开GPS/NETWORK
			ToastUtils.showToast(context, "请打开网络或GPS");
			return null;
		}
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			//获取当前位置信息
			info = new LocationInfo();
			info.setLatitude(Double.toString(location.getLatitude()));
			info.setLongitude(Double.toString(location.getLongitude()));
			Log.d("MainActivity", info.toString());
		}
		return info;
	}
}
