define(['jquery','md5','../module/msgbox','validate','cookie','baseurl'],
	function($,md5,msgbox,validate,cookie,baseurl){
	// var $= require('../lib/jquery'),
	// 		md5=require('../lib/jQuery.md5'),
	// 		validation=require('../lib/jquery.validate.min');
	var init,postForm,eleComfig,datas,formValid,init;
	eleComfig = {
		btn:$('.js_login_btn'),
		phone: $('.js_phone'),
		code:$('.js_phone_code')
	}

	formValid = function(ele){//validation中自定义验证手机号码(telphone)
		$.validator.addMethod(
		  "tel",                             // name验证方法名
		  function(value, element){        // 验证规则
		    return this.optional(element)  || (/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/).test(value);
		  },
		"手机号码不合法"        // 默认验证消息
		);
		ele.validate({
			rules:{
				valid_phone:{
					required:true,
					tel:true
				},
				valid_code:{
					required:true
				}
			},
			messages:{
				valid_phone:{
					required:'手机不能为空',
					tel:'请输入正确的手机号码'
				},
				valid_code:{
					required:'请输入短信验证码'
				}
			}
		});
	}

	postForm = function($form){
		var userid=eleComfig.phone.val().trim();
		var myDate=new Date();
		var dateString=myDate.getTime();
		var smscode=eleComfig.code.val();
		console.log(userid+'---'+smscode+'---'+dateString)
		var md5String=$.md5(userid + '123' + dateString);
		formValid($form);
		if($form.valid()){//做验证,最好加插件验证手机号码 必填
			$.ajax({
				type:'POST',
				url:baseurl.root+'app/login/smslogin?random='+Math.random(),
				data:{"userid":userid,"password":smscode,"key":"123","logintime":dateString,"md5key":md5String},
				success:function(data){
					if(data.meta.rescode==0){
						//将用户id和密码存到cookie
						var userid=data.tUser.userid,
								password=data.tUser.password;
						$.cookie('userid',userid,{ expires: 7 });
						$.cookie('password',password,{ expires: 7 });
						setTimeout(function(){

							console.log(baseurl.root+'static/htm/index.html');
							window.location.href=baseurl.root+'static/htm/index.html'
						},200);
					}else{
						msgbox.alert(data.meta.msg);
					}
				}
			});
		}
	}

	return{
		postForm:postForm
	}
});


