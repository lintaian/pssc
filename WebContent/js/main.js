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
		canvas: 'custom/canvas',
		picture: 'custom/picture',
		preemptive: 'custom/preemptive',
		workbook: 'custom/workbook'
	},
	shim: {
		patternLock: {deps: ['jquery']},
		info: {deps: ['patternLock']}
	}
});

require(['jquery', 'patternLock', 'util', 'logout', 'teach', 'record', 'info', 'learn',
         'video', 'exercise', 'canvas', 'picture', 'preemptive', 'workbook'], function ($) {
	var startY, startX;
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
			x = e.originalEvent.pageX,
			clientHeight = e.currentTarget.clientHeight,
			scrollHeight = e.currentTarget.scrollHeight,
			scrollTop = e.currentTarget.scrollTop,
			clientWidth = e.currentTarget.clientWidth,
			scrollWidth = e.currentTarget.scrollWidth,
			scrollLeft = e.currentTarget.scrollLeft;
		if (Math.abs(y - startY) >= Math.abs(x - startX)) {
			if (!(scrollHeight == clientHeight || 
					(scrollTop == 0 && startY < y) || 
					((scrollTop + clientHeight) == scrollHeight && startY > y))) {
				e.stopPropagation();
			}
		} else {
			if (!(scrollWidth == clientWidth ||
					(scrollLeft == 0 && startX < x) ||
					((scrollLeft + clientWidth) == scrollWidth && startX > x))) {
				e.stopPropagation();
			}
		}
	});
	/**
	 * 开始滑动
	 */
	$('body').on('touchstart', '.scroll', function(e) {
		startY = e.originalEvent.pageY;
		startX = e.originalEvent.pageX;
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
	$('body').on('click', '#location a:not(:last)', function() {
		Util.location.jump($(this).index());
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
	
	$('body').on('click', '.iv-close', function() {
		Util.imageView.close();
	});
	$('body').on('click', 'img.viewable', function() {
		var url = $(this).attr('src');
		Util.imageView.show(url);
	});
	$('.iv-img img').on('touchstart', function(e) {
		Util.zoomImage.reSet();
	});	
	$('.iv-img img').on('touchmove', function(e) {
		Util.zoomImage.zoom(e);
	});	
	
	Util.msg.show('通知信息', '小朋友,欢迎回来!', 'info');
	
	
	/**
	 * 改变窗口大小
	 */
	function resize(windResize) {
		var height = Util.getWinHeight();
		if ($('.content').hasClass('full')) {
			var h = height - 40;
			$('.outerPage').height(h);
		} else {
			$('.outerPage').height(height - 40 - 70 - 40);
		}
		if (windResize) {
			$('.left').height(height);
			$('.right').height(height);
		}
	}
});
