package com.lesport.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;import org.omg.CORBA.COMM_FAILURE;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.ComAndLikeMapper;
import com.lesport.model.ComAndLike;
import com.lesport.model.Manager;
import com.lesport.service.ComAndLikeService;
import com.lesport.util.Utility;

@Service
@Transactional
public class ComAndLikeServiceImpl implements ComAndLikeService {

	@Resource
	private ComAndLikeMapper mapper;
	@Override
	public List<ComAndLike> getCommonAndLike(String circleId) {
		List<ComAndLike> comList = new ArrayList<>();
		List<ComAndLike> likeList = new ArrayList<>();
		List<ComAndLike> finalList = new ArrayList<>();
		try {
			System.out.println("查询前");
			comList = mapper.getCommon(circleId);//获取所有的点赞列表
			likeList = mapper.getLike(circleId);
			System.out.println("查询后");
			for(ComAndLike com : comList){
				com.setFlag("0");
			}
			
			for(ComAndLike like : likeList){
				like.setFlag("1");
			}
			finalList.addAll(comList);
			finalList.addAll(likeList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发生了异常");
			finalList = null;
		}finally {
			return finalList;
		}
	}

}
