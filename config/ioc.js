var ioc = {
  	config : {
        type : 'org.nutz.ioc.impl.PropertiesProxy',
        fields : {
            paths : ['thrift.properties', 'mongo.properties', 'config.properties', 'cache.properties']
        }
    },
	thriftConfig: {
		type: 'com.lps.pssc.config.ThriftConfig',
		args: [{java: "$config.get('remote.ip')"}, 
		       {java: "$config.get('remote.port')"},
		       {java: "$config.get('remote.timeout')"}]
	},
	loginConfig: {
		type: 'com.lps.pssc.config.ThriftConfig',
		args: [{java: "$config.get('login.ip')"}, 
		       {java: "$config.get('login.port')"},
		       {java: "$config.get('login.timeout')"}]
	},
	mongoConfig: {
		type: 'com.lps.pssc.config.MongoConfig',
		args: [{java: "$config.get('mongo.host')"}, 
		       {java: "$config.get('mongo.port')"},
			   {java: "$config.get('mongo.db')"}]
	},
	qrcodeHost: {
		type: 'java.lang.String',
		args: [{java: "$config.get('qrcode.host')"}]
	},
	cacheConfig: {
		type: 'com.lps.pssc.config.CacheConfig',
		args: [{java: "$config.get('cache.timeout')"},
		       {java: "$config.get('cache.pollTime')"},
		       {java: "$config.get('cache.unActiveTime')"}]
	}
}