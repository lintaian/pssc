/**
 * 
 */
package com.lps.pssc.util;

import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;

/**
 * @author lta
 *
 */
public class LoginList {
	private static volatile LoginList instance = null;
	private static Object lockHelper = new Object();
	
	public static LoginList getInstance() {
		if (instance == null) {
			synchronized (lockHelper) {
				if (instance == null) {
					instance = new LoginList();
				}
			}
		}
		return instance;
	}
	private ConcurrentHashMap<String, HttpSession> data;
	private LoginList() {
		data = new ConcurrentHashMap<String, HttpSession>();
	}
	
	public void destory(String studentId) {
		if (data.containsKey(studentId)) {
			HttpSession session = data.get(studentId);
			session.invalidate();
		}
	}
	
	public void remove(String studentId) {
		data.remove(studentId);
	}
	
	public void add(String studentId, HttpSession session) {
		data.put(studentId, session);
	}
}
