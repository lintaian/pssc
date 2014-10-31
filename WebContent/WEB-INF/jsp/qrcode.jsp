<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <base href="<%=basePath%>">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">  
  <meta name="apple-mobile-web-app-capable" content="yes">  
  <meta name="apple-mobile-web-app-status-bar-style" content="black">  
  <title>泡桐小学-翻转课堂</title>
  <style type="text/css">
  	.upload {
		position: relative;
		z-index: 0;
		margin-left: 30%;
		margin-top: 200px;
		font-size: 30px;
	}
	.upload span {
		padding: 10px;
		background-color: rgb(242,204,129);
		border-radius: 6px;
		cursor: pointer;
	}
	.upload input {
		width: 147px;
		height: 50px;
		position: absolute;
		top: -8px;
		opacity: 0;
	}
  </style>
</head>
<body>
	<div class="upload" data-sid="${obj.sId }" data-cid="${obj.cId }" 
		data-eid="${obj.eId }" data-etype="${obj.eType }">
		<span>上传${obj.eType == 20 ? '图片' : (obj.eType == 21 ? '录音' : (obj.eType == 22 ? '视频' : '')) }</span>
		<form method="post" id="answerUpload"
			enctype="multipart/form-data">
			<input type="file" name="file">				
		</form>
	</div>
	<script src="js/lib/jquery.js"></script>
	<script>
		$(function() {
			$('body').on('change', '.upload input[type="file"]', function() {
				var val = $(this).val(),
					suffix = val.substr(val.lastIndexOf('.') + 1, val.length),
					eid = $('.upload').data('eid'),
					etype = $('.upload').data('etype'),
					sid = $('.upload').data('sid'),
					cid = $('.upload').data('cid');
				suffix = suffix.toLowerCase();
				var fixRight = false;
				switch (etype) {
				case 20:
					if (suffix == 'png' || suffix == 'gif' || suffix == 'jpg' || suffix == 'jpeg')
						fixRight = true;
					break;
				case 21:
					if (suffix == 'acc' || suffix == 'mp3')
						fixRight = true;
					break;
				case 22:
					if (suffix == 'mp4' || suffix == '3gp')
						fixRight = true;
					break;
				default:
					break;
				}
				if (fixRight) {
					var fd = new FormData(document.getElementById("answerUpload"));
					fd.append("eId", eid);
					fd.append("eType", etype);
					fd.append("cId", cid);
					fd.append("sId", sid);
					$.ajax({
						url: "exercise/uploader",
						type: "POST",
						data: fd,
						dataType: 'json',
						processData: false,  // tell jQuery not to process the data
						contentType: false,   // tell jQuery not to set contentType
						success: function(data) {
							if (data) {
								alert('文件上传成功!');
							}
						},
						error: function(data) {
						}
					});
				} else {
					alert('请选择正确格式的文件!');
				}
			});
		})
	</script>
</body>
</html>