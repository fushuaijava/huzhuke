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
	
	$('.help .tipTxt .iconfont').click(function(e) {
        $(this).closest('.tipTxt').hide();
    });
	guestorderajax.masterpagelist(0,'receptiontpl','receptionul');
	/*$('#helpbtn').click(function(e) {
		var helpsmscode=$('#helpsmscode').val();
        var helpid=$('#helpid').val();
        helpajax.finishhelp(helpid,helpsmscode);
    });*/
		
});	