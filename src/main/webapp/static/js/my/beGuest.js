require.config({
	baseUrl: "../js",
	 shim:{
		    'plupload':{
		      exports: 'plupload'
		    },
		    'cookie': ['jquery'],
		    'lazyload':['jquery']
		  },
    paths : {
        jquery: 'lib/jquery',
        lazyload: 'lib/lazyload',
        juicer:'lib/juicermin',
        dateutil:'lib/dateutil',
        houseajax:'my/house/houseajax',
        msgbox:'module/msgbox'
    }
})
var projectpath=location.protocol+'//'+location.host+'/zuoke';
require(['jquery','houseajax','msgbox','juicer','dateutil','lazyload'], function($,houseajax,msgbox) {
	//输入框焦点
	$('.txtInp').focus(function(e) {
		var oldVal=$(this).val();
		if(oldVal==this.defaultValue){
			$(this).val('').removeClass('grey');
		 };
	});
	$('.txtInp').blur(function(e) {
		var oldVal=$(this).val();
		if(oldVal==''){
			$(this).val(this.defaultValue).addClass('grey');
		 };
	});
	
	
	$('.loadmorepage').click(function(e) {
		if($(".loadmorepage").data("canload")=="yes"){
			$(".waitMore").show();
			var pageid=$(".loadmorepage").data("pageid");
			houseajax.pagelist(pageid+1,'housetpl','beGuestlist');
			$(".loadmorepage").data("pageid",pageid+1);
			$(".loadmorepage").data("canload","no");
		}else{
			msgbox.alert('亲，不要点那么快，还没反应过来呢，会疼的！');
		}
    });
	houseajax.pagelist(0,'housetpl','beGuestlist');

});
