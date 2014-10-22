package com.lps.pssc.dao.impl;

import java.util.UUID;

import org.nutz.ioc.loader.annotation.IocBean;

import com.lps.pssc.dao.interfaces.UserDaoIF;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
@IocBean
public class UserDao extends BaseDao implements UserDaoIF {

	@Override
	public DBObject get(String name) throws Exception {
		DBObject query = new BasicDBObject("UserName", name);
		DBCursor cursor = getCollection("TB_Student").find(query);
		return cursor.count() > 0 ? cursor.next() : null;
	}
	@Override
	public void update(DBObject query, DBObject user) throws Exception {
		getCollection("TB_Student").update(query, user);
	}
	@Override
	public DBCursor getList(UUID classId) throws Exception {
		return getCollection("TB_Student").find(new BasicDBObject("Class_ID", classId));
	}
	
}
