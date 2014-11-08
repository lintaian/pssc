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
var exerciseBatches;
$('#video1').on('timeupdate', function(data) {
	for ( var e in exerciseBatches) {
		if (!exerciseBatches[e].done && exerciseBatches[e].timestamp == parseInt(data.target.currentTime)) {
			document.getElementById("video1").pause();
			exerciseBatches[e].done = true;
			Util.load('.video_exercise', 'exercise', 'id=' 
					+ exerciseBatches[e].exercise_batch_id.$oid 
					+ '&parentEle=.video_exercise', 
					function() {
				$('.video_exercise').show();
			});
			$('#video1').css({'top': -3000});
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
			exerciseBatches = data;
			document.getElementById("video1").play();
		}
	});
});
$('#video1').on('ended', function() {
	for ( var e in exerciseBatches) {
		exerciseBatches[e].done = false;
	}
});
</script>