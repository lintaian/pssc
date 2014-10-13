package com.lps.pssc.config;

public class ThriftConfig {
	private String ip;
	private int port;
	private int timeout;
	public ThriftConfig() {
	}
	public ThriftConfig(String ip, int port, int timeout) {
		this.ip = ip;
		this.port = port;
		this.timeout = timeout;
	}
	public String getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
