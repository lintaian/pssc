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

import com.lps.pssc.dao.interfaces.RecordDaoIF;
import com.lps.pssc.filter.LoginFilter;
import com.lps.pssc.util.Page;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.DBCursor;

@IocBean
@InjectName
@At("/")
@Fail("json")
@Filters({@By(type=LoginFilter.class)})
public class RecordModule {
	@Inject
	RecordDaoIF recordDao;
	
	@At("record")
	@GET
	@Ok("jsp:/tpl/record.jsp")
	public Object getRecords(HttpServletRequest req) throws Exception {
		String userId = SessionHelper.getUserId(req);
		DBCursor cursor = recordDao.get(userId, new Page(1, 10));
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("data", cursor.toArray());
		rs.put("page", new Page(1, 10, cursor.count()));
		return rs;
	}
}
