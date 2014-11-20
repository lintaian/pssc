package com.lps.pssc.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

import com.lps.pssc.dao.BaseDao;
import com.lps.pssc.filter.LoginJsonFilter;
import com.lps.pssc.util.DbMap;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@IocBean
@InjectName
@At("/video")
@Fail("json")
@Filters({@By(type=LoginJsonFilter.class)})
public class VideoModule {
	@Inject
	BaseDao baseDao;
	
	@At("")
	@GET
	@Ok("jsp:/tpl/video.jsp")
	public Object getVideo(HttpServletRequest req, String id) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("video", baseDao.get(DbMap.VideoPackage, QueryBuilder.start("_id").is(new ObjectId(id)).and("status").is(1).get()));
		return rs;
	}
	@At("/dict")
	@GET
	@Ok("json:nice")
	public Object getVideoDict(HttpServletRequest req, String id) throws Exception {
		List<DBObject> rs = baseDao.query(DbMap.VideoDict, QueryBuilder.start("video_id").is(new ObjectId(id)).get()).toArray(); 
		return Json.fromJson(Lang.inr(rs.toString()));
	}
}
