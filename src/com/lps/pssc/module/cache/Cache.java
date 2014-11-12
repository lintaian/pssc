package com.lps.pssc.module.cache;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.nutz.mvc.Mvcs;
import com.lps.pssc.config.CacheConfig;

public class Cache {
	private static volatile Cache instance = null;
	private static Object lockHelper = new Object();

	public static Cache getInstance() {
		if (instance == null) {
			synchronized (lockHelper) {
				if (instance == null) {
					instance = new Cache();
				}
			}
		}
		return instance;
	}
	
	private CacheConfig config;
	private ConcurrentHashMap<String, CacheData> dict;

	private Cache() {
		dict = new ConcurrentHashMap<String, CacheData>();
		config = Mvcs.ctx.getDefaultIoc().get(CacheConfig.class);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				deamon();
			}
		});
		thread.start();
	}
	
	public CacheData getCacheData(String key) {
		CacheData cacheData = null;
        if(dict.containsKey(key)) {
        	cacheData = dict.get(key);
        } else {
            cacheData = new CacheData();
            dict.put(key, cacheData);
        }
        return cacheData;
	}
	
	public void pushPoint(String studentId, String targetId, String data)
    {
		String key = studentId.toLowerCase() + "-" + targetId.toLowerCase();
        getCacheData(key).push(data);
    }
	public void pushVote(String studentId, String targetId, String data)
	{
		String key = targetId.toLowerCase();
		data = studentId + "," + data;
		CacheData cacheData = getCacheData(key);
		cacheData.remove(studentId);
		cacheData.push(data);
	}
	public void pushPreemptive(String studentId, String targetId, long time)
	{
		String key = targetId.toLowerCase();
		getCacheData(key).push(studentId, time);
	}

	public String pullPoint(String studentId, String targetId, long tick)
    {
        String result = "";
        String key = studentId.toLowerCase() + "-" + targetId.toLowerCase();
        if(dict.containsKey(key)) {
        	CacheData cacheData = dict.get(key);
        	result = cacheData.pull(tick);
        }
        return result;
    }
	public String pullVote(String targetId)
	{
		String result = "";
		String key = targetId.toLowerCase();
		if(dict.containsKey(key)) {
			CacheData cacheData = dict.get(key);
			result = cacheData.pull(0);
		}
		return result;
	}
	public String pullPreemptive(String targetId)
	{
		return pullVote(targetId);
	}

	private void deamon()
    {
        while (true)
        {
        	Set<String> keys = dict.keySet();
        	for (String key : keys) {
        		CacheData drawCacheData = dict.get(key);
				if (drawCacheData != null) {
					drawCacheData.tick(config);
				}
			}
            try {
				Thread.sleep(config.getPollTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
	public CacheConfig getConfig() {
		return config;
	}
}