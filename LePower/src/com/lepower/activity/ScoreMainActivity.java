package com.lepower.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lepower.R;
import com.lepower.dao.IUserDao;
import com.lepower.dao.impl.UserDaoImpl;
import com.lepower.minterface.HttpCallbackListener;
import com.lepower.model.SportRecord;
import com.lepower.model.User;
import com.lepower.utils.DataFormatUtil;
import com.lepower.utils.HttpRequest;
import com.lepower.utils.InternetState;
import com.lepower.utils.LeUrls;
import com.lepower.widget.ScoreMainListItemView;

public class ScoreMainActivity extends Activity {
	//显示六种运动概要的自定义View
	private ScoreMainListItemView smlivWalk, smlivRunning, smlivBicyling,smlivJumpRope, smlivPushUp, smlivSitUp;
	//头像
	private ImageView rivHead;
	//所在位置城市信息，天气和气温
	private TextView tvNowAddress,tvTemp,tvNickName,tvSportsMan;
	private ImageView ivWeatherBg;
	//天气key,15天10000次请求，会员
	private String WEATHER_KEY="sdxupav7dozqj5sf";//我的用户id U88D6D0372
	//获取城市名称信息和天气都要用到这个对象，包含了地址的经纬度信息
	private Location mLocation;
	private LocationManager locationManager;
	//默认的城市名
	private String provinceCityNames="江苏-苏州";
	private String weatherString="晴";
	private static final int UPDATE_CITY_WEATHER=1;
	private static final int UPDATE_ERROR = 2;
	private static final int UPDATE_SPORT_RECORED=3;
	//当前用户
	private User userNow;
	private String userIDNow;
	
	private List<SportRecord> sportRecordList;
	
	private String urlImage;
	private SharedPreferences sharedPreferences;
	private String sharedPreName="scoreData";
	private ImageOptions imageOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_main);
		//初始化View
		initView();
		//开启向后台发送经纬度的服务
		Intent startIntent = new Intent(this,LocationService.class);
		startService(startIntent);
		sharedPreferences=getSharedPreferences(sharedPreName, MODE_PRIVATE);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    //网络可用即可获取地址信息//获取地址信息
		if(InternetState.isNetWorkConnected(this)){
//			getLocation();
		}else{
			Toast.makeText(this, "网络不可用，请检查网络连接", 0).show();
		}
		//获取当前登录的用户uid，从而进行头像设置，成绩查询并更新到UI
		//初始化当前用户
		initUserNow();
		//成绩查询
		if(userIDNow!=null){
			sportRecordList=new ArrayList();
			if(InternetState.isNetWorkConnected(this)){
				getScoreByUserId(userIDNow);
			}else{
				String scoreData=sharedPreferences.getString("ScoreData","");
				json2RecordList(userIDNow, scoreData);
			}
		}
	}
	
	/*
	 * 获取成绩信息
	 */
	private void getScoreByUserId(final String userId){
		//构造接口请求地址
		String urlString=LeUrls.SCORE_QUERY+userId;
//		Log.d("heheda", "请求的url"+urlString);
		HttpRequest.sendHttpRequest(new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				Log.d("heheda", "返回的额json数据"+response);
				//获取到数据，保存
				Editor editor=sharedPreferences.edit();
				editor.putString("ScoreData", response);
				editor.commit();
				//对数据进行解析
				json2RecordList(userId, response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
			}
		}, urlString);
	}
	
	/**
	 * 初始化当前用户，把当前用户取到，并将其简要信息展示到UI上
	 */
	private void initUserNow(){
		IUserDao iUserDao=new UserDaoImpl();
		userNow=iUserDao.getUserNow();
		if(userNow==null){
			return ;
		}
		urlImage = userNow.getImgURL();
		//设置ID和昵称,id先保存起来
		userIDNow=userNow.getUserId();
		userNow.getNickName();
		tvNickName.setText(userNow.getNickName());
		//获取头像信息，并设置
		imageOptions = new ImageOptions.Builder()
		.setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))
		//设置显示圆形图片
		.setCircular(true)
		//设置使用缓存
		.setUseMemCache(true)
		//设置支持gif
		.setIgnoreGif(false)
		.build();
		x.image().bind(rivHead, urlImage, imageOptions);
	}
	
	/*
	 * 获取地址location对象，得到经纬度信息，并通过其找到城市的详细信息
	 */
	private void getLocation() {
		//获取地址，并把它转换成城市名称
//		Log.d("heheda", "获取地址经纬度对象");
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String provider=null;
		List<String> providerList = locationManager.getProviders(true);
//		Log.d("heheda", "可用的provider"+providerList.toString());
		
		if(providerList.contains(LocationManager.GPS_PROVIDER)) {//使用GPS
			provider = LocationManager.GPS_PROVIDER;
		}else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {//使用网络定位
			provider = LocationManager.NETWORK_PROVIDER;
		}else {
			// 权限不足，无法获取地址信息,说明手机没有提供位置信息的权限，没有可用的provider
			Toast.makeText(ScoreMainActivity.this, "没有GPS或者网络，无法获取地址信息,请开启", 0);
			return;
		}
		//获取地址
		mLocation=locationManager.getLastKnownLocation(provider);
		//如果地址对象不空，获取天气
		if(mLocation!=null){
			getWeather(mLocation);
		}else{
			
			Toast.makeText(ScoreMainActivity.this, "定位失败无法获天气情况", 0);
		}
		//定位监听
		locationManager.requestLocationUpdates(provider, 0, 1, new LocationListener() {
			
			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			}
			
			@Override
			public void onProviderEnabled(String arg0) {
			}
			
			@Override
			public void onProviderDisabled(String arg0) {
			}
			
			@Override
			public void onLocationChanged(Location location) {
				mLocation=location;
				getWeather(mLocation);
			}
		});

	}
	/**
	 * 初始化View
	 */
	private void initView() {
		smlivWalk = (ScoreMainListItemView) findViewById(R.id.smlivWalk);
		smlivRunning = (ScoreMainListItemView) findViewById(R.id.smlivRunning);
		smlivBicyling = (ScoreMainListItemView) findViewById(R.id.smlivBicyling);
		smlivJumpRope = (ScoreMainListItemView) findViewById(R.id.smlivJumpRope);
		smlivPushUp = (ScoreMainListItemView) findViewById(R.id.smlivPushUp);
		smlivSitUp = (ScoreMainListItemView) findViewById(R.id.smlivSitUp);
		
		tvNowAddress=(TextView) findViewById(R.id.tvNowAddress);
		tvTemp=(TextView) findViewById(R.id.tvTemp);
		tvNickName=(TextView) findViewById(R.id.tvNickName);
		tvSportsMan=(TextView) findViewById(R.id.tvSportsMan);
		
		
		ivWeatherBg=(ImageView) findViewById(R.id.ivWeatherBg);
		rivHead = (ImageView) findViewById(R.id.rivHead);
	}
	/**
	 * 点击事件处理
	 */
	private void initOnClick(){
		//点击天气背景图片，更新地址和天气
		ivWeatherBg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//网络是否可用,点击更新地址和天气
				if(mLocation!=null){
					getWeather(mLocation);
				}
			}
		});
	}
	/**
	 * 获取天气信息
	 */
	public void getWeather(Location location) {
		// TODO Auto-generated method stub   这里要先判断location是否为空，直接在外面判断
		//根据经纬度，获取天气,url如下
		//https://api.thinkpage.cn/v3/weather/now.json?key=sdxupav7dozqj5sf&location=beijing&language=zh-Hans&unit=c
		//location参数可以是经纬度,下面的经纬度格式lat:lng
		//然后构造天气请求的url
		String urlWeather="https://api.thinkpage.cn/v3/weather/now.json?key="+WEATHER_KEY+"&location="+
				location.getLatitude()+":"+location.getLongitude()+"&language=zh-Hans&unit=c";
		//发送网络请求，请求天气数据，传入回调函数
		HttpRequest.sendHttpRequest(new WeatherHttpCallbackListener(), urlWeather);
	}
	/**
	 * 获取天气的listener，在这里面解析天气数据，并更细UI
	 * @author Flyer
	 *
	 */
	public class WeatherHttpCallbackListener implements HttpCallbackListener{

		@Override
		public void onFinish(String response) {
			// TODO Auto-generated method stub
			try {
				/*
				//使用会员返回的数据
				{"results":
					[{	"now":{"wind_scale":"3","text":"多云","humidity":"31","pressure":"1019","wind_direction":"西南","visibility":"9.4","wind_direction_degree":"225","wind_speed":"14.04","code":"4","feels_like":"12","temperature":"12"},
						"location":{"path":"惠民,滨州,山东,中国","id":"WWET1Z0BU6QH","timezone_offset":"+08:00","timezone":"Asia\/Shanghai","country":"CN","name":"惠民"},
						"last_update":"2016-03-15T12:15:00+08:00"
					}]
				}
				 */
				JSONObject jsonObject;
				jsonObject = new JSONObject(response);
				Log.d("weather", "天气数据"+jsonObject.toString());
				JSONArray resultsArray = jsonObject.getJSONArray("results");
				JSONObject resultObject=resultsArray.getJSONObject(0);
				JSONObject nowObject = resultObject.getJSONObject("now");
				JSONObject locationObject=resultObject.getJSONObject("location");
				
				String text=nowObject.getString("text");//当前天气
				String temperature=nowObject.getString("temperature");//当前气温
//				String nameLocation=locationObject.getString("name");
				String pathLocation=locationObject.getString("path");
				String[] locationNames=pathLocation.split(",");
				//天气信息里面有地址
				//如果是直辖市，有可能是 北京 北京 中国，也有可能是 海淀 北京 中国，如果是其它地方，有可能是 南京 南京 江苏 中国 ，也可以是 长兴 湖州 浙江 中国
				//经过测试，只有两种长度，3和4,基本有下面几种情况
				/*
				 * 1、北京 北京 中国 2、香港 香港 香港3、海淀 北京 中国 
				 * 4、 南京 南京 江苏 中国 5、 长兴 湖州 浙江 中国6、台北 台北 台湾 （不清楚）7、XX XX 台湾 台湾
				 * 所以归纳为以下三种情况
				 */
				if(!locationNames[0].equals(locationNames[1])){
					provinceCityNames=locationNames[1]+"-"+locationNames[0];
				}else if(!locationNames[1].equals(locationNames[2])){
					provinceCityNames=locationNames[2]+"-"+locationNames[1];
				}else{
					provinceCityNames=locationNames[2];
				}
				weatherString=text+"  "+temperature+" ℃";
				//通知更新UI
				Message msg=new Message();
				msg.what=UPDATE_CITY_WEATHER;
				handler.sendMessage(msg);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onError(Exception e) {
			// TODO Auto-generated method stub
			Message msg=new Message();
			msg.what=UPDATE_ERROR;
			handler.sendMessage(msg);
		}
		
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case UPDATE_CITY_WEATHER:
				//显示在UI上
				tvTemp.setText(weatherString);
				//设置地址信息
				tvNowAddress.setText(provinceCityNames);
				break;
			case UPDATE_ERROR:
				Toast.makeText(ScoreMainActivity.this, "无法获取数据,请稍后再试", 0);
				break;
			case UPDATE_SPORT_RECORED:
				//说明这个已经更新了list，可以显示在view中了
				/*
				 * 遍历整个list，将数据取出来，并显示在UI上
				 * 
				 * 下面
				 */
				for(SportRecord sr : sportRecordList){
					//下面还得加上单位换算
					String sportCount=sr.getSportCount()+"次";
					String sportDis=sr.getSportDis();//+"m"
					String sportsNum=sr.getSportNum()+"个";
					String sportTime=sr.getSportTime();//+"h"
					String sportCal=sr.getSportCal();//+"kCal"
					String sportSteps=sr.getSportSteps()+"步";
					
					if(sr.getType().equals("run")){
						//跑步记录、总次数、总距离、总时间/s、总消耗/kCal、本月跑量、""
						smlivRunning.setMyScoresAttrs("跑步记录",sportCount, sportDis+"km", DataFormatUtil.formatDataTimeS(sportTime), sportCal+"kCal", "本月跑量","");
					}else if(sr.getType().equals("walk")){
						smlivWalk.setMyScoresAttrs("步行记录", sportCount, sportDis+"m", DataFormatUtil.getTimeFromMili(Long.parseLong(sportTime)), sportCal+"kCal", "总步数",sportSteps);
					}else if(sr.getType().equals("bike")){
						smlivBicyling.setMyScoresAttrs("骑行记录",sportCount, sportDis+"km", DataFormatUtil.formatDataTimeM(sportTime), sportCal+"kCal", "本月数据","");
					}else if(sr.getType().equals("pushup")){
						smlivPushUp.setMyScoresAttrs("俯卧撑",sportCount, sportsNum, "-", sportCal+"kCal", "本月数据","");
					}else if(sr.getType().equals("jump")){
						smlivJumpRope.setMyScoresAttrs("跳绳记录",sportCount, sportsNum, "-", sportCal+"kCal", "本月数据","");
					}else if(sr.getType().equals("situp")){
						smlivSitUp.setMyScoresAttrs("仰卧起坐",sportCount, sportsNum, "-", DataFormatUtil.formatDataCal2KCal(sportCal), "本月数据","");
					}
				}
				break;
			default:
				break;
			}
		}
		
	};
	/**
	 * 解析数据，并保存到listView中
	 * @param userId
	 * @param response
	 */
	private void json2RecordList(final String userId, String response) {
		try {
			JSONObject resultObject=new JSONObject(response);
			
			JSONArray resultDataArray=resultObject.getJSONArray("data");
			JSONObject data;
			for(int i=0;i<resultDataArray.length();i++){
				data=resultDataArray.getJSONObject(i);
				SportRecord sportRecord=new SportRecord(userId, 
						DataFormatUtil.formatData(data.getString("sportCal")),
						DataFormatUtil.formatData(data.getString("sportCount")),
						DataFormatUtil.formatData(data.getString("sportDis")),
						DataFormatUtil.formatData(data.getString("sportNum")),
						DataFormatUtil.formatData(data.getString("sportSteps")),
						DataFormatUtil.formatData(data.getString("sportTime")),
						DataFormatUtil.formatData(data.getString("type"))
						);
				sportRecordList.add(sportRecord);
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message msg=new Message();
		msg.what=UPDATE_SPORT_RECORED;
		handler.sendMessage(msg);
	}
	
	public void onClick2UserInfo(View view){
		onClick2UserInfoAct();
	}
	private void onClick2UserInfoAct(){
		Intent intent =new Intent(ScoreMainActivity.this,UpdateUserInfoActivity.class);
		startActivity(intent);
	}

}
