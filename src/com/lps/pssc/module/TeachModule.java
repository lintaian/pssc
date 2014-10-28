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

import com.lps.pssc.dao.impl.BaseDao;
import com.lps.pssc.filter.LoginJsonFilter;

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
	@Ok("jsp:/tpl/classRoom.jsp")
	public Object getIndex(HttpServletRequest req) throws Exception {
		return null;
	}

	@At("/state")
	@GET
	@Ok("json")
	public Object getState(HttpServletRequest req) throws Exception {
		return null;
	}
}
