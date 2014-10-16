package com.lps.pssc.dao.impl;

import org.nutz.ioc.loader.annotation.IocBean;

import com.lps.pssc.dao.interfaces.UserDaoIF;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
@IocBean
public class UserDao extends BaseDao implements UserDaoIF {

	@Override
	public DBObject get(String name) throws Exception {
		DBObject query = new BasicDBObject("name", name);
		DBCursor cursor = getCollection("user").find(query);
		return cursor.count() > 0 ? cursor.next() : null;
	}
	@Override
	public void update(DBObject query, DBObject user) throws Exception {
		getCollection("user").update(query, user);
	}
	
}
