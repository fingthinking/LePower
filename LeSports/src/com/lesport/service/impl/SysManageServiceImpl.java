package com.lesport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.CircleMapper;
import com.lesport.mapper.SysManageMapper;
import com.lesport.model.Circle;
import com.lesport.model.Comment;
import com.lesport.model.ManageCircle;
import com.lesport.service.CircleService;
import com.lesport.service.SysManageService;

@Service
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class SysManageServiceImpl implements SysManageService{

	@Resource
	private SysManageMapper mapper;

	/**
	 * //查看动态信息接口
	 */
	@Override
	public List<ManageCircle> getCircleInfo(String userId, String beginTime, String endTime) {
		return mapper.getCircleInfo(userId, beginTime, endTime);
	}

}
