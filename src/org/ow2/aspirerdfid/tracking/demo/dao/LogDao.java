package org.ow2.aspirerdfid.tracking.demo.dao;

import java.util.List;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_log;

public interface LogDao {
	//加入仓库日志
	public void addLog(Factory_log log);
	
	public List<Factory_log> getLogs();
	
	public List<Factory_log> getLogs(String start,String end,int offset,int pageSize);
	
	public List<Factory_log> getLogs(String start,String end,int id);
}
