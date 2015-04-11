package org.ow2.aspirerdfid.tracking.demo.dao;

import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;

public interface ItemDao {
	//根据关键字、ip获得物资
	public SubSystem findMaterials(String keyword,String IP);
	//根据关键字、ip、数量下限获得物资
	public SubSystem findMaterialsWithLimit(String key,String ip,int limit);
}
