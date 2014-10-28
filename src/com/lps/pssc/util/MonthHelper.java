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
		List<Map<String, Object>> rs = new ArrayList<Map<String,Object>>();
		try {
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
			Date current = new Date();
			Date start = sdf.parse(sdf.format(d));
			if (d.getTime() > current.getTime()) {
				d.setMonth(current.getMonth() + 1);
				start.setMonth(d.getMonth() - 4);
			} else {
				start.setMonth(d.getMonth() - 3);
			}
			SimpleDateFormat sdf2 = new SimpleDateFormat("M月");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yy年M月");
			for (int i = 0; i < 4; i++) {
				Map<String, Object> temp = new HashMap<String, Object>();
				start.setMonth(start.getMonth() + 1);
				temp.put("name", start.getMonth() == 0 ? sdf3.format(start) : sdf2.format(start));
				temp.put("date", sdf.format(start));
				temp.put("active", start.getMonth() == d.getMonth());
				rs.add(temp);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
