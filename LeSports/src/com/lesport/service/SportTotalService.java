package com.lesport.service;

import com.lesport.model.SportTotal;


public interface SportTotalService {
	SportTotal getRunInfo(String userId);
	SportTotal getWalkInfo(String userId);
	SportTotal getBikeInfo(String userId);
	SportTotal getPushupInfo(String userId);
	SportTotal getSitupInfo(String userId);
	SportTotal getJumpInfo(String userId);
}
