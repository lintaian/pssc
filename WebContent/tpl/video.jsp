<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="video" data-cw-id="${obj.cw_id }" data-cw-type="${obj.cw_type }">
	<video id="video1" controls="controls" preload="metadata" data-video-id="${obj.video._id }">
		<source src="${obj.video.url }" type="video/ogg" />
		<source src="${obj.video.url }" type="video/mp4" />
		<source src="${obj.video.url }" type="video/webm" />
		Your browser does not support the video tag.
	</video>
	<div class="video_exercise hide"></div>
</div>
<script>
var exercisePackages;
$('#video1').on('timeupdate', function(data) {
	for ( var e in exercisePackages) {
		if (!exercisePackages[e].done && exercisePackages[e].timestamp == parseInt(data.target.currentTime)) {
			document.getElementById("video1").pause();
			exercisePackages[e].done = true;
			Util.load('.video_exercise', 'exercise', 'id=' 
					+ exercisePackages[e].exercise_package_id.$oid 
					+ '&parentEle=.video_exercise', 
					function() {
				$('#video1').css({'top': -3000});
				$('.video_exercise').show();
				Util.location.add({
					url: '',
					name: $('#exercise').data('title')
				});
			});
			break;
		}
	}
});
$('#video1').on('loadedmetadata', function() {
	$.ajax({
		url: 'video/dict',
		type: 'get',
		data: 'id=' + $('#video1').data('videoId'),
		dataType: 'json',
		success: function(data) {
			exercisePackages = data;
			document.getElementById("video1").play();
		}
	});
});
$('#video1').on('ended', function() {
	if ($('#video').data('cwType') == 1) {
		
	} else {
		Util.location.jump(-2);
	}
});
</script>