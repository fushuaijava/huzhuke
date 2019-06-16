var projectpath=location.protocol+'//'+location.host+'/zuoke';
require.config({
	baseUrl: projectpath+"/static/js",
    paths : {
        jquery: 'lib/jquery',
		SuperSlide:'lib/TouchSlide',       
        juicer:'lib/juicermin',
        dateutil:'lib/dateutil',
        houseajax:'my/house/houseajax',
        msgbox:'module/msgbox'
        
    }
});
require(['jquery','SuperSlide','houseajax','msgbox','juicer','dateutil'], function($,SuperSlide,houseajax,msgbox) {
	var getParameterByName = function(name) {
		var match = RegExp('[?&]' + name + '=([^&]*)').exec(
				window.location.search);
		return match
				&& decodeURIComponent(match[1].replace(/\+/g, ' '));
	}
	$('.help .tipTxt .iconfont').click(function(e) {
		
        $(this).closest('.tipTxt').hide();
    });
	
	
	
	houseajax.housedetailajax(getParameterByName('id'),'housedetailtpl','housedetaildiv');	
	houseajax.userproductlist(getParameterByName('id'),'userproducttpl','userproductdiv');
	houseajax.commentpagelist(0,getParameterByName('id'),'housecommentpl','housecommendiv');
	$('.loadmorepage').click(function(e) {
		if($(".loadmorepage").data("canload")=="yes"){
			
			$(".waitMore").show();
			var pageid=$(".loadmorepage").data("pageid");
			houseajax.commentpagelist(pageid+1,getParameterByName('id'),'housecommentpl','housecommendiv');
			$(".loadmorepage").data("pageid",pageid+1);
			$(".loadmorepage").data("canload","no");
		}else{
			msgbox.alert('亲，不要点那么快，还没反应过来呢，会疼的！');
		}
    });
/*$('#findmobile').click(function(e) {
		
        $(this).html($("#mobiletd").data("mobilephone"));
    });*/
	//helpajax.helpreplypagelist(0,helpajax.getParameterByName('id'),'helpreplytpl','helpreplydiv');	
	
});	