package com.ws.services;

import javax.jws.WebService;

import org.ow2.aspirerdfid.tracking.demo.db.BriefParams;
import org.ow2.aspirerdfid.tracking.demo.db.ScheduleParams;
import org.ow2.aspirerdfid.tracking.demo.db.DetailParams;
import org.ow2.aspirerdfid.tracking.demo.db.SubSystem;

@WebService
public interface IForwardServices {
	//发送命令
	public abstract boolean instruct(ScheduleParams schedul);
	//汇报 接口自己实现，仓库方向本方回报
	public abstract boolean report(BriefParams params);
	//暂无
	public abstract boolean reportToWareHouse(DetailParams params);
	//注册服务，本方自己实现
	public abstract String registerApply(SubSystem factory);
	//获得第三方物流信息**
	public abstract String expressInfo(String transactionID);
}
	