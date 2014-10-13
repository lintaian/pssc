package com.lps.pssc.config;

public class MongoConfig {
	private String host = "127.0.0.1";
	private int port = 27017;
	private String db;
	public MongoConfig() {
	}
	
	public MongoConfig(String host, int port, String db) {
		super();
		this.host = host;
		this.port = port;
		this.db = db;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}
}
