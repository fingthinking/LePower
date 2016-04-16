package com.lesport.service;

import java.util.List;

import com.lesport.model.SportData;
import com.lesport.model.UserInfo;


public interface SportDataService {
	List<SportData> getSportData(String userId,String beginDate,String endDate);
	UserInfo getRunDis(String userId,String date);
	List<UserInfo> getRunRange(String date);
}
