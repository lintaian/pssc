/*global require*/
'use strict';

require.config({
	paths: {
		jquery: 'lib/jquery',
		logout: 'custom/logout'
	},
	shim: {
		
	}
});

require(['jquery', 'logout'], function ($) {
	var startY, rate = 5000, state = '';
	$(function() {
		/**
		 * 改变窗口时，页面自适应
		 */
		resize(true);
		$(window).resize(function() {
			resize(true);
		});
		/**
		 * 全屏切换
		 */
		$('#fullIcon').click(function() {
			$('.content').toggleClass('full');
			resize(false);
		});
		/**
		 * 禁止滑动事件
		 */
		$('body').on('touchmove', function(e) {
			e.preventDefault();
		});
		/**
		 * 特定可以滑动模块
		 */
		$('body').on('touchmove', '.scroll', function(e) {
			var y = e.originalEvent.pageY,
				clientHeight = e.currentTarget.clientHeight,
				scrollHeight = e.currentTarget.scrollHeight,
				scrollTop = e.currentTarget.scrollTop;
			if (!(scrollHeight == clientHeight || 
					(scrollTop == 0 && startY < y) || 
					((scrollTop + clientHeight) == scrollHeight && startY > y))) {
				e.stopPropagation();
			}
		});
		/**
		 * 开始滑动
		 */
		$('body').on('touchstart', '.scroll', function(e) {
			startY = e.originalEvent.pageY;
		});
		/**
		 * 改变加载的外部页面
		 */
		$('body').on('click', '[data-change-page]', function(e) {
			e.preventDefault();
			var url = $(this).data('changePage');
			$('.outerPage').load(url);
			$('.left').find('li').each(function() {
				$(this).removeClass('active');
			});
			$(this).parent('li').addClass('active');
			$('#location').text($(this).text());
		})
		/**
		 * 加载默认显示页面
		 */
		$('[data-change-page="html/learn.html"]').click();
		/**
		 * 轮询查看是否上课
		 */
		setInterval(function() {
			$.ajax({
				url: 'teach/state',
				type: 'get',
				dataType: 'json',
				success: function(data) {
					if (data.learning) {
						rate = 1000;
						state = data.state;
					}
				} 
			})
		}, rate);
	});
	function resize(windResize) {
		var height = getWinHeight();
		if ($('.content').hasClass('full')) {
			$('.outerPage').height(height - 40);
		} else {
			$('.outerPage').height(height - 40 - 70 - 40);
		}
		if (windResize) {
			$('.left').height(height);
			$('.right').height(height);
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
});
