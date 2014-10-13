package com.lps.pssc.service.impl;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.nutz.ioc.loader.annotation.Inject;

import com.lps.pssc.config.ThriftConfig;
import com.lps.pssc.util.Callback;

public class BaseService {
	@Inject("refer:thriftConfig")
	private ThriftConfig thriftConfig;
	@Inject("refer:loginConfig")
	private ThriftConfig loginConfig;
	
	public TTransport getTTransport() {
        return new TSocket(thriftConfig.getIp(), thriftConfig.getPort());
	}
	public TProtocol getTBinaryProtocl() {
		TTransport transport = new TSocket(thriftConfig.getIp(), thriftConfig.getPort());
        TProtocol protocol = new TBinaryProtocol(transport);
        return protocol;
	}
	public Object exec(Callback callBack) {
		Object obj = null;
		TTransport transport = new TSocket(thriftConfig.getIp(), thriftConfig.getPort());
		try {
			TProtocol protocol = new TBinaryProtocol(transport);
			transport.open();
			obj = callBack.run(protocol);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			transport.close();
		}
		return obj;
	}
	public ThriftConfig getLoginConfig() {
		return loginConfig;
	}
	public ThriftConfig getThriftConfig() {
		return thriftConfig;
	}
}
