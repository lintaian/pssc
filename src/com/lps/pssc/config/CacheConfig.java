package com.lps.pssc.config;

public class CacheConfig {
	private long timeout;
	private long pollTime;
	public CacheConfig() {
	}
	public CacheConfig(long timeout, long pollTime) {
		super();
		this.timeout = timeout;
		this.pollTime = pollTime;
	}
	public long getTimeout() {
		return timeout;
	}
	public long getPollTime() {
		return pollTime;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public void setPollTime(long pollTime) {
		this.pollTime = pollTime;
	}
}
