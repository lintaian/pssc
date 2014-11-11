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
import com.lps.pssc.dao.impl.BaseDao;
import com.lps.pssc.filter.LoginJsonFilter;
import com.lps.pssc.util.DbMap;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.Util;

@IocBean
@InjectName
@At("/user")
@Fail("json")
@Filters({@By(type=LoginJsonFilter.class)})
public class UserModule {
	@Inject
	BaseDao baseDao;
	
	@At("/info")
	@GET
	@Ok("jsp:/tpl/info.jsp")
	public Object getInfo(HttpServletRequest req) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("user", SessionHelper.getUser(req));
		rs.put("classes", baseDao.get(DbMap.ClassDict, new BasicDBObject("_id", SessionHelper.getClassId(req))));
		return rs;
	}
	@At("/updatePwd")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object updatePwd(Map<String, String> obj, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			DBObject user = baseDao.updateAndGet(DbMap.Student, new BasicDBObject("_id", SessionHelper.getUserId(req)), 
					new BasicDBObject("auth_code", Util.hexMD5((obj.get("pwd") + SessionHelper.getUserIdStr(req)).getBytes())));
			SessionHelper.setUser(req, user);
		} catch (Exception e) {
			rs.put("status", false);
		}
		return rs;
	}
	@At("/updatePhoto")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object uploader(Map<String, String> obj, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			DBObject user = baseDao.updateAndGet(DbMap.Student, new BasicDBObject("_id", SessionHelper.getUserId(req)), 
					new BasicDBObject("photo", obj.get("photo")));
			SessionHelper.setUser(req, user);
		} catch (Exception e) {
			rs.put("status", false);
		}
		return rs;
	}
	
	@At("/logout")
	@GET
	@Ok("jsp:/tpl/logout.jsp")
	public Object logout(HttpServletRequest req) throws Exception {
		return null;
	}
}
