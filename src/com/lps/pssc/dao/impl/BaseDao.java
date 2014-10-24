package com.lps.pssc.dao.impl;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.lps.pssc.config.MongoConfig;
import com.lps.pssc.util.DbMap;
import com.lps.pssc.util.Page;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
@IocBean
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
	/**
	 * 获取一条记录
	 * @param query
	 * @param dbMap
	 * @return
	 */
	public DBObject get(DbMap dbMap, DBObject query) {
		return getCollection(dbMap.getContext()).findOne(query);
	}
	/**
	 * 获取一条记录的某些字段
	 * @param query
	 * @param fields
	 * @param dbMap
	 * @return
	 */
	public DBObject get(DbMap dbMap, DBObject query, DBObject fields) {
		return getCollection(dbMap.getContext()).findOne(query, fields);
	}
	/**
	 * 获取匹配的所有记录
	 * @param query
	 * @param dbMap
	 * @return
	 */
	public DBCursor query(DbMap dbMap, DBObject query) {
		return getCollection(dbMap.getContext()).find(query);
	}
	public DBCursor query(DbMap dbMap, DBObject query, DBObject keys) {
		return getCollection(dbMap.getContext()).find(query, keys);
	}
	public DBCursor queryPage(DbMap dbMap, DBObject query, Page page) {
		return getCollection(dbMap.getContext()).find(query).skip(page.skip()).limit(page.getPerPage());
	}
	public DBCursor queryPage(DbMap dbMap, DBObject query, DBObject keys, Page page) {
		return getCollection(dbMap.getContext()).find(query, keys).skip(page.skip()).limit(page.getPerPage());
	}
	public DBCursor queryPageSort(DbMap dbMap, DBObject query, DBObject sort, Page page) {
		return getCollection(dbMap.getContext()).find(query).skip(page.skip()).limit(page.getPerPage()).sort(sort);
	}
	public DBCursor queryPageSort(DbMap dbMap, DBObject query, DBObject keys, DBObject sort, Page page) {
		return getCollection(dbMap.getContext()).find(query, keys).skip(page.skip()).limit(page.getPerPage()).sort(sort);
	}
	
	public void insert(DbMap dbMap, DBObject... objs) {
		getCollection(dbMap.getContext()).insert(objs);
	}
	public void insert(DbMap dbMap, List<DBObject> objs) {
		getCollection(dbMap.getContext()).insert(objs);
	}
	
	public void remove(DbMap dbMap, DBObject obj) {
		getCollection(dbMap.getContext()).remove(obj);
	}
	
	public void update(DbMap dbMap, DBObject query, DBObject obj) {
		update(dbMap, query, obj, true);
	}
	public void update(DbMap dbMap, DBObject query, DBObject obj, boolean useSet) {
		if (useSet) {
			getCollection(dbMap.getContext()).update(query, new BasicDBObject("$set", obj));
		} else {
			getCollection(dbMap.getContext()).update(query, obj);
		}
	}

	public void update(DbMap dbMap, DBObject query, DBObject obj, boolean upsert, boolean multi) {
		update(dbMap, query, obj, upsert, multi, true);
	}
	public void update(DbMap dbMap, DBObject query, DBObject obj, boolean upsert, boolean multi, boolean useSet) {
		if (useSet) {
			getCollection(dbMap.getContext()).update(query, new BasicDBObject("$set", obj), upsert, multi);
		} else {
			getCollection(dbMap.getContext()).update(query, obj, upsert, multi);
		}
	}
	
	public DBObject updateAndGet(DbMap dbMap, DBObject query, DBObject obj) {
		return updateAndGet(dbMap, query, obj, true);
	}
	public DBObject updateAndGet(DbMap dbMap, DBObject query, DBObject obj, boolean useSet) {
		if (useSet) {
			getCollection(dbMap.getContext()).update(query, new BasicDBObject("$set", obj));
		} else {
			getCollection(dbMap.getContext()).update(query, obj);
		}
		return get(dbMap, query);
	}

	public DBObject updateAndGet(DbMap dbMap, DBObject query, DBObject obj, DBObject query2) {
		return updateAndGet(dbMap, query, obj, query2, true);
	}
	public DBObject updateAndGet(DbMap dbMap, DBObject query, DBObject obj, DBObject query2, boolean useSet) {
		if (useSet) {
			getCollection(dbMap.getContext()).update(query, new BasicDBObject("$set", obj));
		} else {
			getCollection(dbMap.getContext()).update(query, obj);
		}
		return get(dbMap, query2);
	}
	
}
