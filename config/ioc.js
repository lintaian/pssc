var ioc = {
  	config : {
        type : 'org.nutz.ioc.impl.PropertiesProxy',
        fields : {
            paths : ['thrift.properties', 'mongo.properties']
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
		args: [{java: "$config.get('host')"}, 
		       {java: "$config.get('port')"},
			   {java: "$config.get('db')"}]
	}
}