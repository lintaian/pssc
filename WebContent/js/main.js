/*global require*/
'use strict';

require.config({
	paths: {
		jquery: 'lib/jquery',
		patternLock: 'lib/patternLock',
		util: 'custom/util',
		logout: 'custom/logout',
		teach: 'custom/teach',
		record: 'custom/record',
		info: 'custom/info',
		learn: 'custom/learn',
		video: 'custom/video',
		exercise: 'custom/exercise',
		canvas: 'custom/canvas'
	},
	shim: {
		patternLock: {deps: ['jquery']},
		info: {deps: ['patternLock']}
	}
});

require(['jquery', 'patternLock', 'util', 'logout', 'teach', 'record', 'info', 'learn',
         'video', 'exercise', 'canvas'], function ($) {
	var startY;
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
		if (!$(this).hasClass('unclickable')) {
			$('.content').toggleClass('full');
			resize(false);
		}
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
		Util.load('.outerPage', url);
		$('.left').find('li').each(function() {
			$(this).removeClass('active');
		});
		$(this).parent('li').addClass('active');
		Util.location.set([{
			url: url,
			name: $(this).text()
		}]);
	})
	$('body').on('click', '#location a', function() {
		var url = $(this).data('url');
		Util.load('.outerPage', url);
		Util.location.jump($(this).text());
	});
	/**
	 * 加载默认显示页面
	 */
	$('[data-change-page="learn"]').click();
	
	$('#msg').on('click', '.close', function() {
		Util.msg.close();
	});
	$('#msg').on('mouseenter', function() {
		Util.msg.recover();
	});
	$('#msg').on('mouseleave', function() {
		Util.msg.mySetTimeout();
	});
	$('#msg').on('touchstart', function() {
		Util.msg.recover();
	});
	$('#msg').on('touchend', function() {
		Util.msg.mySetTimeout();
	});
	
	Util.msg.show('通知信息', '小朋友,欢迎回来!', 'info');
	
	
	/**
	 * 改变窗口大小
	 */
	function resize(windResize) {
		var height = getWinHeight();
		if ($('.content').hasClass('full')) {
			var h = height - 40;
			if(navigator.userAgent.indexOf("Safari") > -1 && navigator.userAgent.indexOf("Chrome") < 0) {
				h -= 20;
			} 
			$('.outerPage').height(h);
		} else {
			$('.outerPage').height(height - 40 - 70 - 40);
		}
		if (windResize) {
			$('.left').height(height);
			$('.right').height(height);
		}
	}
	/**
	 * 获取窗口高度
	 */
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
