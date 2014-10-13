package com.lps.pssc.util;

import javax.servlet.http.HttpServletRequest;

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
	public static Object getUser(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute("loginUser");
		return obj == null ? null : obj;
	}
	public static void invalidate(HttpServletRequest req) {
		req.getSession().invalidate();
	}
	public static boolean isLogin(HttpServletRequest req) {
		return getUser(req) != null;
	}
}
