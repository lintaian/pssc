package com.lps.pssc.module;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.lps.pssc.filter.LoginJsonFilter;
import com.lps.pssc.util.DbMap;
import com.lps.pssc.util.MonthHelper;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@IocBean
@InjectName
@At("/learn")
@Fail("json")
@Filters({@By(type=LoginJsonFilter.class)})
public class LearnModule {
	@Inject
	BaseDao baseDao;
	
	@At("/")
	@GET
	@Ok("jsp:/tpl/student.jsp")
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
	@SuppressWarnings("deprecation")
	@At("/courseware")
	@GET
	@Ok("jsp:/tpl/courseware.jsp")
	public Object getCourseware(HttpServletRequest req, String subjectId, String date) throws Exception {
		DBObject query = new BasicDBObject();
		query.put("class_id", new ObjectId(SessionHelper.getClassId(req)));
		query.put("valid_time", new BasicDBObject("$lt", new Date()));
		query.put("status", 1);
		if (subjectId != null && !"".equals(subjectId)) {
			query.put("subject_id", subjectId);
		}
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date start = sdf.parse(date);
			Date end = sdf.parse(date);
			end.setMonth(end.getMonth() + 1);
			query.put("create_date", new BasicDBObject("$lt", end).append("$gte", start));
		}
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("cws", baseDao.query(DbMap.Courseware, query).toArray());
		rs.put("subjects", baseDao.query(DbMap.SubjectDict, new BasicDBObject("status", 1)).toArray());
		rs.put("subjectId", subjectId);
		rs.put("months", MonthHelper.getMonths(date));
		return rs;
	}
}
