var projectpath=location.protocol+'//'+location.host+'/zuoke';
require.config({
	baseUrl: projectpath+"/static/js",
    paths : {
        jquery: 'lib/jquery',
        juicer:'lib/juicermin',
        dateutil:'lib/dateutil',
        guestorderajax:'my/guestorder/guestorderajax',
        msgbox:'module/msgbox'
        
    }
});
require(['jquery','guestorderajax','msgbox','juicer','dateutil'], function($,guestorderajax,msgbox) {
	

	guestorderajax.guestpagelist(0,'triptpl','tripul');
	/*$('#helpbtn').click(function(e) {
		var helpsmscode=$('#helpsmscode').val();
        var helpid=$('#helpid').val();
        helpajax.finishhelp(helpid,helpsmscode);
    });*/
	
	$('.commentdiv .iconfont').click(function(){
		var _this = $(this)
		var num_level = $(this).index()
		$('#level').val(num_level+1)
		for( var a=0; a<=num_level; a++ ){
			$('.commentdiv .iconfont').eq(a).removeClass('grey').addClass('orange')
		}
		for( var b=num_level+1; b<$('.commentdiv .iconfont').length; b++ ){
			$('.commentdiv .iconfont').eq(b).removeClass('orange').addClass('grey')
		}

	})
		
});	