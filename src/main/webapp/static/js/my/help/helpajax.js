define(
		[ 'jquery', 'msgbox','juicer', 'dateutil' ],
		function($, msgbox) {
			//上传图片路径
			var photopath = function(path) {
				
				return projectpath+path;
			};
			juicer.register('photopath', photopath);
			var add = function(helpInfo, place, mobile, coin) {
				$.ajax({
					type : "POST", // 提交方式
					url : projectpath + "/app/helpinfo/add",// 路径
					data : {
						"helpInfo" : helpInfo,
						"place" : place,
						"mobile" : mobile,
						"coin" : coin
					},// 数据，这里使用的是Json格式进行传输
					success : function(result) {// 返回数据根据结果进行相应的处理

					}
				});
			};
			var getParameterByName = function(name) {
				var match = RegExp('[?&]' + name + '=([^&]*)').exec(
						window.location.search);
				return match
						&& decodeURIComponent(match[1].replace(/\+/g, ' '));
			}
			var addhelprelply = function(helpInfoId, replyInfo) {

				$
						.ajax({
							type : "POST", // 提交方式
							url : projectpath + "/app/helpinforeply/add",// 路径
							data : {
								"helpInfoId" : helpInfoId,
								"replyInfo" : replyInfo
							},// 数据，这里使用的是Json格式进行传输
							success : function(result) {// 返回数据根据结果进行相应的处理
								if (result.meta.rescode == 0) {
									msgbox
											.alert('已经成功回复成功，建议您直接联系该求助人，进行电话沟通，您的好心会让您的人生更加美好！');
									setTimeout(function () {
							            location.href=projectpath+'/static/htm/helpDetail.html?id='+helpInfoId;
							          },2000)
								} else {
									msgbox.alert(result.meta.msg);
								}

							}
						});

			};
			var helpcannel = function(helpInfoId) {
				$
						.ajax({
							type : "POST", // 提交方式
							url : projectpath + "/app/helpinfo/updatestate",// 路径
							data : {
								"id" : helpInfoId,
								"state":11
							},// 数据，这里使用的是Json格式进行传输
							success : function(result) {// 返回数据根据结果进行相应的处理
								if (result.meta.rescode == 0) {
									msgbox
											.alert('求助信息已经取消！');
									setTimeout(function () {
							            location.href=projectpath+'/static/htm/askhelp.html';
							          },2000)
								} else {
									msgbox.alert(result.meta.msg);
								}

							}
						});

			};
			var helpreplyfinish = function(helpInfoId) {
				$.ajax({
							type : "POST", // 提交方式
							url : projectpath + "/app/helpinfo/updatestate",// 路径
							data : {
								"id" : helpInfoId,
								"state":3
							},// 数据，这里使用的是Json格式进行传输
							success : function(result) {// 返回数据根据结果进行相应的处理
								if (result.meta.rescode == 0) {
									msgbox
											.alert('帮助完成已经确认，请您耐心等待');
									setTimeout(function () {
							            location.href=projectpath+'/static/htm/askhelp.html';
							          },2000)
								} else {
									msgbox.alert(result.meta.msg);
								}

							}
						});

			};
			var accepthelp = function(helpInfoId,helpUserId,helpReplyId,state) {
				$
						.ajax({
							type : "POST", // 提交方式
							url : projectpath + "/app/helpinfo/updatestate",// 路径
							data : {
								"id" : helpInfoId,
								"helpUserId" : helpUserId,
								"helpReplyId":helpReplyId,
								"state":state
							},// 数据，这里使用的是Json格式进行传输
							success : function(result) {// 返回数据根据结果进行相应的处理
								if (result.meta.rescode == 0) {
									msgbox
											.alert('已经接受帮助请在帮助完成之后确认，并付给对方金币！');
									setTimeout(function () {
							            location.href=projectpath+'/static/htm/askhelp.html';
							          },2000)
								} else {
									msgbox.alert(result.meta.msg);
								}

							}
						});

			};
			var finishhelp = function(helpInfoId,smscode) {
				$
						.ajax({
							type : "POST", // 提交方式
							url : projectpath + "/app/helpinfo/helpsendcoin",// 路径
							data : {
								"id" : helpInfoId,
								"smscode":smscode
							},// 数据，这里使用的是Json格式进行传输
							success : function(result) {// 返回数据根据结果进行相应的处理
								if (result.meta.rescode == 0) {
									msgbox
											.alert('帮助已经完结，感谢您的使用！');
									setTimeout(function () {
							            location.href=projectpath+'/static/htm/askhelp.html';
							          },2000)
								} else {
									msgbox.alert(result.meta.msg);
								}

							}
						});

			};
			var smsfinishhelp = function(helpInfoId) {
				$
						.ajax({
							type : "POST", // 提交方式
							url : projectpath + "/app/sms/smshelpfinish",// 路径
							data : {
								"id" : helpInfoId
							},// 数据，这里使用的是Json格式进行传输
							success : function(result) {// 返回数据根据结果进行相应的处理
								if (result.meta.rescode == 0) {
									$("#helpsmscodediv").show();
									$("#helpid").val(helpInfoId);
									
//									msgbox
//											.alert('已经接受帮助请在帮助完成之后确认，并付给对方金币！');
//									setTimeout(function () {
//							            location.href=projectpath+'/static/htm/askhelp.html';
//							          },2000);
								} else {
									msgbox.alert(result.meta.msg);
								}

							}
						});

			};
			
			// 模板绘制
			var juicerview = function(data, tplId, parentId) {
				var tpl = document.getElementById(tplId).innerHTML;
				var html = juicer(tpl, data);
				$('#' + parentId).append(html);
				$('.chathe').click(function (e){
					//myalbcchatwin.chatwithhe(''+this.dataset.userid);
					//window.open(projectpath+'/static/htm/chat.html?userid='+this.dataset.userid, '和他联系', 'toolbar=no, status=no,scrollbars=0,resizable=0,menubar＝0,location=0,width=700,height=500');
					location.href=projectpath+'/static/htm/chat.html?userid='+this.dataset.userid					
				});
			};
			// 帮助分页查询
			var pagelist = function(pageid, tplId, parentId) {
				var myDate = new Date();
				var dateString = myDate.getTime();
				$.ajax({
					type : "POST", // 提交方式
					url : projectpath + "/app/helpinfo/page",// 路径
					data : {
						"start" : pageid
					},// 数据，这里使用的是Json格式进行传输
					success : function(result) {// 返回数据根据结果进行相应的处理
						if(result.page.content.length>0){
							
							juicerview(result.page, tplId, parentId);
						}else{
							if (pageid>0) {
								msgbox.alert('亲，没有更多数据了，不要再点我了，会疼的！');
							}
						}
						$(".loadmorepage").data("canload","yes");
						$(".waitMore").hide();
					}
				});
			};
			// 求助分页查询
			var askhelppage = function(pageid, tplId, parentId) {
				var myDate = new Date();
				var dateString = myDate.getTime();
				$.ajax({
					type : "POST", // 提交方式
					url : projectpath + "/app/helpinfo/askhelpinfo",// 路径
					
					data : {
						"start" : pageid
					},// 数据，这里使用的是Json格式进行传输
					success : function(result) {// 返回数据根据结果进行相应的处理
						juicerview(result.page, tplId, parentId);
						$('.acceptbtn').click(function (e){
							accepthelp(this.dataset.helpInfoId,this.dataset.helpUserId,this.dataset.helpReplyId,2);
						});
						$('.cancelhelpbtn').click(function (e){
							helpcannel(this.dataset.helpInfoId);
						});
						$('.finishhelpbtn').click(function (e){
							smsfinishhelp(this.dataset.helpInfoId);
						});
					}
				});
			};
			// 帮助分页查询
			var givehelppage = function(pageid, tplId, parentId) {
				var myDate = new Date();
				var dateString = myDate.getTime();
				$.ajax({
					type : "POST", // 提交方式
					url : projectpath + "/app/helpinforeply/myhomepage",// 路径
					data : {
						"start" : pageid
					},// 数据，这里使用的是Json格式进行传输
					success : function(result) {// 返回数据根据结果进行相应的处理
						juicerview(result.page, tplId, parentId);
						
						$('.helpreplyfinishbtn').click(function (e){
							helpreplyfinish(this.dataset.helpInfoId);
						});
					}
				});
			};
			// 详情页面数据获取
			var helpdetailajax = function(helpid, tplId, parentId) {
				$.ajax({
					type : "POST", // 提交方式
					url : projectpath + "/app/helpinfo/helpdetail/" + helpid,// 路径
					success : function(result) {// 返回数据根据结果进行相应的处理
						juicerview(result, tplId, parentId);
					}
				});
			}
			// 详情页面数据获取
			var helpreplypagelist = function(pageId, helpid, tplId, parentId) {
				$.ajax({
					type : "POST", // 提交方式
					url : projectpath + "/app/helpinforeply/page/" + helpid
							+ "/" + pageId,// 路径
					success : function(result) {// 返回数据根据结果进行相应的处理
						if(result.page.content.length>0){
							
							juicerview(result.page, tplId, parentId);
						}else{
							if (pageId>0) {
								msgbox.alert('亲，没有更多数据了，不要再点我了，会疼的！');
							}
						}
						$(".loadmorepage").data("canload","yes");
						$(".waitMore").hide();
					}
				});
			}
			// 等级方法
			var levelbuild = function(data) {
				var level = 1;
				for (level; data >= 10; level++) {
					data = data / 10;
					level = level + 1;
				}
				return level;
			};
			juicer.register('level_build', levelbuild);

			// 时间处理
			var datebuild = function(data) {
				var date = new Date(data);

				return date.Format("MM.dd hh:mm");
			};
			juicer.register('datebuild', datebuild);
			return {
				add : add,
				addhelprelply : addhelprelply,
				pagelist : pagelist,
				helpdetailajax : helpdetailajax,
				getParameterByName : getParameterByName,
				helpreplypagelist : helpreplypagelist,
				askhelppage:askhelppage,
				givehelppage:givehelppage,
				finishhelp:finishhelp,
				helpreplyfinish:helpreplyfinish
			}
		});