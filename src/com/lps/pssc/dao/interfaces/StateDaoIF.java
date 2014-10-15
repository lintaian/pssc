package com.lps.pssc.dao.interfaces;

import com.mongodb.DBObject;

public interface StateDaoIF {
	public DBObject get(String userId) throws Exception;
}
