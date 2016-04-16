package com.lesport.mapper;

import java.util.List;

import com.lesport.model.Manager;

/**
 * 
* @ClassName: ManagerMapper 
* @Description: 管理员mapper代理
* @author Qiuzg
* @date 2016年3月7日 上午10:10:50 
*
 */
public interface ManagerMapper {
	public boolean insertManager(Manager manager);//添加管理员接口
	public int deleteManager(String id) throws Exception;//删除管理员接口
	public boolean updateManager(Manager manager) ;//更新管理员接口
	public Manager findManagerById(String id) throws Exception;//通过id查找管理员
	public List<Manager> findAllManager() throws Exception;//查找所有的管理员
}
