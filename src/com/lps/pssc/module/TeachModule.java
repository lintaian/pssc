package com.lps.pssc.module;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	public Object getIndex(HttpServletRequest req) throws Exception {
		return null;
	}

	@At("/state")
	@GET
	@Ok("json")
	public Object getState(HttpServletRequest req) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		DBObject c = baseDao.get(DbMap.Class, QueryBuilder.start("class_id").is(SessionHelper.getClassId(req)).get());
		if (c != null) {
			rs.put("c", c);
			if (!"".equals(c.get("status").toString()) && Integer.parseInt(c.get("status").toString()) != 2) {
				rs.put("co", baseDao.query(DbMap.ClassOperate, 
						QueryBuilder.start("student_id").is(SessionHelper.getUserId(req))
						.and("class_id").is(SessionHelper.getClassId(req)).
						and("courseware_id").is(c.get("courseware_id"))
						.and("status").is(0).get()).toArray());
				rs.put("current", baseDao.get(DbMap.ClassOperate, 
						QueryBuilder.start("student_id").is(SessionHelper.getUserId(req))
						.and("class_id").is(SessionHelper.getClassId(req)).
						and("courseware_id").is(c.get("courseware_id"))
						.and("status").is(2).get()));
			}
		}
		return rs;
	}
}
