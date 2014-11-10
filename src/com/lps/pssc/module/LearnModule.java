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
import com.lps.pssc.util.MyHelper;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

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
	@Ok("jsp:/tpl/learn/student.jsp")
	public Object getStudentList(HttpServletRequest req) throws Exception {
		List<DBObject> list = baseDao.query(DbMap.Student, new BasicDBObject("class_id", SessionHelper.getClassId(req))).toArray();
		List<DBObject> rs = new ArrayList<DBObject>();
		for (DBObject dbObject : list) {
			if (SessionHelper.getUserIdStr(req).equals(dbObject.get("_id").toString())) {
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
	@Ok("jsp:/tpl/learn/courseware.jsp")
	public Object getCourseware(HttpServletRequest req, String subjectId, String date) throws Exception {
		DBObject query = new BasicDBObject();
		query.put("class_id", SessionHelper.getClassId(req));
		query.put("valid_time", new BasicDBObject("$lt", new Date()));
		query.put("status", 1);
		if (subjectId != null && !"".equals(subjectId)) {
			query.put("subject_id", new ObjectId(subjectId));
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
		rs.put("months", MyHelper.getMonths(date));
		return rs;
	}
	@SuppressWarnings("unchecked")
	@At("/coursewareDetail")
	@GET
	@Ok("jsp:/tpl/learn/coursewareDetail.jsp")
	public Object getCoursewareDetail(HttpServletRequest req, String id) throws Exception {
		List<DBObject> videoIds = baseDao.distinct(DbMap.CoursewareDict, "item_id", 
				new BasicDBObject("courseware_id", new ObjectId(id)).append("item_type", 0));
		List<DBObject> exerciseIds = baseDao.distinct(DbMap.CoursewareDict, "item_id", 
				new BasicDBObject("courseware_id", new ObjectId(id)).append("item_type", 1));
		List<DBObject> pictureIds = baseDao.distinct(DbMap.CoursewareDict, "item_id", 
				new BasicDBObject("courseware_id", new ObjectId(id)).append("item_type", 2));
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("videoPackages", baseDao.query(DbMap.VideoPackage, 
				QueryBuilder.start("_id").in(videoIds).and("status").is(1).get()).toArray());
		rs.put("exercisePackages", baseDao.query(DbMap.ExercisePackage, 
				QueryBuilder.start("_id").in(exerciseIds).and("status").is(1).get()).toArray());
		rs.put("picturePackages", baseDao.query(DbMap.PicturePackage, 
				QueryBuilder.start("_id").in(pictureIds).and("status").is(1).get()).toArray());
		DBObject cw = baseDao.get(DbMap.Courseware, QueryBuilder.start("_id").is(new ObjectId(id)).get());
		SessionHelper.set(req, "coursewareId", cw.get("_id"));
		SessionHelper.set(req, "coursewareType", cw.get("courseware_type"));
		return rs;
	}
}
