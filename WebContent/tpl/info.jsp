<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="info">
	<div class="photo">
		<img id="myPhoto" src="${obj.photo}">
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
			<li style="position: relative;">
				<span class="label letter">密</span>码:
				<div class="password"></div>
				<input type="hidden" class="passwordText">
				<div class="pwdList hide">
					<a class="n_1" data-value="1"></a>
					<a class="n_2" data-value="2"></a>
					<a class="n_3" data-value="3"></a>
					<a class="n_4" data-value="4"></a>
					<a class="n_5" data-value="5"></a>
					<a class="n_6" data-value="6"></a>
					<a class="n_7" data-value="7"></a>
					<a class="n_8" data-value="8"></a>
					<a class="n_9" data-value="9"></a>
					<a class="n_close" data-close></a>
					<a class="n_0" data-value="0"></a>
					<a class="n_back" data-back></a>
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