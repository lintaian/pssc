<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="info">
	<div class="photo">
		<img id="myPhoto" src="${obj.Photo}">
	</div>
	<div class="basic-info">
		<ul>
			<li>
				<span class="label letter">姓</span>名:
				<span class="text">${obj.Name}</span>
			</li>
			<li>
				<span class="label">用户名:</span>
				<span class="text">${obj.UserName}</span>
			</li>
			<li style="position: relative;">
				<span class="label letter">密</span>码:
				<div class="password"></div>
				<div class="pwdList hide">
					<div id="patternLock"></div>
				</div>
			</li>
			<li>
				<span class="label letter">班</span>级:
				<span class="text">${obj.klass}</span>
			</li>
		</ul>
		<div class="fun-btn">
			<div id="uploadPhoto">
				<span>上传图片</span>
				<form action="user/uploader" method="post" id="uploadForm"
					enctype="multipart/form-data">
					<input type="file" name="file">				
				</form>
			</div>
			<span id="updatePwd">更新密码</span>
		</div>
	</div>
</div>
<script>
	var lock = new PatternLock('#patternLock', {
		margin: 18,
		onDraw: function(pattern){
			var temp = '';
			for (var i = 0; i < pattern.length; i++) {
				temp += '*';
			}
			$('.password').text(temp);
		},
		onReset: function() {
			$('.password').text('');
		}
	});
</script>