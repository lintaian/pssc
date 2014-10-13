/*global require*/
'use strict';

require.config({
	paths: {
		jquery: 'lib/jquery',
		fastclick: 'lib/fastclick' 
	},
	shim: {
		
	}
});

require(['jquery', 'fastclick'], function ($, FastClick) {
	var startY;
	$(function() {
		/**
		 * 改变窗口时，页面自适应
		 */
		resize(true);
		$(window).resize(function() {
			resize(true);
		});
		/**
		 * 禁止滑动事件
		 */
		$('body').on('touchmove', function(e) {
			e.preventDefault();
		});
	});
	function resize(windResize) {
		var height = getWinHeight();
		if (windResize) {
//			$('.main').height(height);
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
	    return winHeight;
	};
	window.addEventListener('load', function () {
		FastClick.attach(document.body);
	}, false);
});
