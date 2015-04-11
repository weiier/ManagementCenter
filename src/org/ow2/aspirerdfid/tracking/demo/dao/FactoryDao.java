package org.ow2.aspirerdfid.tracking.demo.dao;

import java.util.List;

import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;
//仓库类接口
public interface FactoryDao {
	//查询所有仓库
	public List<Factory_info> getFactorys();
	//根据ip获得仓库状态和库存
	public SubSystem getFactoryDetail(String ip);
	//获得仓库状态
	public SubSystem getFactoryState(String ip);
	//按仓库编号查询
	public List<Factory_info> getFactorysById(int id);
	//注册仓库服务
	public int addFactory(Factory_info f);
}
