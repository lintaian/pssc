package com.lps.pssc.module;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;

import com.lps.pssc.dao.impl.BaseDao;
import com.lps.pssc.module.cache.DrawCache;
import com.lps.pssc.util.SessionHelper;

@IocBean
@InjectName
@At("/cache")
@Fail("json")
public class CacheModule {
	@Inject
	BaseDao baseDao;
	
	@At("")
	@POST
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object pushData(Map<String, String> obj, HttpServletRequest req) {
		DrawCache.getInstance().push(SessionHelper.getUserIdStr(req), SessionHelper.get(req, "coursewareId").toString(), obj.get("data"));
		return null;
	}
	
	@At("")
	@GET
	@Ok("raw")
	public Object pullData(String sid, String cid, long tick ) {
		String a = DrawCache.getInstance().poll(sid, cid, tick); 
		return a;
	}
}
