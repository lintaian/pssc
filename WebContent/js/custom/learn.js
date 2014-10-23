define(['jquery'], function($) {
	$('body').on('click', '.student.me', function() {
		$('.outerPage').load('learn/courseware');
		Util.setLocation([{
			url: 'learn',
			name: '我的学习'
		},{
			url: 'learn/coursewareList',
			name: '课件列表'
		}]);
	});
});