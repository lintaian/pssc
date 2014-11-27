package com.lps.pssc.util;

import java.util.Timer;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import com.lps.pssc.config.ActiveConfig;

public class MySetup implements Setup {
	@Override
	public void init(NutConfig arg0) {
		ActiveConfig config = Mvcs.ctx.getDefaultIoc().get(ActiveConfig.class);
		Timer timer = new Timer();
		timer.schedule(new UpdateClassStatus(), 60000, config.getPollTime());
	}
	@Override
	public void destroy(NutConfig arg0) {

	}
}
