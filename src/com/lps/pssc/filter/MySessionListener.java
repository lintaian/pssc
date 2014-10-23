package com.lps.pssc.filter;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.nutz.mvc.Mvcs;

import com.lps.pssc.dao.impl.RecordDao;
import com.lps.pssc.dao.impl.UserDao;
import com.lps.pssc.dao.interfaces.RecordDaoIF;
import com.lps.pssc.dao.interfaces.UserDaoIF;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MySessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("session created: " + arg0.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("session destroyed: " + arg0.getSession().getId());
		Object obj = arg0.getSession().getAttribute("loginUser");
		if (obj != null) {
			try {
				RecordDaoIF recordDao = Mvcs.ctx.getDefaultIoc().get(RecordDao.class);
				DBObject user = (DBObject) obj;
				DBObject record = new BasicDBObject();
				record.put("operate", "退出系统!");
				record.put("userId", user.get("_id").toString());
				record.put("time", (new Date()).getTime());
				recordDao.add(record);

				UserDaoIF userDao = Mvcs.ctx.getDefaultIoc().get(UserDao.class);
				user.put("LoginStatus", 0);
				userDao.update(new BasicDBObject("_id", user.get("_id")), user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
