package com.lps.pssc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateHelper {
	public static long getDaysOfMonth(int year, int month) {
		String startStr = year + "-" + month;
		String endStr = year + "-" + (month + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		long time = 0L;
		try {
			Date start = sdf.parse(startStr);
			Date end = sdf.parse(endStr);
			time = (end.getTime() - start.getTime()) / 86400000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	public static Object getDate(int year, int month, int day) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("current_year", year);
		rs.put("current_month", month);
		rs.put("current_day", day);
		rs.put("max_day", getDaysOfMonth(year, month));
		return rs;
	}
	public static int getYear(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return Integer.parseInt(sdf.format(d));
	}
	public static int getMonth(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return Integer.parseInt(sdf.format(d));
	}
	public static int getDay(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return Integer.parseInt(sdf.format(d));
	}
	public static Date getStartDate(int year, int month, int day) {
		Date date = null;
		if (year != 0) {
			try {
				SimpleDateFormat sdf = null;
				String str = "";
				if (month != 0) {
					if (day != 0) {
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						str = year + "-" + month + "-" + day;
					} else {
						sdf = new SimpleDateFormat("yyyy-MM");
						str = year + "-" + month;
					}
				} else {
					sdf = new SimpleDateFormat("yyyy");
					str = year + "";
				}
				date = sdf.parse(String.valueOf(str));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	public static Date getEndDate(int year, int month, int day) {
		Date date = null;
		if (year != 0) {
			try {
				SimpleDateFormat sdf = null;
				String str = "";
				if (month != 0) {
					if (day != 0) {
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						str = year + "-" + month + "-" + (day + 1);
					} else {
						sdf = new SimpleDateFormat("yyyy-MM");
						str = year + "-" + (month + 1);
					}
				} else {
					sdf = new SimpleDateFormat("yyyy");
					str = year + 1 + "";
				}
				date = sdf.parse(String.valueOf(str));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	public static void main(String[] args) throws ParseException {
		System.out.println(getDaysOfMonth(2000, 2));
		String aa = "2014-13-05";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.parse(aa));
		System.out.println(getStartDate(2014, 2, 5));
		System.out.println(getEndDate(2014, 2, 5));
	}
}
