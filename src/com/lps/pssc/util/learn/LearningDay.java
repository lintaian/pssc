package com.lps.pssc.util.learn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class LearningDay {
	private String url;
	private String startTime;
	private String endTime;
	private List<Day> days = new ArrayList<Day>();
	private static volatile LearningDay instance = null;
	private static Object lockHelper = new Object();
	private LearningDay() {
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(LearningDay.class.getResource("/holiday.xml"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		this.url = root.element("url").attributeValue("value");
		this.startTime = root.element("start").attributeValue("value");
		this.endTime = root.element("end").attributeValue("value");
		for (Object object : root.element("days").elements("day")) {
			Element ele = (Element) object;
			Day day = new Day();
			day.setDate(ele.attributeValue("value"));
			String start = ele.attributeValue("start");
			day.setStart((start != null && !"".equals(start)) ? start : this.startTime);
			String end = ele.attributeValue("end");
			day.setEnd((end != null && !"".equals(end)) ? end : this.endTime);
			this.days.add(day);
		}
	};
	public static LearningDay getInstance() {
		if (instance == null) {
			synchronized (lockHelper) {
				if (instance == null) {
					instance = new LearningDay();
				}
			}
		}
		return instance;
	}
	public Object jump(Date inDate) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("isJump", false);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = sdf.format(inDate);
		Day day = days.get(days.indexOf(new Day(date)));
		try {
			Date start = sdf2.parse(date + " " + day.getStart());
			Date end = sdf2.parse(date + " " + day.getEnd());
			if (days.contains(day) && inDate.after(start) && inDate.before(end)) {
				rs.put("isJump", true);
				rs.put("url", url);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rs;
	} 
}
