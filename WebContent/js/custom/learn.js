define(['jquery'], function($) {
	$('body').on('click', '.student.me', function() {
		$('.outerPage').load('learn/courseware');
		Util.location.set([{
			url: 'learn',
			name: '我的学习'
		},{
			url: 'learn/courseware',
			name: '课件列表'
		}]);
	});
});