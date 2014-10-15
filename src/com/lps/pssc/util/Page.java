package com.lps.pssc.util;

public class Page {
	private int curPage = 1;
	private int count;
	private int perPage = 10;
	private int countPage;
	
	public Page() {}
	
	public Page(int curPage, int perPage) {
		super();
		this.perPage = perPage;
		this.curPage = curPage;
	}
	
	public Page(int curPage, int perPage, int count) {
		super();
		this.count = count;
		this.perPage = perPage;
		this.curPage = curPage;
		this.countPage = (int) Math.ceil(count * 1.0 / perPage);
	}
	
	public int skip() {
		return (curPage - 1) * perPage;
	}
	
	public int getCurPage() {
		return curPage;
	}
	public int getCount() {
		return count;
	}
	public int getPerPage() {
		return perPage;
	}
	public int getCountPage() {
		return countPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("{ \"count\" : ");
		sb.append(count);
		sb.append(", \"curPage\" : ");
		sb.append(curPage);
		sb.append(", \"perPage\" : ");
		sb.append(perPage);
		sb.append(", \"countPage\" : ");
		sb.append(countPage);
		sb.append(" }");
		return sb.toString();
	}
}
