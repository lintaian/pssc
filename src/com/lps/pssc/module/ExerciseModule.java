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
				QueryBuilder.start("exercise_batch_id").is(new ObjectId(id)).get());
		if (ids.size() > 0) {
			DBObject obj = baseDao.get(DbMap.Exercise, QueryBuilder.start("_id").is(ids.get(page-1)).get());
			rs.put("exercise", obj);
			rs.put("page", new Page(page, 1, ids.size()));
			rs.put("answer", MyHelper.getAnswer(8));
			rs.put("myAnswer", baseDao.get(DbMap.Answer, 
					QueryBuilder.start("courseware_id").is(SessionHelper.get(req, "coursewareId"))
					.and("exercise_id").is(ids.get(page-1)).and("student_id").
					is(SessionHelper.getUserId(req)).get()));
			rs.put("exerciseBatchId", id);
			rs.put("parentEle", parentEle);
		}
		return rs;
	}
	@At("/objective")
	@GET
	@Ok("json")
	public Object updateObjective(HttpServletRequest req, String exerciseId, String answer, 
			String originAnswer, boolean single, int maxNum) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		if (!(answer.equals(originAnswer) && single)) {
			if (!single) {
				answer = MyHelper.addOrRemoveString(originAnswer, answer, maxNum);
			}
			baseDao.updateOrInsert(DbMap.Answer, 
					QueryBuilder.start("courseware_id").is(SessionHelper.get(req, "coursewareId"))
					.and("exercise_id").is(new ObjectId(exerciseId)).and("student_id").
					is(SessionHelper.getUserId(req)).get(), 
					new BasicDBObject("answer", answer).append("answer_date", new Date()));
			baseDao.insert(DbMap.AnswerLog, new BasicDBObject("courseware_id", SessionHelper.get(req, "coursewareId"))
					.append("exercise_id", new ObjectId(exerciseId))
					.append("student_id", SessionHelper.getUserId(req))
					.append("answer", answer).append("answer_date", new Date()));
		}
		rs.put("answer", answer);
		return rs;
	}
	
	@At("/subjective")
	@Ok("json")
	@Filters
	public Object uploader(String answer, String eId, String sId, String cId, int eType, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			ObjectId studentId = sId == null ? SessionHelper.getUserId(req) : new ObjectId(sId);
			ObjectId coursewareId = cId == null ? (ObjectId)SessionHelper.get(req, "coursewareId") : new ObjectId(cId);
			baseDao.updateOrInsert(DbMap.Answer, 
					QueryBuilder.start("courseware_id").is(coursewareId)
					.and("exercise_id").is(new ObjectId(eId)).and("student_id").
					is(studentId).get(), 
					new BasicDBObject("answer", answer).append("answer_date", new Date()));
			baseDao.insert(DbMap.AnswerLog, new BasicDBObject("courseware_id", coursewareId)
					.append("exercise_id", new ObjectId(eId))
					.append("student_id", studentId)
					.append("answer", answer).append("answer_date", new Date()));
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
	public Object insertImageTrace(HttpServletRequest req, String imageUrl, String trace) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			baseDao.insert(DbMap.PictureTrace, new BasicDBObject("img_url", imageUrl).append("trace", trace).
					append("time", new Date()));
		} catch (Exception e) {
			rs.put("status", false);
		}
		return rs;
	}
}
