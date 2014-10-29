package com.lps.pssc.util;

import java.util.ArrayList;
import java.util.List;

public class Helper {
	public static List<String> getAnswer(int num) {
		List<String> rs = new ArrayList<String>();
		int a = 65;
		for (int i = 0; i < num; i++) {
			rs.add(Character.toString((char) (a + i)));
		}
		return rs;
	}
}
