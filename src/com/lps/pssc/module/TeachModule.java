package com.lps.pssc.module;

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
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@IocBean
@InjectName
@At("/teach")
@Fail("json")
@Filters({@By(type=LoginJsonFilter.class)})
public class TeachModule {
	@Inject
	BaseDao baseDao;
	
	@At("")
	@GET
	@Ok("jsp:/tpl/teach/classRoom.jsp")
	public Object getIndex(HttpServletRequest req, String currentClass, String coursewareId) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("currentClass", (currentClass == null || "".equals(currentClass))? "p_unTeach" : currentClass) ;
		if (coursewareId != null && !"".equals(coursewareId)) {
			DBObject cw = baseDao.get(DbMap.Courseware, QueryBuilder.start("_id").is(new ObjectId(coursewareId)).and("status").is(1).get());
			SessionHelper.set(req, "coursewareId", cw.get("_id"));
			SessionHelper.set(req, "coursewareType", cw.get("courseware_type"));
		}
		return rs;
	}

	@At("/state")
	@GET
	@Ok("json")
	public Object getState(HttpServletRequest req, boolean init) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		DBObject c = baseDao.get(DbMap.Class, QueryBuilder.start("class_id").is(SessionHelper.getClassId(req)).get());
		rs.put("status", false);
		if (c != null) {
			rs.put("status", true);
			rs.put("c_status", c.get("status"));
			rs.put("c_photo", c.get("photo"));
			rs.put("c_title", c.get("title"));
			rs.put("courseware_id", c.get("courseware_id").toString());
			if (!"".equals(c.get("status").toString()) && Integer.parseInt(c.get("status").toString()) == 2) {
				List<DBObject> cos = baseDao.query(DbMap.ClassOperate, 
						QueryBuilder.start("student_id").is(SessionHelper.getUserId(req))
						.and("class_id").is(SessionHelper.getClassId(req)).
						and("courseware_id").is(c.get("courseware_id"))
						.and("content_status").is(0).get()).toArray();
				DBObject current = baseDao.get(DbMap.ClassOperate, 
						QueryBuilder.start("student_id").is(SessionHelper.getUserId(req))
						.and("class_id").is(SessionHelper.getClassId(req)).
						and("courseware_id").is(c.get("courseware_id"))
						.and("content_status").is(1).get());
				if (cos.size() > 0) {
					DBObject op = cos.get(0);
					if (current != null) {
						if (Integer.parseInt(current.get("content_type").toString()) == 3) {
							rs.put("op_type", op.get("content_type"));
							rs.put("op_id", op.get("content_id").toString());
							baseDao.update(DbMap.ClassOperate, QueryBuilder.start("_id").is(op.get("_id")).get(), 
									new BasicDBObject("content_status", 1));
							baseDao.update(DbMap.ClassOperate, QueryBuilder.start("_id").is(current.get("_id")).get(), 
									new BasicDBObject("content_status", 2));
						} else {
							if (init) {
								rs.put("op_type", current.get("content_type"));
								rs.put("op_id", current.get("content_id").toString());
							}
						}
					} else {
						rs.put("op_type", op.get("content_type"));
						rs.put("op_id", op.get("content_id").toString());
						baseDao.update(DbMap.ClassOperate, QueryBuilder.start("_id").is(op.get("_id")).get(), 
								new BasicDBObject("content_status", 1));
					}
				} else {
					if (current == null) {
						rs.put("wait", true);
					} else {
						if (init) {
							rs.put("op_type", current.get("content_type"));
							rs.put("op_id", current.get("content_id").toString());
						}
					}
				}
			}
		}
		return rs;
	}
}
