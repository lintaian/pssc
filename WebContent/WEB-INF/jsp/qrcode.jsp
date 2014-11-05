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
					cid = $('.upload').data('cid'),
					url = 'upload/';
				suffix = suffix.toLowerCase();
				var fixRight = false;
				switch (etype) {
				case 20:
					if (suffix == 'png' || suffix == 'gif' || suffix == 'jpg' || suffix == 'jpeg') {
						fixRight = true;
						url += 'img';
					}
					break;
				case 21:
					if (suffix == 'acc' || suffix == 'mp3') {
						fixRight = true;
						url += 'audio';
					}
					break;
				case 22:
					if (suffix == 'mp4' || suffix == '3gp') {
						fixRight = true;
						url += 'video';
					}
					break;
				default:
					break;
				}
				if (fixRight) {
					var fd = new FormData(document.getElementById("answerUpload"));
					$.ajax({
						url: url,
						type: "POST",
						data: fd,
						dataType: 'json',
						processData: false,  // tell jQuery not to process the data
						contentType: false,   // tell jQuery not to set contentType
						success: function(data) {
							if (data) {
								$.ajax({
									url: 'exercise/subjective',
									type: 'get',
									data: {
										answer: data.url,
										eId: eid,
										eType: etype,
										cId: cid,
										sId: sid
									},
									dataType: 'json',
									success: function(d) {
										if (d.status) {
											alert('文件上传成功!');
										} else {
											alert('文件上传失败!');
										}
									}
								})
							} else {
								Util.msg.show('提示信息', data.msg, 'error');
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