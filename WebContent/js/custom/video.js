$(function() {
	var myVideo = document.getElementById("video1");
	var exerciseBatches;
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
	$.ajax({
		url: 'learn/video/dict',
		type: 'get',
		data: 'id=' + $('#video1').data('videoId'),
		dataType: 'json',
		success: function(data) {
			exerciseBatches = data;
		}
	});
	$('#video1').on('timeupdate', function(data) {
		for ( var e in exerciseBatches) {
			if (!exerciseBatches[e].done && exerciseBatches[e].timestamp == parseInt(data.target.currentTime)) {
				myVideo.pause();
				exerciseBatches[e].done = true;
				Util.load('.video_exercise', 'learn/exercise', 'id=' + exerciseBatches[e].exercise_batch_id.$oid);
				$('#video1').css({'top': -3000});
				$('.video_exercise').show();
				break;
			}
		}
	});
	$('#video1').on('loadedmetadata', function(data) {
		myVideo.play();
	});
	$('#video1').on('ended', function(data) {
		for ( var e in exerciseBatches) {
			exerciseBatches[e].done = false;
		}
	});
	$('body').on('click', '.e_finish', function() {
		$('.video_exercise').hide();
		$('#video1').css({'top': 0});
		myVideo.play();
	});
});