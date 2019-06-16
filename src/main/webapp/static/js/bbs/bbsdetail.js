var projectpath=location.protocol+'//'+location.host+'/zuoke';
require.config({
	baseUrl: projectpath+"/static/js",
    paths : {
        jquery: 'lib/jquery',
        juicer:'lib/juicermin',
        dateutil:'lib/dateutil',
        bbsajax:'bbs/bbsajax',
        msgbox:'module/msgbox'
        
    }
});
require(['jquery','bbsajax','msgbox','juicer','dateutil'], function($,bbsajax,msgbox) {
	

	/*$('#replyinfo').click(function(){
		if($(this).val()=='我要说点什么'){
			$(this).val('');
		}
	});*/
	$('#replybbsbtn').click(function(){
		$("#replytouser").html("");
		$("#senreplydiv").hide();
		$("#replyId").val("");
		
	});
	$('#replybtn').click(function() {
		var replyinfo=$('#replyinfo').val();
		if(replyinfo==''){
			$('#replyinfo').focus();
			msgbox.alert('回复消息，不能为空！');
		}else{
			if ($("#replyId").val()==null||$("#replyId").val()=="") {
				
				bbsajax.addinvrelply(bbsajax.getParameterByName('id'),replyinfo);
			}else{
				bbsajax.addinvrelply(bbsajax.getParameterByName('id'),replyinfo,$("#replyId").val());
			}
		}
    });
	bbsajax.invitationdetailajax(bbsajax.getParameterByName('id'),'bbsdetailtpl','bbsdetaildiv','rbbsrelplytpl','rbbsrelply');	
	$('.loadmorepage').click(function(e) {
		if($(".loadmorepage").data("canload")=="yes"){
			
			$(".waitMore").show();
			var pageid=$(".loadmorepage").data("pageid");
			bbsajax.invreplypagelist(bbsajax.getParameterByName('id'),pageid+1,'rbbsrelplytpl','rbbsrelply');
			$(".loadmorepage").data("pageid",pageid+1);
			$(".loadmorepage").data("canload","no");
		}else{
			msgbox.alert('亲，不要点那么快，还没反应过来呢，会疼的！');
		}
    });
	//bbsajax.helpreplypagelist(0,bbsajax.getParameterByName('id'),'helpreplytpl','helpreplydiv');	
	
});	