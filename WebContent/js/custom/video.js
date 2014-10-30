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
		url: 'video/dict',
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
				Util.load('.video_exercise', 'exercise', 'exerciseBatchId=' + exerciseBatches[e].exercise_batch_id.$oid, 
						function() {
					$('.video_exercise').show();
				});
				$('#video1').css({'top': -3000});
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
	$('body').on('click', '.e_answer', function() {
		var a = $(this).data('answer'),
			exerciseId = $('#exercise').data('id'),
			originAnswer = $('.e_my_answer_text').text();
		if (!($(this).hasClass('single') && a == originAnswer)) {
			$.ajax({
				url: 'exercise/objective',
				type: 'get',
				data: 'exerciseId=' + exerciseId + '&answer=' + a + '&originAnswer=' + originAnswer + 
				'&single=' + $(this).hasClass('single'),
				dataType: 'json',
				success: function(data) {
					$('.e_my_answer_text').text(data.answer);
				}
			});
		}
	});
	$('body').on('change', '#exercise .uploadImg input[type="file"]', function() {
		var val = $(this).val();
		var suffix = val.substr(val.lastIndexOf('.') + 1, val.length);
		suffix = suffix.toLowerCase();
		if (suffix == 'png' || suffix == 'gif' || suffix == 'jpg' || suffix == 'jpeg') {
			var fd = new FormData(document.getElementById("answerUpload"));
			fd.append("exerciseId", $('#exercise').data('id'));
			$.ajax({
				url: "exercise/uploader",
				type: "POST",
				data: fd,
				dataType: 'json',
				processData: false,  // tell jQuery not to process the data
				contentType: false,   // tell jQuery not to set contentType
				success: function(data) {
					if (data) {
						Util.msg.show('提示信息', '图片上传成功!');
						$('.e_subjective_my_answer').attr('src', data.image).show();
					}
				},
				error: function(data) {
					Util.error(data);
				}
			});
		} else {
			Util.msg.show('错误提示', '请选择正确格式的图片!', 'error');
		}
	});
});