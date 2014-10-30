package com.lps.pssc.module;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import com.lps.pssc.dao.impl.BaseDao;
import com.lps.pssc.util.DbMap;
import com.lps.pssc.util.TwoDimensionCode;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

@IocBean
@InjectName
@At("/qrcode")
@Fail("json")
public class QrCodeModule {
	@Inject
	BaseDao baseDao;
	
	@At("")
	@Ok("raw")
	public Object getVideo(HttpServletRequest req, String id) throws Exception {
		return TwoDimensionCode.qRCodeCommon("http://www.baidu.com", "png", 4, null, 0);
	}
	@At("/validate")
	@GET
	@Ok("jsp:/tpl/")
	public Object getVideoDict(HttpServletRequest req, String id) throws Exception {
		List<DBObject> rs = baseDao.query(DbMap.VideoDict, QueryBuilder.start("video_id").is(new ObjectId(id)).get()).toArray(); 
		return Json.fromJson(Lang.inr(rs.toString()));
	}
}
