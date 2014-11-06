package com.lps.pssc.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
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
	
	@SuppressWarnings("unchecked")
	@At("")
	@GET
	@Ok("jsp:/tpl/picture.jsp")
	public Object getPicturePage(HttpServletRequest req, String id, int page, String parentEle) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		page = page == 0 ? 1 : page;
		List<ObjectId> ids = baseDao.distinct(DbMap.PictureDict, "picture_id", 
				QueryBuilder.start("picture_batch_id").is(new ObjectId(id)).get());
		if (ids.size() > 0) {
			DBObject obj = baseDao.get(DbMap.Picture, QueryBuilder.start("_id").is(ids.get(page-1)).get());
			rs.put("picture", obj);
			rs.put("page", new Page(page, 1, ids.size()));
			rs.put("pictureBatchId", id);
			rs.put("parentEle", parentEle);
		}
		return rs;
	}
	@At("/single")
	@GET
	@Ok("jsp:/tpl/picture.jsp")
	public Object getPicture(HttpServletRequest req, String id) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("picture", baseDao.get(DbMap.Picture, QueryBuilder.start("_id").is(new ObjectId(id)).get()));
		rs.put("page", new Page(1, 1, 1));
		return rs;
	}
}
