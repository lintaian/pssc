package com.lps.pssc.module;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
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
import com.lps.pssc.util.DbMap;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.util.Util;

@IocBean
@InjectName
@At("/admin")
@Fail("json")
public class AdminModule {
	@Inject
	BaseDao baseDao;
	
	@At("/student")
	@GET
	@Ok("jsp:/admin/jsp/studentList.jsp")
	@AdaptBy(type=JsonAdaptor.class)
	public Object studentList(Map<String, String> obj, HttpServletRequest req) {
		return baseDao.querySort(DbMap.Student, new BasicDBObject(), 
				new BasicDBObject("grade_id", 1).append("class_id", 1));
	}
	@At("/student/update")
	@GET
	@Ok("jsp:/admin/jsp/student.jsp")
	public Object updateStudent(String id, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		if (id != null && !"".equals(id)) {
			rs.put("student", baseDao.get(DbMap.Student, QueryBuilder.start("_id").is(new ObjectId(id)).get()));
		}
		rs.put("classes", baseDao.query(DbMap.ClassDict, new BasicDBObject()));
		return rs;
	}
	@At("/student/update")
	@POST
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object updateStudentPost(Map<String, String> body, HttpServletRequest req) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		if ("".equals(body.get("name")) || "".equals(body.get("login_name"))) {
			rs.put("status", false);
			rs.put("msg", "姓名和登录名不能为空!");
		} else {
			DBObject obj = new BasicDBObject();
			obj.put("name", body.get("name"));
			obj.put("login_name", body.get("login_name"));
			obj.put("class_id", new ObjectId(body.get("class_id")));
			obj.put("grade_id", new ObjectId(body.get("grade_id")));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String birthday = body.get("birthday");
			obj.put("birthday", "".equals(birthday) ? "" : sdf.parse(birthday));
			obj.put("sex", Integer.parseInt(body.get("sex")));
			obj.put("status", Integer.parseInt(body.get("status")));
			obj.put("login_status", Integer.parseInt(body.get("login_status")));
			obj.put("photo", body.get("photo"));
			if ("".equals(body.get("id"))) {
				if (!"".equals(body.get("auth_code"))) {
					ObjectId id = new ObjectId();
					obj.put("auth_code", Util.hexMD5((body.get("auth_code") + id.toString()).getBytes()));
					obj.put("_id", id);
				} else {
					obj.put("auth_code", "");
				}
				baseDao.insert(DbMap.Student, obj);
				rs.put("msg", "添加成功!");
			} else {
				if (!"".equals(body.get("auth_code"))) {
					obj.put("auth_code", Util.hexMD5((body.get("auth_code") + body.get("id")).getBytes()));
				}
				baseDao.update(DbMap.Student, 
						QueryBuilder.start("_id").is(new ObjectId(body.get("id"))).get(), obj);
				rs.put("msg", "修改成功!");
			}
			rs.put("status", true);
		}
		return rs;
	}
}
