package com.lps.pssc.util;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class SessionHelper {
	private SessionHelper() {
	}
	public static void set(HttpServletRequest req, String name, Object obj) {
		req.getSession().setAttribute(name, obj);
	}
	public static Object get(HttpServletRequest req, String name) {
		return req.getSession().getAttribute(name);
	}
	public static void setUser(HttpServletRequest req, Object user) {
		req.getSession().setAttribute("loginUser", user);
	}
	public static DBObject getUser(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute("loginUser");
		return obj == null ? null : (DBObject)obj;
	}
	public static ObjectId getUserId(HttpServletRequest req) {
		DBObject obj = getUser(req);
		return obj == null ? null : (ObjectId) obj.get("_id");
	}
	public static ObjectId getClassId(HttpServletRequest req) {
		DBObject obj = getUser(req);
		return obj == null ? null : (ObjectId) obj.get("class_id");
	}
	public static String getUserIdStr(HttpServletRequest req) {
		DBObject obj = getUser(req);
		return obj == null ? "" : obj.get("_id").toString();
	}
	public static String getClassIdStr(HttpServletRequest req) {
		DBObject obj = getUser(req);
		return obj == null ? "" : obj.get("class_id").toString();
	}
	public static void invalidate(HttpServletRequest req) {
		req.getSession().invalidate();
	}
	public static boolean isLogin(HttpServletRequest req) {
		return getUser(req) != null;
	}
}
