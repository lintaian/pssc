package com.lps.pssc.module;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

import com.lps.pssc.dao.BaseDao;
import com.lps.pssc.util.DbMap;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;

@IocBean
@InjectName
@At("/class")
@Fail("json")
public class ClassModule {
	@Inject
	BaseDao baseDao;
	
	@At("/updateTime/*")
	@Ok("raw")
	public Object pushPoint(String cid, HttpServletRequest req) {
		try {
			baseDao.update(DbMap.Class, QueryBuilder.start("class_id").is(new ObjectId(cid)).get(), 
					new BasicDBObject("active_date", new Date()));
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}
	
}
