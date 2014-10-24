package com.lps.pssc.dao.impl;

import org.nutz.ioc.loader.annotation.IocBean;

import com.lps.pssc.dao.interfaces.RecordDaoIF;
import com.lps.pssc.util.Page;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
@IocBean
public class RecordDao extends BaseDao implements RecordDaoIF {

	@Override
	public DBCursor get(String userId, Page page) throws Exception {
		DBObject query = new BasicDBObject();
		query.put("user_id", userId);
		DBObject keys = new BasicDBObject();
		keys.put("time", "");
		keys.put("operate", "");
		return getCollection("rt_record").find(query, keys).skip(page.skip()).
				limit(page.getPerPage()).sort(new BasicDBObject("time", -1));
	}
	@Override
	public void add(DBObject record) throws Exception {
		getCollection("rt_record").insert(record);
	}
}
