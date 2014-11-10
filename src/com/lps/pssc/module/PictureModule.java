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

import com.lps.pssc.dao.impl.BaseDao;
import com.lps.pssc.filter.LoginJsonFilter;
import com.lps.pssc.util.DbMap;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@IocBean
@InjectName
@At("/picture")
@Fail("json")
@Filters({@By(type=LoginJsonFilter.class)})
public class PictureModule {
	@Inject
	BaseDao baseDao;
	
	@At("")
	@GET
	@Ok("jsp:/tpl/picture.jsp")
	public Object getPicturePackage(HttpServletRequest req, String id) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("picture_Package", baseDao.get(DbMap.PicturePackage, QueryBuilder.start("_id").is(new ObjectId(id)).and("status").is(1).get()));
		rs.put("cw_id", SessionHelper.get(req, "coursewareId").toString());
		rs.put("cw_type", SessionHelper.get(req, "coursewareType"));
		return rs;
	}
	@SuppressWarnings("unchecked")
	@At("/dict")
	@GET
	@Ok("json:nice")
	public Object getPicture(HttpServletRequest req, String id) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		List<ObjectId> picIds = baseDao.distinct(DbMap.PictureDict, "picture_id", QueryBuilder.start("picture_package_id").is(new ObjectId(id)).get());
		List<DBObject> pics = baseDao.query(DbMap.Picture, QueryBuilder.start("_id").in(picIds).get()).toArray();
		List<DBObject> picExs = baseDao.query(DbMap.PictureExerciseDict, QueryBuilder.start("picture_package_id").is(new ObjectId(id)).get()).toArray();
		rs.put("pics", Json.fromJson(Lang.inr(pics.toString())));
		rs.put("picExs", Json.fromJson(Lang.inr(picExs.toString())));
		return rs;
	}
	@At("/single")
	@GET
	@Ok("jsp:/tpl/pictureSingle.jsp")
	public Object getPictureSingle(HttpServletRequest req, String id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("picture", baseDao.get(DbMap.Picture, QueryBuilder.start("_id").is(new ObjectId(id)).get()));
		return rs;
	}
}
