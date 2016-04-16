package com.lesport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.CircleManageMapper;
import com.lesport.model.Circle;
import com.lesport.service.CircleManageService;

@Service
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class CircleManageServiceImpl implements CircleManageService{

	@Resource
	private CircleManageMapper mapper;

	//获取每页动态列表
	@Override
	public List<Circle> getCircleList(String nickName,int offset, int pageSizeInt) {
		return  mapper.getCircleList(nickName,offset, pageSizeInt);		
	}
	
	//获取乐友圈动态总数
	@Override
	public int getCountOfCircle(String nickName) {
		return mapper.getCountOfCircle(nickName);
	}

	@Override
	public List<Circle> findCirByName(String nickName) {
		return mapper.findCirByName(nickName);
	}

	@Override
	public boolean deleteCircle(String circleId) {
		return mapper.deleteCircle(circleId);
	}
	
}
