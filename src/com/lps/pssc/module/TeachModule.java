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
	public Object getIndex(HttpServletRequest req, String currentClass, String coursewareId, String photo, String title) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("currentClass", (currentClass == null || "".equals(currentClass)) ? "p_un_teach_default" : currentClass) ;
		rs.put("photo", photo) ;
		rs.put("title", (title == null || "".equals(title)) ? "现在还未到上课时间,请同学们先去预习功课吧!" : title) ;
		if (coursewareId != null && !"".equals(coursewareId)) {
			DBObject cw = baseDao.get(DbMap.Courseware, QueryBuilder.start("_id").is(new ObjectId(coursewareId)).and("status").is(1).get());
			SessionHelper.setCW(req, cw);
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
						int content_type = Integer.parseInt(current.get("content_type").toString());
						if (content_type == 4) {
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
	@At("/contentStatus")
	@POST
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object updateContentStatus(Map<String, String> body, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			baseDao.update(DbMap.ClassOperate, QueryBuilder.start("class_id").is(SessionHelper.getClassId(req)).
					and("student_id").is(SessionHelper.getUserId(req)).
					and("courseware_id").is(SessionHelper.getCWid(req)).
					and("content_id").is(new ObjectId(body.get("content_id"))).get(), 
					new BasicDBObject("content_status", 2).append("complete_date", new Date()));
		} catch (Exception e) {
			rs.put("status", false);
		}
		return rs;
	}
}
