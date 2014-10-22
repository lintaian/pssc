package com.lps.pssc.dao.interfaces;

import java.util.UUID;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public interface UserDaoIF {
	public DBObject get(String name) throws Exception;
	public void update(DBObject query,DBObject user) throws Exception;
	public DBCursor getList(UUID classId) throws Exception;
}
