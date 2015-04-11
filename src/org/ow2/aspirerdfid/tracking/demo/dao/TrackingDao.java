package org.ow2.aspirerdfid.tracking.demo.dao;

import java.util.List;
import com.zzc.model.QueryParams;
import com.zzc.model.Result;

public interface TrackingDao {
	public List<Result> getTracking(String epcId, QueryParams params);
}
