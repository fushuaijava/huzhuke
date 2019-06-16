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
	
	$('.help .tipTxt .iconfont').click(function(e) {
        $(this).closest('.tipTxt').hide();
    });
	helpajax.givehelppage(0,'givehelptpl','givehelpul');
	/*$('#helpbtn').click(function(e) {
		var helpsmscode=$('#helpsmscode').val();
        var helpid=$('#helpid').val();
        helpajax.finishhelp(helpid,helpsmscode);
    });*/
		
});	