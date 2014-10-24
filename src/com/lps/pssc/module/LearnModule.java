package com.lps.pssc.module;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

import com.lps.pssc.dao.impl.BaseDao;
import com.lps.pssc.filter.LoginFilter;
import com.lps.pssc.util.DbMap;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@IocBean
@InjectName
@At("/learn")
@Fail("json")
@Filters({@By(type=LoginFilter.class)})
public class LearnModule {
	@Inject
	BaseDao baseDao;
	
	@At("/")
	@GET
	@Ok("jsp:/tpl/studentList.jsp")
	public Object getStudentList(HttpServletRequest req) throws Exception {
		String classId = SessionHelper.getClassId(req);
		List<DBObject> list = baseDao.query(DbMap.Student, new BasicDBObject("class_id", new ObjectId(classId))).toArray();
		List<DBObject> rs = new ArrayList<DBObject>();
		for (DBObject dbObject : list) {
			if (SessionHelper.getUserId(req).equals(dbObject.get("_id").toString())) {
				rs.add(0, dbObject);
			} else {
				rs.add(dbObject);
			}
		}
		return rs;
	}
	@At("/courseware")
	@GET
	@Ok("jsp:/tpl/coursewareList.jsp")
	public Object getCourseware(HttpServletRequest req) throws Exception {
		String classId = SessionHelper.getClassId(req);
		return null;
	}
}
