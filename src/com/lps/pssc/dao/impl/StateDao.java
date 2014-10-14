package com.lps.pssc.dao.impl;

import org.nutz.ioc.loader.annotation.IocBean;

import com.lps.pssc.dao.interfaces.StateDaoIF;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
@IocBean
public class StateDao extends BaseDao implements StateDaoIF {
	@Override
	public DBObject get(String name) throws Exception {
		DBObject query = new BasicDBObject("name", name);
		DBCursor cursor = getCollection("state").find(query);
		return cursor.count() > 0 ? cursor.next() : null;
	}
}
