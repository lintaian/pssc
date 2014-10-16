define(['jquery'], function($) {
	$('body').on('click', '#recordPage a.clickable', function() {
		var page = $(this).data('page');
		$('.outerPage').load('record?page=' + page);
	});
});