package com.lps.pssc.dao.impl;

import org.nutz.ioc.loader.annotation.Inject;

import com.lps.pssc.config.MongoConfig;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class BaseDao {
	@Inject("refer:mongoConfig")
	private MongoConfig mongoConfig;
	private Mongo mongo = null;
	
	public DB getDB(String dbName) {
		if (mongo == null) {
			init();
		}
		return mongo.getDB(dbName);
	} 
	
	public DBCollection getCollection(String collectionName) {
		return getDB(mongoConfig.getDb()).getCollection(collectionName);
	}
	
	public DBCollection getCollection(String dbName, String collectionName) {
		return getDB(dbName).getCollection(collectionName);
	}
	
	private void init() {
		try {
			System.out.println("init mongo");
			mongo = new Mongo(mongoConfig.getHost(), mongoConfig.getPort());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
