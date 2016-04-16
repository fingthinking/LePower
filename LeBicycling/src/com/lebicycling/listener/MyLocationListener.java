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
		 
		// ��һ�δ�ҳ��ʱ������λ��������Ϊ��Ļ����
		if(isFirstLoc){
			isFirstLoc = false;		    	
			// ��ʾ��ǰ��λҳ��
			MapStatus.Builder builder = new MapStatus.Builder();
			// ���õ�ͼ���ĵ�
			builder.target(position).zoom(18.0f);
			baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
		}
	}


}
