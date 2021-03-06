package com.lps.pssc.module;

import java.util.Date;
import java.util.HashMap;
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
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@IocBean
@InjectName
@At("/preemptive")
@Fail("json")
@Filters({@By(type=LoginJsonFilter.class)})
public class PreemptiveModule {
	@Inject
	BaseDao baseDao;
	
	@At("")
	@GET
	@Ok("jsp:/tpl/preemptive.jsp")
	public Object getPreemptive(HttpServletRequest req, String id) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("p", baseDao.get(DbMap.Preemptive, QueryBuilder.start("_id").is(new ObjectId(id)).get()));
		rs.put("pa", baseDao.get(DbMap.PreemptiveAnswer, QueryBuilder.start("preemptive_id").is(new ObjectId(id)).
				and("student_id").is(SessionHelper.getUserId(req)).get()));
		return rs;
	}
	@At("")
	@POST
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object updatePreemptive(Map<String, String> body, HttpServletRequest req) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			DBObject obj = baseDao.get(DbMap.PreemptiveAnswer, QueryBuilder.start("preemptive_id").
					is(new ObjectId(body.get("id"))).and("student_id").
					is(SessionHelper.getUserId(req)).get());
			if (obj == null) {
				Date date = new Date();
				baseDao.insert(DbMap.PreemptiveAnswer, new BasicDBObject("preemptive_id", new ObjectId(body.get("id")))
				.append("student_id", SessionHelper.getUserId(req)).append("create_date", date));
				rs.put("timestamp", date.getTime());
			}
		} catch (Exception e) {
			rs.put("status", false);
		}
		return rs;
	}
	@GET
	@At("/access")
	@Ok("json")
	public Object access(String id, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		DBObject p = baseDao.get(DbMap.PreemptiveAnswer, 
				QueryBuilder.start("student_id").is(SessionHelper.getUserId(req)).
				and("preemptive_id").is(new ObjectId(id)).
				and("access").is(true).get());
		if (p != null) {
			rs.put("status", true);
		}
		return rs;
	} 
}
