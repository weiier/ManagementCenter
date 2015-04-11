package org.ow2.aspirerdfid.tracking.demo.manage;

import java.util.List;
import javax.annotation.Resource;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_log;
import org.ow2.aspirerdfid.tracking.demo.dao.LogDao;
import org.springframework.stereotype.Component;

@Component
public class LogManager {
	private LogDao logDao;

	public List<Factory_log> findLogs(){
		return this.logDao.getLogs();
	}
	public List<Factory_log>findLogsByTime(String start,String end,int offset,int pageSize){
		return this.logDao.getLogs(start, end,offset,pageSize);
	}
	public List<Factory_log>findLogsById(String start,String end,int id){
		return this.logDao.getLogs(start, end,id);
	}
	public LogDao getLogDao() {
		return logDao;
	}
	@Resource
	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}
}
