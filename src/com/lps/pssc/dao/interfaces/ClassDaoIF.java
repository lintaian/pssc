package com.lps.pssc.dao.interfaces;

import com.mongodb.DBObject;

public interface ClassDaoIF {
	public DBObject get(String id) throws Exception;
}
