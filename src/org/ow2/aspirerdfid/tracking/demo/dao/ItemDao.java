package org.ow2.aspirerdfid.tracking.demo.dao;

import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;

public interface ItemDao {
	//���ݹؼ��֡�ip�������
	public SubSystem findMaterials(String keyword,String IP);
	//���ݹؼ��֡�ip���������޻������
	public SubSystem findMaterialsWithLimit(String key,String ip,int limit);
}
