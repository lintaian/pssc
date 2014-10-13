package com.lps.pssc.dao.interfaces;

import com.mongodb.DBObject;

public interface UserDaoIF {
	public DBObject get(String name) throws Exception;
}
