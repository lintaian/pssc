package com.lps.pssc.module;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;

import com.lps.pssc.dao.BaseDao;
import com.lps.pssc.filter.LoginJsonFilter;
import com.lps.pssc.util.DbMap;
import com.lps.pssc.util.MyHelper;
import com.lps.pssc.util.Page;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@IocBean
@InjectName
@At("/exercise")
@Fail("json")
@Filters({@By(type=LoginJsonFilter.class)})
public class ExerciseModule {
	@Inject
	BaseDao baseDao;
	
	@SuppressWarnings("unchecked")
	@At("")
	@GET
	@Ok("jsp:/tpl/exercise.jsp")
	public Object getExercise(HttpServletRequest req, String id, int page, String parentEle) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		page = page == 0 ? 1 : page;
		List<ObjectId> ids = baseDao.distinct(DbMap.ExerciseDict, "exercise_id", 
				QueryBuilder.start("exercise_package_id").is(new ObjectId(id)).get());
		if (ids.size() > 0) {
			DBObject obj = baseDao.get(DbMap.Exercise, QueryBuilder.start("_id").is(ids.get(page-1)).get());
			rs.put("exercise", obj);
			rs.put("page", new Page(page, 1, ids.size()));
			rs.put("answer", MyHelper.getAnswer(8));
			rs.put("myAnswer", baseDao.get(DbMap.Answer, 
					QueryBuilder.start("courseware_id").is(SessionHelper.getCWid(req))
					.and("exercise_id").is(ids.get(page-1)).and("student_id").
					is(SessionHelper.getUserId(req)).get()));
			rs.put("exercisePackageId", id);
			rs.put("parentEle", parentEle);
		}
		return rs;
	}
	@At("/objective")
	@GET
	@Ok("json")
	public Object updateObjective(HttpServletRequest req, String exerciseId, String answer, String epId, 
			String originAnswer, boolean single, int maxNum) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		if (!(answer.equals(originAnswer) && single)) {
			if (!single) {
				answer = MyHelper.addOrRemoveString(originAnswer, answer, maxNum);
			}
			baseDao.updateOrInsert(DbMap.Answer, 
					QueryBuilder.start("courseware_id").is(SessionHelper.getCWid(req))
					.and("exercise_id").is(new ObjectId(exerciseId)).
					and("student_id").is(SessionHelper.getUserId(req)).
					and("exercise_package_id").is(new ObjectId(epId)).get(), 
					new BasicDBObject("answer", answer).append("answer_date", new Date())
					.append("opt_user", 0),
					new BasicDBObject("first_answer_date", new Date()).append("scores", -1).
					append("note", "").append("teacher_id", ""));
			baseDao.insert(DbMap.AnswerLog, new BasicDBObject("courseware_id", SessionHelper.getCWid(req))
					.append("exercise_id", new ObjectId(exerciseId))
					.append("student_id", SessionHelper.getUserId(req))
					.append("exercise_package_id", new ObjectId(epId))
					.append("answer", answer).append("answer_date", new Date())
					.append("opt_user", 0));
		}
		rs.put("answer", answer);
		return rs;
	}
	
	@At("/subjective")
	@Ok("json")
	@Filters
	public Object uploader(String answer, String eId, String sId, String cId, String epId, int eType, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			ObjectId studentId = sId == null ? SessionHelper.getUserId(req) : new ObjectId(sId);
			ObjectId coursewareId = cId == null ? SessionHelper.getCWid(req) : new ObjectId(cId);
			baseDao.updateOrInsert(DbMap.Answer, 
					QueryBuilder.start("courseware_id").is(coursewareId)
					.and("exercise_id").is(new ObjectId(eId)).and("student_id").
					is(studentId).and("exercise_package_id").is(new ObjectId(epId)).get(), 
					new BasicDBObject("answer", answer).append("answer_date", new Date())
					.append("opt_user", 0),
					new BasicDBObject("first_answer_date", new Date()).append("scores", -1)
					.append("note", "").append("teacher_id", ""));
			baseDao.insert(DbMap.AnswerLog, new BasicDBObject("courseware_id", coursewareId)
					.append("exercise_id", new ObjectId(eId))
					.append("student_id", studentId)
					.append("exercise_package_id", new ObjectId(epId))
					.append("answer", answer).append("answer_date", new Date())
					.append("opt_user", 0));
			rs.put("url", answer);
		} catch (Exception e) {
			rs.put("status", false);
		}
		return rs;
	}
	@At("/canvas")
	@Ok("jsp:/tpl/canvas.jsp")
	public Object getCanvas(HttpServletRequest req, String eid) {
		return eid;
	}
	@At("/canvas/imageTrace")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object insertImageTrace(Map<String, String> obj, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			baseDao.insert(DbMap.PictureTrace, new BasicDBObject("img_url", obj.get("imageUrl")).append("trace", obj.get("trace")).
					append("create_date", new Date()).append("base_url", obj.get("baseUrl")));
		} catch (Exception e) {
			rs.put("status", false);
		}
		return rs;
	}
}
