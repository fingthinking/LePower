package com.lesport.service;

import java.util.List;

import com.lesport.model.Circle;
import com.lesport.model.ManageCircle;


public interface SysManageService {
	List<ManageCircle> getCircleInfo(String userId,String beginTime,String endTime);
}
