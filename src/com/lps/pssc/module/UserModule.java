package com.lps.pssc.module;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.nutz.mvc.annotation.Param;

import com.lps.pssc.dao.interfaces.UserDaoIF;
import com.lps.pssc.filter.LoginFilter;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@IocBean
@InjectName
@At("/user")
@Fail("json")
@Filters({@By(type=LoginFilter.class)})
public class UserModule {
	@Inject
	UserDaoIF userDao;
	
	@At("/info")
	@GET
	@Ok("jsp:/tpl/info.jsp")
	public Object getRecords(HttpServletRequest req) throws Exception {
		return SessionHelper.getUser(req);
	}
	@At("/updatePwd")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object updatePwd(Map<String, Object> obj, HttpServletRequest req) {
		DBObject object = SessionHelper.getUser(req);
		object.put("password", obj.get("pwd"));
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			userDao.update(new BasicDBObject("_id", object.get("_id")), object);
		} catch (Exception e) {
			rs.put("status", false);
		}
		return rs;
	}
}
