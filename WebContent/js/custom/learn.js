define(['jquery'], function($) {
	$('body').on('click', '.student.me', function() {
		Util.load('.outerPage', 'learn/courseware');
		Util.location.set([{
			url: 'learn',
			name: '我的学习'
		},{
			url: 'learn/courseware',
			name: '课件列表'
		}]);
	});
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
});