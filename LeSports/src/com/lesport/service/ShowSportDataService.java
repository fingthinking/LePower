package com.lesport.service;

import java.util.List;

import com.lesport.model.SportTotal;

public interface ShowSportDataService {

	public List<SportTotal> showRun();
	public List<SportTotal> showSitUp();
	public List<SportTotal> showPushUp();
	public List<SportTotal> showWalk();
	public List<SportTotal> showBicycle();
	public List<SportTotal> showJump();
}
