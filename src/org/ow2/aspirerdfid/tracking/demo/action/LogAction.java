package org.ow2.aspirerdfid.tracking.demo.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;
import org.ow2.aspirerdfid.tracking.demo.manage.LogManager;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
@Component
@Scope("prototype")
public class LogAction extends ActionSupport {
	private List<Factory_log> logs;
	private LogManager lm;
	private String start;
	private String end;
	private int id;
	private int offset;
	private int pageSize;
	public String list(){
		logs = this.lm.findLogs();
		return "list";
	}
	
	public String search(){
		if(id==0){
			if(offset==0||pageSize==0){
				logs = this.lm.findLogsByTime(start, end,0,999);
			}else{
				logs = this.lm.findLogsByTime(start, end,offset,pageSize);
			}
		}else{
			logs = this.lm.findLogsById(start, end, id);
		}
		return "search";
	}
	public List<Factory_log> getLogs() {
		return logs;
	}
	public void setLogs(List<Factory_log> logs) {
		this.logs = logs;
	}
	@JSON(serialize=false)
	public LogManager getLm() {
		return lm;
	}
	@Resource
	public void setLm(LogManager lm) {
		this.lm = lm;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
