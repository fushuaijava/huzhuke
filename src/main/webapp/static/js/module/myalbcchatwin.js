var projectpath=location.protocol+'//'+location.host+'/zuoke';
require.config({
	baseUrl: projectpath+"/static/js",
    paths : {
        jquery: 'lib/jquery',
        msgbox:'module/msgbox',
    /*    albcchat:'module/albcchat'*/
        
    }
});
define(['jquery','msgbox'],function($,msgbox){
	var chatwithhe=function(touserid){
		$.ajax({
			type : "POST", // 提交方式
			url : projectpath + "/app/user/get/imuser",// 路径
			
			success : function(result) {// 返回数据根据结果进行相应的处理
				if(result.meta.rescode==0){
					
					
					// 设置自定义主题
					WKIT.init({
					    container: document.getElementById('J_demo'),
					   // width: 700,
					   // height: 300,
					    uid: result.tUser.id+"",
					    appkey: result.albcappkey,
					    credential:result.tUser.impwd,
					    touid: touserid,
					    title:'与TA聊天',
					    themeBgColor: '#02a7e3',
					    themeColor: '#fff',
					    msgBgColor: '#02a7e3',
					    msgColor: '#fff'
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
	var getParameterByName = function(name) {
		var match = RegExp('[?&]' + name + '=([^&]*)').exec(
				window.location.search);
		return match
				&& decodeURIComponent(match[1].replace(/\+/g, ' '));
	}
	chatwithhe(getParameterByName('userid'));

return{
	chatwithhe:chatwithhe
  }

});