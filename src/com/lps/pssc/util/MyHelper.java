package com.lps.pssc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHelper {
	/**
	 * 比较两个UUID是否相等
	 * @param u1
	 * @param u2
	 * @return
	 */
	public static boolean equalUUID(String u1, String u2) {
		u1 = u1.replaceAll("-", "").toLowerCase();
		u2 = u2.replaceAll("-", "").toLowerCase();
		return u1.equals(u2);
	}
	public static List<String> getAnswer(int num) {
		List<String> rs = new ArrayList<String>();
		int a = 65;
		for (int i = 0; i < num; i++) {
			rs.add(Character.toString((char) (a + i)));
		}
		return rs;
	}
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
	public static String addOrRemoveString(String os, String target, int maxNum) {
		if (os.indexOf(target) != -1) {
			os = os.replaceAll(target, "");
		} else {
			if (maxNum == 0 || os.length() < maxNum) {
				os = os + target;
				String[] temp = os.split("");
				Arrays.sort(temp);
				os = "";
				for (int i = 0; i < temp.length; i++) {
					os += temp[i];
				}
			}
		}
		return os;
	}

	public static void fileCopy(File source, File target) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(source);
			fo = new FileOutputStream(target);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
