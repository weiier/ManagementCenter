package org.ow2.aspirerdfid.tracking.demo.dao;

import java.util.List;

import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_info;
//�ֿ���ӿ�
public interface FactoryDao {
	//��ѯ���вֿ�
	public List<Factory_info> getFactorys();
	//����ip��òֿ�״̬�Ϳ��
	public SubSystem getFactoryDetail(String ip);
	//��òֿ�״̬
	public SubSystem getFactoryState(String ip);
	//���ֿ��Ų�ѯ
	public List<Factory_info> getFactorysById(int id);
	//ע��ֿ����
	public int addFactory(Factory_info f);
}
