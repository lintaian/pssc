package com.lps.pssc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthHelper {
	@SuppressWarnings("deprecation")
	public static List<Map<String, Object>> getMonths(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		d.setDate(1);
		if (date != null && !"".equals(date)) {
			try {
				d = sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		d.setMonth(d.getMonth() - 3);
		List<Map<String, Object>> rs = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("M");
		for (int i = 0; i < 4; i++) {
			Map<String, Object> temp = new HashMap<String, Object>();
			d.setMonth(d.getMonth() + 1);
			temp.put("name", sdf2.format(d) + "月");
			temp.put("date", sdf.format(d));
			temp.put("active", i == 2);
			rs.add(temp);
		}
		return rs;
	}
}
