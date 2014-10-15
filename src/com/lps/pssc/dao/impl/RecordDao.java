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
		query.put("userId", userId);
		DBObject keys = new BasicDBObject();
		keys.put("time", "");
		keys.put("operate", "");
		return getCollection("record").find(query, keys).skip(page.skip()).limit(page.getPerPage());
	}
	
}
