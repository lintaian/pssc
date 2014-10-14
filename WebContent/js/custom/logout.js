define(['jquery'], function($) {
	$('body').on('click', '.logout .yes', function() {
		window.location.href = 'logout';
	});
	$('body').on('click', '.logout .no', function() {
		$('[data-change-page="html/learn.html"]').click();
	});
});