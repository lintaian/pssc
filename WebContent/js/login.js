/*global require*/
'use strict';

require.config({
	paths: {
		jquery: 'lib/jquery',
		patternLock: 'lib/patternLock'
	},
	shim: {
		patternLock: {deps: ['jquery']}
	}
});

require(['jquery', 'patternLock'], function ($) {
	$(function() {
		/**
		 * 改变窗口时，页面自适应
		 */
		resize(true);
		$('.main').show();
		$(window).resize(function() {
			resize(true);
		});
		/**
		 * 禁止滑动事件
		 */
		$('body').on('touchmove', function(e) {
			e.preventDefault();
		});
		$('.left').on('click', '.name', function() {
			$('.pwdList').hide();
			$('.nameList').toggle();
		});
		$('.left').on('click', '.pwd', function() {
			$('.nameList').hide();
			$('.pwdList').toggle();
		});
		$('.nameList').on('click', 'a[data-value]', function() {
			var name = $('.nameText').text(),
				value = $(this).data('value');
			if (name.length < 11) {
				$('.nameText').text(name + value);
			}
		});
		$('.nameList').on('click', 'a[data-close]', function() {
			$('.nameList').hide();
		});
		$('.nameList').on('click', 'a[data-back]', function() {
			var name = $('.nameText').text();
			name = name.substr(0, name.length - 1);
			$('.nameText').text(name);
		});
		$('body').on('click', '.right', function() {
			var name = $('.nameText').text();
			var pwd = lock.getPattern() || '';
			if (name == '' || pwd == '') {
				myAlert.show(null, '用户名或密码不能为空!');
			} else {
				$.ajax({
					url: 'login',
					type: 'post',
					data: JSON.stringify({
						username: name,
						password: pwd
					}),
					dataType: 'json',
					success: function(data) {
						if (data.status) {
							window.location.href = 'main';
						} else {
							myAlert.show(null, data.msg);
						}
					}
				});
			}
		});
		$('#alert').on('click', '.btn', function() {
			myAlert.close();
		});
		
		var lock = new PatternLock('#patternLock', {
			margin: 18,
			onDraw: function(pattern){
				var temp = '';
				for (var i = 0; i < pattern.length; i++) {
					temp += '*';
				}
				$('.pwdText').text(temp);
			},
			onReset: function() {
				$('.pwdText').text('');
			}
		});
	});
	
	function resize(windResize) {
		if (windResize) {
			var height = getWinHeight(),
				top = (height - $('.main').height() - 170) / 2,
				left = (getWinWidth() - $('.main').width()) /2;
			$('.main').css('top', top);
			$('.main').css('left', left);
			$('body').height(height);
		}
	}
	var myAlert = {
		ok: function() {
		},
		show: function(ok, text) {
			this.ok = ok || this.ok;
			text = text || '用户名或密码错误';
			var $text = $('#alert .text'),
				$alert = $('#alert');
			$alert.show();
			$('#modal').show();
			$text.text(text);
			var h = $alert.height(),
				w = $alert.width(),
				winH = getWinHeight(),
				winW = getWinWidth();
			$alert.css({
				top: (winH - h) / 2,
				left: (winW - w) / 2
			});
		},
		close: function() {
			$('#alert').hide();
			$('#modal').hide();
			this.ok();
		}
	}
	function getWinHeight() {
	    var winHeight = 0;
	    // 获取窗口高度
	    if (window.innerHeight)
	        winHeight = window.innerHeight;
	    else if ((document.body) && (document.body.clientHeight))
	        winHeight = document.body.clientHeight;
	    // 通过深入 Document 内部对 body 进行检测，获取窗口大小
	    if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth)
	    {
	        winHeight = document.documentElement.clientHeight;
	    }
	    if(navigator.userAgent.indexOf("Safari") > -1 && navigator.userAgent.indexOf("Chrome") < 0
	    		&& navigator.userAgent.indexOf("CriOS") < 0) {
	    	winHeight -= 20;
		} 
	    return winHeight;
	};
	/**
	 * 获取窗口宽度
	 * @returns {number}
	 */
	function getWinWidth() {
	    var winWidth = 0;
	    // 获取窗口宽度
	    if (window.innerWidth)
	        winWidth = window.innerWidth;
	    else if ((document.body) && (document.body.clientWidth))
	        winWidth = document.body.clientWidth;
	    if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth)
	    {
	        winWidth = document.documentElement.clientWidth;
	    }
	    return winWidth;
	};
});
