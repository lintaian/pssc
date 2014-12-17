package com.lps.pssc.util.learn;

public class Day {
	private String date;
	private String start;
	private String end;
	public Day() {
	}
	public Day(String date) {
		this.date = date;
	}
	public Day(String date, String start, String end) {
		this.date = date;
		this.start = start;
		this.end = end;
	}
	public boolean hasDate() {
		return date == null || "".equals(date);
	}
	public boolean hasStart() {
		return start == null || "".equals(start);
	}
	public boolean hasEnd() {
		return end == null || "".equals(end);
	}
	public String getDate() {
		return date;
	}
	public String getStart() {
		return start;
	}
	public String getEnd() {
		return end;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	@Override
	public boolean equals(Object obj) {
		try {
			if (date == null || "".equals(date)) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Day other = (Day)obj;
			if (date.equals(other.date)) {
				return true;
			}
		} catch (Exception e) {
		}
	    return false;  
	}
}
