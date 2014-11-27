package com.lps.pssc.util;

import java.util.Date;
import java.util.TimerTask;
import org.nutz.mvc.Mvcs;
import com.lps.pssc.config.ActiveConfig;
import com.lps.pssc.dao.BaseDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public class UpdateClassStatus extends TimerTask {
	
	@Override
	public void run() {
		try {
			ActiveConfig config = Mvcs.ctx.getDefaultIoc().get(ActiveConfig.class);
			BaseDao baseDao = Mvcs.ctx.getDefaultIoc().get(BaseDao.class);
			Date now = new Date(System.currentTimeMillis() - config.getTimeout());
			DBObject aa = QueryBuilder.start("active_date").lessThan(now)
					.and("status").notEquals(0).get();
			baseDao.update(DbMap.Class, aa, 
					new BasicDBObject("status", 0), false, true);
		} catch (Exception e) {
		}
	}
}
