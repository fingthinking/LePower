package com.lesport.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lesport.mapper.ManagerMapper;
import com.lesport.model.Manager;
import com.lesport.service.ManagerService;
import com.lesport.util.Utility;

/**
 * 
 * @ClassName: ManagerServiceImpl
 * @Description: TODO
 * @author Qiuzg
 * @date 2016年3月7日 上午11:29:49
 *
 */
@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

	@Resource
	private ManagerMapper mapper;

	@Override
	public boolean insertManager(Manager manager) {
			return mapper.insertManager(manager);
	}

	@Override
	public int deleteManager(String id) {

		int row = -1;
		try {
			row = mapper.deleteManager(id);
		} catch (Exception e) {
			System.out.println("发生了异常");
			row = 0;
		} finally {
			return row;
		}
	}

	@Override
	public boolean updateManager(Manager manager) {
		return mapper.updateManager(manager);
	}

	@Override
	public Manager findManagerById(String id) {
		Manager manager = null;
		try {
			manager = mapper.findManagerById(id);
		} catch (Exception e) {
			manager = null;
		} finally {
			return manager;
		}
	}

	@Override
	public List<Manager> findAllManager() {

		List<Manager> allManager = new ArrayList<>();
		try {
			allManager = mapper.findAllManager();
		} catch (Exception e) {
			System.out.println("发生了异常");
			allManager = null;
		} finally {
			return allManager;
		}
	}

}
