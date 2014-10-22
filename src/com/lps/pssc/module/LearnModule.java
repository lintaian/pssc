package com.lps.pssc.module;

import java.util.UUID;

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

import com.lps.pssc.dao.interfaces.UserDaoIF;
import com.lps.pssc.filter.LoginFilter;
import com.lps.pssc.util.SessionHelper;

@IocBean
@InjectName
@At("/learn")
@Fail("json")
@Filters({@By(type=LoginFilter.class)})
public class LearnModule {
	@Inject
	UserDaoIF userDao;
	
	@At("/")
	@GET
	@Ok("jsp:/tpl/learn.jsp")
	public Object getState(HttpServletRequest req) throws Exception {
		String classId = SessionHelper.getClassId(req);
		return userDao.getList(UUID.fromString(classId)).toArray();
	}
}
