<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="video">
	<video id="video1" controls="controls" preload="metadata" data-video-id="${obj._id }">
		<source src="${obj.url }" type="video/mp4" />
		Your browser does not support the video tag.
	</video>
	<div class="video_exercise hide">
	
	</div>
</div>
<script src="js/custom/video.js"></script>