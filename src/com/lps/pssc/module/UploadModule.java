package com.lps.pssc.module;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bson.types.ObjectId;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.lps.pssc.dao.impl.BaseDao;
import com.lps.pssc.util.MyHelper;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@IocBean
@InjectName
@At("/upload")
@Fail("json")
public class UploadModule {
	@Inject
	BaseDao baseDao;
	
	@At("/video")
	@Ok("json")
	@AdaptBy(type=UploadAdaptor.class, args = { "${app.root}/temp" })
	public Object uploadVideo(@Param("file") File file, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			String f = file.toString();
			String fix = f.substring(f.lastIndexOf(".") + 1, f.length());
			fix = fix.toLowerCase();
			if ("mp4".equals(fix) || "3gp".equals(fix)) {
				String url = "uploadFiles/videos/" + new ObjectId() + "." + fix;
				File myfile = new File(req.getServletContext().getRealPath("/") + url);
				MyHelper.fileCopy(file, myfile);
				rs.put("url", url);
			} else {
				rs.put("status", false);
				rs.put("msg", "文件格式错误,请上传mp4/3gp格式的视频");
			}
		} catch (Exception e) {
			rs.put("status", false);
			rs.put("msg", "系统错误,请重试");
		}
		file.delete();
		return rs;
	}
	@At("/img")
	@Ok("json")
	@AdaptBy(type=UploadAdaptor.class, args = { "${app.root}/temp" })
	public Object uploadImg(@Param("file") File file, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			String f = file.toString();
			String fix = f.substring(f.lastIndexOf(".") + 1, f.length());
			fix = fix.toLowerCase();
			if ("png".equals(fix) || "jpg".equals(fix) || "jpeg".equals(fix) || "gif".equals(fix)) {
				String url = "uploadFiles/images/" + new ObjectId() + "." + fix;
				File myfile = new File(req.getServletContext().getRealPath("/") + url);
				MyHelper.fileCopy(file, myfile);
				rs.put("url", url);
			} else {
				rs.put("status", false);
				rs.put("msg", "文件格式错误,请上传png/jpg/jpeg/gif格式的图片");
			}
		} catch (Exception e) {
			rs.put("status", false);
			rs.put("msg", "系统错误,请重试");
		}
		file.delete();
		return rs;
	}
	@At("/audio")
	@Ok("json")
	@AdaptBy(type=UploadAdaptor.class, args = { "${app.root}/temp" })
	public Object uploadAudio(@Param("file") File file, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			String f = file.toString();
			String fix = f.substring(f.lastIndexOf(".") + 1, f.length());
			fix = fix.toLowerCase();
			if ("mp3".equals(fix) || "acc".equals(fix)) {
				String url = "uploadFiles/audios/" + new ObjectId() + "." + fix;
				File myfile = new File(req.getServletContext().getRealPath("/") + url);
				MyHelper.fileCopy(file, myfile);
				rs.put("url", url);
			} else {
				rs.put("status", false);
				rs.put("msg", "文件格式错误,请上传mp3/acc格式的音频");
			}
		} catch (Exception e) {
			rs.put("status", false);
			rs.put("msg", "系统错误,请重试");
		}
		file.delete();
		return rs;
	}
	
	
	@At("/draw")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	public Object uploadDrawImg(Map<String, String> obj, HttpServletRequest req) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("status", true);
		try {
			String data = obj.get("data");
			data = data.substring(data.indexOf("base64,") + 7);
			String url = "uploadFiles/images/" + new ObjectId() + ".png";
			File myfile = new File(req.getServletContext().getRealPath("/") + url);
			FileOutputStream fos = new FileOutputStream(myfile);
			Base64.decode(data, fos);
			rs.put("url", url);
		} catch (Exception e) {
			rs.put("status", false);
			rs.put("msg", "系统错误,请重试");
		}
		return rs;
	}
}