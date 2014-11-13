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
//		var width = $('.outerPage').width();
//		width = $('#video1').css('top') == '-3000px' ? width - 17 : width; 
		$('#video1').width($('#video').width());
		$('#video1').height($('.outerPage').height());
	}
});