package com.lps.pssc.module;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.lps.pssc.dao.impl.BaseDao;
import com.lps.pssc.filter.LoginJsonFilter;
import com.lps.pssc.util.DbMap;
import com.lps.pssc.util.ImageHelper;
import com.lps.pssc.util.SessionHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.Util;

@IocBean
@InjectName
@At("/user")
@Fail("json")
@Filters({@By(type=LoginJsonFilter.class)})
public class UserModule {
	@Inject
	BaseDao baseDao;
	
	@At("/info")
	@GET
	@Ok("jsp:/tpl/info.jsp")
	public Object getInfo(HttpServletRequest req) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("user", SessionHelper.getUser(req));
		rs.put("classes", baseDao.get(DbMap.ClassDict, new BasicDBObject("_id", SessionHelper.getUser(req).get("class_id"))));
		return rs;
	}
	@At("/updatePwd")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object updatePwd(Map<String, String> obj, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			DBObject user = baseDao.updateAndGet(DbMap.Student, new BasicDBObject("_id", SessionHelper.getUser(req).get("_id")), 
					new BasicDBObject("auth_code", Util.hexMD5((obj.get("pwd") + SessionHelper.getUserIdStr(req)).getBytes())));
			SessionHelper.setUser(req, user);
		} catch (Exception e) {
			rs.put("status", false);
		}
		return rs;
	}
	@At("/uploader")
	@Ok("json")
	@AdaptBy(type=UploadAdaptor.class, args = { "${app.root}/photo" })
	public Object uploader(@Param("file") File file, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			String photo = file.toString();
			//改变图片尺寸
			BufferedImage image = ImageIO.read(new File(photo));
			image = ImageHelper.zoom(image, 400, 300);
			String name = file.getName();
			name = name.substring(name.lastIndexOf(".") + 1, name.length());
			ImageIO.write(image, name, new FileOutputStream(photo));
			//存入数据库
			photo = photo.replace('\\', '/');
			photo = photo.substring(photo.lastIndexOf("photo"), photo.length());
			DBObject user = baseDao.updateAndGet(DbMap.Student, new BasicDBObject("_id", SessionHelper.getUser(req).get("_id")), 
					new BasicDBObject("photo", photo));
			//写入session
			SessionHelper.setUser(req, user);
			rs.put("photo", photo);
		} catch (Exception e) {
			rs.put("status", false);
		}
		return rs;
	}
	
	@At("/logout")
	@GET
	@Ok("jsp:/tpl/logout.jsp")
	public Object logout(HttpServletRequest req) throws Exception {
		return null;
	}
}
