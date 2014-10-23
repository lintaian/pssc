define([ 'jquery'], function(jquery) {
	(function($) {
		var Util = function() {

		};
		
		Util.msg = {
			timeout: null,
			show: function(title, content, type, time) {
				this.close(10);
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
				time == 0 ? null : this.mySetTimeout(time);
			},
			close: function(time) {
				clearTimeout(this.timeout);
				$('#msg').animate({
					bottom: -250
				}, time || 1000);
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
		
		Util.setLocation = function(option) {
			var html = '';
			for (var i = 0; i < option.length; i++) {
				html += '<a data-url="';
				html += option[i].url;
				html += '">';
				html += option[i].name;
				html += '</a>';
				if (i != (option.length - 1)) {
					html += ' &gt; ';
				}
			}
			$('#location').html(html);
		}
		
		if (!window.Util) {
			window['Util'] = Util;
		}
	})(jquery);
});