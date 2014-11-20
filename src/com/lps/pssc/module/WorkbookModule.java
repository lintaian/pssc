package com.lps.pssc.module;

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

import com.lps.pssc.dao.BaseDao;
import com.lps.pssc.filter.LoginJsonFilter;
import com.lps.pssc.util.DateHelper;
import com.lps.pssc.util.DbMap;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@IocBean
@InjectName
@At("/workbook")
@Fail("json")
@Filters({@By(type=LoginJsonFilter.class)})
public class WorkbookModule {
	@Inject
	BaseDao baseDao;
	
	@At("")
	@GET
	@Ok("jsp:/tpl/workbook/exerciseList.jsp")
	public Object getCourseware(HttpServletRequest req, String subjectId, int year, int month, int day, boolean init) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		if (init) {
			Date d = new Date();
			year = DateHelper.getYear(d);
			month = DateHelper.getMonth(d);
		}
		QueryBuilder qb = QueryBuilder.start("student_id").is(SessionHelper.getUserId(req));
		Date start = DateHelper.getStartDate(year, month, day);
		if (start != null) {
			qb.and("first_answer_date").greaterThanEquals(start);
		}
		Date end = DateHelper.getEndDate(year, month, day);
		if (end != null) {
			qb.and("first_answer_date").lessThan(end);
		}
		List<DBObject> ans = baseDao.query(DbMap.Answer, qb.get()).toArray();
		List<DBObject> exercises = new ArrayList<DBObject>();
		for (DBObject dbObject : ans) {
			QueryBuilder qb2 = QueryBuilder.start("_id").is(dbObject.get("exercise_id")).
					and("exercise_type").lessThan(30);
			if (subjectId != null && !"".equals(subjectId)) {
				qb2.and("subject_id").is(new ObjectId(subjectId));
			}
			DBObject temp = baseDao.get(DbMap.Exercise, qb2.get());
			if (temp != null) {
				dbObject.put("my_ex", temp);
				exercises.add(dbObject);
			}
		}
		rs.put("exercises", exercises);
		rs.put("subjects", baseDao.query(DbMap.SubjectDict, new BasicDBObject("status", 1)).toArray());
		rs.put("subjectId", subjectId);
		rs.put("date", DateHelper.getDate(year, month, day));
		return rs;
	}
	@At("/exercise/*")
	@GET
	@Ok("jsp:/tpl/workbook/exerciseDetail.jsp")
	public Object getCoursewareDetail(String id, HttpServletRequest req) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		DBObject answer = baseDao.get(DbMap.Answer, QueryBuilder.start("_id").is(new ObjectId(id)).get());
		DBObject cw = baseDao.get(DbMap.Courseware, QueryBuilder.start("_id").is(answer.get("courseware_id")).get());
		DBObject ex = baseDao.get(DbMap.Exercise, QueryBuilder.start("_id").is(answer.get("exercise_id")).get());
		DBObject ep = baseDao.get(DbMap.ExercisePackage, QueryBuilder.start("_id").is(answer.get("exercise_package_id")).get());
		DBObject sj = baseDao.get(DbMap.SubjectDict, QueryBuilder.start("_id").is(ex.get("subject_id")).get());
		rs.put("a", answer);
		rs.put("cw", cw);
		rs.put("ex", ex);
		rs.put("ep", ep);
		rs.put("sj", sj);
		return rs;
	}
	@At("/exercise/answerlog/*")
	@GET
	@Ok("jsp:/tpl/workbook/answerLog.jsp")
	public Object getAnswerLog(String id, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		DBObject answer = baseDao.get(DbMap.Answer, QueryBuilder.start("_id").is(new ObjectId(id)).get());
		DBObject ex = baseDao.get(DbMap.Exercise, QueryBuilder.start("_id").is(answer.get("exercise_id")).get());
		List<DBObject> als = baseDao.querySort(DbMap.AnswerLog, QueryBuilder.start("student_id").is(answer.get("student_id")).
				and("courseware_id").is(answer.get("courseware_id")).
				and("exercise_package_id").is(answer.get("exercise_package_id")).
				and("exercise_id").is(answer.get("exercise_id")).get(), 
				new BasicDBObject("answer_date", -1)).toArray();
		rs.put("als", als);
		rs.put("ex", ex);
		return rs;
	}
}
