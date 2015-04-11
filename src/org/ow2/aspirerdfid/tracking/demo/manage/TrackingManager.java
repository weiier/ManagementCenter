package org.ow2.aspirerdfid.tracking.demo.manage;

import java.util.List;
import javax.annotation.Resource;
import org.ow2.aspirerdfid.tracking.demo.dao.TrackingDao;
import org.springframework.stereotype.Component;
import com.zzc.model.QueryParams;
import com.zzc.model.Result;

@Component
public class TrackingManager {
	private TrackingDao trackingDao;
	public List<Result> queryTracking(String epcId, QueryParams params){
		return trackingDao.getTracking(epcId, params);
	}
	public TrackingDao getTrackingDao() {
		return trackingDao;
	}
	@Resource
	public void setTrackingDao(TrackingDao trackingDao) {
		this.trackingDao = trackingDao;
	}
	
	
}
