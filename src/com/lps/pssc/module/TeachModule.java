package com.lps.pssc.module;

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

import com.lps.pssc.dao.interfaces.StateDaoIF;
import com.lps.pssc.filter.LoginFilter;
import com.lps.pssc.util.SessionHelper;

@IocBean
@InjectName
@At("/")
@Fail("json")
@Filters({@By(type=LoginFilter.class)})
public class TeachModule {
	@Inject
	StateDaoIF stateDao;
	
	@At("teach/state")
	@GET
	@Ok("json")
	public Object getState(HttpServletRequest req) throws Exception {
		String userId = SessionHelper.getUserId(req);
		return stateDao.get(userId);
	}
}
