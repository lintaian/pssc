package com.lps.pssc.filter;

import java.util.Date;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.nutz.mvc.Mvcs;
import com.lps.pssc.dao.impl.BaseDao;
import com.lps.pssc.util.DbMap;
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
				BaseDao baseDao = Mvcs.ctx.getDefaultIoc().get(BaseDao.class);
				DBObject user = (DBObject) obj;
				DBObject record = new BasicDBObject();
				record.put("operate", "退出系统!");
				record.put("user_id", user.get("_id"));
				record.put("create_date", new Date());
				baseDao.insert(DbMap.StudentRecord, record);
				
				baseDao.update(DbMap.Student, new BasicDBObject("_id", user.get("_id")), 
						new BasicDBObject("login_status", 0));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
