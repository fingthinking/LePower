package  com.lebicycling.listener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MyLocationListener implements BDLocationListener {

	private MapView mapView;
	boolean isFirstLoc = true;
	public MyLocationListener(MapView mapview) {
		this.mapView = mapview;
	}
	
	@Override
	public void onReceiveLocation(BDLocation location) {
		
		if(location == null || mapView == null){
			return;
		}
		MyLocationData locData = new MyLocationData.Builder()
		.accuracy(location.getRadius())
		.direction(100)
		.latitude(location.getLatitude())
		.longitude(location.getLongitude())
		.build();
		
		 BaiduMap baiduMap = mapView.getMap();
		
		 LatLng position = new LatLng(location.getLatitude(),
					location.getLongitude());
		
		 baiduMap.setMyLocationData(locData);
		 
		// 第一次打开页面时，将定位的坐标作为屏幕中心
		if(isFirstLoc){
			isFirstLoc = false;		    	
			// 显示当前定位页面
			MapStatus.Builder builder = new MapStatus.Builder();
			// 设置地图中心点
			builder.target(position).zoom(18.0f);
			baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
		}
	}


}
