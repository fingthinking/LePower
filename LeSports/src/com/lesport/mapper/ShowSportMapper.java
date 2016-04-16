package com.lesport.mapper;

import java.util.List;

import com.lesport.model.SportTotal;

public interface ShowSportMapper {

	public List<SportTotal> showRun();
	public List<SportTotal> showWalk();
	public List<SportTotal> showSitUp();
	public List<SportTotal> showPushUp();
	public List<SportTotal> showBicycle();
	public List<SportTotal> showJump();
}
