package com.lps.pssc.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

import com.lps.pssc.dao.interfaces.StateDaoIF;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.DBObject;

@IocBean
@InjectName
@At("/")
@Fail("json")
public class TeachModule {
	@Inject
	StateDaoIF stateDao;
	
	@At("teach/state")
	@GET
	@Ok("json")
	public Object getState(HttpServletRequest req) throws Exception {
		String name = ((DBObject) SessionHelper.getUser(req)).get("name").toString();
		return stateDao.get(name);
	}
}
