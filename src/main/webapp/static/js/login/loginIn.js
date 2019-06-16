require.config({
//	baseUrl: "../js",
  shim:{
		'validate': ['jquery'],
		'cookie': ['jquery'],
		'md5': ['jquery']
  },
  paths : {
    jquery: '../lib/jquery',
    login: 'login',
    md5: '../lib/jQuery.md5',
    validate: '../lib/jquery.validate.min',
    cookie: '../lib/jquery.cookie',
    baseurl: '../module/baseurl',
    sendsms: '../my/sendsms'
  }
});


require(['jquery','login','sendsms','md5','cookie','baseurl','validate'],function($,login,sendsms,md5,cookie,baseurl){

  //进入页面查询是够已经存在cookie，有就跳转过去登录
    var userid=$.cookie('userid'),
        password=$.cookie('password');
    if(userid && password && userid!='null' &&  password!='null'){
      window.location.href=baseurl.root+'static/htm/index.html'
    }

	$(document).on('submit','.js_login_form',function(event){
		event.preventDefault();
		console.log(login.postForm)
		login.postForm($(this));
	})

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


	//手机号码校验
	(function(){
		var myPhone = $('.telInp');
		var myCkCode = $('.codeInp');
		var sendCd = $('.sendCd');
		myPhone.blur(function(){
			var phoneVal = myPhone.val();
			var phoneValCnt = $.trim(phoneVal).toString().length;
			if(phoneVal=='' || phoneVal=='请输入手机号码'){
				myPhone.focus().next('.checkTxt').text('手机号码不能为空');
			}else if( isNaN($.trim(phoneVal)) ){
				myPhone.focus().val('').next('.checkTxt').text('请输入正确的手机号码');
			}else if( !isNaN(phoneVal) && phoneValCnt != 11 ){
				myPhone.focus().next('.checkTxt').text('请输入正确的手机号码');
			}else{
				myPhone.next('.checkTxt').text('');
			};
		}).bind('input propertychange', function(){
			var phoneVal = myPhone.val();
			var phoneValCnt = $.trim(phoneVal).toString().length;
			if( !isNaN(phoneVal) && phoneValCnt==11 ){
				myPhone.next('.checkTxt').text('');
				sendCd.removeClass('dnIm');
			}else{
				sendCd.addClass('dnIm');
			};
		});

		//点击发送验证码
		sendCd.off('click');
		sendCd.on('click',function() {
			sendsms.sendSms($('.js_phone').val())
            var ct = 60;
			var ctTimer = null;
			function runTime(){
				if( ct== 0 ){
					sendCd.attr('disabled',false);
					sendCd.text('重新发送');
					clearInterval(ctTimer);
				}else{
					sendCd.attr('disabled',true);
					myCkCode.focus();
					sendCd.text('倒计时'+ct + '秒');
					ct--;
					ctTimer = setTimeout(runTime,1000);
				};
			};
			runTime();
        });

		myCkCode.focus(function(){
			var phoneVal = myPhone.val();
			if(phoneVal=='' || phoneVal=='请输入手机号码'){
				myPhone.focus().next('.checkTxt').text('手机号码不能为空');
			}else{
				myPhone.next('.checkTxt').text('');
			};
		});

	})();
});
