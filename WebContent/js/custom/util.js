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
		
		Util.location = {
			list: [],
			set: function(list) {
				this.list = list;
				var html = '';
				for (var i = 0; i < list.length; i++) {
					html += '<a data-url="';
					html += list[i].url;
					html += '" data-parent-ele="';
					html += list[i].parentEle || '.outerPage';
					html += '" data-fn="';
					html += list[i].fn || '';
					html += '" data-id="';
					html += list[i].id || '';
					html += '">';
					html += list[i].name;
					html += '</a>';
					if (i != (list.length - 1)) {
						html += ' &gt; ';
					}
				}
				$('#location').html(html);
			},
			jump: function(index) {
				index = (this.list.length + index) % this.list.length;
				var $this = $('#location a:eq(' + index + ')');
				var url = $this.data('url'),
					parentPage = $this.data('parentEle');
				Util.load(parentPage, url, '', function() {
					if ($this.data('fn')) {
						eval($this.data('fn'));
						locationFn($this.data('id'));
					}
				});
				var temp = [];
				for (var i = 0; i < this.list.length; i++) {
					temp.push(this.list[i]);
					if (i >= index) break;
				}
				this.set(temp);
			},
			add: function(obj) {
				this.list.push(obj);
				this.set(this.list);
			},
			remove: function(index, length) {
				index = (this.list.length + index) % this.list.length;
				var temp = [];
				length = length || 1;
				var count = 0;
				for (var i = 0; i < this.list.length; i++) {
					if (i >= index) {
						if (count < length) {
							count++;
						} else {
							temp.push(this.list[i]);
						}
					} else {
						temp.push(this.list[i]);
					}
				}
				this.set(temp);
			}
		}
		
		Util.load = function(ele, url, data, suc) {
			$.ajax({
				url: url,
				type: 'get',
				data: data,
				dataType: 'html',
				success: function(html) {
					$(ele).html(html);
					suc && suc(html);
				},
				error: function(data) {
					if (data.status == 403) {
						window.location.reload(true);
					}
				}
			});
		}
		
		Util.error = function(data) {
			if (data.status == 403) {
				window.location.reload(true);
			}
		}
		
		Util.img = {
			pics: [],
			picExs: [],
			pageIndex: 0,
			setData: function(d) {
				this.pics = d.pics || [];
				this.picExs = d.picExs || [];
				this.changePage(0);
			},
			changePage: function(index) {
				index = index && (index < 0 ? 0 : index);
				if (index < this.pics.length) {
					this.pageIndex = index;
					var exercise_package_id;
					if (this.pageIndex <= index) {
						for (var i = 0; i < this.picExs.length; i++) {
							if (index == this.picExs[i].position) {
								exercise_package_id = this.picExs[i].exercise_package_id.$oid;
								break;
							}
						}
					}
					if (exercise_package_id) {
						Util.load('.picture_exercise', 'exercise', 'id=' 
								+ exercise_package_id 
								+ '&parentEle=.picture_exercise', 
								function() {
							$('.picture').css('top', -3000);
							$('.picture_exercise').show();
							Util.location.add({
								url: '',
								name: $('#exercise').data('title')
							});
							Util.img.setSrc();
						});
					} else {
						this.setSrc();
					}
					var html = '';
					if (index > 0) {
						html += '<span class="p_change btn p_prev">上一题</span>';
					} 
					if (index < this.pics.length - 1) {
						html += '<span class="p_change btn p_next">下一题</span>';
					}
					if (index == this.pics.length - 1) {
						html += '<span class="p_finish btn">完成</span>';
					}
					if (this.pics.length > 1) {
						html += (index + 1) + ' / ' + this.pics.length;
					}
					$('#picture').find('.p_page').html(html);
				}
			},
			prev: function() {
				this.changePage(this.pageIndex - 1);
			},
			next: function() {
				this.changePage(this.pageIndex + 1);
			},
			setSrc: function() {
				$('#picture').find('.p_img').children('img').attr('src', this.pics[this.pageIndex].url);
			}
		}
		
		if (!window.Util) {
			window['Util'] = Util;
		}
	})(jquery);
});