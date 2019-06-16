define([ 'jquery', 'msgbox', 'juicer', 'dateutil' ], function($, msgbox) {
	// 上传图片路径
	var photopath = function(path) {

		return projectpath + path;
	};
	juicer.register('photopath', photopath);
	// 时间处理
	var datebuild = function(data) {
		var date = new Date(data);

		return date.Format("yyyy.MM.dd hh:mm");
	};
	juicer.register('datebuild', datebuild);
	// 模板绘制
	var juicerview = function(data, tplId, parentId) {
		var tpl = document.getElementById(tplId).innerHTML;
		var html = juicer(tpl, data);
		$('#' + parentId).append(html);
	};
	var guestpagelist = function(pageid, tplId, parentId) {
		var myDate = new Date();
		var dateString = myDate.getTime();
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/guestorder/guestpage",// 路径
			data : {
				"start" : pageid
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				juicerview(result.page, tplId, parentId);
				initbtnevent();
			}
		});
	};
	var masterpagelist = function(pageid, tplId, parentId) {
		var myDate = new Date();
		var dateString = myDate.getTime();
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/guestorder/masterpage",// 路径
			data : {
				"start" : pageid
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				juicerview(result.page, tplId, parentId);
				initbtnevent();
			}
		});
	};
	// 初始化按钮事件方法
	var initbtnevent = function() {
		$('.acceptbtn').click(function(e) {
			acceptguestorder(this.dataset.guestOrderId);
		});
		$('.confirmstartbtn').click(function(e) {
			smsvisitstart(this.dataset.guestOrderId);
		});
		$('.confirmendbtn').click(function(e) {
			smsvisitfinish(this.dataset.guestOrderId);
		});
		$('.cancelguestbtn').click(function(e) {
			cannelguestorder(this.dataset.guestOrderId,this.dataset.guestOrderState);
		});
		$('.commentbtn').click(function(e) {
			commentorder(this.dataset.guestOrderId);
		});
		
		$('.topaybtn').click(function(e){
			$.ajax({
				type : 'POST',
				url : projectpath + "/app/guestorder/prepay",
				data : {"id":this.dataset.guestOrderId},
				success : function(result) {
					// 目前暂且定义添加成功就返回交流中心
					if (result.meta.rescode == 0) {
						//msgbox.alert('做客订单已经创建成功！请您尽快完成支付');
						setTimeout(function() {
							location.href = projectpath
									+ '/static/htm/trip.html';
						}, 2000)
					} else {
						msgbox.alert(result.meta.msg);
					}
				}
			});
		
	});
	}
	var acceptguestorder = function(guestOrderId) {
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/guestorder/updatestate",// 路径
			data : {
				"id" : guestOrderId,
				"orderState":2
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				if (result.meta.rescode == 0) {
					msgbox.alert('已经接受帮助请在帮助完成之后确认，并付给对方金币！');
					setTimeout(function() {
						location.href = projectpath
								+ '/static/htm/reception.html';
					}, 2000)
				} else {
					msgbox.alert(result.meta.msg);
				}

			}
		});

	}
	var cannelguestorder = function(guestOrderId,state) {
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/guestorder/updatestate",// 路径
			data : {
				"id" : guestOrderId,
				"orderState":state
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				if (result.meta.rescode == 0) {
					msgbox.alert('行程已经取消，期待您的下一次使用！');
					setTimeout(function() {
						if(state==11){
							
							location.href = projectpath
							+ '/static/htm/trip.html';
						}else{
							location.href = projectpath
							+ '/static/htm/reception.html';
						}
					}, 2000)
				} else {
					msgbox.alert(result.meta.msg);
				}

			}
		});

	}
	var startguestorder = function(guestOrderId) {
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/guestorder/updatestate",// 路径
			data : {
				"id" : guestOrderId,
				"orderState":3,
				"code":$("#guestordersmscode").val()
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				if (result.meta.rescode == 0) {
					msgbox.alert('对方已经开始接待您，祝您有一个愉快的旅程！');
					setTimeout(function() {
						location.href = projectpath
								+ '/static/htm/reception.html';
					}, 2000)
				} else {
					msgbox.alert(result.meta.msg);
				}

			}
		});

	}
	var endguestorder = function(guestOrderId) {
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/guestorder/updatestate",// 路径
			data : {
				"id" : guestOrderId,
				"orderState":4,
				"code":$("#guestordersmscode").val()
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				if (result.meta.rescode == 0) {
					msgbox.alert('这段旅行已经结束，期待与您的下一次相遇！');
					setTimeout(function() {
						location.href = projectpath
								+ '/static/htm/trip.html';
					}, 2000)
				} else {
					msgbox.alert(result.meta.msg);
				}

			}
		});

	}
	var smsvisitstart = function(guestOrderId) {
		$
				.ajax({
					type : "POST", // 提交方式
					url : projectpath + "/app/sms/smsvisitstart",// 路径
					data : {
						"guestOrderId" : guestOrderId
					},// 数据，这里使用的是Json格式进行传输
					success : function(result) {// 返回数据根据结果进行相应的处理
						if (result.meta.rescode == 0) {
							$("#guestordersmscodediv").show();
							$("#guestOrderId").val(guestOrderId);
							$('#gobtn').click(function(e) {
								startguestorder(guestOrderId);
							});
//							msgbox
//									.alert('已经接受帮助请在帮助完成之后确认，并付给对方金币！');
//							setTimeout(function () {
//					            location.href=projectpath+'/static/htm/askhelp.html';
//					          },2000);
						} else {
							msgbox.alert(result.meta.msg);
						}

					}
				});

	};
	var commentorder=function(orderid){
		$("#commentdiv").show();
		$("#commentOrderId").val(orderid);
		$('#commentsubbtn').click(function(e) {
			commensubtorder(orderid);
			
		});
	};
	var commensubtorder=function (orderid){

		$
				.ajax({
					type : "POST", // 提交方式
					url : projectpath + "/app/comment/add",// 路径
					data : {
						"orderId" : orderid,
						"level":$("#level").val(),
						"comment":$("#comment").val()
					},// 数据，这里使用的是Json格式进行传输
					success : function(result) {// 返回数据根据结果进行相应的处理
						if (result.meta.rescode == 0) {
							msgbox.alert('您已经成功评论，感谢您对我们的支持，构建最好的互助旅行生态圈，我们一起努力！');
							setTimeout(function() {
								location.href = projectpath
										+ '/static/htm/trip.html';
							}, 2000)
						} else {
							msgbox.alert(result.meta.msg);
						}

					}
				});

	
	
	};
	var smsvisitfinish = function(guestOrderId) {
		$
				.ajax({
					type : "POST", // 提交方式
					url : projectpath + "/app/sms/smsvisitfinish",// 路径
					data : {
						"guestOrderId" : guestOrderId
					},// 数据，这里使用的是Json格式进行传输
					success : function(result) {// 返回数据根据结果进行相应的处理
						if (result.meta.rescode == 0) {
							$("#guestordersmscodediv").show();
							$("#guestOrderId").val(guestOrderId);
							$('#gobtn').click(function(e) {
								endguestorder(guestOrderId);
								
							});
//							msgbox
//									.alert('已经接受帮助请在帮助完成之后确认，并付给对方金币！');
//							setTimeout(function () {
//					            location.href=projectpath+'/static/htm/askhelp.html';
//					          },2000);
						} else {
							msgbox.alert(result.meta.msg);
						}

					}
				});

	};
	var getParameterByName = function(name) {
		var match = RegExp('[?&]' + name + '=([^&]*)').exec(
				window.location.search);
		return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
	}

	return {
		guestpagelist : guestpagelist,
		masterpagelist : masterpagelist,
		getParameterByName : getParameterByName
	}
});