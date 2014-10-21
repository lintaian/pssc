package com.lps.pssc.util;

public class UUIDHelper {
	
	public static boolean equal(String u1, String u2) {
		u1 = u1.replaceAll("-", "").toLowerCase();
		u2 = u2.replaceAll("-", "").toLowerCase();
		return u1.equals(u2);
	}
}
