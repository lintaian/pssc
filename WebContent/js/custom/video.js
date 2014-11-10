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
		$('#video1').width($('.outerPage').width());
		$('#video1').height($('.outerPage').height());
	}
	
	$('body').on('click', '.video_exercise .e_finish', function() {
		$('.video_exercise').hide();
		$('#video1').css({'top': 0});
		Util.location.remove(-1);
		document.getElementById("video1").play();
	});
});