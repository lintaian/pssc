<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="coursewareDetail">
	<div class="item_title">视频</div>
	<div class="videoBatchList">
		<c:forEach items="${obj.videoBatches }" var="vb">
			<div class="item" data-id="${vb._id }" data-name="${vb.title }">
				<img src="${vb.photo == '' ? 'photo/courseware.jpg' : vb.photo}" />
				<div class="name">${vb.title }</div>
				<div class="face icon_face laugh"></div>
				<div class="operate c_icon c_icon_video"></div>
			</div>
		</c:forEach>
	</div>
	<div class="item_title">课件</div>
	<div class="pictureBatchList">
		<c:forEach items="${obj.pictureBatches }" var="pb">
			<div class="item" data-id="${pb._id }" data-name="${pb.title }">
				<img src="${pb.photo == '' ? 'photo/courseware.jpg' : pb.photo}"/>
				<div class="name">${pb.title }</div>
				<div class="face icon_face laugh"></div>
				<div class="operate  c_icon c_icon_video"></div>
			</div>
		</c:forEach>
	</div>
	<div class="item_title">习题</div>
	<div class="exerciseBatchList">
		<c:forEach items="${obj.exerciseBatches }" var="eb">
			<div class="item" data-id="${eb._id }" data-name="${eb.title }">
				<img src="${eb.photo == '' ? 'photo/courseware.jpg' : eb.photo}"/>
				<div class="name">${eb.title }</div>
				<div class="face icon_face laugh"></div>
				<div class="operate  c_icon c_icon_exercise"></div>
			</div>
		</c:forEach>
	</div>
</div>