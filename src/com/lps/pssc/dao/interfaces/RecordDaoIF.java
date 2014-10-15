package com.lps.pssc.dao.interfaces;

import com.lps.pssc.util.Page;
import com.mongodb.DBCursor;

public interface RecordDaoIF {
	public DBCursor get(String userId, Page page) throws Exception;
}
