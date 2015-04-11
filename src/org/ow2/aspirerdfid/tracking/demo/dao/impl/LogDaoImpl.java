package org.ow2.aspirerdfid.tracking.demo.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.ow2.aspirerdfid.tracking.demo.dao.LogDao;
import org.ow2.aspirerdfid.tracking.demo.model.Factory_log;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
@Component("logDao")
public class LogDaoImpl  implements LogDao {
	private HibernateTemplate hibernateTemplate;
	@Override
	public void addLog(Factory_log log) {
			this.hibernateTemplate.save(log);
	}
	
	@Override
	public List<Factory_log> getLogs() {
		return this.hibernateTemplate.find("from Factory_log");
	}
	
	@Override
	public List<Factory_log> getLogs(String start, String end,int offset,int pageSize) {
		String hql = "from Factory_log l where l.stateTime between '"+start+"' and '"+end+"'";
		return this.findByPage(hql, null, offset, pageSize);
	}
	/**
	 * find logs by offset and pageSize,support pagination
	 * @param hql
	 * @param values
	 * @param offset
	 * @param pageSize
	 * @return logs
	 */
	public List<Factory_log> findByPage(final String hql,final Object[] values,
			final int offset,final int pageSize){
		List list = this.hibernateTemplate.executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query =  session.createQuery(hql);
				if(values!=null){
				for(int i=0;i<values.length;i++){
					query.setParameter(i, values[i]);
				}}
				List result = query.setFirstResult(offset)
						.setMaxResults(pageSize)
						.list();
				return result;
			}
		});
			
		return list;
	}
	
	@Override
	public List<Factory_log> getLogs(String start, String end, int id) {
		return this.hibernateTemplate.find("from Factory_log l where l.factory.id='"+id+"' and l.stateTime between '"
				+start+"' and '"+end+"'");
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
