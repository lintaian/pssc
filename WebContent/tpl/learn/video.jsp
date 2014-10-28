<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="video">
	<video id="video1" controls="controls">
		<source src="${obj.url }" type="video/mp4" />
		Your browser does not support the video tag.
	</video>
</div>
<script>
	var myVideo = document.getElementById("video1");
	myVideo.play();
	videoResize();
	$(window).resize(function(){
		videoResize();
	});
	$('#fullIcon').click(function() {
		videoResize();
	});
	function videoResize() {
		myVideo.width = $('.outerPage').width();
		myVideo.height = $('.outerPage').height();
	}
</script>