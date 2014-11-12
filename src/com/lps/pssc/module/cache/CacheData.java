package com.lps.pssc.module.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.lps.pssc.config.CacheConfig;

public class CacheData {
	private long activeTime;
	private List<CacheDateItem> queue;

	public CacheData() {
		activeTime = System.currentTimeMillis();
		queue = new ArrayList<CacheDateItem>();
	}
	
	public void push(String data) {
		long now = System.currentTimeMillis();
		push(data, now);
	}
	
	public void push(String data, long timestamp) {
		synchronized (queue) {
			CacheDateItem item = new CacheDateItem();
			item.data = data;
			item.timestamp = timestamp;
			queue.add(item);
		}
		activeTime = timestamp;
	}

	public String pull(long tick) {
		StringBuffer result = new StringBuffer("");
		synchronized (queue) {
			Iterator<CacheDateItem> iter = queue.iterator();
			while (iter.hasNext()) {
				CacheDateItem item = iter.next();
				if (item.timestamp > tick) {
					result.append(item.timestamp);
					result.append("&");
					result.append(item.data);
					result.append("-");
					break;
				}
			}
		}
		activeTime = System.currentTimeMillis();
		return result.substring(0, result.length() - 1);
	}
	
	public void remove(String str) {
		List<CacheDateItem> list = new ArrayList<CacheDateItem>();
		Iterator<CacheDateItem> iter = queue.iterator();
		while (iter.hasNext()) {
			CacheDateItem item = iter.next();
			if (item.data.indexOf(str) >= 0) {
				list.add(item);
			}
		}
		if (list.size() > 0) {
			synchronized (queue) {
				queue.removeAll(list);
			}
		}
	}

	public void tick(CacheConfig config) {
		long now = System.currentTimeMillis();
		long span = now - activeTime;
		if (span > config.getUnActiveTime()) {
			List<CacheDateItem> list = new ArrayList<CacheDateItem>();
			Iterator<CacheDateItem> iter = queue.iterator();
			while (iter.hasNext()) {
				CacheDateItem item = iter.next();
				if (item.timestamp + config.getTimeout() < now) {
					list.add(item);
				}
			}
			if (list.size() > 0) {
				synchronized (queue) {
					queue.removeAll(list);
				}
			}
		}
	}

}
