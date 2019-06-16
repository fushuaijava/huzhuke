define([ 'jquery', 'msgbox', 'juicer', 'dateutil' ], function($, msgbox) {
	//上传图片路径
	var photopath = function(path) {
		
		return projectpath+path;
	};
	juicer.register('photopath', photopath);
	// 时间处理
	var datebuild = function(data) {
		var date = new Date(data);

		return date.Format("MM.dd hh:mm");
	};
	juicer.register('datebuild', datebuild);
	// 模板绘制
	var juicerview = function(data, tplId, parentId) {
		var tpl = document.getElementById(tplId).innerHTML;
		var html = juicer(tpl, data);
		$('#' + parentId).append(html);
	};
	var pagelist = function(pageid, tplId, parentId) {
		var myDate = new Date();
		var dateString = myDate.getTime();
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/house/page",// 路径
			data : {
				"start" : pageid
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				if(result.page.content.length>0){
					
					juicerview(result.page, tplId, parentId);
					//图片懒加载
					$('img.lazy').lazyload({
				    	 effect : "fadeIn",
						 threshold : 200,
					});
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
	var commentpagelist = function(pageid,houseid, tplId, parentId) {
		var myDate = new Date();
		var dateString = myDate.getTime();
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/comment/page",// 路径
			data : {
				"start" : pageid,
				"houseid":houseid
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				if(result.page.content.length>0){
					
					juicerview(result.page, tplId, parentId);
					//图片懒加载
					$('img.lazy').lazyload({
				    	 effect : "fadeIn",
						 threshold : 200,
					});
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
	var userproductlist = function(houseid, tplId, parentId) {
		var myDate = new Date();
		var dateString = myDate.getTime();
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/userproduct/houselist/"+houseid,// 路径
			/*data : {
				"start" : 0,
				"houseid":houseid
			},*/// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				if(result.list!=null){
					
					juicerview(result, tplId, parentId);
					$("#userproductdiv").show();
					$('#reservebtn2').click(function(){
						var userproid= this.dataset.userproid
						setTimeout(function() {
							location.href = projectpath
									+ '/static/htm/payconfirm.html?id='+getParameterByName('id')+"&userproid="+userproid;
						}, 2000)
					});
					//图片懒加载
					/*$('img.lazy').lazyload({
				    	 effect : "fadeIn",
						 threshold : 200,
					});*/
				}
			}
		});
	};
	var getParameterByName = function(name) {
		var match = RegExp('[?&]' + name + '=([^&]*)').exec(
				window.location.search);
		return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
	}
	var userhousedetailajax = function(houseid, tplId, parentId,userproid) {
		var url=projectpath + "/app/house/housedetail/" + houseid;
		if (userproid!=null&&userproid>0) {
			url=projectpath + "/app/userproduct/userproduct/" + userproid;
		} 
		$.ajax({
			type : "POST", // 提交方式
			url : url,// 路径
			success : function(result) {// 返回数据根据结果进行相应的处理
				if(result.tUser==null){
					$('#reservebtn').click(function(){
						msgbox.alert('您还没有登录，请您先登录再进行做客流程！');
						setTimeout(function() {
							location.href = projectpath
									+ '/static/htm/loginIn.html';
						}, 2000);
					});
				}else if(result.tUser!=null&&result.tUser.isValidInfo!=1){
					$('#reservebtn').click(function(){
						msgbox.alert('您还没有进行实名制验证，验证成功，我们有旅行金币赠送哦！为了您和他人的安全，请您先进行实名验证。');
						setTimeout(function() {
							location.href = projectpath
									+ '/static/htm/perInfo.html';
						}, 2000);
					});
				}else{
					$('#reservebtn').click(function(){
						setTimeout(function() {
							location.href = projectpath
									+ '/static/htm/payconfirm.html?id='+getParameterByName('id');
						}, 2000)
					});
				}
				juicerview(result, tplId, parentId);
				//$('#findmobile').attr('href',"wtai://wp//mc;"+$("#mobiletd").data("mobilephone")); 
				$('#findmobile').attr('href',"tel:"+$("#mobiletd").data("mobilephone"));

				//banner 轮播图
				function banner_wh(){
					$('.banner .bd a').width($('.banner').outerWidth()).height($('.banner').outerHeight())
				}
				banner_wh()
				
				TouchSlide({
					slideCell:"#banner",
					titCell:".hd ul",
					mainCell:".bd ul",
					effect:"leftLoop",
					autoPlay:true,//自动播放
					autoPage:true, //自动分页
					delayTime:500, //切换效果持续时间
					interTime:2500, //自动运行间隔
				});
				
				
				//banner图片大小控制
				function imgSize(pTag){
					pTag.each(function(){
						var wW = $(this).outerWidth()
						var wH = $(this).outerHeight()
						var t = $(this).children('img')
						var iW = t.width()
						var iH = t.height()
						var rateW = wW/iW
						var rateH = wH/iH
						var rateM = Math.max(rateW,rateH)
						var mT = -(iH*rateM-wH)/2
						var mL = -(iW*rateM-wW)/2
						
						t.width(iW*rateM).height(iH*rateM).css({'marginTop':mT,'marginLeft':mL})
					})
				};
				imgSize($('.banner .bd a'));
				
				$(window).resize(function(){
					banner_wh()
					imgSize($('.banner .bd a'))
				});

			}
		});
	}
	// 详情页面数据获取
	var housedetailajax = function(houseid, tplId, parentId) {
		userhousedetailajax(houseid, tplId, parentId);
	}
	return {
		pagelist : pagelist,
		getParameterByName : getParameterByName,
		housedetailajax:housedetailajax,
		commentpagelist:commentpagelist,
		userproductlist:userproductlist,
		userhousedetailajax:userhousedetailajax
	}
});