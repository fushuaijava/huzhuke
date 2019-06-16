var projectpath=location.protocol+'//'+location.host+'/zuoke';
require.config({
	baseUrl: projectpath+"/static/js",
    paths : {
        jquery: 'lib/jquery',
        juicer:'lib/juicermin',
        dateutil:'lib/dateutil',
        helpajax:'my/help/helpajax',
        msgbox:'module/msgbox',
    /*    albcchat:'module/albcchat'*/
        
    }
});
require(['jquery','helpajax','msgbox','juicer','dateutil'], function($,helpajax,msgbox) {
	
	$('.help .tipTxt .iconfont').click(function(e) {
		
        $(this).closest('.tipTxt').hide();
    });
	$('#replyinfo').click(function(){
		if($(this).val()=='我要说点什么'){
			$(this).val('');
		}
	});
	$('#replybtn').click(function() {
		var replyinfo=$('#replyinfo').val();
		if(replyinfo==''){
			$('#replyinfo').focus();
			msgbox.alert('回复消息，不能为空！');
		}else{
			helpajax.addhelprelply(helpajax.getParameterByName('id'),replyinfo);
		}
    });
	helpajax.helpdetailajax(helpajax.getParameterByName('id'),'helpdetailtpl','helpdetaildiv');	
	helpajax.helpreplypagelist(0,helpajax.getParameterByName('id'),'helpreplytpl','helpreplydiv');
	$('.loadmorepage').click(function(e) {
		if($(".loadmorepage").data("canload")=="yes"){
			
			$(".waitMore").show();
			var pageid=$(".loadmorepage").data("pageid");
			helpajax.helpreplypagelist(pageid+1,helpajax.getParameterByName('id'),'helpreplytpl','helpreplydiv');
			$(".loadmorepage").data("pageid",pageid+1);
			$(".loadmorepage").data("canload","no");
		}else{
			msgbox.alert('亲，不要点那么快，还没反应过来呢，会疼的！');
		}
    });
	
});	