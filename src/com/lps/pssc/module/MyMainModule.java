package com.lps.pssc.module;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;

import com.lps.pssc.dao.interfaces.RecordDaoIF;
import com.lps.pssc.dao.interfaces.UserDaoIF;
import com.lps.pssc.filter.LoginFilter;
import com.lps.pssc.util.MD5Util;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@IocBean
@InjectName
@At("/")
@Fail("json")
public class MyMainModule {
	@Inject
	UserDaoIF userDao;
	@Inject
	RecordDaoIF recordDao;

	@At("login")
	@AdaptBy(type = JsonAdaptor.class)
	@Ok("json")
	@POST
	public Object loginPost(Map<String, String> body, HttpServletRequest req) throws Exception {
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("status", false);
		String name = body.containsKey("username") ? body.get("username") : "";
		String password = body.containsKey("password") ? body.get("password") : "";
		if (name != null && !"".equals(name) && password != null && !"".equals(password)) {
			DBObject user = userDao.get(name);
			if (user != null && ("".equals(user.get("Password")) || 
				user.get("Password").toString().equals(MD5Util.string2MD5(password + user.get("_id").toString())))) {
				re.put("status", true);
				SessionHelper.setUser(req, user);
				DBObject record = new BasicDBObject();
				record.put("operate", "登陆系统");
				record.put("userId", user.get("_id").toString());
				record.put("time", (new Date()).getTime());
				recordDao.add(record);
				user.put("LoginStatus", 1);
				userDao.update(new BasicDBObject("_id", user.get("_id")), user);
			} else {
				re.put("msg", "用户名或密码错误!");
			}
		} else {
			re.put("msg", "用户名和密码不能为空!");
		}
		return re;
	}
	
	@At("login")
	@Ok("jsp:jsp.login")
	@Fail("redirect:/main")
	public void loginGet(HttpServletRequest req) throws Exception {
		if (SessionHelper.isLogin(req)) {
			throw new Exception();
		}
	}
	@At("main")
	@Filters({@By(type=LoginFilter.class)})
	@Ok("jsp:jsp.main")
	public void main(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.addHeader("Cache-Control", "no-store, must-revalidate"); 
		resp.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT"); 
	}
	@At("")
	@Ok("redirect:/main")
	public void index() {
	}
	@At("logout")
	@Ok("redirect:/login")
	public void logout(HttpServletRequest req) throws IOException {
		req.getSession().invalidate();
	}
	@At("session")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object getSession(Map<String, String> body, HttpServletRequest req) {
		System.out.println(body.get("name"));
		return SessionHelper.get(req, body.get("name"));
	}
	@At("application")
	@Ok("json")
	public Object getApplication(String name, HttpServletRequest req) {
		return req.getSession().getServletContext().getAttribute(name);
	}
}
