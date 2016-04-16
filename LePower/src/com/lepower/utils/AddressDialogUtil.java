package com.lepower.utils;


import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lepower.R;
import com.lepower.adapter.ArrayWheelAdapter;
import com.lepower.model.CityModel;
import com.lepower.model.DistrictModel;
import com.lepower.model.ProvinceModel;
import com.lepower.widget.OnWheelChangedListener;
import com.lepower.widget.WheelView;



public class AddressDialogUtil implements OnClickListener,OnWheelChangedListener{
	
	private Activity activity;
	private AlertDialog ad;
	private WheelView mViewProvince;
	private WheelView mViewCity;
//	private WheelView mViewDistrict;
	private LinearLayout addressLayout;
	

	/**
	 * 所有省
	 */
	private String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	private Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 * key - 区 values - 邮编
	 */
	private Map<String, String> mZipcodeDatasMap = new HashMap<String, String>(); 

	/**
	 * 当前省的名称
	 */
	private String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	private String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	private String mCurrentDistrictName ="";

	/**
	 * 当前区的邮政编码
	 */
	private String mCurrentZipCode ="";

	/**
	 * 解析省市区的XML数据
	 */
	
	public void initProvinceDatas()
	{
		List<ProvinceModel> provinceList = null;
		AssetManager asset = activity.getAssets();
		try {
			InputStream input = asset.open("province_data.xml");
			// 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
			//*/ 初始化默认选中的省、市、区
			if (provinceList!= null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList!= null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0).getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			//*/
			mProvinceDatas = new String[provinceList.size()];
			for (int i=0; i< provinceList.size(); i++) {
				// 遍历所有省的数据
				mProvinceDatas[i] = provinceList.get(i).getName();
				List<CityModel> cityList = provinceList.get(i).getCityList();
				String[] cityNames = new String[cityList.size()];
				for (int j=0; j< cityList.size(); j++) {
					// 遍历省下面的所有市的数据
					cityNames[j] = cityList.get(j).getName();
					List<DistrictModel> districtList = cityList.get(j).getDistrictList();
					String[] distrinctNameArray = new String[districtList.size()];
					DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
					for (int k=0; k<districtList.size(); k++) {
						// 遍历市下面所有区/县的数据
						DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
						// 区/县对于的邮编，保存到mZipcodeDatasMap
						mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
						distrinctArray[k] = districtModel;
						distrinctNameArray[k] = districtModel.getName();
					}
					// 市-区/县的数据，保存到mDistrictDatasMap
					mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
				}
				// 省-市的数据，保存到mCitisDatasMap
				mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
			}
		} catch (Throwable e) {  
			e.printStackTrace();  
		} finally {

		} 
	}

	
	
	public AddressDialogUtil(Activity activity){
		this.activity = activity;
	}
	
	
	public void init(){
		
		mViewProvince=(WheelView)addressLayout.findViewById(R.id.id_province);
		mViewCity=(WheelView)addressLayout.findViewById(R.id.id_city);
		//mViewDistrict=(WheelView)addressLayout.findViewById(R.id.id_district);
		
		setUpListener();
		setUpData();
		
	}
	
	
	public AlertDialog addressDialog(final TextView inputDate) {
		 addressLayout = (LinearLayout) activity
				.getLayoutInflater().inflate(R.layout.register_address, null);
		init();

		ad = new AlertDialog.Builder(activity)
		.setTitle("选择")
		.setView(addressLayout)
		.setPositiveButton("设置", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				inputDate.setText(mCurrentProviceName+","+mCurrentCityName);
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				inputDate.setText("");
			}
		}).show();

		return ad;
	}
	
	private void setUpListener() {
    	// 添加change事件
    	mViewProvince.addChangingListener(this);
    	// 添加change事件
    	mViewCity.addChangingListener(this);
    	// 添加change事件
    //	mViewDistrict.addChangingListener(this);
    }
	
	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(activity, mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
	//	mViewDistrict.setVisibleItems(0);
		//mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}


	@Override
	public void onChanged(WheelView wheel,
			int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		}/* else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}*/
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		/*mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(activity, areas));
		mViewDistrict.setCurrentItem(0);*/
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName =mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(activity, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
