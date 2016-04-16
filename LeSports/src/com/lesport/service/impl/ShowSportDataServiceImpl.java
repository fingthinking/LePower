package com.lesport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.ShowSportMapper;
import com.lesport.model.SportTotal;
import com.lesport.service.ShowSportDataService;

@Service
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class ShowSportDataServiceImpl implements ShowSportDataService {

	@Resource
	private ShowSportMapper mapper;
	@Override
	public List<SportTotal> showRun() {
		// TODO Auto-generated method stub
		List<SportTotal> sportRun=mapper.showRun();
		return sportRun;
	}

	@Override
	public List<SportTotal> showSitUp() {
		// TODO Auto-generated method stub
		List<SportTotal> sportSitUp=mapper.showSitUp();
		return sportSitUp;
	}

	@Override
	public List<SportTotal> showPushUp() {
		// TODO Auto-generated method stub
		List<SportTotal> sportPushUp=mapper.showPushUp();
		return sportPushUp;
	}

	@Override
	public List<SportTotal> showWalk() {
		// TODO Auto-generated method stub
		List<SportTotal> sportWalk=mapper.showWalk();
		return sportWalk;
	}

	@Override
	public List<SportTotal> showBicycle() {
		// TODO Auto-generated method stub
		List<SportTotal> sportBicycle=mapper.showBicycle();
		return sportBicycle;
	}

	@Override
	public List<SportTotal> showJump() {
		// TODO Auto-generated method stub
		List<SportTotal> sportJump=mapper.showJump();
		return sportJump;
	}

}
