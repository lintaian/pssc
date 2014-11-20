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

import com.lps.pssc.dao.BaseDao;
import com.lps.pssc.module.cache.Cache;
import com.lps.pssc.util.SessionHelper;

@IocBean
@InjectName
@At("/cache")
@Fail("json")
public class CacheModule {
	@Inject
	BaseDao baseDao;
	
	@At("/point")
	@POST
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object pushPoint(Map<String, String> obj, HttpServletRequest req) {
		Cache.getInstance().pushPoint(SessionHelper.getUserIdStr(req), 
				obj.get("eid"), obj.get("data"));
		return null;
	}
	
	@At("/point/*")
	@GET
	@Ok("raw")
	public Object pullData(String sid, String eid, long tick) {
		return Cache.getInstance().pullPoint(sid, eid, tick); 
	}
	
	@At("/vote")
	@POST
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object pushVote(Map<String, String> obj, HttpServletRequest req) {
		Cache.getInstance().pushVote(SessionHelper.getUserIdStr(req), 
				obj.get("vid"), obj.get("data"));
		return null;
	}
	
	@At("/vote/*")
	@GET
	@Ok("raw")
	public Object pullVote(String vid) {
		return Cache.getInstance().pullVote(vid); 
	}
	
	@At("/preemptive")
	@POST
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object pushPreemptive(Map<String, String> obj, HttpServletRequest req) {
		Cache.getInstance().pushPreemptive(SessionHelper.getUserIdStr(req), 
				obj.get("pid"), Long.parseLong(obj.get("timestamp")));
		return null;
	}
	
	@At("/preemptive/*")
	@GET
	@Ok("raw")
	public Object pullPreemptive(String pid) {
		return Cache.getInstance().pullPreemptive(pid);
	}
}
