package com.lesport.service;

import java.util.List;

import com.lesport.model.Manager;

public interface ManagerService {
	public boolean insertManager(Manager manager) throws Exception;//添加管理员接口
	public int deleteManager(String id) throws Exception;//删除管理员接口
	public boolean updateManager(Manager manager) throws Exception;//更新管理员接口
	public Manager findManagerById(String id) throws Exception;//通过id查找管理员
	public List<Manager> findAllManager() throws Exception;//查找所有的管理员
}
