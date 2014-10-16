<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="info">
	<div class="photo">
		<img src="${obj.photo}">
	</div>
	<div class="basic-info">
		<ul>
			<li>
				<span class="label letter">姓</span>名:
				<span class="text">${obj.realName}</span>
			</li>
			<li>
				<span class="label">用户名:</span>
				<span class="text">${obj.name}</span>
			</li>
			<li>
				<span class="label letter">密</span>码:
				<input type="password" class="password">
			</li>
			<li>
				<span class="label letter">班</span>级:
				<span class="text">${obj.klass}</span>
			</li>
		</ul>
		<div class="fun-btn">
			<span id="uploadPhoto">上传图片</span>
			<span id="updatePwd">更新密码</span>
		</div>
	</div>
</div>