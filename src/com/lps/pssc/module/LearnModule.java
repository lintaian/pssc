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
	@Ok("jsp:/tpl/studentList.jsp")
	public Object getStudentList(HttpServletRequest req) throws Exception {
		String classId = SessionHelper.getClassId(req);
		return userDao.getList(UUID.fromString(classId)).toArray();
	}
	@At("/courseware")
	@GET
	@Ok("jsp:/tpl/coursewareList.jsp")
	public Object getCourseware(HttpServletRequest req) throws Exception {
		String classId = SessionHelper.getClassId(req);
		return userDao.getList(UUID.fromString(classId)).toArray();
	}
}
