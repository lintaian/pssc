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
import com.lps.pssc.util.Page;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

@IocBean
@InjectName
@At("/")
@Fail("json")
@Filters({@By(type=LoginJsonFilter.class)})
public class RecordModule {
	@Inject
	BaseDao baseDao;
	
	@At("record")
	@GET
	@Ok("jsp:/tpl/record.jsp")
	public Object getRecords(HttpServletRequest req, int page) throws Exception {
		page = page > 0 ? page : 1;
		int perPage = 10;
		String userId = SessionHelper.getUserId(req);
		DBCursor cursor = baseDao.queryPage(DbMap.Record, new BasicDBObject("user_id", userId), new Page(page, perPage));
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("data", cursor.toArray());
		rs.put("page", new Page(page, perPage, cursor.count()));
		return rs;
	}
}
