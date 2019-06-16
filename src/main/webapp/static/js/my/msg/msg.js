var projectpath=location.protocol+'//'+location.host+'/zuoke';
require.config({
	baseUrl: projectpath+"/static/js",
    paths : {
        jquery: 'lib/jquery',
        juicer:'lib/juicermin',
        dateutil:'lib/dateutil',
        helpajax:'my/help/helpajax',
        msgbox:'module/msgbox'
        
    }
});
require(['jquery','helpajax','msgbox','juicer','dateutil'], function($,helpajax,msgbox) {
	
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
	var imidbuild=function(luid){
		return sdk.Base.getNick(luid);
	}
	juicer.register('imidbuild', imidbuild);
	// 模板绘制
	var juicerview = function(data, tplId, parentId) {
		var tpl = document.getElementById(tplId).innerHTML;
		var html = juicer(tpl, data);
		$('#' + parentId).append(html);
		$('.chathe').click(function (e){
			location.href=projectpath+'/static/htm/chat.html?userid='+this.dataset.userid					
		});
	};
	var sdk = new WSDK();
	var initimlogin=function (){
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/user/get/imuser",// 路径
			
			success : function(result) {// 返回数据根据结果进行相应的处理
				if(result.meta.rescode==0){					 
					sdk.Base.login({
					    uid:result.tUser.id+"",
					    appkey: result.albcappkey,
					    credential: result.tUser.impwd,
					    timeout: 4000,
					    success: function(data){
					    	console.log('login success', data);
					    	sdk.Base.getRecentContact({
					    	    count: 10,
					    	    success: function(data){
					    	        console.log('get recent contact success' ,data);
					    	        var cnts=data.data.cnts;
					    	        var uids=new Array()
					    	        for (var cni = 0; cni < cnts.length; cni++) {
					    	        	var luid=cnts[cni].uid
					    	        	var suid = sdk.Base.getNick(luid);
					    	        	uids[cni]=suid;
									}
					    	       
					    	        //设置未读消息数量
					    	        sdk.Base.getUnreadMsgCount({
							    	    count: 10,
							    	    success: function(msgdata){
							    	    	if (msgdata.data.length>0) {
												for (var msgint = 0; msgint < msgdata.data.length; msgint++) {
													for (var cni = 0; cni < cnts.length; cni++) {
									    	        	var luid=cnts[cni].uid
									    	        	var suid = sdk.Base.getNick(luid);
									    	        	var msguid=sdk.Base.getNick(msgdata.data[msgint].contact);
									    	        	if (suid==msguid) {
									    	        		cnts[cni].msgcount=msgdata.data[msgint].msgCount;
														}
													}
												}
											}
							    	        console.log('get unread msg count success' ,data);
							    	        //设置用户资料
							    	        var userMap=getusersInfo(uids);
							    	        for (var cni = 0; cni < cnts.length; cni++) {
							    	        	var luid=cnts[cni].uid
							    	        	var suid = sdk.Base.getNick(luid);
							    	        	if (userMap[suid]!=null) {
							    	        		cnts[cni].userinfo=userMap[suid];
												}
											}
							    	        juicerview(data.data, 'msgtpl', 'msgparentdiv');
							    	    },
							    	    error: function(error){
							    	        console.log('get unread msg count fail' ,error);
							    	    }
							    	});
					    	       
					    	    },
					    	    error: function(error){
					    	        console.log('get recent contact success' ,error);
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
	var getusersInfo=function (uids){
		var userMap=null;
		$.ajax({
			type : "POST", // 提交方式
			async:false,
			url : projectpath + "/app/user/get/users",// 路径
			data : {
				"uids" : uids
			},// 数据，这里使用的是Json格式进行传输
			success : function(result) {// 返回数据根据结果进行相应的处理
				if(result.tUsers!=null&&result.tUsers.length>0){
					userMap=result.userMap;
					//juicerview(result.page, tplId, parentId);
				}
			}
		});
		return userMap;
	};
	initimlogin();
});	