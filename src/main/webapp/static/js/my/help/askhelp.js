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
	helpajax.askhelppage(0,'askhelptpl','askhelpul');
	$('#helpbtn').click(function(e) {
		var helpsmscode=$('#helpsmscode').val();
        var helpid=$('#helpid').val();
		if( helpsmscode=='' ){
			$('.checkTxt').text('验证码不能为空')
		}else if( helpsmscode!=helpid ){
			$('.checkTxt').text('验证码错误')
		}

        helpajax.finishhelp(helpid,helpsmscode);
    });
		
	$('#helpsmscodediv button:first').click(function() {
        $('#helpsmscodediv').hide().find('.checkTxt').text('')
    });
		
});	