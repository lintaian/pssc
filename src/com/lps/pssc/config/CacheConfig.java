package com.lps.pssc.config;

public class CacheConfig {
	private long timeout;
	private long pollTime;
	private long unActiveTime;
	public CacheConfig() {
	}
	public CacheConfig(long timeout, long pollTime, long unActiveTime) {
		super();
		this.timeout = timeout;
		this.pollTime = pollTime;
		this.unActiveTime = unActiveTime;
	}
	public long getTimeout() {
		return timeout;
	}
	public long getPollTime() {
		return pollTime;
	}
	public long getUnActiveTime() {
		return unActiveTime;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public void setPollTime(long pollTime) {
		this.pollTime = pollTime;
	}
	public void setUnActiveTime(long unActiveTime) {
		this.unActiveTime = unActiveTime;
	}
}
