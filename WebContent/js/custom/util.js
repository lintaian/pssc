define([ 'jquery'], function(jquery) {
	(function($) {
		var Util = function() {

		};
		
		Util.msg = {
			timeout: null,
			show: function(title, content, type, time) {
				$('#msg').find('.msgContent').html(content);
				$('#msg').find('.titleText').html(title);
				$('#msg').removeClass();
				switch (type) {
				case 'info':
					$('#msg').addClass('info');
					break;
				case 'warning':
					$('#msg').addClass('warning');
					break;
				case 'error':
					$('#msg').addClass('error');
					break;
				default:
					break;
				}
				$('#msg').show().animate({
					bottom: 5
				}, 1000);
				this.mySetTimeout(time);
			},
			close: function() {
				$('#msg').animate({
					bottom: -250
				}, 1000);
			},
			mySetTimeout: function(time) {
				this.timeout = setTimeout(function() {
					Util.msg.close();
				}, time || 3000);
			},
			recover: function() {
				clearTimeout(this.timeout);
				$('#msg').stop().animate({
					bottom: 5
				}, 1000);
			}
		}
		
		if (!window.Util) {
			window['Util'] = Util;
		}
	})(jquery);
});