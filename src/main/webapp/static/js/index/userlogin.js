define([ 'jquery', 'baseurl', 'md5', 'module/msgbox', 'validate', 'cookie',
		'lib/juicer-min' ], function($, baseurl, md5, msgbox, cookie) {
	// 上传图片路径
	var photopathbuild = function(path) {

		return baseurl.root + path;
	};
	juicer.register('photopathbuild', photopathbuild);
	var loginTpl, init;

	loginTpl = function(data) {
		var tpl = document.getElementById('js_userInfo').innerHTML;
		var user_tpl = juicer(tpl);
		var html = user_tpl.render(data);
		$('.js_user_content').html(html);

		var headTpl = document.getElementById('js_headerBar_tpl').innerHTML;
		var head_tpl = juicer(headTpl);
		var head_html = head_tpl.render(data);
		console.log(head_tpl);
		$('.js_headBar').html(head_html);
	}

	init = function() {
		var userid = $.cookie('userid'), password = $.cookie('password');

		if ((userid != 'null' && userid != undefined)
				&& (password != 'null' && password != undefined)) {
			// console.log(userid+'~~~'+password);
			userid = userid.trim();
			var key = '123';
			var myDate = new Date();
			var dateString = myDate.getTime();
			var md5String = $.md5(userid + '123' + dateString);
			console.log(1111126576)
			$.ajax({
				type : 'POST',
				url : baseurl.root + 'app/login/login',
				data : {
					"userid" : userid,
					"password" : password,
					"key" : key,
					"logintime" : dateString,
					"md5key" : md5String
				},
				success : function(data) {
					if (data.meta.rescode == 0) {
						loginTpl(data);hassign();initimlogin();
					} else {
						console.log(1)
						msgbox.alert(data.meta.msg);
					}
				}
			});
		}

	}
	var dosign = function() {
		$.ajax({
			type : 'POST',
			url : baseurl.root + 'app/user/sign',
			success : function(data) {
				if (data.meta.rescode == 0) {
					//$("#dosign").html("已签到")
					$("#dosign").addClass('icon_icon45');
					msgbox.alert("签到完成！");
				} else {
					console.log(1)
					msgbox.alert(data.meta.msg);
				}
			}
		});
	}
	var hassign = function() {
		$.ajax({
			type : 'POST',
			url : baseurl.root + 'app/user/hassign',
			success : function(data) {
				if (data.meta.rescode == 0) {
					if (data.issign) {
						//$("#dosign").html("已签到");
						$("#dosign").addClass('icon_icon45');
						$('#dosign').on('click',function(){
							msgbox.alert("亲，您今天已经签到过了，请明天再来吧！");
						});
					} else {
						//$("#dosign").html("签到");
						$("#dosign").removeClass('icon_icon45');
						$('#dosign').on('click',function(){
							dosign();
						});
					}
				} else {
					$("#dosign").html("");
				}
			}
		});
	}
	var initimlogin=function (){
		$.ajax({
			type : "POST", // 提交方式
			url : baseurl.root + "/app/user/get/imuser",// 路径
			
			success : function(result) {// 返回数据根据结果进行相应的处理
				if(result.meta.rescode==0){
					var sdk = new WSDK();
					 
					sdk.Base.login({
					    uid:result.tUser.id+"",
					    appkey: result.albcappkey,
					    credential: result.tUser.impwd,
					    timeout: 4000,
					    success: function(data){
					    	console.log('login success', data);
					    	sdk.Base.getUnreadMsgCount({
					    	    count: 10,
					    	    success: function(data){
					    	    	if (data.data.length>0) {
					    	    		var msgcount=0;
										for (var msgint = 0; msgint < data.data.length; msgint++) {
											msgcount=data.data[msgint].msgCount;
										}
										if (msgcount>99) {
											$("#noreadmsg").html("99+");
										}else{
											$("#noreadmsg").html(""+msgcount);
										}
									}
					    	        console.log('get unread msg count success' ,data);
					    	    },
					    	    error: function(error){
					    	        console.log('get unread msg count fail' ,error);
					    	    }
					    	});
					    },
					    error: function(error){
					       // {code: 1002, resultText: 'TIMEOUT'}
					       console.log('login fail', error);
					    }
					});
				}else{
					msgbox.alert('亲，只有登录之后才能与他人聊天，请您先登录。');
					setTimeout(function () {
			            location.href=projectpath+'/static/htm/loginIn.html';
			          },2000);
				}
			}
		});
		
	}

	return {
		loginTpl : loginTpl,
		init : init
	}
});
