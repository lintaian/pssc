define(['jquery'], function($) {
	//student
	$('body').on('click', '.studentList .item.me', function() {
		Util.load('.outerPage', 'learn/courseware');
		Util.location.add({
			url: 'learn/courseware',
			name: '课件列表'
		});
	});
	//courseware
	$('body').on('click', '#courseware .month', function() {
		var date = $(this).data('date');
		var subjectId = $('.subject.active').data('id');
		Util.load('.outerPage', 'learn/courseware', 'date=' + date + '&subjectId=' + subjectId);
	});
	$('body').on('click', '#courseware .subject', function() {
		var subjectId = $(this).data('id');
		var date = $('.month.active').data('date');
		Util.load('.outerPage', 'learn/courseware', 'date=' + date + '&subjectId=' + subjectId);
	});
	$('body').on('click', '.coursewareList .item', function() {
		var id = $(this).data("id");
		Util.load('.outerPage', 'learn/coursewareDetail', 'id=' + id);
		Util.location.add({
			url: 'learn/coursewareDetail?id=' + id,
			name: $(this).data('name')
		});
	});
	$('body').on('click', '#coursewareDetail .videoList .operate', function() {
		var id = $(this).parent('.item').data('id');
		Util.load('.outerPage', 'video', 'id=' + id, function() {
			document.getElementById("video1").width = $('.outerPage').width();
			document.getElementById("video1").height = $('.outerPage').height();
		});
		Util.location.add({
			url: 'video?id=' + id,
			name: $(this).parent('.item').data('name')
		});
	});
	$('body').on('click', '#coursewareDetail .exerciseBatchList .operate', function() {
		var id = $(this).parent('.item').data('id');
		Util.load('.outerPage', 'exercise', 'id=' + id + '&parentEle=.outerPage');
		Util.location.add({
			url: 'exercise?id=' + id,
			name: $(this).parent('.item').data('name')
		});
		$('.outerPage').addClass('prevAndAfterDoExercise');
	});
	$('body').on('click', '.prevAndAfterDoExercise .e_finish', function() {
		Util.location.virtualClick(-1);
		$('.outerPage').removeClass('prevAndAfterDoExercise');
	});
});