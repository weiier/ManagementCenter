package com.ws.services;

import javax.jws.WebService;

import org.ow2.aspirerdfid.tracking.demo.db.BriefParams;
import org.ow2.aspirerdfid.tracking.demo.db.ScheduleParams;
import org.ow2.aspirerdfid.tracking.demo.db.DetailParams;
import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;

@WebService
public interface IForwardServices {
	//��������
	public abstract boolean instruct(ScheduleParams schedul);
	//�㱨 �ӿ��Լ�ʵ�֣��ֿⷽ�򱾷��ر�
	public abstract boolean report(BriefParams params);
	//����
	public abstract boolean reportToWareHouse(DetailParams params);
	//ע����񣬱����Լ�ʵ��
	public abstract String registerApply(SubSystem factory);
	//��õ�����������Ϣ**
	public abstract String expressInfo(String transactionID);
}
	