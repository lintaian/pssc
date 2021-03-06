define([ 'jquery'], function(jquery) {
	(function($) {
		var Util = function() {

		};
		Util.isLive = false;
		
		Util.msg = {
			timeout: null,
			netError: false,
			show: function(title, content, type, time, netError) {
				this.close(10, netError && this.netError && $('#msg').is(':visible'));
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
				this.netError = netError;
			},
			close: function(time, onlyClearTimeout) {
				clearTimeout(this.timeout);
				if (!onlyClearTimeout) {
					$('#msg').animate({
						bottom: -250
					}, time || 1000);
				}
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
					Util.error(data);
				}
			});
		}
		
		Util.error = function(data) {
			if(data.status == 403) {
				Util.alert.show(function() {
					window.location.reload(true);
				});
			} else if(data.status == 0) {
				Util.msg.show('错误提示', '请检查你的网络!', 'error', 10000, true);
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
						html += '<span class="p_change btn p_prev">上一张</span>';
					} 
					if (index < this.pics.length - 1) {
						html += '<span class="p_change btn p_next">下一张</span>';
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
		
		Util.imageView = {
			show: function(url) {
				var winHeight = Util.getWinHeight();
				$('#imageView img').attr('src', url);
				$('#imageViewBg').show().height(winHeight - 20);
				$('#imageView').show().height(winHeight - 20);
				Util.zoomImage.init($('#imageView img')[0]);
				this.reLocation();
			},
			reLocation: function() {
				var imageView = $('#imageView'),
					img = $('#imageView img');
				var iHeight = img.height(),
				height = imageView.height();
				img.css({marginTop: (height - iHeight) > 0 ? (height - iHeight) / 2 : 0});
			},
			close: function() {
				$('#imageViewBg').hide();
				$('#imageView').hide();
				$('#imageView img').attr('src', '');
				Util.zoomImage.destroy();
			}
		}
		
		Util.zoomImage = {
			originWidth: 0,
			originHeight: 0,
			distance: 0,
			img: null,
			init: function(img) {
				this.distance = 0;
				this.originHeight = img.naturalHeight;
				this.originWidth = img.naturalWidth;
				this.img = img;
				this.img.width = img.naturalWidth > $('#imageView').width() ? 
						$('#imageView').width() : 
						(img.naturalWidth < 200 ? 200 : img.naturalWidth);
				this.setZoomScale();
			},
			reSet: function() {
				this.distance = 0;
			},
			zoom: function(e) {
				if (e.originalEvent.targetTouches.length == 2) {
					var x1 = e.originalEvent.targetTouches[0].pageX;
					var y1 = e.originalEvent.targetTouches[0].pageY;
					var x2 = e.originalEvent.targetTouches[1].pageX;
					var y2 = e.originalEvent.targetTouches[1].pageY;
					var distance = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
					if (this.distance) {
						var temp = 2 * (distance - this.distance);
						var w = this.img.width + temp;
						if (w > 10 * this.originWidth) {
							w = 10 * this.originWidth;
						} else if (w < this.originWidth * 0.1) {
							w = this.originWidth * 0.1;
						}
						w = w < 200 ? 200 : w;
						this.img.width = w;
					}
					this.distance = distance;
					Util.imageView.reLocation();
					this.setZoomScale();
				}
			},
			setZoomScale: function() {
				var scale = parseInt((this.img.width * 1.0 / this.originWidth) * 100);
				$('#imageView .iv-zoom-scale').text(scale + '%(' + this.img.width + ' * ' + this.img.height + ')');
			},
			destroy: function() {
				this.distance = 0;
				this.img = null;
			}
		}
		
		Util.loader = {
			timeout: '',
			started: false,
			show: function(text) {
				text = text || '文件上传中,请稍等...';
				var $text = $('#loading .text'),
					$load = $('#loading');
				$load.show();
				$('#modal').show();
				$text.text(text);
				var h = $load.height(),
					w = $load.width(),
					winH = Util.getWinHeight(),
					winW = Util.getWinWidth();
				$load.css({
					top: (winH - h) / 2,
					left: (winW - w) / 2
				});
				this.startTimeout();
			},
			close: function() {
				$('#loading').hide();
				$('#modal').hide();
				this.stopTimeout();
			},
			startTimeout: function() {
				this.timeout = setTimeout(function() {
					$.ajax({
						url: 'upload/info',
						type: 'get',
						dataType: 'json',
						success: function(data) {
							var progress,
								flag;
							if (Util.loader.started) {
								if (data.current == 0) {
									progress = 100;
									flag = true;
								} else {
									progress = parseInt(data.current / data.sum * 100);
								}
							} else {
								progress = parseInt(data.current / data.sum * 100);
								Util.loader.started = true;
							}
							$('#loading .progress').text(progress + '%');
							if(!flag) {
								Util.loader.startTimeout();
							} else {
								Util.loader.stopTimeout();
							}
						}
					})
				}, 500);
			},
			stopTimeout: function() {
				clearTimeout(this.timeout);
				this.started = false;
			}
		}
		
		Util.alert = {
			ok: function() {
			},
			show: function(ok, text) {
				this.ok = ok || this.ok;
				text = text || '该账号长时间未操作或者已从其他地方登陆,如果不是你本人操作,请注意你的密码可能已经被盗!';
				var $text = $('#alert .text'),
					$alert = $('#alert');
				$alert.show();
				$('#modal2').show();
				$text.text(text);
				var h = $alert.height(),
					w = $alert.width(),
					winH = Util.getWinHeight(),
					winW = Util.getWinWidth();
				$alert.css({
					top: (winH - h) / 2,
					left: (winW - w) / 2
				});
				$('video').remove();
				$('audio').remove();
			},
			close: function() {
				$('#alert').hide();
				$('#modal2').hide();
				this.ok();
			}
		}
		Util.preemptiveSuccess = {
			interval: '',
			show: function(text) {
				var $ele = $('#preemptive_success'),
					text = text || '小泡泡,恭喜你抢到了!请准备回答!';
				$ele.show();
				$('#modal2').show();
				$('#preemptive_success .text').text(text);
				var h = $ele.height(),
					w = $ele.width(),
					winH = Util.getWinHeight(),
					winW = Util.getWinWidth();
				$ele.css({
					top: (winH - h) / 2,
					left: (winW - w) / 2
				});
				this.setDynamic();
			},
			close: function() {
				$('#preemptive_success').hide();
				$('#modal2').hide();
			},
			setDynamic: function() {
				setTimeout(function() {
					Util.preemptiveSuccess.close();
				}, 10000);
			}
		}
		
		Util.getImgNaturalDimensions = function(img, callback) {
		    var nWidth = 0, nHeight = 0;
		    if (img.naturalWidth) { // 现代浏览器
		        nWidth = img.naturalWidth;
		        nHeight = img.naturalHeight;
		    } else { // IE6/7/8
		        var imgae = new Image();
		        image.src = img.src;
		        image.onload = function() {
		            callback(image.width, image.height);
		        }
		    }
		    return {width: nWidth, height: nHeight};
		}
		
		Util.getWinHeight = function() {
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
		Util.getWinWidth = function() {
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
		
		if (!window.Util) {
			window['Util'] = Util;
		}
	})(jquery);
});