package com.lps.pssc.module.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrawCacheData {
	private long activeTime;
	private List<DrawCacheDateItem> queue;

	public DrawCacheData() {
		activeTime = System.currentTimeMillis();
		queue = new ArrayList<DrawCacheDateItem>();
	}

	public void push(String data) {
		long now = System.currentTimeMillis();
		synchronized (queue) {
			DrawCacheDateItem item = new DrawCacheDateItem();
			item.data = data;
			item.timeout = now;
			item.timestamp = now + 3600000;
			queue.add(item);
		}
		activeTime = now;
	}

	public String poll(long tick) {
		String result = "";
		synchronized (queue) {
			Iterator<DrawCacheDateItem> iter = queue.iterator();
			while (iter.hasNext()) {
				DrawCacheDateItem item = iter.next();
				if (item.timestamp > tick) {
					result = String.valueOf(item.timestamp) + "&" + item.data;
					break;
				}
			}
		}
		return result;
	}

	public void Tick() {
		long now = System.currentTimeMillis();
		long span = now - activeTime;
		if (span > 3600000) {
			List<DrawCacheDateItem> list = new ArrayList<DrawCacheDateItem>();
			Iterator<DrawCacheDateItem> iter = queue.iterator();
			while (iter.hasNext()) {
				DrawCacheDateItem item = iter.next();
				if (item.timeout > now) {
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
