define(['jquery'], function($) {
	$(window).resize(function(){
		if ($('#video1').length > 0) {
			videoResize();
		}
	});
	$('body').on('click', '#fullIcon', function() {
		if ($('#video1').length > 0) {
			videoResize();
		}
	});
	
	function videoResize() {
		$('#video1').width($('#video').width());
		$('#video1').height($('.outerPage').height());
	}
});