define(['jquery'], function($) {
	
	$('body').on('click', '.picture_exercise .e_finish', function() {
		$('.picture_exercise').hide();
		$('.picture').css('top', 0);
		Util.location.remove(-1);
	});
	$('body').on('click', '.p_page .p_prev', function() {
		Util.img.prev();
	});
	$('body').on('click', '.p_page .p_next', function() {
		Util.img.next();
	});
	$('body').on('click', '.p_page .p_finish', function() {
		Util.location.jump(-2);
	});
});