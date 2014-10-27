define(['jquery'], function($) {
	$('body').on('click', '#recordPage a.clickable', function() {
		var page = $(this).data('page');
		Util.load('.outerPage', 'record', 'page=' + page)
	});
});