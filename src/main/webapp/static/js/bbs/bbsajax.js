define(
		[ 'jquery', 'msgbox', 'juicer', 'dateutil' ],
		function($, msgbox) {

			var getParameterByName = function(name) {
				var match = RegExp('[?&]' + name + '=([^&]*)').exec(
						window.location.search);
				return match
						&& decodeURIComponent(match[1].replace(/\+/g, ' '));
			}
			var setreplyinfo = function(replyId, name) {

			}
			var initReplyBtn = function() {
				$(".replyi").live(
						'click',
						function() {
							var replyId = $(this).data("id");
							var replyname = $(this).data("replyname");
							// $("#replytouser").html(replyname);
							// $("#senreplydiv").show();
							$('#replyinfo').attr('placeholder',
									'回复' + replyname + '：').focus()
							$("#replyId").val(replyId);
						});
				$('.communtheme').click(function() {
					$('#replyinfo').attr('placeholder', '回复楼主：').blur()
				});
				$('.hisfoot').click(function(e) {
					e.stopPropagation();
				})
				$(".moresubreply").click(
						function() {
							invreplysubpagelist($(this).data('pageid'), $(this)
									.data('id'), 'senrbbsrelplytpl', $(this)
									.parents('.conTopR'));
							$(this).data('pageid', $(this).data('pageid') + 1);
						});

			}
			var addinvrelply = function(invitationId, content, replyId) {

				$.ajax({
							type : "POST", // 提交方式
							url : projectpath + "/app/reviceinv/add",// 路径
							data : {
								"invitationId" : invitationId,
								"content" : content,
								"replyId" : replyId
							},// 数据，这里使用的是Json格式进行传输
							success : function(result) {// 返回数据根据结果进行相应的处理
								if (result.meta.rescode == 0) {
									msgbox
											.alert('已经成功回复成功，建议您直接联系该求助人，进行电话沟通，您的好心会让您的人生更加美好！');
									setTimeout(
											function() {
												location.href = projectpath
														+ '/static/htm/communtheme.html?id='
														+ invitationId;
											}, 2000);

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
				initReplyBtn();
			};

			// 详情页面数据获取
			var invitationdetailajax = function(invitationid, tplId, parentId,
					rtplId, rparentId) {
				$.ajax({
					type : "POST", // 提交方式
					url : projectpath + "/app/invitation/view/" + invitationid,// 路径
					success : function(result) {// 返回数据根据结果进行相应的处理
						juicerview(result.tInvitation, tplId, parentId);
						juicerview(result.tReviceInvPage, rtplId, rparentId);

					}
				});
			}
			// 详情页面回复数据获取
			var invreplypagelist = function(invitationId, pageId, tplId,
					parentId) {
				$.ajax({
					type : "POST", // 提交方式
					data : {
						"invitationId" : invitationId,
						"start" : pageId
					},
					url : projectpath + "/app/reviceinv/page",// 路径
					success : function(result) {// 返回数据根据结果进行相应的处理
						if (result.page.content.length > 0) {

							juicerview(result.page, tplId, parentId);
						} else {
							if (pageId > 0) {
								msgbox.alert('亲，没有更多数据了，不要再点我了，会疼的！');
							}
						}
						$(".loadmorepage").data("canload", "yes");
						$(".waitMore").hide();
					}
				});
			}
			// 详情页面子回复数据获取
			var invreplysubpagelist = function(pageId, replyparentId, tplId,
					parentObject) {
				$.ajax({
					type : "POST", // 提交方式
					data : {
						"parentId" : replyparentId,
						"start" : pageId
					},
					url : projectpath + "/app/reviceinv/subpage",// 路径
					success : function(result) {// 返回数据根据结果进行相应的处理
						var tpl = document.getElementById(tplId).innerHTML;
						var html = juicer(tpl, result.page);
						$(parentObject).append(html);
						initReplyBtn();
					}
				});
			}
			// 上传图片路径
			var photopath = function(path) {

				return projectpath + path;
			};
			juicer.register('photopath', photopath);
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
				addinvrelply : addinvrelply,
				invreplypagelist:invreplypagelist,
				invitationdetailajax : invitationdetailajax,
				getParameterByName : getParameterByName,
				invreplysubpagelist : invreplysubpagelist
			}
		});