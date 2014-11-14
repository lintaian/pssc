package com.lps.pssc.module;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;

import com.lps.pssc.dao.impl.BaseDao;
import com.lps.pssc.util.SessionHelper;
import com.lps.pssc.util.TwoDimensionCode;

@IocBean
@InjectName
@At("/qrcode")
@Fail("json")
public class QrCodeModule {
	@Inject
	BaseDao baseDao;
	@Inject("refer:qrcodeHost")
	String qrcodeHost;
	
	@At("/*")
	@Ok("raw")
	public Object getVideo(String exerciseId, int exerciseType, String epId, HttpServletRequest req) throws Exception {
		StringBuffer content = new StringBuffer(qrcodeHost);
		content.append("/qrcode/validate/");
		content.append(SessionHelper.getUserIdStr(req));
		content.append("/");
		content.append(SessionHelper.getCWidStr(req));
		content.append("/");
		content.append(exerciseId);
		content.append("/");
		content.append(exerciseType);
		content.append("/");
		content.append(epId);
		return TwoDimensionCode.qRCodeCommon(content.toString(), "png", 8, null, 0);
	}
	@At("/validate/*")
	@GET
	@Ok("jsp:jsp.qrcode")
	public Object getVideoDict(String sId, String cId, String eId, int eType, String epId, HttpServletRequest req) throws Exception {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("sId", sId);
		rs.put("cId", cId);
		rs.put("eId", eId);
		rs.put("epId", epId);
		rs.put("eType", eType);
		return rs;
	}
}
